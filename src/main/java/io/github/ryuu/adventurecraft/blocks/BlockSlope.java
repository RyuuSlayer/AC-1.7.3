package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockSlope extends BlockStairMulti {

    protected BlockSlope(int i, Tile block, int textureID) {
        super(i, block, textureID);
    }

    @Override
    public int method_1621() {
        return 38;
    }
}
