package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.tile.Tile;

public class BlockSlope extends BlockStairMulti {
    protected BlockSlope(int i, Tile block, int textureID) {
        super(i, block, textureID);
    }

    @Override
    public int method_1621() {
        return 38;
    }
}
