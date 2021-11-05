package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.items.Items;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

import java.util.List;

public abstract class LivingEntity extends Entity {
    public int E;

    public float F;

    public float G;

    public float H;

    public float I;
    public String O;
    public boolean V;
    public float W;
    public float X;
    public int Y;
    public int maxHealth;
    public int Z;
    public int aa;
    public int ab;
    public float ac;
    public int ad;
    public int ae;
    public float af;
    public float ag;
    public int ai;
    public float aj;
    public float ak;
    public float al;
    public float am;
    public int au;
    public boolean az;
    public float aB;
    public Entity b;
    public ItemInstance heldItem;
    public int timesCanJumpInAir;
    public int jumpsLeft;
    public boolean canWallJump;
    public double jumpVelocity;
    public double jumpWallMultiplier;
    public double jumpInAirMultiplier;
    public float airControl;
    public double gravity;
    public float fov;
    public float extraFov;
    public boolean canLookRandomly;
    public float randomLookVelocity;
    public int randomLookNext;
    public int randomLookRate;
    public int randomLookRateVariation;
    protected float J;
    protected float K;
    protected float L;
    protected float M;
    protected boolean N;
    protected boolean P;
    protected float Q;
    protected String R;
    protected float S;
    protected int T;
    protected float U;
    protected boolean ah;
    protected int an;
    protected double ao;
    protected double ap;
    protected double aq;
    protected double ar;
    protected double as;
    protected int av;
    protected float aw;
    protected float ax;
    protected float ay;
    protected float aA;
    protected int aC;
    float at;
    private int a;
    private long hurtTick;
    private long tickBeforeNextJump;

    public LivingEntity(Level world) {
        super(world);
        this.timesCanJumpInAir = 0;
        this.jumpsLeft = 0;
        this.canWallJump = false;
        this.jumpVelocity = 0.42D;
        this.jumpWallMultiplier = 1.0D;
        this.jumpInAirMultiplier = 1.0D;
        this.airControl = 0.9259F;
        this.gravity = 0.08D;
        this.fov = 140.0F;
        this.extraFov = 0.0F;
        this.canLookRandomly = true;
        this.randomLookVelocity = 20.0F;
        this.randomLookNext = 0;
        this.randomLookRate = 100;
        this.randomLookRateVariation = 40;
        this.E = 20;
        this.H = 0.0F;
        this.I = 0.0F;
        this.N = true;
        this.O = "/mob/char.png";
        this.P = true;
        this.Q = 0.0F;
        this.R = null;
        this.S = 1.0F;
        this.T = 0;
        this.U = 0.0F;
        this.V = false;
        this.ac = 0.0F;
        this.ad = 0;
        this.ae = 0;
        this.ah = false;
        this.ai = -1;
        this.aj = (float) (Math.random() * 0.8999999761581421D + 0.10000000149011612D);
        this.at = 0.0F;
        this.au = 0;
        this.av = 0;
        this.az = false;
        this.aA = 0.0F;
        this.aB = 0.7F;
        this.aC = 0;
        this.Y = 10;
        this.maxHealth = 10;
        this.aF = true;
        this.G = (float) (Math.random() + 1.0D) * 0.01F;
        e(this.aM, this.aN, this.aO);
        this.F = (float) Math.random() * 12398.0F;
        this.aS = (float) (Math.random() * 3.1415927410125732D * 2.0D);
        this.bp = 0.5F;
        this.heldItem = null;
    }

    protected void b() {
    }

    public boolean e(Entity entity) {
        double angleOffset = -180.0D * Math.atan2(entity.aM - this.aM, entity.aO - this.aO) / Math.PI;
        double diffAngle = angleOffset - this.aS;
        while (diffAngle < -180.0D)
            diffAngle += 360.0D;
        while (diffAngle > 180.0D)
            diffAngle -= 360.0D;
        if (Math.abs(diffAngle) > (this.fov / 2.0F + this.extraFov))
            return false;
        return (this.aI.a(bt.b(this.aM, this.aN + w(), this.aO), bt.b(entity.aM, entity.aN + entity.w(), entity.aO)) == null);
    }

