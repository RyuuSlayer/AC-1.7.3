package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
import net.minecraft.entity.player.Player;
import net.minecraft.item.armour.ArmourItem;

public class ItemPegasusBoots extends ArmourItem implements ExItemType {

    public ItemPegasusBoots(int id) {
        super(id, 0, 0, 3);
        this.setTexturePosition(183);
    }

    @Override
    public void onAddToSlot(Player entityPlayer, int slot, int damage) {
        if (slot == 36) {
            ((ExPlayer) entityPlayer).setCanWallJump(true);
            ((ExPlayer) entityPlayer).setTimesCanJumpInAir(1);
        }
    }

    @Override
    public void onRemovedFromSlot(Player entityPlayer, int slot, int damage) {
        if (slot == 36) {
            ((ExPlayer) entityPlayer).setCanWallJump(false);
            ((ExPlayer) entityPlayer).setTimesCanJumpInAir(0);
        }
    }
}
