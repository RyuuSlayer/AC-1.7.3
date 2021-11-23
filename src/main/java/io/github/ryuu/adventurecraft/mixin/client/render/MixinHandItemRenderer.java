package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.storage.MapStorage;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HandItemRenderer.class)
public class MixinHandItemRenderer {

    @Shadow()
    private final Minecraft minecraft;
    private final TileRenderer tileRenderer = new TileRenderer();
    private final MapRenderer mapRenderer;
    private final BipedModel refBiped;
    public ModelPart powerGlove;
    public ModelPart powerGloveRuby;
    private ItemInstance item = null;
    private float field_2403 = 0.0f;
    private float field_2404 = 0.0f;
    private int field_2407 = -1;
    private boolean itemRotate;

    public MixinHandItemRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.mapRenderer = new MapRenderer(minecraft.textRenderer, minecraft.options, minecraft.textureManager);
        this.itemRotate = true;
        this.powerGlove = new ModelPart(0, 0);
        this.powerGlove.addCuboid(-3.5f, 4.5f, -2.5f, 5, 7, 5, 0.0f);
        this.powerGlove.setPivot(-5.0f, 2.0f, 0.0f);
        this.powerGloveRuby = new ModelPart(0, 0);
        this.powerGloveRuby.addCuboid(-4.0f, 7.5f, -0.5f, 1, 1, 1, 0.0f);
        this.powerGloveRuby.setPivot(-5.0f, 2.0f, 0.0f);
        this.refBiped = new BipedModel(0.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1862(LivingEntity entityliving, ItemInstance itemstack) {
        GL11.glPushMatrix();
        if (itemstack.itemId < 256 && TileRenderer.method_42(Tile.BY_ID[itemstack.itemId].method_1621())) {
            int textureNum = Tile.BY_ID[itemstack.itemId].getTextureNum();
            if (textureNum == 0) {
                GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/terrain.png"));
            } else {
                GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId(String.format("/terrain%d.png", textureNum)));
            }
            this.tileRenderer.method_48(Tile.BY_ID[itemstack.itemId], itemstack.getDamage(), entityliving.getBrightnessAtEyes(1.0f));
        } else {
            String textureName = "/gui/items.png";
            if (itemstack.itemId < 256) {
                int textureNum = Tile.BY_ID[itemstack.itemId].getTextureNum();
                textureName = textureNum == 0 ? "/terrain.png" : String.format("/terrain%d.png", textureNum);
            }
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId(textureName));
            Vec2 texResolution = this.minecraft.textureManager.getTextureResolution(textureName);
            int width = texResolution.x / 16;
            int height = texResolution.y / 16;
            float halfPixelW = 0.5f / (float) texResolution.x;
            float halfPixelH = 0.5f / (float) texResolution.x;
            Tessellator tessellator = Tessellator.INSTANCE;
            int i = entityliving.getItemTexturePosition(itemstack);
            float f = ((float) (i % 16 * 16) + 0.0f) / 256.0f;
            float f1 = ((float) (i % 16 * 16) + 15.99f) / 256.0f;
            float f2 = ((float) (i / 16 * 16) + 0.0f) / 256.0f;
            float f3 = ((float) (i / 16 * 16) + 15.99f) / 256.0f;
            float f4 = 1.0f;
            float f5 = 0.0f;
            float f6 = 0.3f;
            GL11.glEnable(32826);
            GL11.glTranslatef(-f5, -f6, 0.0f);
            float f7 = 1.5f;
            GL11.glScalef(f7, f7, f7);
            if (this.itemRotate) {
                GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
            float f8 = 0.0625f;
            tessellator.start();
            tessellator.method_1697(0.0f, 0.0f, 1.0f);
            tessellator.vertex(0.0, 0.0, 0.0, f1, f3);
            tessellator.vertex(f4, 0.0, 0.0, f, f3);
            tessellator.vertex(f4, 1.0, 0.0, f, f2);
            tessellator.vertex(0.0, 1.0, 0.0, f1, f2);
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 0.0f, -1.0f);
            tessellator.vertex(0.0, 1.0, 0.0f - f8, f1, f2);
            tessellator.vertex(f4, 1.0, 0.0f - f8, f, f2);
            tessellator.vertex(f4, 0.0, 0.0f - f8, f, f3);
            tessellator.vertex(0.0, 0.0, 0.0f - f8, f1, f3);
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(-1.0f, 0.0f, 0.0f);
            for (int j = 0; j < width; ++j) {
                float f9 = (float) j / (float) width;
                float f13 = f1 + (f - f1) * f9 - halfPixelW;
                float f17 = f4 * f9;
                tessellator.vertex(f17, 0.0, 0.0f - f8, f13, f3);
                tessellator.vertex(f17, 0.0, 0.0, f13, f3);
                tessellator.vertex(f17, 1.0, 0.0, f13, f2);
                tessellator.vertex(f17, 1.0, 0.0f - f8, f13, f2);
            }
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(1.0f, 0.0f, 0.0f);
            for (int k = 0; k < width; ++k) {
                float f10 = (float) k / (float) width;
                float f14 = f1 + (f - f1) * f10 - halfPixelW;
                float f18 = f4 * f10 + 1.0f / (float) width;
                tessellator.vertex(f18, 1.0, 0.0f - f8, f14, f2);
                tessellator.vertex(f18, 1.0, 0.0, f14, f2);
                tessellator.vertex(f18, 0.0, 0.0, f14, f3);
                tessellator.vertex(f18, 0.0, 0.0f - f8, f14, f3);
            }
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, 1.0f, 0.0f);
            for (int l = 0; l < height; ++l) {
                float f11 = (float) l / (float) height;
                float f15 = f3 + (f2 - f3) * f11 - halfPixelH;
                float f19 = f4 * f11 + 1.0f / (float) height;
                tessellator.vertex(0.0, f19, 0.0, f1, f15);
                tessellator.vertex(f4, f19, 0.0, f, f15);
                tessellator.vertex(f4, f19, 0.0f - f8, f, f15);
                tessellator.vertex(0.0, f19, 0.0f - f8, f1, f15);
            }
            tessellator.draw();
            tessellator.start();
            tessellator.method_1697(0.0f, -1.0f, 0.0f);
            for (int i1 = 0; i1 < height; ++i1) {
                float f12 = (float) i1 / (float) height;
                float f16 = f3 + (f2 - f3) * f12 - halfPixelH;
                float f20 = f4 * f12;
                tessellator.vertex(f4, f20, 0.0, f, f16);
                tessellator.vertex(0.0, f20, 0.0, f1, f16);
                tessellator.vertex(0.0, f20, 0.0f - f8, f1, f16);
                tessellator.vertex(f4, f20, 0.0f - f8, f, f16);
            }
            tessellator.draw();
            if (ItemType.byId[itemstack.itemId].isMuzzleFlash(itemstack)) {
                this.renderMuzzleFlash();
            }
            GL11.glDisable(32826);
        }
        GL11.glPopMatrix();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderMuzzleFlash() {
        RenderHelper.disableLighting();
        Tessellator tessellator = Tessellator.INSTANCE;
        float pixelSize = 0.0625f;
        float bX = 0.8125f;
        float tX = 1.3125f;
        float bY = 0.625f;
        float tY = 1.3125f;
        float bU = 0.375f;
        float tU = 0.4375f;
        float bV = 0.6875f;
        float tV = 0.75f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        tessellator.start();
        tessellator.vertex(bX, tY, -6.0f * pixelSize, bU, bV);
        tessellator.vertex(tX, bY, -6.0f * pixelSize, tU, bV);
        tessellator.vertex(tX, bY, 7.0f * pixelSize, tU, tV);
        tessellator.vertex(bX, tY, 7.0f * pixelSize, bU, tV);
        tessellator.vertex(bX, tY, 7.0f * pixelSize, bU, tV);
        tessellator.vertex(tX, bY, 7.0f * pixelSize, tU, tV);
        tessellator.vertex(tX, bY, -6.0f * pixelSize, tU, bV);
        tessellator.vertex(bX, tY, -6.0f * pixelSize, bU, bV);
        tessellator.draw();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderItemInFirstPerson(float f, float swingProgress, float otherHand) {
        float f8;
        float f10;
        float f7;
        float f1 = this.field_2404 + (this.field_2403 - this.field_2404) * f;
        ClientPlayer entityplayersp = this.minecraft.player;
        float f2 = entityplayersp.prevPitch + (entityplayersp.pitch - entityplayersp.prevPitch) * f;
        GL11.glPushMatrix();
        GL11.glRotatef(f2, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(entityplayersp.prevYaw + (entityplayersp.yaw - entityplayersp.prevYaw) * f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableLighting();
        GL11.glPopMatrix();
        ItemInstance itemstack = this.item;
        float f3 = this.minecraft.level.getBrightness(MathsHelper.floor(entityplayersp.x), MathsHelper.floor(entityplayersp.y), MathsHelper.floor(entityplayersp.z));
        if (itemstack != null && ItemType.byId[itemstack.itemId] != null) {
            int i = ItemType.byId[itemstack.itemId].getNameColour(itemstack.getDamage());
            f7 = (float) (i >> 16 & 0xFF) / 255.0f;
            float f11 = (float) (i >> 8 & 0xFF) / 255.0f;
            float f15 = (float) (i & 0xFF) / 255.0f;
            GL11.glColor4f(f3 * f7, f3 * f11, f3 * f15, 1.0f);
        } else {
            GL11.glColor4f(f3, f3, f3, 1.0f);
        }
        if (itemstack != null && itemstack.itemId == ItemType.map.id) {
            GL11.glPushMatrix();
            float f4 = 0.8f;
            f7 = entityplayersp.method_930(f);
            f10 = MathsHelper.sin(f7 * 3.141593f);
            float f13 = MathsHelper.sin(MathsHelper.sqrt(f7) * 3.141593f);
            GL11.glTranslatef(-f13 * 0.4f, MathsHelper.sin(MathsHelper.sqrt(f7) * 3.141593f * 2.0f) * 0.2f, -f10 * 0.2f);
            f7 = 1.0f - f2 / 45.0f + 0.1f;
            if (f7 < 0.0f) {
                f7 = 0.0f;
            }
            if (f7 > 1.0f) {
                f7 = 1.0f;
            }
            f7 = -MathsHelper.cos(f7 * 3.141593f) * 0.5f + 0.5f;
            GL11.glTranslatef(0.0f, 0.0f * f4 - (1.0f - f1) * 1.2f - f7 * 0.5f + 0.04f, -0.9f * f4);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(f7 * -85.0f, 0.0f, 0.0f, 1.0f);
            GL11.glEnable(32826);
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId(this.minecraft.player.skinUrl, this.minecraft.player.method_1314()));
            for (f10 = 0.0f; f10 < 2.0f; f10 += 1.0f) {
                f13 = f10 * 2.0f - 1.0f;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0f, -0.6f, 1.1f * f13);
                GL11.glRotatef(-45.0f * f13, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(59.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-65.0f * f13, 0.0f, 1.0f, 0.0f);
                EntityRenderer render1 = EntityRenderDispatcher.INSTANCE.get(this.minecraft.player);
                PlayerRenderer renderplayer1 = (PlayerRenderer) render1;
                float f17 = 1.0f;
                GL11.glScalef(f17, f17, f17);
                renderplayer1.method_345();
                GL11.glPopMatrix();
            }
            f10 = entityplayersp.method_930(f);
            f13 = MathsHelper.sin(f10 * f10 * 3.141593f);
            float f16 = MathsHelper.sin(MathsHelper.sqrt(f10) * 3.141593f);
            GL11.glRotatef(-f13 * 20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-f16 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-f16 * 80.0f, 1.0f, 0.0f, 0.0f);
            f10 = 0.38f;
            GL11.glScalef(f10, f10, f10);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-1.0f, -1.0f, 0.0f);
            f13 = 0.015625f;
            GL11.glScalef(f13, f13, f13);
            this.minecraft.textureManager.bindTexture(this.minecraft.textureManager.getTextureId("/misc/mapbg.png"));
            Tessellator tessellator = Tessellator.INSTANCE;
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            tessellator.start();
            int byte0 = 7;
            tessellator.vertex(0 - byte0, 128 + byte0, 0.0, 0.0, 1.0);
            tessellator.vertex(128 + byte0, 128 + byte0, 0.0, 1.0, 1.0);
            tessellator.vertex(128 + byte0, 0 - byte0, 0.0, 1.0, 0.0);
            tessellator.vertex(0 - byte0, 0 - byte0, 0.0, 0.0, 0.0);
            tessellator.draw();
            MapStorage mapdata = ItemType.map.method_1730(itemstack, this.minecraft.level);
            this.mapRenderer.renderMap(this.minecraft.player, this.minecraft.textureManager, mapdata);
            GL11.glPopMatrix();
        } else if (itemstack != null) {
            if (itemstack.itemId != Items.woodenShield.id && itemstack.itemId != Items.powerGlove.id) {
                GL11.glPushMatrix();
                float ft3 = 0.8f;
                f7 = MathsHelper.sin(swingProgress * 3.141593f);
                float f9 = MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f);
                GL11.glTranslatef(-f9 * 0.4f, MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f * 2.0f) * 0.2f, -f7 * 0.2f);
                GL11.glTranslatef(0.7f * ft3, -0.65f * ft3 - (1.0f - f1) * 0.6f, -0.9f * ft3);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glEnable(32826);
                f7 = MathsHelper.sin(swingProgress * swingProgress * 3.141593f);
                f9 = MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f);
                GL11.glRotatef(-f7 * 20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-f9 * 20.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-f9 * 80.0f, 1.0f, 0.0f, 0.0f);
                GL11.glScalef(0.4f, 0.4f, 0.4f);
                if (itemstack.getType().shouldRotate180()) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                this.method_1862(entityplayersp, itemstack);
                GL11.glPopMatrix();
            } else if (itemstack.itemId == Items.powerGlove.id) {
                GL11.glPushMatrix();
                float f4 = 0.8f;
                f8 = MathsHelper.sin(swingProgress * 3.141593f);
                f10 = MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f);
                GL11.glTranslatef(-f10 * 0.3f, MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f * 2.0f) * 0.4f, -f8 * 0.4f);
                GL11.glTranslatef(0.8f * f4, -0.75f * f4 - (1.0f - f1) * 0.6f, -0.9f * f4);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glEnable(32826);
                f8 = MathsHelper.sin(swingProgress * swingProgress * 3.141593f);
                f10 = MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f);
                GL11.glRotatef(f10 * 70.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-f8 * 20.0f, 0.0f, 0.0f, 1.0f);
                GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId(this.minecraft.player.skinUrl, this.minecraft.player.method_1314()));
                GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
                GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glTranslatef(5.6f, 0.0f, 0.0f);
                EntityRenderer render = EntityRenderDispatcher.INSTANCE.get(this.minecraft.player);
                PlayerRenderer renderplayer = (PlayerRenderer) render;
                renderplayer.method_345();
                GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/mob/powerGlove.png"));
                this.refBiped.handSwingProgress = 0.0f;
                this.refBiped.setAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                this.powerGlove.pivotX = this.refBiped.rightArm.pivotX;
                this.powerGlove.pivotY = this.refBiped.rightArm.pivotY;
                this.powerGlove.pivotZ = this.refBiped.rightArm.pivotZ;
                this.powerGlove.pitch = this.refBiped.rightArm.pitch;
                this.powerGlove.yaw = this.refBiped.rightArm.yaw;
                this.powerGlove.roll = this.refBiped.rightArm.roll;
                this.powerGlove.render(0.0625f);
                this.powerGloveRuby.pivotX = this.refBiped.rightArm.pivotX;
                this.powerGloveRuby.pivotY = this.refBiped.rightArm.pivotY;
                this.powerGloveRuby.pivotZ = this.refBiped.rightArm.pivotZ;
                this.powerGloveRuby.pitch = this.refBiped.rightArm.pitch;
                this.powerGloveRuby.yaw = this.refBiped.rightArm.yaw;
                this.powerGloveRuby.roll = this.refBiped.rightArm.roll;
                this.powerGloveRuby.render(0.0625f);
                GL11.glPopMatrix();
            } else {
                this.renderShield(f, swingProgress, otherHand);
            }
        } else {
            GL11.glPushMatrix();
            float f4 = 0.8f;
            f8 = MathsHelper.sin(swingProgress * 3.141593f);
            f10 = MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f);
            GL11.glTranslatef(-f10 * 0.3f, MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f * 2.0f) * 0.4f, -f8 * 0.4f);
            GL11.glTranslatef(0.8f * f4, -0.75f * f4 - (1.0f - f1) * 0.6f, -0.9f * f4);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            f8 = MathsHelper.sin(swingProgress * swingProgress * 3.141593f);
            f10 = MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f);
            GL11.glRotatef(f10 * 70.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-f8 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId(this.minecraft.player.skinUrl, this.minecraft.player.method_1314()));
            GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
            GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(5.6f, 0.0f, 0.0f);
            EntityRenderer render = EntityRenderDispatcher.INSTANCE.get(this.minecraft.player);
            PlayerRenderer renderplayer = (PlayerRenderer) render;
            renderplayer.method_345();
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826);
        RenderHelper.disableLighting();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void renderShield(float f, float swingProgress, float otherHand) {
        float f1 = this.field_2404 + (this.field_2403 - this.field_2404) * f;
        ClientPlayer entityplayersp = this.minecraft.player;
        float f2 = this.minecraft.level.getBrightness(MathsHelper.floor(entityplayersp.x), MathsHelper.floor(entityplayersp.y), MathsHelper.floor(entityplayersp.z));
        GL11.glColor4f(f2, f2, f2, 1.0f);
        ItemInstance itemstack = new ItemInstance(Items.woodenShield);
        GL11.glPushMatrix();
        float f3 = 0.8f;
        if (otherHand == 0.0f) {
            float f7 = MathsHelper.sin(swingProgress * 3.141593f);
            float f9 = MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f);
            GL11.glTranslatef(-f9 * 0.4f, MathsHelper.sin(MathsHelper.sqrt(swingProgress) * 3.141593f * 2.0f) * 0.2f, -f7 * 0.2f);
            GL11.glTranslatef(1.0f, -0.65f * f3 - (1.0f - f1) * 0.6f, -0.9f * f3);
        } else {
            float f7 = MathsHelper.sin(otherHand * 3.141593f);
            float f9 = MathsHelper.sin(MathsHelper.sqrt(otherHand) * 3.141593f);
            GL11.glTranslatef(f9 * 0.4f, MathsHelper.sin(MathsHelper.sqrt(otherHand) * 3.141593f * 2.0f) * 0.2f, -f7 * 0.2f);
            GL11.glTranslatef(1.0f, -0.65f * f3 - (1.0f - f1) * 0.6f, -0.9f * f3);
            GL11.glRotatef(-90.0f * f7, 0.0f, 1.0f, 0.0f);
        }
        GL11.glEnable(32826);
        GL11.glScalef(0.6f, 0.6f, 0.6f);
        this.itemRotate = false;
        this.method_1862(entityplayersp, itemstack);
        this.itemRotate = true;
        GL11.glPopMatrix();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1864(float f) {
        GL11.glDisable(3008);
        if (!this.minecraft.cameraActive && this.minecraft.player.method_1359()) {
            int i = this.minecraft.textureManager.getTextureId("/terrain.png");
            GL11.glBindTexture(3553, i);
            this.method_1867(f);
        }
        if (this.minecraft.field_2807.isInsideWall()) {
            int j = MathsHelper.floor(this.minecraft.field_2807.x);
            int l = MathsHelper.floor(this.minecraft.field_2807.y);
            int i1 = MathsHelper.floor(this.minecraft.field_2807.z);
            int j1 = this.minecraft.textureManager.getTextureId("/terrain.png");
            GL11.glBindTexture(3553, j1);
            int k1 = this.minecraft.level.getTileId(j, l, i1);
            if (this.minecraft.level.canSuffocate(j, l, i1) && this.minecraft.level.isFullOpaque(j, l, i1)) {
                this.method_1861(f, Tile.BY_ID[k1].getTextureForSide(2));
            } else {
                for (int l1 = 0; l1 < 8; ++l1) {
                    int k2;
                    int j2;
                    float f1 = ((float) ((l1 >> 0) % 2) - 0.5f) * this.minecraft.player.width * 0.9f;
                    float f2 = ((float) ((l1 >> 1) % 2) - 0.5f) * this.minecraft.player.height * 0.2f;
                    float f3 = ((float) ((l1 >> 2) % 2) - 0.5f) * this.minecraft.player.width * 0.9f;
                    int i2 = MathsHelper.floor((float) j + f1);
                    if (!this.minecraft.level.canSuffocate(i2, j2 = MathsHelper.floor((float) l + f2), k2 = MathsHelper.floor((float) i1 + f3)))
                        continue;
                    k1 = this.minecraft.level.getTileId(i2, j2, k2);
                }
            }
            if (Tile.BY_ID[k1] != null) {
                this.method_1861(f, Tile.BY_ID[k1].getTextureForSide(2));
            }
        }
        if (this.minecraft.field_2807.isInFluid(Material.WATER)) {
            int k = this.minecraft.textureManager.getTextureId("/misc/water.png");
            GL11.glBindTexture(3553, k);
            this.method_1866(f);
        }
        GL11.glEnable(3008);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1861(float f, int i) {
        if (this.minecraft.player.field_1642) {
            return;
        }
        Tessellator tessellator = Tessellator.INSTANCE;
        float f1 = this.minecraft.field_2807.getBrightnessAtEyes(f);
        f1 = 0.1f;
        GL11.glColor4f(f1, f1, f1, 0.5f);
        GL11.glPushMatrix();
        float f2 = -1.0f;
        float f3 = 1.0f;
        float f4 = -1.0f;
        float f5 = 1.0f;
        float f6 = -0.5f;
        float f7 = 0.0078125f;
        float f8 = (float) (i % 16) / 256.0f - f7;
        float f9 = ((float) (i % 16) + 15.99f) / 256.0f + f7;
        float f10 = (float) (i / 16) / 256.0f - f7;
        float f11 = ((float) (i / 16) + 15.99f) / 256.0f + f7;
        tessellator.start();
        tessellator.vertex(f2, f4, f6, f9, f11);
        tessellator.vertex(f3, f4, f6, f8, f11);
        tessellator.vertex(f3, f5, f6, f8, f10);
        tessellator.vertex(f2, f5, f6, f9, f10);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1866(float f) {
        Tessellator tessellator = Tessellator.INSTANCE;
        float f1 = this.minecraft.field_2807.getBrightnessAtEyes(f);
        GL11.glColor4f(f1, f1, f1, 0.5f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        float f2 = 4.0f;
        float f3 = -1.0f;
        float f4 = 1.0f;
        float f5 = -1.0f;
        float f6 = 1.0f;
        float f7 = -0.5f;
        float f8 = -this.minecraft.field_2807.yaw / 64.0f;
        float f9 = this.minecraft.field_2807.pitch / 64.0f;
        tessellator.start();
        tessellator.vertex(f3, f5, f7, f2 + f8, f2 + f9);
        tessellator.vertex(f4, f5, f7, 0.0f + f8, f2 + f9);
        tessellator.vertex(f4, f6, f7, 0.0f + f8, 0.0f + f9);
        tessellator.vertex(f3, f6, f7, f2 + f8, 0.0f + f9);
        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1867(float f) {
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        float f1 = 1.0f;
        for (int i = 0; i < 2; ++i) {
            GL11.glPushMatrix();
            int j = Tile.FIRE.tex + i * 16;
            int k = (j & 0xF) << 4;
            int l = j & 0xF0;
            float f2 = (float) k / 256.0f;
            float f3 = ((float) k + 15.99f) / 256.0f;
            float f4 = (float) l / 256.0f;
            float f5 = ((float) l + 15.99f) / 256.0f;
            float f6 = (0.0f - f1) / 2.0f;
            float f7 = f6 + f1;
            float f8 = 0.0f - f1 / 2.0f;
            float f9 = f8 + f1;
            float f10 = -0.5f;
            GL11.glTranslatef((float) (-(i * 2 - 1)) * 0.24f, -0.3f, 0.0f);
            GL11.glRotatef((float) (i * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            tessellator.start();
            tessellator.vertex(f6, f8, f10, f3, f5);
            tessellator.vertex(f7, f8, f10, f2, f5);
            tessellator.vertex(f7, f9, f10, f2, f4);
            tessellator.vertex(f6, f9, f10, f3, f4);
            tessellator.draw();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1859() {
        float f;
        float f1;
        float f2;
        boolean flag;
        ItemInstance itemstack;
        this.field_2404 = this.field_2403;
        ClientPlayer entityplayersp = this.minecraft.player;
        ItemInstance itemstack1 = itemstack = entityplayersp.inventory.getHeldItem();
        boolean bl = flag = this.field_2407 == entityplayersp.inventory.selectedHotbarSlot && itemstack1 == this.item;
        if (this.item == null && itemstack1 == null) {
            flag = true;
        }
        if (itemstack1 != null && this.item != null && itemstack1 != this.item && itemstack1.itemId == this.item.itemId && itemstack1.getDamage() == this.item.getDamage()) {
            this.item = itemstack1;
            flag = true;
        }
        if ((f2 = (f1 = flag ? 1.0f : 0.0f) - this.field_2403) < -(f = 0.4f)) {
            f2 = -f;
        }
        if (f2 > f) {
            f2 = f;
        }
        this.field_2403 += f2;
        if (this.field_2403 < 0.1f) {
            this.item = itemstack1;
            this.field_2407 = entityplayersp.inventory.selectedHotbarSlot;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean hasItem() {
        return this.item != null;
    }
}
