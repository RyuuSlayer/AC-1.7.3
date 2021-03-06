package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.extensions.client.render.ExFlowingLavaTextureBinder2;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.render.FlowingLavaTextureBinder2;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FlowingLavaTextureBinder2.class)
public abstract class MixinFlowingLavaTextureBinder2 extends MixinTextureBinder {

    @Shadow
    protected float[] field_1166;

    @Shadow
    protected float[] field_1167;

    @Shadow
    protected float[] field_1168;

    @Shadow
    protected float[] field_1169;

    @Shadow
    int field_1170;

    @Override
    public void onTick(Vec2 texRes) {
        int w = texRes.x / 16;
        int h = texRes.y / 16;
        int width = ExFlowingLavaTextureBinder2.width;
        if (ExFlowingLavaTextureBinder2.hasImages) {
            int ratio = w / width;
            int frameOffset = ExFlowingLavaTextureBinder2.curFrame * width * width;
            int k = 0;
            boolean shrink = false;
            if (ratio == 0) {
                shrink = true;
                ratio = width / w;
            }
            if (!shrink) {
                for (int i = 0; i < width; ++i) {
                    for (int j = 0; j < width; ++j) {
                        int curPixel = ExFlowingLavaTextureBinder2.frameImages[j + i * width + frameOffset];
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
                                int curPixel = ExFlowingLavaTextureBinder2.frameImages[j * ratio + x + (i * ratio + y) * width + frameOffset];
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
            ExFlowingLavaTextureBinder2.curFrame = (ExFlowingLavaTextureBinder2.curFrame + 1) % ExFlowingLavaTextureBinder2.numFrames;
            return;
        }
        int s = w * h;
        if (this.field_1166.length != s) {
            this.field_1166 = new float[s];
            this.field_1167 = new float[s];
            this.field_1168 = new float[s];
            this.field_1169 = new float[s];
        }
        int vw = (int) Math.sqrt(w / 16);
        int vh = (int) Math.sqrt(h / 16);
        float totalWeight = (float) ((vw * 2 + 1) * (vh * 2 + 1)) * 1.1f;
        int speed = (int) Math.sqrt(w / 16);
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
                this.field_1167[i + j * w] = f / totalWeight + (this.field_1168[(i & w - 1) + (j & h - 1) * w] + this.field_1168[(i + 1 & w - 1) + (j & h - 1) * w] + this.field_1168[(i + 1 & w - 1) + (j + 1 & h - 1) * w] + this.field_1168[(i & w - 1) + (j + 1 & h - 1) * w]) / 4.0f * 0.8f;
                int n = i + j * w;
                this.field_1168[n] = this.field_1168[n] + this.field_1169[i + j * w] * 0.01f;
                if (this.field_1168[i + j * w] < 0.0f) {
                    this.field_1168[i + j * w] = 0.0f;
                }
                int n2 = i + j * w;
                this.field_1169[n2] = this.field_1169[n2] - 0.06f;
                if (!(Math.random() < 0.005)) continue;
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
            this.grid[k * 4] = (byte) j1;
            this.grid[k * 4 + 1] = (byte) l1;
            this.grid[k * 4 + 2] = (byte) j2;
            this.grid[k * 4 + 3] = -1;
        }
    }
}
