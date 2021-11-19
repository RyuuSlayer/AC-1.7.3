package io.github.ryuu.adventurecraft.rendering;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import org.lwjgl.opengl.GL11;

public class RenderBomb extends ItemRenderer {
    public void method_1484(ItemEntity entityitem, double d, double d1, double d2, float f, float f1) {
        ItemInstance itemstack = entityitem.item;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1 + 0.1F, (float) d2);
        GL11.glEnable(32826);
        int i = itemstack.getTexturePosition();
        this.bindTexture("/gui/items.png");
        Tessellator tessellator = Tessellator.INSTANCE;
        float f6 = (i % 16 * 16 + 0) / 256.0F;
        float f8 = (i % 16 * 16 + 16) / 256.0F;
        float f10 = (i / 16 * 16 + 0) / 256.0F;
        float f11 = (i / 16 * 16 + 16) / 256.0F;
        float f12 = 1.0F;
        float f13 = 0.5F;
        float f14 = 0.25F;
        GL11.glRotatef(180.0F - this.dispatcher.field_2497, 0.0F, 1.0F, 0.0F);
        tessellator.start();
        tessellator.method_1697(0.0F, 1.0F, 0.0F);
        tessellator.vertex((0.0F - f13), (0.0F - f14), 0.0D, f6, f11);
        tessellator.vertex((f12 - f13), (0.0F - f14), 0.0D, f8, f11);
        tessellator.vertex((f12 - f13), (1.0F - f14), 0.0D, f8, f10);
        tessellator.vertex((0.0F - f13), (1.0F - f14), 0.0D, f6, f10);
        tessellator.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}