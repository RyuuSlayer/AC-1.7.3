package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.blocks.IBlockColor;

public class ItemPaintBucket extends ItemType {

    protected ItemPaintBucket(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (ItemCursor.bothSet) {
            int minX = Math.min((int) ItemCursor.oneX, (int) ItemCursor.twoX);
            int maxX = Math.max((int) ItemCursor.oneX, (int) ItemCursor.twoX);
            int minY = Math.min((int) ItemCursor.oneY, (int) ItemCursor.twoY);
            int maxY = Math.max((int) ItemCursor.oneY, (int) ItemCursor.twoY);
            int minZ = Math.min((int) ItemCursor.oneZ, (int) ItemCursor.twoZ);
            int maxZ = Math.max((int) ItemCursor.oneZ, (int) ItemCursor.twoZ);
            for (int x2 = minX; x2 <= maxX; ++x2) {
                for (int y2 = minY; y2 <= maxY; ++y2) {
                    for (int z2 = minZ; z2 <= maxZ; ++z2) {
                        Tile b = Tile.BY_ID[level.getTileId(x2, y2, z2)];
                        if (b == null || !(b instanceof IBlockColor))
                            continue;
                        ((IBlockColor) ((Object) b)).incrementColor(level, x2, y2, z2);
                        level.method_243(x2, y2, z2);
                    }
                }
            }
        }
        return false;
    }
}