    public String q_() {
        return this.O;
    }

    public boolean h_() {
        return !this.be;
    }

    public boolean i_() {
        return !this.be;
    }

    public float w() {
        return this.bh * 0.85F;
    }

    public int e() {
        return 80;
    }

    public void T() {
        String s = g();
        if (s != null)
            this.aI.a(this, s, k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
    }

    public void U() {
        this.W = this.X;
        super.U();
        if (this.bs.nextInt(1000) < this.a++) {
            this.a = -e();
            T();
        }
        if (W() && L() && !this.bq)
            a(null, 1);
        if (this.bC || this.aI.B)
            this.bv = 0;
        if (W() && a(ln.g) && !d_()) {
            this.bz--;
            if (this.bz == -20) {
                this.bz = 0;
                for (int i = 0; i < 8; i++) {
                    float f = this.bs.nextFloat() - this.bs.nextFloat();
                    float f1 = this.bs.nextFloat() - this.bs.nextFloat();
                    float f2 = this.bs.nextFloat() - this.bs.nextFloat();
                    this.aI.a("bubble", this.aM + f, this.aN + f1, this.aO + f2, this.aP, this.aQ, this.aR);
                }
                a(null, 2);
            }
            this.bv = 0;
        } else {
            this.bz = this.bw;
        }
        this.af = this.ag;
        if (this.ae > 0)
            this.ae--;
        if (this.aa > 0)
            this.aa--;
        if (this.by > 0)
            this.by--;
        if (this.Y <= 0) {
            this.ad++;
            if (this.ad > 20) {
                aa();
                K();
                for (int j = 0; j < 20; j++) {
                    double d = this.bs.nextGaussian() * 0.02D;
                    double d1 = this.bs.nextGaussian() * 0.02D;
                    double d2 = this.bs.nextGaussian() * 0.02D;
                    this.aI.a("explode", this.aM + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, this.aN + (this.bs.nextFloat() * this.bh), this.aO + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, d, d1, d2);
                }
            }
        }
        this.M = this.L;
        this.I = this.H;
        this.aU = this.aS;
        this.aV = this.aT;
        if (this.extraFov > 0.0F) {
            this.extraFov -= 5.0F;
            if (this.extraFov < 0.0F)
                this.extraFov = 0.0F;
        }
    }

    public void V() {
        for (int i = 0; i < 20; i++) {
            double d = this.bs.nextGaussian() * 0.02D;
            double d1 = this.bs.nextGaussian() * 0.02D;
            double d2 = this.bs.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            this.aI.a("explode", this.aM + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg - d * d3, this.aN + (this.bs.nextFloat() * this.bh) - d1 * d3, this.aO + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg - d2 * d3, d, d1, d2);
        }
    }

    public void s_() {
        super.s_();
        this.J = this.K;
        this.K = 0.0F;
    }

    public void a(double d, double d1, double d2, float f, float f1, int i) {
        this.bf = 0.0F;
        this.ao = d;
        this.ap = d1;
        this.aq = d2;
        this.ar = f;
        this.as = f1;
        this.an = i;
    }

    public void w_() {
        super.w_();
        o();
        double d = this.aM - this.aJ;
        double d1 = this.aO - this.aL;
        float f = MathsHelper.a(d * d + d1 * d1);
        float f1 = this.H;
        float f2 = 0.0F;
        this.J = this.K;
        float f3 = 0.0F;
        if (f > 0.05F) {
            f3 = 1.0F;
            f2 = f * 3.0F;
            f1 = (float) Math.atan2(d1, d) * 180.0F / 3.141593F - 90.0F;
        }
        if (this.X > 0.0F)
            f1 = this.aS;
        if (!this.aX)
            f3 = 0.0F;
        this.K += (f3 - this.K) * 0.3F;
        float f4;
        for (f4 = f1 - this.H; f4 < -180.0F; f4 += 360.0F) ;
        for (; f4 >= 180.0F; f4 -= 360.0F) ;
        this.H += f4 * 0.3F;
        float f5;
        for (f5 = this.aS - this.H; f5 < -180.0F; f5 += 360.0F) ;
        for (; f5 >= 180.0F; f5 -= 360.0F) ;
        boolean flag = (f5 < -90.0F || f5 >= 90.0F);
        if (f5 < -75.0F)
            f5 = -75.0F;
        if (f5 >= 75.0F)
            f5 = 75.0F;
        this.H = this.aS - f5;
        if (f5 * f5 > 2500.0F)
            this.H += f5 * 0.2F;
        if (flag)
            f2 *= -1.0F;
        for (; this.aS - this.aU < -180.0F; this.aU -= 360.0F) ;
        for (; this.aS - this.aU >= 180.0F; this.aU += 360.0F) ;
        for (; this.H - this.I < -180.0F; this.I -= 360.0F) ;
        for (; this.H - this.I >= 180.0F; this.I += 360.0F) ;
        for (; this.aT - this.aV < -180.0F; this.aV -= 360.0F) ;
        for (; this.aT - this.aV >= 180.0F; this.aV += 360.0F) ;
        this.L += f2;
    }

    protected void b(float f, float f1) {
        super.b(f, f1);
    }

    public void c(int i) {
        if (this.Y <= 0)
            return;
        this.Y += i;
        if (this.Y > this.maxHealth)
            this.Y = this.maxHealth;
        this.by = this.E / 2;
    }

    public boolean a(Entity entity, int i) {
        if (this.aI.B)
            return false;
        this.av = 0;
        if (this.Y <= 0)
            return false;
        this.extraFov = 180.0F;
        this.al = 1.5F;
        boolean flag = true;
        if (this.by > this.E / 2.0F && this.aa > 0) {
            if (i <= this.au)
                return false;
            b(i - this.au);
            this.au = i;
            flag = false;
        } else {
            this.au = i;
            this.Z = this.Y;
            this.by = this.E;
            b(i);
            this.aa = this.ab = 10;
            this.hurtTick = this.aI.t();
        }
        this.ac = 0.0F;
        if (flag) {
            this.aI.a(this, (byte) 2);
            ai();
            if (entity != null) {
                double d = entity.aM - this.aM;
                double d1;
                for (d1 = entity.aO - this.aO; d * d + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
                    d = (Math.random() - Math.random()) * 0.01D;
                this.ac = (float) (Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - this.aS;
                a(entity, i, d, d1);
            } else {
                this.ac = ((int) (Math.random() * 2.0D) * 180);
            }
        }
        if (this.Y <= 0) {
            if (flag)
                this.aI.a(this, i(), k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
            b(entity);
        } else if (flag) {
            this.aI.a(this, j_(), k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
        }
        return true;
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        if (this.aI.B)
            return false;
        this.av = 0;
        if (this.Y <= 0)
            return false;
        this.extraFov = 180.0F;
        this.al = 1.5F;
        boolean flag = true;
        if (this.by > this.E / 2.0F && this.hurtTick != this.aI.t()) {
            if (i <= this.au)
                return false;
            b(i - this.au);
            this.au = i;
            flag = false;
        } else {
            this.au = i;
            this.Z = this.Y;
            this.by = this.E;
            b(i);
            this.aa = this.ab = 10;
            this.hurtTick = this.aI.t();
        }
        this.ac = 0.0F;
        if (flag) {
            this.aI.a(this, (byte) 2);
            ai();
            if (entity != null) {
                double d = entity.aM - this.aM;
                double d1;
                for (d1 = entity.aO - this.aO; d * d + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
                    d = (Math.random() - Math.random()) * 0.01D;
                this.ac = (float) (Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - this.aS;
                a(entity, i, d, d1);
            } else {
                this.ac = ((int) (Math.random() * 2.0D) * 180);
            }
        }
        if (this.Y <= 0) {
            if (flag)
                this.aI.a(this, i(), k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
            b(entity);
        } else if (flag) {
            this.aI.a(this, j_(), k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
        }
        return true;
    }

    public void h() {
        this.aa = this.ab = 10;
        this.ac = 0.0F;
    }

    protected void b(int i) {
        this.Y -= i;
    }

    protected float k() {
        return 1.0F;
    }

    protected String g() {
        return null;
    }

    protected String j_() {
        return "random.hurt";
    }

    protected String i() {
        return "random.hurt";
    }

    public void a(Entity entity, int i, double d, double d1) {
        float f = MathsHelper.a(d * d + d1 * d1);
        float f1 = 0.4F;
        this.aP /= 2.0D;
        this.aQ /= 2.0D;
        this.aR /= 2.0D;
        this.aP -= d / f * f1;
        this.aQ += 0.4000000059604645D;
        this.aR -= d1 / f * f1;
        if (this.aQ > 0.4000000059604645D)
            this.aQ = 0.4000000059604645D;
    }

    public void b(Entity entity) {
        if (this.T >= 0 && entity != null)
            entity.c(this, this.T);
        if (entity != null)
            entity.a(this);
        this.ah = true;
        if (!this.aI.B) {
            q();
            if (entity != null && entity instanceof LivingEntity && ((LivingEntity) entity).Y < ((LivingEntity) entity).maxHealth)
                if (this.bs.nextInt(3) != 0) {
                    ItemEntity ItemEntity = new ItemEntity(this.aI, this.aM, this.aN, this.aO, new ItemInstance(Items.heart.bf, 1, 0));
                    this.aI.b((Entity) ItemEntity);
                }
        }
        this.aI.a(this, (byte) 3);
    }

    protected void q() {
        int i = j();
        if (i > 0) {
            int j = this.bs.nextInt(3);
            for (int k = 0; k < j; k++)
                b(i, 1);
        }
    }

    protected int j() {
        return 0;
    }

    protected void b(float f) {
        if (!handleFlying()) {
            float pre = Math.max(f - 0.8F, 0.0F) / 0.08F;
            int i = (int) Math.ceil(Math.pow(pre, 1.5D));
            if (i > 0) {
                a(null, i);
                int j = this.aI.a(MathsHelper.b(this.aM), MathsHelper.b(this.aN - 0.20000000298023224D - this.bf), MathsHelper.b(this.aO));
                if (j > 0) {
                    ct stepsound = (Tile.m[j]).by;
                    this.aI.a(this, stepsound.d(), stepsound.b() * 0.5F, stepsound.c() * 0.75F);
                }
            }
        }
    }

    public void a_(float f, float f1) {
        if (handleFlying()) {
            double l = Math.sqrt((f * f + f1 * f1));
            double ySpeed = (-0.1F * f1) * Math.sin((this.aT * 3.141593F / 180.0F));
            if (l < 1.0D)
                ySpeed *= l;
            this.aQ += ySpeed;
            float speed = (float) (0.10000000149011612D * (Math.abs(f1 * Math.cos((this.aT * 3.141593F / 180.0F))) + Math.abs(f)));
            a(f, f1, speed);
            b(this.aP, this.aQ, this.aR);
            this.bk = 0.0F;
            this.aP *= 0.8D;
            this.aQ *= 0.8D;
            this.aR *= 0.8D;
            if (Math.abs(this.aP) < 0.01D)
                this.aP = 0.0D;
            if (Math.abs(this.aQ) < 0.01D)
                this.aQ = 0.0D;
            if (Math.abs(this.aR) < 0.01D)
                this.aR = 0.0D;
        } else if (ag()) {
            if (this.aQ < -0.4D)
                this.aQ *= 0.8D;
            double d = this.aN;
            a(f, f1, 0.02F);
            b(this.aP, this.aQ, this.aR);
            this.aP *= 0.800000011920929D;
            this.aQ *= 0.800000011920929D;
            this.aR *= 0.800000011920929D;
            this.aQ -= 0.25D * getGravity();
            if (this.aY && f(this.aP, this.aQ + 0.6000000238418579D - this.aN + d, this.aR))
                this.aQ = 0.30000001192092896D;
        } else if (ah()) {
            if (this.aQ < -0.4D)
                this.aQ *= 0.5D;
            double d1 = this.aN;
            a(f, f1, 0.02F);
            b(this.aP, this.aQ, this.aR);
            this.aP *= 0.5D;
            this.aQ *= 0.5D;
            this.aR *= 0.5D;
            this.aQ -= 0.25D * getGravity();
            if (this.aY && f(this.aP, this.aQ + 0.6000000238418579D - this.aN + d1, this.aR))
                this.aQ = 0.30000001192092896D;
        } else {
            float f2 = 0.91F;
            if (this.aX) {
                f2 = 0.5460001F;
                int i = this.aI.a(MathsHelper.b(this.aM), MathsHelper.b(this.aW.b) - 1, MathsHelper.b(this.aO));
                if (i > 0)
                    f2 = (Tile.m[i]).bB * 0.91F;
            }
            float f3 = 0.1627714F / f2 * f2 * f2;
            a(f, f1, this.aX ? (0.1F * f3) : (0.1F * this.airControl * f3));
            f2 = 0.91F;
            if (this.aX) {
                f2 = 0.5460001F;
                int j = this.aI.a(MathsHelper.b(this.aM), MathsHelper.b(this.aW.b) - 1, MathsHelper.b(this.aO));
                if (j > 0)
                    f2 = (Tile.m[j]).bB * 0.91F;
            }
            if (p()) {
                float f4 = 0.15F;
                if (this.aP < -f4)
                    this.aP = -f4;
                if (this.aP > f4)
                    this.aP = f4;
                if (this.aR < -f4)
                    this.aR = -f4;
                if (this.aR > f4)
                    this.aR = f4;
                this.bk = 0.0F;
                if (this.aQ < -0.15D)
                    this.aQ = -0.15D;
                if (t() && this.aQ < 0.0D)
                    this.aQ = 0.0D;
            }
            b(this.aP, this.aQ, this.aR);
            if ((this.aY || this.az) && p())
                this.aQ = 0.2D;
            this.aQ -= getGravity();
            this.aQ *= 0.9800000190734863D;
            this.aP *= f2;
            this.aR *= f2;
        }
        this.ak = this.al;
        double d2 = this.aM - this.aJ;
        double d3 = this.aO - this.aL;
        float f5 = MathsHelper.a(d2 * d2 + d3 * d3) * 4.0F;
        if (f5 > 1.0F)
            f5 = 1.0F;
        this.al += (f5 - this.al) * 0.4F;
        this.am += this.al;
    }

    public boolean p() {
        int i = MathsHelper.b(this.aM);
        int j = MathsHelper.b(this.aW.b);
        int k = MathsHelper.b(this.aO);
        int blockID = this.aI.a(i, j, k);
        int blockIDAbove = this.aI.a(i, j + 1, k);
        boolean v = (this.aI.e(i, j, k) % 3 == 0);
        boolean vAbove = (this.aI.e(i, j + 1, k) % 3 == 0);
        return (LadderTile.isLadderID(blockID) || LadderTile.isLadderID(blockIDAbove) || (blockID == AC_Blocks.ropes1.bn && v) || (blockIDAbove == AC_Blocks.ropes1.bn && vAbove) || (blockID == AC_Blocks.ropes2.bn && v) || (blockIDAbove == AC_Blocks.ropes2.bn && vAbove) || (blockID == AC_Blocks.chains.bn && v) || (blockIDAbove == AC_Blocks.chains.bn && vAbove));
    }

    public void b(CompoundTag nbttagcompound) {
        nbttagcompound.a("Health", (short) this.Y);
        nbttagcompound.a("MaxHealth", (short) this.maxHealth);
        nbttagcompound.a("HurtTime", (short) this.aa);
        nbttagcompound.a("DeathTime", (short) this.ad);
        nbttagcompound.a("AttackTime", (short) this.ae);
        nbttagcompound.a("EntityID", this.aD);
        nbttagcompound.a("timesCanJumpInAir", this.timesCanJumpInAir);
        nbttagcompound.a("canWallJump", this.canWallJump);
        nbttagcompound.a("fov", this.fov);
        nbttagcompound.a("canLookRandomly", this.canLookRandomly);
        nbttagcompound.a("randomLookVelocity", this.randomLookVelocity);
        nbttagcompound.a("randomLookRate", this.randomLookRate);
        nbttagcompound.a("randomLookRateVariation", this.randomLookRateVariation);
    }

    public void a(CompoundTag nbttagcompound) {
        this.Y = nbttagcompound.d("Health");
        if (!nbttagcompound.b("Health"))
            this.Y = 10;
        if (!nbttagcompound.b("MaxHealth")) {
            this.maxHealth = 10;
        } else {
            this.maxHealth = nbttagcompound.d("MaxHealth");
        }
        this.aa = nbttagcompound.d("HurtTime");
        this.ad = nbttagcompound.d("DeathTime");
        this.ae = nbttagcompound.d("AttackTime");
        if (nbttagcompound.b("EntityID") && !(this instanceof gs))
            this.aD = nbttagcompound.e("EntityID");
        this.timesCanJumpInAir = nbttagcompound.e("timesCanJumpInAir");
        this.canWallJump = nbttagcompound.m("canWallJump");
        if (nbttagcompound.b("fov"))
            this.fov = nbttagcompound.g("fov");
        if (nbttagcompound.b("canLookRandomly"))
            this.canLookRandomly = nbttagcompound.m("canLookRandomly");
        if (nbttagcompound.b("randomLookVelocity"))
            this.randomLookVelocity = nbttagcompound.g("randomLookVelocity");
        if (nbttagcompound.b("randomLookRate"))
            this.randomLookRate = nbttagcompound.e("randomLookRate");
        if (nbttagcompound.b("randomLookRateVariation"))
            this.randomLookRateVariation = nbttagcompound.e("randomLookRateVariation");
    }

    public boolean W() {
        return (!this.be && this.Y > 0);
    }

    public boolean d_() {
        return false;
    }

    public void o() {
        if (this.an > 0) {
            double d = this.aM + (this.ao - this.aM) / this.an;
            double d1 = this.aN + (this.ap - this.aN) / this.an;
            double d2 = this.aO + (this.aq - this.aO) / this.an;
            double d3;
            for (d3 = this.ar - this.aS; d3 < -180.0D; d3 += 360.0D) ;
            for (; d3 >= 180.0D; d3 -= 360.0D) ;
            this.aS = (float) (this.aS + d3 / this.an);
            this.aT = (float) (this.aT + (this.as - this.aT) / this.an);
            this.an--;
            e(d, d1, d2);
            c(this.aS, this.aT);
            List<eq> list1 = this.aI.a(this, this.aW.e(0.03125D, 0.0D, 0.03125D));
            if (list1.size() > 0) {
                double d4 = 0.0D;
                for (int j = 0; j < list1.size(); j++) {
                    eq axisalignedbb = list1.get(j);
                    if (axisalignedbb.e > d4)
                        d4 = axisalignedbb.e;
                }
                d1 += d4 - this.aW.b;
                e(d, d1, d2);
            }
        }
        if (y()) {
            this.az = false;
            this.aw = 0.0F;
            this.ax = 0.0F;
            this.ay = 0.0F;
        } else if (!this.V) {
            f_();
        }
        boolean flag = ag();
        boolean flag1 = ah();
        if (this.aX)
            this.jumpsLeft = this.timesCanJumpInAir;
        if (this.moveYawOffset != 0.0F)
            if (this.moveYawOffset > 40.0F) {
                this.moveYawOffset -= 40.0F;
                this.aS += 40.0F;
            } else if (this.moveYawOffset < -40.0F) {
                this.moveYawOffset += 40.0F;
                this.aS -= 40.0F;
            } else {
                this.aS += this.moveYawOffset;
                this.moveYawOffset = 0.0F;
            }
        if (this.az)
            if (flag) {
                this.aQ += 0.03999999910593033D;
            } else if (flag1) {
                this.aQ += 0.03999999910593033D;
            } else if (this.aX) {
                R();
            } else if (this.aI.t() >= this.tickBeforeNextJump) {
                if (this.canWallJump && (this.collisionX != 0 || this.collisionZ != 0)) {
                    R();
                    this.aQ *= this.jumpWallMultiplier;
                    this.aP += -this.collisionX * 0.325D;
                    this.aR += -this.collisionZ * 0.325D;
                    this.moveYawOffset = (float) (180.0D * Math.atan2(-this.aP, this.aR) / Math.PI) - this.aS;
                    for (; this.moveYawOffset >= 180.0D; this.moveYawOffset = (float) (this.moveYawOffset - 360.0D)) ;
                    for (; this.moveYawOffset < -180.0D; this.moveYawOffset = (float) (this.moveYawOffset + 360.0D)) ;
                    for (int i = 0; i < 10; i++)
                        this.aI.a("reddust", this.aM + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, this.aN - 0.20000000298023224D, this.aO + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, 2.5D, 2.5D, 2.5D);
                } else if (this.jumpsLeft > 0) {
                    this.jumpsLeft--;
                    R();
                    this.aQ *= this.jumpInAirMultiplier;
                    for (int i = 0; i < 10; i++)
                        this.aI.a("reddust", this.aM + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, this.aN - 0.20000000298023224D, this.aO + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, 2.5D, 2.5D, 2.5D);
                }
            }
        this.aw *= 0.98F;
        this.ax *= 0.98F;
        this.ay *= 0.9F;
        a_(this.aw, this.ax);
        List<Entity> list = this.aI.b(this, this.aW.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        if (list != null && list.size() > 0)
            for (int i = 0; i < list.size(); i++) {
                Entity entity = list.get(i);
                if (entity.i_())
                    entity.h(this);
            }
    }

    protected boolean y() {
        return (this.Y <= 0);
    }

    protected void R() {
        this.tickBeforeNextJump = this.aI.t() + 5L;
        this.aQ = this.jumpVelocity;
    }

    protected boolean u() {
        return true;
    }

    protected void X() {
        gs entityplayer = this.aI.a(this, -1.0D);
        if (u() && entityplayer != null) {
            double d = entityplayer.aM - this.aM;
            double d1 = entityplayer.aN - this.aN;
            double d2 = entityplayer.aO - this.aO;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384.0D)
                K();
            if (this.av > 600 && this.bs.nextInt(800) == 0)
                if (d3 < 1024.0D) {
                    this.av = 0;
                } else {
                    K();
                }
        }
    }

    protected void f_() {
        this.av++;
        gs entityplayer = this.aI.a(this, -1.0D);
        X();
        this.aw = 0.0F;
        this.ax = 0.0F;
        float f = 8.0F;
        if (this.bs.nextFloat() < 0.02F) {
            gs entityplayer1 = this.aI.a(this, f);
            if (entityplayer1 != null && e(entityplayer1)) {
                this.b = entityplayer1;
                this.aC = 10 + this.bs.nextInt(20);
            }
        }
        if (this.b != null) {
            a(this.b, 10.0F, x());
            if (this.aC-- <= 0 || this.b.be || this.b.g(this) > (f * f))
                this.b = null;
        } else if (this.canLookRandomly) {
            if (this.randomLookNext-- <= 0) {
                float r = this.bs.nextFloat();
                if (r < 0.5D) {
                    this.ay = -this.randomLookVelocity * (r + 0.5F);
                } else {
                    this.ay = this.randomLookVelocity * r;
                }
                this.randomLookNext = this.randomLookRate + this.bs.nextInt(this.randomLookRateVariation);
            }
            this.aS += this.ay;
            this.aT = this.aA;
            this.ay *= 0.95F;
            if (Math.abs(this.ay) < 1.0F)
                this.ay = 0.0F;
        }
        boolean flag = ag();
        boolean flag1 = ah();
        if (flag || flag1)
            this.az = (this.bs.nextFloat() < 0.8F);
    }

    protected int x() {
        return 40;
    }

    public void a(Entity entity, float f, float f1) {
        double d1, d = entity.aM - this.aM;
        double d2 = entity.aO - this.aO;
        if (entity instanceof LivingEntity) {
            LivingEntity entityliving = (LivingEntity) entity;
            d1 = this.aN + w() - entityliving.aN + entityliving.w();
        } else {
            d1 = (entity.aW.b + entity.aW.e) / 2.0D - this.aN + w();
        }
        double d3 = MathsHelper.a(d * d + d2 * d2);
        float f2 = (float) (Math.atan2(d2, d) * 180.0D / 3.1415927410125732D) - 90.0F;
        float f3 = (float) -(Math.atan2(d1, d3) * 180.0D / 3.1415927410125732D);
        this.aT = -b(this.aT, f3, f1);
        this.aS = b(this.aS, f2, f);
    }

    public boolean Y() {
        return (this.b != null);
    }

    public Entity Z() {
        return this.b;
    }

    private float b(float f, float f1, float f2) {
        float f3;
        for (f3 = f1 - f; f3 < -180.0F; f3 += 360.0F) ;
        for (; f3 >= 180.0F; f3 -= 360.0F) ;
        if (f3 > f2)
            f3 = f2;
        if (f3 < -f2)
            f3 = -f2;
        return f + f3;
    }

    public void aa() {
    }

    public boolean d() {
        return (this.aI.a(this.aW) && this.aI.a(this, this.aW).size() == 0 && !this.aI.b(this.aW));
    }

    protected void ab() {
        a(null, 4);
    }

    public float d(float f) {
        float f1 = this.X - this.W;
        if (f1 < 0.0F)
            f1++;
        return this.W + f1 * f;
    }

    public bt e(float f) {
        if (f == 1.0F)
            return bt.b(this.aM, this.aN, this.aO);
        double d = this.aJ + (this.aM - this.aJ) * f;
        double d1 = this.aK + (this.aN - this.aK) * f;
        double d2 = this.aL + (this.aO - this.aL) * f;
        return bt.b(d, d1, d2);
    }

    public bt ac() {
        return f(1.0F);
    }

    public bt f(float f) {
        if (f == 1.0F) {
            float f1 = MathsHelper.b(-this.aS * 0.01745329F - 3.141593F);
            float f3 = MathsHelper.a(-this.aS * 0.01745329F - 3.141593F);
            float f5 = -MathsHelper.b(-this.aT * 0.01745329F);
            float f7 = MathsHelper.a(-this.aT * 0.01745329F);
            return bt.b((f3 * f5), f7, (f1 * f5));
        }
        float f2 = this.aV + (this.aT - this.aV) * f;
        float f4 = this.aU + (this.aS - this.aU) * f;
        float f6 = MathsHelper.b(-f4 * 0.01745329F - 3.141593F);
        float f8 = MathsHelper.a(-f4 * 0.01745329F - 3.141593F);
        float f9 = -MathsHelper.b(-f2 * 0.01745329F);
        float f10 = MathsHelper.a(-f2 * 0.01745329F);
        return bt.b((f8 * f9), f10, (f6 * f9));
    }

    public vf a(double d, float f) {
        bt vec3d = e(f);
        bt vec3d1 = f(f);
        bt vec3d2 = vec3d.c(vec3d1.a * d, vec3d1.b * d, vec3d1.c * d);
        return this.aI.a(vec3d, vec3d2);
    }

    public int l() {
        return 4;
    }

    public ItemInstance r_() {
        return this.heldItem;
    }

    public void a(byte byte0) {
        if (byte0 == 2) {
            this.al = 1.5F;
            this.by = this.E;
            this.aa = this.ab = 10;
            this.ac = 0.0F;
            this.aI.a(this, j_(), k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
            a(null, 0);
        } else if (byte0 == 3) {
            this.aI.a(this, i(), k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
            this.Y = 0;
            b((Entity) null);
        } else {
            super.a(byte0);
        }
    }

    public boolean N() {
        return false;
    }

    public int c(ItemInstance itemstack) {
        return itemstack.b();
    }

    public boolean protectedByShield() {
        return false;
    }

    public boolean protectedByShield(double x, double y, double z) {
        if (!protectedByShield() || d(1.0F) > 0.0F)
            return false;
        double diffX = this.aM - x;
        double diffZ = this.aO - z;
        float angle = -57.29578F * (float) Math.atan2(diffX, diffZ) + 180.0F;
        float diff = Math.abs(angle - this.aS);
        while (diff > 180.0F)
            diff -= 360.0F;
        while (diff < -180.0F)
            diff += 360.0F;
        return (diff < 50.0F);
    }

    public double getGravity() {
        return this.gravity;
    }
}
