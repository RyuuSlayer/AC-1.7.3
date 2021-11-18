package io.github.ryuu.adventurecraft.util;

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
    static int torchBrightness = 15;
    static int range = torchBrightness * 2 + 1;
    static float[] cache = new float[range * range * range];
    public static boolean isTorchActive() {
        return torchActive;
    }

    public static void setTorchState(Level world, boolean active) {
        if (torchActive != active) {
            torchActive = active;
            markBlocksDirty(world);
        }
    }

    public static void setTorchPos(Level world, float x, float y, float z) {
        long avgTime = Minecraft.minecraftInstance.getAvgFrameTime();
        int updateRate = 1;
        if (avgTime > 33333333L) {
            updateRate = 3;
        } else if (avgTime > 16666666L) {
            updateRate = 2;
        }
        if (world.t() % updateRate == 0L && (posX != x || posY != y || posZ != z)) {
            posX = x;
            posY = y;
            posZ = z;
            iX = (int) posX;
            iY = (int) posY;
            iZ = (int) posZ;
            markBlocksDirty(world);
        }
    }

    public static float getTorchLight(Level world, int x, int y, int z) {
        if (torchActive) {
            int diffX = x - iX + torchBrightness;
            int diffY = y - iY + torchBrightness;
            int diffZ = z - iZ + torchBrightness;
            if (diffX >= 0 && diffX < range && diffY >= 0 && diffY < range && diffZ >= 0 && diffZ < range)
                return cache[diffX * range * range + diffY * range + diffZ];
        }
        return 0.0F;
    }

    private static void markBlocksDirty(Level world) {
        float xDiff = posX - iX;
        float yDiff = posY - iY;
        float zDiff = posZ - iZ;
        int index = 0;
        for (int i = -torchBrightness; i <= torchBrightness; i++) {
            int blockX = i + iX;
            for (int j = -torchBrightness; j <= torchBrightness; j++) {
                int blockY = j + iY;
                for (int k = -torchBrightness; k <= torchBrightness; k++) {
                    int blockZ = k + iZ;
                    int blockId = world.a(blockX, blockY, blockZ);
                    if (blockId != 0 && Tile.m[blockId].c() && blockId != Tile.al.bn && blockId != Tile.aB.bn) {
                        cache[index++] = 0.0F;
                    } else {
                        float distance = (float) (Math.abs(i + 0.5D - xDiff) + Math.abs(j + 0.5D - yDiff) + Math.abs(k + 0.5D - zDiff));
                        if (distance <= torchBrightness) {
                            if (torchBrightness - distance > world.n(blockX, blockY, blockZ))
                                world.j(blockX, blockY, blockZ);
                            cache[index++] = torchBrightness - distance;
                        } else {
                            cache[index++] = 0.0F;
                        }
                    }
                }
            }
        }
    }
}