package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.extensions.entity.ExFlyingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;

import java.util.Random;

public class EntityBat extends FlyingEntity implements MonsterEntityType {

    public int courseChangeCooldown = 0;

    public double waypointX;

    public double waypointY;

    public double waypointZ;
    boolean movingToTarget;
    int attackCooldown;
    Random bs;
    private Entity targetedEntity = null;
    private int aggroCooldown = 0;

    public EntityBat(Level world) {
        super(world);
        this.texture = "/mob/bat.png";
        this.bs = new Random();
        this.bs.setSeed(System.currentTimeMillis() * 10L + (long) this.id);
        this.movingToTarget = false;
        this.setSize(0.5f, 0.5f);
        this.health = 5;
        ((ExLivingEntity) this).setMaxHealth(5);
    }

    @Override
    protected void tickHandSwing() {
        double d2;
        double d1;
        double d;
        double d3;
        if (this.level.difficulty == 0) {
            this.remove();
        }
        if (this.targetedEntity != null && this.targetedEntity.removed) {
            this.targetedEntity = null;
            this.movingToTarget = false;
        }
        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.level.getClosestPlayerTo(this, 100.0);
            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
            }
        }
        if ((d3 = MathsHelper.sqrt((d = this.waypointX - this.x) * d + (d1 = this.waypointY - this.y) * d1 + (d2 = this.waypointZ - this.z) * d2)) < 1.0 || d3 > 60.0 || this.bs.nextInt(20) == 0) {
            if (this.targetedEntity == null || this.bs.nextInt(3) == 0) {
                this.movingToTarget = false;
                this.waypointX = this.x + (double) ((this.bs.nextFloat() * 2.0f - 1.0f) * 4.0f);
                this.waypointY = this.y + (double) ((this.bs.nextFloat() * 2.0f - 1.0f) * 1.0f);
                this.waypointZ = this.z + (double) ((this.bs.nextFloat() * 2.0f - 1.0f) * 4.0f);
            } else {
                this.movingToTarget = true;
                this.waypointX = this.targetedEntity.x;
                this.waypointY = this.targetedEntity.y;
                this.waypointZ = this.targetedEntity.z;
            }
        }
        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.bs.nextInt(5) + 2;
            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                this.velocityX += d / d3 * 0.08;
                this.velocityY += d1 / d3 * 0.08;
                this.velocityZ += d2 / d3 * 0.08;
                this.yaw = -((float) Math.atan2(this.velocityX, this.velocityZ)) * 180.0f / 3.141593f;
            } else {
                this.waypointX = this.x;
                this.waypointY = this.y;
                this.waypointZ = this.z;
            }
        }
        if (this.targetedEntity != null) {
            double d5 = this.targetedEntity.x - this.x;
            double d7 = this.targetedEntity.z - this.z;
            this.field_1012 = -((float) Math.atan2(d5, d7)) * 180.0f / 3.141593f;
            if (this.movingToTarget && this.targetedEntity.method_1352(this) < 2.25) {
                this.velocityX = 0.0;
                this.velocityY = 0.0;
                this.velocityZ = 0.0;
                this.yaw = this.field_1012;
                if (this.attackCooldown <= 0) {
                    this.targetedEntity.damage(this, ((ExFlyingEntity) this).getAttackStrength());
                    this.attackCooldown = 10;
                    this.targetedEntity = null;
                }
            }
        }
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
        double d4 = (this.waypointX - this.x) / d3;
        double d5 = (this.waypointY - this.y) / d3;
        double d6 = (this.waypointZ - this.z) / d3;
        Box axisalignedbb = this.boundingBox.method_92();
        int i = 1;
        while ((double) i < d3) {
            axisalignedbb.method_102(d4, d5, d6);
            if (this.level.method_190(this, axisalignedbb).size() > 0) {
                return false;
            }
            ++i;
        }
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
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
