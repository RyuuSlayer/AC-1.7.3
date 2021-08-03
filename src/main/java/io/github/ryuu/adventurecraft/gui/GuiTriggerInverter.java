package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerInverter;
import net.minecraft.client.Minecraft;

public class GuiTriggerInverter extends da {
    private TileEntityTriggerInverter trigger;

    private int blockX;

    private int blockY;

    private int blockZ;

    private fd world;

    public GuiTriggerInverter(fd w, int x, int y, int z, TileEntityTriggerInverter triggerClicked) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.trigger = triggerClicked;
    }

    public void a() {}

    public void b() {
        this.e.add(new ab(0, 4, 40, "Use Current Selection"));
    }

    protected void a(ke guibutton) {
        int blockID = this.world.a(this.blockX, this.blockY, this.blockZ);
        if (blockID == Blocks.triggerInverter.bn)
            Blocks.triggerInverter.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
        this.world.b(this.blockX, this.blockZ).g();
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, String.format("Min: (%d, %d, %d)", new Object[] { Integer.valueOf(this.trigger.minX), Integer.valueOf(this.trigger.minY), Integer.valueOf(this.trigger.minZ) }), 4, 4, 14737632);
        b(this.g, String.format("Max: (%d, %d, %d)", new Object[] { Integer.valueOf(this.trigger.maxX), Integer.valueOf(this.trigger.maxY), Integer.valueOf(this.trigger.maxZ) }), 4, 24, 14737632);
        super.a(i, j, f);
    }

    public static void showUI(fd w, int x, int y, int z, TileEntityTriggerInverter triggerClicked) {
        Minecraft.minecraftInstance.a(new GuiTriggerInverter(w, x, y, z, triggerClicked));
    }

    public boolean c() {
        return false;
    }
}

