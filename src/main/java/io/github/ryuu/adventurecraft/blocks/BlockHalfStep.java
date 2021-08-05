package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.util.maths.Box;

public class BlockHalfStep extends BlockSolid {
    protected BlockHalfStep(int i, int j) {
        super(i, j);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        if (j % 2 == 0) {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        } else {
            setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        j = 2 * j / 2;
        if (i <= 1)
            return this.tex + j + 1;
        return this.tex + j;
    }

    @Override
    public boolean method_1618(TileView iblockaccess, int i, int j, int k, int l) {
        updateBlockBounds(iblockaccess, i, j, k);
        return super.method_1618(iblockaccess, i, j, k, l);
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        updateBlockBounds(world, i, j, k);
        return super.getCollisionShape(world, i, j, k);
    }

    private void updateBlockBounds(TileView iblockaccess, int i, int j, int k) {
        int metadata = iblockaccess.getTileMeta(i, j, k);
        if (metadata % 2 == 0) {
            setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        } else {
            setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }
}
