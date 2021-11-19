package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayer;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.armour.ArmourItem;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;

public class MixinPlayerRenderer extends LivingEntityRenderer {
    private BipedModel headRenderer;
    private BipedModel field_295;
    private BipedModel field_296;
    private static final String[] field_297 = new String[]{"cloth", "chain", "iron", "diamond", "gold"};

    public MixinPlayerRenderer() {
        super(new BipedModel(0.0f), 0.5f);
        this.headRenderer = (BipedModel)this.field_909;
        this.field_295 = new BipedModel(1.0f);
        this.field_296 = new BipedModel(0.5f);
    }

    protected boolean method_344(Player entityplayer, int i, float f) {
        ItemType item;
        ItemInstance itemstack = entityplayer.inventory.getArmourItem(3 - i);
        if (itemstack != null && (item = itemstack.getType()) instanceof ArmourItem) {
            ArmourItem itemarmor = (ArmourItem)item;
            this.bindTexture("/armor/" + field_297[itemarmor.field_2085] + "_" + (i != 2 ? 1 : 2) + ".png");
            BipedModel modelbiped = i != 2 ? this.field_295 : this.field_296;
            modelbiped.head.visible = i == 0;
            modelbiped.helmet.visible = i == 0;
            modelbiped.body.visible = i == 1 || i == 2;
            modelbiped.rightArm.visible = i == 1;
            modelbiped.leftArm.visible = i == 1;
            modelbiped.rightLeg.visible = i == 2 || i == 3;
            modelbiped.leftLeg.visible = i == 2 || i == 3;
            this.setModel(modelbiped);
            return true;
        }
        return false;
    }

    public void method_341(Player entityplayer, double d, double d1, double d2, float f, float f1) {
        ItemInstance itemstack = entityplayer.inventory.getHeldItem();
        this.headRenderer.sneaking = itemstack != null;
        this.field_296.sneaking = this.headRenderer.sneaking;
        this.field_295.sneaking = this.headRenderer.sneaking;
        this.field_296.aiming = this.headRenderer.aiming = entityplayer.method_1373();
        this.field_295.aiming = this.headRenderer.aiming;
        double d3 = d1 - (double)entityplayer.standingEyeHeight;
        if (entityplayer.method_1373() && !(entityplayer instanceof ClientPlayer)) {
            d3 -= 0.125;
        }
        super.render(entityplayer, d, d3, d2, f, f1);
        this.headRenderer.aiming = false;
        this.field_296.aiming = false;
        this.field_295.aiming = false;
        this.headRenderer.sneaking = false;
        this.field_296.sneaking = false;
        this.field_295.sneaking = false;
    }

