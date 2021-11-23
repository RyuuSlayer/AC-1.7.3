package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.image.BufferedImage;
import java.util.Random;

@Mixin(PortalTextureBinder.class)
public class MixinPortalTextureBinder extends TextureBinder {

    static boolean hasImages;
    static int numFrames;
    static int curFrame;
    private static int[] frameImages;
    private static int width;

    static {
        curFrame = 0;
    }

    @Shadow()
    private int field_1091 = 0;
    private byte[][] field_1092;

    public MixinPortalTextureBinder() {
        super(Tile.PORTAL.tex);
        this.generatePortalData(16, 16);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void loadImage() {
        PortalTextureBinder.loadImage("/custom_portal.png");
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
    @Overwrite()
    private void generatePortalData(int w, int h) {
        this.field_1092 = new byte[32][w * h * 4];
        Random random = new Random(100L);
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < w; ++j) {
                for (int k = 0; k < h; ++k) {
                    float f = 0.0f;
                    for (int l = 0; l < 2; ++l) {
                        float f1 = l * w / 2;
                        float f2 = l * h / 2;
                        float f3 = ((float) j - f1) / (float) w * 2.0f;
                        float f4 = ((float) k - f2) / (float) h * 2.0f;
                        if (f3 < -1.0f) {
                            f3 += 2.0f;
                        }
                        if (f3 >= 1.0f) {
                            f3 -= 2.0f;
                        }
                        if (f4 < -1.0f) {
                            f4 += 2.0f;
                        }
                        if (f4 >= 1.0f) {
                            f4 -= 2.0f;
                        }
                        float f5 = f3 * f3 + f4 * f4;
                        float f6 = (float) Math.atan2(f4, f3) + ((float) i / 32.0f * 3.141593f * 2.0f - f5 * 10.0f + (float) (l * 2)) * (float) (l * 2 - 1);
                        f6 = (MathsHelper.sin(f6) + 1.0f) / 2.0f;
                        f += (f6 /= f5 + 1.0f) * 0.5f;
                    }
                    int i1 = (int) ((f += random.nextFloat() * 0.1f) * 100.0f + 155.0f);
                    int j1 = (int) (f * f * 200.0f + 55.0f);
                    int k1 = (int) (f * f * f * f * 255.0f);
                    int l1 = (int) (f * 100.0f + 155.0f);
                    int i2 = k * w + j;
                    this.field_1092[i][i2 * 4 + 0] = (byte) j1;
                    this.field_1092[i][i2 * 4 + 1] = (byte) k1;
                    this.field_1092[i][i2 * 4 + 2] = (byte) i1;
                    this.field_1092[i][i2 * 4 + 3] = (byte) l1;
                }
            }
        }
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
        int s = texRes.x * texRes.y / 256;
        if (this.field_1092[0].length != s * 4) {
            this.generatePortalData(texRes.x / 16, texRes.y / 16);
        }
        ++this.field_1091;
        byte[] abyte0 = this.field_1092[this.field_1091 & 0x1F];
        for (int i = 0; i < s; ++i) {
            int j = abyte0[i * 4 + 0] & 0xFF;
            int k = abyte0[i * 4 + 1] & 0xFF;
            int l = abyte0[i * 4 + 2] & 0xFF;
            int i1 = abyte0[i * 4 + 3] & 0xFF;
            if (this.render3d) {
                int j1 = (j * 30 + k * 59 + l * 11) / 100;
                int k1 = (j * 30 + k * 70) / 100;
                int l1 = (j * 30 + l * 70) / 100;
                j = j1;
                k = k1;
                l = l1;
            }
            this.grid[i * 4 + 0] = (byte) j;
            this.grid[i * 4 + 1] = (byte) k;
            this.grid[i * 4 + 2] = (byte) l;
            this.grid[i * 4 + 3] = (byte) i1;
        }
    }
}
