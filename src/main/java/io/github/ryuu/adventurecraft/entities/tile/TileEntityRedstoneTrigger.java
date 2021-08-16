package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.util.io.CompoundTag;

public class TileEntityRedstoneTrigger extends TileEntityMinMax {
    public boolean isActivated = false;

    public boolean visited;

    public boolean resetOnTrigger;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.getBoolean("ResetOnTrigger");
        this.isActivated = nbttagcompound.getBoolean("IsActivated");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.put("IsActivated", this.isActivated);
    }
}
