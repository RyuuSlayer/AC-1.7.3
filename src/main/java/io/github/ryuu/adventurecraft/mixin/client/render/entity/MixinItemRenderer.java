package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer extends EntityRenderer {

    @Shadow()
    private TileRenderer field_1708 = new TileRenderer();

    private Random rand = new Random();

    public boolean field_1707 = true;

    public float scale = 1.0f;

    public MixinItemRenderer() {
        this.field_2678 = 0.15f;
        this.field_2679 = 0.75f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1484(ItemEntity entityitem, double d, double d1, double d2, float f, float f1) {
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
        GL11.glTranslatef((float) ((float) d), (float) ((float) d1 + f2), (float) ((float) d2));
        GL11.glEnable((int) 32826);
        if (itemstack.itemId < 256 && TileRenderer.method_42(Tile.BY_ID[itemstack.itemId].method_1621())) {
            GL11.glRotatef((float) f3, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            int textureNum = Tile.BY_ID[itemstack.itemId].getTextureNum();
            if (textureNum == 0) {
                this.bindTexture("/terrain.png");
            } else {
                this.bindTexture(String.format((String) "/terrain%d.png", (Object[]) new Object[] { textureNum }));
            }
            float f4 = 0.25f;
            if (!Tile.BY_ID[itemstack.itemId].isFullCube() && itemstack.itemId != Tile.STONE_SLAB.id && Tile.BY_ID[itemstack.itemId].method_1621() != 16) {
                f4 = 0.5f;
            }
            GL11.glScalef((float) (this.scale * f4), (float) (this.scale * f4), (float) (this.scale * f4));
            for (int j = 0; j < byte0; ++j) {
                GL11.glPushMatrix();
                if (j > 0) {
                    float f5 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f / f4;
                    float f7 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f / f4;
                    float f9 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.2f / f4;
                    GL11.glTranslatef((float) f5, (float) f7, (float) f9);
                }
                this.field_1708.method_48(Tile.BY_ID[itemstack.itemId], itemstack.getDamage(), entityitem.getBrightnessAtEyes(f1));
                GL11.glPopMatrix();
            }
        } else {
            GL11.glScalef((float) (this.scale * 0.5f), (float) (this.scale * 0.5f), (float) (this.scale * 0.5f));
            int i = itemstack.getTexturePosition();
            if (itemstack.itemId < 256) {
                int textureNum = Tile.BY_ID[itemstack.itemId].getTextureNum();
                if (textureNum == 0) {
                    this.bindTexture("/terrain.png");
                } else {
                    this.bindTexture(String.format((String) "/terrain%d.png", (Object[]) new Object[] { textureNum }));
                }
            } else {
                this.bindTexture("/gui/items.png");
            }
            Tessellator tessellator = Tessellator.INSTANCE;
            float f6 = (float) (i % 16 * 16 + 0) / 256.0f;
            float f8 = (float) (i % 16 * 16 + 16) / 256.0f;
            float f10 = (float) (i / 16 * 16 + 0) / 256.0f;
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
                GL11.glColor4f((float) (f15 * f21), (float) (f17 * f21), (float) (f19 * f21), (float) 1.0f);
            }
            for (int l = 0; l < byte0; ++l) {
                GL11.glPushMatrix();
                if (l > 0) {
                    float f16 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f18 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f20 = (this.rand.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef((float) f16, (float) f18, (float) f20);
                }
                GL11.glRotatef((float) (180.0f - this.dispatcher.field_2497), (float) 0.0f, (float) 1.0f, (float) 0.0f);
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
        GL11.glDisable((int) 32826);
        GL11.glPopMatrix();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderItemInstance(TextRenderer textRenderer, TextureManager textureManager, int itemId, int damage, int texturePosition, int x, int y) {
        if (itemId < 256 && TileRenderer.method_42(Tile.BY_ID[itemId].method_1621())) {
            int textureNum = Tile.BY_ID[itemId].getTextureNum();
            if (textureNum == 0) {
                textureManager.bindTexture(textureManager.getTextureId("/terrain.png"));
            } else {
                textureManager.bindTexture(textureManager.getTextureId(String.format((String) "/terrain%d.png", (Object[]) new Object[] { textureNum })));
            }
            Tile block = Tile.BY_ID[itemId];
            GL11.glPushMatrix();
            GL11.glTranslatef((float) (x - 2), (float) (y + 3), (float) -3.0f);
            GL11.glScalef((float) 10.0f, (float) 10.0f, (float) 10.0f);
            GL11.glTranslatef((float) 1.0f, (float) 0.5f, (float) 1.0f);
            GL11.glScalef((float) 1.0f, (float) 1.0f, (float) -1.0f);
            GL11.glRotatef((float) 210.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
            GL11.glRotatef((float) 45.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            int l1 = ItemType.byId[itemId].getNameColour(damage);
            float f2 = (float) (l1 >> 16 & 0xFF) / 255.0f;
            float f4 = (float) (l1 >> 8 & 0xFF) / 255.0f;
            float f5 = (float) (l1 & 0xFF) / 255.0f;
            if (this.field_1707) {
                GL11.glColor4f((float) f2, (float) f4, (float) f5, (float) 1.0f);
            }
            GL11.glRotatef((float) -90.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
            this.field_1708.field_81 = this.field_1707;
            this.field_1708.method_48(block, damage, 1.0f);
            this.field_1708.field_81 = true;
            GL11.glPopMatrix();
        } else if (texturePosition >= 0) {
            GL11.glDisable((int) 2896);
            if (itemId < 256) {
                int textureNum = Tile.BY_ID[itemId].getTextureNum();
                if (textureNum == 0) {
                    textureManager.bindTexture(textureManager.getTextureId("/terrain.png"));
                } else {
                    textureManager.bindTexture(textureManager.getTextureId(String.format((String) "/terrain%d.png", (Object[]) new Object[] { textureNum })));
                }
            } else {
                textureManager.bindTexture(textureManager.getTextureId("/gui/items.png"));
            }
            int k1 = ItemType.byId[itemId].getNameColour(damage);
            float f = (float) (k1 >> 16 & 0xFF) / 255.0f;
            float f1 = (float) (k1 >> 8 & 0xFF) / 255.0f;
            float f3 = (float) (k1 & 0xFF) / 255.0f;
            if (this.field_1707) {
                GL11.glColor4f((float) f, (float) f1, (float) f3, (float) 1.0f);
            }
            this.method_1483(x, y, texturePosition % 16 * 16, texturePosition / 16 * 16, 16, 16);
            GL11.glEnable((int) 2896);
        }
        GL11.glEnable((int) 2884);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderItemInstance(TextRenderer textRenderer, TextureManager textureManager, ItemInstance itemInstance, int x, int y) {
        if (itemInstance == null || ItemType.byId[itemInstance.itemId] == null) {
            return;
        }
        this.renderItemInstance(textRenderer, textureManager, itemInstance.itemId, itemInstance.getDamage(), itemInstance.getTexturePosition(), x, y);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1488(TextRenderer fontrenderer, TextureManager renderengine, ItemInstance item, int x, int y) {
        String s;
        if (item == null || ItemType.byId[item.itemId] == null) {
            return;
        }
        if (item.count > 1) {
            s = "" + item.count;
            GL11.glDisable((int) 2896);
            GL11.glDisable((int) 2929);
            fontrenderer.drawTextWithShadow(s, x + 19 - 2 - fontrenderer.getTextWidth(s), y + 6 + 3, 0xFFFFFF);
            GL11.glEnable((int) 2896);
            GL11.glEnable((int) 2929);
        } else if (item.count < 0) {
            s = "\u00ec";
            GL11.glDisable((int) 2896);
            GL11.glDisable((int) 2929);
            fontrenderer.drawTextWithShadow(s, x + 19 - 2 - fontrenderer.getTextWidth(s), y + 6 + 3, 0xFFFFFF);
            GL11.glEnable((int) 2896);
            GL11.glEnable((int) 2929);
        }
        if (item.method_720() || ItemType.byId[item.itemId] instanceof IItemReload) {
            int k = (int) Math.round((double) (13.0 - (double) item.method_721() * 13.0 / (double) item.method_723()));
            int l = (int) Math.round((double) (255.0 - (double) item.method_721() * 255.0 / (double) item.method_723()));
            GL11.glDisable((int) 2896);
            GL11.glDisable((int) 2929);
            GL11.glDisable((int) 3553);
            Tessellator tessellator = Tessellator.INSTANCE;
            int i1 = 255 - l << 16 | l << 8;
            int j1 = (255 - l) / 4 << 16 | 0x3F00;
            this.fillTessellator(tessellator, x + 2, y + 13, 13, 2, 0);
            this.fillTessellator(tessellator, x + 2, y + 13, 12, 1, j1);
            this.fillTessellator(tessellator, x + 2, y + 13, k, 1, i1);
            GL11.glEnable((int) 3553);
            GL11.glEnable((int) 2896);
            GL11.glEnable((int) 2929);
            GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void fillTessellator(Tessellator tessellator, int x, int y, int width, int height, int colour) {
        tessellator.start();
        tessellator.colour(colour);
        tessellator.pos(x + 0, y + 0, 0.0);
        tessellator.pos(x + 0, y + height, 0.0);
        tessellator.pos(x + width, y + height, 0.0);
        tessellator.pos(x + width, y + 0, 0.0);
        tessellator.draw();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1483(int x, int y, int k, int l, int i1, int j1) {
        float f = 0.0f;
        float f1 = 0.00390625f;
        float f2 = 0.00390625f;
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        tessellator.vertex(x + 0, y + j1, f, (float) (k + 0) * f1, (float) (l + j1) * f2);
        tessellator.vertex(x + i1, y + j1, f, (float) (k + i1) * f1, (float) (l + j1) * f2);
        tessellator.vertex(x + i1, y + 0, f, (float) (k + i1) * f1, (float) (l + 0) * f2);
        tessellator.vertex(x + 0, y + 0, f, (float) (k + 0) * f1, (float) (l + 0) * f2);
        tessellator.draw();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(Entity entity, double x, double y, double z, float f, float f1) {
        this.method_1484((ItemEntity) entity, x, y, z, f, f1);
    }
}
