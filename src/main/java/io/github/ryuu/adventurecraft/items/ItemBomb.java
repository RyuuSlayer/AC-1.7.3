package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBomb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemBomb extends ItemType {
    public ItemBomb(int itemIndex) {
        super(itemIndex);
        c(150);
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        itemstack.a--;
        world.b((Entity) new EntityBomb(world, entityplayer));
        return itemstack;
    }
}
