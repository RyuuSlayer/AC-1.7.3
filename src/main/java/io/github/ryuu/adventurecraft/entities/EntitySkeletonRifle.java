package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.entity.monster.MixinSkeleton;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public class EntitySkeletonRifle extends MixinSkeleton {

    int ammo;

    public EntitySkeletonRifle(Level world) {
        super(world);
        this.attackDamage = 6;
        this.ammo = 30;
        this.heldItem = new ItemInstance(Items.rifle, 1);
    }

    @Override
    protected void method_637(Entity entity, float f) {
        if ((double) f < 15.0 && this.rand.nextBoolean()) {
            this.method_924(entity, 30.0f, 30.0f);
            if (this.attackTime == 0) {
                --this.ammo;
                this.method_924(entity, 60.0f, 90.0f);
                this.yaw = (float) ((double) this.yaw + 10.0 * this.rand.nextGaussian());
                this.pitch = (float) ((double) this.pitch + 3.0 * this.rand.nextGaussian());
                this.level.playSound(this, "items.rifle.fire", 1.0f, 1.0f);
                UtilBullet.fireBullet(this.level, this, 0.07f, this.attackDamage);
                this.attackTime = 5;
                if (this.ammo == 0) {
                    this.ammo = 30;
                    this.attackTime = 40;
                }
            }
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            this.yaw = (float) (Math.atan2((double) d1, (double) d) * 180.0 / 3.1415927410125732) - 90.0f;
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
                this.heldItem.timeLeft = this.attackTime - 35;
                if (this.heldItem.timeLeft < 0) {
                    this.heldItem.timeLeft = 0;
                }
            } else {
                this.heldItem.timeLeft = this.attackTime;
            }
            if (this.heldItem.timeLeft < 0) {
                this.heldItem.timeLeft = 0;
            }
        }
    }

    @Override
    protected int getMobDrops() {
        return Items.rifleAmmo.id;
    }
}
