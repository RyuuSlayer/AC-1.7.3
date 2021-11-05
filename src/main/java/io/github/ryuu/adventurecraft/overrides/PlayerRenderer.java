package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import org.lwjgl.opengl.GL11;

public class PlayerRenderer extends LivingEntityRenderer {
    private static final String[] i = new String[]{"cloth", "chain", "iron", "diamond", "gold"};
    private final BipedModel a;
    private final BipedModel g;
    private final BipedModel h;

    public PlayerRenderer() {
        super(new BipedModel(0.0F), 0.5F);
        this.a = (BipedModel) this.e;
        this.g = new BipedModel(1.0F);
        this.h = new BipedModel(0.5F);
    }

    protected boolean a(Player entityplayer, int i, float f) {
        ItemInstance itemstack = entityplayer.c.d(3 - i);
        if (itemstack != null) {
            ItemType item = itemstack.a();
            if (item instanceof ArmourItem) {
                ArmourItem itemarmor = (ArmourItem) item;
                a("/armor/" + PlayerRenderer.i[itemarmor.bm] + "_" + ((i != 2) ? 1 : 2) + ".png");
                BipedModel modelbiped = (i != 2) ? this.g : this.h;
                modelbiped.a.h = (i == 0);
                modelbiped.b.h = (i == 0);
                modelbiped.c.h = (i == 1 || i == 2);
                modelbiped.d.h = (i == 1);
                modelbiped.e.h = (i == 1);
                modelbiped.f.h = (i == 2 || i == 3);
                modelbiped.g.h = (i == 2 || i == 3);
                a((EntityModel) modelbiped);
                return true;
            }
        }
        return false;
    }

    public void a(Player entityplayer, double d, double d1, double d2, float f, float f1) {
        ItemInstance itemstack = entityplayer.c.b();
        this.a.k = (itemstack != null);
        this.a.l = entityplayer.t();
        double d3 = d1 - entityplayer.bf;
        if (entityplayer.t() && !(entityplayer instanceof dc))
            d3 -= 0.125D;
        super.a(entityplayer, d, d3, d2, f, f1);
        this.a.l = false;
        this.a.k = false;
    }

