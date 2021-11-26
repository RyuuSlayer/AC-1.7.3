package io.github.ryuu.adventurecraft.util;

import java.util.ArrayList;
import java.util.List;

public final class CoordBlock {

    private static final List<CoordBlock> blockCoords = new ArrayList<>();
    public static int numBlockCoordsInUse = 0;

    public int x;
    public int y;
    public int z;

    public CoordBlock(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public static CoordBlock getFromPool(int i, int j, int k) {
        if (numBlockCoordsInUse >= blockCoords.size()) {
            blockCoords.add(new CoordBlock(i, j, k));
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
