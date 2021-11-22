package io.github.ryuu.adventurecraft.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import org.lwjgl.opengl.GL11;

public class RenderBomb extends ItemRenderer {

    public RenderBomb() {
        this.field_2678 = 0.35f;
    }

    @Override
    public void method_1484(ItemEntity entityitem, double d, double d1, double d2, float f, float f1) {
        ItemInstance itemstack = entityitem.item;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) ((float) d), (float) ((float) d1 + 0.1f), (float) ((float) d2));
        GL11.glEnable((int) 32826);
        int i = itemstack.getTexturePosition();
        this.bindTexture("/gui/items.png");
        Tessellator tessellator = Tessellator.INSTANCE;
        float f6 = (float) (i % 16 * 16 + 0) / 256.0f;
        float f8 = (float) (i % 16 * 16 + 16) / 256.0f;
        float f10 = (float) (i / 16 * 16 + 0) / 256.0f;
        float f11 = (float) (i / 16 * 16 + 16) / 256.0f;
        float f12 = 1.0f;
        float f13 = 0.5f;
        float f14 = 0.25f;
        GL11.glRotatef((float) (180.0f - this.dispatcher.field_2497), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        tessellator.start();
        tessellator.method_1697(0.0f, 1.0f, 0.0f);
        tessellator.vertex(0.0f - f13, 0.0f - f14, 0.0, f6, f11);
        tessellator.vertex(f12 - f13, 0.0f - f14, 0.0, f8, f11);
        tessellator.vertex(f12 - f13, 1.0f - f14, 0.0, f8, f10);
        tessellator.vertex(0.0f - f13, 1.0f - f14, 0.0, f6, f10);
        tessellator.draw();
        GL11.glDisable((int) 32826);
        GL11.glPopMatrix();
    }
}
