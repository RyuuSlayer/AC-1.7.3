package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;

public class BlockRope extends BlockPlant {
    protected BlockRope(int i, int j) {
        super(i, j);
        float f = 0.2F;
        a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
    }

    public Box f(Level world, int i, int j, int k) {
        updateBounds(world, i, j, k);
        return super.f(world, i, j, k);
    }

    public Box e(Level world, int i, int j, int k) {
        int m = world.e(i, j, k) % 3;
        if (m == 0)
            return null;
        updateBounds(world, i, j, k);
        return eq.b(i + this.bs, j + this.bt, k + this.bu, i + this.bv, j + this.bw, k + this.bx);
    }

    private void updateBounds(Level world, int i, int j, int k) {
        int m = world.e(i, j, k) % 3;
        float f = 0.2F;
        if (m == 0) {
            a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        } else if (m == 1) {
            a(0.0F, 0.5F - f, 0.5F - f, 1.0F, 0.5F + f, 0.5F + f);
        } else {
            a(0.5F - f, 0.5F - f, 0.0F, 0.5F + f, 0.5F + f, 1.0F);
        }
    }

    public int b() {
        return 35;
    }

    public int a(int i, int j) {
        return this.bm + j / 3;
    }
}
