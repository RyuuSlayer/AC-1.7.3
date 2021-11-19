/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.awt.image.BufferedImage
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.net.URL
 *  javax.imageio.ImageIO
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.client.colour;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.colour.FoliageColour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinFoliageColour;

@Mixin(FoliageColour.class)
public class MixinFoliageColour {

    @Shadow()
    private static int[] map = new int[65536];

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void set(int[] map) {
        FoliageColour.map = map;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int getColour(double d, double d1) {
        int i = (int) ((1.0 - d) * 255.0);
        int j = (int) ((1.0 - (d1 *= d)) * 255.0);
        return map[j << 8 | i];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int method_1079() {
        return 0x619961;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int method_1082() {
        return 8431445;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int method_1083() {
        return 4764952;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    static void loadFoliage(String foliageName) {
        BufferedImage bufferedimage = Minecraft.minecraftInstance.level.loadMapTexture(foliageName);
        if (bufferedimage == null) {
            try {
                bufferedimage = ImageIO.read((URL) MixinFoliageColour.class.getResource(foliageName));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (bufferedimage != null) {
            bufferedimage.getRGB(0, 0, 256, 256, map, 0, 256);
        }
    }
}
