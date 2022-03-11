package io.github.ryuu.adventurecraft.extensions.level.chunk;

import net.minecraft.tile.entity.TileEntity;

public interface ExChunk {

    static int translate128(int bID) {
        if (bID > 127) {
            return -129 + (bID - 127);
        }
        return bID;
    }

    static int translate256(int bID) {
        if (bID < 0) {
            return bID + 256;
        }
        return bID;
    }

    double getTemperatureValue(int x, int z);

    void setTemperatureValue(int x, int z, double temp);

    TileEntity getChunkBlockTileEntityDontCreate(int i, int j, int k);

    boolean setBlockIDWithMetadataTemp(int i, int j, int k, int l, int i1);

    long getLastUpdated();

    void setLastUpdated(long lastUpdated);
}
