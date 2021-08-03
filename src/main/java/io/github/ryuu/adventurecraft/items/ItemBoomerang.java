package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBoomerang;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemBoomerang extends ItemType {
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

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.i() == 0) {
            world.b(new EntityBoomerang(world, entityplayer, itemstack));
            itemstack.b(1);
        }
        return itemstack;
    }
}
