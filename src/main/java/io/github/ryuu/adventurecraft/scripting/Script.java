package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.client.gui.ExOverlay;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.level.Level;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("unused")
public class Script {

    static final String scriptPackage = "io.github.ryuu.adventurecraft.scripting";

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
    LinkedList<ScriptContinuation> sleepingScripts = new LinkedList<>();
    LinkedList<ScriptContinuation> removeMe = new LinkedList<>();

    public Script(Level level) {
        this.cx = Context.enter();
        this.cx.setOptimizationLevel(-1);
        if (!shutterSet) {
            this.cx.setClassShutter(className -> className.startsWith(scriptPackage) || className.equals("java.lang.Object") || className.equals("java.lang.String") || className.equals("java.lang.Double") || className.equals("java.lang.Boolean"));
            shutterSet = true;
        }
        this.globalScope = this.cx.initStandardObjects();
        this.runScope = this.cx.newObject(this.globalScope);
        this.runScope.setParentScope(this.globalScope);
        this.time = new ScriptTime(level);
        this.world = new ScriptWorld(level);
        this.chat = new ScriptChat();
        this.weather = new ScriptWeather(level);
        this.effect = new ScriptEffect(level, AccessMinecraft.getInstance().worldRenderer);
        this.sound = new ScriptSound(AccessMinecraft.getInstance().soundHelper);
        this.ui = new ScriptUI();
        this.script = new ScriptScript(level);
        this.keyboard = new ScriptKeyboard(level, AccessMinecraft.getInstance().options, this.getNewScope());
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
        wrappedOut = Context.javaToJS(((ExOverlay) AccessMinecraft.getInstance().overlay).getScriptUI(), this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "screen", wrappedOut);
        wrappedOut = Context.javaToJS(this.script, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "script", wrappedOut);
        wrappedOut = Context.javaToJS(this.keyboard, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "keyboard", wrappedOut);
        wrappedOut = Context.javaToJS(null, this.globalScope);
        ScriptableObject.putProperty(this.globalScope, "hitEntity", wrappedOut);
        ScriptableObject.putProperty(this.globalScope, "hitBlock", wrappedOut);

        this.runString(String.format("Item = Packages.%s.ScriptItem", scriptPackage));
        this.runString(String.format("UILabel = Packages.%s.ScriptUILabel", scriptPackage));
        this.runString(String.format("UISprite = Packages.%s.ScriptUISprite", scriptPackage));
        this.runString(String.format("UIRect = Packages.%s.ScriptUIRect", scriptPackage));
        this.runString(String.format("UIContainer = Packages.%s.ScriptUIContainer", scriptPackage));
        this.runString(String.format("UIContainer = Packages.%s.ScriptUIContainer", scriptPackage));
        this.runString(String.format("Model = Packages.%s.ScriptModel", scriptPackage));
        this.runString(String.format("Vec3 = Packages.%s.ScriptVec3", scriptPackage));
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
        this.player = new ScriptEntityPlayer(AccessMinecraft.getInstance().player);
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
            ((ExOverlay) AccessMinecraft.getInstance().overlay).logJavascriptException(e);
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
                ((ExOverlay) AccessMinecraft.getInstance().overlay).logJavascriptException(e);
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
        Iterator<ScriptContinuation> i$ = this.removeMe.iterator();
        while (true) {
            if (!i$.hasNext()) {
                this.removeMe.clear();
                return;
            }
            ScriptContinuation c = i$.next();
            this.sleepingScripts.remove(c);
            try {
                this.curScope = c.scope;
                this.cx.resumeContinuation(c.contituation, c.scope, null);
            } catch (ContinuationPending e) {
            } catch (Exception e) {
                ((ExOverlay) AccessMinecraft.getInstance().overlay).logJavascriptException(e);
            } finally {
                this.curScope = null;
            }
        }
    }

    public void sleep(float t) {
        int ticks = (int) (20.0f * t);
        ContinuationPending cp = this.cx.captureContinuation();
        this.sleepingScripts.add(new ScriptContinuation(cp.getContinuation(), this.time.getTickCount() + (long) ticks, this.curScope));
        throw cp;
    }
}
