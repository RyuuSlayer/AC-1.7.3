package io.github.ryuu.adventurecraft.rendering;

import io.github.ryuu.adventurecraft.entities.EntityBoomerang;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderBoomerang extends EntityRenderer {

    public RenderBoomerang() {
        this.field_2678 = 0.15f;
        this.field_2679 = 0.75f;
    }

    public void doRenderItem(EntityBoomerang entityBoomerang, double d, double d1, double d2, float yaw, float time) {
        float pitchToUse = entityBoomerang.prevPitch + (entityBoomerang.pitch - entityBoomerang.prevPitch) * time;
        float boomerangRotation = entityBoomerang.prevBoomerangRotation + (entityBoomerang.boomerangRotation - entityBoomerang.prevBoomerangRotation) * time;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(-yaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(pitchToUse, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(entityBoomerang.boomerangRotation, 0.0f, 1.0f, 0.0f);
        this.bindTexture("/gui/items.png");
        Vec2 texResolution = this.dispatcher.textureManager.getTextureResolution("/gui/items.png");
        int width = texResolution.x / 16;
        int height = texResolution.y / 16;
        float halfPixelW = 0.5f / (float) texResolution.x;
        float halfPixelH = 0.5f / (float) texResolution.x;
        Tessellator tessellator = Tessellator.INSTANCE;
        int iconIndex = Items.boomerang.getTexturePosition(null);
        float f = ((float) (iconIndex % 16 * 16) + 0.0f) / 256.0f;
        float f1 = ((float) (iconIndex % 16 * 16) + 15.99f) / 256.0f;
        float f2 = ((float) (iconIndex / 16 * 16) + 0.0f) / 256.0f;
        float f3 = ((float) (iconIndex / 16 * 16) + 15.99f) / 256.0f;
        float f4 = 1.0f;
        GL11.glEnable(32826);
        float f8 = 0.0625f;
        GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
        tessellator.start();
        tessellator.method_1697(0.0f, 0.0f, 1.0f);
        tessellator.vertex(0.0, 0.0 - (double) f8, 0.0, f1, f3);
        tessellator.vertex(f4, 0.0 - (double) f8, 0.0, f, f3);
        tessellator.vertex(f4, 0.0 - (double) f8, 1.0, f, f2);
        tessellator.vertex(0.0, 0.0 - (double) f8, 1.0, f1, f2);
        tessellator.draw();
        tessellator.start();
        tessellator.method_1697(0.0f, 0.0f, -1.0f);
        tessellator.vertex(0.0, 0.0, 1.0, f1, f2);
        tessellator.vertex(f4, 0.0, 1.0, f, f2);
        tessellator.vertex(f4, 0.0, 0.0, f, f3);
        tessellator.vertex(0.0, 0.0, 0.0, f1, f3);
        tessellator.draw();
        tessellator.start();
        tessellator.method_1697(-1.0f, 0.0f, 0.0f);
        for (int i = 0; i < width; ++i) {
            float f9 = (float) i / (float) width;
            float f13 = f1 + (f - f1) * f9 - halfPixelW;
            float f17 = f4 * f9;
            tessellator.vertex(f17, 0.0f - f8, 1.0, f13, f2);
            tessellator.vertex(f17, 0.0, 1.0, f13, f2);
            tessellator.vertex(f17, 0.0, 0.0, f13, f3);
            tessellator.vertex(f17, 0.0f - f8, 0.0, f13, f3);
        }
        tessellator.draw();
        tessellator.start();
        tessellator.method_1697(1.0f, 0.0f, 0.0f);
        for (int j = 0; j < width; ++j) {
            float f10 = (float) j / (float) width;
            float f14 = f1 + (f - f1) * f10 - halfPixelW;
            float f18 = f4 * f10 + 1.0f / (float) width;
            tessellator.vertex(f18, 0.0f - f8, 0.0, f14, f3);
            tessellator.vertex(f18, 0.0, 0.0, f14, f3);
            tessellator.vertex(f18, 0.0, 1.0, f14, f2);
            tessellator.vertex(f18, 0.0f - f8, 1.0, f14, f2);
        }
        tessellator.draw();
        tessellator.start();
        tessellator.method_1697(0.0f, 1.0f, 0.0f);
        for (int k = 0; k < height; ++k) {
            float f11 = (float) k / (float) height;
            float f15 = f3 + (f2 - f3) * f11 - halfPixelH;
            float f19 = f4 * f11 + 1.0f / (float) height;
            tessellator.vertex(0.0, 0.0f - f8, f19, f1, f15);
            tessellator.vertex(f4, 0.0f - f8, f19, f, f15);
            tessellator.vertex(f4, 0.0, f19, f, f15);
            tessellator.vertex(0.0, 0.0, f19, f1, f15);
        }
        tessellator.draw();
        tessellator.start();
        tessellator.method_1697(0.0f, -1.0f, 0.0f);
        for (int l = 0; l < height; ++l) {
            float f12 = (float) l / (float) height;
            float f16 = f3 + (f2 - f3) * f12 - halfPixelH;
            float f20 = f4 * f12;
            tessellator.vertex(f4, 0.0f - f8, f20, f, f16);
            tessellator.vertex(0.0, 0.0f - f8, f20, f1, f16);
            tessellator.vertex(0.0, 0.0, f20, f1, f16);
            tessellator.vertex(f4, 0.0, f20, f, f16);
        }
        tessellator.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    @Override
    public void render(Entity entity, double x, double y, double z, float f, float f1) {
        this.doRenderItem((EntityBoomerang) entity, x, y, z, f, f1);
    }
}
