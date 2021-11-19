package io.github.ryuu.adventurecraft.mixin.client.colour;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;

public class MixinFoliageColour {
    private static int[] map = new int[65536];

    public static void set(int[] map) {
        FoliageColour.map = map;
    }

    public static int getColour(double d, double d1) {
        int i = (int)((1.0 - d) * 255.0);
        int j = (int)((1.0 - (d1 *= d)) * 255.0);
        return map[j << 8 | i];
    }

    public static int method_1079() {
        return 0x619961;
    }

    public static int method_1082() {
        return 8431445;
    }

    public static int method_1083() {
        return 4764952;
    }

    static void loadFoliage(String foliageName) {
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