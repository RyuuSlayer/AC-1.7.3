package io.github.ryuu.adventurecraft.scripting;

import java.util.Iterator;
import java.util.LinkedList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.script.ScriptChat;
import net.minecraft.script.ScriptContinuation;
import net.minecraft.script.ScriptEffect;
import net.minecraft.script.ScriptEntityPlayer;
import net.minecraft.script.ScriptKeyboard;
import net.minecraft.script.ScriptScript;
import net.minecraft.script.ScriptSound;
import net.minecraft.script.ScriptTime;
import net.minecraft.script.ScriptUI;
import net.minecraft.script.ScriptWeather;
import net.minecraft.script.ScriptWorld;
import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class Script {

    public Scriptable globalScope;

    Scriptable curScope;

    Scriptable runScope;

    Context cx;

    ScriptTime time;

    ScriptWorld world;

    ScriptEntityPlayer player;

    ScriptChat chat;

    ScriptWeather weather;

    ScriptEffect effect;

    ScriptSound sound;

    ScriptUI ui;

    ScriptScript script;

    public ScriptKeyboard keyboard;

    LinkedList<ScriptContinuation> sleepingScripts = new LinkedList();

    LinkedList<ScriptContinuation> removeMe = new LinkedList();

    static boolean shutterSet = false;

    public Script(MixinLevel w) {
        this.cx = ContextFactory.getGlobal().enterContext();
        this.cx.setOptimizationLevel(-1);
        if (!shutterSet) {
            this.cx.setClassShutter(new ClassShutter() {

                public boolean visibleToScripts(String className) {
                    return className.startsWith("net.minecraft.script") || className.equals((Object) "java.lang.Object") || className.equals((Object) "java.lang.String") || className.equals((Object) "java.lang.Double") || className.equals((Object) "java.lang.Boolean");
                }
            });
            shutterSet = true;
        }
        this.globalScope = this.cx.initStandardObjects();
        this.runScope = this.cx.newObject(this.globalScope);
        this.runScope.setParentScope(this.globalScope);
        this.time = new ScriptTime(w);
        this.world = new ScriptWorld(w);
        this.chat = new ScriptChat();
        this.weather = new ScriptWeather(w);
        this.effect = new ScriptEffect(w, Minecraft.minecraftInstance.worldRenderer);
        this.sound = new ScriptSound(Minecraft.minecraftInstance.soundHelper);
        this.ui = new ScriptUI();
        this.script = new ScriptScript(w);
        this.keyboard = new ScriptKeyboard(w, Minecraft.minecraftInstance.options, this.getNewScope());
        Object wrappedOut = Context.javaToJS((Object) this.time, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "time", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.world, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "world", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.chat, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "chat", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.weather, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "weather", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.effect, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "effect", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.sound, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "sound", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.ui, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "ui", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) Minecraft.minecraftInstance.overlay.scriptUI, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "screen", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.script, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "script", (Object) wrappedOut);
        wrappedOut = Context.javaToJS((Object) this.keyboard, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "keyboard", (Object) wrappedOut);
        wrappedOut = Context.javaToJS(null, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "hitEntity", (Object) wrappedOut);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "hitBlock", (Object) wrappedOut);
        this.runString("Item = net.minecraft.script.ScriptItem");
        this.runString("UILabel = net.minecraft.script.ScriptUILabel");
        this.runString("UISprite = net.minecraft.script.ScriptUISprite");
        this.runString("UIRect = net.minecraft.script.ScriptUIRect");
        this.runString("UIContainer = net.minecraft.script.ScriptUIContainer");
        this.runString("UIContainer = net.minecraft.script.ScriptUIContainer");
        this.runString("Model = net.minecraft.script.ScriptModel");
        this.runString("Vec3 = net.minecraft.script.ScriptVec3");
    }

    public void addObject(String name, Object o) {
        Object wrappedOut = Context.javaToJS((Object) o, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) name, (Object) wrappedOut);
    }

    protected void finalize() {
        Script script = this;
        script.cx.exit();
    }

    public void initPlayer() {
        this.player = new ScriptEntityPlayer(Minecraft.minecraftInstance.player);
        Object wrappedOut = Context.javaToJS((Object) this.player, (Scriptable) this.globalScope);
        ScriptableObject.putProperty((Scriptable) this.globalScope, (String) "player", (Object) wrappedOut);
    }

    public String runString(String script) {
        Object result;
        org.mozilla.javascript.Script s = this.compileString(script, "<cmd>");
        if (s != null && (result = this.runScript(s, this.runScope)) != null) {
            return Context.toString((Object) result);
        }
        return null;
    }

    public org.mozilla.javascript.Script compileString(String script, String sourceName) {
        try {
            return this.cx.compileString(script, sourceName, 1, null);
        } catch (Exception e) {
            Minecraft.minecraftInstance.overlay.addChatMessage("Javascript Error: " + e.getMessage());
            return null;
        }
    }

    public Scriptable getNewScope() {
        Scriptable scope = this.cx.newObject(this.globalScope);
        scope.setParentScope(this.globalScope);
        return scope;
    }

    public Object runScript(org.mozilla.javascript.Script script, Scriptable scope) {
        if (this.curScope == null) {
            try {
                this.curScope = scope;
                Object object = this.cx.executeScriptWithContinuations(script, scope);
                return object;
            } catch (ContinuationPending e) {
            } catch (Exception e) {
                Minecraft.minecraftInstance.overlay.addChatMessage("Javascript Error: " + e.getMessage());
            } finally {
                this.curScope = null;
            }
        } else {
            return script.exec(this.cx, this.curScope);
        }
        return null;
    }

    public void wakeupScripts(long tick) {
        for (ScriptContinuation c : this.sleepingScripts) {
            if (c.wakeUp > tick)
                continue;
            this.removeMe.add((Object) c);
        }
        Iterator i$ = this.removeMe.iterator();
        while (true) {
            ScriptContinuation c;
            if (!i$.hasNext()) {
                this.removeMe.clear();
                return;
            }
            c = (ScriptContinuation) i$.next();
            this.sleepingScripts.remove((Object) c);
            try {
                this.curScope = c.scope;
                this.cx.resumeContinuation(c.contituation, c.scope, null);
                continue;
            } catch (ContinuationPending e) {
                continue;
            } catch (Exception e) {
                Minecraft.minecraftInstance.overlay.addChatMessage("Javascript Error: " + e.getMessage());
                continue;
            } finally {
                this.curScope = null;
                continue;
            }
            break;
        }
    }

    public void sleep(float t) {
        int ticks = (int) (20.0f * t);
        ContinuationPending cp = this.cx.captureContinuation();
        this.sleepingScripts.add((Object) new ScriptContinuation(cp.getContinuation(), this.time.getTickCount() + (long) ticks, this.curScope));
        throw cp;
    }
}
