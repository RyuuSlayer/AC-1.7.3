package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.tile.Tile;
import org.lwjgl.opengl.GL11;

public class TileEntityMinMaxRenderer extends je {
    float r;

    float g;

    float b;

    public TileEntityMinMaxRenderer(float red, float green, float blue) {
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    public void render(TileEntityMinMax minMax, double d, double d1, double d2, float f) {
        if (DebugMode.active) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.5F, (float) d2 + 0.5F);
            GL11.glLineWidth(6.0F);
            GL11.glShadeModel(7425);
            GL11.glBegin(1);
            for (int i = minMax.minX; i <= minMax.maxX; i++) {
                for (int j = minMax.minY; j <= minMax.maxY; j++) {
                    for (int k = minMax.minZ; k <= minMax.maxZ; k++) {
                        Tile block = net.minecraft.tile.Tile.m[minMax.d.a(i, j, k)];
                        if (block != null && block.canBeTriggered()) {
                            GL11.glColor3f(0.0F, 0.0F, 0.0F);
                            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
                            GL11.glColor3f(this.r, this.g, this.b);
                            GL11.glVertex3f((i - minMax.e), (j - minMax.f), (k - minMax.g));
                        }
                    }
                }
            }
            GL11.glEnd();
            GL11.glShadeModel(7424);
            GL11.glLineWidth(1.0F);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    public void a(ow tileentity, double d, double d1, double d2, float f) {
        render((TileEntityMinMax) tileentity, d, d1, d2, f);
    }
}
