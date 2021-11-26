package io.github.ryuu.adventurecraft.extensions.items;

import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

public interface ExItemInstance {

    boolean useItemLeftClick(Player player, Level world, int i, int j, int k, int l);
}
