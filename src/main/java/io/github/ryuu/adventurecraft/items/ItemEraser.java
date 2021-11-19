package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

public class ItemEraser extends MixinItemType {

    protected ItemEraser(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        if (ItemCursor.bothSet) {
            Minecraft.minecraftInstance.overlay.addChatMessage("Erasing Area");
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX);
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY);
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ);
            for (int x2 = minX; x2 <= maxX; ++x2) {
                for (int y2 = minY; y2 <= maxY; ++y2) {
                    for (int z2 = minZ; z2 <= maxZ; ++z2) {
                        level.setTile(x2, y2, z2, 0);
                    }
                }
            }
        }
        return false;
    }
}
