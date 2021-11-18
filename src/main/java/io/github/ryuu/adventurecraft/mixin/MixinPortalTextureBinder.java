package io.github.ryuu.adventurecraft.mixin;

import java.awt.image.BufferedImage;
import java.util.Random;

import io.github.ryuu.adventurecraft.mixin.tile.MixinTile;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.util.maths.MathsHelper;

public class MixinPortalTextureBinder extends TextureBinder {
    static boolean hasImages;
    static int numFrames;
    static int curFrame = 0;
    private static int[] frameImages;
    private static int width;
    private int g;
    private byte[][] h;

    public MixinPortalTextureBinder() {
        super(MixinTile.bf.bm);
        this.g = 0;
        generatePortalData(16, 16);
    }

    public static void loadImage() {
        loadImage("/custom_portal.png");
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

    private void generatePortalData(int w, int h) {
        this.h = new byte[32][w * h * 4];
        Random random = new Random(100L);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < w; j++) {
                for (int k = 0; k < h; k++) {
                    float f = 0.0F;
                    for (int l = 0; l < 2; l++) {
                        float f1 = (l * w / 2);
                        float f2 = (l * h / 2);
                        float f3 = (j - f1) / w * 2.0F;
                        float f4 = (k - f2) / h * 2.0F;
                        if (f3 < -1.0F)
                            f3 += 2.0F;
                        if (f3 >= 1.0F)
                            f3 -= 2.0F;
                        if (f4 < -1.0F)
                            f4 += 2.0F;
                        if (f4 >= 1.0F)
                            f4 -= 2.0F;
                        float f5 = f3 * f3 + f4 * f4;
                        float f6 = (float) Math.atan2(f4, f3) + (i / 32.0F * 3.141593F * 2.0F - f5 * 10.0F + (l * 2)) * (l * 2 - 1);
                        f6 = (MathsHelper.a(f6) + 1.0F) / 2.0F;
                        f6 /= f5 + 1.0F;
                        f += f6 * 0.5F;
                    }
                    f += random.nextFloat() * 0.1F;
                    int i1 = (int) (f * 100.0F + 155.0F);
                    int j1 = (int) (f * f * 200.0F + 55.0F);
                    int k1 = (int) (f * f * f * f * 255.0F);
                    int l1 = (int) (f * 100.0F + 155.0F);
                    int i2 = k * w + j;
                    this.h[i][i2 * 4 + 0] = (byte) j1;
                    this.h[i][i2 * 4 + 1] = (byte) k1;
                    this.h[i][i2 * 4 + 2] = (byte) i1;
                    this.h[i][i2 * 4 + 3] = (byte) l1;
                }
            }
        }
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
                for (int j = 0; j < width; j++) {
                    for (int m = 0; m < width; m++) {
                        int curPixel = frameImages[m + j * width + frameOffset];
                        for (int x = 0; x < ratio; x++) {
                            for (int y = 0; y < ratio; y++) {
                                k = m * ratio + x + (j * ratio + y) * w;
                                this.a[k * 4 + 0] = (byte) (curPixel >> 16 & 0xFF);
                                this.a[k * 4 + 1] = (byte) (curPixel >> 8 & 0xFF);
                                this.a[k * 4 + 2] = (byte) (curPixel & 0xFF);
                                this.a[k * 4 + 3] = (byte) (curPixel >> 24 & 0xFF);
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
        int s = texRes.x * texRes.y / 256;
        if ((this.h[0]).length != s * 4)
            generatePortalData(texRes.x / 16, texRes.y / 16);
        this.g++;
        byte[] abyte0 = this.h[this.g & 0x1F];
        for (int i = 0; i < s; i++) {
            int j = abyte0[i * 4 + 0] & 0xFF;
            int k = abyte0[i * 4 + 1] & 0xFF;
            int l = abyte0[i * 4 + 2] & 0xFF;
            int i1 = abyte0[i * 4 + 3] & 0xFF;
            if (this.c) {
                int j1 = (j * 30 + k * 59 + l * 11) / 100;
                int k1 = (j * 30 + k * 70) / 100;
                int l1 = (j * 30 + l * 70) / 100;
                j = j1;
                k = k1;
                l = l1;
            }
            this.a[i * 4 + 0] = (byte) j;
            this.a[i * 4 + 1] = (byte) k;
            this.a[i * 4 + 2] = (byte) l;
            this.a[i * 4 + 3] = (byte) i1;
        }
    }
}
