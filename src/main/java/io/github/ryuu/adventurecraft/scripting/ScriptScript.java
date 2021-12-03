package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.gui.GuiUrlRequest;
import net.minecraft.level.Level;

@SuppressWarnings("unused")
public class ScriptScript {

    Level level;

    public ScriptScript(Level level) {
        this.level = level;
    }

    public Object runScript(String scriptName) {
        return ((ExLevel) this.level).getScriptHandler().runScript(scriptName, null);
    }

    public void openUrl(String url) {
        GuiUrlRequest.showUI(url);
    }

    public void openUrl(String url, String msg) {
        GuiUrlRequest.showUI(url, msg);
    }
}
