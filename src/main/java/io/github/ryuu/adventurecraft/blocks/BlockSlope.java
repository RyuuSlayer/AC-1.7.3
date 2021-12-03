package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.tile.Tile;

public class BlockSlope extends BlockStairMulti {

    protected BlockSlope(int i, Tile block, int textureID) {
        super(i, block, textureID);
        ((ExTile) this).setSubTypes(4);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    public int method_1621() {
        return 38;
    }
}
