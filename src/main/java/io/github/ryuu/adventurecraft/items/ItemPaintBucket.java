package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemPaintBucket extends gm {
    protected ItemPaintBucket(int i) {
        super(i);
    }

    public boolean a(iz itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (ItemCursor.bothSet) {
            int minX = Math.min(ItemCursor.oneX, ItemCursor.twoX);
            int maxX = Math.max(ItemCursor.oneX, ItemCursor.twoX);
            int minY = Math.min(ItemCursor.oneY, ItemCursor.twoY);
            int maxY = Math.max(ItemCursor.oneY, ItemCursor.twoY);
            int minZ = Math.min(ItemCursor.oneZ, ItemCursor.twoZ);
            int maxZ = Math.max(ItemCursor.oneZ, ItemCursor.twoZ);
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Tile b = Tile.m[world.a(x, y, z)];
                        if (b != null && b instanceof IBlockColor) {
                            ((IBlockColor)b).incrementColor(world, x, y, z);
                            world.j(x, y, z);
                        }
                    }
                }
            }
        }
        return false;
    }
}
