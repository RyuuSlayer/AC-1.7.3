package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;

public class TileEntityTree extends TileEntity {
    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.size = nbttagcompound.g("size");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("size", this.size);
    }

    public float size = 1.0F;
}
