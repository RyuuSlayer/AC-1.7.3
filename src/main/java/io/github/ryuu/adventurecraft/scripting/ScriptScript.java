package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.Level;

public class ScriptScript {

    Level world;

    ScriptScript(Level w) {
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
