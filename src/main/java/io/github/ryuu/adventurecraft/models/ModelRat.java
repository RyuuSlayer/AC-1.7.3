package io.github.ryuu.adventurecraft.models;

import org.lwjgl.opengl.GL11;

public class ModelRat extends ko {
    public ps snout;

    public ps theHead;

    public ps leftEar;

    public ps rightEar;

    public ps theBody;

    public ps tail;

    public ModelRat() {
        float offset = 20.0F;
        this.theHead = new ps(0, 0);
        this.theHead.a(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.theHead.a(0.0F, offset, -4.0F);
        this.snout = new ps(0, 6);
        this.snout.a(-1.5F, 1.0F, -5.0F, 3, 2, 2, 0.0F);
        this.snout.a(0.0F, offset, -4.0F);
        this.leftEar = new ps(10, 6);
        this.leftEar.a(-1.5F, -1.0F, -2.0F, 0, 1, 1, 0.0F);
        this.leftEar.a(0.0F, offset, -4.0F);
        this.rightEar = new ps(12, 6);
        this.rightEar.a(1.5F, -1.0F, -2.0F, 0, 1, 1, 0.0F);
        this.rightEar.a(0.0F, offset, -4.0F);
        this.theBody = new ps(0, 10);
        this.theBody.a(-2.0F, -0.5F, -4.0F, 4, 4, 8, 0.0F);
        this.theBody.a(0.0F, 0.0F + offset, 0.0F);
        this.tail = new ps(16, 0);
        this.tail.a(-0.5F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
        this.tail.a(0.0F, 2.0F + offset, 3.5F);
    }

    public void a(float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glEnable(2884);
        b(f, f1, f2, f3, f4, f5);
        this.theHead.a(f5);
        this.snout.a(f5);
        this.leftEar.a(f5);
        this.rightEar.a(f5);
        this.theBody.a(f5);
        this.tail.a(f5);
    }

    public void b(float f, float f1, float f2, float f3, float f4, float f5) {
        this.theHead.d = -(f4 / 57.29578F);
        this.theHead.e = f3 / 57.29578F;
        this.leftEar.d = this.theHead.d;
        this.leftEar.e = this.theHead.e;
        this.rightEar.d = this.theHead.d;
        this.rightEar.e = this.theHead.e;
        this.snout.d = this.theHead.d;
        this.snout.e = this.theHead.e;
        this.tail.e = -this.theHead.e;
    }
}