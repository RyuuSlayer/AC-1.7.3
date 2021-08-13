package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import org.lwjgl.input.Keyboard;

public class ItemPowerGlove extends ItemType {
    protected ItemPowerGlove(int i) {
        super(i);
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int side) {
        int xOffset = 0;
        int zOffset = 0;
        if (side == 2) {
            zOffset = 1;
        } else if (side == 3) {
            zOffset = -1;
        } else if (side == 4) {
            xOffset = 1;
        } else if (side == 5) {
            xOffset = -1;
        } else {
            return false;
        }
        if (world.a(i, j, k) != Blocks.pushableBlock.id)
            return false;
        if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
            xOffset *= -1;
            zOffset *= -1;
        }
        int nextBlockID = world.a(i + xOffset, j, k + zOffset);
        if (Tile.BY_ID[nextBlockID] == null || (Tile.BY_ID[nextBlockID]).material.d() || nextBlockID == Tile.as.id) {
            int blockID = world.a(i, j, k);
            int metadata = world.e(i, j, k);
            world.b(i, j, k, 0, 0);
            FallingTile e = new FallingTile(world, i + 0.5D, j + 0.5D, k + 0.5D, blockID);
            e.aP = 0.3D * xOffset;
            e.aR = 0.3D * zOffset;
            e.metadata = metadata;
            world.b(e);
        }
        return true;
    }
}
