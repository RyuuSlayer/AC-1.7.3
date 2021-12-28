package io.github.ryuu.adventurecraft.items;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public interface LeftClickItemType {

    void onItemLeftClick(ItemInstance itemstack, Level world, Player entityplayer);
}
