package io.github.ryuu.adventurecraft.models;

import net.minecraft.client.render.entity.model.EntityModel;

public class ModelCamera extends EntityModel {

    public MixinModelPart head = new MixinModelPart(0, 0);

    public ModelCamera() {
        this.head.addCuboid(-4.0f, -4.0f, -4.0f, 8, 8, 8, 0.0f);
        this.head.setPivot(0.0f, 24.0f, 0.0f);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.pitch = f4 / 57.29578f;
        this.head.yaw = f3 / 57.29578f;
        this.head.render(f5);
    }
}
