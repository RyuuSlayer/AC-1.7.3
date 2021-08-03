package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMobSpawner;
import io.github.ryuu.adventurecraft.util.Coord;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.tile.Tile;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer extends TileEntityRenderer {
    public void render(TileEntityMobSpawner mobSpawner, double d, double d1, double d2, float f) {
        if (DebugMode.active) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.5F, (float)d2 + 0.5F);
            GL11.glLineWidth(6.0F);
            GL11.glShadeModel(7425);
            GL11.glBegin(1);
            for (int l = 0; l < 4; l++) {
                Coord min = mobSpawner.minVec[l];
                Coord max = mobSpawner.maxVec[l];
                for (int i = min.x; i <= max.x; i++) {
                    for (int j = min.y; j <= max.y; j++) {
                        for (int k = min.z; k <= max.z; k++) {
                            Tile b = Tile.m[mobSpawner.d.a(i, j, k)];
                            if (b != null && b.canBeTriggered()) {
                                GL11.glColor3f(0.0F, 0.0F, 0.0F);
                                GL11.glVertex3f(0.0F, 0.0F, 0.0F);
                                GL11.glColor3f(0.105F, 0.329F, 0.486F);
                                GL11.glVertex3f((i - mobSpawner.e), (j - mobSpawner.f), (k - mobSpawner.g));
                            }
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
        render((TileEntityMobSpawner)tileentity, d, d1, d2, f);
    }
}
