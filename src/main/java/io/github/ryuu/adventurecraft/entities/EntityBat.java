package io.github.ryuu.adventurecraft.entities;


import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;

import java.util.Random;

public class EntityBat extends FlyingEntity implements MonsterEntityType {
    public int courseChangeCooldown;

    public double waypointX;

    public double waypointY;

    public double waypointZ;

    private Entity targetedEntity;

    private int aggroCooldown;

    boolean movingToTarget;

    int attackCooldown;

    Random bs;

    public EntityBat(Level world) {
        super(world);
        this.courseChangeCooldown = 0;
        this.targetedEntity = null;
        this.aggroCooldown = 0;
        this.texture = "/mob/bat.png";
        this.bs = new Random();
        this.bs.setSeed(System.currentTimeMillis() * 10L + this.id);
        this.movingToTarget = false;
        setSize(0.5F, 0.5F);
        this.health = 5;
        this.maxHealth = 5;
    }

    @Override
    protected void tickHandSwing() {
        if (this.level.difficulty == 0) // maybe difficulty
            remove(); // maybe remove
        if (this.targetedEntity != null && this.targetedEntity.removed) {
            this.targetedEntity = null;
            this.movingToTarget = false;
        }
        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.level.getClosestPlayerTo(this, 100.0D);
            if (this.targetedEntity != null)
                this.aggroCooldown = 20;
        }
        double d = this.waypointX - this.x;
        double d1 = this.waypointY - this.y;
        double d2 = this.waypointZ - this.z;
        double d3 = MathsHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        if (d3 < 1.0D || d3 > 60.0D || this.bs.nextInt(20) == 0)
            if (this.targetedEntity == null || this.bs.nextInt(3) == 0) {
                this.movingToTarget = false;
                this.waypointX = this.x + ((this.bs.nextFloat() * 2.0F - 1.0F) * 4.0F);
                this.waypointY = this.y + ((this.bs.nextFloat() * 2.0F - 1.0F) * 1.0F);
                this.waypointZ = this.z + ((this.bs.nextFloat() * 2.0F - 1.0F) * 4.0F);
            } else {
                this.movingToTarget = true;
                this.waypointX = this.targetedEntity.x;
                this.waypointY = this.targetedEntity.y;
                this.waypointZ = this.targetedEntity.z;
            }
        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.bs.nextInt(5) + 2;
            if (isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                this.velocityX += d / d3 * 0.08D;
                this.velocityY += d1 / d3 * 0.08D;
                this.velocityZ += d2 / d3 * 0.08D;
                this.yaw = -((float) Math.atan2(this.velocityX, this.velocityZ)) * 180.0F / 3.141593F;
            } else {
                this.waypointX = this.x;
                this.waypointY = this.y;
                this.waypointZ = this.z;
            }
        }
        if (this.targetedEntity != null) {
            double d5 = this.targetedEntity.x - this.x;
            double d7 = this.targetedEntity.z - this.z;
            this.field_1012 = -((float) Math.atan2(d5, d7)) * 180.0F / 3.141593F;
            if (this.movingToTarget && this.targetedEntity.method_1352(this) < 2.25D) {
                this.velocityX = 0.0D;
                this.velocityY = 0.0D;
                this.velocityZ = 0.0D;
                this.yaw = this.field_1012;
                if (this.attackCooldown <= 0) {
                    this.targetedEntity.damage(this, this.attackStrength);
                    this.attackCooldown = 10;
                    this.targetedEntity = null;
                }
            }
        }
        if (this.attackCooldown > 0)
            this.attackCooldown--;
    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
        double d4 = (this.waypointX - this.x) / d3;
        double d5 = (this.waypointY - this.y) / d3;
        double d6 = (this.waypointZ - this.z) / d3;
        Box axisalignedbb = this.boundingBox.method_92();
        for (int i = 1; i < d3; i++) {
            axisalignedbb.method_102(d4, d5, d6);
            if (this.level.getEntities(this, axisalignedbb).size() > 0)
                return false;
        }
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6F;
    }

    @Override
    protected String getAmbientSound() {
        return "mob.bat.ambient";
    }

    @Override
    protected String getHurtSound() {
        return "mob.bat.pain";
    }

    @Override
    protected String getDeathSound() {
        return "mob.bat.death";
    }
}