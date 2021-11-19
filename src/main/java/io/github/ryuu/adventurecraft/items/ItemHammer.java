package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

public class ItemHammer extends MixinItemType {

    protected ItemHammer(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        if (ItemCursor.bothSet) {
            int blockToSwapTo = level.getTileId(x, y, z);
            int metadata = level.getTileMeta(x, y, z);
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Swapping Area With BlockID %d", new Object[]{blockToSwapTo}));
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
    public float method_438(MixinItemInstance item, MixinTile tile) {
        return 32.0f;
    }

    @Override
    public boolean isEffectiveOn(MixinTile tile) {
        return true;
    }

    @Override
    public boolean shouldRotate180() {
        return true;
    }
}
