package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;

public class BlockRope extends BlockPlant {
    protected BlockRope(int i, int j) {
        super(i, j);
        float f = 0.2F;
        setBoundingBox(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
    }

    @Override
    public Box getOutlineShape(Level world, int i, int j, int k) {
        updateBounds(world, i, j, k);
        return super.getOutlineShape(world, i, j, k);
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        int m = world.getTileMeta(i, j, k) % 3;
        if (m == 0)
            return null;
        updateBounds(world, i, j, k);
        return Box.getOrCreate(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ);
    }

    private void updateBounds(Level world, int i, int j, int k) {
        int m = world.getTileMeta(i, j, k) % 3;
        float f = 0.2F;
        if (m == 0) {
            setBoundingBox(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        } else if (m == 1) {
            setBoundingBox(0.0F, 0.5F - f, 0.5F - f, 1.0F, 0.5F + f, 0.5F + f);
        } else {
            setBoundingBox(0.5F - f, 0.5F - f, 0.0F, 0.5F + f, 0.5F + f, 1.0F);
        }
    }

    @Override
    public int method_1621() {
        return 35;
    }

    @Override
    public int getTextureForSide(int i, int j) {
        return this.tex + j / 3;
    }
}