    protected void method_340(Player entityplayer, double d, double d1, double d2) {
        if (Minecraft.isHudHidden() && entityplayer != this.dispatcher.entity) {
            float f3;
            float f = 1.6f;
            float f1 = 0.01666667f * f;
            float f2 = entityplayer.distanceTo(this.dispatcher.entity);
            float f4 = f3 = entityplayer.method_1373() ? 32.0f : 64.0f;
            if (f2 < f3) {
                String s = entityplayer.name;
                if (!entityplayer.method_1373()) {
                    if (entityplayer.isSleeping()) {
                        this.method_818(entityplayer, s, d, d1 - 1.5, d2, 64);
                    } else {
                        this.method_818(entityplayer, s, d, d1, d2, 64);
                    }
                } else {
                    TextRenderer fontrenderer = this.getTextRenderer();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)d + 0.0f, (float)d1 + 2.3f, (float)d2);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-this.dispatcher.field_2497, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(this.dispatcher.field_2498, 1.0f, 0.0f, 0.0f);
                    GL11.glScalef(-f1, -f1, f1);
                    GL11.glDisable(2896);
                    GL11.glTranslatef(0.0f, 0.25f / f1, 0.0f);
                    GL11.glDepthMask(false);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    Tessellator tessellator = Tessellator.INSTANCE;
                    GL11.glDisable(3553);
                    tessellator.start();
                    int i = fontrenderer.getTextWidth(s) / 2;
                    tessellator.colour(0.0f, 0.0f, 0.0f, 0.25f);
                    tessellator.pos(-i - 1, -1.0, 0.0);
                    tessellator.pos(-i - 1, 8.0, 0.0);
                    tessellator.pos(i + 1, 8.0, 0.0);
                    tessellator.pos(i + 1, -1.0, 0.0);
                    tessellator.draw();
                    GL11.glEnable(3553);
                    GL11.glDepthMask(true);
                    fontrenderer.drawTextWithoutShadow(s, -fontrenderer.getTextWidth(s) / 2, 0, 0x20FFFFFF);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    protected void method_342(Player entityplayer, float f) {
        ItemInstance itemstack1;
        ItemInstance itemstack = entityplayer.inventory.getArmourItem(3);
        if (itemstack != null && itemstack.getType().id < 256) {
            GL11.glPushMatrix();
            this.headRenderer.head.method_1820(0.0625f);
            if (TileRenderer.method_42(Tile.BY_ID[itemstack.itemId].method_1621())) {
                float f1 = 0.625f;
                GL11.glTranslatef(0.0f, -0.25f, 0.0f);
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(f1, -f1, f1);
            }
            this.dispatcher.field_2494.method_1862(entityplayer, itemstack);
            GL11.glPopMatrix();
        }
        if (entityplayer.name.equals("deadmau5") && this.method_2027(entityplayer.skinUrl, null)) {
            for (int i = 0; i < 2; ++i) {
                float f2 = entityplayer.prevYaw + (entityplayer.yaw - entityplayer.prevYaw) * f - (entityplayer.field_1013 + (entityplayer.field_1012 - entityplayer.field_1013) * f);
                float f6 = entityplayer.prevPitch + (entityplayer.pitch - entityplayer.prevPitch) * f;
                GL11.glPushMatrix();
                GL11.glRotatef(f2, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(f6, 1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(0.375f * (float)(i * 2 - 1), 0.0f, 0.0f);
                GL11.glTranslatef(0.0f, -0.375f, 0.0f);
                GL11.glRotatef(-f6, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-f2, 0.0f, 1.0f, 0.0f);
                float f7 = 1.333333f;
                GL11.glScalef(f7, f7, f7);
                this.headRenderer.renderDeadMau5Ears(0.0625f);
                GL11.glPopMatrix();
            }
        }
        if (entityplayer.cloakTexture != null || this.method_2027(entityplayer.playerCloakUrl, null)) {
            if (entityplayer.cloakTexture != null) {
                this.bindTexture(entityplayer.cloakTexture);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 0.125f);
            double d = entityplayer.field_530 + (entityplayer.field_533 - entityplayer.field_530) * (double)f - (entityplayer.prevX + (entityplayer.x - entityplayer.prevX) * (double)f);
            double d1 = entityplayer.field_531 + (entityplayer.field_534 - entityplayer.field_531) * (double)f - (entityplayer.prevY + (entityplayer.y - entityplayer.prevY) * (double)f);
            double d2 = entityplayer.field_532 + (entityplayer.field_535 - entityplayer.field_532) * (double)f - (entityplayer.prevZ + (entityplayer.z - entityplayer.prevZ) * (double)f);
            float f8 = entityplayer.field_1013 + (entityplayer.field_1012 - entityplayer.field_1013) * f;
            double d3 = MathsHelper.sin(f8 * 3.141593f / 180.0f);
            double d4 = -MathsHelper.cos(f8 * 3.141593f / 180.0f);
            float f9 = (float)d1 * 10.0f;
            if (f9 < -6.0f) {
                f9 = -6.0f;
            }
            if (f9 > 32.0f) {
                f9 = 32.0f;
            }
            float f10 = (float)(d * d3 + d2 * d4) * 100.0f;
            float f11 = (float)(d * d4 - d2 * d3) * 100.0f;
            if (f10 < 0.0f) {
                f10 = 0.0f;
            }
            float f12 = entityplayer.field_524 + (entityplayer.field_525 - entityplayer.field_524) * f;
            f9 += MathsHelper.sin((entityplayer.field_1634 + (entityplayer.field_1635 - entityplayer.field_1634) * f) * 6.0f) * 32.0f * f12;
            if (entityplayer.method_1373()) {
                f9 += 25.0f;
            }
            GL11.glRotatef(6.0f + f10 / 2.0f + f9, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(f11 / 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-f11 / 2.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            this.headRenderer.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
        if ((itemstack1 = entityplayer.inventory.getHeldItem()) != null) {
            GL11.glPushMatrix();
            this.headRenderer.rightArm.method_1820(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            if (entityplayer.fishHook != null) {
                itemstack1 = new ItemInstance(ItemType.stick);
            }
            if (itemstack1.itemId < 256 && TileRenderer.method_42(Tile.BY_ID[itemstack1.itemId].method_1621())) {
                float f3 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(f3 *= 0.75f, -f3, f3);
            } else if (ItemType.byId[itemstack1.itemId].shouldRenderLikeStick()) {
                float f4 = 0.625f;
                if (ItemType.byId[itemstack1.itemId].shouldRotate180()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
                GL11.glScalef(f4, -f4, f4);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            } else {
                float f5 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(f5, f5, f5);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            this.dispatcher.field_2494.method_1862(entityplayer, itemstack1);
            GL11.glPopMatrix();
        }
    }

    protected void method_347(Player entityplayer, float f) {
        float f1 = 0.9375f;
        GL11.glScalef(f1, f1, f1);
    }

    public void method_345() {
        this.headRenderer.handSwingProgress = 0.0f;
        this.headRenderer.setAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.headRenderer.rightArm.render(0.0625f);
    }

    protected void method_346(Player entityplayer, double d, double d1, double d2) {
        if (entityplayer.isAlive() && entityplayer.isSleeping()) {
            super.method_826(entityplayer, d + (double)entityplayer.field_509, d1 + (double)entityplayer.field_507, d2 + (double)entityplayer.field_510);
        } else {
            super.method_826(entityplayer, d, d1, d2);
        }
    }

    protected void method_343(Player entityplayer, float f, float f1, float f2) {
        if (entityplayer.isAlive() && entityplayer.isSleeping()) {
            GL11.glRotatef(entityplayer.method_482(), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.method_816(entityplayer), 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
        } else {
            super.method_824(entityplayer, f, f1, f2);
        }
    }

    protected void method_821(LivingEntity entityliving, double d, double d1, double d2) {
        this.method_340((Player)entityliving, d, d1, d2);
    }

    protected void method_823(LivingEntity entityliving, float f) {
        this.method_347((Player)entityliving, f);
    }

    protected boolean render(LivingEntity entityliving, int i, float f) {
        return this.method_344((Player)entityliving, i, f);
    }

    protected void method_827(LivingEntity entityliving, float f) {
        this.method_342((Player)entityliving, f);
    }

    protected void method_824(LivingEntity entityliving, float f, float f1, float f2) {
        this.method_343((Player)entityliving, f, f1, f2);
    }

    protected void method_826(LivingEntity entityliving, double d, double d1, double d2) {
        this.method_346((Player)entityliving, d, d1, d2);
    }

    public void render(LivingEntity entity, double x, double y, double z, float f, float f1) {
        this.method_341((Player)entity, x, y, z, f, f1);
    }

    public void render(Entity entity, double x, double y, double z, float f, float f1) {
        this.method_341((Player)entity, x, y, z, f, f1);
    }
}