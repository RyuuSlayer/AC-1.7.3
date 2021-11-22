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

@Mixin(CompassTextureBinder.class)
public class MixinCompassTextureBinder extends TextureBinder {

    @Shadow()
    private Minecraft field_1326;

    private int[] field_1327 = new int[256];

    private double field_1328;

    private double field_1329;

    public MixinCompassTextureBinder(Minecraft minecraft) {
        super(ItemType.compass.getTexturePosition(0));
        this.field_1326 = minecraft;
        this.renderMode = 1;
        try {
            BufferedImage bufferedimage = ImageIO.read((URL) Minecraft.class.getResource("/gui/items.png"));
            int i = this.field_1412 % 16 * 16;
            int j = this.field_1412 / 16 * 16;
            bufferedimage.getRGB(i, j, 16, 16, this.field_1327, 0, 16);
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
        for (int i = 0; i < 256; ++i) {
            int j = this.field_1327[i] >> 24 & 0xFF;
            int k = this.field_1327[i] >> 16 & 0xFF;
            int l = this.field_1327[i] >> 8 & 0xFF;
            int i1 = this.field_1327[i] >> 0 & 0xFF;
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
        double d = 0.0;
        if (this.field_1326.level != null && this.field_1326.player != null) {
            Vec3i chunkcoordinates = this.field_1326.level.getSpawnPosition();
            double d2 = (double) chunkcoordinates.x - this.field_1326.player.x;
            double d4 = (double) chunkcoordinates.z - this.field_1326.player.z;
            d = (double) (this.field_1326.player.yaw - 90.0f) * Math.PI / 180.0 - Math.atan2((double) d4, (double) d2);
            if (this.field_1326.level.dimension.hasFog) {
                d = Math.random() * 3.1415927410125732 * 2.0;
            }
        }
        for (d1 = d - this.field_1328; d1 < -Math.PI; d1 += Math.PI * 2) {
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
        this.field_1329 += d1 * 0.1;
        this.field_1329 *= 0.8;
        this.field_1328 += this.field_1329;
        double d3 = Math.sin((double) this.field_1328);
        double d5 = Math.cos((double) this.field_1328);
        for (int i2 = -4; i2 <= 4; ++i2) {
            int k2 = (int) (8.5 + d5 * (double) i2 * 0.3);
            int i3 = (int) (7.5 - d3 * (double) i2 * 0.3 * 0.5);
            int k3 = i3 * 16 + k2;
            int i4 = 100;
            int k4 = 100;
            int i5 = 100;
            int c = 255;
            if (this.render3d) {
                int k5 = (i4 * 30 + k4 * 59 + i5 * 11) / 100;
                int i6 = (i4 * 30 + k4 * 70) / 100;
                int k6 = (i4 * 30 + i5 * 70) / 100;
                i4 = k5;
                k4 = i6;
                i5 = k6;
            }
            this.grid[k3 * 4 + 0] = (byte) i4;
            this.grid[k3 * 4 + 1] = (byte) k4;
            this.grid[k3 * 4 + 2] = (byte) i5;
            this.grid[k3 * 4 + 3] = (byte) c;
        }
        for (int j2 = -8; j2 <= 16; ++j2) {
            int l2 = (int) (8.5 + d3 * (double) j2 * 0.3);
            int j3 = (int) (7.5 + d5 * (double) j2 * 0.3 * 0.5);
            int l3 = j3 * 16 + l2;
            int j4 = j2 < 0 ? 100 : 255;
            int l4 = j2 < 0 ? 100 : 20;
            int j5 = j2 < 0 ? 100 : 20;
            int c1 = 255;
            if (this.render3d) {
                int l5 = (j4 * 30 + l4 * 59 + j5 * 11) / 100;
                int j6 = (j4 * 30 + l4 * 70) / 100;
                int l6 = (j4 * 30 + j5 * 70) / 100;
                j4 = l5;
                l4 = j6;
                j5 = l6;
            }
            this.grid[l3 * 4 + 0] = (byte) j4;
            this.grid[l3 * 4 + 1] = (byte) l4;
            this.grid[l3 * 4 + 2] = (byte) j5;
            this.grid[l3 * 4 + 3] = (byte) c1;
        }
    }
}
