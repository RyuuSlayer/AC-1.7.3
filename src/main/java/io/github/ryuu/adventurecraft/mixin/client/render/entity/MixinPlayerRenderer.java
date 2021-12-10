package io.github.ryuu.adventurecraft.mixin.client.render.entity;

import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer {

    @Shadow
    private BipedModel headRenderer;

    public MixinPlayerRenderer(EntityModel arg, float f) {
        super(arg, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void method_827(Player entityplayer, float f) {
        ExPlayer exPlayer = (ExPlayer) entityplayer;
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
                GL11.glTranslatef(0.375f * (float) (i * 2 - 1), 0.0f, 0.0f);
                GL11.glTranslatef(0.0f, -0.375f, 0.0f);
                GL11.glRotatef(-f6, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-f2, 0.0f, 1.0f, 0.0f);
                float f7 = 1.333333f;
                GL11.glScalef(f7, f7, f7);
                this.headRenderer.renderDeadMau5Ears(0.0625f);
                GL11.glPopMatrix();
            }
        }
        if (exPlayer.getCloakTexture() != null || this.method_2027(entityplayer.playerCloakUrl, null)) {
            if (exPlayer.getCloakTexture() != null) {
                this.bindTexture(exPlayer.getCloakTexture());
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 0.125f);
            double d = entityplayer.field_530 + (entityplayer.field_533 - entityplayer.field_530) * (double) f - (entityplayer.prevX + (entityplayer.x - entityplayer.prevX) * (double) f);
            double d1 = entityplayer.field_531 + (entityplayer.field_534 - entityplayer.field_531) * (double) f - (entityplayer.prevY + (entityplayer.y - entityplayer.prevY) * (double) f);
            double d2 = entityplayer.field_532 + (entityplayer.field_535 - entityplayer.field_532) * (double) f - (entityplayer.prevZ + (entityplayer.z - entityplayer.prevZ) * (double) f);
            float f8 = entityplayer.field_1013 + (entityplayer.field_1012 - entityplayer.field_1013) * f;
            double d3 = MathsHelper.sin(f8 * 3.141593f / 180.0f);
            double d4 = -MathsHelper.cos(f8 * 3.141593f / 180.0f);
            float f9 = (float) d1 * 10.0f;
            if (f9 < -6.0f) {
                f9 = -6.0f;
            }
            if (f9 > 32.0f) {
                f9 = 32.0f;
            }
            float f10 = (float) (d * d3 + d2 * d4) * 100.0f;
            float f11 = (float) (d * d4 - d2 * d3) * 100.0f;
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
}
