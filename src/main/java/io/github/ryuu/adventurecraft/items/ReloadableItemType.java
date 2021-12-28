package io.github.ryuu.adventurecraft.items;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public interface ReloadableItemType {

    void reload(ItemInstance var1, Level var2, Player var3);
}
