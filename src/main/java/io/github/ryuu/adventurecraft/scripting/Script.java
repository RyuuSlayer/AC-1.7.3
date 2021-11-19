package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;
import org.mozilla.javascript.*;

import java.util.Iterator;
import java.util.LinkedList;

public class Script {

    static boolean shutterSet = false;
    public Scriptable globalScope;
    public ScriptKeyboard keyboard;
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
    LinkedList<ScriptContinuation> sleepingScripts = new LinkedList();
    LinkedList<ScriptContinuation> removeMe = new LinkedList();

    public Script(MixinLevel w) {
        this.cx = ContextFactory.getGlobal().enterContext();
        this.cx.setOptimizationLevel(-1);
        if (!shutterSet) {
            this.cx.setClassShutter(new ClassShutter() {

                public boolean visibleToScripts(String className) {
                    return className.startsWith("net.minecraft.script") || className.equals("java.lang.Object") || className.equals("java.lang.String") || className.equals("java.lang.Double") || className.equals("java.lang.Boolean");
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
        Object wrappedOut = Context.javaToJS(this.time, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "time", wrappedOut);
        wrappedOut = Context.javaToJS(this.world, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "world", wrappedOut);
        wrappedOut = Context.javaToJS(this.chat, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "chat", wrappedOut);
        wrappedOut = Context.javaToJS(this.weather, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "weather", wrappedOut);
        wrappedOut = Context.javaToJS(this.effect, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "effect", wrappedOut);
        wrappedOut = Context.javaToJS(this.sound, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "sound", wrappedOut);
        wrappedOut = Context.javaToJS(this.ui, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "ui", wrappedOut);
        wrappedOut = Context.javaToJS((Object) Minecraft.minecraftInstance.overlay.scriptUI, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "screen", wrappedOut);
        wrappedOut = Context.javaToJS(this.script, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "script", wrappedOut);
        wrappedOut = Context.javaToJS(this.keyboard, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "keyboard", wrappedOut);
        wrappedOut = Context.javaToJS(null, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "hitEntity", wrappedOut);
        ScriptableObject.putProperty(this.globalScope, "hitBlock", wrappedOut);
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
        Object wrappedOut = Context.javaToJS(o, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, name, wrappedOut);
    }

    protected void finalize() {
        Script script = this;
        Context.exit();
    }

    public void initPlayer() {
        this.player = new ScriptEntityPlayer(Minecraft.minecraftInstance.player);
        Object wrappedOut = Context.javaToJS(this.player, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "player", wrappedOut);
    }

    public String runString(String script) {
        Object result;
        org.mozilla.javascript.Script s = this.compileString(script, "<cmd>");
        if (s != null && (result = this.runScript(s, this.runScope)) != null) {
            return Context.toString(result);
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
            if (c.wakeUp > tick) continue;
            this.removeMe.add(c);
        }
        Iterator i$ = this.removeMe.iterator();
        while (true) {
            ScriptContinuation c;
            if (!i$.hasNext()) {
                this.removeMe.clear();
                return;
            }
            c = (ScriptContinuation) i$.next();
            this.sleepingScripts.remove(c);
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
        this.sleepingScripts.add(new ScriptContinuation(cp.getContinuation(), this.time.getTickCount() + (long) ticks, this.curScope));
        throw cp;
    }
}
