package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.util.io.CompoundTag;

public class TileEntityRedstoneTrigger extends TileEntityMinMax {
    public boolean isActivated = false;

    public boolean visited;

    public boolean resetOnTrigger;

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.getBoolean("ResetOnTrigger");
        this.isActivated = nbttagcompound.getBoolean("IsActivated");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("ResetOnTrigger", this.resetOnTrigger);
        nbttagcompound.put("IsActivated", this.isActivated);
    }
}
