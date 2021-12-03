package io.github.ryuu.adventurecraft.extensions.client.gui;

import io.github.ryuu.adventurecraft.scripting.ScriptUIContainer;

public interface ExOverlay {

    ScriptUIContainer getScriptUI();

    boolean isHudEnabled();

    void setHudEnabled(boolean hudEnabled);
}
