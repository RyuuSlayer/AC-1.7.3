package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.util.io.CompoundTag;

public class TileEntityRedstoneTrigger extends TileEntityMinMax {
    public boolean isActivated = false;

    public boolean visited;

    public boolean resetOnTrigger;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.m("ResetOnTrigger");
        this.isActivated = nbttagcompound.m("IsActivated");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.a("IsActivated", this.isActivated);
    }
}
