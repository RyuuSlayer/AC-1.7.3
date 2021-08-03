package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityEffectRenderer extends TileEntityRenderer {
    public void render(TileEntityEffect effect, double d, double d1, double d2, float f) {
        if (DebugMode.active) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.5F, (float)d2 + 0.5F);
            GL11.glLineWidth(6.0F);
            GL11.glShadeModel(7425);
            GL11.glTranslatef(effect.offsetX, effect.offsetY, effect.offsetZ);
            GL11.glColor3f(1.0F, 0.0F, 0.0F);
            GL11.glBegin(1);
            GL11.glVertex3f(effect.randX, effect.randY, effect.randZ);
            GL11.glVertex3f(-effect.randX, effect.randY, effect.randZ);
            GL11.glVertex3f(effect.randX, effect.randY, effect.randZ);
            GL11.glVertex3f(effect.randX, -effect.randY, effect.randZ);
            GL11.glVertex3f(effect.randX, effect.randY, effect.randZ);
            GL11.glVertex3f(effect.randX, effect.randY, -effect.randZ);
            GL11.glVertex3f(-effect.randX, effect.randY, effect.randZ);
            GL11.glVertex3f(-effect.randX, -effect.randY, effect.randZ);
            GL11.glVertex3f(-effect.randX, effect.randY, effect.randZ);
            GL11.glVertex3f(-effect.randX, effect.randY, -effect.randZ);
            GL11.glVertex3f(effect.randX, -effect.randY, effect.randZ);
            GL11.glVertex3f(-effect.randX, -effect.randY, effect.randZ);
            GL11.glVertex3f(effect.randX, -effect.randY, effect.randZ);
            GL11.glVertex3f(effect.randX, -effect.randY, -effect.randZ);
            GL11.glVertex3f(effect.randX, effect.randY, -effect.randZ);
            GL11.glVertex3f(effect.randX, -effect.randY, -effect.randZ);
            GL11.glVertex3f(effect.randX, effect.randY, -effect.randZ);
            GL11.glVertex3f(-effect.randX, effect.randY, -effect.randZ);
            GL11.glVertex3f(-effect.randX, -effect.randY, -effect.randZ);
            GL11.glVertex3f(-effect.randX, -effect.randY, effect.randZ);
            GL11.glVertex3f(-effect.randX, -effect.randY, -effect.randZ);
            GL11.glVertex3f(effect.randX, -effect.randY, -effect.randZ);
            GL11.glVertex3f(-effect.randX, -effect.randY, -effect.randZ);
            GL11.glVertex3f(-effect.randX, effect.randY, -effect.randZ);
            GL11.glEnd();
            GL11.glShadeModel(7424);
            GL11.glLineWidth(1.0F);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    public void a(ow tileentity, double d, double d1, double d2, float f) {
        render((TileEntityEffect)tileentity, d, d1, d2, f);
    }
}
