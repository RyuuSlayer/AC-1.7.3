package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.util.io.CompoundTag;

public class TileEntityTriggerPushable extends TileEntityMinMax {
    public boolean activated;

    public boolean resetOnTrigger;

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.getBoolean("ResetOnTrigger");
        this.activated = nbttagcompound.getBoolean("activated");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.put("activated", this.activated);
    }
}
