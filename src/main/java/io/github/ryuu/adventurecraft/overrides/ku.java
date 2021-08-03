package io.github.ryuu.adventurecraft.overrides;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;

public class ku extends TextureBinder {
    private final Minecraft g;

    private final int[] h;

    private final int[] i;

    private double j;

    private double k;

    public ku(Minecraft minecraft) {
        super(gm.aQ.a(0));
        this.h = new int[256];
        this.i = new int[256];
        this.g = minecraft;
        this.f = 1;
        try {
            BufferedImage bufferedimage = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
            int i = this.b % 16 * 16;
            int j = this.b / 16 * 16;
            bufferedimage.getRGB(i, j, 16, 16, this.h, 0, 16);
            bufferedimage = ImageIO.read(Minecraft.class.getResource("/misc/dial.png"));
            bufferedimage.getRGB(0, 0, 16, 16, this.i, 0, 16);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void onTick(Vec2 texRes) {
        double d = 0.0D;
        if (this.g.f != null && this.g.h != null) {
            float f = this.g.f.b(1.0F);
            d = (-f * 3.141593F * 2.0F);
            if (this.g.f.t.c)
                d = Math.random() * 3.1415927410125732D * 2.0D;
        }
        double d1;
        for (d1 = d - this.j; d1 < -3.141592653589793D; d1 += 6.283185307179586D) ;
        for (; d1 >= Math.PI; d1 -= 6.283185307179586D) ;
        if (d1 < -1.0D)
            d1 = -1.0D;
        if (d1 > 1.0D)
            d1 = 1.0D;
        this.k += d1 * 0.1D;
        this.k *= 0.8D;
        this.j += this.k;
        double d2 = Math.sin(this.j);
        double d3 = Math.cos(this.j);
        for (int i = 0; i < 256; i++) {
            int j = this.h[i] >> 24 & 0xFF;
            int k = this.h[i] >> 16 & 0xFF;
            int l = this.h[i] >> 8 & 0xFF;
            int i1 = this.h[i] >> 0 & 0xFF;
            if (k == i1 && l == 0 && i1 > 0) {
                double d4 = -((i % 16) / 15.0D - 0.5D);
                double d5 = (i / 16) / 15.0D - 0.5D;
                int i2 = k;
                int j2 = (int) ((d4 * d3 + d5 * d2 + 0.5D) * 16.0D);
                int k2 = (int) ((d5 * d3 - d4 * d2 + 0.5D) * 16.0D);
                int l2 = (j2 & 0xF) + (k2 & 0xF) * 16;
                j = this.i[l2] >> 24 & 0xFF;
                k = (this.i[l2] >> 16 & 0xFF) * i2 / 255;
                l = (this.i[l2] >> 8 & 0xFF) * i2 / 255;
                i1 = (this.i[l2] >> 0 & 0xFF) * i2 / 255;
            }
            if (this.c) {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }
            this.a[i * 4 + 0] = (byte) k;
            this.a[i * 4 + 1] = (byte) l;
            this.a[i * 4 + 2] = (byte) i1;
            this.a[i * 4 + 3] = (byte) j;
        }
    }
}
