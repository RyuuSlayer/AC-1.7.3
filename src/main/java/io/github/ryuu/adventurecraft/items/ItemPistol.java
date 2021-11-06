package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemPistol extends ItemType implements IItemReload {
    public ItemPistol(int itemIndex) {
        super(itemIndex);
        this.maxStackSize = 1;
        this.itemUseDelay = 1;
    }

    // getDamage could be wrong right here. But by the looks of it, it makes sense :)
    @Override
    public ItemInstance use(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.timeLeft > 0 || itemstack.isReloading)
            return itemstack;
        if (itemstack.getDamage() == itemstack.method_723()) {
            itemstack.isReloading = true;
            return itemstack;
        }
        itemstack.justReloaded = false;
        world.playSound(entityplayer, "items.pistol.fire", 1.0F, 1.0F);
        UtilBullet.fireBullet(world, entityplayer, 0.05F, 9);
        itemstack.setDamage(itemstack.getDamage() + 1);
        itemstack.timeLeft = 7;
        if (itemstack.getDamage() == itemstack.method_723())
            itemstack.isReloading = true;
        return itemstack;
    }

    public boolean isLighting(ItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 4;
    }

    public boolean isMuzzleFlash(ItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 4;
    }

    @Override
    public void reload(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.pistolAmmo.id)) {
            itemstack.setDamage(itemstack.getDamage() - 1);
            while (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.pistolAmmo.id))
                itemstack.setDamage(itemstack.getDamage() - 1);
            itemstack.timeLeft = 32;
            world.playSound(entityplayer, "items.clipReload", 1.0F, 1.0F);
        } else {
            world.playSound(entityplayer, "items.dryFire", 1.0F, 1.0F);
            itemstack.timeLeft = 4;
        }
        itemstack.justReloaded = true;
        itemstack.isReloading = false;
    }
}
