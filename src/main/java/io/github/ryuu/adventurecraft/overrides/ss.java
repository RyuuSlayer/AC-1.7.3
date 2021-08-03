package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.blocks.BlockColor;

import java.util.ArrayList;
import java.util.Random;

public class ss extends BlockColor {
    private Tile a;

    protected ss(int i, Tile block) {
        super(i, block.bm, block.bA);
        this.a = block;
        c(block.bo);
        b(block.bp / 3.0F);
        a(block.by);
        g(255);
        if (block.bA == ln.d)
            this.defaultColor = 16777215;
    }

    public void a(xp iblockaccess, int i, int j, int k) {
        a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public eq e(fd world, int i, int j, int k) {
        return super.e(world, i, j, k);
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 10;
    }

    public boolean b(xp iblockaccess, int i, int j, int k, int l) {
        return super.b(iblockaccess, i, j, k, l);
    }

    public void a(fd world, int i, int j, int k, eq axisalignedbb, ArrayList arraylist) {
        int l = world.e(i, j, k) & 0x3;
        a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        super.a(world, i, j, k, axisalignedbb, arraylist);
        if (l == 0) {
            Tile b = Tile.m[world.a(i - 1, j, k)];
            if (b != null && b.b() == 10) {
                int n = world.e(i - 1, j, k) & 0x3;
                if (n == 2) {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 3) {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.e(i + 1, j, k) & 0x3;
            b = Tile.m[world.a(i + 1, j, k)];
            if (b != null && b.b() == 10 && (m == 2 || m == 3)) {
                if (m == 2) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (m == 3) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
        } else if (l == 1) {
            int m = world.e(i - 1, j, k) & 0x3;
            Tile b = Tile.m[world.a(i - 1, j, k)];
            if (b != null && b.b() == 10 && (m == 2 || m == 3)) {
                if (m == 3) {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
            b = Tile.m[world.a(i + 1, j, k)];
            if (b != null && b.b() == 10) {
                m = world.e(i + 1, j, k) & 0x3;
                if (m == 2) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (m == 3) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
        } else if (l == 2) {
            Tile b = Tile.m[world.a(i, j, k - 1)];
            if (b != null && b.b() == 10) {
                int n = world.e(i, j, k - 1) & 0x3;
                if (n == 1) {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 0) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.e(i, j, k + 1) & 0x3;
            b = Tile.m[world.a(i, j, k + 1)];
            if (b != null && b.b() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
        } else if (l == 3) {
            Tile b = Tile.m[world.a(i, j, k + 1)];
            if (b != null && b.b() == 10) {
                int n = world.e(i, j, k + 1) & 0x3;
                if (n == 1) {
                    a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else if (n == 0) {
                    a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            }
            int m = world.e(i, j, k - 1) & 0x3;
            b = Tile.m[world.a(i, j, k - 1)];
            if (b != null && b.b() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                } else {
                    a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    super.a(world, i, j, k, axisalignedbb, arraylist);
                }
            } else {
                a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                super.a(world, i, j, k, axisalignedbb, arraylist);
            }
        }
        a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void b(fd world, int i, int j, int k, Random random) {
        this.a.b(world, i, j, k, random);
    }

    public void b(fd world, int i, int j, int k, gs entityplayer) {
        this.a.b(world, i, j, k, entityplayer);
    }

    public void c(fd world, int i, int j, int k, int l) {
        this.a.c(world, i, j, k, l);
    }

    public float d(xp iblockaccess, int i, int j, int k) {
        return this.a.d(iblockaccess, i, j, k);
    }

    public float a(sn entity) {
        return this.a.a(entity);
    }

    public int b_() {
        return this.a.b_();
    }

    public int a(int i, Random random) {
        return this.a.a(i, random);
    }

    public int a(Random random) {
        return this.a.a(random);
    }

    public int a(int i, int j) {
        return this.a.a(i, j);
    }

    public int a(int i) {
        return this.a.a(i);
    }

    public int a(xp iblockaccess, int i, int j, int k, int l) {
        return this.a.a(iblockaccess, i, j, k, l);
    }

    public int e() {
        return this.a.e();
    }

    public eq f(fd world, int i, int j, int k) {
        return this.a.f(world, i, j, k);
    }

    public void a(fd world, int i, int j, int k, sn entity, bt vec3d) {
        this.a.a(world, i, j, k, entity, vec3d);
    }

    public boolean v_() {
        return this.a.v_();
    }

    public boolean a(int i, boolean flag) {
        return this.a.a(i, flag);
    }

    public boolean a(fd world, int i, int j, int k) {
        return this.a.a(world, i, j, k);
    }

    public void c(fd world, int i, int j, int k) {
        b(world, i, j, k, 0);
        this.a.c(world, i, j, k);
    }

    public void b(fd world, int i, int j, int k) {
        this.a.b(world, i, j, k);
    }

    public void a(fd world, int i, int j, int k, int l, float f) {
        this.a.a(world, i, j, k, l, f);
    }

    public void g(fd world, int i, int j, int k, int l) {
        this.a.g(world, i, j, k, l);
    }

    public void b(fd world, int i, int j, int k, sn entity) {
        this.a.b(world, i, j, k, entity);
    }

    public void a(fd world, int i, int j, int k, Random random) {
        this.a.a(world, i, j, k, random);
    }

    public boolean a(fd world, int i, int j, int k, gs entityplayer) {
        return this.a.a(world, i, j, k, entityplayer);
    }

    public void d(fd world, int i, int j, int k) {
        this.a.d(world, i, j, k);
    }

    public void a(fd world, int i, int j, int k, ls entityliving) {
        int l = in.b((entityliving.aS * 4.0F / 360.0F) + 0.5D) & 0x3;
        if (l == 0)
            world.d(i, j, k, 2);
        if (l == 1)
            world.d(i, j, k, 1);
        if (l == 2)
            world.d(i, j, k, 3);
        if (l == 3)
            world.d(i, j, k, 0);
    }

    protected int getColorMetaData(xp iblockaccess, int i, int j, int k) {
        return iblockaccess.e(i, j, k) >> 2;
    }

    protected void setColorMetaData(fd world, int i, int j, int k, int color) {
        world.d(i, j, k, world.e(i, j, k) & 0x3 | color << 2);
    }
}
