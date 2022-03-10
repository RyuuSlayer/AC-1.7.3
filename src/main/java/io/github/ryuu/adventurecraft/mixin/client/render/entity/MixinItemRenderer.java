package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import io.github.ryuu.adventurecraft.extensions.client.render.entity.ExItemRenderer;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.items.ReloadableItemType;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(value = ItemRenderer.class, priority = 999)
public abstract class MixinItemRenderer extends EntityRenderer implements ExItemRenderer {

    @Shadow
    private TileRenderer field_1708;

    @Shadow
    private Random rand;

    @Shadow
    public boolean field_1707;

    public float scale;

    @Shadow
    public abstract void method_1483(int i, int j, int k, int i1, int i2, int i3);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void render(ItemEntity entityitem, double d, double d1, double d2, float f, float f1) {
        this.rand.setSeed(187L);
        ItemInstance itemstack = entityitem.item;
        GL11.glPushMatrix();
        float f2 = MathsHelper.sin(((float) entityitem.age + f1) / 10.0f + entityitem.field_567) * 0.1f + 0.1f;
        float f3 = (((float) entityitem.age + f1) / 20.0f + entityitem.field_567) * 57.29578f;
        int byte0 = 1;
        if (entityitem.item.count > 1) {
            byte0 = 2;
        }
        if (entityitem.item.count > 5) {
            byte0 = 3;
        }
        if (entityitem.item.count > 20) {
            byte0 = 4;
        }
        GL11.glTranslatef((float) d, (float) d1 + f2, (float) d2);
        GL11.glEnable(32826);
        if (itemstack.itemId < 256 && TileRenderer.method_42(Tile.BY_ID[itemstack.itemId].method_1621())) {
            GL11.glRotatef(f3, 0.0f, 1.0f, 0.0f);
            int textureNum = ((ExTile) Tile.BY_ID[itemstack.itemId]).getTextureNum();
            if (textureNum == 0) {
                this.bindTexture("/terrain.png");
            } else {
                this.bindTexture(String.format("/terrain%d.png", textureNum));
            }
            float f4 = 0.25f;
            if (!Tile.BY_ID[itemstack.itemId].isFullCube() && itemstack.itemId != Tile.STONE_SLAB.id && Tile.BY_ID[itemstack.itemId].method_1621() != 16) {
                f4 = 0.5f;
            }
            GL11.glScalef(this.scale * f4, this.scale * f4, this.scale * f4);
            for (int j = 0; j < byte0; ++j) {
                GL11.glPushMatrix();
                if (j > 0) {
                    float f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f / f4;
                    float f7 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f / f4;
                    float f9 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f / f4;
                    GL11.glTranslatef(f5, f7, f9);
                }
                this.field_1708.method_48(Tile.BY_ID[itemstack.itemId], itemstack.getDamage(), entityitem.getBrightnessAtEyes(f1));
                GL11.glPopMatrix();
            }
        } else {
            GL11.glScalef(this.scale * 0.5f, this.scale * 0.5f, this.scale * 0.5f);
            int i = itemstack.getTexturePosition();
            if (itemstack.itemId < 256) {
                int textureNum = ((ExTile) Tile.BY_ID[itemstack.itemId]).getTextureNum();
                if (textureNum == 0) {
                    this.bindTexture("/terrain.png");
                } else {
                    this.bindTexture(String.format("/terrain%d.png", textureNum));
                }
            } else {
                this.bindTexture("/gui/items.png");
            }
            Tessellator tessellator = Tessellator.INSTANCE;
            float f6 = (float) (i % 16 * 16) / 256.0f;
            float f8 = (float) (i % 16 * 16 + 16) / 256.0f;
            float f10 = (float) (i / 16 * 16) / 256.0f;
            float f11 = (float) (i / 16 * 16 + 16) / 256.0f;
            float f12 = 1.0f;
            float f13 = 0.5f;
            float f14 = 0.25f;
            if (this.field_1707) {
                int k = ItemType.byId[itemstack.itemId].getNameColour(itemstack.getDamage());
                float f15 = (float) (k >> 16 & 0xFF) / 255.0f;
                float f17 = (float) (k >> 8 & 0xFF) / 255.0f;
                float f19 = (float) (k & 0xFF) / 255.0f;
                float f21 = entityitem.getBrightnessAtEyes(f1);
                GL11.glColor4f(f15 * f21, f17 * f21, f19 * f21, 1.0f);
            }

            for (int l = 0; l < byte0; ++l) {
                GL11.glPushMatrix();
                if (l > 0) {
                    float f16 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f18 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f20 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef(f16, f18, f20);
                }

                GL11.glRotatef(180.0f - this.dispatcher.renderYaw, 0.0f, 1.0f, 0.0f);
                tessellator.start();
                tessellator.method_1697(0.0f, 1.0f, 0.0f);
                tessellator.vertex(0.0f - f13, 0.0f - f14, 0.0, f6, f11);
                tessellator.vertex(f12 - f13, 0.0f - f14, 0.0, f8, f11);
                tessellator.vertex(f12 - f13, 1.0f - f14, 0.0, f8, f10);
                tessellator.vertex(0.0f - f13, 1.0f - f14, 0.0, f6, f10);
                tessellator.draw();
                GL11.glPopMatrix();
            }
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void renderItemInstance(TextRenderer textRenderer, TextureManager textureManager, int itemId, int damage, int texturePosition, int x, int y) {
        if (itemId < 256 && TileRenderer.method_42(Tile.BY_ID[itemId].method_1621())) {
            int textureNum = ((ExTile) Tile.BY_ID[itemId]).getTextureNum();
            if (textureNum == 0) {
                textureManager.bindTexture(textureManager.getTextureId("/terrain.png"));
            } else {
                textureManager.bindTexture(textureManager.getTextureId(String.format("/terrain%d.png", textureNum)));
            }
            Tile block = Tile.BY_ID[itemId];
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (x - 2), (float) (y + 3), -3.0f);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 1.0f);
            GL11.glScalef(1.0f, 1.0f, -1.0f);
            GL11.glRotatef(210.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            int l1 = ItemType.byId[itemId].getNameColour(damage);
            float f2 = (float) (l1 >> 16 & 0xFF) / 255.0f;
            float f4 = (float) (l1 >> 8 & 0xFF) / 255.0f;
            float f5 = (float) (l1 & 0xFF) / 255.0f;
            if (this.field_1707) {
                GL11.glColor4f(f2, f4, f5, 1.0f);
            }
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            this.field_1708.field_81 = this.field_1707;
            this.field_1708.method_48(block, damage, 1.0f);
            this.field_1708.field_81 = true;
            GL11.glPopMatrix();
        } else if (texturePosition >= 0) {
            GL11.glDisable(2896);
            if (itemId < 256) {
                int textureNum = ((ExTile) Tile.BY_ID[itemId]).getTextureNum();
                if (textureNum == 0) {
                    textureManager.bindTexture(textureManager.getTextureId("/terrain.png"));
                } else {
                    textureManager.bindTexture(textureManager.getTextureId(String.format("/terrain%d.png", textureNum)));
                }
            } else {
                textureManager.bindTexture(textureManager.getTextureId("/gui/items.png"));
            }
            int k1 = ItemType.byId[itemId].getNameColour(damage);
            float f = (float) (k1 >> 16 & 0xFF) / 255.0f;
            float f1 = (float) (k1 >> 8 & 0xFF) / 255.0f;
            float f3 = (float) (k1 & 0xFF) / 255.0f;
            if (this.field_1707) {
                GL11.glColor4f(f, f1, f3, 1.0f);
            }
            this.method_1483(x, y, texturePosition % 16 * 16, texturePosition / 16 * 16, 16, 16);
            GL11.glEnable(2896);
        }
        GL11.glEnable(2884);
    }

    @Redirect(method = "method_1488", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemInstance;method_720()Z"))
    private boolean drawExtraIfItemReload(ItemInstance instance) {
        return instance.method_720() || ItemType.byId[instance.itemId] instanceof ReloadableItemType;
    }

    @Override
    public void setScale(float scale) {
        this.scale = scale;
    }
}
