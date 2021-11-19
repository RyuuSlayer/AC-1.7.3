package io.github.ryuu.adventurecraft.scripting;

public class ScriptScript {

    MixinLevel world;

    ScriptScript(MixinLevel w) {
        this.world = w;
    }

    public Object runScript(String scriptName) {
        return this.world.scriptHandler.runScript(scriptName, null);
    }

    public void openUrl(String url) {
        GuiUrlRequest.showUI(url);
    }

    public void openUrl(String url, String msg) {
        GuiUrlRequest.showUI(url, msg);
    }
}
