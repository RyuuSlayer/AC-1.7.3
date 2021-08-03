package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ScreenScaler;

public class ScriptUI {
    Minecraft mc = Minecraft.minecraftInstance;

    public int getWidth() {
        ScreenScaler scaledresolution = new ScreenScaler(this.mc.z, this.mc.d, this.mc.e);
        return scaledresolution.a();
    }

    public int getHeight() {
        ScreenScaler scaledresolution = new ScreenScaler(this.mc.z, this.mc.d, this.mc.e);
        return scaledresolution.b();
    }

    public int getStringWidth(String s) {
        return this.mc.q.a(s);
    }

    public int getScale() {
        return this.mc.z.I;
    }

    public boolean getGUIHidden() {
        return this.mc.z.z;
    }

    public void setGUIHidden(boolean i) {
        this.mc.z.z = i;
    }

    public boolean getThirdPerson() {
        return this.mc.z.A;
    }

    public void setThirdPerson(boolean i) {
        this.mc.z.A = i;
    }

    public boolean getFancyGraphics() {
        return this.mc.z.j;
    }

    public boolean getHudEnabled() {
        return this.mc.v.hudEnabled;
    }

    public void setHudEnabled(boolean b) {
        this.mc.v.hudEnabled = b;
    }
}
