package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTriggerMemory extends TileEntityMinMax {

    public boolean isActivated;

    public boolean activateOnDetrigger;

    public boolean resetOnDeath;

    public void set(int x1, int y1, int z1, int x2, int y2, int z2) {
        if (this.isSet() && this.isActivated) {
            Blocks.triggerMemory.triggerDeactivate(this.level, this.x, this.y, this.z);
        }
        this.minX = x1;
        this.minY = y1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxY = y2;
        this.maxZ = z2;
        if (this.isActivated) {
            Blocks.triggerMemory.triggerActivate(this.level, this.x, this.y, this.z);
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.isActivated = tag.getBoolean("IsActivated");
        this.activateOnDetrigger = tag.getBoolean("ActivateOnDetrigger");
        this.resetOnDeath = tag.getBoolean("ResetOnDeath");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("IsActivated", this.isActivated);
        tag.put("ActivateOnDetrigger", this.activateOnDetrigger);
        tag.put("ResetOnDeath", this.resetOnDeath);
    }
}
