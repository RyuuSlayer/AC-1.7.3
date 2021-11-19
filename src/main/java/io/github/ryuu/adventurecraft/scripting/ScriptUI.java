/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ScreenScaler;

public class ScriptUI {

    Minecraft mc = Minecraft.minecraftInstance;

    ScriptUI() {
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
        return this.mc.overlay.hudEnabled;
    }

    public void setHudEnabled(boolean b) {
        this.mc.overlay.hudEnabled = b;
    }
}
