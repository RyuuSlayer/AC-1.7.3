package io.github.ryuu.adventurecraft.entities.tile;

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
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.minX = nbttagcompound.getInt("minX");
        this.minY = nbttagcompound.getInt("minY");
        this.minZ = nbttagcompound.getInt("minZ");
        this.maxX = nbttagcompound.getInt("maxX");
        this.maxY = nbttagcompound.getInt("maxY");
        this.maxZ = nbttagcompound.getInt("maxZ");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("minX", this.minX);
        nbttagcompound.put("minY", this.minY);
        nbttagcompound.put("minZ", this.minZ);
        nbttagcompound.put("maxX", this.maxX);
        nbttagcompound.put("maxY", this.maxY);
        nbttagcompound.put("maxZ", this.maxZ);
    }

    public boolean isSet() {
        return (this.minX != 0 || this.minY != 0 || this.minZ != 0 || this.maxX != 0 || this.maxY != 0 || this.maxZ != 0);
    }
}
