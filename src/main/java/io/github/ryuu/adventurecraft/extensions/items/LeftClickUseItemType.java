package io.github.ryuu.adventurecraft.extensions.items;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public interface LeftClickUseItemType {

    boolean onItemUseLeftClick(ItemInstance instance, Player player, Level world, int i, int j, int k, int l);
}
