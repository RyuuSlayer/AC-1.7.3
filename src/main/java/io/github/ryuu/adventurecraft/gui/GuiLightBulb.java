package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.blocks.Blocks;

public class GuiLightBulb extends Screen {

    private int blockX;

    private int blockY;

    private int blockZ;

    private Level world;

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
        this.lightSlider = new GuiSlider2(4, 4, 4, 10, String.format((String) "Light Value: %d", (Object[]) new Object[] { this.lightValue }), (float) this.lightValue / 15.0f);
        this.buttons.add((Object) this.lightSlider);
    }

    @Override
    protected void buttonClicked(Button button) {
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.lightValue = (int) (this.lightSlider.sliderValue * 15.0f + 0.5f);
        this.lightSlider.text = String.format((String) "Light Value: %d", (Object[]) new Object[] { this.lightValue });
        if (this.lightValue != this.world.getTileMeta(this.blockX, this.blockY, this.blockZ)) {
            this.world.method_201(this.blockX, this.blockY, this.blockZ, 0, 0);
            this.world.method_201(this.blockX, this.blockY, this.blockZ, Blocks.lightBulb.id, this.lightValue);
        }
        super.render(mouseX, mouseY, delta);
        this.world.getChunk(this.blockX, this.blockZ).method_885();
    }

    public static void showUI(Level w, int x, int y, int z) {
        Minecraft.minecraftInstance.openScreen(new GuiLightBulb(w, x, y, z));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
