package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public class EntitySkeletonRifle extends Skeleton {
    int ammo;

    public EntitySkeletonRifle(Level world) {
        super(world);
        this.attackDamage = 6; // c = attackDamage (for future)
        this.ammo = 30;
        this.heldItem = new ItemInstance(Items.rifle, 1);
    }

    @Override
    protected void method_637(Entity entity, float f) {
        if (f < 15.0D && this.rand.nextBoolean()) {
            a(entity, 30.0F, 30.0F);
            if (this.attackTime == 0) {
                this.ammo--;
                a(entity, 60.0F, 90.0F);
                this.yaw = (float) (this.yaw + 10.0D * this.rand.nextGaussian());
                this.pitch = (float) (this.pitch + 3.0D * this.rand.nextGaussian());
                this.level.playSound(this, "items.rifle.fire", 1.0F, 1.0F);
                UtilBullet.fireBullet(this.level, this, 0.07F, this.attackDamage);
                this.attackTime = 5;
                if (this.ammo == 0) {
                    this.ammo = 30;
                    this.attackTime = 40;
                }
            }
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            this.yaw = (float) (Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - 90.0F;
            this.field_663 = true;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.health <= 0) {
            this.heldItem.timeLeft = 0;
        } else {
            if (this.ammo == 30) {
                this.heldItem.timeLeft = this.ae - 35;
                if (this.heldItem.timeLeft < 0)
                    this.heldItem.timeLeft = 0;
            } else {
                this.heldItem.timeLeft = this.attackTime;
            }
            if (this.heldItem.timeLeft < 0)
                this.heldItem.timeLeft = 0;
        }
    }

    @Override
    protected int getMobDrops() {
        return Items.rifleAmmo.id;
    }
}