package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.client.Minecraft;

public class GuiLightBulb extends da {
    private final int blockX;

    private final int blockY;

    private final int blockZ;

    private final fd world;

    GuiSlider2 lightSlider;

    int lightValue;

    public GuiLightBulb(fd w, int x, int y, int z) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.lightValue = w.e(x, y, z);
    }

    public void a() {
    }

    public void b() {
        this.lightSlider = new GuiSlider2(4, 4, 4, 10, String.format("Light Value: %d", Integer.valueOf(this.lightValue)), this.lightValue / 15.0F);
        this.e.add(this.lightSlider);
    }

    protected void a(ke guibutton) {
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        this.lightValue = (int) (this.lightSlider.sliderValue * 15.0F + 0.5F);
        this.lightSlider.e = String.format("Light Value: %d", new Object[]{Integer.valueOf(this.lightValue)});
        if (this.lightValue != this.world.e(this.blockX, this.blockY, this.blockZ)) {
            this.world.b(this.blockX, this.blockY, this.blockZ, 0, 0);
            this.world.b(this.blockX, this.blockY, this.blockZ, Blocks.lightBulb.bn, this.lightValue);
        }
        super.a(i, j, f);
        this.world.b(this.blockX, this.blockZ).g();
    }

    public static void showUI(fd w, int x, int y, int z) {
        Minecraft.minecraftInstance.a(new GuiLightBulb(w, x, y, z));
    }

    public boolean c() {
        return false;
    }
}
