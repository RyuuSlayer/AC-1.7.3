package io.github.ryuu.adventurecraft.models;

import org.lwjgl.opengl.GL11;

public class ModelBat extends ko {
    public ps theHead;

    public ps ears;

    public ps rightWing;

    public ps theBody;

    public ps leftWing;

    public ModelBat() {
        float offset = 16.0F;
        this.theHead = new ps(0, 0);
        this.theHead.a(-1.5F, -3.0F, -1.5F, 3, 3, 3, 0.0F);
        this.theHead.a(0.0F, -4.0F + offset, 0.0F);
        this.ears = new ps(12, 10);
        this.ears.a(-1.5F, -4.0F, 0.0F, 3, 1, 0, 0.0F);
        this.ears.a(0.0F, -4.0F + offset, 0.0F);
        this.theBody = new ps(0, 6);
        this.theBody.a(-1.5F, -4.0F, -1.5F, 3, 5, 3, 0.0F);
        this.theBody.a(0.0F, 0.0F + offset, 0.0F);
        this.leftWing = new ps(12, 0);
        this.leftWing.a(0.0F, -4.0F, 0.0F, 7, 5, 0, 0.0F);
        this.leftWing.a(1.5F, 0.0F + offset, 0.0F);
        this.rightWing = new ps(12, 5);
        this.rightWing.a(-7.0F, -4.0F, 0.0F, 7, 5, 0, 0.0F);
        this.rightWing.a(-1.5F, 0.0F + offset, 0.0F);
    }

    public void a(float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glEnable(2884);
        b(f, f1, f2, f3, f4, f5);
        this.theHead.a(f5);
        this.ears.a(f5);
        this.theBody.a(f5);
        this.leftWing.a(f5);
        this.rightWing.a(f5);
    }

    public void b(float f, float f1, float f2, float f3, float f4, float f5) {
        this.theHead.d = -(f4 / 57.29578F);
        this.theHead.e = f3 / 57.29578F;
        this.ears.d = this.theHead.d;
        this.ears.e = this.theHead.e;
        double t = (System.currentTimeMillis() % 500L) / 500.0D;
        this.leftWing.e = 0.3F * (float)Math.cos(2.0D * t * Math.PI);
        this.rightWing.e = -0.3F * (float)Math.cos(2.0D * t * Math.PI);
    }
}
