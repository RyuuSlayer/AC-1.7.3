package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;

import java.util.Random;

public class om extends rp {
    int a;

    boolean[] b;

    int[] c;

    protected om(int i, ln material) {
        super(i, material);
        this.a = 0;
        this.b = new boolean[4];
        this.c = new int[4];
    }

    private void j(Level world, int i, int j, int k) {
        int l = world.e(i, j, k);
        world.a(i, j, k, this.bn + 1, l);
        world.b(i, j, k, i, j, k);
        world.j(i, j, k);
    }

    public void a(Level world, int i, int j, int k, Random random) {
        if (DebugMode.active)
            return;
        int l = h(world, i, j, k);
        byte byte0 = 1;
        if (this.bA == ln.h && !world.t.d)
            byte0 = 2;
        boolean flag = true;
        if (l > 0) {
            int i1 = -100;
            this.a = 0;
            i1 = f(world, i - 1, j, k, i1);
            i1 = f(world, i + 1, j, k, i1);
            i1 = f(world, i, j, k - 1, i1);
            i1 = f(world, i, j, k + 1, i1);
            int j1 = i1 + byte0;
            if (j1 >= 8 || i1 < 0)
                j1 = -1;
            if (h(world, i, j + 1, k) >= 0) {
                int l1 = h(world, i, j + 1, k);
                if (l1 >= 8) {
                    j1 = l1;
                } else {
                    j1 = l1 + 8;
                }
            }
            if (this.a >= 2 && this.bA == ln.g)
                if (world.f(i, j - 1, k).a()) {
                    j1 = 0;
                } else if (world.f(i, j - 1, k) == this.bA && world.e(i, j, k) == 0) {
                    j1 = 0;
                }
            if (this.bA == ln.h && l < 8 && j1 < 8 && j1 > l && random.nextInt(4) != 0) {
                j1 = l;
                flag = false;
            }
            if (j1 != l) {
                l = j1;
                if (l < 0) {
                    world.f(i, j, k, 0);
                } else {
                    world.d(i, j, k, l);
                    world.c(i, j, k, this.bn, e());
                    world.i(i, j, k, this.bn);
                }
            } else if (flag) {
                j(world, i, j, k);
            }
        } else {
            j(world, i, j, k);
        }
        if (m(world, i, j - 1, k)) {
            if (l >= 8) {
                world.b(i, j - 1, k, this.bn, l);
            } else {
                world.b(i, j - 1, k, this.bn, l + 8);
            }
        } else if (l >= 0 && (l == 0 || l(world, i, j - 1, k))) {
            boolean[] aflag = k(world, i, j, k);
            int k1 = l + byte0;
            if (l >= 8)
                k1 = 1;
            if (k1 >= 8)
                return;
            if (aflag[0])
                h(world, i - 1, j, k, k1);
            if (aflag[1])
                h(world, i + 1, j, k, k1);
            if (aflag[2])
                h(world, i, j, k - 1, k1);
            if (aflag[3])
                h(world, i, j, k + 1, k1);
        }
    }

    private void h(Level world, int i, int j, int k, int l) {
        if (m(world, i, j, k)) {
            int i1 = world.a(i, j, k);
            if (i1 > 0)
                if (this.bA == ln.h) {
                    i(world, i, j, k);
                } else {
                    Tile.m[i1].g(world, i, j, k, world.e(i, j, k));
                }
            world.b(i, j, k, this.bn, l);
        }
    }

    private int b(Level world, int i, int j, int k, int l, int i1) {
        int j1 = 1000;
        for (int k1 = 0; k1 < 4; k1++) {
            if ((k1 != 0 || i1 != 1) && (k1 != 1 || i1 != 0) && (k1 != 2 || i1 != 3) && (k1 != 3 || i1 != 2)) {
                int l1 = i;
                int i2 = j;
                int j2 = k;
                if (k1 == 0)
                    l1--;
                if (k1 == 1)
                    l1++;
                if (k1 == 2)
                    j2--;
                if (k1 == 3)
                    j2++;
                if (!l(world, l1, i2, j2) && (world.f(l1, i2, j2) != this.bA || world.e(l1, i2, j2) != 0)) {
                    if (!l(world, l1, i2 - 1, j2))
                        return l;
                    if (l < 4) {
                        int k2 = b(world, l1, i2, j2, l + 1, k1);
                        if (k2 < j1)
                            j1 = k2;
                    }
                }
            }
        }
        return j1;
    }

    private boolean[] k(Level world, int i, int j, int k) {
        for (int l = 0; l < 4; l++) {
            this.c[l] = 1000;
            int j1 = i;
            int i2 = j;
            int j2 = k;
            if (l == 0)
                j1--;
            if (l == 1)
                j1++;
            if (l == 2)
                j2--;
            if (l == 3)
                j2++;
            if (!l(world, j1, i2, j2) && (world.f(j1, i2, j2) != this.bA || world.e(j1, i2, j2) != 0))
                if (!l(world, j1, i2 - 1, j2)) {
                    this.c[l] = 0;
                } else {
                    this.c[l] = b(world, j1, i2, j2, 1, l);
                }
        }
        int i1 = this.c[0];
        for (int k1 = 1; k1 < 4; k1++) {
            if (this.c[k1] < i1)
                i1 = this.c[k1];
        }
        for (int l1 = 0; l1 < 4; l1++)
            this.b[l1] = (this.c[l1] == i1);
        return this.b;
    }

    private boolean l(Level world, int i, int j, int k) {
        int l = world.a(i, j, k);
        if (l == Tile.aF.bn || l == Tile.aM.bn || l == Tile.aE.bn || l == Tile.aG.bn || l == Tile.aY.bn)
            return true;
        if (l == 0)
            return false;
        ln material = (Tile.m[l]).bA;
        return material.c();
    }

    protected int f(Level world, int i, int j, int k, int l) {
        int i1 = h(world, i, j, k);
        if (i1 < 0)
            return l;
        if (i1 == 0)
            this.a++;
        if (i1 >= 8)
            i1 = 0;
        return (l >= 0 && i1 >= l) ? l : i1;
    }

    private boolean m(Level world, int i, int j, int k) {
        ln material = world.f(i, j, k);
        if (material == this.bA)
            return false;
        if (material == ln.h)
            return false;
        return !l(world, i, j, k);
    }

    public void c(Level world, int i, int j, int k) {
        super.c(world, i, j, k);
        if (world.a(i, j, k) == this.bn)
            world.c(i, j, k, this.bn, e());
    }
}
