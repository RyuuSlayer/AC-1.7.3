package io.github.ryuu.adventurecraft.models;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelCamera extends EntityModel {
    public ModelPart head;

    public ModelCamera() {
        this.head = new ModelPart(0, 0);
        this.head.a(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.head.a(0.0F, 24.0F, 0.0F);
    }

    public void a(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.d = f4 / 57.29578F;
        this.head.e = f3 / 57.29578F;
        this.head.a(f5);
    }
}
