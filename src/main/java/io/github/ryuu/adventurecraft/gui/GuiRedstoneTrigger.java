package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityRedstoneTrigger;
import net.minecraft.client.Minecraft;

public class GuiRedstoneTrigger extends da {
    private final TileEntityRedstoneTrigger trigger;

    private final int blockX;

    private final int blockY;

    private final int blockZ;

    private final fd world;

    public GuiRedstoneTrigger(fd w, int x, int y, int z, TileEntityRedstoneTrigger triggerClicked) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.trigger = triggerClicked;
    }

    public void a() {
    }

    public void b() {
        this.e.add(new ab(0, 4, 40, "Use Current Selection"));
        ab b = new ab(1, 4, 60, "Trigger Target");
        if (this.trigger.resetOnTrigger)
            b.e = "Reset Target";
        this.e.add(b);
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            int blockID = this.world.a(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.redstoneTrigger.bn)
                Blocks.redstoneTrigger.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
        } else if (guibutton.f == 1) {
            this.trigger.resetOnTrigger = !this.trigger.resetOnTrigger;
            if (this.trigger.resetOnTrigger) {
                guibutton.e = "Reset Target";
            } else {
                guibutton.e = "Trigger Target";
            }
        }
        this.world.b(this.blockX, this.blockZ).g();
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, String.format("Min: (%d, %d, %d)", new Object[]{Integer.valueOf(this.trigger.minX), Integer.valueOf(this.trigger.minY), Integer.valueOf(this.trigger.minZ)}), 4, 4, 14737632);
        b(this.g, String.format("Max: (%d, %d, %d)", new Object[]{Integer.valueOf(this.trigger.maxX), Integer.valueOf(this.trigger.maxY), Integer.valueOf(this.trigger.maxZ)}), 4, 24, 14737632);
        super.a(i, j, f);
    }

    public static void showUI(fd w, int x, int y, int z, TileEntityRedstoneTrigger triggerClicked) {
        Minecraft.minecraftInstance.a(new GuiRedstoneTrigger(w, x, y, z, triggerClicked));
    }

    public boolean c() {
        return false;
    }
}
