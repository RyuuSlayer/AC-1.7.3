package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemRifle extends MixinItemType implements IItemReload {

    public ItemRifle(int id) {
        super(id);
        this.maxStackSize = 1;
        this.itemUseDelay = 1;
    }

    @Override
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        if (item.timeLeft > 3 || item.isReloading) {
            return item;
        }
        if (item.getDamage() == item.method_723()) {
            item.isReloading = true;
            return item;
        }
        item.justReloaded = false;
        level.playSound(player, "items.rifle.fire", 1.0f, 1.0f);
        UtilBullet.fireBullet(level, player, 0.04f * (float) item.timeLeft + 0.03f, 10);
        item.setDamage(item.getDamage() + 1);
        item.timeLeft = 6;
        if (player.pitch > -90.0f) {
            player.pitch -= 1.0f;
        }
        if (item.getDamage() == item.method_723()) {
            item.isReloading = true;
        }
        return item;
    }

    @Override
    public boolean isLighting(MixinItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 3;
    }

    @Override
    public boolean isMuzzleFlash(MixinItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 3;
    }

    @Override
    public void reload(MixinItemInstance itemstack, MixinLevel world, MixinPlayer entityplayer) {
        if (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.rifleAmmo.id)) {
            itemstack.setDamage(itemstack.getDamage() - 1);
            while (itemstack.getDamage() > 0 && entityplayer.inventory.decreaseAmountOfItem(Items.rifleAmmo.id)) {
                itemstack.setDamage(itemstack.getDamage() - 1);
            }
            itemstack.timeLeft = 32;
            world.playSound(entityplayer, "items.clipReload", 1.0f, 1.0f);
        } else {
            world.playSound(entityplayer, "items.dryFire", 1.0f, 1.0f);
            itemstack.timeLeft = 4;
        }
        itemstack.isReloading = false;
        itemstack.justReloaded = true;
    }
}
