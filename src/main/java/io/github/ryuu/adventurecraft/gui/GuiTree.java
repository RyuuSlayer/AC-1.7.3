package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;

public class GuiTree extends da {
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

    public void a() {
    }

    public void b() {
        this.treeSize = new GuiSlider2(4, 4, 4, 10, String.format("Tree Size: %.2f", Float.valueOf(this.tree.size)), (this.tree.size - 0.5F) / 3.5F);
        this.e.add(this.treeSize);
        this.prevValue = this.treeSize.sliderValue;
    }

    protected void a(ke guibutton) {
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        if (this.prevValue != this.treeSize.sliderValue) {
            this.tree.size = this.treeSize.sliderValue * 3.5F + 0.5F;
            this.treeSize.e = String.format("Tree Size: %.2f", new Object[]{Float.valueOf(this.tree.size)});
            this.world.k(this.blockX, this.blockY, this.blockZ);
            this.world.b(this.blockX, this.blockZ).g();
        }
        super.a(i, j, f);
    }

    public static void showUI(Level w, int x, int y, int z, TileEntityTree t) {
        Minecraft.minecraftInstance.a(new GuiTree(w, x, y, z, t));
    }

    public boolean c() {
        return false;
    }
}
