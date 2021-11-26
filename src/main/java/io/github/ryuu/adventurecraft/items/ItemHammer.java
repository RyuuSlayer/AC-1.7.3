package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemHammer extends ItemType {

    protected ItemHammer(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (ItemCursor.bothSet) {
            int blockToSwapTo = level.getTileId(x, y, z);
            int metadata = level.getTileMeta(x, y, z);
            AccessMinecraft.getInstance().overlay.addChatMessage(String.format("Swapping Area With BlockID %d", blockToSwapTo));
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX);
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY);
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ);
            for (int x2 = minX; x2 <= maxX; ++x2) {
                for (int y2 = minY; y2 <= maxY; ++y2) {
                    for (int z2 = minZ; z2 <= maxZ; ++z2) {
                        level.method_201(x2, y2, z2, blockToSwapTo, metadata);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public float method_438(ItemInstance item, Tile tile) {
        return 32.0f;
    }

    @Override
    public boolean isEffectiveOn(Tile tile) {
        return true;
    }

    @Override
    public boolean shouldRotate180() {
        return true;
    }
}
