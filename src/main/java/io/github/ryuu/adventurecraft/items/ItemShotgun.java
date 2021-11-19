package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemShotgun extends ItemType implements IItemReload {
    public ItemShotgun(int itemIndex) {
        super(itemIndex);
        this.maxStackSize = 1;
        this.itemUseDelay = 1;
    }

    @Override
    public ItemInstance use(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.timeLeft > 0) {
            if (itemstack.isReloading && itemstack.i() > 0) {
                itemstack.isReloading = false;
                return itemstack;
            }
            return itemstack;
        }
        if (itemstack.getDamage() == itemstack.method_723()) {
            itemstack.isReloading = true;
            itemstack.timeLeft = 0;
            return itemstack;
        }
        world.playSound(entityplayer, "items.shotgun.fire_and_pump", 1.0F, 1.0F);
        for (int i = 0; i < 14; i++)
            UtilBullet.fireBullet(world, entityplayer, 0.12F, 2);
        itemstack.setDamage(itemstack.getDamage() + 1);
        itemstack.timeLeft = 40;
        if (itemstack.getDamage() == itemstack.method_723())
            itemstack.isReloading = true;
        return itemstack;
    }

    public boolean isLighting(ItemInstance itemstack) {
        return itemstack.timeLeft > 42;
    }

    public boolean isMuzzleFlash(ItemInstance itemstack) {
        return itemstack.timeLeft > 35;
    }

    @Override
    public void reload(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.shotgunAmmo.id)) {
            itemstack.setDamage(itemstack.getDamage() - 1);
            itemstack.timeLeft = 20;
            world.playSound(entityplayer, "items.shotgun.reload", 1.0F, 1.0F);
            if (itemstack.getDamage() == 0)
                itemstack.isReloading = false;
        } else {
            itemstack.isReloading = false;
        }
    }
}
