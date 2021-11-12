package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.ItemEntity;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;

import java.util.List;
import java.util.Random;

public abstract class MixinEntity {
    public static int a = 0;
    public final eq aW;
    public int aD;
    public double aE;
    public boolean aF;
    public Entity aG;
    public Entity aH;
    public Level aI;
    public double aJ;
    public double aK;
    public double aL;
    public double aM;
    public double aN;
    public double aO;
    public double aP;
    public double aQ;
    public double aR;
    public float aS;
    public float aT;
    public float aU;
    public float aV;
    public boolean aX;
    public boolean aY;
    public boolean aZ;
    public boolean ba;
    public boolean bb;
    public boolean bc;
    public boolean bd;
    public boolean be;
    public float bf;
    public float bg;
    public float bh;
    public float bi;
    public float bj;
    public double bl;
    public double bm;
    public double bn;
    public float bo;
    public float bp;
    public boolean bq;
    public float br;
    public int bt;
    public int bu;
    public int bv;
    public int bw;
    public int by;
    public int bz;
    public String bA;
    public String bB;
    public boolean bC;
    public float bE;
    public boolean bF;
    public int bG;
    public int bH;
    public int bI;
    public int bJ;
    public int bK;
    public int bL;
    public boolean bM;
    public boolean isFlying;
    public int stunned;
    public boolean collidesWithClipBlocks;
    public int collisionX;
    public int collisionZ;
    public float moveYawOffset;
    protected float bk;
    protected Random bs;
    protected boolean bx;
    protected ud bD;
    private int b;
    private boolean c;
    private double d;
    private double e;

    public MixinEntity(Level world) {
        this.aW = eq.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        this.collidesWithClipBlocks = true;
        this.moveYawOffset = 0.0F;
        this.aD = a++;
        this.aE = 1.0D;
        this.aF = false;
        this.aX = false;
        this.ba = false;
        this.bb = false;
        this.bd = true;
        this.be = false;
        this.bf = 0.0F;
        this.bg = 0.6F;
        this.bh = 1.8F;
        this.bi = 0.0F;
        this.bj = 0.0F;
        this.bk = 0.0F;
        this.b = 1;
        this.bo = 0.0F;
        this.bp = 0.0F;
        this.bq = false;
        this.br = 0.0F;
        this.bs = new Random();
        this.bt = 0;
        this.bu = 1;
        this.bv = 0;
        this.bw = 300;
        this.bx = false;
        this.by = 0;
        this.bz = 300;
        this.c = true;
        this.bC = false;
        this.bD = new ud();
        this.bE = 0.0F;
        this.bF = false;
        this.aI = world;
        e(0.0D, 0.0D, 0.0D);
        this.bD.a(0, Byte.valueOf((byte) 0));
        b();
        this.isFlying = false;
    }

    protected abstract void b();

