package io.github.ryuu.adventurecraft.rendering;

import org.lwjgl.opengl.GL11;

public class RenderBomb extends bb {
    public void a(hl entityitem, double d, double d1, double d2, float f, float f1) {
        iz itemstack = entityitem.a;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1 + 0.1F, (float)d2);
        GL11.glEnable(32826);
        int i = itemstack.b();
        a("/gui/items.png");
        nw tessellator = nw.a;
        float f6 = (i % 16 * 16 + 0) / 256.0F;
        float f8 = (i % 16 * 16 + 16) / 256.0F;
        float f10 = (i / 16 * 16 + 0) / 256.0F;
        float f11 = (i / 16 * 16 + 16) / 256.0F;
        float f12 = 1.0F;
        float f13 = 0.5F;
        float f14 = 0.25F;
        GL11.glRotatef(180.0F - this.b.i, 0.0F, 1.0F, 0.0F);
        tessellator.b();
        tessellator.b(0.0F, 1.0F, 0.0F);
        tessellator.a((0.0F - f13), (0.0F - f14), 0.0D, f6, f11);
        tessellator.a((f12 - f13), (0.0F - f14), 0.0D, f8, f11);
        tessellator.a((f12 - f13), (1.0F - f14), 0.0D, f8, f10);
        tessellator.a((0.0F - f13), (1.0F - f14), 0.0D, f6, f10);
        tessellator.a();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
