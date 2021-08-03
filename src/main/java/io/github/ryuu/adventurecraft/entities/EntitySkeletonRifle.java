package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.level.Level;

public class EntitySkeletonRifle extends Skeleton {
    int ammo;

    public EntitySkeletonRifle(Level world) {
        super(world);
        this.c = 6;
        this.ammo = 30;
        this.heldItem = new iz(Items.rifle, 1);
    }

    protected void a(sn entity, float f) {
        if (f < 15.0D && this.bs.nextBoolean()) {
            a(entity, 30.0F, 30.0F);
            if (this.ae == 0) {
                this.ammo--;
                a(entity, 60.0F, 90.0F);
                this.aS = (float) (this.aS + 10.0D * this.bs.nextGaussian());
                this.aT = (float) (this.aT + 3.0D * this.bs.nextGaussian());
                this.aI.a(this, "items.rifle.fire", 1.0F, 1.0F);
                UtilBullet.fireBullet(this.aI, this, 0.07F, this.c);
                this.ae = 5;
                if (this.ammo == 0) {
                    this.ammo = 30;
                    this.ae = 40;
                }
            }
            double d = entity.aM - this.aM;
            double d1 = entity.aO - this.aO;
            this.aS = (float) (Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - 90.0F;
            this.e = true;
        }
    }

    public void w_() {
        super.w_();
        if (this.Y <= 0) {
            this.heldItem.timeLeft = 0;
        } else {
            if (this.ammo == 30) {
                this.heldItem.timeLeft = this.ae - 35;
                if (this.heldItem.timeLeft < 0)
                    this.heldItem.timeLeft = 0;
            } else {
                this.heldItem.timeLeft = this.ae;
            }
            if (this.heldItem.timeLeft < 0)
                this.heldItem.timeLeft = 0;
        }
    }

    protected int j() {
        return Items.rifleAmmo.bf;
    }
}