package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.util.io.CompoundTag;

public class TileEntityTriggerPushable extends TileEntityMinMax {
    public boolean activated;

    public boolean resetOnTrigger;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.getBoolean("ResetOnTrigger");
        this.activated = nbttagcompound.getBoolean("activated");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.put("activated", this.activated);
    }
}
