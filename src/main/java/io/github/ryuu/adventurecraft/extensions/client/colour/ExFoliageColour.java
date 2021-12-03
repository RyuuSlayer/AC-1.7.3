package io.github.ryuu.adventurecraft.extensions.client.colour;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.mixin.client.colour.AccessFoliageColour;
import net.minecraft.client.colour.FoliageColour;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public interface ExFoliageColour {

    static void loadFoliage(String foliageName) {
        BufferedImage bufferedimage = ((ExLevel) AccessMinecraft.getInstance().level).loadMapTexture(foliageName);
        if (bufferedimage == null) {
            try {
                bufferedimage = ImageIO.read(FoliageColour.class.getResource(foliageName));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (bufferedimage != null) {
            bufferedimage.getRGB(0, 0, 256, 256, AccessFoliageColour.getMap(), 0, 256);
        }
    }
}
