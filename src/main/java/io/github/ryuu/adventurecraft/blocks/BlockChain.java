package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockChain extends BlockRope {

    protected BlockChain(int i, int j) {
        super(i, j);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + side % 2 + meta / 3 * 2;
    }
}
