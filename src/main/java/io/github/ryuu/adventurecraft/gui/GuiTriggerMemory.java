package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerMemory;
import net.minecraft.client.Minecraft;

public class GuiTriggerMemory extends da {
    private final TileEntityTriggerMemory trigger;

    private final int blockX;

    private final int blockY;

    private final int blockZ;

    private final fd world;

    public GuiTriggerMemory(fd w, int x, int y, int z, TileEntityTriggerMemory triggerClicked) {
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
        ab b = new ab(1, 4, 60, "Activate on Trigger");
        if (this.trigger.activateOnDetrigger)
            b.e = "Activate on Detrigger";
        this.e.add(b);
        b = new ab(2, 4, 80, "Reset on Death");
        if (!this.trigger.resetOnDeath)
            b.e = "Don't Reset on Death";
        this.e.add(b);
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            int blockID = this.world.a(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.triggerMemory.bn)
                Blocks.triggerMemory.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
        } else if (guibutton.f == 1) {
            this.trigger.activateOnDetrigger = !this.trigger.activateOnDetrigger;
            if (this.trigger.activateOnDetrigger) {
                guibutton.e = "Activate on Detrigger";
            } else {
                guibutton.e = "Activate on Trigger";
            }
        } else if (guibutton.f == 2) {
            this.trigger.resetOnDeath = !this.trigger.resetOnDeath;
            if (this.trigger.resetOnDeath) {
                guibutton.e = "Reset on Death";
            } else {
                guibutton.e = "Don't Reset on Death";
            }
        }
        this.world.b(this.blockX, this.blockZ).g();
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, String.format("Min: (%d, %d, %d)", new Object[]{Integer.valueOf(this.trigger.minX), Integer.valueOf(this.trigger.minY), Integer.valueOf(this.trigger.minZ)}), 4, 4, 14737632);
        b(this.g, String.format("Max: (%d, %d, %d)", new Object[]{Integer.valueOf(this.trigger.maxX), Integer.valueOf(this.trigger.maxY), Integer.valueOf(this.trigger.maxZ)}), 4, 24, 14737632);
        if (this.trigger.isActivated) {
            b(this.g, "Memory Set", 4, 104, 14737632);
        } else {
            b(this.g, "Memory Unset", 4, 104, 14737632);
        }
        super.a(i, j, f);
    }

    public static void showUI(fd w, int x, int y, int z, TileEntityTriggerMemory triggerClicked) {
        Minecraft.minecraftInstance.a(new GuiTriggerMemory(w, x, y, z, triggerClicked));
    }

    public boolean c() {
        return false;
    }
}
