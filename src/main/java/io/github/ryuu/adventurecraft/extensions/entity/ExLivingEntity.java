package io.github.ryuu.adventurecraft.extensions.entity;

import net.minecraft.item.ItemInstance;

public interface ExLivingEntity extends ExEntity {

    void applyDamage(int damage);

    double getGravity();

    int getMaxHealth();

    void setMaxHealth(int i);

    boolean protectedByShield();

    boolean protectedByShield(double x, double y, double z);

    ItemInstance getHeldItem();

    void setHeldItem(ItemInstance itemInstance);
}
