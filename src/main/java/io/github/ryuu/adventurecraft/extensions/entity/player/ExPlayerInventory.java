package io.github.ryuu.adventurecraft.extensions.entity.player;

import net.minecraft.item.ItemInstance;

public interface ExPlayerInventory {

    int getOffhandSlot();

    void setOffhandSlot(int offhandSlot);

    ItemInstance getOffhandItem();

    void swapOffhandWithMain();

    boolean consumeItemAmount(int itemID, int damage, int amount);
}
