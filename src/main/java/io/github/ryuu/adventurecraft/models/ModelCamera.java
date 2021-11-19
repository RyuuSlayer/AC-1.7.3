package io.github.ryuu.adventurecraft.models;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelCamera extends EntityModel {
    public ModelPart head;

    public ModelCamera() {
        this.head = new ModelPart(0, 0);
        this.head.addCuboid(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.head.setPivot(0.0F, 24.0F, 0.0F);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.pitch = f4 / 57.29578F;
        this.head.yaw = f3 / 57.29578F;
        this.head.render(f5);
    }
}
