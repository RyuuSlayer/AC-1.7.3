package io.github.ryuu.adventurecraft.extensions.items;

import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

public interface ExItemInstance {

    boolean useItemLeftClick(Player player, Level world, int i, int j, int k, int l);

    default int getTimeLeft() {
        return 0;
    }

    default void setTimeLeft(int timeLeft) {
    }

    default boolean isReloading() {
        return false;
    }

    default void setReloading(boolean reloading) {
    }

    default boolean isJustReloaded() {
        return false;
    }

    default void setJustReloaded(boolean justReloaded) {
    }
}
