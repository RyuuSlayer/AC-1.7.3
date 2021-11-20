package io.github.ryuu.adventurecraft.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureBinder;

public class TextureFanFX extends MixinTextureBinder {

    static int numFrames;

    int curFrame = 0;

    private static int[] fanImage;

    private static int width;

    private static int height;

    public TextureFanFX() {
        super(Blocks.fan.tex);
        TextureFanFX.loadImage();
    }

    public static void loadImage() {
        try {
            BufferedImage bufferedimage = null;
            if (Minecraft.minecraftInstance.level != null) {
                bufferedimage = Minecraft.minecraftInstance.level.loadMapTexture("/misc/fan.png");
            }
            if (bufferedimage == null) {
                bufferedimage = Minecraft.minecraftInstance.textureManager.getTextureImage("/misc/fan.png");
            }
            width = bufferedimage.getWidth();
            height = bufferedimage.getHeight();
            numFrames = bufferedimage.getWidth() / bufferedimage.getHeight();
            fanImage = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
            bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), fanImage, 0, bufferedimage.getWidth());
        } catch (IOException e) {
            System.out.println(":(");
            fanImage = new int[256];
            numFrames = 1;
            width = 16;
            height = 16;
        }
    }

    @Override
    public void onTick(Vec2 texRes) {
        int frameOffset = this.curFrame * height;
        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < height; ++j) {
                int curPixel = fanImage[i + j * width + frameOffset];
                this.grid[k * 4 + 0] = (byte) (curPixel >> 16 & 0xFF);
                this.grid[k * 4 + 1] = (byte) (curPixel >> 8 & 0xFF);
                this.grid[k * 4 + 2] = (byte) (curPixel & 0xFF);
                this.grid[k * 4 + 3] = (byte) (curPixel >> 24 & 0xFF);
                ++k;
            }
        }
        this.curFrame = (this.curFrame + 1) % numFrames;
    }
}
