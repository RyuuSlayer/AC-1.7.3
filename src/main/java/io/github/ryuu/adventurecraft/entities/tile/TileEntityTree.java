package io.github.ryuu.adventurecraft.entities.tile;

import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityTree extends TileEntity {
    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.size = nbttagcompound.getFloat("size");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("size", this.size);
    }

    public float size = 1.0F;
}
