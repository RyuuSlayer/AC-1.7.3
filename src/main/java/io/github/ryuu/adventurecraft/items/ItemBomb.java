package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBomb;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

class ItemBomb extends gm {
    public ItemBomb(int itemIndex) {
        super(itemIndex);
        c(150);
    }

    public iz a(iz itemstack, Level world, Player entityplayer) {
        itemstack.a--;
        world.b((sn)new EntityBomb(world, entityplayer));
        return itemstack;
    }
}
