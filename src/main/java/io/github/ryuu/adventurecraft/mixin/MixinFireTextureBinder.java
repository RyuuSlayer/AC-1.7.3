package io.github.ryuu.adventurecraft.mixin;

import java.awt.image.BufferedImage;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureBinder;

public class MixinFireTextureBinder extends TextureBinder {
    static boolean hasImages;
    static int numFrames;
    static int curFrame = 0;
    private static int[] frameImages;
    private static int width;
    protected float[] g;
    protected float[] h;

    public MixinFireTextureBinder(int i) {
        super(MixinTile.as.bm + i * 16);
        this.g = new float[320];
        this.h = new float[320];
    }

    public static void loadImage() {
        loadImage("/custom_fire.png");
    }

    public static void loadImage(String texName) {
        BufferedImage bufferedimage = null;
        if (Minecraft.minecraftInstance.f != null)
            bufferedimage = Minecraft.minecraftInstance.f.loadMapTexture(texName);
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

    public void onTick(Vec2 texRes) {
        int w = texRes.x / 16;
        int h = texRes.y / 16;
        if (hasImages) {
            int ratio = w / width;
            int frameOffset = curFrame * width * width;
            int i = 0;
            boolean shrink = false;
            if (ratio == 0) {
                shrink = true;
                ratio = width / w;
            }
            if (!shrink) {
                for (int j = 0; j < width; j++) {
                    for (int m = 0; m < width; m++) {
                        int curPixel = frameImages[m + j * width + frameOffset];
                        for (int x = 0; x < ratio; x++) {
                            for (int y = 0; y < ratio; y++) {
                                i = m * ratio + x + (j * ratio + y) * w;
                                this.a[i * 4 + 0] = (byte) (curPixel >> 16 & 0xFF);
                                this.a[i * 4 + 1] = (byte) (curPixel >> 8 & 0xFF);
                                this.a[i * 4 + 2] = (byte) (curPixel & 0xFF);
                                this.a[i * 4 + 3] = (byte) (curPixel >> 24 & 0xFF);
                            }
                        }
                    }
                }
            } else {
                for (int j = 0; j < w; j++) {
                    for (int m = 0; m < w; m++) {
                        int r = 0, g = 0, b = 0, a = 0;
                        for (int x = 0; x < ratio; x++) {
                            for (int y = 0; y < ratio; y++) {
                                int curPixel = frameImages[m * ratio + x + (j * ratio + y) * width + frameOffset];
                                r += curPixel >> 16 & 0xFF;
                                g += curPixel >> 8 & 0xFF;
                                b += curPixel & 0xFF;
                                a += curPixel >> 24 & 0xFF;
                            }
                        }
                        this.a[i * 4 + 0] = (byte) (r / ratio / ratio);
                        this.a[i * 4 + 1] = (byte) (g / ratio / ratio);
                        this.a[i * 4 + 2] = (byte) (b / ratio / ratio);
                        this.a[i * 4 + 3] = (byte) (a / ratio / ratio);
                        i++;
                    }
                }
            }
            curFrame = (curFrame + 1) % numFrames;
            return;
        }
        h = texRes.y / 16 * 20 / 16;
        if (this.g.length != w * h) {
            this.g = new float[w * h];
            this.h = new float[w * h];
        }
        float reduceAmount = 1.0F + 15.36F / texRes.y;
        int times = texRes.y / 256;
        int volatility = 14 + (times + 1) * (times + 1);
        if (times >= 4) {
            times = 2;
        } else {
            times = 1;
        }
        int t;
        for (t = 0; t < times; t++) {
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    int l = volatility;
                    float f1 = this.g[i + (j + 1) % h * w] * l;
                    for (int i1 = i - 1; i1 <= i + 1; i1++) {
                        for (int k1 = j; k1 <= j + 1; k1++) {
                            int i2 = i1 % w;
                            int k2 = k1;
                            while (i2 < 0)
                                i2 += w;
                            if (k2 >= 0 && k2 < h)
                                f1 += this.g[i2 + k2 * w];
                            l++;
                        }
                    }
                    this.h[i + j * w] = f1 / l * reduceAmount;
                    if (j >= h - 1)
                        this.h[i + j * w] = (float) (Math.random() * Math.random() * Math.random() * 4.0D + Math.random() * 0.10000000149011612D + 0.20000000298023224D);
                }
            }
            float[] af = this.h;
            this.h = this.g;
            this.g = af;
        }
        h = texRes.y / 16;
        t = w * h;
        for (int k = 0; k < t; k++) {
            float f = this.g[k] * 1.8F;
            if (f > 1.0F)
                f = 1.0F;
            if (f < 0.0F)
                f = 0.0F;
            float f2 = f;
            int j1 = (int) (f2 * 155.0F + 100.0F);
            int l1 = (int) (f2 * f2 * 255.0F);
            int j2 = (int) (f2 * f2 * f2 * f2 * f2 * f2 * f2 * f2 * f2 * f2 * 255.0F);
            char c = ';
            if (f2 < 0.5F)
                c = Character.MIN_VALUE;
            f2 = (f2 - 0.5F) * 2.0F;
            if (this.c) {
                int l2 = (j1 * 30 + l1 * 59 + j2 * 11) / 100;
                int i3 = (j1 * 30 + l1 * 70) / 100;
                int j3 = (j1 * 30 + j2 * 70) / 100;
                j1 = l2;
                l1 = i3;
                j2 = j3;
            }
            this.a[k * 4 + 0] = (byte) j1;
            this.a[k * 4 + 1] = (byte) l1;
            this.a[k * 4 + 2] = (byte) j2;
            this.a[k * 4 + 3] = (byte) c;
        }
    }
}
