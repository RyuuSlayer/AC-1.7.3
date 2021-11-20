package io.github.ryuu.adventurecraft.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;

public class RenderHookshot extends EntityRenderer {

    public void doRenderFish(EntityHookshot entityHookshot, double d, double d1, double d2, float f, float f1) {
        this.bindTexture("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float) ((float) d), (float) ((float) d1), (float) ((float) d2));
        GL11.glRotatef((float) (entityHookshot.prevYaw + (entityHookshot.yaw - entityHookshot.prevYaw) * f1 - 90.0f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        GL11.glRotatef((float) (entityHookshot.prevPitch + (entityHookshot.pitch - entityHookshot.prevPitch) * f1), (float) 0.0f, (float) 0.0f, (float) 1.0f);
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
        if (entityHookshot.returnsTo != null) {
            float f12 = (entityHookshot.returnsTo.prevYaw + (entityHookshot.returnsTo.yaw - entityHookshot.returnsTo.prevYaw) * f1) * 3.141593f / 180.0f;
            float f11 = (entityHookshot.returnsTo.prevPitch + (entityHookshot.returnsTo.pitch - entityHookshot.returnsTo.prevPitch) * f1) * 3.141593f / 180.0f;
            double d3 = MathsHelper.sin(f12);
            double d5 = MathsHelper.cos(f12);
            double d7 = MathsHelper.sin(f11);
            double d8 = MathsHelper.cos(f11);
            double xOffset = 0.35;
            double yOffset = 0.5;
            if (!entityHookshot.mainHand) {
                xOffset = -xOffset;
            }
            double d9 = entityHookshot.returnsTo.prevX + (entityHookshot.returnsTo.x - entityHookshot.returnsTo.prevX) * (double) f1 - d5 * xOffset - d3 * yOffset * d8;
            double d10 = entityHookshot.returnsTo.prevY + (entityHookshot.returnsTo.y - entityHookshot.returnsTo.prevY) * (double) f1 - d7 * yOffset;
            double d11 = entityHookshot.returnsTo.prevZ + (entityHookshot.returnsTo.z - entityHookshot.returnsTo.prevZ) * (double) f1 - d3 * xOffset + d5 * yOffset * d8;
            if (this.dispatcher.options.thirdPerson || Minecraft.minecraftInstance.cameraActive) {
                float f13 = (entityHookshot.returnsTo.field_1013 + (entityHookshot.returnsTo.field_1012 - entityHookshot.returnsTo.field_1013) * f1) * 3.141593f / 180.0f;
                double d4 = MathsHelper.sin(f13);
                double d6 = MathsHelper.cos(f13);
                d9 = entityHookshot.returnsTo.prevX + (entityHookshot.returnsTo.x - entityHookshot.returnsTo.prevX) * (double) f1 - d6 * 0.35 - d4 * 0.85;
                d10 = entityHookshot.returnsTo.prevY + (entityHookshot.returnsTo.y - entityHookshot.returnsTo.prevY) * (double) f1 - 0.45;
                d11 = entityHookshot.returnsTo.prevZ + (entityHookshot.returnsTo.z - entityHookshot.returnsTo.prevZ) * (double) f1 - d4 * 0.35 + d6 * 0.85;
            }
            double d12 = entityHookshot.prevX + (entityHookshot.x - entityHookshot.prevX) * (double) f1;
            double d13 = entityHookshot.prevY + (entityHookshot.y - entityHookshot.prevY) * (double) f1;
            double d14 = entityHookshot.prevZ + (entityHookshot.z - entityHookshot.prevZ) * (double) f1;
            double d15 = (float) (d9 - d12);
            double d16 = (float) (d10 - d13);
            double d17 = (float) (d11 - d14);
            GL11.glDisable((int) 3553);
            GL11.glDisable((int) 2896);
            GL11.glLineWidth((float) 3.0f);
            tessellator.start(3);
            tessellator.colour(0);
            int j = 1;
            for (int k = 0; k <= j; ++k) {
                f12 = (float) k / (float) j;
                tessellator.pos(d + d15 * (double) f12, d1 + d16 * (double) f12, d2 + d17 * (double) f12);
            }
            tessellator.draw();
            GL11.glEnable((int) 2896);
            GL11.glEnable((int) 3553);
        }
    }

    @Override
    public void render(MixinEntity entity, double x, double y, double z, float f, float f1) {
        this.doRenderFish((EntityHookshot) entity, x, y, z, f, f1);
    }
}
