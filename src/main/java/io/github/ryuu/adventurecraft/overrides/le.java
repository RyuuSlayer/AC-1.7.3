package io.github.ryuu.adventurecraft.overrides;

import java.util.Random;

public class le extends Tile {
    protected le(int i, ln material) {
        super(i, material);
        this.bm = 97;
        if (material == ln.f)
            this.bm++;
        float f = 0.5F;
        float f1 = 1.0F;
        a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public int a(int i, int j) {
        if (i == 0 || i == 1)
            return this.bm;
        int k = e(j);
        if ((((k == 0 || k == 2) ? 1 : 0) ^ ((i <= 3) ? 1 : 0)) != 0)
            return this.bm;
        int l = k / 2 + (i & 0x1 ^ k);
        l += (j & 0x4) / 4;
        int i1 = this.bm - (j & 0x8) * 2;
        if ((l & 0x1) != 0)
            i1 = -i1;
        return i1;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 7;
    }

    public eq f(fd world, int i, int j, int k) {
        a((xp) world, i, j, k);
        return super.f(world, i, j, k);
    }

    public eq e(fd world, int i, int j, int k) {
        a((xp) world, i, j, k);
        return super.e(world, i, j, k);
    }

    public void a(xp iblockaccess, int i, int j, int k) {
        d(e(iblockaccess.e(i, j, k)));
    }

    public void d(int i) {
        float f = 0.1875F;
        a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        if (i == 0)
            a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        if (i == 1)
            a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        if (i == 2)
            a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        if (i == 3)
            a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
    }

    public void b(fd world, int i, int j, int k, gs entityplayer) {
        a(world, i, j, k, entityplayer);
    }

    public boolean a(fd world, int i, int j, int k, gs entityplayer) {
        if (this.bA == ln.f)
            return true;
        int l = world.e(i, j, k);
        if ((l & 0x8) != 0) {
            if (world.a(i, j - 1, k) == this.bn)
                a(world, i, j - 1, k, entityplayer);
            return true;
        }
        if (world.a(i, j + 1, k) == this.bn)
            world.d(i, j + 1, k, (l ^ 0x4) + 8);
        world.d(i, j, k, l ^ 0x4);
        world.b(i, j - 1, k, i, j, k);
        world.a(entityplayer, 1003, i, j, k, 0);
        return true;
    }

    public void a(fd world, int i, int j, int k, boolean flag) {
        int l = world.e(i, j, k);
        if ((l & 0x8) != 0) {
            if (world.a(i, j - 1, k) == this.bn)
                a(world, i, j - 1, k, flag);
            return;
        }
        boolean flag1 = ((world.e(i, j, k) & 0x4) > 0);
        if (flag1 == flag)
            return;
        if (world.a(i, j + 1, k) == this.bn)
            world.d(i, j + 1, k, (l ^ 0x4) + 8);
        world.d(i, j, k, l ^ 0x4);
        world.b(i, j - 1, k, i, j, k);
        world.a(null, 1003, i, j, k, 0);
    }

    public void b(fd world, int i, int j, int k, int l) {
        int i1 = world.e(i, j, k);
        if ((i1 & 0x8) != 0) {
            if (world.a(i, j - 1, k) != this.bn)
                world.f(i, j, k, 0);
            if (l > 0 && Tile.m[l].f())
                b(world, i, j - 1, k, l);
        } else {
            boolean flag = false;
            if (world.a(i, j + 1, k) != this.bn) {
                world.f(i, j, k, 0);
                flag = true;
            }
            if (!world.h(i, j - 1, k)) {
                world.f(i, j, k, 0);
                flag = true;
                if (world.a(i, j + 1, k) == this.bn)
                    world.f(i, j + 1, k, 0);
            }
            if (flag) {
                if (!world.B)
                    g(world, i, j, k, i1);
            } else if (l > 0 && Tile.m[l].f()) {
                boolean flag1 = (world.s(i, j, k) || world.s(i, j + 1, k));
                a(world, i, j, k, flag1);
            }
        }
    }

    public int a(int i, Random random) {
        if ((i & 0x8) != 0)
            return 0;
        if (this.bA == ln.f)
            return ItemType.az.bf;
        return ItemType.at.bf;
    }

    public vf a(fd world, int i, int j, int k, bt vec3d, bt vec3d1) {
        a((xp) world, i, j, k);
        int m = world.e(i, j, k);
        if (this.bA == ln.f && (m & 0x8) == 8)
            this.bt = 0.8125D;
        vec3d = vec3d.c(-i, -j, -k);
        vec3d1 = vec3d1.c(-i, -j, -k);
        bt vec3d2 = vec3d.a(vec3d1, this.bs);
        bt vec3d3 = vec3d.a(vec3d1, this.bv);
        bt vec3d4 = vec3d.b(vec3d1, this.bt);
        bt vec3d5 = vec3d.b(vec3d1, this.bw);
        bt vec3d6 = vec3d.c(vec3d1, this.bu);
        bt vec3d7 = vec3d.c(vec3d1, this.bx);
        if (!a(vec3d2))
            vec3d2 = null;
        if (!a(vec3d3))
            vec3d3 = null;
        if (!b(vec3d4))
            vec3d4 = null;
        if (!b(vec3d5))
            vec3d5 = null;
        if (!c(vec3d6))
            vec3d6 = null;
        if (!c(vec3d7))
            vec3d7 = null;
        bt vec3d8 = null;
        if (vec3d2 != null && (vec3d8 == null || vec3d.c(vec3d2) < vec3d.c(vec3d8)))
            vec3d8 = vec3d2;
        if (vec3d3 != null && (vec3d8 == null || vec3d.c(vec3d3) < vec3d.c(vec3d8)))
            vec3d8 = vec3d3;
        if (vec3d4 != null && (vec3d8 == null || vec3d.c(vec3d4) < vec3d.c(vec3d8)))
            vec3d8 = vec3d4;
        if (vec3d5 != null && (vec3d8 == null || vec3d.c(vec3d5) < vec3d.c(vec3d8)))
            vec3d8 = vec3d5;
        if (vec3d6 != null && (vec3d8 == null || vec3d.c(vec3d6) < vec3d.c(vec3d8)))
            vec3d8 = vec3d6;
        if (vec3d7 != null && (vec3d8 == null || vec3d.c(vec3d7) < vec3d.c(vec3d8)))
            vec3d8 = vec3d7;
        if (vec3d8 == null)
            return null;
        byte byte0 = -1;
        if (vec3d8 == vec3d2)
            byte0 = 4;
        if (vec3d8 == vec3d3)
            byte0 = 5;
        if (vec3d8 == vec3d4)
            byte0 = 0;
        if (vec3d8 == vec3d5)
            byte0 = 1;
        if (vec3d8 == vec3d6)
            byte0 = 2;
        if (vec3d8 == vec3d7)
            byte0 = 3;
        return new vf(i, j, k, byte0, vec3d8.c(i, j, k));
    }

    public int e(int i) {
        if ((i & 0x4) == 0)
            return i - 1 & 0x3;
        return i & 0x3;
    }

    public boolean a(fd world, int i, int j, int k) {
        if (j >= 127)
            return false;
        return (world.h(i, j - 1, k) && super.a(world, i, j, k) && super.a(world, i, j + 1, k));
    }

    public static boolean f(int i) {
        return ((i & 0x4) != 0);
    }

    public int h() {
        return 1;
    }
}
