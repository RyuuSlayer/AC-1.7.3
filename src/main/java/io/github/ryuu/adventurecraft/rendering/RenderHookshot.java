package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityHookshot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderHookshot extends EntityRenderer {
    public void doRenderFish(EntityHookshot entityHookshot, double d, double d1, double d2, float f, float f1) {
        a("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(entityHookshot.aU + (entityHookshot.aS - entityHookshot.aU) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entityHookshot.aV + (entityHookshot.aT - entityHookshot.aV) * f1, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.a;
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
        if (entityHookshot.returnsTo != null) {
            float f12 = (entityHookshot.returnsTo.aU + (entityHookshot.returnsTo.aS - entityHookshot.returnsTo.aU) * f1) * 3.141593F / 180.0F;
            float f11 = (entityHookshot.returnsTo.aV + (entityHookshot.returnsTo.aT - entityHookshot.returnsTo.aV) * f1) * 3.141593F / 180.0F;
            double d3 = in.a(f12);
            double d5 = in.b(f12);
            double d7 = in.a(f11);
            double d8 = in.b(f11);
            double xOffset = 0.35D;
            double yOffset = 0.5D;
            if (!entityHookshot.mainHand)
                xOffset = -xOffset;
            double d9 = entityHookshot.returnsTo.aJ + (entityHookshot.returnsTo.aM - entityHookshot.returnsTo.aJ) * f1 - d5 * xOffset - d3 * yOffset * d8;
            double d10 = entityHookshot.returnsTo.aK + (entityHookshot.returnsTo.aN - entityHookshot.returnsTo.aK) * f1 - d7 * yOffset;
            double d11 = entityHookshot.returnsTo.aL + (entityHookshot.returnsTo.aO - entityHookshot.returnsTo.aL) * f1 - d3 * xOffset + d5 * yOffset * d8;
            if (this.b.k.A || Minecraft.minecraftInstance.cameraActive) {
                float f13 = (entityHookshot.returnsTo.I + (entityHookshot.returnsTo.H - entityHookshot.returnsTo.I) * f1) * 3.141593F / 180.0F;
                double d4 = in.a(f13);
                double d6 = in.b(f13);
                d9 = entityHookshot.returnsTo.aJ + (entityHookshot.returnsTo.aM - entityHookshot.returnsTo.aJ) * f1 - d6 * 0.35D - d4 * 0.85D;
                d10 = entityHookshot.returnsTo.aK + (entityHookshot.returnsTo.aN - entityHookshot.returnsTo.aK) * f1 - 0.45D;
                d11 = entityHookshot.returnsTo.aL + (entityHookshot.returnsTo.aO - entityHookshot.returnsTo.aL) * f1 - d4 * 0.35D + d6 * 0.85D;
            }
            double d12 = entityHookshot.aJ + (entityHookshot.aM - entityHookshot.aJ) * f1;
            double d13 = entityHookshot.aK + (entityHookshot.aN - entityHookshot.aK) * f1;
            double d14 = entityHookshot.aL + (entityHookshot.aO - entityHookshot.aL) * f1;
            double d15 = (float) (d9 - d12);
            double d16 = (float) (d10 - d13);
            double d17 = (float) (d11 - d14);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glLineWidth(3.0F);
            tessellator.a(3);
            tessellator.b(0);
            int m = 1;
            for (int k = 0; k <= m; k++) {
                f12 = k / m;
                tessellator.a(d + d15 * f12, d1 + d16 * f12, d2 + d17 * f12);
            }
            tessellator.a();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }

    public void a(Entity entity, double d, double d1, double d2, float f, float f1) {
        doRenderFish((EntityHookshot) entity, d, d1, d2, f, f1);
    }
}