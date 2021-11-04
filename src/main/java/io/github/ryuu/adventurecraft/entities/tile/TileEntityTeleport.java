package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTeleport extends TileEntity {
    public int x;

    public int y;

    public int z;

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.x = nbttagcompound.getInt("teleportX");
        this.y = nbttagcompound.getInt("teleportY");
        this.z = nbttagcompound.getInt("teleportZ");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("teleportX", this.x);
        nbttagcompound.put("teleportY", this.y);
        nbttagcompound.put("teleportZ", this.z);
    }
}
