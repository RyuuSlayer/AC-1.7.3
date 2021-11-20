package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTriggerPushable extends TileEntityMinMax {

    public boolean activated;

    public boolean resetOnTrigger;

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.resetOnTrigger = tag.getBoolean("ResetOnTrigger");
        this.activated = tag.getBoolean("activated");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("ResetOnTrigger", this.resetOnTrigger);
        tag.put("activated", this.activated);
    }
}
