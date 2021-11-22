package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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
        if (x < this.minX || x > this.maxX) {
            return false;
        }
        if (y < this.minY || y > this.maxY) {
            return false;
        }
        return this.minZ <= z && z <= this.maxZ;
    }

    CompoundTag getTagCompound() {
        CompoundTag t = new CompoundTag();
        t.put("minX", this.minX);
        t.put("minY", this.minY);
        t.put("minZ", this.minZ);
        t.put("maxX", this.maxX);
        t.put("maxY", this.maxY);
        t.put("maxZ", this.maxZ);
        return t;
    }

    static TriggerArea getFromTagCompound(CompoundTag tag) {
        int minX = tag.getInt("minX");
        int minY = tag.getInt("minY");
        int minZ = tag.getInt("minZ");
        int maxX = tag.getInt("maxX");
        int maxY = tag.getInt("maxY");
        int maxZ = tag.getInt("maxZ");
        return new TriggerArea(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