    public ud ad() {
        return this.bD;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Entity)
            return (((Entity) obj).aD == this.aD);
        return false;
    }

    public int hashCode() {
        return this.aD;
    }

    protected void t_() {
        if (this.aI == null)
            return;
        while (this.aN > 0.0D) {
            e(this.aM, this.aN, this.aO);
            if (this.aI.a(this, this.aW).size() == 0)
                break;
            this.aN++;
        }
        this.aP = this.aQ = this.aR = 0.0D;
        this.aT = 0.0F;
    }

    public void K() {
        this.be = true;
    }

    protected void b(float f, float f1) {
        this.bg = f;
        this.bh = f1;
    }

    public void c(float f, float f1) {
        this.aS = f % 360.0F;
        this.aT = f1 % 360.0F;
    }

    public void e(double d, double d1, double d2) {
        this.aM = d;
        this.aN = d1;
        this.aO = d2;
        float f = this.bg / 2.0F;
        float f1 = this.bh;
        this.aW.c(d - f, d1 - this.bf + this.bo, d2 - f, d + f, d1 - this.bf + this.bo + f1, d2 + f);
    }

    public void d(float f, float f1) {
        float f2 = this.aT;
        float f3 = this.aS;
        this.aS = (float) (this.aS + f * 0.15D);
        this.aT = (float) (this.aT - f1 * 0.15D);
        if (this.aT < -90.0F)
            this.aT = -90.0F;
        if (this.aT > 90.0F)
            this.aT = 90.0F;
        this.aV += this.aT - f2;
        this.aU += this.aS - f3;
    }

    public void w_() {
        U();
    }

    public void U() {
        if (this.aH != null && this.aH.be)
            this.aH = null;
        this.bt++;
        this.bi = this.bj;
        this.aJ = this.aM;
        this.aK = this.aN;
        this.aL = this.aO;
        this.aV = this.aT;
        this.aU = this.aS;
        if (k_()) {
            if (!this.bx && !this.c) {
                float f = MathsHelper.a(this.aP * this.aP * 0.20000000298023224D + this.aQ * this.aQ + this.aR * this.aR * 0.20000000298023224D) * 0.2F;
                if (f > 1.0F)
                    f = 1.0F;
                this.aI.a(this, "random.splash", f, 1.0F + (this.bs.nextFloat() - this.bs.nextFloat()) * 0.4F);
                float f1 = MathsHelper.b(this.aW.b);
                for (int i = 0; i < 1.0F + this.bg * 20.0F; i++) {
                    float f2 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                    float f4 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                    this.aI.a("bubble", this.aM + f2, (f1 + 1.0F), this.aO + f4, this.aP, this.aQ - (this.bs.nextFloat() * 0.2F), this.aR);
                }
                for (int j = 0; j < 1.0F + this.bg * 20.0F; j++) {
                    float f3 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                    float f5 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg;
                    this.aI.a("splash", this.aM + f3, (f1 + 1.0F), this.aO + f5, this.aP, this.aQ, this.aR);
                }
            }
            this.bk = 0.0F;
            this.bx = true;
            this.bv = 0;
        } else {
            this.bx = false;
        }
        if (this.aI.B) {
            this.bv = 0;
        } else if (this.bv > 0) {
            if (this.bC) {
                this.bv -= 4;
                if (this.bv < 0)
                    this.bv = 0;
            } else {
                if (this.bv % 20 == 0)
                    a((Entity) null, 1);
                this.bv--;
            }
        }
        if (ah())
            ae();
        if (this.aN < -64.0D)
            ab();
        if (!this.aI.B) {
            b(0, (this.bv > 0));
            b(2, (this.aH != null));
        }
        this.c = false;
    }

    protected void ae() {
        if (!this.bC) {
            a((Entity) null, 4);
            this.bv = 600;
        }
    }

    protected void ab() {
        K();
    }

    public boolean f(double d, double d1, double d2) {
        eq axisalignedbb = this.aW.c(d, d1, d2);
        List list = this.aI.a(this, axisalignedbb);
        if (list.size() > 0)
            return false;
        return !this.aI.b(axisalignedbb);
    }

    public void b(double d, double d1, double d2) {
        if (this.bq) {
            this.aW.d(d, d1, d2);
            this.aM = (this.aW.a + this.aW.d) / 2.0D;
            this.aN = this.aW.b + this.bf - this.bo;
            this.aO = (this.aW.c + this.aW.f) / 2.0D;
            return;
        }
        this.bo *= 0.4F;
        double d3 = this.aM;
        double d4 = this.aO;
        if (this.bc) {
            this.bc = false;
            d *= 0.25D;
            d1 *= 0.05000000074505806D;
            d2 *= 0.25D;
            this.aP = 0.0D;
            this.aQ = 0.0D;
            this.aR = 0.0D;
        }
        double d5 = d;
        double d6 = d1;
        double d7 = d2;
        eq axisalignedbb = this.aW.d();
        boolean flag = (this.aX && t());
        if (flag) {
            double d8 = 0.05D;
            for (; d != 0.0D && this.aI.a(this, this.aW.c(d, -1.0D, 0.0D)).size() == 0; d5 = d) {
                if (d < d8 && d >= -d8) {
                    d = 0.0D;
                } else if (d > 0.0D) {
                    d -= d8;
                } else {
                    d += d8;
                }
            }
            for (; d2 != 0.0D && this.aI.a(this, this.aW.c(0.0D, -1.0D, d2)).size() == 0; d7 = d2) {
                if (d2 < d8 && d2 >= -d8) {
                    d2 = 0.0D;
                } else if (d2 > 0.0D) {
                    d2 -= d8;
                } else {
                    d2 += d8;
                }
            }
        }
        List<eq> list = this.aI.a(this, this.aW.a(d, d1, d2));
        for (int i = 0; i < list.size(); i++)
            d1 = ((eq) list.get(i)).b(this.aW, d1);
        this.aW.d(0.0D, d1, 0.0D);
        if (!this.bd && d6 != d1)
            d = d1 = d2 = 0.0D;
        boolean flag1 = (this.aX || (d6 != d1 && d6 < 0.0D));
        for (int j = 0; j < list.size(); j++)
            d = ((eq) list.get(j)).a(this.aW, d);
        this.aW.d(d, 0.0D, 0.0D);
        if (!this.bd && d5 != d)
            d = d1 = d2 = 0.0D;
        for (int k = 0; k < list.size(); k++)
            d2 = ((eq) list.get(k)).c(this.aW, d2);
        this.aW.d(0.0D, 0.0D, d2);
        if (!this.bd && d7 != d2)
            d = d1 = d2 = 0.0D;
        if (this.bp > 0.0F && flag1 && (flag || this.bo < 0.05F) && (d5 != d || d7 != d2)) {
            double d9 = d;
            double d11 = d1;
            double d13 = d2;
            d = d5;
            d1 = this.bp;
            d2 = d7;
            eq axisalignedbb1 = this.aW.d();
            this.aW.b(axisalignedbb);
            List<eq> list1 = this.aI.a(this, this.aW.a(d, d1, d2));
            for (int j2 = 0; j2 < list1.size(); j2++)
                d1 = ((eq) list1.get(j2)).b(this.aW, d1);
            this.aW.d(0.0D, d1, 0.0D);
            if (!this.bd && d6 != d1)
                d = d1 = d2 = 0.0D;
            for (int k2 = 0; k2 < list1.size(); k2++)
                d = ((eq) list1.get(k2)).a(this.aW, d);
            this.aW.d(d, 0.0D, 0.0D);
            if (!this.bd && d5 != d)
                d = d1 = d2 = 0.0D;
            for (int l2 = 0; l2 < list1.size(); l2++)
                d2 = ((eq) list1.get(l2)).c(this.aW, d2);
            this.aW.d(0.0D, 0.0D, d2);
            if (!this.bd && d7 != d2)
                d = d1 = d2 = 0.0D;
            d = d1 = d2 = 0.0D;
            d1 = -this.bp;
            for (int i3 = 0; i3 < list1.size(); i3++)
                d1 = ((eq) list1.get(i3)).b(this.aW, d1);
            this.aW.d(0.0D, d1, 0.0D);
            if (d9 * d9 + d13 * d13 >= d * d + d2 * d2) {
                d = d9;
                d1 = d11;
                d2 = d13;
                this.aW.b(axisalignedbb1);
            } else {
                double d14 = this.aW.b - (int) this.aW.b;
                if (d14 > 0.0D)
                    this.bo = (float) (this.bo + d14 + 0.01D);
            }
        }
        this.aM = (this.aW.a + this.aW.d) / 2.0D;
        this.aN = this.aW.b + this.bf - this.bo;
        this.aO = (this.aW.c + this.aW.f) / 2.0D;
        if (d5 == d) {
            this.collisionX = 0;
        } else if (d5 < d) {
            this.collisionX = -1;
        } else {
            this.collisionX = 1;
        }
        if (this.collisionX != 0) {
            boolean nonClipFound = false;
            for (int m = 0; m < this.bh + this.aN - this.bf - Math.floor(this.aN - this.bf); m++) {
                int blockID = this.aI.a((int) Math.floor(this.aM) + this.collisionX, (int) Math.floor(this.aN + m - this.bf), (int) Math.floor(this.aO));
                if (blockID != 0 && blockID != Blocks.clipBlock.bn)
                    nonClipFound = true;
            }
            if (!nonClipFound)
                this.collisionX = 0;
        }
        if (d7 == d2) {
            this.collisionZ = 0;
        } else if (d7 < d2) {
            this.collisionZ = -1;
        } else {
            this.collisionZ = 1;
        }
        if (this.collisionZ != 0) {
            boolean nonClipFound = false;
            for (int m = 0; m < this.bh + this.aN - this.bf - Math.floor(this.aN - this.bf); m++) {
                int blockID = this.aI.a((int) Math.floor(this.aM), (int) Math.floor(this.aN + m - this.bf), (int) Math.floor(this.aO) + this.collisionZ);
                if (blockID != 0 && blockID != Blocks.clipBlock.bn)
                    nonClipFound = true;
            }
            if (!nonClipFound)
                this.collisionZ = 0;
        }
        this.aY = (d5 != d || d7 != d2);
        this.aZ = (d6 != d1);
        this.aX = (d6 != d1 && d6 < 0.0D);
        this.ba = (this.aY || this.aZ);
        a(d1, this.aX);
        if (d5 != d)
            this.aP = 0.0D;
        if (d6 != d1)
            this.aQ = 0.0D;
        if (d7 != d2)
            this.aR = 0.0D;
        double d10 = this.aM - d3;
        double d12 = this.aO - d4;
        if (n() && !flag && this.aH == null) {
            this.bj = (float) (this.bj + MathsHelper.a(d10 * d10 + d12 * d12) * 0.6D);
            int l = MathsHelper.b(this.aM);
            int j1 = MathsHelper.b(this.aN - 0.20000000298023224D - this.bf);
            int l1 = MathsHelper.b(this.aO);
            int j3 = this.aI.a(l, j1, l1);
            if (this.aI.a(l, j1 - 1, l1) == Tile.ba.bn)
                j3 = this.aI.a(l, j1 - 1, l1);
            if (this.bj > this.b && j3 > 0) {
                this.b = (int) (this.b + Math.ceil((this.bj - this.b)));
                ct stepsound = (Tile.m[j3]).by;
                if (this.aI.a(l, j1 + 1, l1) == Tile.aT.bn) {
                    stepsound = Tile.aT.by;
                    this.aI.a(this, stepsound.d(), stepsound.b() * 0.15F, stepsound.c());
                } else if (!(Tile.m[j3]).bA.d()) {
                    this.aI.a(this, stepsound.d(), stepsound.b() * 0.15F, stepsound.c());
                }
                Tile.m[j3].b(this.aI, l, j1, l1, this);
            }
        }
        int i1 = MathsHelper.b(this.aW.a + 0.001D);
        int k1 = MathsHelper.b(this.aW.b + 0.001D);
        int i2 = MathsHelper.b(this.aW.c + 0.001D);
        int k3 = MathsHelper.b(this.aW.d - 0.001D);
        int l3 = MathsHelper.b(this.aW.e - 0.001D);
        int i4 = MathsHelper.b(this.aW.f - 0.001D);
        if (this.aI.a(i1, k1, i2, k3, l3, i4))
            for (int j4 = i1; j4 <= k3; j4++) {
                for (int k4 = k1; k4 <= l3; k4++) {
                    for (int l4 = i2; l4 <= i4; l4++) {
                        int i5 = this.aI.a(j4, k4, l4);
                        if (i5 > 0)
                            Tile.m[i5].a(this.aI, j4, k4, l4, this);
                    }
                }
            }
        boolean flag2 = af();
        if (this.aI.c(this.aW.e(0.001D, 0.001D, 0.001D))) {
            a(1);
            if (!flag2) {
                this.bv++;
                if (this.bv == 0)
                    this.bv = 300;
            }
        } else if (this.bv <= 0) {
            this.bv = -this.bu;
        }
        if (flag2 && this.bv > 0) {
            this.aI.a(this, "random.fizz", 0.7F, 1.6F + (this.bs.nextFloat() - this.bs.nextFloat()) * 0.4F);
            this.bv = -this.bu;
        }
    }

    protected boolean n() {
        return true;
    }

    protected void a(double d, boolean flag) {
        if (flag) {
            if (this.aQ < 0.0D)
                b(-((float) this.aQ));
        } else if (d < 0.0D) {
            this.bk = (float) (this.bk - d);
        }
    }

    public eq f() {
        return null;
    }

    protected void a(int i) {
        if (!this.bC)
            a((Entity) null, i);
    }

    protected void b(float f) {
        if (this.aG != null)
            this.aG.b(f);
    }

    public boolean af() {
        return (this.bx || this.aI.t(MathsHelper.b(this.aM), MathsHelper.b(this.aN), MathsHelper.b(this.aO)));
    }

    public boolean ag() {
        return this.bx;
    }

    public boolean k_() {
        return this.aI.a(this.aW.b(0.0D, -0.4000000059604645D, 0.0D).e(0.001D, 0.001D, 0.001D), ln.g, this);
    }

    public boolean a(ln material) {
        double d = this.aN + w();
        int i = MathsHelper.b(this.aM);
        int j = MathsHelper.d(MathsHelper.b(d));
        int k = MathsHelper.b(this.aO);
        int l = this.aI.a(i, j, k);
        if (l != 0 && (Tile.m[l]).bA == material) {
            float f = rp.d(this.aI.e(i, j, k)) - 0.1111111F;
            float f1 = (j + 1) - f;
            return (d < f1);
        }
        return false;
    }

    public float w() {
        return 0.0F;
    }

    public boolean handleFlying() {
        return this.isFlying;
    }

    public boolean ah() {
        return this.aI.a(this.aW.b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), ln.h);
    }

    public void a(float f, float f1, float f2) {
        float f3 = f * f + f1 * f1;
        if (f3 < 1.0E-4F)
            return;
        f *= f2;
        f1 *= f2;
        float f4 = MathsHelper.a((this.aS + this.moveYawOffset) * 3.141593F / 180.0F);
        float f5 = MathsHelper.b((this.aS + this.moveYawOffset) * 3.141593F / 180.0F);
        this.aP += (f * f5 - f1 * f4);
        this.aR += (f1 * f5 + f * f4);
    }

    public float a(float f) {
        int i = MathsHelper.b(this.aM);
        double d = (this.aW.e - this.aW.b) * 0.66D;
        int j = MathsHelper.b(this.aN - this.bf + d);
        int k = MathsHelper.b(this.aO);
        if (this.aI.a(MathsHelper.b(this.aW.a), MathsHelper.b(this.aW.b), MathsHelper.b(this.aW.c), MathsHelper.b(this.aW.d), MathsHelper.b(this.aW.e), MathsHelper.b(this.aW.f))) {
            float f1 = this.aI.c(i, j, k);
            if (f1 < this.bE)
                f1 = this.bE;
            return f1;
        }
        return this.bE;
    }

    public void a(Level world) {
        this.aI = world;
    }

    public void b(double d, double d1, double d2, float f, float f1) {
        this.aJ = this.aM = d;
        this.aK = this.aN = d1;
        this.aL = this.aO = d2;
        this.aU = this.aS = f;
        this.aV = this.aT = f1;
        this.bo = 0.0F;
        double d3 = (this.aU - f);
        if (d3 < -180.0D)
            this.aU += 360.0F;
        if (d3 >= 180.0D)
            this.aU -= 360.0F;
        e(this.aM, this.aN, this.aO);
        c(f, f1);
    }

    public void c(double d, double d1, double d2, float f, float f1) {
        this.bl = this.aJ = this.aM = d;
        this.bm = this.aK = this.aN = d1 + this.bf;
        this.bn = this.aL = this.aO = d2;
        this.aS = f;
        this.aT = f1;
        e(this.aM, this.aN, this.aO);
    }

    public float f(Entity entity) {
        float f = (float) (this.aM - entity.aM);
        float f1 = (float) (this.aN - entity.aN);
        float f2 = (float) (this.aO - entity.aO);
        return MathsHelper.c(f * f + f1 * f1 + f2 * f2);
    }

    public double g(double d, double d1, double d2) {
        double d3 = this.aM - d;
        double d4 = this.aN - d1;
        double d5 = this.aO - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double h(double d, double d1, double d2) {
        double d3 = this.aM - d;
        double d4 = this.aN - d1;
        double d5 = this.aO - d2;
        return MathsHelper.a(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double g(Entity entity) {
        double d = this.aM - entity.aM;
        double d1 = this.aN - entity.aN;
        double d2 = this.aO - entity.aO;
        return d * d + d1 * d1 + d2 * d2;
    }

    public void b(gs entityplayer) {
    }

    public void h(Entity entity) {
        if (entity.aG == this || entity.aH == this)
            return;
        double d = entity.aM - this.aM;
        double d1 = entity.aO - this.aO;
        double d2 = MathsHelper.a(d, d1);
        if (d2 >= 0.009999999776482582D) {
            d2 = MathsHelper.a(d2);
            d /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;
            if (d3 > 1.0D)
                d3 = 1.0D;
            d *= d3;
            d1 *= d3;
            d *= 0.05000000074505806D;
            d1 *= 0.05000000074505806D;
            d *= (1.0F - this.br);
            d1 *= (1.0F - this.br);
            d(-d, 0.0D, -d1);
            if (entity.i_()) {
                entity.d(d, 0.0D, d1);
            } else {
                d(-d, 0.0D, -d1);
            }
        }
    }

    public void d(double d, double d1, double d2) {
        this.aP += d;
        this.aQ += d1;
        this.aR += d2;
    }

    protected void ai() {
        this.bb = true;
    }

    public boolean a(Entity entity, int i) {
        ai();
        return false;
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        return a(entity, i);
    }

    public boolean h_() {
        return false;
    }

    public boolean i_() {
        return false;
    }

    public void c(Entity entity, int i) {
    }

    public boolean a(bt vec3d) {
        double d = this.aM - vec3d.a;
        double d1 = this.aN - vec3d.b;
        double d2 = this.aO - vec3d.c;
        double d3 = d * d + d1 * d1 + d2 * d2;
        return a(d3);
    }

    public boolean a(double d) {
        double d1 = this.aW.c();
        d1 *= 64.0D * this.aE;
        return (d < d1 * d1);
    }

    public String q_() {
        return null;
    }

    public boolean c(CompoundTag nbttagcompound) {
        String s = aj();
        if (this.be || s == null)
            return false;
        nbttagcompound.a("id", s);
        d(nbttagcompound);
        return true;
    }

    public void d(CompoundTag nbttagcompound) {
        nbttagcompound.a("Pos", (ij) a(new double[]{this.aM, this.aN + this.bo, this.aO}));
        nbttagcompound.a("Motion", (ij) a(new double[]{this.aP, this.aQ, this.aR}));
        nbttagcompound.a("Rotation", (ij) a(new float[]{this.aS, this.aT}));
        nbttagcompound.a("FallDistance", this.bk);
        nbttagcompound.a("Fire", (short) this.bv);
        nbttagcompound.a("Air", (short) this.bz);
        nbttagcompound.a("OnGround", this.aX);
        b(nbttagcompound);
    }

    public void e(CompoundTag nbttagcompound) {
        sp nbttaglist = nbttagcompound.l("Pos");
        sp nbttaglist1 = nbttagcompound.l("Motion");
        sp nbttaglist2 = nbttagcompound.l("Rotation");
        this.aP = ((sz) nbttaglist1.a(0)).a;
        this.aQ = ((sz) nbttaglist1.a(1)).a;
        this.aR = ((sz) nbttaglist1.a(2)).a;
        if (Math.abs(this.aP) > 10.0D)
            this.aP = 0.0D;
        if (Math.abs(this.aQ) > 10.0D)
            this.aQ = 0.0D;
        if (Math.abs(this.aR) > 10.0D)
            this.aR = 0.0D;
        this.aJ = this.bl = this.aM = ((sz) nbttaglist.a(0)).a;
        this.aK = this.bm = this.aN = ((sz) nbttaglist.a(1)).a;
        this.aL = this.bn = this.aO = ((sz) nbttaglist.a(2)).a;
        this.aU = this.aS = ((p) nbttaglist2.a(0)).a;
        this.aV = this.aT = ((p) nbttaglist2.a(1)).a;
        this.bk = nbttagcompound.g("FallDistance");
        this.bv = nbttagcompound.d("Fire");
        this.bz = nbttagcompound.d("Air");
        this.aX = nbttagcompound.m("OnGround");
        e(this.aM, this.aN, this.aO);
        c(this.aS, this.aT);
        a(nbttagcompound);
    }

    protected final String aj() {
        return EntityRegistry.b(this);
    }

    protected abstract void a(CompoundTag paramnu);

    protected abstract void b(CompoundTag paramnu);

    protected sp a(double[] ad) {
        sp nbttaglist = new sp();
        double[] ad1 = ad;
        int i = ad1.length;
        for (int j = 0; j < i; j++) {
            double d = ad1[j];
            nbttaglist.a((ij) new sz(d));
        }
        return nbttaglist;
    }

    protected sp a(float[] af) {
        sp nbttaglist = new sp();
        float[] af1 = af;
        int i = af1.length;
        for (int j = 0; j < i; j++) {
            float f = af1[j];
            nbttaglist.a((ij) new p(f));
        }
        return nbttaglist;
    }

    public float x_() {
        return this.bh / 2.0F;
    }

    public ItemEntity b(int i, int j) {
        return a(i, j, 0.0F);
    }

    public ItemEntity a(int i, int j, float f) {
        return a(new iz(i, j, 0), f);
    }

    public ItemEntity a(iz itemstack, float f) {
        ItemEntity entityitem = new ItemEntity(this.aI, this.aM, this.aN + f, this.aO, itemstack);
        entityitem.c = 10;
        this.aI.b((Entity) entityitem);
        return entityitem;
    }

    public boolean W() {
        return !this.be;
    }

    public boolean L() {
        for (int i = 0; i < 8; i++) {
            float f = (((i >> 0) % 2) - 0.5F) * this.bg * 0.9F;
            float f1 = (((i >> 1) % 2) - 0.5F) * 0.1F;
            float f2 = (((i >> 2) % 2) - 0.5F) * this.bg * 0.9F;
            int j = MathsHelper.b(this.aM + f);
            int k = MathsHelper.b(this.aN + w() + f1);
            int l = MathsHelper.b(this.aO + f2);
            if (this.aI.h(j, k, l) && this.aI.g(j, k, l))
                return true;
        }
        return false;
    }

    public boolean a(gs entityplayer) {
        return false;
    }

    public eq a(Entity entity) {
        return null;
    }

    public void s_() {
        if (this.aH.be) {
            this.aH = null;
            return;
        }
        this.aP = 0.0D;
        this.aQ = 0.0D;
        this.aR = 0.0D;
        w_();
        if (this.aH == null)
            return;
        this.aH.l_();
        this.e += (this.aH.aS - this.aH.aU);
        this.d += (this.aH.aT - this.aH.aV);
        for (; this.e >= 180.0D; this.e -= 360.0D) ;
        for (; this.e < -180.0D; this.e += 360.0D) ;
        for (; this.d >= 180.0D; this.d -= 360.0D) ;
        for (; this.d < -180.0D; this.d += 360.0D) ;
        double d = this.e * 0.5D;
        double d1 = this.d * 0.5D;
        float f = 10.0F;
        if (d > f)
            d = f;
        if (d < -f)
            d = -f;
        if (d1 > f)
            d1 = f;
        if (d1 < -f)
            d1 = -f;
        this.e -= d;
        this.d -= d1;
        this.aS = (float) (this.aS + d);
        this.aT = (float) (this.aT + d1);
    }

    public void l_() {
        this.aG.e(this.aM, this.aN + m() + this.aG.I(), this.aO);
    }

    public double I() {
        return this.bf;
    }

    public double m() {
        return this.bh * 0.75D;
    }

    public void i(Entity entity) {
        this.d = 0.0D;
        this.e = 0.0D;
        if (entity == null) {
            if (this.aH != null) {
                c(this.aH.aM, this.aH.aW.b + this.aH.bh, this.aH.aO, this.aS, this.aT);
                this.aH.aG = null;
            }
            this.aH = null;
            return;
        }
        if (this.aH == entity) {
            this.aH.aG = null;
            this.aH = null;
            c(entity.aM, entity.aW.b + entity.bh, entity.aO, this.aS, this.aT);
            return;
        }
        if (this.aH != null)
            this.aH.aG = null;
        if (entity.aG != null)
            entity.aG.aH = null;
        this.aH = entity;
        entity.aG = this;
    }

    public void a(double d, double d1, double d2, float f, float f1, int i) {
        e(d, d1, d2);
        c(f, f1);
        List<eq> list = this.aI.a(this, this.aW.e(0.03125D, 0.0D, 0.03125D));
        if (list.size() > 0) {
            double d3 = 0.0D;
            for (int j = 0; j < list.size(); j++) {
                eq axisalignedbb = list.get(j);
                if (axisalignedbb.e > d3)
                    d3 = axisalignedbb.e;
            }
            d1 += d3 - this.aW.b;
            e(d, d1, d2);
        }
    }

    public float m_() {
        return 0.1F;
    }

    public bt ac() {
        return null;
    }

    public void S() {
    }

    public void a(double d, double d1, double d2) {
        this.aP = d;
        this.aQ = d1;
        this.aR = d2;
    }

    public void a(byte byte0) {
    }

    public void h() {
    }

    public void u_() {
    }

    public void c(int i, int j, int k) {
    }

    public boolean ak() {
        return (this.bv > 0 || d(0));
    }

    public boolean al() {
        return (this.aH != null || d(2));
    }

    public boolean t() {
        return d(1);
    }

    protected boolean d(int i) {
        return ((this.bD.a(0) & 1 << i) != 0);
    }

    protected void b(int i, boolean flag) {
        byte byte0 = this.bD.a(0);
        if (flag) {
            this.bD.b(0, Byte.valueOf((byte) (byte0 | 1 << i)));
        } else {
            this.bD.b(0, Byte.valueOf((byte) (byte0 & (1 << i ^ 0xFFFFFFFF))));
        }
    }

    public void a(c entitylightningbolt) {
        a(5);
        this.bv++;
        if (this.bv == 0)
            this.bv = 300;
    }

    public void a(LivingEntity entityliving) {
    }

    protected boolean c(double d, double d1, double d2) {
        int i = MathsHelper.b(d);
        int j = MathsHelper.b(d1);
        int k = MathsHelper.b(d2);
        double d3 = d - i;
        double d4 = d1 - j;
        double d5 = d2 - k;
        if (this.aI.h(i, j, k)) {
            boolean flag = !this.aI.h(i - 1, j, k);
            boolean flag1 = !this.aI.h(i + 1, j, k);
            boolean flag2 = !this.aI.h(i, j - 1, k);
            boolean flag3 = !this.aI.h(i, j + 1, k);
            boolean flag4 = !this.aI.h(i, j, k - 1);
            boolean flag5 = !this.aI.h(i, j, k + 1);
            byte byte0 = -1;
            double d6 = 9999.0D;
            if (flag && d3 < d6) {
                d6 = d3;
                byte0 = 0;
            }
            if (flag1 && 1.0D - d3 < d6) {
                d6 = 1.0D - d3;
                byte0 = 1;
            }
            if (flag2 && d4 < d6) {
                d6 = d4;
                byte0 = 2;
            }
            if (flag3 && 1.0D - d4 < d6) {
                d6 = 1.0D - d4;
                byte0 = 3;
            }
            if (flag4 && d5 < d6) {
                d6 = d5;
                byte0 = 4;
            }
            if (flag5 && 1.0D - d5 < d6) {
                double d7 = 1.0D - d5;
                byte0 = 5;
            }
            float f = this.bs.nextFloat() * 0.2F + 0.1F;
            if (byte0 == 0)
                this.aP = -f;
            if (byte0 == 1)
                this.aP = f;
            if (byte0 == 2)
                this.aQ = -f;
            if (byte0 == 3)
                this.aQ = f;
            if (byte0 == 4)
                this.aR = -f;
            if (byte0 == 5)
                this.aR = f;
        }
        return false;
    }
}