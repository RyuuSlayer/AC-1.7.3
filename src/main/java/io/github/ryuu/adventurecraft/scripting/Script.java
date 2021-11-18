package io.github.ryuu.adventurecraft.scripting;

import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

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
    LinkedList<ScriptContinuation> sleepingScripts;
    LinkedList<ScriptContinuation> removeMe;

    public Script(Level w) {
        this.sleepingScripts = new LinkedList<>();
        this.removeMe = new LinkedList<>();
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
        this.effect = new ScriptEffect(w, Minecraft.minecraftInstance.g);
        this.sound = new ScriptSound(Minecraft.minecraftInstance.B);
        this.ui = new ScriptUI();
        this.script = new ScriptScript(w);
        this.keyboard = new ScriptKeyboard(w, Minecraft.minecraftInstance.z, getNewScope());
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
        wrappedOut = Context.javaToJS(Minecraft.minecraftInstance.v.scriptUI, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "screen", wrappedOut);
        wrappedOut = Context.javaToJS(this.script, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "script", wrappedOut);
        wrappedOut = Context.javaToJS(this.keyboard, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "keyboard", wrappedOut);
        wrappedOut = Context.javaToJS(null, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "hitEntity", wrappedOut);
        ScriptableObject.putProperty(this.globalScope, "hitBlock", wrappedOut);
        runString("Item = net.minecraft.script.ScriptItem");
        runString("UILabel = net.minecraft.script.ScriptUILabel");
        runString("UISprite = net.minecraft.script.ScriptUISprite");
        runString("UIRect = net.minecraft.script.ScriptUIRect");
        runString("UIContainer = net.minecraft.script.ScriptUIContainer");
        runString("UIContainer = net.minecraft.script.ScriptUIContainer");
        runString("Model = net.minecraft.script.ScriptModel");
        runString("Vec3 = net.minecraft.script.ScriptVec3");
    }

    public void addObject(String name, Object o) {
        Object wrappedOut = Context.javaToJS(o, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, name, wrappedOut);
    }

    protected void finalize() {
        Context.exit();
    }

    public void initPlayer() {
        this.player = new ScriptEntityPlayer((Player) Minecraft.minecraftInstance.h);
        Object wrappedOut = Context.javaToJS(this.player, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "player", wrappedOut);
    }

    public String runString(String script) {
        org.mozilla.javascript.Script s = compileString(script, "<cmd>");
        if (s != null) {
            Object result = runScript(s, this.runScope);
            if (result != null)
                return Context.toString(result);
        }
        return null;
    }

    public org.mozilla.javascript.Script compileString(String script, String sourceName) {
        try {
            return this.cx.compileString(script, sourceName, 1, null);
        } catch (Exception e) {
            Minecraft.minecraftInstance.v.a("Javascript Error: " + e.getMessage());
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
                return this.cx.executeScriptWithContinuations(script, scope);
            } catch (ContinuationPending e) {

            } catch (Exception e) {
                Minecraft.minecraftInstance.v.a("Javascript Error: " + e.getMessage());
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
            if (c.wakeUp <= tick)
                this.removeMe.add(c);
        }
        for (ScriptContinuation c : this.removeMe) {
            this.sleepingScripts.remove(c);
            try {
                this.curScope = c.scope;
                this.cx.resumeContinuation(c.contituation, c.scope, null);
            } catch (ContinuationPending e) {

            } catch (Exception e) {
                Minecraft.minecraftInstance.v.a("Javascript Error: " + e.getMessage());
            } finally {
                this.curScope = null;
            }
        }
        this.removeMe.clear();
    }

    public void sleep(float t) {
        int ticks = (int) (20.0F * t);
        ContinuationPending cp = this.cx.captureContinuation();
        this.sleepingScripts.add(new ScriptContinuation(cp.getContinuation(), this.time.getTickCount() + ticks, this.curScope));
        throw cp;
    }
}