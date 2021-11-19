package io.github.ryuu.adventurecraft.rendering;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.opengl.GL11
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.ArrowRenderer;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;

public class RenderArrowBomb extends ArrowRenderer {

    @Override
    public void render(Arrow entity, double x, double y, double z, float f, float f1) {
        this.bindTexture("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) ((float) x), (float) ((float) y), (float) ((float) z));
        GL11.glRotatef((float) (entity.prevYaw + (entity.yaw - entity.prevYaw) * f1 - 90.0f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        GL11.glRotatef((float) (entity.prevPitch + (entity.pitch - entity.prevPitch) * f1), (float) 0.0f, (float) 0.0f, (float) 1.0f);
        Tessellator tessellator = Tessellator.INSTANCE;
        int i = 0;
        float f2 = 0.0f;
        float f3 = 0.5f;
        float f4 = (float) (0 + i * 10) / 32.0f;
        float f5 = (float) (5 + i * 10) / 32.0f;
        float f6 = 0.0f;
        float f7 = 0.15625f;
        float f8 = (float) (5 + i * 10) / 32.0f;
        float f9 = (float) (10 + i * 10) / 32.0f;
        float f10 = 0.05625f;
        GL11.glEnable((int) 32826);
        float f11 = (float) entity.shake - f1;
        if (f11 > 0.0f) {
            float f12 = -MathsHelper.sin(f11 * 3.0f) * f11;
            GL11.glRotatef((float) f12, (float) 0.0f, (float) 0.0f, (float) 1.0f);
        }
        GL11.glRotatef((float) 45.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        GL11.glScalef((float) f10, (float) f10, (float) f10);
        GL11.glTranslatef((float) -4.0f, (float) 0.0f, (float) 0.0f);
        GL11.glNormal3f((float) f10, (float) 0.0f, (float) 0.0f);
        tessellator.start();
        tessellator.vertex(-7.0, -2.0, -2.0, f6, f8);
        tessellator.vertex(-7.0, -2.0, 2.0, f7, f8);
        tessellator.vertex(-7.0, 2.0, 2.0, f7, f9);
        tessellator.vertex(-7.0, 2.0, -2.0, f6, f9);
        tessellator.draw();
        GL11.glNormal3f((float) (-f10), (float) 0.0f, (float) 0.0f);
        tessellator.start();
        tessellator.vertex(-7.0, 2.0, -2.0, f6, f8);
        tessellator.vertex(-7.0, 2.0, 2.0, f7, f8);
        tessellator.vertex(-7.0, -2.0, 2.0, f7, f9);
        tessellator.vertex(-7.0, -2.0, -2.0, f6, f9);
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
            GL11.glNormal3f((float) 0.0f, (float) 0.0f, (float) f10);
            tessellator.start();
            tessellator.vertex(-8.0, -2.0, 0.0, f2, f4);
            tessellator.vertex(8.0, -2.0, 0.0, f3, f4);
            tessellator.vertex(8.0, 2.0, 0.0, f3, f5);
            tessellator.vertex(-8.0, 2.0, 0.0, f2, f5);
            tessellator.draw();
        }
        GL11.glDisable((int) 32826);
        GL11.glPopMatrix();
    }
}
