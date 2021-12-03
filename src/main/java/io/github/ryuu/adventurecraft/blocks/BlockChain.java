package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.tile.Tile;

public class BlockChain extends BlockRope {

    protected BlockChain(int i, int j) {
        super(i, j);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setSubTypes(9);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + side % 2 + meta / 3 * 2;
    }
}
