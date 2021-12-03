package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import net.minecraft.client.options.GameOptions;
import net.minecraft.level.Level;
import org.lwjgl.input.Keyboard;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.HashMap;

@SuppressWarnings("unused")
public class ScriptKeyboard {

    private final GameOptions gameSettings;
    public String keyForwardScript = "";
    public String keyBackScript = "";
    public String keyLeftScript = "";
    public String keyRightScript = "";
    public String keyJumpScript = "";
    public String keySneakScript = "";
    String allKeys;
    HashMap<Integer, String> keyBinds;
    Level level;
    Scriptable scope;

    public ScriptKeyboard(Level level, GameOptions options, Scriptable scope) {
        this.level = level;
        this.keyBinds = new HashMap<>();
        this.allKeys = null;
        this.scope = scope;
        this.gameSettings = options;
    }

    public void bindKey(int keyID, String script) {
        this.keyBinds.put(keyID, script);
    }

    public void unbindKey(int keyID) {
        this.keyBinds.remove(keyID);
    }

    public void bindAllKeyScript(String script) {
        this.allKeys = script;
    }

    public void unbindAllKeyScript() {
        this.allKeys = null;
    }

    public void processKeyPress(int keyID) {
        ExLevel level = (ExLevel) this.level;
        boolean keyIDSet = false;
        String script = this.keyBinds.get(keyID);
        if (script != null) {
            keyIDSet = true;
            Object wrappedOut = Context.javaToJS(keyID, level.getScope());
            ScriptableObject.putProperty(level.getScope(), "keyID", wrappedOut);
            level.getScriptHandler().runScript(script, level.getScope());
        }
        if (this.allKeys != null) {
            if (!keyIDSet) {
                Object wrappedOut = Context.javaToJS(keyID, level.getScope());
                ScriptableObject.putProperty(level.getScope(), "keyID", wrappedOut);
            }
            level.getScriptHandler().runScript(this.allKeys, level.getScope());
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
        if (keyID == this.gameSettings.forwardKey.key) {
            scriptToRun = this.keyForwardScript;
        }
        if (keyID == this.gameSettings.backKey.key) {
            scriptToRun = this.keyBackScript;
        }
        if (keyID == this.gameSettings.leftKey.key) {
            scriptToRun = this.keyLeftScript;
        } else if (keyID == this.gameSettings.rightKey.key) {
            scriptToRun = this.keyRightScript;
        } else if (keyID == this.gameSettings.jumpKey.key) {
            scriptToRun = this.keyJumpScript;
        } else if (keyID == this.gameSettings.sneakKey.key) {
            scriptToRun = this.keySneakScript;
        }
        if (scriptToRun != null && !scriptToRun.equals("")) {
            allowProcess = this.runScript(scriptToRun, keyID, keyState);
        }
        return allowProcess;
    }

    private boolean runScript(String scriptName, int keyID, boolean keyState) {
        ExLevel level = (ExLevel) this.level;
        Object wrappedOut = Context.javaToJS(keyID, level.getScope());
        ScriptableObject.putProperty(level.getScope(), "keyID", wrappedOut);
        wrappedOut = Context.javaToJS(keyState, this.scope);
        ScriptableObject.putProperty(this.scope, "keyState", wrappedOut);
        Object result = level.getScriptHandler().runScript(scriptName, this.scope);
        if (!(result instanceof Boolean)) {
            return true;
        }
        return (Boolean) result;
    }
}
