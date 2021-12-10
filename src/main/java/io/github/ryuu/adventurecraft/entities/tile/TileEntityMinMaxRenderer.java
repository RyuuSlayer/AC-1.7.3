package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileEntityMinMaxRenderer extends TileEntityRenderer {

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
            GL11.glTranslatef((float) d + 0.5f, (float) d1 + 0.5f, (float) d2 + 0.5f);
            GL11.glLineWidth(6.0f);
            GL11.glShadeModel(7425);
            GL11.glBegin(1);
            for (int i = minMax.minX; i <= minMax.maxX; ++i) {
                for (int j = minMax.minY; j <= minMax.maxY; ++j) {
                    for (int k = minMax.minZ; k <= minMax.maxZ; ++k) {
                        Tile block = Tile.BY_ID[minMax.level.getTileId(i, j, k)];
                        if (block == null || !((ExTile)block).canBeTriggered()) continue;
                        GL11.glColor3f(0.0f, 0.0f, 0.0f);
                        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
                        GL11.glColor3f(this.r, this.g, this.b);
                        GL11.glVertex3f((float) (i - minMax.x), (float) (j - minMax.y), (float) (k - minMax.z));
                    }
                }
            }
            GL11.glEnd();
            GL11.glShadeModel(7424);
            GL11.glLineWidth(1.0f);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void render(TileEntity entity, double x, double y, double z, float f) {
        this.render((TileEntityMinMax) entity, x, y, z, f);
    }
}
