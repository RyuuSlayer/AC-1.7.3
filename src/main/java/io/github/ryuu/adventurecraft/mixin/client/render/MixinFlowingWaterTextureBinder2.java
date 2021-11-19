package io.github.ryuu.adventurecraft.mixin.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FlowingWaterTextureBinder2;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.image.BufferedImage;

@Mixin(FlowingWaterTextureBinder2.class)
public class MixinFlowingWaterTextureBinder2 extends MixinTextureBinder {

    static boolean hasImages;
    static int numFrames;
    static int curFrame;
    private static int[] frameImages;
    private static int width;

    static {
        curFrame = 0;
    }

    @Shadow()
    protected float[] field_2566 = new float[256];
    protected float[] field_2567 = new float[256];
    protected float[] field_2568 = new float[256];
    protected float[] field_2569 = new float[256];
    private int field_2570 = 0;

    public MixinFlowingWaterTextureBinder2() {
        super(Tile.FLOWING_WATER.tex);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void loadImage() {
        FlowingWaterTextureBinder2.loadImage("/custom_water_still.png");
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
        int s = w * h;
        if (this.field_2566.length != s) {
            this.field_2566 = new float[s];
            this.field_2567 = new float[s];
            this.field_2568 = new float[s];
            this.field_2569 = new float[s];
        }
        int vw = (int) Math.sqrt(w / 16);
        float weight = (float) (vw * 2 + 1) * 1.1f;
        ++this.field_2570;
        for (int i = 0; i < w; ++i) {
            for (int k = 0; k < h; ++k) {
                float f = 0.0f;
                for (int j1 = i - vw; j1 <= i + vw; ++j1) {
                    int k1 = j1 & w - 1;
                    int i2 = k & h - 1;
                    f += this.field_2566[k1 + i2 * w];
                }
                this.field_2567[i + k * w] = f / weight + this.field_2568[i + k * w] * 0.8f;
            }
        }
        for (int j = 0; j < w; ++j) {
            for (int l = 0; l < h; ++l) {
                int n = j + l * w;
                this.field_2568[n] = this.field_2568[n] + this.field_2569[j + l * w] * 0.05f;
                if (this.field_2568[j + l * w] < 0.0f) {
                    this.field_2568[j + l * w] = 0.0f;
                }
                int n2 = j + l * w;
                this.field_2569[n2] = this.field_2569[n2] - 0.1f;
                if (!(Math.random() < 0.05)) continue;
                this.field_2569[j + l * w] = 0.5f;
            }
        }
        float[] af = this.field_2567;
        this.field_2567 = this.field_2566;
        this.field_2566 = af;
        for (int i1 = 0; i1 < s; ++i1) {
            int k2;
            int j2;
            int l1;
            float f1 = this.field_2566[i1];
            if (f1 > 1.0f) {
                f1 = 1.0f;
            }
            if (f1 < 0.0f) {
                f1 = 0.0f;
            }
            float f2 = f1 * f1;
            if (TerrainImage.isWaterLoaded) {
                l1 = (int) (127.0f + f2 * 128.0f);
                j2 = (int) (127.0f + f2 * 128.0f);
                k2 = (int) (127.0f + f2 * 128.0f);
            } else {
                l1 = (int) (32.0f + f2 * 32.0f);
                j2 = (int) (50.0f + f2 * 64.0f);
                k2 = 255;
            }
            int l2 = (int) (146.0f + f2 * 50.0f);
            if (this.render3d) {
                int i3 = (l1 * 30 + j2 * 59 + k2 * 11) / 100;
                int j3 = (l1 * 30 + j2 * 70) / 100;
                int k3 = (l1 * 30 + k2 * 70) / 100;
                l1 = i3;
                j2 = j3;
                k2 = k3;
            }
            this.grid[i1 * 4 + 0] = (byte) l1;
            this.grid[i1 * 4 + 1] = (byte) j2;
            this.grid[i1 * 4 + 2] = (byte) k2;
            this.grid[i1 * 4 + 3] = (byte) l2;
        }
    }
}
