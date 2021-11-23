package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockSpike extends Tile {

    protected BlockSpike(int i) {
        super(i, 246, Material.METAL);
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
        float f = 0.25f;
        return Box.getOrCreate((float) x + f, y, (float) z + f, (float) (x + 1) - f, (float) (y + 1) - f, (float) (z + 1) - f);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public int method_1621() {
        return 32;
    }

    @Override
    public void onEntityCollision(Level world, int i, int j, int k, Entity entity) {
        entity.damage(null, 10);
    }
}
