package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.tile.entity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TileEntityEffectRenderer extends TileEntityRenderer {

    public void render(TileEntityEffect effect, double d, double d1, double d2, float f) {
        if (DebugMode.active) {
            GL11.glPushMatrix();
            GL11.glEnable((int) 3042);
            GL11.glBlendFunc((int) 770, (int) 771);
            GL11.glDisable((int) 3553);
            GL11.glDisable((int) 2896);
            GL11.glTranslatef((float) ((float) d + 0.5f), (float) ((float) d1 + 0.5f), (float) ((float) d2 + 0.5f));
            GL11.glLineWidth((float) 6.0f);
            GL11.glShadeModel((int) 7425);
            GL11.glTranslatef((float) effect.offsetX, (float) effect.offsetY, (float) effect.offsetZ);
            GL11.glColor3f((float) 1.0f, (float) 0.0f, (float) 0.0f);
            GL11.glBegin((int) 1);
            GL11.glVertex3f((float) effect.randX, (float) effect.randY, (float) effect.randZ);
            GL11.glVertex3f((float) (-effect.randX), (float) effect.randY, (float) effect.randZ);
            GL11.glVertex3f((float) effect.randX, (float) effect.randY, (float) effect.randZ);
            GL11.glVertex3f((float) effect.randX, (float) (-effect.randY), (float) effect.randZ);
            GL11.glVertex3f((float) effect.randX, (float) effect.randY, (float) effect.randZ);
            GL11.glVertex3f((float) effect.randX, (float) effect.randY, (float) (-effect.randZ));
            GL11.glVertex3f((float) (-effect.randX), (float) effect.randY, (float) effect.randZ);
            GL11.glVertex3f((float) (-effect.randX), (float) (-effect.randY), (float) effect.randZ);
            GL11.glVertex3f((float) (-effect.randX), (float) effect.randY, (float) effect.randZ);
            GL11.glVertex3f((float) (-effect.randX), (float) effect.randY, (float) (-effect.randZ));
            GL11.glVertex3f((float) effect.randX, (float) (-effect.randY), (float) effect.randZ);
            GL11.glVertex3f((float) (-effect.randX), (float) (-effect.randY), (float) effect.randZ);
            GL11.glVertex3f((float) effect.randX, (float) (-effect.randY), (float) effect.randZ);
            GL11.glVertex3f((float) effect.randX, (float) (-effect.randY), (float) (-effect.randZ));
            GL11.glVertex3f((float) effect.randX, (float) effect.randY, (float) (-effect.randZ));
            GL11.glVertex3f((float) effect.randX, (float) (-effect.randY), (float) (-effect.randZ));
            GL11.glVertex3f((float) effect.randX, (float) effect.randY, (float) (-effect.randZ));
            GL11.glVertex3f((float) (-effect.randX), (float) effect.randY, (float) (-effect.randZ));
            GL11.glVertex3f((float) (-effect.randX), (float) (-effect.randY), (float) (-effect.randZ));
            GL11.glVertex3f((float) (-effect.randX), (float) (-effect.randY), (float) effect.randZ);
            GL11.glVertex3f((float) (-effect.randX), (float) (-effect.randY), (float) (-effect.randZ));
            GL11.glVertex3f((float) effect.randX, (float) (-effect.randY), (float) (-effect.randZ));
            GL11.glVertex3f((float) (-effect.randX), (float) (-effect.randY), (float) (-effect.randZ));
            GL11.glVertex3f((float) (-effect.randX), (float) effect.randY, (float) (-effect.randZ));
            GL11.glEnd();
            GL11.glShadeModel((int) 7424);
            GL11.glLineWidth((float) 1.0f);
            GL11.glEnable((int) 3553);
            GL11.glDisable((int) 3042);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void render(TileEntity entity, double x, double y, double z, float f) {
        this.render((TileEntityEffect) entity, x, y, z, f);
    }
}
