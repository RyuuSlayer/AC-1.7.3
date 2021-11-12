package io.github.ryuu.adventurecraft.mixin;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.CutsceneCameraPoint;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.MapEditing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.HandItemRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class MixinGameRenderer {
    public static boolean a = false;
    public static int b;
    private final Minecraft j;
    private final cu n;
    private final cu o;
    private final cu p;
    private final cu q;
    private final cu r;
    private final cu s;
    private final float t;
    private final float v;
    private final float x;
    private final float B;
    private final double F;
    private final double G;
    private final Random J;
    public HandItemRenderer c;
    public float z;
    public HandItemRenderer offHandItemRenderer;
    volatile int d;
    volatile int e;
    FloatBuffer f;
    float g;
    float h;
    float i;
    float farClipAdjustment;
    private float k;
    private int l;
    private Entity m;
    private float u;
    private float w;
    private float y;
    private float A;
    private float C;
    private boolean D;
    private double E;
    private long H;
    private long I;
    private int K;
    private float L;
    private float M;

    public MixinGameRenderer(Minecraft minecraft) {
        this.k = 0.0F;
        this.m = null;
        this.n = new cu();
        this.o = new cu();
        this.p = new cu();
        this.q = new cu();
        this.r = new cu();
        this.s = new cu();
        this.t = 4.0F;
        this.u = 4.0F;
        this.v = 0.0F;
        this.w = 0.0F;
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
        this.A = 0.0F;
        this.B = 0.0F;
        this.C = 0.0F;
        this.D = false;
        this.E = 1.0D;
        this.F = 0.0D;
        this.G = 0.0D;
        this.H = System.currentTimeMillis();
        this.I = 0L;
        this.J = new Random();
        this.K = 0;
        this.d = 0;
        this.e = 0;
        this.f = ge.e(16);
        this.j = minecraft;
        this.c = new HandItemRenderer(minecraft);
        this.offHandItemRenderer = new HandItemRenderer(minecraft);
        this.farClipAdjustment = 1.0F;
    }

    public void a() {
        this.L = this.M;
        this.u = this.t;
        this.w = this.v;
        this.y = this.x;
        this.A = this.z;
        this.C = this.B;
        if (this.j.i == null)
            this.j.i = (LivingEntity) this.j.h;
        float f = this.j.f.c(MathsHelper.b(this.j.i.aM), MathsHelper.b(this.j.i.aN), MathsHelper.b(this.j.i.aO));
        float f1 = (4 - this.j.z.e) / 4.0F;
        float f2 = f * (1.0F - f1) + f1;
        this.M += (f2 - this.M) * 0.1F;
        this.l++;
        this.c.a();
        this.j.h.c.swapOffhandWithMain();
        this.offHandItemRenderer.a();
        this.j.h.c.swapOffhandWithMain();
        c();
    }

    public void a(float f) {
        if (this.j.i == null)
            return;
        if (this.j.f == null)
            return;
        double d = this.j.c.b();
        this.j.y = this.j.i.a(d, f);
        double d1 = d;
        bt vec3d = this.j.i.e(f);
        if (this.j.y != null)
            d1 = this.j.y.f.c(vec3d);
        d1 = d = 32.0D;
        if (d1 > 3.0D)
            d1 = 3.0D;
        d = d1;
        bt vec3d1 = this.j.i.f(f);
        bt vec3d2 = vec3d.c(vec3d1.a * d, vec3d1.b * d, vec3d1.c * d);
        this.m = null;
        float f1 = 1.0F;
        List<Entity> list = this.j.f.b((Entity) this.j.i, this.j.i.aW.a(vec3d1.a * d, vec3d1.b * d, vec3d1.c * d).b(f1, f1, f1));
        double d2 = 0.0D;
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity.h_()) {
                float f2 = entity.m_();
                eq axisalignedbb = entity.aW.b(f2, f2, f2);
                vf movingobjectposition = axisalignedbb.a(vec3d, vec3d2);
                if (axisalignedbb.a(vec3d)) {
                    if (0.0D < d2 || d2 == 0.0D) {
                        this.m = entity;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3d.c(movingobjectposition.f);
                    if (d3 < d2 || d2 == 0.0D) {
                        this.m = entity;
                        d2 = d3;
                    }
                }
            }
        }
        if (this.m != null && !(this.j.c instanceof pj))
            this.j.y = new vf(this.m);
    }

    private float d(float f) {
        LivingEntity entityliving = this.j.i;
        float f1 = 70.0F;
        if (entityliving.a(ln.g))
            f1 = 60.0F;
        if (entityliving.Y <= 0) {
            float f2 = entityliving.ad + f;
            f1 /= (1.0F - 500.0F / (f2 + 500.0F)) * 2.0F + 1.0F;
        }
        return f1 + this.A + (this.z - this.A) * f;
    }

    private void e(float f) {
        LivingEntity entityliving = this.j.i;
        float f1 = entityliving.aa - f;
        if (entityliving.Y <= 0) {
            float f2 = entityliving.ad + f;
            GL11.glRotatef(40.0F - 8000.0F / (f2 + 200.0F), 0.0F, 0.0F, 1.0F);
        }
        if (f1 < 0.0F)
            return;
        f1 /= entityliving.ab;
        f1 = MathsHelper.a(f1 * f1 * f1 * f1 * 3.141593F);
        float f3 = entityliving.ac;
        GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f1 * 14.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
    }

    private void f(float f) {
        if (!(this.j.i instanceof gs) || this.j.cameraActive || this.j.i.stunned != 0)
            return;
        gs entityplayer = (gs) this.j.i;
        float f1 = entityplayer.bj - entityplayer.bi;
        float f2 = -(entityplayer.bj + f1 * f);
        float f3 = entityplayer.h + (entityplayer.i - entityplayer.h) * f;
        float f4 = entityplayer.af + (entityplayer.ag - entityplayer.af) * f;
        GL11.glTranslatef(MathsHelper.a(f2 * 3.141593F) * f3 * 0.5F, -Math.abs(MathsHelper.b(f2 * 3.141593F) * f3), 0.0F);
        GL11.glRotatef(MathsHelper.a(f2 * 3.141593F) * f3 * 3.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(Math.abs(MathsHelper.b(f2 * 3.141593F - 0.2F) * f3) * 5.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
    }

    private void g(float f) {
        LivingEntity entityliving = this.j.i;
        float f1 = entityliving.bf - 1.62F;
        double d = entityliving.aJ + (entityliving.aM - entityliving.aJ) * f;
        double d1 = entityliving.aK + (entityliving.aN - entityliving.aK) * f - f1;
        double d2 = entityliving.aL + (entityliving.aO - entityliving.aL) * f;
        GL11.glRotatef(this.C + (this.B - this.C) * f, 0.0F, 0.0F, 1.0F);
        if (entityliving.N()) {
            f1 = (float) (f1 + 1.0D);
            GL11.glTranslatef(0.0F, 0.3F, 0.0F);
            if (!this.j.z.F) {
                int i = this.j.f.a(MathsHelper.b(entityliving.aM), MathsHelper.b(entityliving.aN), MathsHelper.b(entityliving.aO));
                if (i == Tile.T.bn) {
                    int j = this.j.f.e(MathsHelper.b(entityliving.aM), MathsHelper.b(entityliving.aN), MathsHelper.b(entityliving.aO));
                    int k = j & 0x3;
                    GL11.glRotatef((k * 90), 0.0F, 1.0F, 0.0F);
                }
                GL11.glRotatef(entityliving.aU + (entityliving.aS - entityliving.aU) * f + 180.0F, 0.0F, -1.0F, 0.0F);
                GL11.glRotatef(entityliving.aV + (entityliving.aT - entityliving.aV) * f, -1.0F, 0.0F, 0.0F);
            }
        } else if (this.j.z.A) {
            double d3 = (this.u + (this.t - this.u) * f);
            if (this.j.z.F) {
                float f2 = this.w + (this.v - this.w) * f;
                float f4 = this.y + (this.x - this.y) * f;
                GL11.glTranslatef(0.0F, 0.0F, (float) -d3);
                GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
            } else {
                float f3 = entityliving.aS;
                float f5 = entityliving.aT;
                double d4 = (-MathsHelper.a(f3 / 180.0F * 3.141593F) * MathsHelper.b(f5 / 180.0F * 3.141593F)) * d3;
                double d5 = (MathsHelper.b(f3 / 180.0F * 3.141593F) * MathsHelper.b(f5 / 180.0F * 3.141593F)) * d3;
                double d6 = -MathsHelper.a(f5 / 180.0F * 3.141593F) * d3;
                for (int l = 0; l < 8; l++) {
                    float f6 = ((l & 0x1) * 2 - 1);
                    float f7 = ((l >> 1 & 0x1) * 2 - 1);
                    float f8 = ((l >> 2 & 0x1) * 2 - 1);
                    f6 *= 0.1F;
                    f7 *= 0.1F;
                    f8 *= 0.1F;
                    vf movingobjectposition = this.j.f.a(bt.b(d + f6, d1 + f7, d2 + f8), bt.b(d - d4 + f6 + f8, d1 - d6 + f7, d2 - d5 + f8));
                    if (movingobjectposition != null) {
                        double d7 = movingobjectposition.f.c(bt.b(d, d1, d2));
                        if (d7 < d3)
                            d3 = d7;
                    }
                }
                GL11.glRotatef(entityliving.aT - f5, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(entityliving.aS - f3, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, (float) -d3);
                GL11.glRotatef(f3 - entityliving.aS, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f5 - entityliving.aT, 1.0F, 0.0F, 0.0F);
            }
        } else {
            if (this.j.cameraActive) {
                CutsceneCameraPoint p = this.j.cutsceneCamera.getCurrentPoint(f);
                GL11.glRotatef(p.rotPitch, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(p.rotYaw + 180.0F, 0.0F, 1.0F, 0.0F);
                return;
            }
            GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        }
        if (!this.j.z.F) {
            GL11.glRotatef(entityliving.aV + (entityliving.aT - entityliving.aV) * f, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(entityliving.aU + (entityliving.aS - entityliving.aU) * f + 180.0F, 0.0F, 1.0F, 0.0F);
        }
        GL11.glTranslatef(0.0F, f1, 0.0F);
        d = entityliving.aJ + (entityliving.aM - entityliving.aJ) * f;
        d1 = entityliving.aK + (entityliving.aN - entityliving.aK) * f - f1;
        d2 = entityliving.aL + (entityliving.aO - entityliving.aL) * f;
        this.D = this.j.g.a(d, d1, d2, f);
    }

    public void resetZoom() {
        this.E = 1.0D;
    }

    public float getFarPlane() {
        if (!this.j.z.autoFarClip)
            return (512 >> this.j.z.e);
        long avgTime = this.j.getAvgFrameTime();
        if (avgTime > 33333333L) {
            this.farClipAdjustment *= 0.99F;
        } else if (avgTime < 20000000L) {
            this.farClipAdjustment *= 1.01F;
        }
        this.farClipAdjustment = Math.max(Math.min(this.farClipAdjustment, 1.0F), 0.25F);
        return this.farClipAdjustment * (512 >> this.j.z.e);
    }

    private void a(float f, int i) {
        this.k = getFarPlane();
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        float f1 = 0.07F;
        if (this.j.z.g)
            GL11.glTranslatef(-(i * 2 - 1) * f1, 0.0F, 0.0F);
        if (this.E != 1.0D) {
            GL11.glTranslatef((float) this.F, (float) -this.G, 0.0F);
            GL11.glScaled(this.E, this.E, 1.0D);
            GLU.gluPerspective(d(f), this.j.d / this.j.e, 0.05F, this.k);
        } else {
            GLU.gluPerspective(d(f), this.j.d / this.j.e, 0.05F, this.k);
        }
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        if (this.j.z.g)
            GL11.glTranslatef((i * 2 - 1) * 0.1F, 0.0F, 0.0F);
        e(f);
        if (this.j.z.f)
            f(f);
        float f2 = this.j.h.C + (this.j.h.B - this.j.h.C) * f;
        if (f2 > 0.0F) {
            float f3 = 5.0F / (f2 * f2 + 5.0F) - f2 * 0.04F;
            f3 *= f3;
            GL11.glRotatef((this.l + f) * 20.0F, 0.0F, 1.0F, 1.0F);
            GL11.glScalef(1.0F / f3, 1.0F, 1.0F);
            GL11.glRotatef(-(this.l + f) * 20.0F, 0.0F, 1.0F, 1.0F);
        }
        g(f);
    }

    private void b(float f, int i) {
        GL11.glLoadIdentity();
        if (this.j.z.g)
            GL11.glTranslatef((i * 2 - 1) * 0.1F, 0.0F, 0.0F);
        GL11.glPushMatrix();
        e(f);
        if (this.j.z.f && !this.j.cameraActive)
            f(f);
        if (!this.j.z.A && !this.j.cameraActive && !this.j.i.N() && !this.j.z.z) {
            this.c.renderItemInFirstPerson(f, this.j.h.d(f), this.j.h.getSwingOffhandProgress(f));
            if (this.offHandItemRenderer.hasItem()) {
                GL11.glScalef(-1.0F, 1.0F, 1.0F);
                GL11.glFrontFace(2304);
                this.offHandItemRenderer.renderItemInFirstPerson(f, this.j.h.getSwingOffhandProgress(f), this.j.h.d(f));
                GL11.glFrontFace(2305);
            }
        }
        GL11.glPopMatrix();
        if (!this.j.z.A && !this.j.i.N()) {
            this.c.b(f);
            e(f);
        }
        if (this.j.z.f)
            f(f);
    }

    public void b(float f) {
        if (!Display.isActive()) {
            if (System.currentTimeMillis() - this.H > 500L)
                this.j.i();
        } else {
            this.H = System.currentTimeMillis();
        }
        if (this.j.N) {
            this.j.C.c();
            float f1 = this.j.z.c * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = this.j.C.a * f2;
            float f4 = this.j.C.b * f2;
            int l = 1;
            if (this.j.z.d)
                l = -1;
            if (this.j.z.E) {
                f3 = this.n.a(f3, 0.05F * f2);
                f4 = this.o.a(f4, 0.05F * f2);
            }
            this.j.h.d(f3, f4 * l);
        }
        if (this.j.w)
            return;
        a = this.j.z.g;
        qq scaledresolution = new qq(this.j.z, this.j.d, this.j.e);
        int i = scaledresolution.a();
        int j = scaledresolution.b();
        int k = Mouse.getX() * i / this.j.d;
        int i1 = j - Mouse.getY() * j / this.j.e - 1;
        char c = ';
        if (this.j.z.i == 1)
            c = 'x';
        if (this.j.z.i == 2)
            c = '(';
        if (this.j.f != null) {
            if (this.j.z.i == 0) {
                a(f, 0L);
            } else {
                a(f, this.I + (1000000000 / c));
            }
            if (this.j.z.i == 2) {
                long l1 = (this.I + (1000000000 / c) - System.nanoTime()) / 1000000L;
                if (l1 > 0L && l1 < 500L)
                    try {
                        Thread.sleep(l1);
                    } catch (InterruptedException interruptedexception) {
                        interruptedexception.printStackTrace();
                    }
            }
            this.I = System.nanoTime();
            if (!this.j.z.z || this.j.r != null)
                this.j.v.a(f, (this.j.r != null), k, i1);
        } else {
            GL11.glViewport(0, 0, this.j.d, this.j.e);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            b();
            if (this.j.z.i == 2) {
                long l2 = (this.I + (1000000000 / c) - System.nanoTime()) / 1000000L;
                if (l2 < 0L)
                    l2 += 10L;
                if (l2 > 0L && l2 < 500L)
                    try {
                        Thread.sleep(l2);
                    } catch (InterruptedException interruptedexception1) {
                        interruptedexception1.printStackTrace();
                    }
            }
            this.I = System.nanoTime();
        }
        if (this.j.r != null) {
            GL11.glClear(256);
            this.j.r.a(k, i1, f);
            if (this.j.r != null && this.j.r.h != null)
                this.j.r.h.a(f);
        } else if (this.j.y != null && this.j.y.a == jg.a) {
            if (this.j.f.a(this.j.y.b, this.j.y.c, this.j.y.d) == Blocks.store.bn) {
                TileEntityStore storeObj = (TileEntityStore) this.j.f.b(this.j.y.b, this.j.y.c, this.j.y.d);
                if (storeObj.buySupplyLeft != 0) {
                    this.j.storeGUI.setBuyItem(storeObj.buyItemID, storeObj.buyItemAmount, storeObj.buyItemDamage);
                    this.j.storeGUI.setSellItem(storeObj.sellItemID, storeObj.sellItemAmount, storeObj.sellItemDamage);
                    this.j.storeGUI.setSupplyLeft(storeObj.buySupplyLeft);
                    this.j.updateStoreGUI();
                    this.j.storeGUI.a(k, i1, f);
                }
            }
        }
    }

    public void a(float f, long l) {
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        if (this.j.cameraActive && this.j.cutsceneCamera.isEmpty())
            this.j.cameraActive = false;
        CutsceneCameraPoint p = this.j.cutsceneCamera.getCurrentPoint(f);
        this.j.i = this.j.cutsceneCameraEntity;
        this.j.i.aM = this.j.i.bl = this.j.i.aJ = p.posX;
        this.j.i.aN = this.j.i.bm = this.j.i.aK = p.posY;
        this.j.i.aO = this.j.i.bn = this.j.i.aL = p.posZ;
        this.j.i.aS = this.j.i.aU = p.rotYaw;
        this.j.i.aT = this.j.i.aV = p.rotPitch;
        this.j.i = (LivingEntity) this.j.h;
        if (this.j.h.stunned != 0) {
            this.j.h.bl = this.j.h.aJ = this.j.h.aM;
            this.j.h.bm = this.j.h.aK = this.j.h.aN;
            this.j.h.bn = this.j.h.aL = this.j.h.aO;
            this.j.h.bi = this.j.h.bj;
        }
        a(f);
        LivingEntity entityliving = this.j.i;
        WorldRenderer renderglobal = this.j.g;
        ParticleManager effectrenderer = this.j.j;
        double d = entityliving.bl + (entityliving.aM - entityliving.bl) * f;
        double d1 = entityliving.bm + (entityliving.aN - entityliving.bm) * f;
        double d2 = entityliving.bn + (entityliving.aO - entityliving.bn) * f;
        cl ichunkprovider = this.j.f.w();
        if (ichunkprovider instanceof ClientChunkCache) {
            ClientChunkCache chunkproviderloadorgenerate = (ClientChunkCache) ichunkprovider;
            int j = MathsHelper.d((int) d) >> 4;
            int k = MathsHelper.d((int) d2) >> 4;
            chunkproviderloadorgenerate.d(j, k);
        }
        for (int i = 0; i < 2; i++) {
            if (this.j.z.g) {
                b = i;
                if (b == 0) {
                    GL11.glColorMask(false, true, true, false);
                } else {
                    GL11.glColorMask(true, false, false, false);
                }
            }
            GL11.glViewport(0, 0, this.j.d, this.j.e);
            h(f);
            GL11.glClear(16640);
            GL11.glEnable(2884);
            a(f, i);
            w.a();
            if (this.j.z.e < 3) {
                a(-1, f);
                renderglobal.a(f);
            }
            GL11.glEnable(2912);
            a(1, f);
            if (this.j.z.k)
                GL11.glShadeModel(7425);
            sr frustrum = new sr();
            frustrum.a(d, d1, d2);
            this.j.g.a((yn) frustrum, f);
            if (i == 0)
                while (!this.j.g.a(entityliving, false) && l != 0L) {
                    long l1 = l - System.nanoTime();
                    if (l1 < 0L || l1 > 1000000000L)
                        break;
                }
            GL11.glPushMatrix();
            if (DebugMode.editMode && DebugMode.mapEditing != null)
                DebugMode.mapEditing.updateCursor(entityliving, d(f), f);
            a(0, f);
            GL11.glEnable(2912);
            GL11.glBindTexture(3553, this.j.p.b("/terrain.png"));
            u.a();
            renderglobal.a(entityliving, 0, f);
            GL11.glShadeModel(7424);
            u.b();
            renderglobal.a(entityliving.e(f), (yn) frustrum, f);
            effectrenderer.b(entityliving, f);
            u.a();
            a(0, f);
            effectrenderer.a(entityliving, f);
            if (this.j.y != null && entityliving.a(ln.g) && entityliving instanceof gs) {
                gs entityplayer = (gs) entityliving;
                GL11.glDisable(3008);
                renderglobal.a(entityplayer, this.j.y, 0, entityplayer.c.b(), f);
                renderglobal.b(entityplayer, this.j.y, 0, entityplayer.c.b(), f);
                GL11.glEnable(3008);
            }
            GL11.glBlendFunc(770, 771);
            a(0, f);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            GL11.glBindTexture(3553, this.j.p.b("/terrain.png"));
            if (this.j.z.j) {
                if (this.j.z.k)
                    GL11.glShadeModel(7425);
                GL11.glColorMask(false, false, false, false);
                int i1 = renderglobal.a(entityliving, 1, f);
                if (this.j.z.g) {
                    if (b == 0) {
                        GL11.glColorMask(false, true, true, true);
                    } else {
                        GL11.glColorMask(true, false, false, true);
                    }
                } else {
                    GL11.glColorMask(true, true, true, true);
                }
                if (i1 > 0)
                    renderglobal.a(1, f);
                GL11.glShadeModel(7424);
            } else {
                renderglobal.a(entityliving, 1, f);
            }
            if (DebugMode.editMode && DebugMode.mapEditing != null)
                DebugMode.mapEditing.render(f);
            iz curItem = this.j.h.c.b();
            if (curItem != null && curItem.c == Items.paste.bf) {
                if (DebugMode.mapEditing == null) {
                    DebugMode.mapEditing = new MapEditing(this.j, this.j.f);
                } else {
                    DebugMode.mapEditing.updateWorld(this.j.f);
                }
                DebugMode.mapEditing.renderSelection(f);
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            if (!DebugMode.editMode && this.E == 1.0D && entityliving instanceof gs && this.j.y != null && !entityliving.a(ln.g)) {
                gs entityplayer1 = (gs) entityliving;
                GL11.glDisable(3008);
                renderglobal.a(entityplayer1, this.j.y, 0, entityplayer1.c.b(), f);
                renderglobal.b(entityplayer1, this.j.y, 0, entityplayer1.c.b(), f);
                GL11.glEnable(3008);
            }
            GL11.glDisable(3008);
            renderglobal.drawCursorSelection(entityliving, ((gs) entityliving).c.b(), f);
            if (DebugMode.active && this.j.activeCutsceneCamera != null)
                this.j.activeCutsceneCamera.drawLines(entityliving, f);
            if (DebugMode.active || DebugMode.renderPaths)
                for (Object obj : this.j.f.b) {
                    Entity e = (Entity) obj;
                    renderglobal.drawEntityPath(e, entityliving, f);
                }
            if (DebugMode.active || DebugMode.renderFov)
                for (Object obj : this.j.f.b) {
                    Entity e = (Entity) obj;
                    if (e instanceof LivingEntity)
                        renderglobal.drawEntityFOV((LivingEntity) e, entityliving, f);
                }
            GL11.glEnable(3008);
            GL11.glDisable(2912);
            if (this.m == null) ;
            a(0, f);
            GL11.glEnable(2912);
            renderglobal.b(f);
            GL11.glDisable(2912);
            GL11.glPopMatrix();
            c(f);
            a(1, f);
            if (this.E == 1.0D) {
                GL11.glClear(256);
                b(f, i);
            }
            if (!this.j.z.g)
                return;
        }
        GL11.glColorMask(true, true, true, false);
    }

    private void c() {
        float f = this.j.f.g(1.0F);
        if (!this.j.z.j)
            f /= 2.0F;
        if (f == 0.0F)
            return;
        this.J.setSeed(this.l * 312987231L);
        LivingEntity entityliving = this.j.i;
        Level world = this.j.f;
        int i = MathsHelper.b(entityliving.aM);
        int j = MathsHelper.b(entityliving.aN);
        int k = MathsHelper.b(entityliving.aO);
        byte byte0 = 10;
        double d = 0.0D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        int l = 0;
        for (int i1 = 0; i1 < (int) (100.0F * f * f); i1++) {
            int j1 = i + this.J.nextInt(byte0) - this.J.nextInt(byte0);
            int k1 = k + this.J.nextInt(byte0) - this.J.nextInt(byte0);
            int l1 = world.e(j1, k1);
            int i2 = world.a(j1, l1 - 1, k1);
            if (l1 <= j + byte0 && l1 >= j - byte0 && world.getTemperatureValue(j1, k1) >= 0.5D) {
                float f1 = this.J.nextFloat();
                float f2 = this.J.nextFloat();
                if (i2 > 0)
                    if ((Tile.m[i2]).bA == ln.h) {
                        this.j.j.a((xw) new xn(world, (j1 + f1), (l1 + 0.1F) - (Tile.m[i2]).bt, (k1 + f2), 0.0D, 0.0D, 0.0D));
                    } else {
                        if (this.J.nextInt(++l) == 0) {
                            d = (j1 + f1);
                            d1 = (l1 + 0.1F) - (Tile.m[i2]).bt;
                            d2 = (k1 + f2);
                        }
                        this.j.j.a((xw) new xd(world, (j1 + f1), (l1 + 0.1F) - (Tile.m[i2]).bt, (k1 + f2)));
                    }
            }
        }
        if (l > 0 && this.J.nextInt(3) < this.K++) {
            this.K = 0;
            if (d1 > entityliving.aN + 1.0D && world.e(MathsHelper.b(entityliving.aM), MathsHelper.b(entityliving.aO)) > MathsHelper.b(entityliving.aN)) {
                this.j.f.a(d, d1, d2, "ambient.weather.rain", 0.1F, 0.5F);
            } else {
                this.j.f.a(d, d1, d2, "ambient.weather.rain", 0.2F, 1.0F);
            }
        }
    }

    protected void c(float f) {
        float f1 = this.j.f.g(f);
        if (f1 <= 0.0F)
            return;
        LivingEntity entityliving = this.j.i;
        Level world = this.j.f;
        int i = MathsHelper.b(entityliving.aM);
        int j = MathsHelper.b(entityliving.aN);
        int k = MathsHelper.b(entityliving.aO);
        Tessellator tessellator = Tessellator.a;
        GL11.glDisable(2884);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glAlphaFunc(516, 0.01F);
        GL11.glBindTexture(3553, this.j.p.b("/environment/snow.png"));
        double d = entityliving.bl + (entityliving.aM - entityliving.bl) * f;
        double d1 = entityliving.bm + (entityliving.aN - entityliving.bm) * f;
        double d2 = entityliving.bn + (entityliving.aO - entityliving.bn) * f;
        int l = MathsHelper.b(d1);
        int i1 = 5;
        if (this.j.z.j)
            i1 = 10;
        int j1 = 0;
        for (int k1 = i - i1; k1 <= i + i1; k1++) {
            for (int i2 = k - i1; i2 <= k + i1; i2++) {
                if (world.getTemperatureValue(k1, i2) < 0.5D) {
                    int k2 = world.e(k1, i2);
                    if (k2 < 0)
                        k2 = 0;
                    int i3 = k2;
                    if (i3 < l)
                        i3 = l;
                    int k3 = j - i1;
                    int i4 = j + i1;
                    if (k3 < k2)
                        k3 = k2;
                    if (i4 < k2)
                        i4 = k2;
                    float f3 = 1.0F;
                    if (k3 != i4) {
                        this.J.setSeed((k1 * k1 * 3121 + k1 * 45238971 + i2 * i2 * 418711 + i2 * 13761));
                        float f5 = this.l + f;
                        float f6 = ((this.l & 0x1FF) + f) / 512.0F;
                        float f7 = this.J.nextFloat() + f5 * 0.01F * (float) this.J.nextGaussian();
                        float f8 = this.J.nextFloat() + f5 * (float) this.J.nextGaussian() * 0.001F;
                        double d5 = (k1 + 0.5F) - entityliving.aM;
                        double d6 = (i2 + 0.5F) - entityliving.aO;
                        float f11 = MathsHelper.a(d5 * d5 + d6 * d6) / i1;
                        tessellator.b();
                        float f12 = world.c(k1, i3, i2);
                        GL11.glColor4f(f12, f12, f12, ((1.0F - f11 * f11) * 0.3F + 0.5F) * f1);
                        tessellator.b(-d * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                        tessellator.a((k1 + 0), k3, i2 + 0.5D, (0.0F * f3 + f7), (k3 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.a((k1 + 1), k3, i2 + 0.5D, (1.0F * f3 + f7), (k3 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.a((k1 + 1), i4, i2 + 0.5D, (1.0F * f3 + f7), (i4 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.a((k1 + 0), i4, i2 + 0.5D, (0.0F * f3 + f7), (i4 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.a(k1 + 0.5D, k3, (i2 + 0), (0.0F * f3 + f7), (k3 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.a(k1 + 0.5D, k3, (i2 + 1), (1.0F * f3 + f7), (k3 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.a(k1 + 0.5D, i4, (i2 + 1), (1.0F * f3 + f7), (i4 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.a(k1 + 0.5D, i4, (i2 + 0), (0.0F * f3 + f7), (i4 * f3 / 4.0F + f6 * f3 + f8));
                        tessellator.b(0.0D, 0.0D, 0.0D);
                        tessellator.a();
                    }
                }
            }
        }
        GL11.glBindTexture(3553, this.j.p.b("/environment/rain.png"));
        if (this.j.z.j)
            i1 = 10;
        j1 = 0;
        for (int l1 = i - i1; l1 <= i + i1; l1++) {
            for (int j2 = k - i1; j2 <= k + i1; j2++) {
                if (world.getTemperatureValue(l1, j2) >= 0.5D) {
                    int l2 = world.e(l1, j2);
                    int j3 = j - i1;
                    int l3 = j + i1;
                    if (j3 < l2)
                        j3 = l2;
                    if (l3 < l2)
                        l3 = l2;
                    float f2 = 1.0F;
                    if (j3 != l3) {
                        this.J.setSeed((l1 * l1 * 3121 + l1 * 45238971 + j2 * j2 * 418711 + j2 * 13761));
                        float f4 = ((this.l + l1 * l1 * 3121 + l1 * 45238971 + j2 * j2 * 418711 + j2 * 13761 & 0x1F) + f) / 32.0F * (3.0F + this.J.nextFloat());
                        double d3 = (l1 + 0.5F) - entityliving.aM;
                        double d4 = (j2 + 0.5F) - entityliving.aO;
                        float f9 = MathsHelper.a(d3 * d3 + d4 * d4) / i1;
                        tessellator.b();
                        float f10 = world.c(l1, 128, j2) * 0.85F + 0.15F;
                        GL11.glColor4f(f10, f10, f10, ((1.0F - f9 * f9) * 0.5F + 0.5F) * f1);
                        tessellator.b(-d * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                        tessellator.a((l1 + 0), j3, j2 + 0.5D, (0.0F * f2), (j3 * f2 / 4.0F + f4 * f2));
                        tessellator.a((l1 + 1), j3, j2 + 0.5D, (1.0F * f2), (j3 * f2 / 4.0F + f4 * f2));
                        tessellator.a((l1 + 1), l3, j2 + 0.5D, (1.0F * f2), (l3 * f2 / 4.0F + f4 * f2));
                        tessellator.a((l1 + 0), l3, j2 + 0.5D, (0.0F * f2), (l3 * f2 / 4.0F + f4 * f2));
                        tessellator.a(l1 + 0.5D, j3, (j2 + 0), (0.0F * f2), (j3 * f2 / 4.0F + f4 * f2));
                        tessellator.a(l1 + 0.5D, j3, (j2 + 1), (1.0F * f2), (j3 * f2 / 4.0F + f4 * f2));
                        tessellator.a(l1 + 0.5D, l3, (j2 + 1), (1.0F * f2), (l3 * f2 / 4.0F + f4 * f2));
                        tessellator.a(l1 + 0.5D, l3, (j2 + 0), (0.0F * f2), (l3 * f2 / 4.0F + f4 * f2));
                        tessellator.b(0.0D, 0.0D, 0.0D);
                        tessellator.a();
                    }
                }
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glAlphaFunc(516, 0.1F);
    }

    public void b() {
        qq scaledresolution = new qq(this.j.z, this.j.d, this.j.e);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.a, scaledresolution.b, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
    }

    private void h(float f) {
        Level world = this.j.f;
        LivingEntity entityliving = this.j.i;
        float f1 = 1.0F / (5 - this.j.z.e);
        f1 = 1.0F - (float) Math.pow(f1, 0.25D);
        bt vec3d = world.a((Entity) this.j.i, f);
        float f2 = (float) vec3d.a;
        float f3 = (float) vec3d.b;
        float f4 = (float) vec3d.c;
        bt vec3d1 = world.d(f);
        this.g = (float) vec3d1.a;
        this.h = (float) vec3d1.b;
        this.i = (float) vec3d1.c;
        this.g += (f2 - this.g) * f1;
        this.h += (f3 - this.h) * f1;
        this.i += (f4 - this.i) * f1;
        float f5 = world.g(f);
        if (f5 > 0.0F) {
            float f6 = 1.0F - f5 * 0.5F;
            float f8 = 1.0F - f5 * 0.4F;
            this.g *= f6;
            this.h *= f6;
            this.i *= f8;
        }
        float f7 = world.f(f);
        if (f7 > 0.0F) {
            float f9 = 1.0F - f7 * 0.5F;
            this.g *= f9;
            this.h *= f9;
            this.i *= f9;
        }
        if (this.D) {
            bt vec3d2 = world.c(f);
            this.g = (float) vec3d2.a;
            this.h = (float) vec3d2.b;
            this.i = (float) vec3d2.c;
        } else if (entityliving.a(ln.g)) {
            this.g = 0.02F;
            this.h = 0.02F;
            this.i = 0.2F;
        } else if (entityliving.a(ln.h)) {
            this.g = 0.6F;
            this.h = 0.1F;
            this.i = 0.0F;
        }
        float f10 = this.L + (this.M - this.L) * f;
        this.g *= f10;
        this.h *= f10;
        this.i *= f10;
        if (this.j.z.g) {
            float f11 = (this.g * 30.0F + this.h * 59.0F + this.i * 11.0F) / 100.0F;
            float f12 = (this.g * 30.0F + this.h * 70.0F) / 100.0F;
            float f13 = (this.g * 30.0F + this.i * 70.0F) / 100.0F;
            this.g = f11;
            this.h = f12;
            this.i = f13;
        }
        GL11.glClearColor(this.g, this.h, this.i, 0.0F);
    }

    private void a(int i, float f) {
        LivingEntity entityliving = this.j.i;
        GL11.glFog(2918, a(this.g, this.h, this.i, 1.0F));
        GL11.glNormal3f(0.0F, -1.0F, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.D) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1F);
            float f1 = 1.0F;
            float f4 = 1.0F;
            float f7 = 1.0F;
            if (this.j.z.g) {
                float f10 = (f1 * 30.0F + f4 * 59.0F + f7 * 11.0F) / 100.0F;
                float f13 = (f1 * 30.0F + f4 * 70.0F) / 100.0F;
                float f16 = (f1 * 30.0F + f7 * 70.0F) / 100.0F;
                f1 = f10;
                f4 = f13;
                f7 = f16;
            }
        } else if (entityliving.a(ln.g)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.1F);
            float f2 = 0.4F;
            float f5 = 0.4F;
            float f8 = 0.9F;
            if (this.j.z.g) {
                float f11 = (f2 * 30.0F + f5 * 59.0F + f8 * 11.0F) / 100.0F;
                float f14 = (f2 * 30.0F + f5 * 70.0F) / 100.0F;
                float f17 = (f2 * 30.0F + f8 * 70.0F) / 100.0F;
                f2 = f11;
                f5 = f14;
                f8 = f17;
            }
        } else if (entityliving.a(ln.h)) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 2.0F);
            float f3 = 0.4F;
            float f6 = 0.3F;
            float f9 = 0.3F;
            if (this.j.z.g) {
                float f12 = (f3 * 30.0F + f6 * 59.0F + f9 * 11.0F) / 100.0F;
                float f15 = (f3 * 30.0F + f6 * 70.0F) / 100.0F;
                float f18 = (f3 * 30.0F + f9 * 70.0F) / 100.0F;
                f3 = f12;
                f6 = f15;
                f9 = f18;
            }
        } else {
            Level world = this.j.f;
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, world.getFogStart(this.k * 0.25F, f));
            GL11.glFogf(2916, world.getFogEnd(this.k, f));
            if (i < 0) {
                GL11.glFogf(2915, world.getFogStart(0.0F, f));
                GL11.glFogf(2916, world.getFogEnd(0.8F * this.k, f));
            }
            if ((GLContext.getCapabilities()).GL_NV_fog_distance)
                GL11.glFogi(34138, 34139);
            if (this.j.f.t.c)
                GL11.glFogf(2915, world.getFogStart(0.0F, f));
        }
        GL11.glEnable(2903);
        GL11.glColorMaterial(1028, 4608);
    }

    private FloatBuffer a(float f, float f1, float f2, float f3) {
        this.f.clear();
        this.f.put(f).put(f1).put(f2).put(f3);
        this.f.flip();
        return this.f;
    }
}
