package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;

public class BlockHalfStep extends BlockSolid {
    protected BlockHalfStep(int i, int j) {
        super(i, j);
    }

    public int a(int i, int j) {
        if (j % 2 == 0) {
            a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        } else {
            a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        j = 2 * j / 2;
        if (i <= 1)
            return this.bm + j + 1;
        return this.bm + j;
    }

    public boolean b(xp iblockaccess, int i, int j, int k, int l) {
        updateBlockBounds(iblockaccess, i, j, k);
        return super.b(iblockaccess, i, j, k, l);
    }

    public Box e(Level world, int i, int j, int k) {
        updateBlockBounds(world, i, j, k);
        return super.e(world, i, j, k);
    }

    private void updateBlockBounds(xp iblockaccess, int i, int j, int k) {
        int metadata = iblockaccess.e(i, j, k);
        if (metadata % 2 == 0) {
            a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        } else {
            a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public boolean c() {
        return false;
    }
}
