package io.github.ryuu.adventurecraft.mixin.client.render;

import java.awt.image.BufferedImage;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FlowingLavaTextureBinder2;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FlowingLavaTextureBinder2.class)
public class MixinFlowingLavaTextureBinder2 extends TextureBinder {

    @Shadow()
    protected float[] field_1166 = new float[256];

    protected float[] field_1167 = new float[256];

    protected float[] field_1168 = new float[256];

    protected float[] field_1169 = new float[256];

    int field_1170 = 0;

    static boolean hasImages;

    static int numFrames;

    private static int[] frameImages;

    private static int width;

    static int curFrame;

    public MixinFlowingLavaTextureBinder2() {
        super(Tile.FLOWING_LAVA.tex + 1);
        this.field_1415 = 2;
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
        if (this.field_1166.length != s) {
            this.field_1166 = new float[s];
            this.field_1167 = new float[s];
            this.field_1168 = new float[s];
            this.field_1169 = new float[s];
        }
        int vw = (int) Math.sqrt((double) (w / 16));
        int vh = (int) Math.sqrt((double) (h / 16));
        float totalWeight = (float) ((vw * 2 + 1) * (vh * 2 + 1)) * 1.1f;
        int speed = (int) Math.sqrt((double) (w / 16));
        this.field_1170 += speed;
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                float f = 0.0f;
                int l = (int) (MathsHelper.sin((float) j * 3.141593f * 2.0f / (float) w) * 1.2f);
                int i1 = (int) (MathsHelper.sin((float) i * 3.141593f * 2.0f / (float) h) * 1.2f);
                for (int k1 = i - vw; k1 <= i + vw; ++k1) {
                    for (int i2 = j - vh; i2 <= j + vh; ++i2) {
                        int k2 = k1 + l & w - 1;
                        int i3 = i2 + i1 & h - 1;
                        f += this.field_1166[k2 + i3 * w];
                    }
                }
                this.field_1167[i + j * w] = f / totalWeight + (this.field_1168[(i + 0 & w - 1) + (j + 0 & h - 1) * w] + this.field_1168[(i + 1 & w - 1) + (j + 0 & h - 1) * w] + this.field_1168[(i + 1 & w - 1) + (j + 1 & h - 1) * w] + this.field_1168[(i + 0 & w - 1) + (j + 1 & h - 1) * w]) / 4.0f * 0.8f;
                int n = i + j * w;
                this.field_1168[n] = this.field_1168[n] + this.field_1169[i + j * w] * 0.01f;
                if (this.field_1168[i + j * w] < 0.0f) {
                    this.field_1168[i + j * w] = 0.0f;
                }
                int n2 = i + j * w;
                this.field_1169[n2] = this.field_1169[n2] - 0.06f;
                if (!(Math.random() < 0.005))
                    continue;
                this.field_1169[i + j * w] = 1.5f;
            }
        }
        float[] af = this.field_1167;
        this.field_1167 = this.field_1166;
        this.field_1166 = af;
        for (int k = 0; k < s; ++k) {
            float f1 = this.field_1166[k - this.field_1170 / 3 * w & s - 1] * 2.0f;
            if (f1 > 1.0f) {
                f1 = 1.0f;
            }
            if (f1 < 0.0f) {
                f1 = 0.0f;
            }
            float f2 = f1;
            int j1 = (int) (f2 * 100.0f + 155.0f);
            int l1 = (int) (f2 * f2 * 255.0f);
            int j2 = (int) (f2 * f2 * f2 * f2 * 128.0f);
            if (this.render3d) {
                int l2 = (j1 * 30 + l1 * 59 + j2 * 11) / 100;
                int j3 = (j1 * 30 + l1 * 70) / 100;
                int k3 = (j1 * 30 + j2 * 70) / 100;
                j1 = l2;
                l1 = j3;
                j2 = k3;
            }
            this.grid[k * 4 + 0] = (byte) j1;
            this.grid[k * 4 + 1] = (byte) l1;
            this.grid[k * 4 + 2] = (byte) j2;
            this.grid[k * 4 + 3] = -1;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void loadImage() {
        FlowingLavaTextureBinder2.loadImage("/custom_lava_flowing.png");
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

    static {
        curFrame = 0;
    }
}
