package io.github.ryuu.adventurecraft.entities;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;

public class EntitySkeletonBoss extends Skeleton {

    public EntitySkeletonBoss(Level world) {
        super(world);
        this.setSize(this.width * 2.5f, this.height * 2.5f);
        this.movementSpeed = 0.25f;
        this.health = 100;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.fire > 0 && this.fire % 20 != 0 && this.fire % 5 == 0) {
            this.damage(null, 1);
        }
    }

    @Override
    protected void method_637(Entity entity, float f) {
        if (f < 20.0f) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                for (int i = 0; i < 5; ++i) {
                    Arrow entityarrow = new Arrow(this.level, this, this.attackDamage);
                    entityarrow.y += (double) 1.4f;
                    double d2 = entity.y - (double) 0.2f - entityarrow.y;
                    float f1 = MathsHelper.sqrt(d * d + d1 * d1) * 0.2f;
                    this.level.playSound(this, "random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
                    this.level.spawnEntity(entityarrow);
                    entityarrow.method_1291(d, d2 + (double) f1, d1, 0.9f, 36.0f);
                }
                this.attackTime = 30;
            }
            this.yaw = (float) (Math.atan2((double) d1, (double) d) * 180.0 / 3.1415927410125732) - 90.0f;
            if (f < 7.5f) {
                this.field_663 = true;
            }
        }
    }

    @Override
    protected int getMobDrops() {
        return 0;
    }

    @Override
    public boolean damage(Entity target, int amount) {
        if (target != null) {
            return false;
        }
        return super.damage(target, amount);
    }
}
