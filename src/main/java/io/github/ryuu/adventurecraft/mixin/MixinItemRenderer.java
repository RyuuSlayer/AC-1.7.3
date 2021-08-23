package io.github.ryuu.adventurecraft.mixin;

import java.util.Random;

import io.github.ryuu.adventurecraft.items.IItemReload;
import io.github.ryuu.adventurecraft.overrides.*;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer extends EntityRenderer {
    private final TileRenderer e = new TileRenderer();

    private final Random f = new Random();

    public boolean a = true;

    public void a(ItemEntity entityitem, double d, double d1, double d2, float f, float f1) {
        this.f.setSeed(187L);
        ItemInstance itemstack = entityitem.a;
        GL11.glPushMatrix();
        float f2 = in.a((entityitem.b + f1) / 10.0F + entityitem.d) * 0.1F + 0.1F;
        float f3 = ((entityitem.b + f1) / 20.0F + entityitem.d) * 57.29578F;
        byte byte0 = 1;
        if (entityitem.a.a > 1)
            byte0 = 2;
        if (entityitem.a.a > 5)
            byte0 = 3;
        if (entityitem.a.a > 20)
            byte0 = 4;
        GL11.glTranslatef((float) d, (float) d1 + f2, (float) d2);
        GL11.glEnable(32826);
        if (itemstack.c < 256 && TileRenderer.a(Tile.m[itemstack.c].b())) {
            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
            int textureNum = Tile.m[itemstack.c].getTextureNum();
            if (textureNum == 0) {
                a("/terrain.png");
            } else {
                a(String.format("/terrain%d.png", new Object[]{Integer.valueOf(textureNum)}));
            }
            float f4 = 0.25F;
            if (!Tile.m[itemstack.c].d() && itemstack.c != Tile.al.bn && Tile.m[itemstack.c].b() != 16)
                f4 = 0.5F;
            GL11.glScalef(this.scale * f4, this.scale * f4, this.scale * f4);
            for (int j = 0; j < byte0; j++) {
                GL11.glPushMatrix();
                if (j > 0) {
                    float f5 = (this.f.nextFloat() * 2.0F - 1.0F) * 0.2F / f4;
                    float f7 = (this.f.nextFloat() * 2.0F - 1.0F) * 0.2F / f4;
                    float f9 = (this.f.nextFloat() * 2.0F - 1.0F) * 0.2F / f4;
                    GL11.glTranslatef(f5, f7, f9);
                }
                this.e.a(Tile.m[itemstack.c], itemstack.i(), entityitem.a(f1));
                GL11.glPopMatrix();
            }
        } else {
            GL11.glScalef(this.scale * 0.5F, this.scale * 0.5F, this.scale * 0.5F);
            int i = itemstack.b();
            if (itemstack.c < 256) {
                int textureNum = Tile.m[itemstack.c].getTextureNum();
                if (textureNum == 0) {
                    a("/terrain.png");
                } else {
                    a(String.format("/terrain%d.png", new Object[]{Integer.valueOf(textureNum)}));
                }
            } else {
                a("/gui/items.png");
            }
            Tessellator tessellator = Tessellator.a;
            float f6 = (i % 16 * 16 + 0) / 256.0F;
            float f8 = (i % 16 * 16 + 16) / 256.0F;
            float f10 = (i / 16 * 16 + 0) / 256.0F;
            float f11 = (i / 16 * 16 + 16) / 256.0F;
            float f12 = 1.0F;
            float f13 = 0.5F;
            float f14 = 0.25F;
            if (this.a) {
                int k = ItemType.c[itemstack.c].f(itemstack.i());
                float f15 = (k >> 16 & 0xFF) / 255.0F;
                float f17 = (k >> 8 & 0xFF) / 255.0F;
                float f19 = (k & 0xFF) / 255.0F;
                float f21 = entityitem.a(f1);
                GL11.glColor4f(f15 * f21, f17 * f21, f19 * f21, 1.0F);
            }
            for (int l = 0; l < byte0; l++) {
                GL11.glPushMatrix();
                if (l > 0) {
                    float f16 = (this.f.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f18 = (this.f.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f20 = (this.f.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(f16, f18, f20);
                }
                GL11.glRotatef(180.0F - this.b.i, 0.0F, 1.0F, 0.0F);
                tessellator.b();
                tessellator.b(0.0F, 1.0F, 0.0F);
                tessellator.a((0.0F - f13), (0.0F - f14), 0.0D, f6, f11);
                tessellator.a((f12 - f13), (0.0F - f14), 0.0D, f8, f11);
                tessellator.a((f12 - f13), (1.0F - f14), 0.0D, f8, f10);
                tessellator.a((0.0F - f13), (1.0F - f14), 0.0D, f6, f10);
                tessellator.a();
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    public void a(TextRenderer fontrenderer, TextureManager renderengine, int i, int j, int k, int l, int i1) {
        if (i < 256 && TileRenderer.a(Tile.m[i].b())) {
            int textureNum = Tile.m[i].getTextureNum();
            if (textureNum == 0) {
                renderengine.b(renderengine.b("/terrain.png"));
            } else {
                renderengine.b(renderengine.b(String.format("/terrain%d.png", Integer.valueOf(textureNum))));
            }
            Tile block = Tile.m[i];
            GL11.glPushMatrix();
            GL11.glTranslatef((l - 2), (i1 + 3), -3.0F);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1.0F);
            GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            int l1 = ItemType.c[i].f(j);
            float f2 = (l1 >> 16 & 0xFF) / 255.0F;
            float f4 = (l1 >> 8 & 0xFF) / 255.0F;
            float f5 = (l1 & 0xFF) / 255.0F;
            if (this.a)
                GL11.glColor4f(f2, f4, f5, 1.0F);
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            this.e.b = this.a;
            this.e.a(block, j, 1.0F);
            this.e.b = true;
            GL11.glPopMatrix();
        } else if (k >= 0) {
            GL11.glDisable(2896);
            if (i < 256) {
                int textureNum = Tile.m[i].getTextureNum();
                if (textureNum == 0) {
                    renderengine.b(renderengine.b("/terrain.png"));
                } else {
                    renderengine.b(renderengine.b(String.format("/terrain%d.png", Integer.valueOf(textureNum))));
                }
            } else {
                renderengine.b(renderengine.b("/gui/items.png"));
            }
            int k1 = ItemType.c[i].f(j);
            float f = (k1 >> 16 & 0xFF) / 255.0F;
            float f1 = (k1 >> 8 & 0xFF) / 255.0F;
            float f3 = (k1 & 0xFF) / 255.0F;
            if (this.a)
                GL11.glColor4f(f, f1, f3, 1.0F);
            a(l, i1, k % 16 * 16, k / 16 * 16, 16, 16);
            GL11.glEnable(2896);
        }
        GL11.glEnable(2884);
    }

    public void a(TextRenderer fontrenderer, TextureManager renderengine, ItemInstance itemstack, int i, int j) {
        if (itemstack == null || ItemType.c[itemstack.c] == null)
            return;
        a(fontrenderer, renderengine, itemstack.c, itemstack.i(), itemstack.b(), i, j);
    }

    public void b(TextRenderer fontrenderer, TextureManager renderengine, ItemInstance itemstack, int i, int j) {
        if (itemstack == null || ItemType.c[itemstack.c] == null)
            return;
        if (itemstack.a > 1) {
            String s = "" + itemstack.a;
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            fontrenderer.a(s, i + 19 - 2 - fontrenderer.a(s), j + 6 + 3, 16777215);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
        } else if (itemstack.a < 0) {
            String s = ";
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            fontrenderer.a(s, i + 19 - 2 - fontrenderer.a(s), j + 6 + 3, 16777215);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
        }
        if (itemstack.g() || ItemType.c[itemstack.c] instanceof IItemReload) {
            int k = (int) Math.round(13.0D - itemstack.h() * 13.0D / itemstack.j());
            int l = (int) Math.round(255.0D - itemstack.h() * 255.0D / itemstack.j());
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            Tessellator tessellator = Tessellator.a;
            int i1 = 255 - l << 16 | l << 8;
            int j1 = (255 - l) / 4 << 16 | 0x3F00;
            a(tessellator, i + 2, j + 13, 13, 2, 0);
            a(tessellator, i + 2, j + 13, 12, 1, j1);
            a(tessellator, i + 2, j + 13, k, 1, i1);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void a(Tessellator tessellator, int i, int j, int k, int l, int i1) {
        tessellator.b();
        tessellator.b(i1);
        tessellator.a((i + 0), (j + 0), 0.0D);
        tessellator.a((i + 0), (j + l), 0.0D);
        tessellator.a((i + k), (j + l), 0.0D);
        tessellator.a((i + k), (j + 0), 0.0D);
        tessellator.a();
    }

    public void a(int i, int j, int k, int l, int i1, int j1) {
        float f = 0.0F;
        float f1 = 0.00390625F;
        float f2 = 0.00390625F;
        Tessellator tessellator = Tessellator.a;
        tessellator.b();
        tessellator.a((i + 0), (j + j1), f, ((k + 0) * f1), ((l + j1) * f2));
        tessellator.a((i + i1), (j + j1), f, ((k + i1) * f1), ((l + j1) * f2));
        tessellator.a((i + i1), (j + 0), f, ((k + i1) * f1), ((l + 0) * f2));
        tessellator.a((i + 0), (j + 0), f, ((k + 0) * f1), ((l + 0) * f2));
        tessellator.a();
    }

    public void a(Entity entity, double d, double d1, double d2, float f, float f1) {
        a((ItemEntity) entity, d, d1, d2, f, f1);
    }

    public float scale = 1.0F;
}
