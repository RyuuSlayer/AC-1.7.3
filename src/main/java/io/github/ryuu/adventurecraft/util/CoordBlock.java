package io.github.ryuu.adventurecraft.util;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

final class CoordBlock {

    public int x;

    public int y;

    public int z;

    private static List<net.minecraft.src.CoordBlock> blockCoords = new ArrayList();

    public static int numBlockCoordsInUse = 0;

    public CoordBlock(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public static CoordBlock getFromPool(int i, int j, int k) {
        if (numBlockCoordsInUse >= blockCoords.size()) {
            blockCoords.add((Object) new CoordBlock(i, j, k));
        }
        return ((CoordBlock) blockCoords.get(numBlockCoordsInUse++)).set(i, j, k);
    }

    public static void resetPool() {
        numBlockCoordsInUse = 0;
    }

    public static void releaseLastOne() {
        --numBlockCoordsInUse;
    }

    public CoordBlock set(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
        return this;
    }

    public boolean isEqual(int i, int j, int k) {
        return this.x == i && this.y == j && this.z == k;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CoordBlock) {
            CoordBlock otherCoord = (CoordBlock) obj;
            return this.x == otherCoord.x && this.y == otherCoord.y && this.z == otherCoord.z;
        }
        return false;
    }

    public int hashCode() {
        return this.x << 16 ^ this.z ^ this.y << 24;
    }
}
