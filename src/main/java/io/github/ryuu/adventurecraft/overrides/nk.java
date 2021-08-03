package io.github.ryuu.adventurecraft.overrides;

import java.util.Random;

public class nk extends jp {
    public nk(int i, int j) {
        super(i, j, ln.s, false);
        this.bB = 0.98F;
        b(true);
    }

    public int b_() {
        return 1;
    }

    public boolean b(xp iblockaccess, int i, int j, int k, int l) {
        return super.b(iblockaccess, i, j, k, 1 - l);
    }

    public void a(fd world, gs entityplayer, int i, int j, int k, int l) {
        super.a(world, entityplayer, i, j, k, l);
        ln material = world.f(i, j - 1, k);
        if (material.c() || material.d())
            world.f(i, j, k, Tile.B.bn);
    }

    public int a(Random random) {
        return 0;
    }

    public void a(fd world, int i, int j, int k, Random random) {
        if (!world.x.iceMelts)
            return;
        if (world.a(eb.b, i, j, k) > 11 - Tile.q[this.bn]) {
            g(world, i, j, k, world.e(i, j, k));
            world.f(i, j, k, Tile.C.bn);
        }
    }

    public int h() {
        return 0;
    }
}
