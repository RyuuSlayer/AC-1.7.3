package io.github.ryuu.adventurecraft.overrides;

import org.lwjgl.opengl.GL11;

public class gl extends xw {
    private int a;

    private int o;

    private ji p;

    public gl(ji renderengine, fd world, double d, double d1, double d2) {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        this.a = 0;
        this.o = 0;
        this.p = renderengine;
        this.aP = this.aQ = this.aR = 0.0D;
        this.o = 200;
    }

    public void a(nw tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = (this.a + f) / this.o;
        f6 *= f6;
        float f7 = 2.0F - f6 * 2.0F;
        if (f7 > 1.0F)
            f7 = 1.0F;
        f7 *= 0.2F;
        GL11.glDisable(2896);
        float f8 = 0.125F;
        float f9 = (float)(this.aM - l);
        float f10 = (float)(this.aN - m);
        float f11 = (float)(this.aO - n);
        float f12 = this.aI.c(in.b(this.aM), in.b(this.aN), in.b(this.aO));
        this.p.b(this.p.b("/misc/footprint.png"));
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        tessellator.b();
        tessellator.a(f12, f12, f12, f7);
        tessellator.a((f9 - f8), f10, (f11 + f8), 0.0D, 1.0D);
        tessellator.a((f9 + f8), f10, (f11 + f8), 1.0D, 1.0D);
        tessellator.a((f9 + f8), f10, (f11 - f8), 1.0D, 0.0D);
        tessellator.a((f9 - f8), f10, (f11 - f8), 0.0D, 0.0D);
        tessellator.a();
        GL11.glDisable(3042);
        GL11.glEnable(2896);
    }

    public void w_() {
        this.a++;
        if (this.a == this.o)
            K();
    }

    public int c_() {
        return 5;
    }
}
