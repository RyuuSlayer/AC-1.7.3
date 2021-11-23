package io.github.ryuu.adventurecraft.items;

import net.minecraft.entity.player.Player;
import net.minecraft.item.armour.ArmourItem;

class ItemPegasusBoots extends ArmourItem {

    public ItemPegasusBoots(int id) {
        super(id, 0, 0, 3);
        this.setTexturePosition(183);
    }

    @Override
    public void onAddToSlot(Player entityPlayer, int slot, int damage) {
        super.onAddToSlot(entityPlayer, slot, damage);
        if (slot == 36) {
            entityPlayer.canWallJump = true;
            entityPlayer.timesCanJumpInAir = 1;
        }
    }

    @Override
    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
        super.onRemovedFromSlot(entityPlayer, slot, damage);
        if (slot == 36) {
            entityPlayer.canWallJump = false;
            entityPlayer.timesCanJumpInAir = 0;
        }
    }
}
