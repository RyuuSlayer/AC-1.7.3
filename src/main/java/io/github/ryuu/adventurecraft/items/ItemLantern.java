package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

class ItemLantern extends MixinItemType {

    public ItemLantern(int id) {
        super(id);
        this.maxStackSize = 1;
    }

    @Override
    public boolean isLighting(MixinItemInstance itemstack) {
        if (itemstack.getDamage() < itemstack.method_723()) {
            itemstack.setDamage(itemstack.getDamage() + 1);
            return true;
        }
        if (itemstack.getDamage() == itemstack.method_723() && Minecraft.minecraftInstance.player.inventory.decreaseAmountOfItem(Items.oil.id)) {
            itemstack.setDamage(0);
            return true;
        }
        return false;
    }
}
