package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.UtilBullet;

class ItemPistol extends gm implements IItemReload {
    public ItemPistol(int itemIndex) {
        super(itemIndex);
        this.bg = 1;
        this.itemUseDelay = 1;
    }

    public iz a(iz itemstack, fd world, gs entityplayer) {
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

    public boolean isLighting(iz itemstack) {
        if (!itemstack.justReloaded && itemstack.timeLeft > 4)
            return true;
        return false;
    }

    public boolean isMuzzleFlash(iz itemstack) {
        if (!itemstack.justReloaded && itemstack.timeLeft > 4)
            return true;
        return false;
    }

    public void reload(iz itemstack, fd world, gs entityplayer) {
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
