package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockPlant extends Tile implements IBlockColor {
    protected BlockPlant(int i, int j) {
        super(i, j, Material.PLANT);
        float f = 0.2F;
        a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
    }

    public int a(int i, int j) {
        return this.bm + j;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 1;
    }

    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.e(i, j, k);
        world.d(i, j, k, (metadata + 1) % subTypes[this.bn]);
    }
}
