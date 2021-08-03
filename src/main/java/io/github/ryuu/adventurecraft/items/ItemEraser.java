package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;

public class ItemEraser extends gm {
    protected ItemEraser(int i) {
        super(i);
    }

    public boolean a(iz itemstack, gs entityplayer, fd world, int i, int j, int k, int l) {
        if (ItemCursor.bothSet) {
            Minecraft.minecraftInstance.v.a("Erasing Area");
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX);
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY);
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ);
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++)
                        world.f(x, y, z, 0);
                }
            }
        }
        return false;
    }
}
