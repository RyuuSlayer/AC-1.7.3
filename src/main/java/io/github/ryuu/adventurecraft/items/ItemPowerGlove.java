package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.extensions.entity.ExFallingTile;
import net.minecraft.entity.FallingTile;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import org.lwjgl.input.Keyboard;

public class ItemPowerGlove extends ItemType {

    protected ItemPowerGlove(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        int nextBlockID;
        int xOffset = 0;
        int zOffset = 0;
        if (facing == 2) {
            zOffset = 1;
        } else if (facing == 3) {
            zOffset = -1;
        } else if (facing == 4) {
            xOffset = 1;
        } else if (facing == 5) {
            xOffset = -1;
        } else {
            return false;
        }
        if (level.getTileId(x, y, z) != Blocks.pushableBlock.id) {
            return false;
        }
        if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) {
            xOffset *= -1;
            zOffset *= -1;
        }
        if (Tile.BY_ID[nextBlockID = level.getTileId(x + xOffset, y, z + zOffset)] == null || Tile.BY_ID[nextBlockID].material.isLiquid() || nextBlockID == Tile.FIRE.id) {
            int blockID = level.getTileId(x, y, z);
            int metadata = level.getTileMeta(x, y, z);
            level.method_201(x, y, z, 0, 0);
            FallingTile e = new FallingTile(level, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, blockID);
            e.velocityX = 0.3 * (double) xOffset;
            e.velocityZ = 0.3 * (double) zOffset;
            ((ExFallingTile)e).setMetadata(metadata);
            level.spawnEntity(e);
        }
        return true;
    }
}
