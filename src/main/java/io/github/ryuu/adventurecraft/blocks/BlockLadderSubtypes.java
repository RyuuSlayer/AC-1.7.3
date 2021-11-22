package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockLadderSubtypes extends LadderTile implements IBlockColor {

    protected BlockLadderSubtypes(int id, int texUVStart) {
        super(id, texUVStart);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + (meta /= 4);
    }

    @Override
    public void onPlaced(Level level, int x, int y, int z, int facing) {
        int meta = level.getTileMeta(x, y, z);
        int side = 0;
        if (side == 0 && BlockLadderSubtypes.isLadderID(level.getTileId(x, y - 1, z))) {
            side = level.getTileMeta(x, y - 1, z) % 4 + 2;
        }
        if (side == 0 && BlockLadderSubtypes.isLadderID(level.getTileId(x, y + 1, z))) {
            side = level.getTileMeta(x, y + 1, z) % 4 + 2;
        }
        if ((side == 0 || facing == 2) && level.isFullOpaque(x, y, z + 1)) {
            side = 2;
        }
        if ((side == 0 || facing == 3) && level.isFullOpaque(x, y, z - 1)) {
            side = 3;
        }
        if ((side == 0 || facing == 4) && level.isFullOpaque(x + 1, y, z)) {
            side = 4;
        }
        if ((side == 0 || facing == 5) && level.isFullOpaque(x - 1, y, z)) {
            side = 5;
        }
        level.setTileMeta(x, y, z, meta += Math.max((int) (side - 2), (int) 0) % 4);
    }

    @Override
    public void method_1609(Level level, int x, int y, int z, int id) {
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 4) % 16);
    }
}
