package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.util.io.CompoundTag;

public class TileEntityTriggerPushable extends TileEntityMinMax {
    public boolean activated;

    public boolean resetOnTrigger;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.m("ResetOnTrigger");
        this.activated = nbttagcompound.m("activated");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.a("activated", this.activated);
    }
}
