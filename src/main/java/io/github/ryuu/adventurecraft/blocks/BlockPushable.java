package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

import java.util.Random;

public class BlockPushable extends BlockColor {
    public BlockPushable(int i, int j, ln material) {
        super(i, j, material);
    }

    public void c(Level world, int i, int j, int k) {
        world.c(i, j, k, this.bn, e());
    }

    public void b(Level world, int i, int j, int k, int l) {
        world.c(i, j, k, this.bn, e());
    }

    public void a(Level world, int i, int j, int k, Random random) {
        tryToFall(world, i, j, k);
    }

    private void tryToFall(Level world, int i, int j, int k) {
        if (canFallBelow(world, i, j - 1, k) && j >= 0) {
            ju entityfallingsand = new ju(world, (i + 0.5F), (j + 0.5F), (k + 0.5F), this.bn);
            entityfallingsand.metadata = world.e(i, j, k);
            world.b(entityfallingsand);
        }
    }

    public int e() {
        return 3;
    }

    public static boolean canFallBelow(Level world, int i, int j, int k) {
        int l = world.a(i, j, k);
        if (l == 0)
            return true;
        if (l == Tile.as.bn)
            return true;
        ln material = (Tile.m[l]).bA;
        if (material == ln.g)
            return true;
        return (material == ln.h);
    }
}
