package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.awt.image.BufferedImage
 *  java.io.File
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.System
 *  javax.imageio.ImageIO
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TerrainImage {

    private static int[] biomeInfo;

    private static int[] terrainInfo;

    private static int[] waterInfo;

    private static int imageHeight;

    private static int imageWidth;

    private static int halfHeight;

    private static int halfWidth;

    public static boolean isLoaded;

    public static boolean isWaterLoaded;

    private static int getOffset(int x, int z) {
        z += halfHeight;
        if ((x += halfWidth) < 0) {
            x = 0;
        } else if (x >= imageWidth) {
            x = imageWidth - 1;
        }
        if (z < 0) {
            z = 0;
        } else if (z >= imageHeight) {
            z = imageHeight - 1;
        }
        return x + z * imageWidth;
    }

    public static int getTerrainInfo(int x, int z) {
        return terrainInfo[TerrainImage.getOffset(x, z)];
    }

    public static int getBiomeInfo(int x, int z) {
        return biomeInfo[TerrainImage.getOffset(x, z)];
    }

    public static int getWaterColor(int x, int z) {
        if (isWaterLoaded) {
            return waterInfo[TerrainImage.getOffset(x, z)];
        }
        return 4221183;
    }

    public static int getTerrainHeight(int x, int z) {
        if (!isLoaded) {
            return 64;
        }
        int value = TerrainImage.getTerrainInfo(x, z);
        return (value >> 8 & 0xFF) / 2;
    }

    public static int getWaterHeight(int x, int z) {
        if (!isLoaded) {
            return 0;
        }
        int value = TerrainImage.getTerrainInfo(x, z);
        return (value & 0xFF) / 2;
    }

    public static boolean hasSandNearWaterEdge(int x, int z) {
        if (!isLoaded) {
            return false;
        }
        int value = TerrainImage.getTerrainInfo(x, z);
        return (value >> 16 & 0xFF) > 127;
    }

    public static double getTerrainHumidity(int x, int z) {
        if (!isLoaded) {
            return 0.25;
        }
        return (double) (TerrainImage.getBiomeInfo(x, z) & 0xFF) / 255.0;
    }

    public static double getTerrainTemperature(int x, int z) {
        if (!isLoaded) {
            return 0.75;
        }
        return (double) (TerrainImage.getBiomeInfo(x, z) >> 16 & 0xFF) / 255.0;
    }

    public static boolean loadBiomeMap(File biomeFile) {
        try {
            BufferedImage bufferedimage = ImageIO.read((File) biomeFile);
            assert (imageWidth == bufferedimage.getWidth()) : "biomeMap.png width doesn't match the width of terrainMap.png";
            assert (imageHeight == bufferedimage.getHeight()) : "biomeMap.png height doesn't match the height of terrainMap.png";
            bufferedimage.getRGB(0, 0, imageWidth, imageHeight, biomeInfo, 0, imageWidth);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public static boolean loadWaterMap(File waterFile) {
        try {
            BufferedImage bufferedimage = ImageIO.read((File) waterFile);
            assert (imageWidth == bufferedimage.getWidth()) : "waterMap.png width doesn't match the width of terrainMap.png";
            assert (imageHeight == bufferedimage.getHeight()) : "waterMap.png height doesn't match the height of terrainMap.png";
            waterInfo = new int[imageWidth * imageHeight];
            bufferedimage.getRGB(0, 0, imageWidth, imageHeight, waterInfo, 0, imageWidth);
            isWaterLoaded = true;
        } catch (Exception exception) {
            isWaterLoaded = false;
            return false;
        }
        return true;
    }

    public static boolean loadMap(File f) {
        biomeInfo = new int[0];
        terrainInfo = new int[0];
        waterInfo = new int[0];
        imageWidth = 0;
        imageHeight = 0;
        halfWidth = 0;
        halfHeight = 0;
        isWaterLoaded = false;
        try {
            File terrainFile = new File(f, "terrainMap.png");
            System.out.printf("Exists: %b Path: %s\n", new Object[] { terrainFile.exists(), terrainFile.getCanonicalPath() });
            BufferedImage bufferedimage = ImageIO.read((File) terrainFile);
            imageWidth = bufferedimage.getWidth();
            imageHeight = bufferedimage.getHeight();
            halfWidth = imageWidth / 2;
            halfHeight = imageHeight / 2;
            terrainInfo = new int[imageWidth * imageHeight];
            biomeInfo = new int[imageWidth * imageHeight];
            bufferedimage.getRGB(0, 0, imageWidth, imageHeight, terrainInfo, 0, imageWidth);
            File biomeFile = new File(f, "biomeMap.png");
            if (!TerrainImage.loadBiomeMap(biomeFile)) {
                isLoaded = false;
                return false;
            }
            File waterFile = new File(f, "waterMap.png");
            TerrainImage.loadWaterMap(waterFile);
            isLoaded = true;
        } catch (Exception exception) {
            isLoaded = false;
            return false;
        }
        return true;
    }
}
