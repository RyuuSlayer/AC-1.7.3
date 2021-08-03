package io.github.ryuu.adventurecraft.overrides;

import java.util.Random;

public class yq extends uu {
    private int[] a;

    private int[] b;

    protected yq(int i, int j) {
        super(i, j, ln.m);
        this.a = new int[256];
        this.b = new int[256];
        b(true);
    }

    public void k() {
        a(uu.y.bn, 5, 20);
        a(uu.ba.bn, 5, 20);
        a(uu.au.bn, 5, 20);
        a(uu.K.bn, 5, 5);
        a(uu.L.bn, 30, 60);
        a(uu.ao.bn, 30, 20);
        a(uu.an.bn, 15, 100);
        a(uu.Y.bn, 60, 100);
        a(uu.ac.bn, 30, 60);
    }

    private void a(int i, int j, int k) {
        this.a[i] = j;
        this.b[i] = k;
    }

    public eq e(fd world, int i, int j, int k) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 3;
    }

    public int a(Random random) {
        return 0;
    }

    public int e() {
        return 40;
    }

    public void a(fd world, int i, int j, int k, Random random) {
        if (AC_DebugMode.active)
            return;
        boolean flag = (world.a(i, j - 1, k) == uu.bc.bn);
        if (!a(world, i, j, k))
            world.f(i, j, k, 0);
        if (!flag && world.C() && (world.t(i, j, k) || world.t(i - 1, j, k) || world.t(i + 1, j, k) || world.t(i, j, k - 1) || world.t(i, j, k + 1))) {
            world.f(i, j, k, 0);
            return;
        }
        int l = world.e(i, j, k);
        if (l < 15)
            world.e(i, j, k, l + random.nextInt(3) / 2);
        world.c(i, j, k, this.bn, e());
        if (!flag && !h(world, i, j, k)) {
            if (!world.h(i, j - 1, k) || l > 3)
                world.f(i, j, k, 0);
            return;
        }
        if (!flag && !c((xp)world, i, j - 1, k) && l == 15 && random.nextInt(4) == 0) {
            world.f(i, j, k, 0);
            return;
        }
        a(world, i + 1, j, k, 300, random, l);
        a(world, i - 1, j, k, 300, random, l);
        a(world, i, j - 1, k, 250, random, l);
        a(world, i, j + 1, k, 250, random, l);
        a(world, i, j, k - 1, 300, random, l);
        a(world, i, j, k + 1, 300, random, l);
        for (int i1 = i - 1; i1 <= i + 1; i1++) {
            for (int j1 = k - 1; j1 <= k + 1; j1++) {
                for (int k1 = j - 1; k1 <= j + 4; k1++) {
                    if (i1 != i || k1 != j || j1 != k) {
                        int l1 = 100;
                        if (k1 > j + 1)
                            l1 += (k1 - j + 1) * 100;
                        int i2 = i(world, i1, k1, j1);
                        if (i2 > 0) {
                            int j2 = (i2 + 40) / (l + 30);
                            if (j2 > 0 && random.nextInt(l1) <= j2 && (!world.C() || !world.t(i1, k1, j1)) && !world.t(i1 - 1, k1, k) && !world.t(i1 + 1, k1, j1) && !world.t(i1, k1, j1 - 1) && !world.t(i1, k1, j1 + 1)) {
                                int k2 = l + random.nextInt(5) / 4;
                                if (k2 > 15)
                                    k2 = 15;
                                world.b(i1, k1, j1, this.bn, k2);
                            }
                        }
                    }
                }
            }
        }
    }

    private void a(fd world, int i, int j, int k, int l, Random random, int i1) {
        int j1 = this.b[world.a(i, j, k)];
        if (random.nextInt(l) < j1) {
            boolean flag = (world.a(i, j, k) == uu.an.bn);
            if (random.nextInt(i1 + 10) < 5 && !world.t(i, j, k)) {
                int k1 = i1 + random.nextInt(5) / 4;
                if (k1 > 15)
                    k1 = 15;
                world.b(i, j, k, this.bn, k1);
            } else {
                world.f(i, j, k, 0);
            }
            if (flag)
                uu.an.c(world, i, j, k, 1);
        }
    }

    private boolean h(fd world, int i, int j, int k) {
        if (c((xp)world, i + 1, j, k))
            return true;
        if (c((xp)world, i - 1, j, k))
            return true;
        if (c((xp)world, i, j - 1, k))
            return true;
        if (c((xp)world, i, j + 1, k))
            return true;
        if (c((xp)world, i, j, k - 1))
            return true;
        return c((xp)world, i, j, k + 1);
    }

    private int i(fd world, int i, int j, int k) {
        int l = 0;
        if (!world.d(i, j, k))
            return 0;
        l = f(world, i + 1, j, k, l);
        l = f(world, i - 1, j, k, l);
        l = f(world, i, j - 1, k, l);
        l = f(world, i, j + 1, k, l);
        l = f(world, i, j, k - 1, l);
        l = f(world, i, j, k + 1, l);
        return l;
    }

    public boolean v_() {
        return false;
    }

    public boolean c(xp iblockaccess, int i, int j, int k) {
        return (this.a[iblockaccess.a(i, j, k)] > 0);
    }

    public int f(fd world, int i, int j, int k, int l) {
        int i1 = this.a[world.a(i, j, k)];
        if (i1 > l)
            return i1;
        return l;
    }

    public boolean a(fd world, int i, int j, int k) {
        return (world.h(i, j - 1, k) || h(world, i, j, k));
    }

    public void b(fd world, int i, int j, int k, int l) {
        if (!world.h(i, j - 1, k) && !h(world, i, j, k)) {
            world.f(i, j, k, 0);
            return;
        }
    }

    public void c(fd world, int i, int j, int k) {
        if (world.a(i, j - 1, k) == uu.aq.bn && uu.bf.a_(world, i, j, k))
            return;
        if (!world.h(i, j - 1, k) && !h(world, i, j, k)) {
            world.f(i, j, k, 0);
            return;
        }
        world.c(i, j, k, this.bn, e());
    }

    public void b(fd world, int i, int j, int k, Random random) {
        if (random.nextInt(24) == 0)
            world.a((i + 0.5F), (j + 0.5F), (k + 0.5F), "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
        if (world.h(i, j - 1, k) || uu.as.c((xp)world, i, j - 1, k)) {
            for (int l = 0; l < 3; l++) {
                float f = i + random.nextFloat();
                float f6 = j + random.nextFloat() * 0.5F + 0.5F;
                float f12 = k + random.nextFloat();
                world.a("largesmoke", f, f6, f12, 0.0D, 0.0D, 0.0D);
            }
        } else {
            if (uu.as.c((xp)world, i - 1, j, k))
                for (int i1 = 0; i1 < 2; i1++) {
                    float f1 = i + random.nextFloat() * 0.1F;
                    float f7 = j + random.nextFloat();
                    float f13 = k + random.nextFloat();
                    world.a("largesmoke", f1, f7, f13, 0.0D, 0.0D, 0.0D);
                }
            if (uu.as.c((xp)world, i + 1, j, k))
                for (int j1 = 0; j1 < 2; j1++) {
                    float f2 = (i + 1) - random.nextFloat() * 0.1F;
                    float f8 = j + random.nextFloat();
                    float f14 = k + random.nextFloat();
                    world.a("largesmoke", f2, f8, f14, 0.0D, 0.0D, 0.0D);
                }
            if (uu.as.c((xp)world, i, j, k - 1))
                for (int k1 = 0; k1 < 2; k1++) {
                    float f3 = i + random.nextFloat();
                    float f9 = j + random.nextFloat();
                    float f15 = k + random.nextFloat() * 0.1F;
                    world.a("largesmoke", f3, f9, f15, 0.0D, 0.0D, 0.0D);
                }
            if (uu.as.c((xp)world, i, j, k + 1))
                for (int l1 = 0; l1 < 2; l1++) {
                    float f4 = i + random.nextFloat();
                    float f10 = j + random.nextFloat();
                    float f16 = (k + 1) - random.nextFloat() * 0.1F;
                    world.a("largesmoke", f4, f10, f16, 0.0D, 0.0D, 0.0D);
                }
            if (uu.as.c((xp)world, i, j + 1, k))
                for (int i2 = 0; i2 < 2; i2++) {
                    float f5 = i + random.nextFloat();
                    float f11 = (j + 1) - random.nextFloat() * 0.1F;
                    float f17 = k + random.nextFloat();
                    world.a("largesmoke", f5, f11, f17, 0.0D, 0.0D, 0.0D);
                }
        }
    }
}
