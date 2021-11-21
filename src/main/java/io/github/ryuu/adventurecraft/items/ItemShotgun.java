package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemShotgun extends ItemType implements IItemReload {

    public ItemShotgun(int id) {
        super(id);
        this.maxStackSize = 1;
        this.itemUseDelay = 1;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        if (item.timeLeft > 0) {
            if (item.isReloading && item.getDamage() > 0) {
                item.isReloading = false;
                return item;
            }
            return item;
        }
        if (item.getDamage() == item.method_723()) {
            item.isReloading = true;
            item.timeLeft = 0;
            return item;
        }
        level.playSound(player, "items.shotgun.fire_and_pump", 1.0f, 1.0f);
        for (int i = 0; i < 14; ++i) {
            UtilBullet.fireBullet(level, player, 0.12f, 2);
        }
        item.setDamage(item.getDamage() + 1);
        item.timeLeft = 40;
        if (item.getDamage() == item.method_723()) {
            item.isReloading = true;
        }
        return item;
    }

    @Override
    public boolean isLighting(ItemInstance itemstack) {
        return itemstack.timeLeft > 42;
    }

    @Override
    public boolean isMuzzleFlash(ItemInstance itemstack) {
        return itemstack.timeLeft > 35;
    }

    @Override
    public void reload(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.shotgunAmmo.id)) {
            itemstack.setDamage(itemstack.getDamage() - 1);
            itemstack.timeLeft = 20;
            world.playSound(entityplayer, "items.shotgun.reload", 1.0f, 1.0f);
            if (itemstack.getDamage() == 0) {
                itemstack.isReloading = false;
            }
        } else {
            itemstack.isReloading = false;
        }
    }
}
