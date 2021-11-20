package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;

public class GuiTree extends MixinScreen {

    private int blockX;

    private int blockY;

    private int blockZ;

    private MixinLevel world;

    TileEntityTree tree;

    GuiSlider2 treeSize;

    float prevValue;

    public GuiTree(MixinLevel w, int x, int y, int z, TileEntityTree t) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.tree = t;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.treeSize = new GuiSlider2(4, 4, 4, 10, String.format((String) "Tree Size: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.tree.size) }), (this.tree.size - 0.5f) / 3.5f);
        this.buttons.add((Object) this.treeSize);
        this.prevValue = this.treeSize.sliderValue;
    }

    @Override
    protected void buttonClicked(Button button) {
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        if (this.prevValue != this.treeSize.sliderValue) {
            this.tree.size = this.treeSize.sliderValue * 3.5f + 0.5f;
            this.treeSize.text = String.format((String) "Tree Size: %.2f", (Object[]) new Object[] { Float.valueOf((float) this.tree.size) });
            this.world.method_246(this.blockX, this.blockY, this.blockZ);
            this.world.getChunk(this.blockX, this.blockZ).method_885();
        }
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(MixinLevel w, int x, int y, int z, TileEntityTree t) {
        Minecraft.minecraftInstance.openScreen(new GuiTree(w, x, y, z, t));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