    protected void a(Player entityplayer, double d, double d1, double d2) {
        if (Minecraft.t() && entityplayer != this.b.h) {
            float f = 1.6F;
            float f1 = 0.01666667F * f;
            float f2 = entityplayer.f(this.b.h);
            float f3 = entityplayer.t() ? 32.0F : 64.0F;
            if (f2 < f3) {
                String s = entityplayer.l;
                if (!entityplayer.t()) {
                    if (entityplayer.N()) {
                        a(entityplayer, s, d, d1 - 1.5D, d2, 64);
                    } else {
                        a(entityplayer, s, d, d1, d2, 64);
                    }
                } else {
                    TextRenderer fontrenderer = a();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) d + 0.0F, (float) d1 + 2.3F, (float) d2);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-this.b.i, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(this.b.j, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(-f1, -f1, f1);
                    GL11.glDisable(2896);
                    GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
                    GL11.glDepthMask(false);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    Tessellator tessellator = Tessellator.a;
                    GL11.glDisable(3553);
                    tessellator.b();
                    int i = fontrenderer.a(s) / 2;
                    tessellator.a(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.a((-i - 1), -1.0D, 0.0D);
                    tessellator.a((-i - 1), 8.0D, 0.0D);
                    tessellator.a((i + 1), 8.0D, 0.0D);
                    tessellator.a((i + 1), -1.0D, 0.0D);
                    tessellator.a();
                    GL11.glEnable(3553);
                    GL11.glDepthMask(true);
                    fontrenderer.b(s, -fontrenderer.a(s) / 2, 0, 553648127);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    protected void a(Player entityplayer, float f) {
        ItemInstance itemstack = entityplayer.c.d(3);
        if (itemstack != null && (itemstack.a()).bf < 256) {
            GL11.glPushMatrix();
            this.a.a.c(0.0625F);
            if (cv.a(Tile.m[itemstack.c].b())) {
                float f1 = 0.625F;
                GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
            }
            this.b.f.a(entityplayer, itemstack);
            GL11.glPopMatrix();
        }
        if (entityplayer.l.equals("deadmau5") && a(entityplayer.bA, (String) null))
            for (int i = 0; i < 2; i++) {
                float f2 = entityplayer.aU + (entityplayer.aS - entityplayer.aU) * f - entityplayer.I + (entityplayer.H - entityplayer.I) * f;
                float f6 = entityplayer.aV + (entityplayer.aT - entityplayer.aV) * f;
                GL11.glPushMatrix();
                GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f6, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.375F * (i * 2 - 1), 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                GL11.glRotatef(-f6, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
                float f7 = 1.333333F;
                GL11.glScalef(f7, f7, f7);
                this.a.a(0.0625F);
                GL11.glPopMatrix();
            }
        if (entityplayer.cloakTexture != null || a(entityplayer.n, (String) null)) {
            if (entityplayer.cloakTexture != null)
                a(entityplayer.cloakTexture);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double d = entityplayer.o + (entityplayer.r - entityplayer.o) * f - entityplayer.aJ + (entityplayer.aM - entityplayer.aJ) * f;
            double d1 = entityplayer.p + (entityplayer.s - entityplayer.p) * f - entityplayer.aK + (entityplayer.aN - entityplayer.aK) * f;
            double d2 = entityplayer.q + (entityplayer.t - entityplayer.q) * f - entityplayer.aL + (entityplayer.aO - entityplayer.aL) * f;
            float f8 = entityplayer.I + (entityplayer.H - entityplayer.I) * f;
            double d3 = MathsHelper.a(f8 * 3.141593F / 180.0F);
            double d4 = -MathsHelper.b(f8 * 3.141593F / 180.0F);
            float f9 = (float) d1 * 10.0F;
            if (f9 < -6.0F)
                f9 = -6.0F;
            if (f9 > 32.0F)
                f9 = 32.0F;
            float f10 = (float) (d * d3 + d2 * d4) * 100.0F;
            float f11 = (float) (d * d4 - d2 * d3) * 100.0F;
            if (f10 < 0.0F)
                f10 = 0.0F;
            float f12 = entityplayer.h + (entityplayer.i - entityplayer.h) * f;
            f9 += MathsHelper.a((entityplayer.bi + (entityplayer.bj - entityplayer.bi) * f) * 6.0F) * 32.0F * f12;
            if (entityplayer.t())
                f9 += 25.0F;
            GL11.glRotatef(6.0F + f10 / 2.0F + f9, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f11 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f11 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.a.b(0.0625F);
            GL11.glPopMatrix();
        }
        ItemInstance itemstack1 = entityplayer.c.b();
        if (itemstack1 != null) {
            GL11.glPushMatrix();
            this.a.d.c(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            if (entityplayer.D != null)
                itemstack1 = new ItemInstance(ItemType.B);
            if (itemstack1.c < 256 && cv.a(Tile.m[itemstack1.c].b())) {
                float f3 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f3 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f3, -f3, f3);
            } else if (ItemType.c[itemstack1.c].b()) {
                float f4 = 0.625F;
                if (ItemType.c[itemstack1.c].c()) {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }
                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f4, -f4, f4);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                float f5 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f5, f5, f5);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }
            this.b.f.a(entityplayer, itemstack1);
            GL11.glPopMatrix();
        }
    }

    protected void b(Player entityplayer, float f) {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }

    public void b() {
        this.a.m = 0.0F;
        this.a.b(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        this.a.d.a(0.0625F);
    }

    protected void b(Player entityplayer, double d, double d1, double d2) {
        if (entityplayer.W() && entityplayer.N()) {
            super.b(entityplayer, d + entityplayer.w, d1 + entityplayer.x, d2 + entityplayer.y);
        } else {
            super.b(entityplayer, d, d1, d2);
        }
    }

    protected void a(Player entityplayer, float f, float f1, float f2) {
        if (entityplayer.W() && entityplayer.N()) {
            GL11.glRotatef(entityplayer.M(), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(a(entityplayer), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        } else {
            super.a(entityplayer, f, f1, f2);
        }
    }

    protected void a(LivingEntity entityliving, double d, double d1, double d2) {
        a((Player) entityliving, d, d1, d2);
    }

    protected void a(LivingEntity entityliving, float f) {
        b((Player) entityliving, f);
    }

    protected boolean a(LivingEntity entityliving, int i, float f) {
        return a((Player) entityliving, i, f);
    }

    protected void b(LivingEntity entityliving, float f) {
        a((Player) entityliving, f);
    }

    protected void a(LivingEntity entityliving, float f, float f1, float f2) {
        a((Player) entityliving, f, f1, f2);
    }

    protected void b(LivingEntity entityliving, double d, double d1, double d2) {
        b((Player) entityliving, d, d1, d2);
    }

    public void a(LivingEntity entityliving, double d, double d1, double d2, float f, float f1) {
        a((Player) entityliving, d, d1, d2, f, f1);
    }

    public void a(Entity entity, double d, double d1, double d2, float f, float f1) {
        a((Player) entity, d, d1, d2, f, f1);
    }
}