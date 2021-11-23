package io.github.ryuu.adventurecraft.entities;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public class EntityZombiePistol extends Zombie {

    int ammo = 15;

    public EntityZombiePistol(Level world) {
        super(world);
        this.heldItem = new ItemInstance(Items.pistol, 1);
        this.attackDamage = 6;
    }

    @Override
    protected void method_637(Entity entity, float f) {
        if (this.attackTime == 0 && this.rand.nextBoolean() && this.rand.nextBoolean()) {
            this.method_924(entity, 45.0f, 90.0f);
            --this.ammo;
            this.yaw = (float) ((double) this.yaw + 6.0 * this.rand.nextGaussian());
            this.pitch = (float) ((double) this.pitch + 2.0 * this.rand.nextGaussian());
            this.level.playSound(this, "items.pistol.fire", 1.0f, 1.0f);
            UtilBullet.fireBullet(this.level, this, 0.08f, this.attackDamage);
            this.attackTime = 20;
            if (this.ammo == 0) {
                this.ammo = 15;
                this.attackTime = 50;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.health <= 0) {
            this.heldItem.timeLeft = 0;
        } else {
            this.heldItem.timeLeft = this.ammo == 15 ? this.attackTime - 43 : this.attackTime - 13;
            if (this.heldItem.timeLeft < 0) {
                this.heldItem.timeLeft = 0;
            }
        }
    }

    @Override
    protected void dropLoot() {
        int i = this.rand.nextInt(3) + 1;
        for (int j = 0; j < i; ++j) {
            ItemEntity item = this.dropItem(this.getMobDrops(), 1);
            item.item.count = 5;
        }
    }

    @Override
    protected int getMobDrops() {
        return Items.pistolAmmo.id;
    }
}
