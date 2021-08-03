package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemHammer extends ItemType {
    protected ItemHammer(int i) {
        super(i);
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (ItemCursor.bothSet) {
            int blockToSwapTo = world.a(i, j, k);
            int metadata = world.e(i, j, k);
            Minecraft.minecraftInstance.v.a(String.format("Swapping Area With BlockID %d", new Object[]{Integer.valueOf(blockToSwapTo)}));
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX);
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY);
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ);
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++)
                        world.b(x, y, z, blockToSwapTo, metadata);
                }
            }
        }
        return false;
    }

    public float a(ItemInstance itemstack, Tile block) {
        return 32.0F;
    }

    public boolean a(Tile block) {
        return true;
    }

    public boolean c() {
        return true;
    }
}
