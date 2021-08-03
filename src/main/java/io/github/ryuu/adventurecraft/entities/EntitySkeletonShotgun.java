package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.level.Level;

import java.util.Random;

public class EntitySkeletonShotgun extends fr {
    public EntitySkeletonShotgun(Level world) {
        super(world);
        this.c = 2;
        this.heldItem = new iz(Items.shotgun, 1);
    }

    protected void a(sn entity, float f) {
        if (f < 10.0D && bs.nextBoolean()) {
            a(entity, 30.0F, 30.0F);
            if (this.ae == 0) {
                a(entity, 60.0F, 90.0F);
                this.aS = (float) (this.aS + 12.0D * bs.nextGaussian());
                this.aT = (float) (this.aT + 6.0D * bs.nextGaussian());
                for (int i = 0; i < 8; i++)
                    UtilBullet.fireBullet(this.aI, this, 0.12F, this.c);
                this.ae = 50;
                this.aI.a(this, "items.shotgun.fire_and_pump", 1.0F, 1.0F);
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
            this.heldItem.timeLeft = this.ae;
        }
    }

    protected int j() {
        return Items.shotgunAmmo.bf;
    }

    private static final Random bs = new Random();
}