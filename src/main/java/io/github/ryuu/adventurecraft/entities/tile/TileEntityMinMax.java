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
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityMinMax extends TileEntity {

    public int minX;

    public int minY;

    public int minZ;

    public int maxX;

    public int maxY;

    public int maxZ;

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.minX = tag.getInt("minX");
        this.minY = tag.getInt("minY");
        this.minZ = tag.getInt("minZ");
        this.maxX = tag.getInt("maxX");
        this.maxY = tag.getInt("maxY");
        this.maxZ = tag.getInt("maxZ");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("minX", this.minX);
        tag.put("minY", this.minY);
        tag.put("minZ", this.minZ);
        tag.put("maxX", this.maxX);
        tag.put("maxY", this.maxY);
        tag.put("maxZ", this.maxZ);
    }

    public boolean isSet() {
        return this.minX != 0 || this.minY != 0 || this.minZ != 0 || this.maxX != 0 || this.maxY != 0 || this.maxZ != 0;
    }
}
