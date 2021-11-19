package io.github.ryuu.adventurecraft.entities.tile;

public class TileEntityRedstoneTrigger extends TileEntityMinMax {

    public boolean isActivated = false;

    public boolean visited;

    public boolean resetOnTrigger;

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.resetOnTrigger = tag.getBoolean("ResetOnTrigger");
        this.isActivated = tag.getBoolean("IsActivated");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("ResetOnTrigger", this.resetOnTrigger);
        tag.put("IsActivated", this.isActivated);
    }
}
