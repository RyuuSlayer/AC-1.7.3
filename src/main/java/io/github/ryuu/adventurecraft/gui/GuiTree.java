package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;

public class GuiTree extends Screen {
    private final int blockX;

    private final int blockY;

    private final int blockZ;

    private final Level world;

    TileEntityTree tree;

    GuiSlider2 treeSize;

    float prevValue;

    public GuiTree(Level w, int x, int y, int z, TileEntityTree t) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.tree = t;
    }

    public static void showUI(Level w, int x, int y, int z, TileEntityTree t) {
        Minecraft.minecraftInstance.a(new GuiTree(w, x, y, z, t));
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.treeSize = new GuiSlider2(4, 4, 4, 10, String.format("Tree Size: %.2f", this.tree.size), (this.tree.size - 0.5F) / 3.5F);
        this.buttons.add(this.treeSize);
        this.prevValue = this.treeSize.sliderValue;
    }

    @Override
    protected void buttonClicked(Button guibutton) {
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        if (this.prevValue != this.treeSize.sliderValue) {
            this.tree.size = this.treeSize.sliderValue * 3.5F + 0.5F;
            this.treeSize.text = String.format("Tree Size: %.2f", this.tree.size);
            this.world.method_246(this.blockX, this.blockY, this.blockZ);
            this.world.getChunk(this.blockX, this.blockZ).method_885();
        }
        super.render(i, j, f);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
