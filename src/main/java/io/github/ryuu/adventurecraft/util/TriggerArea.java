package io.github.ryuu.adventurecraft.util;

import net.minecraft.util.io.CompoundTag;

public class TriggerArea {
    int minX;

    int minY;

    int minZ;

    int maxX;

    int maxY;

    int maxZ;

    public TriggerArea(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
        this.minX = xMin;
        this.minY = yMin;
        this.minZ = zMin;
        this.maxX = xMax;
        this.maxY = yMax;
        this.maxZ = zMax;
    }

    boolean isPointInside(int x, int y, int z) {
        if (x < this.minX || x > this.maxX)
            return false;
        if (y < this.minY || y > this.maxY)
            return false;
        return (this.minZ <= z && z <= this.maxZ);
    }

    CompoundTag getTagCompound() {
        CompoundTag t = new CompoundTag();
        t.a("minX", this.minX);
        t.a("minY", this.minY);
        t.a("minZ", this.minZ);
        t.a("maxX", this.maxX);
        t.a("maxY", this.maxY);
        t.a("maxZ", this.maxZ);
        return t;
    }

    static TriggerArea getFromTagCompound(CompoundTag tag) {
        int minX = tag.e("minX");
        int minY = tag.e("minY");
        int minZ = tag.e("minZ");
        int maxX = tag.e("maxX");
        int maxY = tag.e("maxY");
        int maxZ = tag.e("maxZ");
        return new TriggerArea(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
