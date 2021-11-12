package io.github.ryuu.adventurecraft.overrides;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.github.ryuu.adventurecraft.util.CoordBlock;
import io.github.ryuu.adventurecraft.util.LightCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.tile.TileEntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;

public class dk {
    private static final nw D;
    public static int b = 0;

    static {
        D = nw.a;
    }

    private final List F;
    public Level a;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public boolean o;
    public boolean[] p;
    public int q;
    public int r;
    public int s;
    public float t;
    public boolean u;
    public eq v;
    public int w;
    public boolean x;
    public boolean y;
    public int z;
    public boolean A;
    public List B;
    private int C;
    private boolean E;

    public dk(Level world, List list, int i, int j, int k, int l, int i1) {
        this.C = -1;
        this.o = false;
        this.p = new boolean[2];
        this.x = true;
        this.E = false;
        this.B = new ArrayList();
        this.a = world;
        this.F = list;
        this.f = this.g = this.h = l;
        this.t = MathsHelper.c((this.f * this.f + this.g * this.g + this.h * this.h)) / 2.0F;
        this.C = i1;
        this.c = -999;
        a(i, j, k);
        this.u = false;
    }

    public void a(int i, int j, int k) {
        if (i == this.c && j == this.d && k == this.e)
            return;
        b();
        this.c = i;
        this.d = j;
        this.e = k;
        this.q = i + this.f / 2;
        this.r = j + this.g / 2;
        this.s = k + this.h / 2;
        this.l = i & 0x3FF;
        this.m = j;
        this.n = k & 0x3FF;
        this.i = i - this.l;
        this.j = j - this.m;
        this.k = k - this.n;
        float f = 6.0F;
        this.v = eq.a((i - f), (j - f), (k - f), ((i + this.f) + f), ((j + this.g) + f), ((k + this.h) + f));
        GL11.glNewList(this.C + 2, 4864);
        ItemRenderer.a(eq.b((this.l - f), (this.m - f), (this.n - f), ((this.l + this.f) + f), ((this.m + this.g) + f), ((this.n + this.h) + f)));
        GL11.glEndList();
        f();
    }

    private void g() {
        GL11.glTranslatef(this.l, this.m, this.n);
    }

    public void a() {
        if (!this.u)
            return;
        b++;
        int i = this.c;
        int j = this.d;
        int k = this.e;
        int l = this.c + this.f;
        int i1 = this.d + this.g;
        int j1 = this.e + this.h;
        for (int k1 = 0; k1 < 2; k1++)
            this.p[k1] = true;
        Chunk.a = false;
        HashSet<?> hashset = new HashSet();
        hashset.addAll(this.B);
        this.B.clear();
        int l1 = 1;
        ew chunkcache = new ew(this.a, i - l1, j - l1, k - l1, l + l1, i1 + l1, j1 + l1);
        cv renderblocks = new cv(chunkcache);
        int i2 = 0;
        while (i2 < 2) {
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            for (int texNum = 0; texNum <= 3; texNum++) {
                if (texNum != 1) {
                    boolean startedDrawing = false;
                    for (int j2 = j; j2 < i1; j2++) {
                        for (int k2 = k; k2 < j1; k2++) {
                            for (int l2 = i; l2 < l; l2++) {
                                int i3 = chunkcache.a(l2, j2, k2);
                                if (i3 > 0)
                                    if (texNum == Tile.m[i3].getTextureNum()) {
                                        if (!flag2) {
                                            flag2 = true;
                                            GL11.glNewList(this.C + i2, 4864);
                                            GL11.glPushMatrix();
                                            g();
                                            float f = 1.000001F;
                                            GL11.glTranslatef(-this.h / 2.0F, -this.g / 2.0F, -this.h / 2.0F);
                                            GL11.glScalef(f, f, f);
                                            GL11.glTranslatef(this.h / 2.0F, this.g / 2.0F, this.h / 2.0F);
                                        }
                                        if (!startedDrawing) {
                                            startedDrawing = true;
                                            if (texNum == 0) {
                                                GL11.glBindTexture(3553, Minecraft.minecraftInstance.p.b("/terrain.png"));
                                            } else {
                                                GL11.glBindTexture(3553, Minecraft.minecraftInstance.p.b(String.format("/terrain%d.png", new Object[]{Integer.valueOf(texNum)})));
                                            }
                                            D.b();
                                            D.b(-this.c, -this.d, -this.e);
                                        }
                                        if (i2 == 0 && Tile.p[i3]) {
                                            TileEntity tileentity = chunkcache.b(l2, j2, k2);
                                            if (TileEntityRenderDispatcher.a.a(tileentity))
                                                this.B.add(tileentity);
                                        }
                                        Tile block = Tile.m[i3];
                                        int j3 = block.b_();
                                        if (j3 != i2) {
                                            flag = true;
                                        } else if (j3 == i2) {
                                            flag1 |= renderblocks.b(block, l2, j2, k2);
                                        }
                                    }
                            }
                        }
                        if (startedDrawing) {
                            D.a();
                            startedDrawing = false;
                        }
                    }
                }
            }
            if (flag2) {
                GL11.glPopMatrix();
                GL11.glEndList();
                D.b(0.0D, 0.0D, 0.0D);
            } else {
                flag1 = false;
            }
            if (flag1)
                this.p[i2] = false;
            if (!flag)
                break;
            i2++;
        }
        HashSet hashset1 = new HashSet();
        hashset1.addAll(this.B);
        hashset1.removeAll(hashset);
        this.F.addAll(hashset1);
        hashset.removeAll(this.B);
        this.F.removeAll(hashset);
        this.A = Chunk.a;
        this.E = true;
        LightCache.cache.clear();
        CoordBlock.resetPool();
    }

    public float a(Entity entity) {
        float f = (float) (entity.aM - this.q);
        float f1 = (float) (entity.aN - this.r);
        float f2 = (float) (entity.aO - this.s);
        return f * f + f1 * f1 + f2 * f2;
    }

    public void b() {
        for (int i = 0; i < 2; i++)
            this.p[i] = true;
        this.o = false;
        this.E = false;
    }

    public void c() {
        b();
        this.a = null;
    }

    public int a(int i) {
        if (!this.o)
            return -1;
        if (!this.p[i])
            return this.C + i;
        return -1;
    }

    public void a(yn icamera) {
        this.o = icamera.a(this.v);
    }

    public void d() {
        GL11.glCallList(this.C + 2);
    }

    public boolean e() {
        if (!this.E)
            return false;
        return (this.p[0] && this.p[1]);
    }

    public void f() {
        this.u = true;
    }
}
