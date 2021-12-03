package io.github.ryuu.adventurecraft.extensions.client.render;

import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;

import java.awt.image.BufferedImage;

public class ExFlowingLavaTextureBinder2 {

    public static boolean hasImages;
    public static int numFrames;
    public static int curFrame;

    public static int[] frameImages;
    public static int width;

    public static void loadImage() {
        loadImage("/custom_lava_flowing.png");
    }

    public static void loadImage(String texName) {
        BufferedImage bufferedimage = null;
        if (AccessMinecraft.getInstance().level != null) {
            bufferedimage = ((ExLevel) AccessMinecraft.getInstance().level).loadMapTexture(texName);
        }
        curFrame = 0;
        if (bufferedimage == null) {
            hasImages = false;
            return;
        }
        width = bufferedimage.getWidth();
        numFrames = bufferedimage.getHeight() / bufferedimage.getWidth();
        frameImages = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
        bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), frameImages, 0, bufferedimage.getWidth());
        hasImages = true;
    }
}
