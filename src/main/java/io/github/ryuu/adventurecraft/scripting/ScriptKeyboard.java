package io.github.ryuu.adventurecraft.scripting;

import java.util.HashMap;
import net.minecraft.client.options.GameOptions;
import net.minecraft.level.Level;
import org.lwjgl.input.Keyboard;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ScriptKeyboard {
    private GameOptions gameSettings;

    public String keyForwardScript;

    public String keyBackScript;

    public String keyLeftScript;

    public String keyRightScript;

    public String keyJumpScript;

    public String keySneakScript;

    String allKeys;

    HashMap<Integer, String> keyBinds;

    Level world;

    Scriptable scope;

    ScriptKeyboard(Level w, GameOptions g, Scriptable s) {
        this.keyForwardScript = "";
        this.keyBackScript = "";
        this.keyLeftScript = "";
        this.keyRightScript = "";
        this.keyJumpScript = "";
        this.keySneakScript = "";
        this.world = w;
        this.keyBinds = new HashMap<>();
        this.allKeys = null;
        this.scope = s;
        this.gameSettings = g;
    }

    public void bindKey(int keyID, String script) {
        this.keyBinds.put(new Integer(keyID), script);
    }

    public void unbindKey(int keyID) {
        this.keyBinds.remove(new Integer(keyID));
    }

    public void bindAllKeyScript(String script) {
        this.allKeys = script;
    }

    public void unbindAllKeyScript() {
        this.allKeys = null;
    }

    public void processKeyPress(int keyID) {
        boolean keyIDSet = false;
        String script = this.keyBinds.get(Integer.valueOf(keyID));
        if (script != null) {
            keyIDSet = true;
            Object wrappedOut = Context.javaToJS(Integer.valueOf(keyID), this.world.scope);
            ScriptableObject.putProperty(this.world.scope, "keyID", wrappedOut);
            this.world.scriptHandler.runScript(script, this.world.scope);
        }
        if (this.allKeys != null) {
            if (!keyIDSet) {
                Object wrappedOut = Context.javaToJS(Integer.valueOf(keyID), this.world.scope);
                ScriptableObject.putProperty(this.world.scope, "keyID", wrappedOut);
            }
            this.world.scriptHandler.runScript(this.allKeys, this.world.scope);
        }
    }

    public boolean isKeyDown(int keyID) {
        return Keyboard.isKeyDown(keyID);
    }

    public String getKeyName(int keyID) {
        return Keyboard.getKeyName(keyID);
    }

    public int getKeyID(String keyName) {
        return Keyboard.getKeyIndex(keyName);
    }

    public boolean processPlayerKeyPress(int keyID, boolean keyState) {
        boolean allowProcess = true;
        String scriptToRun = "";
        if (keyID == this.gameSettings.m.b)
            scriptToRun = this.keyForwardScript;
        if (keyID == this.gameSettings.o.b)
            scriptToRun = this.keyBackScript;
        if (keyID == this.gameSettings.n.b) {
            scriptToRun = this.keyLeftScript;
        } else if (keyID == this.gameSettings.p.b) {
            scriptToRun = this.keyRightScript;
        } else if (keyID == this.gameSettings.q.b) {
            scriptToRun = this.keyJumpScript;
        } else if (keyID == this.gameSettings.v.b) {
            scriptToRun = this.keySneakScript;
        }
        if (scriptToRun != null && !scriptToRun.equals(""))
            allowProcess = runScript(scriptToRun, keyID, keyState);
        return allowProcess;
    }

    private boolean runScript(String scriptName, int keyID, boolean keyState) {
        Object wrappedOut = Context.javaToJS(Integer.valueOf(keyID), this.world.scope);
        ScriptableObject.putProperty(this.world.scope, "keyID", wrappedOut);
        wrappedOut = Context.javaToJS(Boolean.valueOf(keyState), this.scope);
        ScriptableObject.putProperty(this.scope, "keyState", wrappedOut);
        Object result = this.world.scriptHandler.runScript(scriptName, this.scope);
        if (result == null || !(result instanceof Boolean))
            return true;
        return ((Boolean)result).booleanValue();
    }
}
