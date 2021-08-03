package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;

public class ItemPaste extends gm {
    public ItemPaste(int i) {
        super(i);
    }

    public iz a(iz itemstack, Level world, Player entityplayer) {
        if (ItemCursor.bothSet) {
            ls camera = Minecraft.minecraftInstance.i;
            bt lookDir = camera.ac();
            int width = ItemCursor.maxX - ItemCursor.minX + 1;
            int height = ItemCursor.maxY - ItemCursor.minY + 1;
            int depth = ItemCursor.maxZ - ItemCursor.minZ + 1;
            int[] blocks = new int[width * height * depth];
            int[] meta = new int[width * height * depth];
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < height; k++) {
                    for (int m = 0; m < depth; m++) {
                        int blockID = world.a(i + ItemCursor.minX, k + ItemCursor.minY, m + ItemCursor.minZ);
                        int metadata = world.e(i + ItemCursor.minX, k + ItemCursor.minY, m + ItemCursor.minZ);
                        blocks[depth * (height * i + k) + m] = blockID;
                        meta[depth * (height * i + k) + m] = metadata;
                    }
                }
            }
            int xOffset = (int)(camera.aM + DebugMode.reachDistance * lookDir.a);
            int yOffset = (int)(camera.aN + DebugMode.reachDistance * lookDir.b);
            int zOffset = (int)(camera.aO + DebugMode.reachDistance * lookDir.c);
            int j;
            for (j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    for (int m = 0; m < depth; m++) {
                        int blockID = blocks[depth * (height * j + k) + m];
                        int metadata = meta[depth * (height * j + k) + m];
                        world.a(xOffset + j, yOffset + k, zOffset + m, blockID, metadata);
                    }
                }
            }
            for (j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    for (int m = 0; m < depth; m++) {
                        int blockID = blocks[depth * (height * j + k) + m];
                        int metadata = meta[depth * (height * j + k) + m];
                        world.a(xOffset + j, yOffset + k, zOffset + m, blockID, metadata);
                    }
                }
            }
            for (j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    for (int m = 0; m < depth; m++) {
                        int blockID = blocks[depth * (height * j + k) + m];
                        world.g(xOffset + j, yOffset + k, zOffset + m, blockID);
                    }
                }
            }
        }
        return itemstack;
    }
}