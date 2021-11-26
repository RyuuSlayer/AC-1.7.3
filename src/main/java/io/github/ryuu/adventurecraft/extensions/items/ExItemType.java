package io.github.ryuu.adventurecraft.extensions.items;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public interface ExItemType {

    default boolean isLighting(ItemInstance itemStack) {
        return false;
    }

    default boolean isMuzzleFlash(ItemInstance itemStack) {
        return false;
    }

    default boolean onItemUseLeftClick(ItemInstance instance, Player player, Level world, int i, int j, int k, int l) {
        return false;
    }

    default void onItemLeftClick(ItemInstance itemstack, Level world, Player entityplayer) {
    }

    default boolean mainActionLeftClick() {
        return false;
    }

    default void onAddToSlot(Player entityPlayer, int slot, int damage) {
    }

    default void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
    }
}
