package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;

class ItemLantern extends ItemType {
    public ItemLantern(int itemIndex) {
        super(itemIndex);
        this.maxStackSize = 1;
    }

    public boolean isLighting(ItemInstance itemstack) {
        if (itemstack.getDamage() < itemstack.method_723()) {
            itemstack.setDamage(itemstack.getDamage() + 1);
            return true;
        }
        if (itemstack.getDamage() == itemstack.method_723())
            if (Minecraft.minecraftInstance.h.inventory.decreaseAmountOfItem(Items.oil.id)) {
                itemstack.setDamage(0);
                return true;
            }
        return false;
    }
}
