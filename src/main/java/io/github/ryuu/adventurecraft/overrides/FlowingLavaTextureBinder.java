package io.github.ryuu.adventurecraft.overrides;

import java.awt.image.BufferedImage;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;

public class FlowingLavaTextureBinder extends TextureBinder {
    protected float[] g;

    protected float[] h;

    protected float[] i;

    protected float[] j;

    static boolean hasImages;

    static int numFrames;

    private static int[] frameImages;

    private static int width;

    public FlowingLavaTextureBinder() {
        super(Tile.D.bm);
        this.g = new float[256];
        this.h = new float[256];
        this.i = new float[256];
        this.j = new float[256];
    }

    public void onTick(Vec2 texRes) {
        int w = texRes.x / 16;
        int h = texRes.y / 16;
        if (hasImages) {
            int ratio = w / width;
            int frameOffset = curFrame * width * width;
            int j = 0;
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
                                j = n * ratio + x + (m * ratio + y) * w;
                                this.a[j * 4 + 0] = (byte) (curPixel >> 16 & 0xFF);
                                this.a[j * 4 + 1] = (byte) (curPixel >> 8 & 0xFF);
                                this.a[j * 4 + 2] = (byte) (curPixel & 0xFF);
                                this.a[j * 4 + 3] = (byte) (curPixel >> 24 & 0xFF);
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
                        this.a[j * 4 + 0] = (byte) (r / ratio / ratio);
                        this.a[j * 4 + 1] = (byte) (g / ratio / ratio);
                        this.a[j * 4 + 2] = (byte) (b / ratio / ratio);
                        this.a[j * 4 + 3] = (byte) (a / ratio / ratio);
                        j++;
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
        int vh = (int) Math.sqrt((h / 16));
        float totalWeight = ((vw * 2 + 1) * (vh * 2 + 1)) * 1.1F;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                float f = 0.0F;
                int l = (int) (in.a(j * 3.141593F * 2.0F / w) * 1.2F);
                int i1 = (int) (in.a(i * 3.141593F * 2.0F / h) * 1.2F);
                for (int k1 = i - vw; k1 <= i + vw; k1++) {
                    for (int i2 = j - vh; i2 <= j + vh; i2++) {
                        int k2 = k1 + l & w - 1;
                        int i3 = i2 + i1 & h - 1;
                        f += this.g[k2 + i3 * w];
                    }
                }
                this.h[i + j * w] = f / totalWeight + (this.i[(i + 0 & w - 1) + (j + 0 & h - 1) * w] + this.i[(i + 1 & w - 1) + (j + 0 & h - 1) * w] + this.i[(i + 1 & w - 1) + (j + 1 & h - 1) * w] + this.i[(i + 0 & w - 1) + (j + 1 & h - 1) * w]) / 4.0F * 0.8F;
                this.i[i + j * w] = this.i[i + j * w] + this.j[i + j * w] * 0.01F;
                if (this.i[i + j * w] < 0.0F)
                    this.i[i + j * w] = 0.0F;
                this.j[i + j * w] = this.j[i + j * w] - 0.06F;
                if (Math.random() < 0.005D)
                    this.j[i + j * w] = 1.5F;
            }
        }
        float[] af = this.h;
        this.h = this.g;
        this.g = af;
        for (int k = 0; k < s; k++) {
            float f1 = this.g[k] * 2.0F;
            if (f1 > 1.0F)
                f1 = 1.0F;
            if (f1 < 0.0F)
                f1 = 0.0F;
            float f2 = f1;
            int j1 = (int) (f2 * 100.0F + 155.0F);
            int l1 = (int) (f2 * f2 * 255.0F);
            int j2 = (int) (f2 * f2 * f2 * f2 * 128.0F);
            if (this.c) {
                int l2 = (j1 * 30 + l1 * 59 + j2 * 11) / 100;
                int j3 = (j1 * 30 + l1 * 70) / 100;
                int k3 = (j1 * 30 + j2 * 70) / 100;
                j1 = l2;
                l1 = j3;
                j2 = k3;
            }
            this.a[k * 4 + 0] = (byte) j1;
            this.a[k * 4 + 1] = (byte) l1;
            this.a[k * 4 + 2] = (byte) j2;
            this.a[k * 4 + 3] = -1;
        }
    }

    public static void loadImage() {
        loadImage("/custom_lava_still.png");
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
