package io.github.ryuu.adventurecraft.items;

import net.minecraft.entity.player.Player;

public interface SlotChangeCallbackItemType {

    void onAddToSlot(Player entityPlayer, int slot, int damage);

    void onRemovedFromSlot(Player entityPlayer, int slot, int damage);
}
