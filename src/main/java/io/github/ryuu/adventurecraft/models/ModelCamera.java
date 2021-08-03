package io.github.ryuu.adventurecraft.models;

public class ModelCamera extends ko {
    public ps head;

    public ModelCamera() {
        this.head = new ps(0, 0);
        this.head.a(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.head.a(0.0F, 24.0F, 0.0F);
    }

    public void a(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.d = f4 / 57.29578F;
        this.head.e = f3 / 57.29578F;
        this.head.a(f5);
    }
}
