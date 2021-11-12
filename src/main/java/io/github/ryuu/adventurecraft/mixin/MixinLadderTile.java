package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

import java.util.Random;

public class MixinLadderTile extends Tile {
    protected MixinLadderTile(int i, int j) {
        super(i, j, ln.o);
    }

    public static boolean isLadderID(int bID) {
        return (bID == Tile.aG.bn || bID == Blocks.ladders1.bn || bID == Blocks.ladders2.bn || bID == Blocks.ladders3.bn || bID == Blocks.ladders4.bn);
    }

    public eq e(Level world, int i, int j, int k) {
        int l = world.e(i, j, k) % 4 + 2;
        float f = 0.125F;
        if (l == 2)
            a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        if (l == 3)
            a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        if (l == 4)
            a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        if (l == 5)
            a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        return super.e(world, i, j, k);
    }

    public eq f(Level world, int i, int j, int k) {
        int l = world.e(i, j, k) % 4 + 2;
        float f = 0.125F;
        if (l == 2)
            a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        if (l == 3)
            a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        if (l == 4)
            a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        if (l == 5)
            a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        return super.f(world, i, j, k);
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 8;
    }

    public boolean a(Level world, int i, int j, int k) {
        if (world.h(i - 1, j, k))
            return true;
        if (world.h(i + 1, j, k))
            return true;
        if (world.h(i, j, k - 1))
            return true;
        int bID = world.a(i, j - 1, k);
        if (isLadderID(bID))
            return true;
        return world.h(i, j, k + 1);
    }

    public void e(Level world, int i, int j, int k, int l) {
        int i1 = world.e(i, j, k);
        if (i1 == 0 && isLadderID(world.a(i, j - 1, k)))
            i1 = world.e(i, j - 1, k) % 4 + 2;
        if (i1 == 0 && isLadderID(world.a(i, j + 1, k)))
            i1 = world.e(i, j + 1, k) % 4 + 2;
        if ((i1 == 0 || l == 2) && world.g(i, j, k + 1))
            i1 = 2;
        if ((i1 == 0 || l == 3) && world.g(i, j, k - 1))
            i1 = 3;
        if ((i1 == 0 || l == 4) && world.g(i + 1, j, k))
            i1 = 4;
        if ((i1 == 0 || l == 5) && world.g(i - 1, j, k))
            i1 = 5;
        world.d(i, j, k, i1 - 2);
    }

    public int a(Random random) {
        return 1;
    }
}
