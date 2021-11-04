package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import org.lwjgl.opengl.GL11;

public class ra {
    private final Minecraft a;

    private ItemInstance b;

    private float c;

    private float d;

    private final cv e;

    private final iy f;

    private int g;

    private boolean itemRotate;

    public ps powerGlove;

    public ps powerGloveRuby;

    private final fh refBiped;

    public ra(Minecraft minecraft) {
        this.b = null;
        this.c = 0.0F;
        this.d = 0.0F;
        this.e = new cv();
        this.g = -1;
        this.a = minecraft;
        this.f = new iy(minecraft.q, minecraft.z, minecraft.p);
        this.itemRotate = true;
        this.powerGlove = new ps(0, 0);
        this.powerGlove.a(-3.5F, 4.5F, -2.5F, 5, 7, 5, 0.0F);
        this.powerGlove.a(-5.0F, 2.0F, 0.0F);
        this.powerGloveRuby = new ps(0, 0);
        this.powerGloveRuby.a(-4.0F, 7.5F, -0.5F, 1, 1, 1, 0.0F);
        this.powerGloveRuby.a(-5.0F, 2.0F, 0.0F);
        this.refBiped = new fh(0.0F);
    }

    public void a(ls entityliving, ItemInstance itemstack) {
        GL11.glPushMatrix();
        if (itemstack.c < 256 && cv.a(Tile.m[itemstack.c].b())) {
            int textureNum = Tile.m[itemstack.c].getTextureNum();
            if (textureNum == 0) {
                GL11.glBindTexture(3553, this.a.p.b("/terrain.png"));
            } else {
                GL11.glBindTexture(3553, this.a.p.b(String.format("/terrain%d.png", new Object[]{Integer.valueOf(textureNum)})));
            }
            this.e.a(Tile.m[itemstack.c], itemstack.i(), entityliving.a(1.0F));
        } else {
            String textureName = "/gui/items.png";
            if (itemstack.c < 256) {
                int textureNum = Tile.m[itemstack.c].getTextureNum();
                if (textureNum == 0) {
                    textureName = "/terrain.png";
                } else {
                    textureName = String.format("/terrain%d.png", Integer.valueOf(textureNum));
                }
            }
            GL11.glBindTexture(3553, this.a.p.b(textureName));
            Vec2 texResolution = this.a.p.getTextureResolution(textureName);
            int width = texResolution.x / 16;
            int height = texResolution.y / 16;
            float halfPixelW = 0.5F / texResolution.x;
            float halfPixelH = 0.5F / texResolution.x;
            Tessellator tessellator = Tessellator.a;
            int i = entityliving.c(itemstack);
            float f = ((i % 16 * 16) + 0.0F) / 256.0F;
            float f1 = ((i % 16 * 16) + 15.99F) / 256.0F;
            float f2 = ((i / 16 * 16) + 0.0F) / 256.0F;
            float f3 = ((i / 16 * 16) + 15.99F) / 256.0F;
            float f4 = 1.0F;
            float f5 = 0.0F;
            float f6 = 0.3F;
            GL11.glEnable(32826);
            GL11.glTranslatef(-f5, -f6, 0.0F);
            float f7 = 1.5F;
            GL11.glScalef(f7, f7, f7);
            if (this.itemRotate) {
                GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            }
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            float f8 = 0.0625F;
            tessellator.b();
            tessellator.b(0.0F, 0.0F, 1.0F);
            tessellator.a(0.0D, 0.0D, 0.0D, f1, f3);
            tessellator.a(f4, 0.0D, 0.0D, f, f3);
            tessellator.a(f4, 1.0D, 0.0D, f, f2);
            tessellator.a(0.0D, 1.0D, 0.0D, f1, f2);
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 0.0F, -1.0F);
            tessellator.a(0.0D, 1.0D, (0.0F - f8), f1, f2);
            tessellator.a(f4, 1.0D, (0.0F - f8), f, f2);
            tessellator.a(f4, 0.0D, (0.0F - f8), f, f3);
            tessellator.a(0.0D, 0.0D, (0.0F - f8), f1, f3);
            tessellator.a();
            tessellator.b();
            tessellator.b(-1.0F, 0.0F, 0.0F);
            for (int j = 0; j < width; j++) {
                float f9 = j / width;
                float f13 = f1 + (f - f1) * f9 - halfPixelW;
                float f17 = f4 * f9;
                tessellator.a(f17, 0.0D, (0.0F - f8), f13, f3);
                tessellator.a(f17, 0.0D, 0.0D, f13, f3);
                tessellator.a(f17, 1.0D, 0.0D, f13, f2);
                tessellator.a(f17, 1.0D, (0.0F - f8), f13, f2);
            }
            tessellator.a();
            tessellator.b();
            tessellator.b(1.0F, 0.0F, 0.0F);
            for (int k = 0; k < width; k++) {
                float f10 = k / width;
                float f14 = f1 + (f - f1) * f10 - halfPixelW;
                float f18 = f4 * f10 + 1.0F / width;
                tessellator.a(f18, 1.0D, (0.0F - f8), f14, f2);
                tessellator.a(f18, 1.0D, 0.0D, f14, f2);
                tessellator.a(f18, 0.0D, 0.0D, f14, f3);
                tessellator.a(f18, 0.0D, (0.0F - f8), f14, f3);
            }
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 1.0F, 0.0F);
            for (int l = 0; l < height; l++) {
                float f11 = l / height;
                float f15 = f3 + (f2 - f3) * f11 - halfPixelH;
                float f19 = f4 * f11 + 1.0F / height;
                tessellator.a(0.0D, f19, 0.0D, f1, f15);
                tessellator.a(f4, f19, 0.0D, f, f15);
                tessellator.a(f4, f19, (0.0F - f8), f, f15);
                tessellator.a(0.0D, f19, (0.0F - f8), f1, f15);
            }
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, -1.0F, 0.0F);
            for (int i1 = 0; i1 < height; i1++) {
                float f12 = i1 / height;
                float f16 = f3 + (f2 - f3) * f12 - halfPixelH;
                float f20 = f4 * f12;
                tessellator.a(f4, f20, 0.0D, f, f16);
                tessellator.a(0.0D, f20, 0.0D, f1, f16);
                tessellator.a(0.0D, f20, (0.0F - f8), f1, f16);
                tessellator.a(f4, f20, (0.0F - f8), f, f16);
            }
            tessellator.a();
            if (ItemType.c[itemstack.c].isMuzzleFlash(itemstack))
                renderMuzzleFlash();
            GL11.glDisable(32826);
        }
        GL11.glPopMatrix();
    }

    public void renderMuzzleFlash() {
        u.a();
        Tessellator tessellator = Tessellator.a;
        float pixelSize = 0.0625F;
        float bX = 0.8125F;
        float tX = 1.3125F;
        float bY = 0.625F;
        float tY = 1.3125F;
        float bU = 0.375F;
        float tU = 0.4375F;
        float bV = 0.6875F;
        float tV = 0.75F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.b();
        tessellator.a(bX, tY, (-6.0F * pixelSize), bU, bV);
        tessellator.a(tX, bY, (-6.0F * pixelSize), tU, bV);
        tessellator.a(tX, bY, (7.0F * pixelSize), tU, tV);
        tessellator.a(bX, tY, (7.0F * pixelSize), bU, tV);
        tessellator.a(bX, tY, (7.0F * pixelSize), bU, tV);
        tessellator.a(tX, bY, (7.0F * pixelSize), tU, tV);
        tessellator.a(tX, bY, (-6.0F * pixelSize), tU, bV);
        tessellator.a(bX, tY, (-6.0F * pixelSize), bU, bV);
        tessellator.a();
    }

    public void renderItemInFirstPerson(float f, float swingProgress, float otherHand) {
        float f1 = this.d + (this.c - this.d) * f;
        dc entityplayersp = this.a.h;
        float f2 = ((Player) entityplayersp).aV + (((Player) entityplayersp).aT - ((Player) entityplayersp).aV) * f;
        GL11.glPushMatrix();
        GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(((Player) entityplayersp).aU + (((Player) entityplayersp).aS - ((Player) entityplayersp).aU) * f, 0.0F, 1.0F, 0.0F);
        u.b();
        GL11.glPopMatrix();
        ItemInstance itemstack = this.b;
        float f3 = this.a.f.c(in.b(((Player) entityplayersp).aM), in.b(((Player) entityplayersp).aN), in.b(((Player) entityplayersp).aO));
        if (itemstack != null && ItemType.c[itemstack.c] != null) {
            int i = ItemType.c[itemstack.c].f(itemstack.i());
            float f7 = (i >> 16 & 0xFF) / 255.0F;
            float f11 = (i >> 8 & 0xFF) / 255.0F;
            float f15 = (i & 0xFF) / 255.0F;
            GL11.glColor4f(f3 * f7, f3 * f11, f3 * f15, 1.0F);
        } else {
            GL11.glColor4f(f3, f3, f3, 1.0F);
        }
        if (itemstack != null && itemstack.c == ItemType.bb.bf) {
            GL11.glPushMatrix();
            float f4 = 0.8F;
            float f7 = entityplayersp.d(f);
            float f10 = in.a(f7 * 3.141593F);
            float f13 = in.a(in.c(f7) * 3.141593F);
            GL11.glTranslatef(-f13 * 0.4F, in.a(in.c(f7) * 3.141593F * 2.0F) * 0.2F, -f10 * 0.2F);
            f7 = 1.0F - f2 / 45.0F + 0.1F;
            if (f7 < 0.0F)
                f7 = 0.0F;
            if (f7 > 1.0F)
                f7 = 1.0F;
            f7 = -in.b(f7 * 3.141593F) * 0.5F + 0.5F;
            GL11.glTranslatef(0.0F, 0.0F * f4 - (1.0F - f1) * 1.2F - f7 * 0.5F + 0.04F, -0.9F * f4);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(f7 * -85.0F, 0.0F, 0.0F, 1.0F);
            GL11.glEnable(32826);
            GL11.glBindTexture(3553, this.a.p.a(this.a.h.bA, this.a.h.q_()));
            for (f10 = 0.0F; f10 < 2.0F; f10++) {
                f13 = f10 * 2.0F - 1.0F;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0F, -0.6F, 1.1F * f13);
                GL11.glRotatef(-45.0F * f13, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-65.0F * f13, 0.0F, 1.0F, 0.0F);
                bw render1 = th.a.a((Entity) this.a.h);
                PlayerRenderer renderplayer1 = (PlayerRenderer) render1;
                float f17 = 1.0F;
                GL11.glScalef(f17, f17, f17);
                renderplayer1.b();
                GL11.glPopMatrix();
            }
            f10 = entityplayersp.d(f);
            f13 = in.a(f10 * f10 * 3.141593F);
            float f16 = in.a(in.c(f10) * 3.141593F);
            GL11.glRotatef(-f13 * 20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f16 * 20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f16 * 80.0F, 1.0F, 0.0F, 0.0F);
            f10 = 0.38F;
            GL11.glScalef(f10, f10, f10);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
            f13 = 0.015625F;
            GL11.glScalef(f13, f13, f13);
            this.a.p.b(this.a.p.b("/misc/mapbg.png"));
            Tessellator tessellator = Tessellator.a;
            GL11.glNormal3f(0.0F, 0.0F, -1.0F);
            tessellator.b();
            byte byte0 = 7;
            tessellator.a((0 - byte0), (128 + byte0), 0.0D, 0.0D, 1.0D);
            tessellator.a((128 + byte0), (128 + byte0), 0.0D, 1.0D, 1.0D);
            tessellator.a((128 + byte0), (0 - byte0), 0.0D, 1.0D, 0.0D);
            tessellator.a((0 - byte0), (0 - byte0), 0.0D, 0.0D, 0.0D);
            tessellator.a();
            iu mapdata = ItemType.bb.a(itemstack, this.a.f);
            this.f.a((gs) this.a.h, this.a.p, mapdata);
            GL11.glPopMatrix();
        } else if (itemstack != null) {
            if (itemstack.c != Items.woodenShield.bf && itemstack.c != Items.powerGlove.bf) {
                GL11.glPushMatrix();
                float ft3 = 0.8F;
                float f7 = in.a(swingProgress * 3.141593F);
                float f9 = in.a(in.c(swingProgress) * 3.141593F);
                GL11.glTranslatef(-f9 * 0.4F, in.a(in.c(swingProgress) * 3.141593F * 2.0F) * 0.2F, -f7 * 0.2F);
                GL11.glTranslatef(0.7F * ft3, -0.65F * ft3 - (1.0F - f1) * 0.6F, -0.9F * ft3);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glEnable(32826);
                f7 = in.a(swingProgress * swingProgress * 3.141593F);
                f9 = in.a(in.c(swingProgress) * 3.141593F);
                GL11.glRotatef(-f7 * 20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-f9 * 20.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-f9 * 80.0F, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(0.4F, 0.4F, 0.4F);
                if (itemstack.a().c())
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                a((ls) entityplayersp, itemstack);
                GL11.glPopMatrix();
            } else if (itemstack.c == Items.powerGlove.bf) {
                GL11.glPushMatrix();
                float f4 = 0.8F;
                float f8 = in.a(swingProgress * 3.141593F);
                float f10 = in.a(in.c(swingProgress) * 3.141593F);
                GL11.glTranslatef(-f10 * 0.3F, in.a(in.c(swingProgress) * 3.141593F * 2.0F) * 0.4F, -f8 * 0.4F);
                GL11.glTranslatef(0.8F * f4, -0.75F * f4 - (1.0F - f1) * 0.6F, -0.9F * f4);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glEnable(32826);
                f8 = in.a(swingProgress * swingProgress * 3.141593F);
                f10 = in.a(in.c(swingProgress) * 3.141593F);
                GL11.glRotatef(f10 * 70.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-f8 * 20.0F, 0.0F, 0.0F, 1.0F);
                GL11.glBindTexture(3553, this.a.p.a(this.a.h.bA, this.a.h.q_()));
                GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
                GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef(5.6F, 0.0F, 0.0F);
                bw render = th.a.a((Entity) this.a.h);
                PlayerRenderer renderplayer = (PlayerRenderer) render;
                renderplayer.b();
                GL11.glBindTexture(3553, this.a.p.b("/mob/powerGlove.png"));
                this.refBiped.m = 0.0F;
                this.refBiped.b(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                this.powerGlove.a = this.refBiped.d.a;
                this.powerGlove.b = this.refBiped.d.b;
                this.powerGlove.c = this.refBiped.d.c;
                this.powerGlove.d = this.refBiped.d.d;
                this.powerGlove.e = this.refBiped.d.e;
                this.powerGlove.f = this.refBiped.d.f;
                this.powerGlove.a(0.0625F);
                this.powerGloveRuby.a = this.refBiped.d.a;
                this.powerGloveRuby.b = this.refBiped.d.b;
                this.powerGloveRuby.c = this.refBiped.d.c;
                this.powerGloveRuby.d = this.refBiped.d.d;
                this.powerGloveRuby.e = this.refBiped.d.e;
                this.powerGloveRuby.f = this.refBiped.d.f;
                this.powerGloveRuby.a(0.0625F);
                GL11.glPopMatrix();
            } else {
                renderShield(f, swingProgress, otherHand);
            }
        } else {
            GL11.glPushMatrix();
            float f4 = 0.8F;
            float f8 = in.a(swingProgress * 3.141593F);
            float f10 = in.a(in.c(swingProgress) * 3.141593F);
            GL11.glTranslatef(-f10 * 0.3F, in.a(in.c(swingProgress) * 3.141593F * 2.0F) * 0.4F, -f8 * 0.4F);
            GL11.glTranslatef(0.8F * f4, -0.75F * f4 - (1.0F - f1) * 0.6F, -0.9F * f4);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(32826);
            f8 = in.a(swingProgress * swingProgress * 3.141593F);
            f10 = in.a(in.c(swingProgress) * 3.141593F);
            GL11.glRotatef(f10 * 70.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f8 * 20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glBindTexture(3553, this.a.p.a(this.a.h.bA, this.a.h.q_()));
            GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
            GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(5.6F, 0.0F, 0.0F);
            bw render = th.a.a((Entity) this.a.h);
            PlayerRenderer renderplayer = (PlayerRenderer) render;
            renderplayer.b();
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826);
        u.a();
    }

    public void renderShield(float f, float swingProgress, float otherHand) {
        float f1 = this.d + (this.c - this.d) * f;
        dc entityplayersp = this.a.h;
        float f2 = this.a.f.c(in.b(((Player) entityplayersp).aM), in.b(((Player) entityplayersp).aN), in.b(((Player) entityplayersp).aO));
        GL11.glColor4f(f2, f2, f2, 1.0F);
        ItemInstance itemstack = new ItemInstance(Items.woodenShield);
        GL11.glPushMatrix();
        float f3 = 0.8F;
        if (otherHand == 0.0F) {
            float f7 = in.a(swingProgress * 3.141593F);
            float f9 = in.a(in.c(swingProgress) * 3.141593F);
            GL11.glTranslatef(-f9 * 0.4F, in.a(in.c(swingProgress) * 3.141593F * 2.0F) * 0.2F, -f7 * 0.2F);
            GL11.glTranslatef(1.0F, -0.65F * f3 - (1.0F - f1) * 0.6F, -0.9F * f3);
        } else {
            float f7 = in.a(otherHand * 3.141593F);
            float f9 = in.a(in.c(otherHand) * 3.141593F);
            GL11.glTranslatef(f9 * 0.4F, in.a(in.c(otherHand) * 3.141593F * 2.0F) * 0.2F, -f7 * 0.2F);
            GL11.glTranslatef(1.0F, -0.65F * f3 - (1.0F - f1) * 0.6F, -0.9F * f3);
            GL11.glRotatef(-90.0F * f7, 0.0F, 1.0F, 0.0F);
        }
        GL11.glEnable(32826);
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        this.itemRotate = false;
        a((ls) entityplayersp, itemstack);
        this.itemRotate = true;
        GL11.glPopMatrix();
    }

    public void b(float f) {
        GL11.glDisable(3008);
        if (!this.a.cameraActive && this.a.h.ak()) {
            int i = this.a.p.b("/terrain.png");
            GL11.glBindTexture(3553, i);
            d(f);
        }
        if (this.a.i.L()) {
            int j = in.b(this.a.i.aM);
            int l = in.b(this.a.i.aN);
            int i1 = in.b(this.a.i.aO);
            int j1 = this.a.p.b("/terrain.png");
            GL11.glBindTexture(3553, j1);
            int k1 = this.a.f.a(j, l, i1);
            if (this.a.f.h(j, l, i1) && this.a.f.g(j, l, i1)) {
                a(f, Tile.m[k1].a(2));
            } else {
                for (int l1 = 0; l1 < 8; l1++) {
                    float f1 = (((l1 >> 0) % 2) - 0.5F) * this.a.h.bg * 0.9F;
                    float f2 = (((l1 >> 1) % 2) - 0.5F) * this.a.h.bh * 0.2F;
                    float f3 = (((l1 >> 2) % 2) - 0.5F) * this.a.h.bg * 0.9F;
                    int i2 = in.d(j + f1);
                    int j2 = in.d(l + f2);
                    int k2 = in.d(i1 + f3);
                    if (this.a.f.h(i2, j2, k2))
                        k1 = this.a.f.a(i2, j2, k2);
                }
            }
            if (Tile.m[k1] != null)
                a(f, Tile.m[k1].a(2));
        }
        if (this.a.i.a(ln.g)) {
            int k = this.a.p.b("/misc/water.png");
            GL11.glBindTexture(3553, k);
            c(f);
        }
        GL11.glEnable(3008);
    }

    private void a(float f, int i) {
        if (this.a.h.bq)
            return;
        Tessellator tessellator = Tessellator.a;
        float f1 = this.a.i.a(f);
        f1 = 0.1F;
        GL11.glColor4f(f1, f1, f1, 0.5F);
        GL11.glPushMatrix();
        float f2 = -1.0F;
        float f3 = 1.0F;
        float f4 = -1.0F;
        float f5 = 1.0F;
        float f6 = -0.5F;
        float f7 = 0.0078125F;
        float f8 = (i % 16) / 256.0F - f7;
        float f9 = ((i % 16) + 15.99F) / 256.0F + f7;
        float f10 = (i / 16) / 256.0F - f7;
        float f11 = ((i / 16) + 15.99F) / 256.0F + f7;
        tessellator.b();
        tessellator.a(f2, f4, f6, f9, f11);
        tessellator.a(f3, f4, f6, f8, f11);
        tessellator.a(f3, f5, f6, f8, f10);
        tessellator.a(f2, f5, f6, f9, f10);
        tessellator.a();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void c(float f) {
        Tessellator tessellator = Tessellator.a;
        float f1 = this.a.i.a(f);
        GL11.glColor4f(f1, f1, f1, 0.5F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        float f2 = 4.0F;
        float f3 = -1.0F;
        float f4 = 1.0F;
        float f5 = -1.0F;
        float f6 = 1.0F;
        float f7 = -0.5F;
        float f8 = -this.a.i.aS / 64.0F;
        float f9 = this.a.i.aT / 64.0F;
        tessellator.b();
        tessellator.a(f3, f5, f7, (f2 + f8), (f2 + f9));
        tessellator.a(f4, f5, f7, (0.0F + f8), (f2 + f9));
        tessellator.a(f4, f6, f7, (0.0F + f8), (0.0F + f9));
        tessellator.a(f3, f6, f7, (f2 + f8), (0.0F + f9));
        tessellator.a();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
    }

    private void d(float f) {
        Tessellator tessellator = Tessellator.a;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        float f1 = 1.0F;
        for (int i = 0; i < 2; i++) {
            GL11.glPushMatrix();
            int j = Tile.as.bm + i * 16;
            int k = (j & 0xF) << 4;
            int l = j & 0xF0;
            float f2 = k / 256.0F;
            float f3 = (k + 15.99F) / 256.0F;
            float f4 = l / 256.0F;
            float f5 = (l + 15.99F) / 256.0F;
            float f6 = (0.0F - f1) / 2.0F;
            float f7 = f6 + f1;
            float f8 = 0.0F - f1 / 2.0F;
            float f9 = f8 + f1;
            float f10 = -0.5F;
            GL11.glTranslatef(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
            GL11.glRotatef((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            tessellator.b();
            tessellator.a(f6, f8, f10, f3, f5);
            tessellator.a(f7, f8, f10, f2, f5);
            tessellator.a(f7, f9, f10, f2, f4);
            tessellator.a(f6, f9, f10, f3, f4);
            tessellator.a();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(3042);
    }

    public void a() {
        this.d = this.c;
        dc entityplayersp = this.a.h;
        ItemInstance itemstack = ((Player) entityplayersp).c.b();
        ItemInstance itemstack1 = itemstack;
        boolean flag = (this.g == ((Player) entityplayersp).c.c && itemstack1 == this.b);
        if (this.b == null && itemstack1 == null)
            flag = true;
        if (itemstack1 != null && this.b != null && itemstack1 != this.b && itemstack1.c == this.b.c && itemstack1.i() == this.b.i()) {
            this.b = itemstack1;
            flag = true;
        }
        float f = 0.4F;
        float f1 = flag ? 1.0F : 0.0F;
        float f2 = f1 - this.c;
        if (f2 < -f)
            f2 = -f;
        if (f2 > f)
            f2 = f;
        this.c += f2;
        if (this.c < 0.1F) {
            this.b = itemstack1;
            this.g = ((Player) entityplayersp).c.c;
        }
    }

    public void b() {
        this.c = 0.0F;
    }

    public void c() {
        this.c = 0.0F;
    }

    public boolean hasItem() {
        return (this.b != null);
    }
}
