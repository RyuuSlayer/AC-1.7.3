package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.mixin.Level;

import java.util.List;

public class cf extends sn {
    private int f;

    private int g;

    private int h;

    private int i;

    private boolean j;

    public int a;

    public ls b;

    private int k;

    private int l;

    public double c;

    public double d;

    public double e;

    private final float radius;

    public cf(Level world) {
        super(world);
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = 0;
        this.j = false;
        this.a = 0;
        this.l = 0;
        b(1.0F, 1.0F);
        this.collidesWithClipBlocks = false;
        this.radius = 1.0F;
    }

    protected void b() {
    }

    public boolean a(double d) {
        double d1 = this.aW.c() * 4.0D;
        d1 *= 64.0D;
        return (d < d1 * d1);
    }

    public cf(Level world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world);
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = 0;
        this.j = false;
        this.a = 0;
        this.l = 0;
        b(1.0F, 1.0F);
        c(d, d1, d2, this.aS, this.aT);
        e(d, d1, d2);
        double d6 = in.a(d3 * d3 + d4 * d4 + d5 * d5);
        this.c = d3 / d6 * 0.1D;
        this.d = d4 / d6 * 0.1D;
        this.e = d5 / d6 * 0.1D;
        this.radius = 1.0F;
    }

    public cf(Level world, ls entityliving, double d, double d1, double d2) {
        super(world);
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = 0;
        this.j = false;
        this.a = 0;
        this.l = 0;
        this.b = entityliving;
        b(1.0F, 1.0F);
        c(entityliving.aM, entityliving.aN, entityliving.aO, entityliving.aS, entityliving.aT);
        e(this.aM, this.aN, this.aO);
        this.bf = 0.0F;
        this.aP = this.aQ = this.aR = 0.0D;
        d += this.bs.nextGaussian() * 0.4D;
        d1 += this.bs.nextGaussian() * 0.4D;
        d2 += this.bs.nextGaussian() * 0.4D;
        double d3 = in.a(d * d + d1 * d1 + d2 * d2);
        this.c = d / d3 * 0.1D;
        this.d = d1 / d3 * 0.1D;
        this.e = d2 / d3 * 0.1D;
        this.radius = 1.0F;
    }

    public cf(Level world, ls entityliving, double d, double d1, double d2, float r) {
        super(world);
        this.f = -1;
        this.g = -1;
        this.h = -1;
        this.i = 0;
        this.j = false;
        this.a = 0;
        this.l = 0;
        this.b = entityliving;
        b(1.0F, 1.0F);
        c(entityliving.aM, entityliving.aN, entityliving.aO, entityliving.aS, entityliving.aT);
        e(this.aM, this.aN, this.aO);
        this.bf = 0.0F;
        this.aP = this.aQ = this.aR = 0.0D;
        d += this.bs.nextGaussian() * 0.4D;
        d1 += this.bs.nextGaussian() * 0.4D;
        d2 += this.bs.nextGaussian() * 0.4D;
        double d3 = in.a(d * d + d1 * d1 + d2 * d2);
        this.c = d / d3 * 0.1D;
        this.d = d1 / d3 * 0.1D;
        this.e = d2 / d3 * 0.1D;
        this.radius = r;
    }

    public void w_() {
        super.w_();
        this.bv = 10;
        if (this.a > 0)
            this.a--;
        if (this.j) {
            int i = this.aI.a(this.f, this.g, this.h);
            if (i != this.i) {
                this.j = false;
                this.aP *= (this.bs.nextFloat() * 0.2F);
                this.aQ *= (this.bs.nextFloat() * 0.2F);
                this.aR *= (this.bs.nextFloat() * 0.2F);
                this.k = 0;
                this.l = 0;
            } else {
                this.k++;
                if (this.k == 1200)
                    K();
                return;
            }
        } else {
            this.l++;
        }
        bt vec3d = bt.b(this.aM, this.aN, this.aO);
        bt vec3d1 = bt.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        vf movingobjectposition = this.aI.rayTraceBlocks2(vec3d, vec3d1, false, true, false);
        vec3d = bt.b(this.aM, this.aN, this.aO);
        vec3d1 = bt.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        if (movingobjectposition != null)
            vec3d1 = bt.b(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        sn entity = null;
        List<sn> list = this.aI.b(this, this.aW.a(this.aP, this.aQ, this.aR).b(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (int j = 0; j < list.size(); j++) {
            sn entity1 = list.get(j);
            if (entity1.h_() && (entity1 != this.b || this.l >= 25)) {
                float f2 = 0.3F;
                eq axisalignedbb = entity1.aW.b(f2, f2, f2);
                vf movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
                if (movingobjectposition1 != null) {
                    double d1 = vec3d.c(movingobjectposition1.f);
                    if (d1 < d || d == 0.0D) {
                        entity = entity1;
                        d = d1;
                    }
                }
            }
        }
        if (entity != null)
            movingobjectposition = new vf(entity);
        if (movingobjectposition != null) {
            if (!this.aI.B) {
                if (movingobjectposition.g != null)
                    if (!movingobjectposition.g.a(this.b, 0)) ;
                this.aI.a((sn) null, this.aM, this.aN, this.aO, this.radius, true);
            }
            K();
        }
        this.aM += this.aP;
        this.aN += this.aQ;
        this.aO += this.aR;
        float f = in.a(this.aP * this.aP + this.aR * this.aR);
        this.aS = (float) (Math.atan2(this.aP, this.aR) * 180.0D / 3.1415927410125732D);
        for (this.aT = (float) (Math.atan2(this.aQ, f) * 180.0D / 3.1415927410125732D); this.aT - this.aV < -180.0F; this.aV -= 360.0F)
            ;
        for (; this.aT - this.aV >= 180.0F; this.aV += 360.0F) ;
        for (; this.aS - this.aU < -180.0F; this.aU -= 360.0F) ;
        for (; this.aS - this.aU >= 180.0F; this.aU += 360.0F) ;
        this.aT = this.aV + (this.aT - this.aV) * 0.2F;
        this.aS = this.aU + (this.aS - this.aU) * 0.2F;
        float f1 = 0.95F;
        if (ag()) {
            for (int k = 0; k < 4; k++) {
                float f3 = 0.25F;
                this.aI.a("bubble", this.aM - this.aP * f3, this.aN - this.aQ * f3, this.aO - this.aR * f3, this.aP, this.aQ, this.aR);
            }
            f1 = 0.8F;
        }
        this.aP += this.c;
        this.aQ += this.d;
        this.aR += this.e;
        this.aP *= f1;
        this.aQ *= f1;
        this.aR *= f1;
        this.aI.a("smoke", this.aM, this.aN + 0.5D, this.aO, 0.0D, 0.0D, 0.0D);
        e(this.aM, this.aN, this.aO);
    }

    public void b(nu nbttagcompound) {
        nbttagcompound.a("xTile", (short) this.f);
        nbttagcompound.a("yTile", (short) this.g);
        nbttagcompound.a("zTile", (short) this.h);
        nbttagcompound.a("inTile", (byte) this.i);
        nbttagcompound.a("shake", (byte) this.a);
        nbttagcompound.a("inGround", (byte) (this.j ? 1 : 0));
    }

    public void a(nu nbttagcompound) {
        this.f = nbttagcompound.d("xTile");
        this.g = nbttagcompound.d("yTile");
        this.h = nbttagcompound.d("zTile");
        this.i = nbttagcompound.c("inTile") & 0xFF;
        this.a = nbttagcompound.c("shake") & 0xFF;
        this.j = (nbttagcompound.c("inGround") == 1);
    }

    public boolean h_() {
        return true;
    }

    public float m_() {
        return 1.0F;
    }

    public boolean a(sn entity, int i) {
        ai();
        if (entity != null) {
            bt vec3d = entity.ac();
            if (vec3d != null) {
                this.aP = vec3d.a;
                this.aQ = vec3d.b;
                this.aR = vec3d.c;
                this.c = this.aP * 0.1D;
                this.d = this.aQ * 0.1D;
                this.e = this.aR * 0.1D;
            }
            return true;
        }
        return false;
    }

    public float x_() {
        return 0.0F;
    }
}
