package io.github.ryuu.adventurecraft.items;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
