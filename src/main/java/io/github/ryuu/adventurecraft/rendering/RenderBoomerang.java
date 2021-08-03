package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityBoomerang;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.Vec2;
import org.lwjgl.opengl.GL11;

public class RenderBoomerang extends bw {
    public void doRenderItem(EntityBoomerang entityBoomerang, double d, double d1, double d2, float yaw, float time) {
        float pitchToUse = entityBoomerang.aV + (entityBoomerang.aT - entityBoomerang.aV) * time;
        float boomerangRotation = entityBoomerang.prevBoomerangRotation + (entityBoomerang.boomerangRotation - entityBoomerang.prevBoomerangRotation) * time;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(-yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(pitchToUse, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(entityBoomerang.boomerangRotation, 0.0F, 1.0F, 0.0F);
        a("/gui/items.png");
        Vec2 texResolution = this.b.e.getTextureResolution("/gui/items.png");
        int width = texResolution.x / 16;
        int height = texResolution.y / 16;
        float halfPixelW = 0.5F / texResolution.x;
        float halfPixelH = 0.5F / texResolution.x;
        nw tessellator = nw.a;
        int iconIndex = Items.boomerang.b((iz) null);
        float f = ((iconIndex % 16 * 16) + 0.0F) / 256.0F;
        float f1 = ((iconIndex % 16 * 16) + 15.99F) / 256.0F;
        float f2 = ((iconIndex / 16 * 16) + 0.0F) / 256.0F;
        float f3 = ((iconIndex / 16 * 16) + 15.99F) / 256.0F;
        float f4 = 1.0F;
        GL11.glEnable(32826);
        float f8 = 0.0625F;
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
        tessellator.b();
        tessellator.b(0.0F, 0.0F, 1.0F);
        tessellator.a(0.0D, 0.0D - f8, 0.0D, f1, f3);
        tessellator.a(f4, 0.0D - f8, 0.0D, f, f3);
        tessellator.a(f4, 0.0D - f8, 1.0D, f, f2);
        tessellator.a(0.0D, 0.0D - f8, 1.0D, f1, f2);
        tessellator.a();
        tessellator.b();
        tessellator.b(0.0F, 0.0F, -1.0F);
        tessellator.a(0.0D, 0.0D, 1.0D, f1, f2);
        tessellator.a(f4, 0.0D, 1.0D, f, f2);
        tessellator.a(f4, 0.0D, 0.0D, f, f3);
        tessellator.a(0.0D, 0.0D, 0.0D, f1, f3);
        tessellator.a();
        tessellator.b();
        tessellator.b(-1.0F, 0.0F, 0.0F);
        for (int i = 0; i < width; i++) {
            float f9 = i / width;
            float f13 = f1 + (f - f1) * f9 - halfPixelW;
            float f17 = f4 * f9;
            tessellator.a(f17, (0.0F - f8), 1.0D, f13, f2);
            tessellator.a(f17, 0.0D, 1.0D, f13, f2);
            tessellator.a(f17, 0.0D, 0.0D, f13, f3);
            tessellator.a(f17, (0.0F - f8), 0.0D, f13, f3);
        }
        tessellator.a();
        tessellator.b();
        tessellator.b(1.0F, 0.0F, 0.0F);
        for (int j = 0; j < width; j++) {
            float f10 = j / width;
            float f14 = f1 + (f - f1) * f10 - halfPixelW;
            float f18 = f4 * f10 + 1.0F / width;
            tessellator.a(f18, (0.0F - f8), 0.0D, f14, f3);
            tessellator.a(f18, 0.0D, 0.0D, f14, f3);
            tessellator.a(f18, 0.0D, 1.0D, f14, f2);
            tessellator.a(f18, (0.0F - f8), 1.0D, f14, f2);
        }
        tessellator.a();
        tessellator.b();
        tessellator.b(0.0F, 1.0F, 0.0F);
        for (int k = 0; k < height; k++) {
            float f11 = k / height;
            float f15 = f3 + (f2 - f3) * f11 - halfPixelH;
            float f19 = f4 * f11 + 1.0F / height;
            tessellator.a(0.0D, (0.0F - f8), f19, f1, f15);
            tessellator.a(f4, (0.0F - f8), f19, f, f15);
            tessellator.a(f4, 0.0D, f19, f, f15);
            tessellator.a(0.0D, 0.0D, f19, f1, f15);
        }
        tessellator.a();
        tessellator.b();
        tessellator.b(0.0F, -1.0F, 0.0F);
        for (int l = 0; l < height; l++) {
            float f12 = l / height;
            float f16 = f3 + (f2 - f3) * f12 - halfPixelH;
            float f20 = f4 * f12;
            tessellator.a(f4, (0.0F - f8), f20, f, f16);
            tessellator.a(0.0D, (0.0F - f8), f20, f1, f16);
            tessellator.a(0.0D, 0.0D, f20, f1, f16);
            tessellator.a(f4, 0.0D, f20, f, f16);
        }
        tessellator.a();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    public void a(sn entity, double d, double d1, double d2, float f, float f1) {
        doRenderItem((EntityBoomerang) entity, d, d1, d2, f, f1);
    }
}