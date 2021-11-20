package io.github.ryuu.adventurecraft.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

public class EntitySkeletonSword extends MixinSkeleton {

    public EntitySkeletonSword(MixinLevel world) {
        super(world);
        this.attackDamage = 1;
        this.heldItem = new MixinItemInstance(ItemType.swordWood, 1);
    }

    @Override
    protected void method_637(MixinEntity entity, float f) {
        if ((double) f < 2.5 && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            entity.damage(this, this.attackDamage);
        }
    }

    @Override
    protected int getMobDrops() {
        return 0;
    }
}
