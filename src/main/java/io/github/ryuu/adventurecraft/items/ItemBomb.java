package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBomb;

class ItemBomb extends gm {
    public ItemBomb(int itemIndex) {
        super(itemIndex);
        c(150);
    }

    public iz a(iz itemstack, fd world, gs entityplayer) {
        itemstack.a--;
        world.b((sn)new EntityBomb(world, entityplayer));
        return itemstack;
    }
}
