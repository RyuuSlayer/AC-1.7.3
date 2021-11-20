package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTeleport extends MixinTileEntity {

    public int x;

    public int y;

    public int z;

    @Override
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        this.x = tag.getInt("teleportX");
        this.y = tag.getInt("teleportY");
        this.z = tag.getInt("teleportZ");
    }

    @Override
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("teleportX", this.x);
        tag.put("teleportY", this.y);
        tag.put("teleportZ", this.z);
    }
}
