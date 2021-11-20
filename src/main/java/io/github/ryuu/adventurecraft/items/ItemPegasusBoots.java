package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.armour.ArmourItem;

class ItemPegasusBoots extends MixinArmourItem {

    public ItemPegasusBoots(int id) {
        super(id, 0, 0, 3);
        this.setTexturePosition(183);
    }

    @Override
    public void onAddToSlot(MixinPlayer entityPlayer, int slot, int damage) {
        super.onAddToSlot(entityPlayer, slot, damage);
        if (slot == 36) {
            entityPlayer.canWallJump = true;
            entityPlayer.timesCanJumpInAir = 1;
        }
    }

    @Override
    public void onRemovedFromSlot(MixinPlayer entityPlayer, int slot, int damage) {
        super.onRemovedFromSlot(entityPlayer, slot, damage);
        if (slot == 36) {
            entityPlayer.canWallJump = false;
            entityPlayer.timesCanJumpInAir = 0;
        }
    }
}
