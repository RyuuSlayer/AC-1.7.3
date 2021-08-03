package io.github.ryuu.adventurecraft.overrides;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;

public class vs extends TextureBinder {
    protected float[] g;

    protected float[] h;

    protected float[] i;

    protected float[] j;

    private int k;

    static boolean hasImages;

    static int numFrames;

    private static int[] frameImages;

    private static int width;

    public vs() {
        super(uu.B.bm);
        this.g = new float[256];
        this.h = new float[256];
        this.i = new float[256];
        this.j = new float[256];
        this.k = 0;
    }

    public void onTick(Vec2 texRes) {
        int w = texRes.x / 16;
        int h = texRes.y / 16;
        if (hasImages) {
            int ratio = w / width;
            int frameOffset = curFrame * width * width;
            int k = 0;
            boolean shrink = false;
            if (ratio == 0) {
                shrink = true;
                ratio = width / w;
            }
            if (!shrink) {
                for (int m = 0; m < width; m++) {
                    for (int n = 0; n < width; n++) {
                        int curPixel = frameImages[n + m * width + frameOffset];
                        for (int x = 0; x < ratio; x++) {
                            for (int y = 0; y < ratio; y++) {
                                k = n * ratio + x + (m * ratio + y) * w;
                                this.a[k * 4 + 0] = (byte) (curPixel >> 16 & 0xFF);
                                this.a[k * 4 + 1] = (byte) (curPixel >> 8 & 0xFF);
                                this.a[k * 4 + 2] = (byte) (curPixel & 0xFF);
                                this.a[k * 4 + 3] = (byte) (curPixel >> 24 & 0xFF);
                            }
                        }
                    }
                }
            } else {
                for (int m = 0; m < w; m++) {
                    for (int n = 0; n < w; n++) {
                        int r = 0, g = 0, b = 0, a = 0;
                        for (int x = 0; x < ratio; x++) {
                            for (int y = 0; y < ratio; y++) {
                                int curPixel = frameImages[n * ratio + x + (m * ratio + y) * width + frameOffset];
                                r += curPixel >> 16 & 0xFF;
                                g += curPixel >> 8 & 0xFF;
                                b += curPixel & 0xFF;
                                a += curPixel >> 24 & 0xFF;
                            }
                        }
                        this.a[k * 4 + 0] = (byte) (r / ratio / ratio);
                        this.a[k * 4 + 1] = (byte) (g / ratio / ratio);
                        this.a[k * 4 + 2] = (byte) (b / ratio / ratio);
                        this.a[k * 4 + 3] = (byte) (a / ratio / ratio);
                        k++;
                    }
                }
            }
            curFrame = (curFrame + 1) % numFrames;
            return;
        }
        int s = w * h;
        if (this.g.length != s) {
            this.g = new float[s];
            this.h = new float[s];
            this.i = new float[s];
            this.j = new float[s];
        }
        int vw = (int) Math.sqrt((w / 16));
        float weight = (vw * 2 + 1) * 1.1F;
        this.k++;
        for (int i = 0; i < w; i++) {
            for (int k = 0; k < h; k++) {
                float f = 0.0F;
                for (int j1 = i - vw; j1 <= i + vw; j1++) {
                    int k1 = j1 & w - 1;
                    int i2 = k & h - 1;
                    f += this.g[k1 + i2 * w];
                }
                this.h[i + k * w] = f / weight + this.i[i + k * w] * 0.8F;
            }
        }
        for (int j = 0; j < w; j++) {
            for (int l = 0; l < h; l++) {
                this.i[j + l * w] = this.i[j + l * w] + this.j[j + l * w] * 0.05F;
                if (this.i[j + l * w] < 0.0F)
                    this.i[j + l * w] = 0.0F;
                this.j[j + l * w] = this.j[j + l * w] - 0.1F;
                if (Math.random() < 0.05D)
                    this.j[j + l * w] = 0.5F;
            }
        }
        float[] af = this.h;
        this.h = this.g;
        this.g = af;
        for (int i1 = 0; i1 < s; i1++) {
            int l1, j2, k2;
            float f1 = this.g[i1];
            if (f1 > 1.0F)
                f1 = 1.0F;
            if (f1 < 0.0F)
                f1 = 0.0F;
            float f2 = f1 * f1;
            if (AC_TerrainImage.isWaterLoaded) {
                l1 = (int) (127.0F + f2 * 128.0F);
                j2 = (int) (127.0F + f2 * 128.0F);
                k2 = (int) (127.0F + f2 * 128.0F);
            } else {
                l1 = (int) (32.0F + f2 * 32.0F);
                j2 = (int) (50.0F + f2 * 64.0F);
                k2 = 255;
            }
            int l2 = (int) (146.0F + f2 * 50.0F);
            if (this.c) {
                int i3 = (l1 * 30 + j2 * 59 + k2 * 11) / 100;
                int j3 = (l1 * 30 + j2 * 70) / 100;
                int k3 = (l1 * 30 + k2 * 70) / 100;
                l1 = i3;
                j2 = j3;
                k2 = k3;
            }
            this.a[i1 * 4 + 0] = (byte) l1;
            this.a[i1 * 4 + 1] = (byte) j2;
            this.a[i1 * 4 + 2] = (byte) k2;
            this.a[i1 * 4 + 3] = (byte) l2;
        }
    }

    public static void loadImage() {
        loadImage("/custom_water_still.png");
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

    static int curFrame = 0;
}
