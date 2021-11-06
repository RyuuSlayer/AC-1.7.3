package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;


public class EntityZombiePistol extends Zombie {
    int ammo;

    public EntityZombiePistol(Level world) {
        super(world);
        this.ammo = 15;
        this.heldItem = new ItemInstance(Items.pistol, 1);
        this.attackDamage = 6;
    }

    @Override
    protected void method_637(Entity entity, float f) {
        if (this.attackTime == 0 && this.rand.nextBoolean() && this.rand.nextBoolean()) {
            a(entity, 45.0F, 90.0F);
            this.ammo--;
            this.yaw = (float) (this.yaw + 6.0D * this.rand.nextGaussian());
            this.pitch = (float) (this.pitch + 2.0D * this.rand.nextGaussian());
            this.level.playSound(this, "items.pistol.fire", 1.0F, 1.0F);
            UtilBullet.fireBullet(this.level, this, 0.08F, this.attackDamage);
            this.attackDamage = 20;
            if (this.ammo == 0) {
                this.ammo = 15;
                this.attackDamage = 50;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.health <= 0) {
            this.heldItem.timeLeft = 0;
        } else {
            if (this.ammo == 15) {
                this.heldItem.timeLeft = this.attackDamage - 43;
            } else {
                this.heldItem.timeLeft = this.attackDamage - 13;
            }
            if (this.heldItem.timeLeft < 0)
                this.heldItem.timeLeft = 0;
        }
    }

    protected void q() {
        int i = this.rand.nextInt(3) + 1;
        for (int j = 0; j < i; j++) {
            ItemEntity item = dropItem(getMobDrops(), 1);
            item.item.count = 5;
        }
    }

    @Override
    protected int getMobDrops() {
        return Items.pistolAmmo.id;
    }
}
