package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemPistol extends MixinItemType implements IItemReload {

    public ItemPistol(int id) {
        super(id);
        this.maxStackSize = 1;
        this.itemUseDelay = 1;
    }

    @Override
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        if (item.timeLeft > 0 || item.isReloading) {
            return item;
        }
        if (item.getDamage() == item.method_723()) {
            item.isReloading = true;
            return item;
        }
        item.justReloaded = false;
        level.playSound(player, "items.pistol.fire", 1.0f, 1.0f);
        UtilBullet.fireBullet(level, player, 0.05f, 9);
        item.setDamage(item.getDamage() + 1);
        item.timeLeft = 7;
        if (item.getDamage() == item.method_723()) {
            item.isReloading = true;
        }
        return item;
    }

    @Override
    public boolean isLighting(MixinItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 4;
    }

    @Override
    public boolean isMuzzleFlash(MixinItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 4;
    }

    @Override
    public void reload(MixinItemInstance itemstack, MixinLevel world, MixinPlayer entityplayer) {
        if (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.pistolAmmo.id)) {
            itemstack.setDamage(itemstack.getDamage() - 1);
            while (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.pistolAmmo.id)) {
                itemstack.setDamage(itemstack.getDamage() - 1);
            }
            itemstack.timeLeft = 32;
            world.playSound(entityplayer, "items.clipReload", 1.0f, 1.0f);
        } else {
            world.playSound(entityplayer, "items.dryFire", 1.0f, 1.0f);
            itemstack.timeLeft = 4;
        }
        itemstack.justReloaded = true;
        itemstack.isReloading = false;
    }
}
