package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemPistol extends ItemType implements IItemReload {
    public ItemPistol(int itemIndex) {
        super(itemIndex);
        this.bg = 1;
        this.itemUseDelay = 1;
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.timeLeft > 0 || itemstack.isReloading)
            return itemstack;
        if (itemstack.i() == itemstack.j()) {
            itemstack.isReloading = true;
            return itemstack;
        }
        itemstack.justReloaded = false;
        world.a(entityplayer, "items.pistol.fire", 1.0F, 1.0F);
        UtilBullet.fireBullet(world, entityplayer, 0.05F, 9);
        itemstack.b(itemstack.i() + 1);
        itemstack.timeLeft = 7;
        if (itemstack.i() == itemstack.j())
            itemstack.isReloading = true;
        return itemstack;
    }

    public boolean isLighting(ItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 4;
    }

    public boolean isMuzzleFlash(ItemInstance itemstack) {
        return !itemstack.justReloaded && itemstack.timeLeft > 4;
    }

    public void reload(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.i() > 0 && entityplayer.c.c(Items.pistolAmmo.bf)) {
            itemstack.b(itemstack.i() - 1);
            while (itemstack.i() > 0 && entityplayer.c.c(Items.pistolAmmo.bf))
                itemstack.b(itemstack.i() - 1);
            itemstack.timeLeft = 32;
            world.a(entityplayer, "items.clipReload", 1.0F, 1.0F);
        } else {
            world.a(entityplayer, "items.dryFire", 1.0F, 1.0F);
            itemstack.timeLeft = 4;
        }
        itemstack.justReloaded = true;
        itemstack.isReloading = false;
    }
}
