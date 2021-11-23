package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FireTextureBinder;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.image.BufferedImage;

@Mixin(FireTextureBinder.class)
public class MixinFireTextureBinder extends TextureBinder {

    static boolean hasImages;
    static int numFrames;
    static int curFrame;
    private static int[] frameImages;
    private static int width;

    static {
        curFrame = 0;
    }

    @Shadow()
    protected float[] field_2459 = new float[320];
    protected float[] field_2460 = new float[320];

    public MixinFireTextureBinder(int i) {
        super(Tile.FIRE.tex + i * 16);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void loadImage() {
        FireTextureBinder.loadImage("/custom_fire.png");
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void loadImage(String texName) {
        BufferedImage bufferedimage = null;
        if (Minecraft.minecraftInstance.level != null) {
            bufferedimage = Minecraft.minecraftInstance.level.loadMapTexture(texName);
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onTick(Vec2 texRes) {
        int t;
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
                for (int i = 0; i < width; ++i) {
                    for (int j = 0; j < width; ++j) {
                        int curPixel = frameImages[j + i * width + frameOffset];
                        for (int x = 0; x < ratio; ++x) {
                            for (int y = 0; y < ratio; ++y) {
                                k = j * ratio + x + (i * ratio + y) * w;
                                this.grid[k * 4 + 0] = (byte) (curPixel >> 16 & 0xFF);
                                this.grid[k * 4 + 1] = (byte) (curPixel >> 8 & 0xFF);
                                this.grid[k * 4 + 2] = (byte) (curPixel & 0xFF);
                                this.grid[k * 4 + 3] = (byte) (curPixel >> 24 & 0xFF);
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < w; ++i) {
                    for (int j = 0; j < w; ++j) {
                        int r = 0;
                        int g = 0;
                        int b = 0;
                        int a = 0;
                        for (int x = 0; x < ratio; ++x) {
                            for (int y = 0; y < ratio; ++y) {
                                int curPixel = frameImages[j * ratio + x + (i * ratio + y) * width + frameOffset];
                                r += curPixel >> 16 & 0xFF;
                                g += curPixel >> 8 & 0xFF;
                                b += curPixel & 0xFF;
                                a += curPixel >> 24 & 0xFF;
                            }
                        }
                        this.grid[k * 4 + 0] = (byte) (r / ratio / ratio);
                        this.grid[k * 4 + 1] = (byte) (g / ratio / ratio);
                        this.grid[k * 4 + 2] = (byte) (b / ratio / ratio);
                        this.grid[k * 4 + 3] = (byte) (a / ratio / ratio);
                        ++k;
                    }
                }
            }
            curFrame = (curFrame + 1) % numFrames;
            return;
        }
        h = texRes.y / 16 * 20 / 16;
        if (this.field_2459.length != w * h) {
            this.field_2459 = new float[w * h];
            this.field_2460 = new float[w * h];
        }
        float reduceAmount = 1.0f + 15.36f / (float) texRes.y;
        int times = texRes.y / 256;
        int volatility = 14 + (times + 1) * (times + 1);
        times = times >= 4 ? 2 : 1;
        for (t = 0; t < times; ++t) {
            for (int j = 0; j < h; ++j) {
                for (int i = 0; i < w; ++i) {
                    int l = volatility;
                    float f1 = this.field_2459[i + (j + 1) % h * w] * (float) l;
                    for (int i1 = i - 1; i1 <= i + 1; ++i1) {
                        for (int k1 = j; k1 <= j + 1; ++k1) {
                            int i2;
                            int k2 = k1;
                            for (i2 = i1 % w; i2 < 0; i2 += w) {
                            }
                            if (k2 >= 0 && k2 < h) {
                                f1 += this.field_2459[i2 + k2 * w];
                            }
                            ++l;
                        }
                    }
                    this.field_2460[i + j * w] = f1 / ((float) l * reduceAmount);
                    if (j < h - 1) continue;
                    this.field_2460[i + j * w] = (float) (Math.random() * Math.random() * Math.random() * 4.0 + Math.random() * (double) 0.1f + (double) 0.2f);
                }
            }
            float[] af = this.field_2460;
            this.field_2460 = this.field_2459;
            this.field_2459 = af;
        }
        h = texRes.y / 16;
        t = w * h;
        for (int k = 0; k < t; ++k) {
            float f = this.field_2459[k] * 1.8f;
            if (f > 1.0f) {
                f = 1.0f;
            }
            if (f < 0.0f) {
                f = 0.0f;
            }
            float f2 = f;
            int j1 = (int) (f2 * 155.0f + 100.0f);
            int l1 = (int) (f2 * f2 * 255.0f);
            int j2 = (int) (f2 * f2 * f2 * f2 * f2 * f2 * f2 * f2 * f2 * f2 * 255.0f);
            int c = 255;
            if (f2 < 0.5f) {
                c = 0;
            }
            f2 = (f2 - 0.5f) * 2.0f;
            if (this.render3d) {
                int l2 = (j1 * 30 + l1 * 59 + j2 * 11) / 100;
                int i3 = (j1 * 30 + l1 * 70) / 100;
                int j3 = (j1 * 30 + j2 * 70) / 100;
                j1 = l2;
                l1 = i3;
                j2 = j3;
            }
            this.grid[k * 4 + 0] = (byte) j1;
            this.grid[k * 4 + 1] = (byte) l1;
            this.grid[k * 4 + 2] = (byte) j2;
            this.grid[k * 4 + 3] = (byte) c;
        }
    }
}
