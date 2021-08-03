package io.github.ryuu.adventurecraft.items;

import net.minecraft.entity.player.Player;

class ItemPegasusBoots extends wa {
    public ItemPegasusBoots(int itemIndex) {
        super(itemIndex, 0, 0, 3);
        c(183);
    }

    public void onAddToSlot(Player entityPlayer, int slot, int damage) {
        super.onAddToSlot(entityPlayer, slot, damage);
        if (slot == 36) {
            entityPlayer.canWallJump = true;
            entityPlayer.timesCanJumpInAir = 1;
        }
    }

    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
        super.onRemovedFromSlot(entityPlayer, slot, damage);
        if (slot == 36) {
            entityPlayer.canWallJump = false;
            entityPlayer.timesCanJumpInAir = 0;
        }
    }
}
