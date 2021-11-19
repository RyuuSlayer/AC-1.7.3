package io.github.ryuu.adventurecraft.mixin.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.entity.monster.ZombiePigman;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

import java.util.List;

public class MixinZombiePigman extends Zombie {
    private int anger = 0;
    private int field_2243 = 0;

    public MixinZombiePigman(Level world) {
        super(world);
        this.texture = "/mob/pigzombie.png";
        this.movementSpeed = 0.5f;
        this.attackDamage = 5;
        this.immuneToFire = true;
        this.heldItem = new ItemInstance(ItemType.swordGold, 1);
    }

    public void tick() {
        float f = this.movementSpeed = this.entity == null ? 0.5f : 0.95f;
        if (this.field_2243 > 0 && --this.field_2243 == 0) {
            this.level.playSound(this, "mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.tick();
    }

    public boolean canSpawn() {
        return this.level.difficulty > 0 && this.level.canSpawnEntity(this.boundingBox) && this.level.method_190(this, this.boundingBox).size() == 0 && !this.level.method_218(this.boundingBox);
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("Anger", (short)this.anger);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.anger = tag.getShort("Anger");
    }

    protected Entity method_638() {
        if (this.anger == 0) {
            return null;
        }
        return super.method_638();
    }

    public void updateDespawnCounter() {
        super.updateDespawnCounter();
    }

    public boolean damage(Entity target, int amount) {
        if (target instanceof Player) {
            List list = this.level.getEntities(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            for (int j = 0; j < list.size(); ++j) {
                Entity entity1 = (Entity)list.get(j);
                if (!(entity1 instanceof ZombiePigman)) continue;
                ZombiePigman entitypigzombie = (ZombiePigman)entity1;
                entitypigzombie.method_1792(target);
            }
            this.method_1792(target);
        }
        return super.damage(target, amount);
    }

    private void method_1792(Entity entity) {
        this.entity = entity;
        this.anger = 400 + this.rand.nextInt(400);
        this.field_2243 = this.rand.nextInt(40);
    }

    protected String getAmbientSound() {
        return "mob.zombiepig.zpig";
    }

    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    protected int getMobDrops() {
        return ItemType.porkchopCooked.id;
    }
}