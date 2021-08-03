package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTeleport extends TileEntity {
    public int x;

    public int y;

    public int z;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.x = nbttagcompound.e("teleportX");
        this.y = nbttagcompound.e("teleportY");
        this.z = nbttagcompound.e("teleportZ");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("teleportX", this.x);
        nbttagcompound.a("teleportY", this.y);
        nbttagcompound.a("teleportZ", this.z);
    }
}
