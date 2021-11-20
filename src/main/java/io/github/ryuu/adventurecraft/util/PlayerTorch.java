package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class PlayerTorch {

    static boolean torchActive;

    static float posX;

    static float posY;

    static float posZ;

    static int iX;

    static int iY;

    static int iZ;

    static int torchBrightness;

    static int range;

    static float[] cache;

    public static boolean isTorchActive() {
        return torchActive;
    }

    public static void setTorchState(MixinLevel world, boolean active) {
        if (torchActive != active) {
            torchActive = active;
            PlayerTorch.markBlocksDirty(world);
        }
    }

    public static void setTorchPos(MixinLevel world, float x, float y, float z) {
        long avgTime = Minecraft.minecraftInstance.getAvgFrameTime();
        int updateRate = 1;
        if (avgTime > 33333333L) {
            updateRate = 3;
        } else if (avgTime > 16666666L) {
            updateRate = 2;
        }
        if (world.getLevelTime() % (long) updateRate == 0L && (posX != x || posY != y || posZ != z)) {
            posX = x;
            posY = y;
            posZ = z;
            iX = (int) posX;
            iY = (int) posY;
            iZ = (int) posZ;
            PlayerTorch.markBlocksDirty(world);
        }
    }

    public static float getTorchLight(MixinLevel world, int x, int y, int z) {
        if (torchActive) {
            int diffX = x - iX + torchBrightness;
            int diffY = y - iY + torchBrightness;
            int diffZ = z - iZ + torchBrightness;
            if (diffX >= 0 && diffX < range && diffY >= 0 && diffY < range && diffZ >= 0 && diffZ < range) {
                return cache[diffX * range * range + diffY * range + diffZ];
            }
        }
        return 0.0f;
    }

    private static void markBlocksDirty(MixinLevel world) {
        float xDiff = posX - (float) iX;
        float yDiff = posY - (float) iY;
        float zDiff = posZ - (float) iZ;
        int index = 0;
        for (int i = -torchBrightness; i <= torchBrightness; ++i) {
            int blockX = i + iX;
            for (int j = -torchBrightness; j <= torchBrightness; ++j) {
                int blockY = j + iY;
                for (int k = -torchBrightness; k <= torchBrightness; ++k) {
                    int blockZ = k + iZ;
                    int blockId = world.getTileId(blockX, blockY, blockZ);
                    if (blockId != 0 && Tile.BY_ID[blockId].isFullOpaque() && blockId != Tile.STONE_SLAB.id && blockId != Tile.FARMLAND.id) {
                        PlayerTorch.cache[index++] = 0.0f;
                        continue;
                    }
                    float distance = (float) (Math.abs((double) ((double) i + 0.5 - (double) xDiff)) + Math.abs((double) ((double) j + 0.5 - (double) yDiff)) + Math.abs((double) ((double) k + 0.5 - (double) zDiff)));
                    if (distance <= (float) torchBrightness) {
                        if ((float) torchBrightness - distance > (float) world.getLightLevel(blockX, blockY, blockZ)) {
                            world.method_243(blockX, blockY, blockZ);
                        }
                        PlayerTorch.cache[index++] = (float) torchBrightness - distance;
                        continue;
                    }
                    PlayerTorch.cache[index++] = 0.0f;
                }
            }
        }
    }

    static {
        torchBrightness = 15;
        range = torchBrightness * 2 + 1;
        cache = new float[range * range * range];
    }
}
