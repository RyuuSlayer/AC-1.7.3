package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;

public class BlockRope extends BlockPlant {

    protected BlockRope(int i, int j) {
        super(i, j);
        float f = 0.2f;
        this.setBoundingBox(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
    }

    @Override
    public Box getOutlineShape(Level level, int x, int y, int z) {
        this.updateBounds(level, x, y, z);
        return super.getOutlineShape(level, x, y, z);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        int m = level.getTileMeta(x, y, z) % 3;
        if (m == 0) {
            return null;
        }
        this.updateBounds(level, x, y, z);
        return Box.getOrCreate((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    private void updateBounds(Level world, int i, int j, int k) {
        int m = world.getTileMeta(i, j, k) % 3;
        float f = 0.2f;
        if (m == 0) {
            this.setBoundingBox(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
        } else if (m == 1) {
            this.setBoundingBox(0.0f, 0.5f - f, 0.5f - f, 1.0f, 0.5f + f, 0.5f + f);
        } else {
            this.setBoundingBox(0.5f - f, 0.5f - f, 0.0f, 0.5f + f, 0.5f + f, 1.0f);
        }
    }

    @Override
    public int method_1621() {
        return 35;
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        return this.tex + meta / 3;
    }
}
