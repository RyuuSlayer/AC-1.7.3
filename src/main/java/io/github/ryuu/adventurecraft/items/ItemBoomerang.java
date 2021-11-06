package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.entities.EntityBoomerang;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;

class ItemBoomerang extends ItemType {
    public ItemBoomerang(int itemIndex) {
        super(itemIndex);
        setTexturePosition(144);
        this.maxStackSize = 1;
        setDurability(0); // e = setDurability
        method_457(true); // a = method_457
    }

    @Override
    public int getTexturePosition(int i) {
        if (i == 0)
            return this.texturePosition;
        return 165;
    }

    @Override
    public ItemInstance use(ItemInstance itemstack, Level world, Player entityplayer) {
        if (itemstack.getDamage() == 0) { // i = getDamage
            world.spawnEntity(new EntityBoomerang(world, entityplayer, itemstack));
            itemstack.setDamage(1); // b = setDamage
        }
        return itemstack;
    }
}
