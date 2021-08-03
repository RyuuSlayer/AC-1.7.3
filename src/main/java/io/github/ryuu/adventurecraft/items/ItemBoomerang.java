package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBoomerang;

class ItemBoomerang extends gm {
    public ItemBoomerang(int itemIndex) {
        super(itemIndex);
        c(144);
        this.bg = 1;
        e(0);
        a(true);
    }

    public int a(int i) {
        if (i == 0)
            return this.bh;
        return 165;
    }

    public iz a(iz itemstack, fd world, gs entityplayer) {
        if (itemstack.i() == 0) {
            world.b(new EntityBoomerang(world, entityplayer, itemstack));
            itemstack.b(1);
        }
        return itemstack;
    }
}
