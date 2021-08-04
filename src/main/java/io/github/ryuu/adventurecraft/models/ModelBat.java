package io.github.ryuu.adventurecraft.models;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import org.lwjgl.opengl.GL11;

public class ModelBat extends EntityModel {
    public ModelPart theHead;

    public ModelPart ears;

    public ModelPart rightWing;

    public ModelPart theBody;

    public ModelPart leftWing;

    public ModelBat() {
        float offset = 16.0F;
        this.theHead = new ModelPart(0, 0);
        this.theHead.addCuboid(-1.5F, -3.0F, -1.5F, 3, 3, 3, 0.0F);
        this.theHead.setPivot(0.0F, -4.0F + offset, 0.0F);
        this.ears = new ModelPart(12, 10);
        this.ears.addCuboid(-1.5F, -4.0F, 0.0F, 3, 1, 0, 0.0F);
        this.ears.setPivot(0.0F, -4.0F + offset, 0.0F);
        this.theBody = new ModelPart(0, 6);
        this.theBody.addCuboid(-1.5F, -4.0F, -1.5F, 3, 5, 3, 0.0F);
        this.theBody.setPivot(0.0F, 0.0F + offset, 0.0F);
        this.leftWing = new ModelPart(12, 0);
        this.leftWing.addCuboid(0.0F, -4.0F, 0.0F, 7, 5, 0, 0.0F);
        this.leftWing.setPivot(1.5F, 0.0F + offset, 0.0F);
        this.rightWing = new ModelPart(12, 5);
        this.rightWing.addCuboid(-7.0F, -4.0F, 0.0F, 7, 5, 0, 0.0F);
        this.rightWing.setPivot(-1.5F, 0.0F + offset, 0.0F);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glEnable(2884);
        setAngles(f, f1, f2, f3, f4, f5);
        this.theHead.render(f5);
        this.ears.render(f5);
        this.theBody.render(f5);
        this.leftWing.render(f5);
        this.rightWing.render(f5);
    }

    @Override
    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.theHead.pitch = -(f4 / 57.29578F);
        this.theHead.yaw = f3 / 57.29578F;
        this.ears.pitch = this.theHead.pitch;
        this.ears.yaw = this.theHead.yaw;
        double t = (System.currentTimeMillis() % 500L) / 500.0D;
        this.leftWing.yaw = 0.3F * (float) Math.cos(2.0D * t * Math.PI);
        this.rightWing.yaw = -0.3F * (float) Math.cos(2.0D * t * Math.PI);
    }
}
