package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ScreenScaler;

public class ScriptUI {
    Minecraft mc = Minecraft.minecraftInstance;

    public int getWidth() {
        ScreenScaler scaledresolution = new ScreenScaler(this.mc.z, this.mc.d, this.mc.e);
        return scaledresolution.getScaledWidth();
    }

    public int getHeight() {
        ScreenScaler scaledresolution = new ScreenScaler(this.mc.z, this.mc.d, this.mc.e);
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
        return this.mc.overlay.hudEnabled;
    }

    public void setHudEnabled(boolean b) {
        this.mc.overlay.hudEnabled = b;
    }
}