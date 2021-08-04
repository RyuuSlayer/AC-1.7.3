package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;

public class GuiLightBulb extends Screen {
    private final int blockX;

    private final int blockY;

    private final int blockZ;

    private final Level world;

    GuiSlider2 lightSlider;

    int lightValue;

    public GuiLightBulb(Level w, int x, int y, int z) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.lightValue = w.getTileMeta(x, y, z);
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.lightSlider = new GuiSlider2(4, 4, 4, 10, String.format("Light Value: %d", this.lightValue), this.lightValue / 15.0F);
        this.buttons.add(this.lightSlider);
    }

    @Override
    protected void buttonClicked(Button guibutton) {
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        this.lightValue = (int) (this.lightSlider.sliderValue * 15.0F + 0.5F);
        this.lightSlider.text = String.format("Light Value: %d", this.lightValue);
        if (this.lightValue != this.world.getTileMeta(this.blockX, this.blockY, this.blockZ)) {
            this.world.method_201(this.blockX, this.blockY, this.blockZ, 0, 0);
            this.world.method_201(this.blockX, this.blockY, this.blockZ, Blocks.lightBulb.id, this.lightValue);
        }
        super.render(i, j, f);
        this.world.getChunk(this.blockX, this.blockZ).method_885();
    }

    public static void showUI(Level w, int x, int y, int z) {
        Minecraft.minecraftInstance.a(new GuiLightBulb(w, x, y, z));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
