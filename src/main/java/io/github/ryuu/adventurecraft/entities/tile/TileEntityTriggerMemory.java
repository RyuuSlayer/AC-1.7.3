package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTriggerMemory extends TileEntityMinMax {
    public boolean isActivated;

    public boolean activateOnDetrigger;

    public boolean resetOnDeath;

    public void set(int x1, int y1, int z1, int x2, int y2, int z2) {
        if (isSet())
            if (this.isActivated)
                Blocks.triggerMemory.triggerDeactivate(this.d, this.e, this.f, this.g);
        this.minX = x1;
        this.minY = y1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxY = y2;
        this.maxZ = z2;
        if (this.isActivated)
            Blocks.triggerMemory.triggerActivate(this.d, this.e, this.f, this.g);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.isActivated = nbttagcompound.getBoolean("IsActivated");
        this.activateOnDetrigger = nbttagcompound.getBoolean("ActivateOnDetrigger");
        this.resetOnDeath = nbttagcompound.getBoolean("ResetOnDeath");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("IsActivated", this.isActivated);
        nbttagcompound.put("ActivateOnDetrigger", this.activateOnDetrigger);
        nbttagcompound.put("ResetOnDeath", this.resetOnDeath);
    }
}
