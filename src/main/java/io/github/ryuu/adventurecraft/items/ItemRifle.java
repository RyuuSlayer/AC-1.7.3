package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

class ItemRifle extends gm implements IItemReload {
    public ItemRifle(int itemIndex) {
        super(itemIndex);
        this.bg = 1;
        this.itemUseDelay = 1;
    }

    public iz a(iz itemstack, Level world, Player entityplayer) {
        if (itemstack.timeLeft > 3 || itemstack.isReloading)
            return itemstack;
        if (itemstack.i() == itemstack.j()) {
            itemstack.isReloading = true;
            return itemstack;
        }
        itemstack.justReloaded = false;
        world.a(entityplayer, "items.rifle.fire", 1.0F, 1.0F);
        UtilBullet.fireBullet(world, entityplayer, 0.04F * itemstack.timeLeft + 0.03F, 10);
        itemstack.b(itemstack.i() + 1);
        itemstack.timeLeft = 6;
        if (entityplayer.aT > -90.0F)
            entityplayer.aT--;
        if (itemstack.i() == itemstack.j())
            itemstack.isReloading = true;
        return itemstack;
    }

    public boolean isLighting(iz itemstack) {
        if (!itemstack.justReloaded && itemstack.timeLeft > 3)
            return true;
        return false;
    }

    public boolean isMuzzleFlash(iz itemstack) {
        if (!itemstack.justReloaded && itemstack.timeLeft > 3)
            return true;
        return false;
    }

    public void reload(iz itemstack, Level world, Player entityplayer) {
        if (itemstack.i() > 0 && entityplayer.c.c(Items.rifleAmmo.bf)) {
            itemstack.b(itemstack.i() - 1);
            while (itemstack.i() > 0 && entityplayer.c.c(Items.rifleAmmo.bf))
                itemstack.b(itemstack.i() - 1);
            itemstack.timeLeft = 32;
            world.a(entityplayer, "items.clipReload", 1.0F, 1.0F);
        } else {
            world.a(entityplayer, "items.dryFire", 1.0F, 1.0F);
            itemstack.timeLeft = 4;
        }
        itemstack.isReloading = false;
        itemstack.justReloaded = true;
    }
}
