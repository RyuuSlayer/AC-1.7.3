package io.github.ryuu.adventurecraft.mixin.client.colour;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.colour.FoliageColour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Mixin(FoliageColour.class)
public class MixinFoliageColour {

    @Shadow
    private static int[] map;


    static void adventurecraft$loadFoliage(String foliageName) {
        BufferedImage bufferedimage = ((ExLevel) AccessMinecraft.getInstance().level).loadMapTexture(foliageName);
        if (bufferedimage == null) {
            try {
                bufferedimage = ImageIO.read(FoliageColour.class.getResource(foliageName));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (bufferedimage != null) {
            bufferedimage.getRGB(0, 0, 256, 256, map, 0, 256);
        }
    }
}
