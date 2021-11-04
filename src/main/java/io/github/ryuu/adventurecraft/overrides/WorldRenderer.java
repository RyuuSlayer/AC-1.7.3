package io.github.ryuu.adventurecraft.overrides;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import io.github.ryuu.adventurecraft.util.IEntityPather;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.LevelListener;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class WorldRenderer implements LevelListener {
    public List a;

    private Level k;

    private final TextureManager l;

    private final List m;

    private dk[] n;

    private dk[] o;

    private int p;

    private int q;

    private int r;

    private final int s;

    private final Minecraft t;

    private cv u;

    private IntBuffer v;

    private boolean w;

    private int x;

    private final int y;

    private final int z;

    private final int A;

    private int B;

    private int C;

    private int D;

    private int E;

    private int F;

    private int G;

    private int H;

    private int I;

    private int J;

    private int K;

    private int L;

    int[] b;

    IntBuffer c;

    private int M;

    private int N;

    private int O;

    private int P;

    private int Q;

    private int R;

    private final List S;

    private final tg[] T;

    int d;

    int e;

    double f;

    double g;

    double h;

    public float i;

    int j;

    public WorldRenderer(Minecraft minecraft, TextureManager renderengine) {
        this.T = new tg[]{new tg(), new tg(), new tg(), new tg()};
        this.a = new ArrayList();
        this.m = new ArrayList();
        this.w = false;
        this.x = 0;
        this.H = -1;
        this.I = 2;
        this.b = new int[50000];
        this.c = ge.d(64);
        this.S = new ArrayList();
        this.d = 0;
        this.e = ge.a(1);
        this.f = -9999.0D;
        this.g = -9999.0D;
        this.h = -9999.0D;
        this.j = 0;
        this.t = minecraft;
        this.l = renderengine;
        byte byte0 = 64;
        this.s = ge.a(byte0 * byte0 * byte0 * 3);
        this.w = minecraft.n().a();
        if (this.w) {
            this.c.clear();
            this.v = ge.d(byte0 * byte0 * byte0);
            this.v.clear();
            this.v.position(0);
            this.v.limit(byte0 * byte0 * byte0);
            ARBOcclusionQuery.glGenQueriesARB(this.v);
        }
        this.y = ge.a(3);
        GL11.glPushMatrix();
        GL11.glNewList(this.y, 4864);
        g();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator = Tessellator.a;
        this.z = this.y + 1;
        GL11.glNewList(this.z, 4864);
        byte byte1 = 64;
        int i = 256 / byte1 + 2;
        float f = 16.0F;
        int j;
        for (j = -byte1 * i; j <= byte1 * i; j += byte1) {
            int l;
            for (l = -byte1 * i; l <= byte1 * i; l += byte1) {
                tessellator.b();
                tessellator.a((j + 0), f, (l + 0));
                tessellator.a((j + byte1), f, (l + 0));
                tessellator.a((j + byte1), f, (l + byte1));
                tessellator.a((j + 0), f, (l + byte1));
                tessellator.a();
            }
        }
        GL11.glEndList();
        this.A = this.y + 2;
        GL11.glNewList(this.A, 4864);
        f = -16.0F;
        tessellator.b();
        int k;
        for (k = -byte1 * i; k <= byte1 * i; k += byte1) {
            int i1;
            for (i1 = -byte1 * i; i1 <= byte1 * i; i1 += byte1) {
                tessellator.a((k + byte1), f, (i1 + 0));
                tessellator.a((k + 0), f, (i1 + 0));
                tessellator.a((k + 0), f, (i1 + byte1));
                tessellator.a((k + byte1), f, (i1 + byte1));
            }
        }
        tessellator.a();
        GL11.glEndList();
    }

    private void g() {
        Random random = new Random(10842L);
        Tessellator tessellator = Tessellator.a;
        tessellator.b();
        for (int i = 0; i < 1500; i++) {
            double d = (random.nextFloat() * 2.0F - 1.0F);
            double d1 = (random.nextFloat() * 2.0F - 1.0F);
            double d2 = (random.nextFloat() * 2.0F - 1.0F);
            double d3 = (0.25F + random.nextFloat() * 0.25F);
            double d4 = d * d + d1 * d1 + d2 * d2;
            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                d *= d4;
                d1 *= d4;
                d2 *= d4;
                double d5 = d * 100.0D;
                double d6 = d1 * 100.0D;
                double d7 = d2 * 100.0D;
                double d8 = Math.atan2(d, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d * d + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);
                for (int j = 0; j < 4; j++) {
                    double d17 = 0.0D;
                    double d18 = ((j & 0x2) - 1) * d3;
                    double d19 = ((j + 1 & 0x2) - 1) * d3;
                    double d20 = d17;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d22;
                    double d24 = d21 * d12 + d20 * d13;
                    double d25 = d20 * d12 - d21 * d13;
                    double d26 = d25 * d9 - d23 * d10;
                    double d27 = d24;
                    double d28 = d23 * d9 + d25 * d10;
                    tessellator.a(d5 + d26, d6 + d27, d7 + d28);
                }
            }
        }
        tessellator.a();
    }

    public void a(Level world) {
        if (this.k != null)
            this.k.b(this);
        this.f = -9999.0D;
        this.g = -9999.0D;
        this.h = -9999.0D;
        th.a.a(world);
        this.k = world;
        this.u = new cv((xp) world);
        if (world != null) {
            world.a(this);
            a();
        }
    }

    public void a() {
        Tile.L.a(this.t.z.j);
        this.H = this.t.z.e;
        if (this.o != null)
            for (int i = 0; i < this.o.length; i++)
                this.o[i].c();
        int j = 64 << 4 - this.H;
        if (this.H > 0) {
            if (j > 400)
                j = 400;
        } else if (j > 800) {
            j = 800;
        }
        this.p = j / 16 + 1;
        this.q = 8;
        this.r = j / 16 + 1;
        this.o = new dk[this.p * this.q * this.r];
        this.n = new dk[this.p * this.q * this.r];
        int k = 0;
        int l = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.E = this.p;
        this.F = this.q;
        this.G = this.r;
        for (int i1 = 0; i1 < this.m.size(); i1++)
            ((dk) this.m.get(i1)).u = false;
        this.m.clear();
        this.a.clear();
        for (int j1 = 0; j1 < this.p; j1++) {
            for (int k1 = 0; k1 < this.q; k1++) {
                for (int l1 = 0; l1 < this.r; l1++) {
                    this.o[(l1 * this.q + k1) * this.p + j1] = new dk(this.k, this.a, j1 * 16, k1 * 16, l1 * 16, 16, this.s + k);
                    if (this.w)
                        (this.o[(l1 * this.q + k1) * this.p + j1]).z = this.v.get(l);
                    (this.o[(l1 * this.q + k1) * this.p + j1]).y = false;
                    (this.o[(l1 * this.q + k1) * this.p + j1]).x = true;
                    (this.o[(l1 * this.q + k1) * this.p + j1]).o = true;
                    (this.o[(l1 * this.q + k1) * this.p + j1]).w = l++;
                    this.o[(l1 * this.q + k1) * this.p + j1].f();
                    this.n[(l1 * this.q + k1) * this.p + j1] = this.o[(l1 * this.q + k1) * this.p + j1];
                    this.m.add(this.o[(l1 * this.q + k1) * this.p + j1]);
                    k += 3;
                }
            }
        }
        if (this.k != null) {
            LivingEntity entityliving = this.t.i;
            if (entityliving != null) {
                b(MathsHelper.b(entityliving.aM), MathsHelper.b(entityliving.aN), MathsHelper.b(entityliving.aO));
                Arrays.sort(this.n, new jo((Entity) entityliving));
            }
        }
        this.I = 2;
    }

    public void a(bt vec3d, yn icamera, float f) {
        if (this.I > 0) {
            this.I--;
            return;
        }
        TileEntityRenderDispatcher.a.a(this.k, this.l, this.t.q, this.t.i, f);
        th.a.a(this.k, this.l, this.t.q, this.t.i, this.t.z, f);
        this.J = 0;
        this.K = 0;
        this.L = 0;
        LivingEntity entityliving = this.t.i;
        th.b = entityliving.bl + (entityliving.aM - entityliving.bl) * f;
        th.c = entityliving.bm + (entityliving.aN - entityliving.bm) * f;
        th.d = entityliving.bn + (entityliving.aO - entityliving.bn) * f;
        TileEntityRenderDispatcher.b = entityliving.bl + (entityliving.aM - entityliving.bl) * f;
        TileEntityRenderDispatcher.c = entityliving.bm + (entityliving.aN - entityliving.bm) * f;
        TileEntityRenderDispatcher.d = entityliving.bn + (entityliving.aO - entityliving.bn) * f;
        List<Entity> list = this.k.o();
        this.J = list.size();
        for (int i = 0; i < this.k.e.size(); i++) {
            Entity entity = this.k.e.get(i);
            this.K++;
            if (entity.a(vec3d))
                th.a.a(entity, f);
        }
        for (int j = 0; j < list.size(); j++) {
            Entity entity1 = list.get(j);
            if (entity1.a(vec3d) && (entity1.bM || icamera.a(entity1.aW)) && (entity1 != this.t.i || this.t.z.A || this.t.i.N())) {
                int l = MathsHelper.b(entity1.aN);
                if (l < 0)
                    l = 0;
                if (l >= 128)
                    l = 127;
                if (this.k.i(MathsHelper.b(entity1.aM), l, MathsHelper.b(entity1.aO))) {
                    this.K++;
                    if ((this.t.cameraActive && this.t.cameraPause) || (AC_DebugMode.active && !(entity1 instanceof gs)) || entity1.stunned > 0) {
                        th.a.a(entity1, 1.0F);
                    } else {
                        th.a.a(entity1, f);
                    }
                }
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslated(-th.b, -th.c, -th.d);
        ScriptModel.renderAll(f);
        GL11.glPopMatrix();
        for (int k = 0; k < this.a.size(); k++)
            TileEntityRenderDispatcher.a.a(this.a.get(k), f);
    }

    public String b() {
        return "C: " + this.P + "/" + this.M + ". F: " + this.N + ", O: " + this.O + ", E: " + this.Q;
    }

    public String c() {
        return "E: " + this.K + "/" + this.J + ". B: " + this.L + ", I: " + (this.J - this.L - this.K);
    }

    private void b(int i, int j, int k) {
        i -= 8;
        j -= 8;
        k -= 8;
        this.B = Integer.MAX_VALUE;
        this.C = Integer.MAX_VALUE;
        this.D = Integer.MAX_VALUE;
        this.E = Integer.MIN_VALUE;
        this.F = Integer.MIN_VALUE;
        this.G = Integer.MIN_VALUE;
        int l = this.p * 16;
        int i1 = l / 2;
        for (int j1 = 0; j1 < this.p; j1++) {
            int k1 = j1 * 16;
            int l1 = k1 + i1 - i;
            if (l1 < 0)
                l1 -= l - 1;
            l1 /= l;
            k1 -= l1 * l;
            if (k1 < this.B)
                this.B = k1;
            if (k1 > this.E)
                this.E = k1;
            for (int i2 = 0; i2 < this.r; i2++) {
                int j2 = i2 * 16;
                int k2 = j2 + i1 - k;
                if (k2 < 0)
                    k2 -= l - 1;
                k2 /= l;
                j2 -= k2 * l;
                if (j2 < this.D)
                    this.D = j2;
                if (j2 > this.G)
                    this.G = j2;
                for (int l2 = 0; l2 < this.q; l2++) {
                    int i3 = l2 * 16;
                    if (i3 < this.C)
                        this.C = i3;
                    if (i3 > this.F)
                        this.F = i3;
                    dk worldrenderer = this.o[(i2 * this.q + l2) * this.p + j1];
                    boolean flag = worldrenderer.u;
                    worldrenderer.a(k1, i3, j2);
                    if (!flag && worldrenderer.u)
                        this.m.add(worldrenderer);
                }
            }
        }
    }

    public int a(LivingEntity entityliving, int i, double d) {
        for (int j = 0; j < 10; j++) {
            this.R = (this.R + 1) % this.o.length;
            dk worldrenderer = this.o[this.R];
            if (worldrenderer.u && !this.m.contains(worldrenderer))
                this.m.add(worldrenderer);
        }
        if (this.t.z.e != this.H) {
            ((ClientChunkCache) this.k.v).updateVeryFar();
            a();
        }
        if (i == 0) {
            this.M = 0;
            this.N = 0;
            this.O = 0;
            this.P = 0;
            this.Q = 0;
        }
        double d1 = entityliving.bl + (entityliving.aM - entityliving.bl) * d;
        double d2 = entityliving.bm + (entityliving.aN - entityliving.bm) * d;
        double d3 = entityliving.bn + (entityliving.aO - entityliving.bn) * d;
        double d4 = entityliving.aM - this.f;
        double d5 = entityliving.aN - this.g;
        double d6 = entityliving.aO - this.h;
        if (d4 * d4 + d5 * d5 + d6 * d6 > 64.0D) {
            this.f = entityliving.aM;
            this.g = entityliving.aN;
            this.h = entityliving.aO;
            b(MathsHelper.b(entityliving.aM), MathsHelper.b(entityliving.aN), MathsHelper.b(entityliving.aO));
            Arrays.sort(this.n, new jo((Entity) entityliving));
        }
        u.a();
        int k = 0;
        if (this.w && this.t.z.h && !this.t.z.g && i == 0) {
            int l = 0;
            int i1 = 16;
            a(l, i1);
            for (int j1 = l; j1 < i1; j1++)
                (this.n[j1]).x = true;
            k += a(l, i1, i, d);
            float farPlane = this.t.t.getFarPlane() * 1.25F;
            do {
                int byte0 = i1;
                i1 *= 2;
                if (i1 > this.n.length)
                    i1 = this.n.length;
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(3008);
                GL11.glDisable(2912);
                GL11.glColorMask(false, false, false, false);
                GL11.glDepthMask(false);
                a(byte0, i1);
                GL11.glPushMatrix();
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                for (int k1 = byte0; k1 < i1; k1++) {
                    if (this.n[k1].e()) {
                        (this.n[k1]).o = false;
                    } else {
                        if (!(this.n[k1]).o)
                            (this.n[k1]).x = true;
                        if ((this.n[k1]).x)
                            (this.n[k1]).x = (this.n[k1].a(entityliving) > farPlane);
                        if ((this.n[k1]).o && !(this.n[k1]).y) {
                            float f3 = MathsHelper.c(this.n[k1].a(entityliving));
                            int l1 = (int) (1.0F + f3 / 128.0F);
                            if (this.x % l1 == k1 % l1) {
                                dk worldrenderer1 = this.n[k1];
                                float f4 = (float) (worldrenderer1.i - d1);
                                float f5 = (float) (worldrenderer1.j - d2);
                                float f6 = (float) (worldrenderer1.k - d3);
                                float f7 = f4 - f;
                                float f8 = f5 - f1;
                                float f9 = f6 - f2;
                                if (f7 != 0.0F || f8 != 0.0F || f9 != 0.0F) {
                                    GL11.glTranslatef(f7, f8, f9);
                                    f += f7;
                                    f1 += f8;
                                    f2 += f9;
                                }
                                ARBOcclusionQuery.glBeginQueryARB(35092, (this.n[k1]).z);
                                this.n[k1].d();
                                ARBOcclusionQuery.glEndQueryARB(35092);
                                (this.n[k1]).y = true;
                            }
                        }
                    }
                }
                GL11.glPopMatrix();
                if (this.t.z.g) {
                    if (GameRenderer.b == 0) {
                        GL11.glColorMask(false, true, true, true);
                    } else {
                        GL11.glColorMask(true, false, false, true);
                    }
                } else {
                    GL11.glColorMask(true, true, true, true);
                }
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glEnable(2912);
                k += a(byte0, i1, i, d);
            } while (i1 < this.n.length);
        } else {
            k += a(0, this.n.length, i, d);
        }
        return k;
    }

    private void a(int i, int j) {
        for (int k = i; k < j; k++) {
            if ((this.n[k]).y) {
                this.c.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB((this.n[k]).z, 34919, this.c);
                if (this.c.get(0) != 0) {
                    (this.n[k]).y = false;
                    this.c.clear();
                    ARBOcclusionQuery.glGetQueryObjectuARB((this.n[k]).z, 34918, this.c);
                    (this.n[k]).x = (this.c.get(0) != 0);
                }
            }
        }
    }

    private int a(int i, int j, int k, double d) {
        this.S.clear();
        int l = 0;
        for (int i1 = i; i1 < j; i1++) {
            if (k == 0) {
                this.M++;
                if ((this.n[i1]).p[k]) {
                    this.Q++;
                } else if (!(this.n[i1]).o) {
                    this.N++;
                } else if (this.w && !(this.n[i1]).x) {
                    this.O++;
                } else {
                    this.P++;
                }
            }
            if (!(this.n[i1]).p[k] && (this.n[i1]).o && (!this.w || (this.n[i1]).x)) {
                int j1 = this.n[i1].a(k);
                if (j1 >= 0) {
                    this.S.add(this.n[i1]);
                    l++;
                }
            }
        }
        LivingEntity entityliving = this.t.i;
        double d1 = entityliving.bl + (entityliving.aM - entityliving.bl) * d;
        double d2 = entityliving.bm + (entityliving.aN - entityliving.bm) * d;
        double d3 = entityliving.bn + (entityliving.aO - entityliving.bn) * d;
        int k1 = 0;
        for (int l1 = 0; l1 < this.T.length; l1++)
            this.T[l1].b();
        for (int i2 = 0; i2 < this.S.size(); i2++) {
            dk worldrenderer = this.S.get(i2);
            int j2 = -1;
            for (int k2 = 0; k2 < k1; k2++) {
                if (this.T[k2].a(worldrenderer.i, worldrenderer.j, worldrenderer.k))
                    j2 = k2;
            }
            if (j2 < 0) {
                j2 = k1++;
                this.T[j2].a(worldrenderer.i, worldrenderer.j, worldrenderer.k, d1, d2, d3);
            }
            this.T[j2].a(worldrenderer.a(k));
        }
        a(k, d);
        return l;
    }

    public void a(int i, double d) {
        for (int j = 0; j < this.T.length; j++)
            this.T[j].a();
    }

    public void d() {
        this.x++;
    }

    public void a(float f) {
        if (this.t.f.t.c)
            return;
        GL11.glDisable(3553);
        bt vec3d = this.k.a((Entity) this.t.i, f);
        float f1 = (float) vec3d.a;
        float f2 = (float) vec3d.b;
        float f3 = (float) vec3d.c;
        if (this.t.z.g) {
            float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f7 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f4;
            f2 = f5;
            f3 = f7;
        }
        GL11.glColor3f(f1, f2, f3);
        Tessellator tessellator = Tessellator.a;
        GL11.glDepthMask(false);
        GL11.glEnable(2912);
        GL11.glColor3f(f1, f2, f3);
        GL11.glCallList(this.z);
        GL11.glDisable(2912);
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        u.a();
        float[] af = this.k.t.a(this.k.b(f), f);
        if (af != null) {
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            float f8 = this.k.b(f);
            GL11.glRotatef((f8 <= 0.5F) ? 0.0F : 180.0F, 0.0F, 0.0F, 1.0F);
            float f10 = af[0];
            float f12 = af[1];
            float f14 = af[2];
            if (this.t.z.g) {
                float f16 = (f10 * 30.0F + f12 * 59.0F + f14 * 11.0F) / 100.0F;
                float f18 = (f10 * 30.0F + f12 * 70.0F) / 100.0F;
                float f19 = (f10 * 30.0F + f14 * 70.0F) / 100.0F;
                f10 = f16;
                f12 = f18;
                f14 = f19;
            }
            tessellator.a(6);
            tessellator.a(f10, f12, f14, af[3]);
            tessellator.a(0.0D, 100.0D, 0.0D);
            int i = 16;
            tessellator.a(af[0], af[1], af[2], 0.0F);
            for (int j = 0; j <= i; j++) {
                float f20 = j * 3.141593F * 2.0F / i;
                float f21 = MathsHelper.a(f20);
                float f22 = MathsHelper.b(f20);
                tessellator.a((f21 * 120.0F), (f22 * 120.0F), (-f22 * 40.0F * af[3]));
            }
            tessellator.a();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
        }
        GL11.glEnable(3553);
        GL11.glBlendFunc(770, 1);
        GL11.glPushMatrix();
        float f6 = 1.0F - this.k.g(f);
        float f9 = 0.0F;
        float f11 = 0.0F;
        float f13 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f6);
        GL11.glTranslatef(f9, f11, f13);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(this.k.b(f) * 360.0F, 1.0F, 0.0F, 0.0F);
        float f15 = 30.0F;
        GL11.glBindTexture(3553, this.l.b("/terrain/sun.png"));
        tessellator.b();
        tessellator.a(-f15, 100.0D, -f15, 0.0D, 0.0D);
        tessellator.a(f15, 100.0D, -f15, 1.0D, 0.0D);
        tessellator.a(f15, 100.0D, f15, 1.0D, 1.0D);
        tessellator.a(-f15, 100.0D, f15, 0.0D, 1.0D);
        tessellator.a();
        f15 = 20.0F;
        GL11.glBindTexture(3553, this.l.b("/terrain/moon.png"));
        tessellator.b();
        tessellator.a(-f15, -100.0D, f15, 1.0D, 1.0D);
        tessellator.a(f15, -100.0D, f15, 0.0D, 1.0D);
        tessellator.a(f15, -100.0D, -f15, 0.0D, 0.0D);
        tessellator.a(-f15, -100.0D, -f15, 1.0D, 0.0D);
        tessellator.a();
        GL11.glDisable(3553);
        float f17 = this.k.e(f) * f6;
        if (f17 > 0.0F) {
            GL11.glColor4f(f17, f17, f17, f17);
            GL11.glCallList(this.y);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(2912);
        GL11.glPopMatrix();
        if (this.k.t.c()) {
            GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
        } else {
            GL11.glColor3f(f1, f2, f3);
        }
        GL11.glDisable(3553);
        GL11.glCallList(this.A);
        GL11.glEnable(3553);
        GL11.glDepthMask(true);
    }

    public void b(float f) {
        if (this.t.f.t.c)
            return;
        if (this.t.z.j) {
            c(f);
            return;
        }
        GL11.glDisable(2884);
        float f1 = (float) (this.t.i.bm + (this.t.i.aN - this.t.i.bm) * f);
        double d = this.t.i.aJ + (this.t.i.aM - this.t.i.aJ) * f + ((this.x + f) * 0.03F);
        double d1 = this.t.i.aL + (this.t.i.aO - this.t.i.aL) * f;
        if (this.t.cameraActive) {
            CutsceneCameraPoint p = this.t.cutsceneCamera.getCurrentPoint(f);
            f1 = p.posY;
            d = p.posX + ((this.x + f) * 0.03F);
            d1 = p.posZ;
        }
        byte byte0 = 32;
        int i = 256 / byte0;
        Tessellator tessellator = Tessellator.a;
        GL11.glBindTexture(3553, this.l.b("/environment/clouds.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        bt vec3d = this.k.c(f);
        float f2 = (float) vec3d.a;
        float f3 = (float) vec3d.b;
        float f4 = (float) vec3d.c;
        if (this.t.z.g) {
            float f5 = (f2 * 30.0F + f3 * 59.0F + f4 * 11.0F) / 100.0F;
            float f7 = (f2 * 30.0F + f3 * 70.0F) / 100.0F;
            float f8 = (f2 * 30.0F + f4 * 70.0F) / 100.0F;
            f2 = f5;
            f3 = f7;
            f4 = f8;
        }
        float f6 = 4.882813E-4F;
        int j = MathsHelper.b(d / 2048.0D);
        int k = MathsHelper.b(d1 / 2048.0D);
        d -= (j * 2048);
        d1 -= (k * 2048);
        float f9 = this.k.t.d() - f1 + 0.33F;
        float f10 = (float) (d * f6);
        float f11 = (float) (d1 * f6);
        tessellator.b();
        tessellator.a(f2, f3, f4, 0.8F);
        int l;
        for (l = -byte0 * i; l < byte0 * i; l += byte0) {
            int i1;
            for (i1 = -byte0 * i; i1 < byte0 * i; i1 += byte0) {
                tessellator.a((l + 0), f9, (i1 + byte0), ((l + 0) * f6 + f10), ((i1 + byte0) * f6 + f11));
                tessellator.a((l + byte0), f9, (i1 + byte0), ((l + byte0) * f6 + f10), ((i1 + byte0) * f6 + f11));
                tessellator.a((l + byte0), f9, (i1 + 0), ((l + byte0) * f6 + f10), ((i1 + 0) * f6 + f11));
                tessellator.a((l + 0), f9, (i1 + 0), ((l + 0) * f6 + f10), ((i1 + 0) * f6 + f11));
            }
        }
        tessellator.a();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
    }

    public boolean a(double d, double d1, double d2, float f) {
        return false;
    }

    public void c(float f) {
        GL11.glDisable(2884);
        float f1 = (float) (this.t.i.bm + (this.t.i.aN - this.t.i.bm) * f);
        Tessellator tessellator = Tessellator.a;
        float f2 = 12.0F;
        float f3 = 4.0F;
        double d = (this.t.i.aJ + (this.t.i.aM - this.t.i.aJ) * f + ((this.x + f) * 0.03F)) / f2;
        double d1 = (this.t.i.aL + (this.t.i.aO - this.t.i.aL) * f) / f2 + 0.33000001311302185D;
        float f4 = this.k.t.d() - f1 + 0.33F;
        int i = MathsHelper.b(d / 2048.0D);
        int j = MathsHelper.b(d1 / 2048.0D);
        d -= (i * 2048);
        d1 -= (j * 2048);
        GL11.glBindTexture(3553, this.l.b("/environment/clouds.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        bt vec3d = this.k.c(f);
        float f5 = (float) vec3d.a;
        float f6 = (float) vec3d.b;
        float f7 = (float) vec3d.c;
        if (this.t.z.g) {
            float f8 = (f5 * 30.0F + f6 * 59.0F + f7 * 11.0F) / 100.0F;
            float f10 = (f5 * 30.0F + f6 * 70.0F) / 100.0F;
            float f12 = (f5 * 30.0F + f7 * 70.0F) / 100.0F;
            f5 = f8;
            f6 = f10;
            f7 = f12;
        }
        float f9 = (float) (d * 0.0D);
        float f11 = (float) (d1 * 0.0D);
        float f13 = 0.00390625F;
        f9 = MathsHelper.b(d) * f13;
        f11 = MathsHelper.b(d1) * f13;
        float f14 = (float) (d - MathsHelper.b(d));
        float f15 = (float) (d1 - MathsHelper.b(d1));
        int k = 8;
        byte byte0 = 3;
        float f16 = 9.765625E-4F;
        GL11.glScalef(f2, 1.0F, f2);
        for (int l = 0; l < 2; l++) {
            if (l == 0) {
                GL11.glColorMask(false, false, false, false);
            } else if (this.t.z.g) {
                if (GameRenderer.b == 0) {
                    GL11.glColorMask(false, true, true, true);
                } else {
                    GL11.glColorMask(true, false, false, true);
                }
            } else {
                GL11.glColorMask(true, true, true, true);
            }
            for (int i1 = -byte0 + 1; i1 <= byte0; i1++) {
                for (int j1 = -byte0 + 1; j1 <= byte0; j1++) {
                    tessellator.b();
                    float f17 = (i1 * k);
                    float f18 = (j1 * k);
                    float f19 = f17 - f14;
                    float f20 = f18 - f15;
                    if (f4 > -f3 - 1.0F) {
                        tessellator.a(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.8F);
                        tessellator.b(0.0F, -1.0F, 0.0F);
                        tessellator.a((f19 + 0.0F), (f4 + 0.0F), (f20 + k), ((f17 + 0.0F) * f13 + f9), ((f18 + k) * f13 + f11));
                        tessellator.a((f19 + k), (f4 + 0.0F), (f20 + k), ((f17 + k) * f13 + f9), ((f18 + k) * f13 + f11));
                        tessellator.a((f19 + k), (f4 + 0.0F), (f20 + 0.0F), ((f17 + k) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                        tessellator.a((f19 + 0.0F), (f4 + 0.0F), (f20 + 0.0F), ((f17 + 0.0F) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                    }
                    if (f4 <= f3 + 1.0F) {
                        tessellator.a(f5, f6, f7, 0.8F);
                        tessellator.b(0.0F, 1.0F, 0.0F);
                        tessellator.a((f19 + 0.0F), (f4 + f3 - f16), (f20 + k), ((f17 + 0.0F) * f13 + f9), ((f18 + k) * f13 + f11));
                        tessellator.a((f19 + k), (f4 + f3 - f16), (f20 + k), ((f17 + k) * f13 + f9), ((f18 + k) * f13 + f11));
                        tessellator.a((f19 + k), (f4 + f3 - f16), (f20 + 0.0F), ((f17 + k) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                        tessellator.a((f19 + 0.0F), (f4 + f3 - f16), (f20 + 0.0F), ((f17 + 0.0F) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                    }
                    tessellator.a(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.8F);
                    if (i1 > -1) {
                        tessellator.b(-1.0F, 0.0F, 0.0F);
                        for (int k1 = 0; k1 < k; k1++) {
                            tessellator.a((f19 + k1 + 0.0F), (f4 + 0.0F), (f20 + k), ((f17 + k1 + 0.5F) * f13 + f9), ((f18 + k) * f13 + f11));
                            tessellator.a((f19 + k1 + 0.0F), (f4 + f3), (f20 + k), ((f17 + k1 + 0.5F) * f13 + f9), ((f18 + k) * f13 + f11));
                            tessellator.a((f19 + k1 + 0.0F), (f4 + f3), (f20 + 0.0F), ((f17 + k1 + 0.5F) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                            tessellator.a((f19 + k1 + 0.0F), (f4 + 0.0F), (f20 + 0.0F), ((f17 + k1 + 0.5F) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                        }
                    }
                    if (i1 <= 1) {
                        tessellator.b(1.0F, 0.0F, 0.0F);
                        for (int l1 = 0; l1 < k; l1++) {
                            tessellator.a((f19 + l1 + 1.0F - f16), (f4 + 0.0F), (f20 + k), ((f17 + l1 + 0.5F) * f13 + f9), ((f18 + k) * f13 + f11));
                            tessellator.a((f19 + l1 + 1.0F - f16), (f4 + f3), (f20 + k), ((f17 + l1 + 0.5F) * f13 + f9), ((f18 + k) * f13 + f11));
                            tessellator.a((f19 + l1 + 1.0F - f16), (f4 + f3), (f20 + 0.0F), ((f17 + l1 + 0.5F) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                            tessellator.a((f19 + l1 + 1.0F - f16), (f4 + 0.0F), (f20 + 0.0F), ((f17 + l1 + 0.5F) * f13 + f9), ((f18 + 0.0F) * f13 + f11));
                        }
                    }
                    tessellator.a(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.8F);
                    if (j1 > -1) {
                        tessellator.b(0.0F, 0.0F, -1.0F);
                        for (int i2 = 0; i2 < k; i2++) {
                            tessellator.a((f19 + 0.0F), (f4 + f3), (f20 + i2 + 0.0F), ((f17 + 0.0F) * f13 + f9), ((f18 + i2 + 0.5F) * f13 + f11));
                            tessellator.a((f19 + k), (f4 + f3), (f20 + i2 + 0.0F), ((f17 + k) * f13 + f9), ((f18 + i2 + 0.5F) * f13 + f11));
                            tessellator.a((f19 + k), (f4 + 0.0F), (f20 + i2 + 0.0F), ((f17 + k) * f13 + f9), ((f18 + i2 + 0.5F) * f13 + f11));
                            tessellator.a((f19 + 0.0F), (f4 + 0.0F), (f20 + i2 + 0.0F), ((f17 + 0.0F) * f13 + f9), ((f18 + i2 + 0.5F) * f13 + f11));
                        }
                    }
                    if (j1 <= 1) {
                        tessellator.b(0.0F, 0.0F, 1.0F);
                        for (int j2 = 0; j2 < k; j2++) {
                            tessellator.a((f19 + 0.0F), (f4 + f3), (f20 + j2 + 1.0F - f16), ((f17 + 0.0F) * f13 + f9), ((f18 + j2 + 0.5F) * f13 + f11));
                            tessellator.a((f19 + k), (f4 + f3), (f20 + j2 + 1.0F - f16), ((f17 + k) * f13 + f9), ((f18 + j2 + 0.5F) * f13 + f11));
                            tessellator.a((f19 + k), (f4 + 0.0F), (f20 + j2 + 1.0F - f16), ((f17 + k) * f13 + f9), ((f18 + j2 + 0.5F) * f13 + f11));
                            tessellator.a((f19 + 0.0F), (f4 + 0.0F), (f20 + j2 + 1.0F - f16), ((f17 + 0.0F) * f13 + f9), ((f18 + j2 + 0.5F) * f13 + f11));
                        }
                    }
                    tessellator.a();
                }
            }
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
    }

    public boolean a(LivingEntity entityliving, boolean flag) {
        boolean flag1 = false;
        if (flag1) {
            Collections.sort(this.m, (Comparator<?>) new md(entityliving));
            int i = this.m.size() - 1;
            int j = this.m.size();
            for (int k = 0; k < j; k++) {
                dk worldrenderer = this.m.get(i - k);
                if (!flag) {
                    if (worldrenderer.a(entityliving) > 256.0F)
                        if (worldrenderer.o) {
                            if (k >= 3)
                                return false;
                        } else if (k >= 1) {
                            return false;
                        }
                } else if (!worldrenderer.o) {
                    continue;
                }
                worldrenderer.a();
                this.m.remove(worldrenderer);
                worldrenderer.u = false;
                continue;
            }
            return (this.m.size() == 0);
        }
        byte byte0 = 2;
        md rendersorter = new md(entityliving);
        dk[] aworldrenderer = new dk[byte0];
        ArrayList<dk> arraylist = null;
        int l = this.m.size();
        int i1 = 0;
        long avgTime = 0L;
        if (PlayerTorch.isTorchActive())
            avgTime = Minecraft.minecraftInstance.getAvgFrameTime();
        for (int j1 = 0; j1 < l; j1++) {
            dk worldrenderer1 = this.m.get(j1);
            if (!flag) {
                if (worldrenderer1.a(entityliving) > 256.0F) {
                    int k2;
                    for (k2 = 0; k2 < byte0 && (aworldrenderer[k2] == null || rendersorter.a(aworldrenderer[k2], worldrenderer1) <= 0); k2++)
                        ;
                    if (--k2 > 0) {
                        for (int i3 = k2; --i3 != 0; )
                            aworldrenderer[i3 - 1] = aworldrenderer[i3];
                        aworldrenderer[k2] = worldrenderer1;
                    }
                    continue;
                }
            } else if (!worldrenderer1.o) {
                continue;
            }
            if (arraylist == null)
                arraylist = new ArrayList();
            i1++;
            arraylist.add(worldrenderer1);
            this.m.set(j1, null);
            if (PlayerTorch.isTorchActive()) {
                if (i1 >= 3 || avgTime > 40000000L)
                    break;
                if (i1 >= 2 && avgTime > 16666666L)
                    break;
            }
            continue;
        }
        if (arraylist != null) {
            if (arraylist.size() > 1)
                Collections.sort(arraylist, rendersorter);
            for (int k1 = arraylist.size() - 1; k1 >= 0; k1--) {
                dk worldrenderer2 = arraylist.get(k1);
                worldrenderer2.a();
                worldrenderer2.u = false;
            }
        }
        int l1 = 0;
        for (int i2 = byte0 - 1; i2 >= 0; i2--) {
            dk worldrenderer3 = aworldrenderer[i2];
            if (worldrenderer3 != null) {
                if (!worldrenderer3.o && i2 != byte0 - 1) {
                    aworldrenderer[i2] = null;
                    aworldrenderer[0] = null;
                    break;
                }
                aworldrenderer[i2].a();
                (aworldrenderer[i2]).u = false;
                l1++;
            }
        }
        int j2 = 0;
        int l2 = 0;
        for (int j3 = this.m.size(); j2 != j3; j2++) {
            dk worldrenderer4 = this.m.get(j2);
            if (worldrenderer4 != null) {
                boolean flag2 = false;
                for (int k3 = 0; k3 < byte0 && !flag2; k3++) {
                    if (worldrenderer4 == aworldrenderer[k3])
                        flag2 = true;
                }
                if (!flag2) {
                    if (l2 != j2)
                        this.m.set(l2, worldrenderer4);
                    l2++;
                }
            }
        }
        while (--j2 >= l2)
            this.m.remove(j2);
        return (l == i1 + l1);
    }

    public void a(Player entityplayer, vf movingobjectposition, int i, ItemInstance itemstack, float f) {
        Tessellator tessellator = Tessellator.a;
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathsHelper.a((float) System.currentTimeMillis() / 100.0F) * 0.2F + 0.4F) * 0.5F);
        if (i == 0) {
            if (this.i > 0.0F) {
                GL11.glBlendFunc(774, 768);
                int j = this.l.b("/terrain.png");
                GL11.glBindTexture(3553, j);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                GL11.glPushMatrix();
                int k = this.k.a(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d);
                Tile block = (k <= 0) ? null : Tile.m[k];
                GL11.glDisable(3008);
                GL11.glPolygonOffset(-3.0F, -3.0F);
                GL11.glEnable(32823);
                double d = entityplayer.bl + (entityplayer.aM - entityplayer.bl) * f;
                double d1 = entityplayer.bm + (entityplayer.aN - entityplayer.bm) * f;
                double d2 = entityplayer.bn + (entityplayer.aO - entityplayer.bn) * f;
                if (block == null)
                    block = Tile.u;
                GL11.glEnable(3008);
                tessellator.b();
                tessellator.b(-d, -d1, -d2);
                tessellator.c();
                this.u.a(block, movingobjectposition.b, movingobjectposition.c, movingobjectposition.d, 240 + (int) (this.i * 10.0F));
                tessellator.a();
                tessellator.b(0.0D, 0.0D, 0.0D);
                GL11.glDisable(3008);
                GL11.glPolygonOffset(0.0F, 0.0F);
                GL11.glDisable(32823);
                GL11.glEnable(3008);
                GL11.glDepthMask(true);
                GL11.glPopMatrix();
            }
        } else if (itemstack != null) {
            GL11.glBlendFunc(770, 771);
            float f1 = MathsHelper.a((float) System.currentTimeMillis() / 100.0F) * 0.2F + 0.8F;
            GL11.glColor4f(f1, f1, f1, MathsHelper.a((float) System.currentTimeMillis() / 200.0F) * 0.2F + 0.5F);
            int l = this.l.b("/terrain.png");
            GL11.glBindTexture(3553, l);
            int i1 = movingobjectposition.b;
            int j1 = movingobjectposition.c;
            int k1 = movingobjectposition.d;
            if (movingobjectposition.e == 0)
                j1--;
            if (movingobjectposition.e == 1)
                j1++;
            if (movingobjectposition.e == 2)
                k1--;
            if (movingobjectposition.e == 3)
                k1++;
            if (movingobjectposition.e == 4)
                i1--;
            if (movingobjectposition.e == 5)
                i1++;
        }
        GL11.glDisable(3042);
        GL11.glDisable(3008);
    }

    public void b(Player entityplayer, vf movingobjectposition, int i, ItemInstance itemstack, float f) {
        if (i == 0 && movingobjectposition.a == jg.a) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            float f1 = 0.002F;
            int j = this.k.a(movingobjectposition.b, movingobjectposition.c, movingobjectposition.d);
            if (j > 0) {
                Tile.m[j].a((xp) this.k, movingobjectposition.b, movingobjectposition.c, movingobjectposition.d);
                double d = entityplayer.bl + (entityplayer.aM - entityplayer.bl) * f;
                double d1 = entityplayer.bm + (entityplayer.aN - entityplayer.bm) * f;
                double d2 = entityplayer.bn + (entityplayer.aO - entityplayer.bn) * f;
                a(Tile.m[j].f(this.k, movingobjectposition.b, movingobjectposition.c, movingobjectposition.d).b(f1, f1, f1).c(-d, -d1, -d2));
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }

    public void drawCursorSelection(LivingEntity entityplayer, ItemInstance itemstack, float f) {
        if (ItemCursor.bothSet && itemstack != null && itemstack.c >= Items.cursor.bf && itemstack.c <= Items.cursor.bf + 20) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 0.6F, 0.0F, 0.4F);
            GL11.glLineWidth(3.0F);
            GL11.glDisable(3553);
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX) + 1;
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY) + 1;
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ) + 1;
            double offX = entityplayer.bl + (entityplayer.aM - entityplayer.bl) * f;
            double offY = entityplayer.bm + (entityplayer.aN - entityplayer.bm) * f;
            double offZ = entityplayer.bn + (entityplayer.aO - entityplayer.bn) * f;
            Tessellator tessellator = Tessellator.a;
            for (int x = minX; x <= maxX; x++) {
                tessellator.a(3);
                tessellator.a(x - offX, minY - offY, minZ - offZ);
                tessellator.a(x - offX, maxY - offY, minZ - offZ);
                tessellator.a(x - offX, maxY - offY, maxZ - offZ);
                tessellator.a(x - offX, minY - offY, maxZ - offZ);
                tessellator.a(x - offX, minY - offY, minZ - offZ);
                tessellator.a();
            }
            for (int y = minY; y <= maxY; y++) {
                tessellator.a(3);
                tessellator.a(minX - offX, y - offY, minZ - offZ);
                tessellator.a(maxX - offX, y - offY, minZ - offZ);
                tessellator.a(maxX - offX, y - offY, maxZ - offZ);
                tessellator.a(minX - offX, y - offY, maxZ - offZ);
                tessellator.a(minX - offX, y - offY, minZ - offZ);
                tessellator.a();
            }
            for (int z = minZ; z <= maxZ; z++) {
                tessellator.a(3);
                tessellator.a(minX - offX, minY - offY, z - offZ);
                tessellator.a(maxX - offX, minY - offY, z - offZ);
                tessellator.a(maxX - offX, maxY - offY, z - offZ);
                tessellator.a(minX - offX, maxY - offY, z - offZ);
                tessellator.a(minX - offX, minY - offY, z - offZ);
                tessellator.a();
            }
            GL11.glLineWidth(1.0F);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }

    public void drawEntityPath(net.minecraft.entity.Entity e, LivingEntity entityplayer, float f) {
        if (e instanceof IEntityPather) {
            IEntityPather ent = (IEntityPather) e;
            dh path = ent.getCurrentPath();
            double offX = entityplayer.bl + (entityplayer.aM - entityplayer.bl) * f;
            double offY = entityplayer.bm + (entityplayer.aN - entityplayer.bm) * f;
            double offZ = entityplayer.bn + (entityplayer.aO - entityplayer.bn) * f;
            if (path != null) {
                Tessellator tessellator = Tessellator.a;
                tessellator.a(3);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                if (e instanceof WalkingEntity && ((WalkingEntity) e).G() != null) {
                    GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
                } else {
                    GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.4F);
                }
                GL11.glLineWidth(5.0F);
                GL11.glDisable(3553);
                tessellator.a(e.aM - offX, e.aN - offY, e.aO - offZ);
                for (int i = path.c; i < path.a; i++) {
                    d p = path.b[i];
                    tessellator.a(p.a - offX + 0.5D, p.b - offY + 0.5D, p.c - offZ + 0.5D);
                }
                tessellator.a();
                GL11.glLineWidth(1.0F);
                GL11.glEnable(3553);
                GL11.glDisable(3042);
            }
        }
    }

    public void drawEntityFOV(LivingEntity e, LivingEntity entityplayer, float f) {
        if (e == entityplayer)
            return;
        double offX = entityplayer.bl + (entityplayer.aM - entityplayer.bl) * f;
        double offY = entityplayer.bm + (entityplayer.aN - entityplayer.bm) * f;
        double offZ = entityplayer.bn + (entityplayer.aO - entityplayer.bn) * f;
        Tessellator tessellator = Tessellator.a;
        tessellator.a(3);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        if (e.extraFov > 0.0F) {
            GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.4F);
        } else {
            GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.4F);
        }
        GL11.glLineWidth(5.0F);
        GL11.glDisable(3553);
        float fov = Math.min(e.fov / 2.0F + e.extraFov, 180.0F);
        double xFov = 5.0D * Math.sin(-3.141592653589793D * (e.aS - fov) / 180.0D) + e.aM;
        double zFov = 5.0D * Math.cos(-3.141592653589793D * (e.aS - fov) / 180.0D) + e.aO;
        tessellator.a(xFov - offX, e.aN - offY + e.w(), zFov - offZ);
        tessellator.a(e.aM - offX, e.aN - offY + e.w(), e.aO - offZ);
        xFov = 5.0D * Math.sin(-3.141592653589793D * (e.aS + fov) / 180.0D) + e.aM;
        zFov = 5.0D * Math.cos(-3.141592653589793D * (e.aS + fov) / 180.0D) + e.aO;
        tessellator.a(xFov - offX, e.aN - offY + e.w(), zFov - offZ);
        tessellator.a();
        GL11.glLineWidth(1.0F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    private void a(eq axisalignedbb) {
        Tessellator tessellator = Tessellator.a;
        tessellator.a(3);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a();
        tessellator.a(3);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a();
        tessellator.a(1);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a();
    }

    public void a(int i, int j, int k, int l, int i1, int j1) {
        int k1 = MathsHelper.a(i, 16);
        int l1 = MathsHelper.a(j, 16);
        int i2 = MathsHelper.a(k, 16);
        int j2 = MathsHelper.a(l, 16);
        int k2 = MathsHelper.a(i1, 16);
        int l2 = MathsHelper.a(j1, 16);
        for (int i3 = k1; i3 <= j2; i3++) {
            int j3 = i3 % this.p;
            if (j3 < 0)
                j3 += this.p;
            for (int k3 = l1; k3 <= k2; k3++) {
                int l3 = k3 % this.q;
                if (l3 < 0)
                    l3 += this.q;
                for (int i4 = i2; i4 <= l2; i4++) {
                    int j4 = i4 % this.r;
                    if (j4 < 0)
                        j4 += this.r;
                    int k4 = (j4 * this.q + l3) * this.p + j3;
                    dk worldrenderer = this.o[k4];
                    if (!worldrenderer.u) {
                        this.m.add(worldrenderer);
                        worldrenderer.f();
                    }
                }
            }
        }
    }

    public void a(int i, int j, int k) {
        a(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
    }

    public void b(int i, int j, int k, int l, int i1, int j1) {
        a(i - 1, j - 1, k - 1, l + 1, i1 + 1, j1 + 1);
    }

    public void a(yn icamera, float f) {
        for (int i = 0; i < this.o.length; i++) {
            if (!this.o[i].e() && (!(this.o[i]).o || (i + this.j & 0xF) == 0))
                this.o[i].a(icamera);
        }
        this.j++;
    }

    public void a(String s, int i, int j, int k) {
        if (s != null)
            this.t.v.b("C418 - " + s);
        this.t.B.a(s, i, j, k, 1.0F, 1.0F);
    }

    public void a(String s, double d, double d1, double d2, float f, float f1) {
        float f2 = 16.0F;
        if (f > 1.0F)
            f2 *= f;
        if (this.t.i.g(d, d1, d2) < (f2 * f2))
            this.t.B.b(s, (float) d, (float) d1, (float) d2, f, f1);
    }

    public void a(String s, double d, double d1, double d2, double d3, double d4, double d5) {
        spawnParticleR(s, d, d1, d2, d3, d4, d5);
    }

    public xw spawnParticleR(String s, double d, double d1, double d2, double d3, double d4, double d5) {
        kc kc;
        if (this.t == null || this.t.i == null || this.t.j == null)
            return null;
        double d6 = this.t.i.aM - d;
        double d7 = this.t.i.aN - d1;
        double d8 = this.t.i.aO - d2;
        double d9 = 16384.0D;
        if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
            return null;
        xw particle = null;
        if (s.equals("bubble")) {
            cr cr = new cr(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("smoke")) {
            xn xn = new xn(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("note")) {
            ae ae = new ae(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("portal")) {
            op op = new op(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("explode")) {
            gy gy = new gy(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("flame")) {
            qu qu = new qu(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("lava")) {
            fg fg = new fg(this.k, d, d1, d2);
        } else if (s.equals("footstep")) {
            gl gl = new gl(this.l, this.k, d, d1, d2);
        } else if (s.equals("splash")) {
            sy sy = new sy(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("largesmoke")) {
            xn xn = new xn(this.k, d, d1, d2, d3, d4, d5, 2.5F);
        } else if (s.equals("reddust")) {
            im im = new im(this.k, d, d1, d2, (float) d3, (float) d4, (float) d5);
        } else if (s.equals("snowballpoof")) {
            pb pb = new pb(this.k, d, d1, d2, MixinItemType.aB);
        } else if (s.equals("snowshovel")) {
            mu mu = new mu(this.k, d, d1, d2, d3, d4, d5);
        } else if (s.equals("slime")) {
            pb pb = new pb(this.k, d, d1, d2, MixinItemType.aK);
        } else if (s.equals("heart")) {
            kc = new kc(this.k, d, d1, d2, d3, d4, d5);
        }
        if (kc != null)
            this.t.j.a((xw) kc);
        return (xw) kc;
    }

    public void a(Entity entity) {
        entity.u_();
        if (entity.bA != null)
            this.l.a(entity.bA, (nf) new rr());
        if (entity.bB != null)
            this.l.a(entity.bB, (nf) new rr());
    }

    public void b(Entity entity) {
        if (entity.bA != null)
            this.l.c(entity.bA);
        if (entity.bB != null)
            this.l.c(entity.bB);
    }

    public void e() {
        for (int i = 0; i < this.o.length; i++) {
            if ((this.o[i]).A && !(this.o[i]).u) {
                this.m.add(this.o[i]);
                this.o[i].f();
            }
        }
    }

    public void updateAllTheRenderers() {
        for (int i = 0; i < this.o.length; i++) {
            if (!(this.o[i]).u)
                this.m.add(this.o[i]);
            this.o[i].f();
        }
    }

    public void a(int i, int j, int k, TileEntity tileentity) {
    }

    public void f() {
        ge.b(this.s);
    }

    public void a(Player entityplayer, int i, int j, int k, int l, int i1) {
        int j1, k1;
        double d, d1, d2;
        int l1, i2;
        Random random = this.k.r;
        switch (i) {
            default:
                return;
            case 1001:
                this.k.a(j, k, l, "random.click", 1.0F, 1.2F);
            case 1000:
                this.k.a(j, k, l, "random.click", 1.0F, 1.0F);
            case 1002:
                this.k.a(j, k, l, "random.bow", 1.0F, 1.2F);
            case 2000:
                j1 = i1 % 3 - 1;
                k1 = i1 / 3 % 3 - 1;
                d = j + j1 * 0.6D + 0.5D;
                d1 = k + 0.5D;
                d2 = l + k1 * 0.6D + 0.5D;
                for (l1 = 0; l1 < 10; l1++) {
                    double d3 = random.nextDouble() * 0.2D + 0.01D;
                    double d4 = d + j1 * 0.01D + (random.nextDouble() - 0.5D) * k1 * 0.5D;
                    double d5 = d1 + (random.nextDouble() - 0.5D) * 0.5D;
                    double d6 = d2 + k1 * 0.01D + (random.nextDouble() - 0.5D) * j1 * 0.5D;
                    double d7 = j1 * d3 + random.nextGaussian() * 0.01D;
                    double d8 = -0.03D + random.nextGaussian() * 0.01D;
                    double d9 = k1 * d3 + random.nextGaussian() * 0.01D;
                    a("smoke", d4, d5, d6, d7, d8, d9);
                }
            case 2001:
                i2 = i1 & 0xFF;
                if (i2 > 0) {
                    Tile block = Tile.m[i2];
                    this.t.B.b(block.by.a(), j + 0.5F, k + 0.5F, l + 0.5F, (block.by.b() + 1.0F) / 2.0F, block.by.c() * 0.8F);
                }
                this.t.j.a(j, k, l, i1 & 0xFF, i1 >> 8 & 0xFF);
            case 1003:
                if (Math.random() < 0.5D) {
                    this.k.a(j + 0.5D, k + 0.5D, l + 0.5D, "random.door_open", 1.0F, this.k.r.nextFloat() * 0.1F + 0.9F);
                } else {
                    this.k.a(j + 0.5D, k + 0.5D, l + 0.5D, "random.door_close", 1.0F, this.k.r.nextFloat() * 0.1F + 0.9F);
                }
            case 1004:
                this.k.a((j + 0.5F), (k + 0.5F), (l + 0.5F), "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
            case 1005:
                break;
        }
        if (ItemType.c[i1] instanceof tr)
            this.k.a(((tr) ItemType.c[i1]).a, j, k, l);
        this.k.a(null, j, k, l);
    }

    public void resetAll() {
        doReset(false);
    }

    public void resetForDeath() {
        doReset(true);
    }

    private void doReset(boolean forDeath) {
        Tile.resetActive = true;
        for (int i = 0; i < this.o.length; i++) {
            int xOffset = (this.o[i]).c;
            int yOffset = (this.o[i]).d;
            int zOffset = (this.o[i]).e;
            if (this.k.a(xOffset, yOffset, zOffset, xOffset + 15, yOffset + 15, zOffset + 15))
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 16; y++) {
                        for (int z = 0; z < 16; z++) {
                            int blockID = this.k.a(xOffset + x, yOffset + y, zOffset + z);
                            if (blockID > 0)
                                Tile.m[blockID].reset(this.k, xOffset + x, yOffset + y, zOffset + z, forDeath);
                        }
                    }
                }
        }
        Tile.resetActive = false;
    }
}
