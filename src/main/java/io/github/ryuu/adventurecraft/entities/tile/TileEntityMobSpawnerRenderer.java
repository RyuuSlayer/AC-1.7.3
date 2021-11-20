package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer extends TileEntityRenderer {

    public void render(TileEntityMobSpawner mobSpawner, double d, double d1, double d2, float f) {
        if (DebugMode.active) {
            GL11.glPushMatrix();
            GL11.glEnable((int) 3042);
            GL11.glBlendFunc((int) 770, (int) 771);
            GL11.glDisable((int) 3553);
            GL11.glDisable((int) 2896);
            GL11.glTranslatef((float) ((float) d + 0.5f), (float) ((float) d1 + 0.5f), (float) ((float) d2 + 0.5f));
            GL11.glLineWidth((float) 6.0f);
            GL11.glShadeModel((int) 7425);
            GL11.glBegin((int) 1);
            for (int l = 0; l < 4; ++l) {
                Coord min = mobSpawner.minVec[l];
                Coord max = mobSpawner.maxVec[l];
                for (int i = min.x; i <= max.x; ++i) {
                    for (int j = min.y; j <= max.y; ++j) {
                        for (int k = min.z; k <= max.z; ++k) {
                            MixinTile b = Tile.BY_ID[mobSpawner.level.getTileId(i, j, k)];
                            if (b == null || !b.canBeTriggered())
                                continue;
                            GL11.glColor3f((float) 0.0f, (float) 0.0f, (float) 0.0f);
                            GL11.glVertex3f((float) 0.0f, (float) 0.0f, (float) 0.0f);
                            GL11.glColor3f((float) 0.105f, (float) 0.329f, (float) 0.486f);
                            GL11.glVertex3f((float) (i - mobSpawner.x), (float) (j - mobSpawner.y), (float) (k - mobSpawner.z));
                        }
                    }
                }
            }
            GL11.glEnd();
            GL11.glShadeModel((int) 7424);
            GL11.glLineWidth((float) 1.0f);
            GL11.glEnable((int) 3553);
            GL11.glDisable((int) 3042);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void render(MixinTileEntity entity, double x, double y, double z, float f) {
        this.render((TileEntityMobSpawner) entity, x, y, z, f);
    }
}
