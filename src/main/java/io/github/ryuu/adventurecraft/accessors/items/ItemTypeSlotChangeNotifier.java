package io.github.ryuu.adventurecraft.accessors.items;

import net.minecraft.entity.player.Player;

public interface ItemTypeSlotChangeNotifier {

    void onAddToSlot(Player entityPlayer, int slot, int damage);

    void onRemovedFromSlot(Player entityPlayer, int slot, int damage);
}
