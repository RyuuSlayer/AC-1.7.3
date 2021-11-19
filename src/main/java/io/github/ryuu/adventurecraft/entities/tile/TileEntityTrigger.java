package io.github.ryuu.adventurecraft.entities.tile;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTrigger extends TileEntityMinMax {

    public int activated = 0;

    public boolean visited;

    public boolean resetOnTrigger;

    @Override
    public void tick() {
        if (this.activated > 0 && !Minecraft.minecraftInstance.cameraActive) {
            --this.activated;
            if (this.activated == 0 && this.level.getTileId(this.x, this.y, this.z) == Blocks.triggerBlock.id) {
                Blocks.triggerBlock.deactivateTrigger(this.level, this.x, this.y, this.z);
            }
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.resetOnTrigger = tag.getBoolean("ResetOnTrigger");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("ResetOnTrigger", this.resetOnTrigger);
    }
}
