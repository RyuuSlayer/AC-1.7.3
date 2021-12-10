package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.TileView;

public interface AcLightTile {

    int getBlockLightValue(TileView iblockaccess, int i, int j, int k); // default: return 0
}
