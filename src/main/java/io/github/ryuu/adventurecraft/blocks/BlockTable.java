package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.tile.Tile;

public class BlockTable extends BlockSolid {

    protected BlockTable(int i, int j) {
        super(i, j, Tile.WOOD_SOUNDS);
        this.setBoundingBox(0.0f, 0.875f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.hardness(5.0f);
        ((ExTile) this).setSubTypes(16);
        ((ExTile) this).setTextureNum(3);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        if (side <= 1) {
            return this.tex + meta;
        }
        return this.tex + 16 + meta;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public int method_1621() {
        return 33;
    }
}
