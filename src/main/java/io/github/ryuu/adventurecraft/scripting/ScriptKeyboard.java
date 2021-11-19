package io.github.ryuu.adventurecraft.scripting;

import org.lwjgl.input.Keyboard;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.HashMap;

public class ScriptKeyboard {

    private final MixinGameOptions gameSettings;
    public String keyForwardScript = "";
    public String keyBackScript = "";
    public String keyLeftScript = "";
    public String keyRightScript = "";
    public String keyJumpScript = "";
    public String keySneakScript = "";
    String allKeys;
    HashMap<Integer, String> keyBinds;
    MixinLevel world;
    Scriptable scope;

    ScriptKeyboard(MixinLevel w, MixinGameOptions g, Scriptable s) {
        this.world = w;
        this.keyBinds = new HashMap();
        this.allKeys = null;
        this.scope = s;
        this.gameSettings = g;
    }

    public void bindKey(int keyID, String script) {
        this.keyBinds.put((Object) new Integer(keyID), (Object) script);
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
        Object wrappedOut;
        boolean keyIDSet = false;
        String script = this.keyBinds.get(keyID);
        if (script != null) {
            keyIDSet = true;
            wrappedOut = Context.javaToJS(keyID, (Scriptable) this.world.scope);
            ScriptableObject.putProperty((Scriptable) this.world.scope, "keyID", wrappedOut);
            this.world.scriptHandler.runScript(script, this.world.scope);
        }
        if (this.allKeys != null) {
            if (!keyIDSet) {
                wrappedOut = Context.javaToJS(keyID, (Scriptable) this.world.scope);
                ScriptableObject.putProperty((Scriptable) this.world.scope, "keyID", wrappedOut);
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
        Object wrappedOut = Context.javaToJS(keyID, (Scriptable) this.world.scope);
        ScriptableObject.putProperty((Scriptable) this.world.scope, "keyID", wrappedOut);
        wrappedOut = Context.javaToJS(keyState, this.scope);
        ScriptableObject.putProperty(this.scope, "keyState", wrappedOut);
        Object result = this.world.scriptHandler.runScript(scriptName, this.scope);
        if (result == null || !(result instanceof Boolean)) {
            return true;
        }
        return (Boolean) result;
    }
}
