package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTrigger extends TileEntityMinMax {
    public int activated = 0;

    public boolean visited;

    public boolean resetOnTrigger;

    @Override
    public void tick() {
        if (this.activated > 0 && !Minecraft.minecraftInstance.cameraActive) {
            this.activated--;
            if (this.activated == 0 && this.level.getTileId(this.x, this.y, this.z) == Blocks.triggerBlock.id)
                Blocks.triggerBlock.deactivateTrigger(this.level, this.x, this.y, this.z);
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.resetOnTrigger = nbttagcompound.getBoolean("ResetOnTrigger");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("ResetOnTrigger", this.resetOnTrigger);
    }
}
