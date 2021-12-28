package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.extensions.items.ExItemInstance;
import io.github.ryuu.adventurecraft.extensions.items.LightingItemType;
import io.github.ryuu.adventurecraft.extensions.items.MuzzleFlashItemType;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public class ItemShotgun extends ReloadItemType implements DelayableUseItemType, LightingItemType, MuzzleFlashItemType {

    public ItemShotgun(int id) {
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
        if (exItem.getTimeLeft() > 0) {
            if (exItem.isReloading() && item.getDamage() > 0) {
                exItem.setReloading(false);
                return item;
            }
            return item;
        }
        if (item.getDamage() == item.method_723()) {
            exItem.setReloading(true);
            exItem.setTimeLeft(0);
            return item;
        }
        level.playSound(player, "items.shotgun.fire_and_pump", 1.0f, 1.0f);
        for (int i = 0; i < 14; ++i) {
            UtilBullet.fireBullet(level, player, 0.12f, 2);
        }
        item.setDamage(item.getDamage() + 1);
        exItem.setTimeLeft(40);
        if (item.getDamage() == item.method_723()) {
            exItem.setReloading(true);
        }
        return item;
    }

    @Override
    public boolean isLighting(ItemInstance item) {
        return ((ExItemInstance) item).getTimeLeft() > 42;
    }

    @Override
    public boolean isMuzzleFlash(ItemInstance item) {
        return ((ExItemInstance) item).getTimeLeft() > 35;
    }

    @Override
    public void reload(ItemInstance item, Level world, Player entityplayer) {
        ExItemInstance exItem = (ExItemInstance) item;
        if (item.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.shotgunAmmo.id)) {
            item.setDamage(item.getDamage() - 1);
            exItem.setTimeLeft(20);
            world.playSound(entityplayer, "items.shotgun.reload", 1.0f, 1.0f);
            if (item.getDamage() == 0) {
                exItem.setReloading(false);
            }
        } else {
            exItem.setReloading(false);
        }
    }
}
