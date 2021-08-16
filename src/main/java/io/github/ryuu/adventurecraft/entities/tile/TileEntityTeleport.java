package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTeleport extends TileEntity {
    public int x;

    public int y;

    public int z;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.x = nbttagcompound.getInt("teleportX");
        this.y = nbttagcompound.getInt("teleportY");
        this.z = nbttagcompound.getInt("teleportZ");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("teleportX", this.x);
        nbttagcompound.put("teleportY", this.y);
        nbttagcompound.put("teleportZ", this.z);
    }
}
