package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;

public class TileEntityTeleport extends TileEntity {
    public int x;

    public int y;

    public int z;

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.x = nbttagcompound.e("teleportX");
        this.y = nbttagcompound.e("teleportY");
        this.z = nbttagcompound.e("teleportZ");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("teleportX", this.x);
        nbttagcompound.a("teleportY", this.y);
        nbttagcompound.a("teleportZ", this.z);
    }
}
