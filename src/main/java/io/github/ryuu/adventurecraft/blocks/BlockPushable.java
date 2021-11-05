package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

import java.util.Random;

public class BlockPushable extends BlockColor {
    public BlockPushable(int i, int j, Material material) {
        super(i, j, material);
    }

    public static boolean canFallBelow(Level world, int i, int j, int k) {
        int l = world.getTileId(i, j, k);
        if (l == 0)
            return true;
        if (l == Tile.FIRE.id)
            return true;
        Material material = (Tile.BY_ID[l]).material;
        if (material == Material.WATER)
            return true;
        return (material == Material.LAVA);
    }

    @Override
    public void method_1611(Level world, int i, int j, int k) {
        world.method_216(i, j, k, this.id, getTickrate());
    }

    @Override
    public void method_1609(Level world, int i, int j, int k, int l) {
        world.method_216(i, j, k, this.id, getTickrate());
    }

    @Override
    public void onScheduledTick(Level world, int i, int j, int k, Random random) {
        tryToFall(world, i, j, k);
    }

    private void tryToFall(Level world, int i, int j, int k) {
        if (canFallBelow(world, i, j - 1, k) && j >= 0) {
            FallingTile entityfallingsand = new FallingTile(world, (i + 0.5F), (j + 0.5F), (k + 0.5F), this.id);
            entityfallingsand.metadata = world.getTileMeta(i, j, k);
            world.spawnEntity(entityfallingsand);
        }
    }

    @Override
    public int getTickrate() {
        return 3;
    }
}
