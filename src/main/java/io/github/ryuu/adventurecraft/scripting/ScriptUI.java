package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.client.gui.ExOverlay;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ScreenScaler;

@SuppressWarnings("unused")
public class ScriptUI {

    Minecraft mc = AccessMinecraft.getInstance();

    public ScriptUI() {
    }

    public int getWidth() {
        ScreenScaler scaledresolution = new ScreenScaler(this.mc.options, this.mc.actualWidth, this.mc.actualHeight);
        return scaledresolution.getScaledWidth();
    }

    public int getHeight() {
        ScreenScaler scaledresolution = new ScreenScaler(this.mc.options, this.mc.actualWidth, this.mc.actualHeight);
        return scaledresolution.getScaledHeight();
    }

    public int getStringWidth(String s) {
        return this.mc.textRenderer.getTextWidth(s);
    }

    public int getScale() {
        return this.mc.options.guiScale;
    }

    public boolean getGUIHidden() {
        return this.mc.options.hideHud;
    }

    public void setGUIHidden(boolean i) {
        this.mc.options.hideHud = i;
    }

    public boolean getThirdPerson() {
        return this.mc.options.thirdPerson;
    }

    public void setThirdPerson(boolean i) {
        this.mc.options.thirdPerson = i;
    }

    public boolean getFancyGraphics() {
        return this.mc.options.fancyGraphics;
    }

    public boolean getHudEnabled() {
        return ((ExOverlay) this.mc.overlay).isHudEnabled();
    }

    public void setHudEnabled(boolean b) {
        ((ExOverlay) this.mc.overlay).setHudEnabled(b);
    }
}
