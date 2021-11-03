package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;

import java.util.Random;

public abstract class FluidTile extends Tile {
    protected FluidTile(int i, ln material) {
        super(i, ((material != ln.h) ? 12 : 14) * 16 + 13, material);
        float f = 0.0F;
        float f1 = 0.0F;
        a(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
        b(true);
    }

    public int b(xp iblockaccess, int i, int j, int k) {
        if (!TerrainImage.isWaterLoaded || (this.bn != Tile.B.bn && this.bn != Tile.C.bn))
            return -1;
        return TerrainImage.getWaterColor(i, k);
    }

    public static float d(int i) {
        if (i >= 8)
            i = 0;
        float f = (i + 1) / 9.0F;
        return f;
    }

    public int a(int i) {
        if (i == 0 || i == 1)
            return this.bm;
        return this.bm + 1;
    }

    protected int h(fd world, int i, int j, int k) {
        if (world.f(i, j, k) != this.bA)
            return -1;
        return world.e(i, j, k);
    }

    protected int c(xp iblockaccess, int i, int j, int k) {
        if (iblockaccess.f(i, j, k) != this.bA)
            return -1;
        int l = iblockaccess.e(i, j, k);
        if (l >= 8)
            l = 0;
        return l;
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public boolean a(int i, boolean flag) {
        return ((DebugMode.active && isHittable) || (flag && i == 0));
    }

    public boolean v_() {
        return (DebugMode.active && isHittable);
    }

    public boolean d(xp iblockaccess, int i, int j, int k, int l) {
        ln material = iblockaccess.f(i, j, k);
        if (material == this.bA)
            return false;
        if (material == ln.s)
            return false;
        if (l == 1)
            return true;
        return super.d(iblockaccess, i, j, k, l);
    }

    public boolean b(xp iblockaccess, int i, int j, int k, int l) {
        ln material = iblockaccess.f(i, j, k);
        if (material == this.bA)
            return false;
        if (material == ln.s)
            return false;
        if (l == 1)
            return true;
        return super.b(iblockaccess, i, j, k, l);
    }

    public eq e(fd world, int i, int j, int k) {
        return null;
    }

    public int b() {
        return 4;
    }

    public int a(int i, Random random) {
        return 0;
    }

    public int a(Random random) {
        return 0;
    }

    private bt e(xp iblockaccess, int i, int j, int k) {
        bt vec3d = bt.b(0.0D, 0.0D, 0.0D);
        int l = c(iblockaccess, i, j, k);
        for (int i1 = 0; i1 < 4; i1++) {
            int j1 = i;
            int k1 = j;
            int l1 = k;
            if (i1 == 0)
                j1--;
            if (i1 == 1)
                l1--;
            if (i1 == 2)
                j1++;
            if (i1 == 3)
                l1++;
            int i2 = c(iblockaccess, j1, k1, l1);
            if (i2 < 0) {
                if (!iblockaccess.f(j1, k1, l1).c()) {
                    i2 = c(iblockaccess, j1, k1 - 1, l1);
                    if (i2 >= 0) {
                        int j2 = i2 - l - 8;
                        vec3d = vec3d.c(((j1 - i) * j2), ((k1 - j) * j2), ((l1 - k) * j2));
                    }
                }
            } else if (i2 >= 0) {
                int k2 = i2 - l;
                vec3d = vec3d.c(((j1 - i) * k2), ((k1 - j) * k2), ((l1 - k) * k2));
            }
        }
        if (iblockaccess.e(i, j, k) >= 8) {
            boolean flag = flag || d(iblockaccess, i, j, k - 1, 2);
            if (flag || d(iblockaccess, i, j, k + 1, 3))
                flag = true;
            if (flag || d(iblockaccess, i - 1, j, k, 4))
                flag = true;
            if (flag || d(iblockaccess, i + 1, j, k, 5))
                flag = true;
            if (flag || d(iblockaccess, i, j + 1, k - 1, 2))
                flag = true;
            if (flag || d(iblockaccess, i, j + 1, k + 1, 3))
                flag = true;
            if (flag || d(iblockaccess, i - 1, j + 1, k, 4))
                flag = true;
            if (flag || d(iblockaccess, i + 1, j + 1, k, 5))
                flag = true;
            if (flag)
                vec3d = vec3d.c().c(0.0D, -6.0D, 0.0D);
        }
        vec3d = vec3d.c();
        return vec3d;
    }

    public void a(fd world, int i, int j, int k, Entity entity, bt vec3d) {
        bt vec3d1 = e((xp) world, i, j, k);
        vec3d.a += vec3d1.a;
        vec3d.b += vec3d1.b;
        vec3d.c += vec3d1.c;
    }

    public int e() {
        if (this.bA == ln.g)
            return 5;
        return (this.bA != ln.h) ? 0 : 30;
    }

    public float d(xp iblockaccess, int i, int j, int k) {
        float f = iblockaccess.c(i, j, k);
        float f1 = iblockaccess.c(i, j + 1, k);
        return (f <= f1) ? f1 : f;
    }

    public void a(fd world, int i, int j, int k, Random random) {
        super.a(world, i, j, k, random);
    }

    public int b_() {
        return (this.bA != ln.g) ? 0 : 1;
    }

    public void b(fd world, int i, int j, int k, Random random) {
        if (this.bA == ln.g && random.nextInt(64) == 0) {
            int l = world.e(i, j, k);
            if (l > 0 && l < 8)
                world.a((i + 0.5F), (j + 0.5F), (k + 0.5F), "liquid.water", random.nextFloat() * 0.25F + 0.75F, random.nextFloat() * 1.0F + 0.5F);
        }
        if (this.bA == ln.h && world.f(i, j + 1, k) == ln.a && !world.g(i, j + 1, k) && random.nextInt(100) == 0) {
            double d = (i + random.nextFloat());
            double d1 = j + this.bw;
            double d2 = (k + random.nextFloat());
            world.a("lava", d, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    public static double a(xp iblockaccess, int i, int j, int k, ln material) {
        bt vec3d = null;
        if (material == ln.g)
            vec3d = ((rp) Tile.B).e(iblockaccess, i, j, k);
        if (material == ln.h)
            vec3d = ((rp) Tile.D).e(iblockaccess, i, j, k);
        if (vec3d.a == 0.0D && vec3d.c == 0.0D)
            return -1000.0D;
        return Math.atan2(vec3d.c, vec3d.a) - 1.5707963267948966D;
    }

    public void c(fd world, int i, int j, int k) {
        j(world, i, j, k);
    }

    public void b(fd world, int i, int j, int k, int l) {
        j(world, i, j, k);
    }

    private void j(fd world, int i, int j, int k) {
        if (world.a(i, j, k) != this.bn)
            return;
        if (this.bA == ln.h) {
            boolean flag = flag || world.f(i, j, k - 1) == ln.g;
            if (flag || world.f(i, j, k + 1) == ln.g)
                flag = true;
            if (flag || world.f(i - 1, j, k) == ln.g)
                flag = true;
            if (flag || world.f(i + 1, j, k) == ln.g)
                flag = true;
            if (flag || world.f(i, j + 1, k) == ln.g)
                flag = true;
            if (flag) {
                int l = world.e(i, j, k);
                if (l == 0) {
                    world.f(i, j, k, Tile.aq.bn);
                } else if (l <= 4) {
                    world.f(i, j, k, Tile.x.bn);
                }
                i(world, i, j, k);
            }
        }
    }

    protected void i(fd world, int i, int j, int k) {
        world.a((i + 0.5F), (j + 0.5F), (k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.r.nextFloat() - world.r.nextFloat()) * 0.8F);
        for (int l = 0; l < 8; l++)
            world.a("largesmoke", i + Math.random(), j + 1.2D, k + Math.random(), 0.0D, 0.0D, 0.0D);
    }

    static boolean isHittable = true;
}
