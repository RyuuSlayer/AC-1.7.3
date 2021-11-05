package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

import java.util.Random;

public class EntitySkeletonShotgun extends Skeleton {
    private static final Random bs = new Random();

    public EntitySkeletonShotgun(Level world) {
        super(world);
        this.attackDamage = 2;
        this.heldItem = new ItemInstance(Items.shotgun, 1);
    }

    @Override
    protected void method_637(Entity entity, float f) { // method_637 is probably for damaging and shooting
        if (f < 10.0D && bs.nextBoolean()) {
            a(entity, 30.0F, 30.0F);
            if (this.attackTime == 0) {
                a(entity, 60.0F, 90.0F);
                this.yaw = (float) (this.yaw + 12.0D * bs.nextGaussian());
                this.pitch = (float) (this.pitch + 6.0D * bs.nextGaussian());
                for (int i = 0; i < 8; i++)
                    UtilBullet.fireBullet(this.level, this, 0.12F, this.attackDamage);
                this.attackTime = 50;
                this.level.playSound(this, "items.shotgun.fire_and_pump", 1.0F, 1.0F);
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
            this.heldItem.timeLeft = this.attackTime;
        }
    }

    @Override
    protected int getMobDrops() {
        return Items.shotgunAmmo.id;
    }
}