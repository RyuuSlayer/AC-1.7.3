package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

class ItemLantern extends gm {
    public ItemLantern(int itemIndex) {
        super(itemIndex);
        this.bg = 1;
    }

    public boolean isLighting(iz itemstack) {
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
