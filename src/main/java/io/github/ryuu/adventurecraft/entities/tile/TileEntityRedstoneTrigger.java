package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TileEntityRedstoneTrigger extends TileEntityMinMax {

    public boolean isActivated = false;

    public boolean visited;

    public boolean resetOnTrigger;

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.resetOnTrigger = tag.getBoolean("ResetOnTrigger");
        this.isActivated = tag.getBoolean("IsActivated");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("ResetOnTrigger", this.resetOnTrigger);
        tag.put("IsActivated", this.isActivated);
    }
}
