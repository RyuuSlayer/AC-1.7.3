package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class BlockSolid extends Tile implements IBlockColor {
    public BlockSolid(int i, int j) {
        super(i, j, ln.e);
    }

    public int a(int i, int j) {
        return this.bm + j;
    }

    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.e(i, j, k);
        world.d(i, j, k, (metadata + 1) % subTypes[this.bn]);
    }
}
