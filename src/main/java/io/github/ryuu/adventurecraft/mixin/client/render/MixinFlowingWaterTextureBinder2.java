package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.extensions.client.render.ExFlowingWaterTextureBinder2;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.render.FlowingWaterTextureBinder2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FlowingWaterTextureBinder2.class)
public abstract class MixinFlowingWaterTextureBinder2 extends MixinTextureBinder {

    @Shadow
    protected float[] field_2566;

    @Shadow
    protected float[] field_2567;

    @Shadow
    protected float[] field_2568;

    @Shadow
    protected float[] field_2569;

    @Shadow
    private int field_2570;

    @Override
    public void onTick(Vec2 texRes) {
        int w = texRes.x / 16;
        int h = texRes.y / 16;
        int width = ExFlowingWaterTextureBinder2.width;
        if (ExFlowingWaterTextureBinder2.hasImages) {
            int ratio = w / width;
            int frameOffset = ExFlowingWaterTextureBinder2.curFrame * width * width;
            int k = 0;
            boolean shrink = false;
            if (ratio == 0) {
                shrink = true;
                ratio = width / w;
            }
            if (!shrink) {
                for (int i = 0; i < width; ++i) {
                    for (int j = 0; j < width; ++j) {
                        int curPixel = ExFlowingWaterTextureBinder2.frameImages[j + i * width + frameOffset];
                        for (int x = 0; x < ratio; ++x) {
                            for (int y = 0; y < ratio; ++y) {
                                k = j * ratio + x + (i * ratio + y) * w;
                                this.grid[k * 4] = (byte) (curPixel >> 16 & 0xFF);
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
                                int curPixel = ExFlowingWaterTextureBinder2.frameImages[j * ratio + x + (i * ratio + y) * width + frameOffset];
                                r += curPixel >> 16 & 0xFF;
                                g += curPixel >> 8 & 0xFF;
                                b += curPixel & 0xFF;
                                a += curPixel >> 24 & 0xFF;
                            }
                        }
                        this.grid[k * 4] = (byte) (r / ratio / ratio);
                        this.grid[k * 4 + 1] = (byte) (g / ratio / ratio);
                        this.grid[k * 4 + 2] = (byte) (b / ratio / ratio);
                        this.grid[k * 4 + 3] = (byte) (a / ratio / ratio);
                        ++k;
                    }
                }
            }
            ExFlowingWaterTextureBinder2.curFrame = (ExFlowingWaterTextureBinder2.curFrame + 1) % ExFlowingWaterTextureBinder2.numFrames;
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
            this.grid[i1 * 4] = (byte) l1;
            this.grid[i1 * 4 + 1] = (byte) j2;
            this.grid[i1 * 4 + 2] = (byte) k2;
            this.grid[i1 * 4 + 3] = (byte) l2;
        }
    }
}
