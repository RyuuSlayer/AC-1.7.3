package io.github.ryuu.adventurecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;

public class EntitySkeletonBoss extends Skeleton {
    public EntitySkeletonBoss(Level world) {
        super(world);
        b(this.bg * 2.5F, this.bh * 2.5F);
        this.aB = 0.25F;
        this.Y = 100;
    }

    public void U() {
        super.U();
        if (this.bv > 0)
            if (this.bv % 20 != 0 && this.bv % 5 == 0)
                a(null, 1);
    }

    protected void a(Entity entity, float f) {
        if (f < 20.0F) {
            double d = entity.aM - this.aM;
            double d1 = entity.aO - this.aO;
            if (this.ae == 0) {
                for (int i = 0; i < 5; i++) {
                    Arrow entityarrow = new Arrow(this.aI, this, this.c);
                    entityarrow.aN += 1.399999976158142D;
                    double d2 = entity.aN - 0.20000000298023224D - entityarrow.aN;
                    float f1 = in.a(d * d + d1 * d1) * 0.2F;
                    this.aI.a(this, "random.bow", 1.0F, 1.0F / (this.bs.nextFloat() * 0.4F + 0.8F));
                    this.aI.b(entityarrow);
                    entityarrow.a(d, d2 + f1, d1, 0.9F, 36.0F);
                }
                this.ae = 30;
            }
            this.aS = (float) (Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - 90.0F;
            if (f < 7.5F)
                this.e = true;
        }
    }

    protected int j() {
        return 0;
    }

    public boolean a(Entity entity, int i) {
        if (entity != null)
            return false;
        return super.a(entity, i);
    }
}
