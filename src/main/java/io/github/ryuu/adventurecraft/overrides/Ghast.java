package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class Ghast extends FlyingEntity implements MonsterEntityType {
    public int a;

    public double b;

    public double c;

    public double d;

    private Entity g;

    private int h;

    public int e;

    public int f;

    public Ghast(Level world) {
        super(world);
        this.a = 0;
        this.g = null;
        this.h = 0;
        this.e = 0;
        this.f = 0;
        this.O = "/mob/ghast.png";
        b(4.0F, 4.0F);
        this.bC = true;
    }

    protected void b() {
        super.b();
        this.bD.a(16, Byte.valueOf((byte) 0));
    }

    public void w_() {
        super.w_();
        byte byte0 = this.bD.a(16);
        this.O = (byte0 != 1) ? "/mob/ghast.png" : "/mob/ghast_fire.png";
    }

    protected void f_() {
        if (!this.aI.B && this.aI.q == 0)
            K();
        X();
        this.e = this.f;
        double d = this.b - this.aM;
        double d1 = this.c - this.aN;
        double d2 = this.d - this.aO;
        double d3 = in.a(d * d + d1 * d1 + d2 * d2);
        if (d3 < 1.0D || d3 > 60.0D) {
            this.b = this.aM + ((this.bs.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.c = this.aN + ((this.bs.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.d = this.aO + ((this.bs.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }
        if (this.a-- <= 0) {
            this.a += this.bs.nextInt(5) + 2;
            if (a(this.b, this.c, this.d, d3)) {
                this.aP += d / d3 * 0.1D;
                this.aQ += d1 / d3 * 0.1D;
                this.aR += d2 / d3 * 0.1D;
            } else {
                this.b = this.aM;
                this.c = this.aN;
                this.d = this.aO;
            }
        }
        if (this.g != null && this.g.be)
            this.g = null;
        if (this.g == null || this.h-- <= 0) {
            this.g = this.aI.a(this, 100.0D);
            if (this.g != null)
                this.h = 20;
        }
        double d4 = 64.0D;
        if (this.g != null && this.g.g(this) < d4 * d4) {
            double d5 = this.g.aM - this.aM;
            double d6 = this.g.aW.b + (this.g.bh / 2.0F) - this.aN + (this.bh / 2.0F);
            double d7 = this.g.aO - this.aO;
            this.H = this.aS = -((float) Math.atan2(d5, d7)) * 180.0F / 3.141593F;
            if (e(this.g)) {
                if (this.f == 10)
                    this.aI.a(this, "mob.ghast.charge", k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
                this.f++;
                if (this.f == 20) {
                    this.aI.a(this, "mob.ghast.fireball", k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
                    Snowball entityfireball = new Snowball(this.aI, this, d5, d6, d7, this.attackStrength);
                    double d8 = 4.0D;
                    bt vec3d = f(1.0F);
                    entityfireball.aM = this.aM + vec3d.a * d8;
                    entityfireball.aN = this.aN + (this.bh / 2.0F) + 0.5D;
                    entityfireball.aO = this.aO + vec3d.c * d8;
                    this.aI.b(entityfireball);
                    this.f = -40;
                }
            } else if (this.f > 0) {
                this.f--;
            }
        } else {
            this.H = this.aS = -((float) Math.atan2(this.aP, this.aR)) * 180.0F / 3.141593F;
            if (this.f > 0)
                this.f--;
        }
        if (!this.aI.B) {
            byte byte0 = this.bD.a(16);
            byte byte1 = (byte) ((this.f <= 10) ? 0 : 1);
            if (byte0 != byte1)
                this.bD.b(16, Byte.valueOf(byte1));
        }
    }

    private boolean a(double d, double d1, double d2, double d3) {
        double d4 = (this.b - this.aM) / d3;
        double d5 = (this.c - this.aN) / d3;
        double d6 = (this.d - this.aO) / d3;
        eq axisalignedbb = this.aW.d();
        for (int i = 1; i < d3; i++) {
            axisalignedbb.d(d4, d5, d6);
            if (this.aI.a(this, axisalignedbb).size() > 0)
                return false;
        }
        return true;
    }

    protected String g() {
        return "mob.ghast.moan";
    }

    protected String j_() {
        return "mob.ghast.scream";
    }

    protected String i() {
        return "mob.ghast.death";
    }

    protected int j() {
        return ItemType.K.bf;
    }

    protected float k() {
        return 10.0F;
    }

    public boolean d() {
        return (this.bs.nextInt(20) == 0 && super.d() && this.aI.q > 0);
    }

    public int l() {
        return 1;
    }
}
