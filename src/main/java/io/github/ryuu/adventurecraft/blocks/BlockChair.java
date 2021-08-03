package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;

public class BlockChair extends BlockSolid {
    protected BlockChair(int i, int j) {
        super(i, j);
        a(0.125F, 0.5F, 0.125F, 0.875F, 0.625F, 0.875F);
    }

    public int a(int i, int j) {
        j /= 4;
        if (i <= 1)
            return this.bm + j;
        return this.bm + 16 + j;
    }

    public boolean c() {
        return false;
    }

    public int b() {
        return 34;
    }

    public void a(Level world, int i, int j, int k, ls entityliving) {
        int meta = world.e(i, j, k);
        int l = in.b((entityliving.aS * 4.0F / 360.0F) + 0.5D) & 0x3;
        world.d(i, j, k, meta + (l + 1) % 4);
    }
}
