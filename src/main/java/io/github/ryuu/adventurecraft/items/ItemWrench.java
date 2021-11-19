package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

public class ItemWrench extends MixinItemType {

    protected ItemWrench(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        if (ItemCursor.bothSet) {
            int blockToSwapTo = level.getTileId(x, y, z);
            int metadata = level.getTileMeta(x, y, z);
            Minecraft.minecraftInstance.overlay.addChatMessage(String.format("Swapping blocks With BlockID %d", new Object[]{blockToSwapTo}));
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX);
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY);
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ);
            for (int x2 = minX; x2 <= maxX; ++x2) {
                for (int y2 = minY; y2 <= maxY; ++y2) {
                    for (int z2 = minZ; z2 <= maxZ; ++z2) {
                        int blockID = level.getTileId(x2, y2, z2);
                        switch (blockID) {
                            default: {
                                level.method_201(x2, y2, z2, blockToSwapTo, metadata);
                            }
                            case 0:
                            case 23:
                            case 25:
                            case 46:
                            case 55:
                            case 61:
                            case 62:
                            case 64:
                            case 69:
                            case 70:
                            case 71:
                            case 72:
                            case 75:
                            case 76:
                            case 77:
                            case 93:
                            case 94:
                            case 101:
                            case 102:
                            case 103:
                            case 104:
                            case 107:
                            case 108:
                            case 109:
                            case 110:
                            case 111:
                            case 115:
                            case 117:
                            case 118:
                        }
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
