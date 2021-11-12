package io.github.ryuu.adventurecraft.mixin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.item.ItemType;

public class MixinCompassTextureBinder extends TextureBinder {
    private final Minecraft g;

    private final int[] h;

    private double i;

    private double j;

    public MixinCompassTextureBinder(Minecraft minecraft) {
        super(ItemType.aO.a(0));
        this.h = new int[256];
        this.g = minecraft;
        this.f = 1;
        try {
            BufferedImage bufferedimage = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
            int i = this.b % 16 * 16;
            int j = this.b / 16 * 16;
            bufferedimage.getRGB(i, j, 16, 16, this.h, 0, 16);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void onTick(Vec2 texRes) {
        for (int i = 0; i < 256; i++) {
            int j = this.h[i] >> 24 & 0xFF;
            int k = this.h[i] >> 16 & 0xFF;
            int l = this.h[i] >> 8 & 0xFF;
            int i1 = this.h[i] >> 0 & 0xFF;
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
        double d = 0.0D;
        if (this.g.f != null && this.g.h != null) {
            br chunkcoordinates = this.g.f.u();
            double d2 = chunkcoordinates.a - this.g.h.aM;
            double d4 = chunkcoordinates.c - this.g.h.aO;
            d = (this.g.h.aS - 90.0F) * Math.PI / 180.0D - Math.atan2(d4, d2);
            if (this.g.f.t.c)
                d = Math.random() * 3.1415927410125732D * 2.0D;
        }
        double d1;
        for (d1 = d - this.i; d1 < -3.141592653589793D; d1 += 6.283185307179586D) ;
        for (; d1 >= Math.PI; d1 -= 6.283185307179586D) ;
        if (d1 < -1.0D)
            d1 = -1.0D;
        if (d1 > 1.0D)
            d1 = 1.0D;
        this.j += d1 * 0.1D;
        this.j *= 0.8D;
        this.i += this.j;
        double d3 = Math.sin(this.i);
        double d5 = Math.cos(this.i);
        for (int i2 = -4; i2 <= 4; i2++) {
            int k2 = (int) (8.5D + d5 * i2 * 0.3D);
            int i3 = (int) (7.5D - d3 * i2 * 0.3D * 0.5D);
            int k3 = i3 * 16 + k2;
            int i4 = 100;
            int k4 = 100;
            int i5 = 100;
            char c = ';
            if (this.c) {
                int k5 = (i4 * 30 + k4 * 59 + i5 * 11) / 100;
                int i6 = (i4 * 30 + k4 * 70) / 100;
                int k6 = (i4 * 30 + i5 * 70) / 100;
                i4 = k5;
                k4 = i6;
                i5 = k6;
            }
            this.a[k3 * 4 + 0] = (byte) i4;
            this.a[k3 * 4 + 1] = (byte) k4;
            this.a[k3 * 4 + 2] = (byte) i5;
            this.a[k3 * 4 + 3] = (byte) c;
        }
        for (int j2 = -8; j2 <= 16; j2++) {
            int l2 = (int) (8.5D + d3 * j2 * 0.3D);
            int j3 = (int) (7.5D + d5 * j2 * 0.3D * 0.5D);
            int l3 = j3 * 16 + l2;
            int j4 = (j2 < 0) ? 100 : 255;
            int l4 = (j2 < 0) ? 100 : 20;
            int j5 = (j2 < 0) ? 100 : 20;
            char c1 = ';
            if (this.c) {
                int l5 = (j4 * 30 + l4 * 59 + j5 * 11) / 100;
                int j6 = (j4 * 30 + l4 * 70) / 100;
                int l6 = (j4 * 30 + j5 * 70) / 100;
                j4 = l5;
                l4 = j6;
                j5 = l6;
            }
            this.a[l3 * 4 + 0] = (byte) j4;
            this.a[l3 * 4 + 1] = (byte) l4;
            this.a[l3 * 4 + 2] = (byte) j5;
            this.a[l3 * 4 + 3] = (byte) c1;
        }
    }
}