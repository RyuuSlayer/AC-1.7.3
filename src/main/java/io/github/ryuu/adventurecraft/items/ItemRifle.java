package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.extensions.items.ExItemInstance;
import io.github.ryuu.adventurecraft.extensions.items.LightingItemType;
import io.github.ryuu.adventurecraft.extensions.items.MuzzleFlashItemType;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

class ItemRifle extends ReloadItemType implements DelayableUseItemType, LightingItemType, MuzzleFlashItemType {

    public ItemRifle(int id) {
        super(id);
        this.maxStackSize = 1;
    }

    @Override
    public int getItemUseDelay() {
        return 1;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        ExItemInstance exItem = (ExItemInstance) item;
        if (exItem.getTimeLeft() > 3 || exItem.isReloading()) {
            return item;
        }
        if (item.getDamage() == item.method_723()) {
            exItem.setReloading(true);
            return item;
        }
        exItem.setJustReloaded(false);
        level.playSound(player, "items.rifle.fire", 1.0f, 1.0f);
        UtilBullet.fireBullet(level, player, 0.04f * (float) exItem.getTimeLeft() + 0.03f, 10);
        item.setDamage(item.getDamage() + 1);
        exItem.setTimeLeft(6);
        if (player.pitch > -90.0f) {
            player.pitch -= 1.0f;
        }
        if (item.getDamage() == item.method_723()) {
            exItem.setReloading(true);
        }
        return item;
    }

    @Override
    public boolean isLighting(ItemInstance item) {
        ExItemInstance exItem = (ExItemInstance) item;
        return !exItem.isJustReloaded() && exItem.getTimeLeft() > 3;
    }

    @Override
    public boolean isMuzzleFlash(ItemInstance item) {
        ExItemInstance exItem = (ExItemInstance) item;
        return !exItem.isJustReloaded() && exItem.getTimeLeft() > 3;
    }

    @Override
    public void reload(ItemInstance item, Level world, Player entityplayer) {
        ExItemInstance exItem = (ExItemInstance) item;
        if (item.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.rifleAmmo.id)) {
            item.setDamage(item.getDamage() - 1);
            while (item.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.rifleAmmo.id)) {
                item.setDamage(item.getDamage() - 1);
            }
            exItem.setTimeLeft(32);
            world.playSound(entityplayer, "items.clipReload", 1.0f, 1.0f);
        } else {
            world.playSound(entityplayer, "items.dryFire", 1.0f, 1.0f);
            exItem.setTimeLeft(4);
        }
        exItem.setReloading(false);
        exItem.setJustReloaded(true);
    }
}
