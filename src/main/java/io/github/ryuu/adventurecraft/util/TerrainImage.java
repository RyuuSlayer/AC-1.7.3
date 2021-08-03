package io.github.ryuu.adventurecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

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
        x += halfWidth;
        z += halfHeight;
        if (x < 0) {
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
        return terrainInfo[getOffset(x, z)];
    }

    public static int getBiomeInfo(int x, int z) {
        return biomeInfo[getOffset(x, z)];
    }

    public static int getWaterColor(int x, int z) {
        if (isWaterLoaded)
            return waterInfo[getOffset(x, z)];
        return 4221183;
    }

    public static int getTerrainHeight(int x, int z) {
        if (!isLoaded)
            return 64;
        int value = getTerrainInfo(x, z);
        return (value >> 8 & 0xFF) / 2;
    }

    public static int getWaterHeight(int x, int z) {
        if (!isLoaded)
            return 0;
        int value = getTerrainInfo(x, z);
        return (value & 0xFF) / 2;
    }

    public static boolean hasSandNearWaterEdge(int x, int z) {
        if (!isLoaded)
            return false;
        int value = getTerrainInfo(x, z);
        return ((value >> 16 & 0xFF) > 127);
    }

    public static double getTerrainHumidity(int x, int z) {
        if (!isLoaded)
            return 0.25D;
        return (getBiomeInfo(x, z) & 0xFF) / 255.0D;
    }

    public static double getTerrainTemperature(int x, int z) {
        if (!isLoaded)
            return 0.75D;
        return (getBiomeInfo(x, z) >> 16 & 0xFF) / 255.0D;
    }

    public static boolean loadBiomeMap(File biomeFile) {
        try {
            BufferedImage bufferedimage = ImageIO.read(biomeFile);
            assert imageWidth == bufferedimage.getWidth() : "biomeMap.png width doesn't match the width of terrainMap.png";
            assert imageHeight == bufferedimage.getHeight() : "biomeMap.png height doesn't match the height of terrainMap.png";
            bufferedimage.getRGB(0, 0, imageWidth, imageHeight, biomeInfo, 0, imageWidth);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public static boolean loadWaterMap(File waterFile) {
        try {
            BufferedImage bufferedimage = ImageIO.read(waterFile);
            assert imageWidth == bufferedimage.getWidth() : "waterMap.png width doesn't match the width of terrainMap.png";
            assert imageHeight == bufferedimage.getHeight() : "waterMap.png height doesn't match the height of terrainMap.png";
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
            System.out.printf("Exists: %b Path: %s\n", Boolean.valueOf(terrainFile.exists()), terrainFile.getCanonicalPath());
            BufferedImage bufferedimage = ImageIO.read(terrainFile);
            imageWidth = bufferedimage.getWidth();
            imageHeight = bufferedimage.getHeight();
            halfWidth = imageWidth / 2;
            halfHeight = imageHeight / 2;
            terrainInfo = new int[imageWidth * imageHeight];
            biomeInfo = new int[imageWidth * imageHeight];
            bufferedimage.getRGB(0, 0, imageWidth, imageHeight, terrainInfo, 0, imageWidth);
            File biomeFile = new File(f, "biomeMap.png");
            if (!loadBiomeMap(biomeFile)) {
                isLoaded = false;
                return false;
            }
            File waterFile = new File(f, "waterMap.png");
            loadWaterMap(waterFile);
            isLoaded = true;
        } catch (Exception exception) {
            isLoaded = false;
            return false;
        }
        return true;
    }
}
