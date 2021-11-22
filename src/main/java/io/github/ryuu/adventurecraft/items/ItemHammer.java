package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ItemHammer extends ItemType {

    protected ItemHammer(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (ItemCursor.bothSet) {
            int blockToSwapTo = level.getTileId(x, y, z);
            int metadata = level.getTileMeta(x, y, z);
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format((String) "Swapping Area With BlockID %d", (Object[]) new Object[] { blockToSwapTo }));
            int minX = Math.min((int) ItemCursor.oneX, (int) ItemCursor.twoX);
            int maxX = Math.max((int) ItemCursor.oneX, (int) ItemCursor.twoX);
            int minY = Math.min((int) ItemCursor.oneY, (int) ItemCursor.twoY);
            int maxY = Math.max((int) ItemCursor.oneY, (int) ItemCursor.twoY);
            int minZ = Math.min((int) ItemCursor.oneZ, (int) ItemCursor.twoZ);
            int maxZ = Math.max((int) ItemCursor.oneZ, (int) ItemCursor.twoZ);
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
