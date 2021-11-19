package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.tile.LadderTile;

public class BlockLadderSubtypes extends LadderTile implements IBlockColor {
    protected BlockLadderSubtypes(int i, int j) {
        super(i, j);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        j /= 4;
        return this.tex + j;
    }

    @Override
    public void onPlaced(Level world, int i, int j, int k, int l) {
        int meta = world.getTileMeta(i, j, k);
        int side = 0;
        if (side == 0 && isLadderID(world.getTileId(i, j - 1, k)))
            side = world.getTileMeta(i, j - 1, k) % 4 + 2;
        if (side == 0 && isLadderID(world.getTileId(i, j + 1, k)))
            side = world.getTileMeta(i, j + 1, k) % 4 + 2;
        if ((side == 0 || l == 2) && world.isFullOpaque(i, j, k + 1))
            side = 2;
        if ((side == 0 || l == 3) && world.isFullOpaque(i, j, k - 1))
            side = 3;
        if ((side == 0 || l == 4) && world.isFullOpaque(i + 1, j, k))
            side = 4;
        if ((side == 0 || l == 5) && world.isFullOpaque(i - 1, j, k))
            side = 5;
        meta += Math.max(side - 2, 0) % 4;
        world.setTileMeta(i, j, k, meta);
    }

    @Override
    public void method_1609(Level world, int i, int j, int k, int l) {
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 4) % 16);
    }
}
