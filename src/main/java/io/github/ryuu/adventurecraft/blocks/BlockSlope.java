package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.tile.Tile;

public class BlockSlope extends BlockStairMulti {
    protected BlockSlope(int i, Tile block, int textureID) {
        super(i, block, textureID);
    }

    public int b() {
        return 38;
    }
}
