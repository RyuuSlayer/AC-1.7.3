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

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.minX = nbttagcompound.e("minX");
        this.minY = nbttagcompound.e("minY");
        this.minZ = nbttagcompound.e("minZ");
        this.maxX = nbttagcompound.e("maxX");
        this.maxY = nbttagcompound.e("maxY");
        this.maxZ = nbttagcompound.e("maxZ");
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("minX", this.minX);
        nbttagcompound.a("minY", this.minY);
        nbttagcompound.a("minZ", this.minZ);
        nbttagcompound.a("maxX", this.maxX);
        nbttagcompound.a("maxY", this.maxY);
        nbttagcompound.a("maxZ", this.maxZ);
    }

    public boolean isSet() {
        return (this.minX != 0 || this.minY != 0 || this.minZ != 0 || this.maxX != 0 || this.maxY != 0 || this.maxZ != 0);
    }
}
