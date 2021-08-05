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
    public Box getCollisionShape(Level world, int i, int j, int k) {
        float f = 0.25F;
        return Box.getOrCreate((i + f), j, (k + f), ((i + 1) - f), ((j + 1) - f), ((k + 1) - f));
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
        entity.damage((Entity) null, 10);
    }
}
