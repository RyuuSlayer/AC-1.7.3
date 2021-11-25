package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.accessors.items.ItemTypeSlotChangeNotifier;
import net.minecraft.entity.player.Player;
import net.minecraft.item.armour.ArmourItem;

public class ItemPegasusBoots extends ArmourItem implements ItemTypeSlotChangeNotifier {

    public ItemPegasusBoots(int id) {
        super(id, 0, 0, 3);
        this.setTexturePosition(183);
    }

    @Override
    public void onAddToSlot(Player entityPlayer, int slot, int damage) {
        if (slot == 36) {
            entityPlayer.canWallJump = true;
            entityPlayer.timesCanJumpInAir = 1;
        }
    }

    @Override
    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
        if (slot == 36) {
            entityPlayer.canWallJump = false;
            entityPlayer.timesCanJumpInAir = 0;
        }
    }
}
