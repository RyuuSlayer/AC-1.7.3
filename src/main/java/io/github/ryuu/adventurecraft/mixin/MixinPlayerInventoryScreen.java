package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.client.gui.screen.container.ContainerScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import org.lwjgl.opengl.GL11;

public class MixinPlayerInventoryScreen extends ContainerScreen {
    private float l;

    private float m;

    public MixinPlayerInventoryScreen(Player entityplayer) {
        super(entityplayer.d);
        this.f = true;
        entityplayer.a((vr) ep.f, 1);
    }

    public void b() {
        this.e.clear();
    }

    protected void k() {
    }

    public void a(int i, int j, float f) {
        super.a(i, j, f);
        this.l = i;
        this.m = j;
    }

    protected void a(float f) {
        int i = this.b.p.b("/gui/inventory.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.b.p.b(i);
        int j = (this.c - this.a) / 2;
        int k = (this.d - this.i) / 2;
        b(j, k, 0, 0, this.a, this.i);
        i = this.b.p.b("/gui/heartPiece.png");
        this.b.p.b(i);
        b(j + 89, k + 6, this.b.h.numHeartPieces * 32, 0, 32, 32);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((j + 51), (k + 75), 50.0F);
        float f1 = 30.0F;
        GL11.glScalef(-f1, f1, f1);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = this.b.h.H;
        float f3 = this.b.h.aS;
        float f4 = this.b.h.aT;
        float f5 = (j + 51) - this.l;
        float f6 = (k + 75 - 50) - this.m;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        u.b();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((f6 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        this.b.h.H = (float) Math.atan((f5 / 40.0F)) * 20.0F;
        this.b.h.aS = (float) Math.atan((f5 / 40.0F)) * 40.0F;
        this.b.h.aT = -((float) Math.atan((f6 / 40.0F))) * 20.0F;
        this.b.h.bE = 1.0F;
        GL11.glTranslatef(0.0F, this.b.h.bf, 0.0F);
        th.a.i = 180.0F;
        th.a.a((Entity) this.b.h, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        this.b.h.bE = 0.0F;
        this.b.h.H = f2;
        this.b.h.aS = f3;
        this.b.h.aT = f4;
        GL11.glPopMatrix();
        u.a();
        GL11.glDisable(32826);
    }

    protected void a(Button guibutton) {
        if (guibutton.f == 0)
            this.b.a((da) new xm(this.b.I));
        if (guibutton.f == 1)
            this.b.a((da) new dv((da) this, this.b.I));
    }
}
