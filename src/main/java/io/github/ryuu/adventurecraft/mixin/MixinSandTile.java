package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

import java.util.Random;

public class MixinSandTile extends MixinTile implements IBlockColor {
    public static boolean a = false;

    public MixinSandTile(int i, int j) {
        super(i, j, ln.n);
    }

    public static boolean c_(Level world, int i, int j, int k) {
        int l = world.a(i, j, k);
        if (l == 0)
            return true;
        if (l == Tile.as.bn)
            return true;
        Material material = (Tile.m[l]).bA;
        if (material == Material.WATER)
            return true;
        return (material == Material.LAVA);
    }

    public void c(Level world, int i, int j, int k) {
        world.c(i, j, k, this.bn, e());
    }

    public void b(Level world, int i, int j, int k, int l) {
        world.c(i, j, k, this.bn, e());
    }

    public void a(Level world, int i, int j, int k, Random random) {
        h(world, i, j, k);
    }

    private void h(Level world, int i, int j, int k) {
        int l = i;
        int i1 = j;
        int j1 = k;
        if (c_(world, l, i1 - 1, j1) && i1 >= 0) {
            byte byte0 = 32;
            if (a || !world.a(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
                world.f(i, j, k, 0);
                for (; c_(world, i, j - 1, k) && j > 0; j--) ;
                if (j > 0)
                    world.f(i, j, k, this.bn);
            } else {
                int metadata = world.e(i, j, k);
                FallingTile entityfallingsand = new FallingTile(world, (i + 0.5F), (j + 0.5F), (k + 0.5F), this.bn);
                entityfallingsand.metadata = metadata;
                world.b(entityfallingsand);
            }
        }
    }

    public int e() {
        return 3;
    }

    public int a(int i, int j) {
        if (j == 0)
            return this.bm;
        return 228 + j - 1;
    }

    public void incrementColor(Level world, int i, int j, int k) {
        if (subTypes[this.bn] > 0) {
            int metadata = world.e(i, j, k);
            world.d(i, j, k, (metadata + 1) % subTypes[this.bn]);
        }
    }
}
