package io.github.ryuu.adventurecraft.entities;


import net.minecraft.level.Level;

public class EntityBat extends wq implements ff {
    public int courseChangeCooldown;

    public double waypointX;

    public double waypointY;

    public double waypointZ;

    private sn targetedEntity;

    private int aggroCooldown;

    boolean movingToTarget;

    int attackCooldown;

    Random bs;

    public EntityBat(Level world) {
        super(world);
        this.courseChangeCooldown = 0;
        this.targetedEntity = null;
        this.aggroCooldown = 0;
        this.O = "/mob/bat.png";
        this.bs = new Random();
        this.bs.setSeed(System.currentTimeMillis() * 10L + this.aD);
        this.movingToTarget = false;
        b(0.5F, 0.5F);
        this.Y = 5;
        this.maxHealth = 5;
    }

    protected void f_() {
        if (this.aI.q == 0)
            K();
        if (this.targetedEntity != null && this.targetedEntity.be) {
            this.targetedEntity = null;
            this.movingToTarget = false;
        }
        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.aI.a(this, 100.0D);
            if (this.targetedEntity != null)
                this.aggroCooldown = 20;
        }
        double d = this.waypointX - this.aM;
        double d1 = this.waypointY - this.aN;
        double d2 = this.waypointZ - this.aO;
        double d3 = in.a(d * d + d1 * d1 + d2 * d2);
        if (d3 < 1.0D || d3 > 60.0D || this.bs.nextInt(20) == 0)
            if (this.targetedEntity == null || this.bs.nextInt(3) == 0) {
                this.movingToTarget = false;
                this.waypointX = this.aM + ((this.bs.nextFloat() * 2.0F - 1.0F) * 4.0F);
                this.waypointY = this.aN + ((this.bs.nextFloat() * 2.0F - 1.0F) * 1.0F);
                this.waypointZ = this.aO + ((this.bs.nextFloat() * 2.0F - 1.0F) * 4.0F);
            } else {
                this.movingToTarget = true;
                this.waypointX = this.targetedEntity.aM;
                this.waypointY = this.targetedEntity.aN;
                this.waypointZ = this.targetedEntity.aO;
            }
        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.bs.nextInt(5) + 2;
            if (isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                this.aP += d / d3 * 0.08D;
                this.aQ += d1 / d3 * 0.08D;
                this.aR += d2 / d3 * 0.08D;
                this.aS = -((float)Math.atan2(this.aP, this.aR)) * 180.0F / 3.141593F;
            } else {
                this.waypointX = this.aM;
                this.waypointY = this.aN;
                this.waypointZ = this.aO;
            }
        }
        if (this.targetedEntity != null) {
            double d5 = this.targetedEntity.aM - this.aM;
            double d7 = this.targetedEntity.aO - this.aO;
            this.H = -((float)Math.atan2(d5, d7)) * 180.0F / 3.141593F;
            if (this.movingToTarget && this.targetedEntity.g(this) < 2.25D) {
                this.aP = 0.0D;
                this.aQ = 0.0D;
                this.aR = 0.0D;
                this.aS = this.H;
                if (this.attackCooldown <= 0) {
                    this.targetedEntity.a(this, this.attackStrength);
                    this.attackCooldown = 10;
                    this.targetedEntity = null;
                }
            }
        }
        if (this.attackCooldown > 0)
            this.attackCooldown--;
    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
        double d4 = (this.waypointX - this.aM) / d3;
        double d5 = (this.waypointY - this.aN) / d3;
        double d6 = (this.waypointZ - this.aO) / d3;
        eq axisalignedbb = this.aW.d();
        for (int i = 1; i < d3; i++) {
            axisalignedbb.d(d4, d5, d6);
            if (this.aI.a(this, axisalignedbb).size() > 0)
                return false;
        }
        return true;
    }

    protected float k() {
        return 0.6F;
    }

    protected String g() {
        return "mob.bat.ambient";
    }

    protected String j_() {
        return "mob.bat.pain";
    }

    protected String i() {
        return "mob.bat.death";
    }
}