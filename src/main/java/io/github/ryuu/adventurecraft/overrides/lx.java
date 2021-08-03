package io.github.ryuu.adventurecraft.overrides;

import java.util.List;

import net.minecraft.client.Minecraft;

public class lx extends sn {
    private int d;

    private int e;

    private int f;

    private int g;

    private boolean h;

    public int a;

    public gs b;

    private int i;

    private int j;

    private int k;

    public sn c;

    private int l;

    private double m;

    private double n;

    private double o;

    private double p;

    private double q;

    private double r;

    private double s;

    private double t;

    public lx(fd world) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = false;
        this.a = 0;
        this.j = 0;
        this.k = 0;
        this.c = null;
        b(0.25F, 0.25F);
        this.bM = true;
    }

    public lx(fd world, double d, double d1, double d2) {
        this(world);
        e(d, d1, d2);
        this.bM = true;
    }

    public lx(fd world, gs entityplayer) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = false;
        this.a = 0;
        this.j = 0;
        this.k = 0;
        this.c = null;
        this.bM = true;
        this.b = entityplayer;
        this.b.D = this;
        b(0.25F, 0.25F);
        c(entityplayer.aM, entityplayer.aN + 1.62D - entityplayer.bf, entityplayer.aO, entityplayer.aS, entityplayer.aT);
        this.aM -= (in.b(this.aS / 180.0F * 3.141593F) * 0.16F);
        this.aN -= 0.10000000149011612D;
        this.aO -= (in.a(this.aS / 180.0F * 3.141593F) * 0.16F);
        e(this.aM, this.aN, this.aO);
        this.bf = 0.0F;
        float f = 0.4F;
        this.aP = (-in.a(this.aS / 180.0F * 3.141593F) * in.b(this.aT / 180.0F * 3.141593F) * f);
        this.aR = (in.b(this.aS / 180.0F * 3.141593F) * in.b(this.aT / 180.0F * 3.141593F) * f);
        this.aQ = (-in.a(this.aT / 180.0F * 3.141593F) * f);
        a(this.aP, this.aQ, this.aR, 1.5F, 1.0F);
    }

    protected void b() {
    }

    public boolean a(double d) {
        double d1 = this.aW.c() * 4.0D;
        d1 *= 64.0D;
        return (d < d1 * d1);
    }

    public void a(double d, double d1, double d2, float f, float f1) {
        float f2 = in.a(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += this.bs.nextGaussian() * 0.007499999832361937D * f1;
        d1 += this.bs.nextGaussian() * 0.007499999832361937D * f1;
        d2 += this.bs.nextGaussian() * 0.007499999832361937D * f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        this.aP = d;
        this.aQ = d1;
        this.aR = d2;
        float f3 = in.a(d * d + d2 * d2);
        this.aU = this.aS = (float) (Math.atan2(d, d2) * 180.0D / 3.1415927410125732D);
        this.aV = this.aT = (float) (Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
        this.i = 0;
    }

    public void a(double d, double d1, double d2, float f, float f1, int i) {
        this.m = d;
        this.n = d1;
        this.o = d2;
        this.p = f;
        this.q = f1;
        this.l = i;
        this.aP = this.r;
        this.aQ = this.s;
        this.aR = this.t;
    }

    public void a(double d, double d1, double d2) {
        this.r = this.aP = d;
        this.s = this.aQ = d1;
        this.t = this.aR = d2;
    }

    public void w_() {
        if (this.b == null) {
            this.b = (gs) Minecraft.minecraftInstance.h;
            Minecraft.minecraftInstance.h.D = this;
        }
        super.w_();
        if (this.l > 0) {
            double d = this.aM + (this.m - this.aM) / this.l;
            double d1 = this.aN + (this.n - this.aN) / this.l;
            double d2 = this.aO + (this.o - this.aO) / this.l;
            double d4;
            for (d4 = this.p - this.aS; d4 < -180.0D; d4 += 360.0D) ;
            for (; d4 >= 180.0D; d4 -= 360.0D) ;
            this.aS = (float) (this.aS + d4 / this.l);
            this.aT = (float) (this.aT + (this.q - this.aT) / this.l);
            this.l--;
            e(d, d1, d2);
            c(this.aS, this.aT);
            return;
        }
        if (!this.aI.B) {
            iz itemstack = this.b.G();
            if (this.b.be || !this.b.W() || itemstack == null || itemstack.a() != gm.aP || g((sn) this.b) > 1024.0D) {
                K();
                this.b.D = null;
                return;
            }
            if (this.c != null)
                if (this.c.be) {
                    this.c = null;
                } else {
                    this.aM = this.c.aM;
                    this.aN = this.c.aW.b + this.c.bh * 0.8D;
                    this.aO = this.c.aO;
                    return;
                }
        }
        if (this.a > 0)
            this.a--;
        if (this.h) {
            int i = this.aI.a(this.d, this.e, this.f);
            if (i != this.g) {
                this.h = false;
                this.aP *= (this.bs.nextFloat() * 0.2F);
                this.aQ *= (this.bs.nextFloat() * 0.2F);
                this.aR *= (this.bs.nextFloat() * 0.2F);
                this.i = 0;
                this.j = 0;
            } else {
                this.i++;
                if (this.i == 1200)
                    K();
                return;
            }
        } else {
            this.j++;
        }
        bt vec3d = bt.b(this.aM, this.aN, this.aO);
        bt vec3d1 = bt.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        vf movingobjectposition = this.aI.a(vec3d, vec3d1);
        vec3d = bt.b(this.aM, this.aN, this.aO);
        vec3d1 = bt.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        if (movingobjectposition != null)
            vec3d1 = bt.b(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        sn entity = null;
        List<sn> list = this.aI.b(this, this.aW.a(this.aP, this.aQ, this.aR).b(1.0D, 1.0D, 1.0D));
        double d3 = 0.0D;
        for (int j = 0; j < list.size(); j++) {
            sn entity1 = list.get(j);
            if (entity1.h_() && (entity1 != this.b || this.j >= 5)) {
                float f2 = 0.3F;
                eq axisalignedbb = entity1.aW.b(f2, f2, f2);
                vf movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
                if (movingobjectposition1 != null) {
                    double d6 = vec3d.c(movingobjectposition1.f);
                    if (d6 < d3 || d3 == 0.0D) {
                        entity = entity1;
                        d3 = d6;
                    }
                }
            }
        }
        if (entity != null)
            movingobjectposition = new vf(entity);
        if (movingobjectposition != null)
            if (movingobjectposition.g != null) {
                if (movingobjectposition.g.a((sn) this.b, 0))
                    this.c = movingobjectposition.g;
            } else {
                this.h = true;
            }
        if (this.h)
            return;
        b(this.aP, this.aQ, this.aR);
        float f = in.a(this.aP * this.aP + this.aR * this.aR);
        this.aS = (float) (Math.atan2(this.aP, this.aR) * 180.0D / 3.1415927410125732D);
        for (this.aT = (float) (Math.atan2(this.aQ, f) * 180.0D / 3.1415927410125732D); this.aT - this.aV < -180.0F; this.aV -= 360.0F)
            ;
        for (; this.aT - this.aV >= 180.0F; this.aV += 360.0F) ;
        for (; this.aS - this.aU < -180.0F; this.aU -= 360.0F) ;
        for (; this.aS - this.aU >= 180.0F; this.aU += 360.0F) ;
        this.aT = this.aV + (this.aT - this.aV) * 0.2F;
        this.aS = this.aU + (this.aS - this.aU) * 0.2F;
        float f1 = 0.92F;
        if (this.aX || this.aY)
            f1 = 0.5F;
        int k = 5;
        double d5 = 0.0D;
        for (int l = 0; l < k; l++) {
            double d8 = this.aW.b + (this.aW.e - this.aW.b) * (l + 0) / k - 0.125D + 0.125D;
            double d9 = this.aW.b + (this.aW.e - this.aW.b) * (l + 1) / k - 0.125D + 0.125D;
            eq axisalignedbb1 = eq.b(this.aW.a, d8, this.aW.c, this.aW.d, d9, this.aW.f);
            if (this.aI.b(axisalignedbb1, ln.g))
                d5 += 1.0D / k;
        }
        if (d5 > 0.0D)
            if (this.k > 0) {
                this.k--;
            } else {
                char c = ';
                if (this.aI.t(in.b(this.aM), in.b(this.aN) + 1, in.b(this.aO)))
                    c = ';
                if (this.bs.nextInt(c) == 0) {
                    this.k = this.bs.nextInt(30) + 10;
                    this.aQ -= 0.20000000298023224D;
                    this.aI.a(this, "random.splash", 0.25F, 1.0F + (this.bs.nextFloat() - this.bs.nextFloat()) * 0.4F);
                    float f3 = in.b(this.aW.b);
                    for (int i1 = 0; i1 < 1.0F + this.bg * 20.0F; i1++) {
                        float f4 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                        float f6 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                        this.aI.a("bubble", this.aM + f4, (f3 + 1.0F), this.aO + f6, this.aP, this.aQ - (this.bs.nextFloat() * 0.2F), this.aR);
                    }
                    for (int j1 = 0; j1 < 1.0F + this.bg * 20.0F; j1++) {
                        float f5 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                        float f7 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                        this.aI.a("splash", this.aM + f5, (f3 + 1.0F), this.aO + f7, this.aP, this.aQ, this.aR);
                    }
                }
            }
        if (this.k > 0)
            this.aQ -= (this.bs.nextFloat() * this.bs.nextFloat() * this.bs.nextFloat()) * 0.2D;
        double d7 = d5 * 2.0D - 1.0D;
        this.aQ += 0.03999999910593033D * d7;
        if (d5 > 0.0D) {
            f1 = (float) (f1 * 0.9D);
            this.aQ *= 0.8D;
        }
        this.aP *= f1;
        this.aQ *= f1;
        this.aR *= f1;
        e(this.aM, this.aN, this.aO);
    }

    public void b(nu nbttagcompound) {
        nbttagcompound.a("xTile", (short) this.d);
        nbttagcompound.a("yTile", (short) this.e);
        nbttagcompound.a("zTile", (short) this.f);
        nbttagcompound.a("inTile", (byte) this.g);
        nbttagcompound.a("shake", (byte) this.a);
        nbttagcompound.a("inGround", (byte) (this.h ? 1 : 0));
    }

    public void a(nu nbttagcompound) {
        this.d = nbttagcompound.d("xTile");
        this.e = nbttagcompound.d("yTile");
        this.f = nbttagcompound.d("zTile");
        this.g = nbttagcompound.c("inTile") & 0xFF;
        this.a = nbttagcompound.c("shake") & 0xFF;
        this.h = (nbttagcompound.c("inGround") == 1);
    }

    public float x_() {
        return 0.0F;
    }

    public int k() {
        byte byte0 = 0;
        if (this.c != null) {
            double d = this.b.aM - this.aM;
            double d2 = this.b.aN - this.aN;
            double d4 = this.b.aO - this.aO;
            double d6 = in.a(d * d + d2 * d2 + d4 * d4);
            double d8 = 0.1D;
            this.c.aP += d * d8;
            this.c.aQ += d2 * d8 + in.a(d6) * 0.08D;
            this.c.aR += d4 * d8;
            byte0 = 3;
        } else if (this.k > 0) {
            hl entityitem = new hl(this.aI, this.aM, this.aN, this.aO, new iz(gm.aS));
            double d1 = this.b.aM - this.aM;
            double d3 = this.b.aN - this.aN;
            double d5 = this.b.aO - this.aO;
            double d7 = in.a(d1 * d1 + d3 * d3 + d5 * d5);
            double d9 = 0.1D;
            entityitem.aP = d1 * d9;
            entityitem.aQ = d3 * d9 + in.a(d7) * 0.08D;
            entityitem.aR = d5 * d9;
            this.aI.b((sn) entityitem);
            this.b.a(jl.B, 1);
            byte0 = 1;
        }
        if (this.h)
            byte0 = 2;
        K();
        this.b.D = null;
        return byte0;
    }
}
