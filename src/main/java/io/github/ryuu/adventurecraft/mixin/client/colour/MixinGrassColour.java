package io.github.ryuu.adventurecraft.mixin.client.colour;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;
import net.minecraft.client.colour.GrassColour;

public class MixinGrassColour {
    private static int[] map = new int[65536];

    public static void set(int[] map) {
        GrassColour.map = map;
    }

    public static int getColour(double d, double d1) {
        int i = (int)((1.0 - d) * 255.0);
        int j = (int)((1.0 - (d1 *= d)) * 255.0);
        return map[j << 8 | i];
    }

    static void loadGrass(String foliageName) {
        BufferedImage bufferedimage = Minecraft.minecraftInstance.level.loadMapTexture(foliageName);
        if (bufferedimage == null) {
            try {
                bufferedimage = ImageIO.read(FoliageColour.class.getResource(foliageName));
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (bufferedimage != null) {
            bufferedimage.getRGB(0, 0, 256, 256, map, 0, 256);
        }
    }
}