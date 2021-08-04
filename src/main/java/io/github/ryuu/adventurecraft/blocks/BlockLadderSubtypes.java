package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.tile.LadderTile;

public class BlockLadderSubtypes extends LadderTile implements IBlockColor {
    protected BlockLadderSubtypes(int i, int j) {
        super(i, j);
    }

    public int a(int i, int j) {
        j /= 4;
        return this.bm + j;
    }

    public void e(Level world, int i, int j, int k, int l) {
        int meta = world.e(i, j, k);
        int side = 0;
        if (side == 0 && isLadderID(world.a(i, j - 1, k)))
            side = world.e(i, j - 1, k) % 4 + 2;
        if (side == 0 && isLadderID(world.a(i, j + 1, k)))
            side = world.e(i, j + 1, k) % 4 + 2;
        if ((side == 0 || l == 2) && world.g(i, j, k + 1))
            side = 2;
        if ((side == 0 || l == 3) && world.g(i, j, k - 1))
            side = 3;
        if ((side == 0 || l == 4) && world.g(i + 1, j, k))
            side = 4;
        if ((side == 0 || l == 5) && world.g(i - 1, j, k))
            side = 5;
        meta += Math.max(side - 2, 0) % 4;
        world.d(i, j, k, meta);
    }

    public void b(Level world, int i, int j, int k, int l) {
    }

    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.e(i, j, k);
        world.d(i, j, k, (metadata + 4) % 16);
    }
}
