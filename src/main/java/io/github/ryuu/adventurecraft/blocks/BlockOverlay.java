package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockOverlay extends Tile implements IBlockColor {
    protected BlockOverlay(int i, int j) {
        super(i, j, Material.PLANT);
        a(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
    }

    public int a(int i, int j) {
        return this.bm + j;
    }

    public Box e(Level world, int i, int j, int k) {
        updateBounds(world, i, j, k);
        return null;
    }

    public Box f(Level world, int i, int j, int k) {
        updateBounds(world, i, j, k);
        return super.f(world, i, j, k);
    }

    public void updateBounds(xp world, int i, int j, int k) {
        if (world.g(i, j - 1, k)) {
            a(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
        } else if (world.g(i, j + 1, k)) {
            a(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else if (world.g(i - 1, j, k)) {
            a(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
        } else if (world.g(i + 1, j, k)) {
            a(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else if (world.g(i, j, k - 1)) {
            a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
        } else if (world.g(i, j, k + 1)) {
            a(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
        } else {
            a(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
        }
    }

    public boolean c() {
        return false;
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 37;
    }

    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.e(i, j, k);
        world.d(i, j, k, (metadata + 1) % subTypes[this.bn]);
    }
}
