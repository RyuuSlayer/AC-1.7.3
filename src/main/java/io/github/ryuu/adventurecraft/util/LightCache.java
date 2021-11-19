package io.github.ryuu.adventurecraft.util;

class LightCache {
    static final int cacheSize = 16384;

    static final int cacheSizeThird = 5461;

    static final int cacheSizeTwoThird = 10922;
    static LightCache cache = new LightCache();
    CoordBlock[] coords = new CoordBlock[16384];
    float[] lightValues = new float[16384];

    public void clear() {
        for (int i = 0; i < 16384; i++)
            this.coords[i] = null;
    }

    private int calcHash(int x, int y, int z) {
        int m = 1540483477;
        int r = 24;
        int h = 1234567890;
        int k = x;
        k *= m;
        k ^= k >>> r;
        k *= m;
        h *= m;
        h ^= k;
        k = y;
        k *= m;
        k ^= k >>> r;
        k *= m;
        h *= m;
        h ^= k;
        k = z;
        k *= m;
        k ^= k >>> r;
        k *= m;
        h *= m;
        h ^= k;
        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;
        return h;
    }

    private int findEntry(int x, int y, int z) {
        int i = Math.abs(calcHash(x, y, z)) % 16384;
        int h = i;
        int j = 0;
        while (this.coords[i] != null && !this.coords[i].isEqual(x, y, z)) {
            i = (i + 1) % 16384;
            if (j++ > 16384)
                throw new RuntimeException("Light cache full");
        }
        return i;
    }

    public float getLightValue(int x, int y, int z) {
        int i = findEntry(x, y, z);
        if (this.coords[i] == null)
            return -1.0F;
        return this.lightValues[i];
    }

    public void setLightValue(int x, int y, int z, float l) {
        int i = findEntry(x, y, z);
        this.coords[i] = CoordBlock.getFromPool(x, y, z);
        this.lightValues[i] = l;
    }
}
