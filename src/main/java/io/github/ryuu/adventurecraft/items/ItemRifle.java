package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemRifle extends ItemType implements IItemReload {
    public ItemRifle(int itemIndex) {
        super(itemIndex);
        this.maxStackSize = 1;
        this.itemUseDelay = 1;
    }

    @Override
    public ItemInstance use(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.timeLeft > 3 || itemstack.isReloading)
            return itemstack;
        if (itemstack.getDamage() == itemstack.method_723()) {
            itemstack.isReloading = true;
            return itemstack;
        }
        itemstack.justReloaded = false;
        world.playSound(entityplayer, "items.rifle.fire", 1.0F, 1.0F);
        UtilBullet.fireBullet(world, entityplayer, 0.04F * itemstack.timeLeft + 0.03F, 10);
        itemstack.setDamage(itemstack.getDamage() + 1);
        itemstack.timeLeft = 6;
        if (entityplayer.pitch > -90.0F)
            entityplayer.pitch--;
        if (itemstack.getDamage() == itemstack.method_723())
            itemstack.isReloading = true;
        return itemstack;
    }

    public boolean isLighting(ItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 3;
    }

    public boolean isMuzzleFlash(ItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 3;
    }

    @Override
    public void reload(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.rifleAmmo.id)) {
            itemstack.setDamage(itemstack.getDamage() - 1);
            while (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.rifleAmmo.id))
                itemstack.setDamage(itemstack.getDamage() - 1);
            itemstack.timeLeft = 32;
            world.playSound(entityplayer, "items.clipReload", 1.0F, 1.0F);
        } else {
            world.playSound(entityplayer, "items.dryFire", 1.0F, 1.0F);
            itemstack.timeLeft = 4;
        }
        itemstack.isReloading = false;
        itemstack.justReloaded = true;
    }
}
