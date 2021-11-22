package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TileEntityTeleport extends TileEntity {

    public int x;

    public int y;

    public int z;

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.x = tag.getInt("teleportX");
        this.y = tag.getInt("teleportY");
        this.z = tag.getInt("teleportZ");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("teleportX", this.x);
        tag.put("teleportY", this.y);
        tag.put("teleportZ", this.z);
    }
}
