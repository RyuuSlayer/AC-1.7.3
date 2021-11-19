package io.github.ryuu.adventurecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;

public class EntitySkeletonBoss extends Skeleton {
    public EntitySkeletonBoss(Level world) {
        super(world);
        setSize(this.width * 2.5F, this.height * 2.5F);
        this.movementSpeed = 0.25F;
        this.health = 100;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.fire > 0)
            if (this.fire % 20 != 0 && this.fire % 5 == 0)
                damage(null, 1);
    }


    @Override
    protected void method_637(Entity entity, float f) {
        if (f < 20.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                for (int i = 0; i < 5; i++) {
                    Arrow entityarrow = new Arrow(this.level, this, this.attackDamage);
                    entityarrow.y += 1.399999976158142D;
                    double d2 = entity.y - 0.20000000298023224D - entityarrow.y;
                    float f1 = MathsHelper.sqrt(d * d + d1 * d1) * 0.2F;
                    this.level.playSound(this, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
                    this.level.spawnEntity(entityarrow);
                    entityarrow.method_1291(d, d2 + f1, d1, 0.9F, 36.0F);
                }
                this.attackTime = 30;
            }
            this.yaw = (float) (Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - 90.0F;
            if (f < 7.5F)
                this.field_663 = true;
        }
    }

    @Override
    protected int getMobDrops() {
        return 0;
    }

    @Override
    public boolean damage(Entity entity, int i) {
        if (entity != null)
            return false;
        return super.damage(entity, i);
    }
}
