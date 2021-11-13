package io.github.ryuu.adventurecraft.mixin;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;

public class MixinGrassColour {
    private static int[] a = new int[65536];

    public static void a(int[] ai) {
        a = ai;
    }

    public static int a(double d, double d1) {
        d1 *= d;
        int i = (int) ((1.0D - d) * 255.0D);
        int j = (int) ((1.0D - d1) * 255.0D);
        return a[j << 8 | i];
    }

    static void loadGrass(String foliageName) {
        BufferedImage bufferedimage = Minecraft.minecraftInstance.f.loadMapTexture(foliageName);
        if (bufferedimage == null)
            try {
                bufferedimage = ImageIO.read(FoliageColour.class.getResource(foliageName));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        if (bufferedimage != null)
            bufferedimage.getRGB(0, 0, 256, 256, a, 0, 256);
    }
}