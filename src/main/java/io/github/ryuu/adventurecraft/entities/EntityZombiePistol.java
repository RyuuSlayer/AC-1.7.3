package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.level.Level;


public class EntityZombiePistol extends uz {
    int ammo;

    public EntityZombiePistol(Level world) {
        super(world);
        this.ammo = 15;
        this.heldItem = new iz(Items.pistol, 1);
        this.c = 6;
    }

    protected void a(sn entity, float f) {
        if (this.ae == 0 && this.bs.nextBoolean() && this.bs.nextBoolean()) {
            a(entity, 45.0F, 90.0F);
            this.ammo--;
            this.aS = (float) (this.aS + 6.0D * this.bs.nextGaussian());
            this.aT = (float) (this.aT + 2.0D * this.bs.nextGaussian());
            this.aI.a(this, "items.pistol.fire", 1.0F, 1.0F);
            UtilBullet.fireBullet(this.aI, this, 0.08F, this.c);
            this.ae = 20;
            if (this.ammo == 0) {
                this.ammo = 15;
                this.ae = 50;
            }
        }
    }

    public void w_() {
        super.w_();
        if (this.Y <= 0) {
            this.heldItem.timeLeft = 0;
        } else {
            if (this.ammo == 15) {
                this.heldItem.timeLeft = this.ae - 43;
            } else {
                this.heldItem.timeLeft = this.ae - 13;
            }
            if (this.heldItem.timeLeft < 0)
                this.heldItem.timeLeft = 0;
        }
    }

    protected void q() {
        int i = this.bs.nextInt(3) + 1;
        for (int j = 0; j < i; j++) {
            hl item = b(j(), 1);
            item.a.a = 5;
        }
    }

    protected int j() {
        return Items.pistolAmmo.bf;
    }
}
