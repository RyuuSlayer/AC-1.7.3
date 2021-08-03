package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;

class ItemLantern extends ItemType {
    public ItemLantern(int itemIndex) {
        super(itemIndex);
        this.bg = 1;
    }

    public boolean isLighting(ItemInstance itemstack) {
        if (itemstack.i() < itemstack.j()) {
            itemstack.b(itemstack.i() + 1);
            return true;
        }
        if (itemstack.i() == itemstack.j())
            if (Minecraft.minecraftInstance.h.c.c(Items.oil.bf)) {
                itemstack.b(0);
                return true;
            }
        return false;
    }
}
