package io.github.ryuu.adventurecraft.mixin.client.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.util.Vec2;

@Mixin(ClockTextureBinder.class)
public class MixinClockTextureBinder extends TextureBinder {

    @Shadow()
    private Minecraft field_1437;

    private int[] field_1438 = new int[256];

    private int[] field_1439 = new int[256];

    private double field_1440;

    private double field_1441;

    public MixinClockTextureBinder(Minecraft minecraft) {
        super(ItemType.clock.getTexturePosition(0));
        this.field_1437 = minecraft;
        this.renderMode = 1;
        try {
            BufferedImage bufferedimage = ImageIO.read((URL) Minecraft.class.getResource("/gui/items.png"));
            int i = this.field_1412 % 16 * 16;
            int j = this.field_1412 / 16 * 16;
            bufferedimage.getRGB(i, j, 16, 16, this.field_1438, 0, 16);
            bufferedimage = ImageIO.read((URL) Minecraft.class.getResource("/misc/dial.png"));
            bufferedimage.getRGB(0, 0, 16, 16, this.field_1439, 0, 16);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onTick(Vec2 texRes) {
        double d1;
        double d = 0.0;
        if (this.field_1437.level != null && this.field_1437.player != null) {
            float f = this.field_1437.level.method_198(1.0f);
            d = -f * 3.141593f * 2.0f;
            if (this.field_1437.level.dimension.hasFog) {
                d = Math.random() * 3.1415927410125732 * 2.0;
            }
        }
        for (d1 = d - this.field_1440; d1 < -Math.PI; d1 += Math.PI * 2) {
        }
        while (d1 >= Math.PI) {
            d1 -= Math.PI * 2;
        }
        if (d1 < -1.0) {
            d1 = -1.0;
        }
        if (d1 > 1.0) {
            d1 = 1.0;
        }
        this.field_1441 += d1 * 0.1;
        this.field_1441 *= 0.8;
        this.field_1440 += this.field_1441;
        double d2 = Math.sin((double) this.field_1440);
        double d3 = Math.cos((double) this.field_1440);
        for (int i = 0; i < 256; ++i) {
            int j = this.field_1438[i] >> 24 & 0xFF;
            int k = this.field_1438[i] >> 16 & 0xFF;
            int l = this.field_1438[i] >> 8 & 0xFF;
            int i1 = this.field_1438[i] >> 0 & 0xFF;
            if (k == i1 && l == 0 && i1 > 0) {
                double d4 = -((double) (i % 16) / 15.0 - 0.5);
                double d5 = (double) (i / 16) / 15.0 - 0.5;
                int i2 = k;
                int j2 = (int) ((d4 * d3 + d5 * d2 + 0.5) * 16.0);
                int k2 = (int) ((d5 * d3 - d4 * d2 + 0.5) * 16.0);
                int l2 = (j2 & 0xF) + (k2 & 0xF) * 16;
                j = this.field_1439[l2] >> 24 & 0xFF;
                k = (this.field_1439[l2] >> 16 & 0xFF) * i2 / 255;
                l = (this.field_1439[l2] >> 8 & 0xFF) * i2 / 255;
                i1 = (this.field_1439[l2] >> 0 & 0xFF) * i2 / 255;
            }
            if (this.render3d) {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }
            this.grid[i * 4 + 0] = (byte) k;
            this.grid[i * 4 + 1] = (byte) l;
            this.grid[i * 4 + 2] = (byte) i1;
            this.grid[i * 4 + 3] = (byte) j;
        }
    }
}
