package io.github.ryuu.adventurecraft.rendering;

import org.lwjgl.opengl.GL11;

public class RenderArrowBomb extends mc {
    public void a(sl entityarrow, double d, double d1, double d2, float f, float f1) {
        a("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(entityarrow.aU + (entityarrow.aS - entityarrow.aU) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entityarrow.aV + (entityarrow.aT - entityarrow.aV) * f1, 0.0F, 0.0F, 1.0F);
        nw tessellator = nw.a;
        int i = 0;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (0 + i * 10) / 32.0F;
        float f5 = (5 + i * 10) / 32.0F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (5 + i * 10) / 32.0F;
        float f9 = (10 + i * 10) / 32.0F;
        float f10 = 0.05625F;
        GL11.glEnable(32826);
        float f11 = entityarrow.b - f1;
        if (f11 > 0.0F) {
            float f12 = -in.a(f11 * 3.0F) * f11;
            GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
        }
        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        tessellator.b();
        tessellator.a(-7.0D, -2.0D, -2.0D, f6, f8);
        tessellator.a(-7.0D, -2.0D, 2.0D, f7, f8);
        tessellator.a(-7.0D, 2.0D, 2.0D, f7, f9);
        tessellator.a(-7.0D, 2.0D, -2.0D, f6, f9);
        tessellator.a();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        tessellator.b();
        tessellator.a(-7.0D, 2.0D, -2.0D, f6, f8);
        tessellator.a(-7.0D, 2.0D, 2.0D, f7, f8);
        tessellator.a(-7.0D, -2.0D, 2.0D, f7, f9);
        tessellator.a(-7.0D, -2.0D, -2.0D, f6, f9);
        tessellator.a();
        for (int j = 0; j < 4; j++) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.b();
            tessellator.a(-8.0D, -2.0D, 0.0D, f2, f4);
            tessellator.a(8.0D, -2.0D, 0.0D, f3, f4);
            tessellator.a(8.0D, 2.0D, 0.0D, f3, f5);
            tessellator.a(-8.0D, 2.0D, 0.0D, f2, f5);
            tessellator.a();
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
