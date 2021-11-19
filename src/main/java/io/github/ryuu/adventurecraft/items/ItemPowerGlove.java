package io.github.ryuu.adventurecraft.items;

import net.minecraft.tile.Tile;
import org.lwjgl.input.Keyboard;

public class ItemPowerGlove extends MixinItemType {

    protected ItemPowerGlove(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
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
            MixinFallingTile e = new MixinFallingTile(level, (double) x + 0.5, (double) y + 0.5, (double) z + 0.5, blockID);
            e.velocityX = 0.3 * (double) xOffset;
            e.velocityZ = 0.3 * (double) zOffset;
            e.metadata = metadata;
            level.spawnEntity(e);
        }
        return true;
    }
}
