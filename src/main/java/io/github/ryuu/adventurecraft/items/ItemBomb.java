package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBomb;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemBomb extends ItemType {
    public ItemBomb(int itemIndex) {
        super(itemIndex);
        setTexturePosition(150); // c = setTexturePosition
    }

    @Override
    public ItemInstance use(ItemInstance itemstack, Level world, Player entityplayer) {
        itemstack.count--; // a = count
        world.spawnEntity(new EntityBomb(world, entityplayer)); // Removed redundant cast no worry
        return itemstack;
    }
}
