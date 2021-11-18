package io.github.ryuu.adventurecraft.models;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import org.lwjgl.opengl.GL11;

public class ModelRat extends EntityModel {
    public ModelPart snout;
    public ModelPart theHead;
    public ModelPart leftEar;
    public ModelPart rightEar;
    public ModelPart theBody;
    public ModelPart tail;

    public ModelRat() {
        float offset = 20.0F;
        this.theHead = new ModelPart(0, 0);
        this.theHead.addCuboid(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.theHead.setPivot(0.0F, offset, -4.0F);
        this.snout = new ModelPart(0, 6);
        this.snout.addCuboid(-1.5F, 1.0F, -5.0F, 3, 2, 2, 0.0F);
        this.snout.setPivot(0.0F, offset, -4.0F);
        this.leftEar = new ModelPart(10, 6);
        this.leftEar.addCuboid(-1.5F, -1.0F, -2.0F, 0, 1, 1, 0.0F);
        this.leftEar.setPivot(0.0F, offset, -4.0F);
        this.rightEar = new ModelPart(12, 6);
        this.rightEar.addCuboid(1.5F, -1.0F, -2.0F, 0, 1, 1, 0.0F);
        this.rightEar.setPivot(0.0F, offset, -4.0F);
        this.theBody = new ModelPart(0, 10);
        this.theBody.addCuboid(-2.0F, -0.5F, -4.0F, 4, 4, 8, 0.0F);
        this.theBody.setPivot(0.0F, 0.0F + offset, 0.0F);
        this.tail = new ModelPart(16, 0);
        this.tail.addCuboid(-0.5F, 0.0F, 0.0F, 1, 1, 7, 0.0F);
        this.tail.setPivot(0.0F, 2.0F + offset, 3.5F);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glEnable(2884);
        setAngles(f, f1, f2, f3, f4, f5);
        this.theHead.render(f5);
        this.snout.render(f5);
        this.leftEar.render(f5);
        this.rightEar.render(f5);
        this.theBody.render(f5);
        this.tail.render(f5);
    }

    @Override
    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.theHead.pitch = -(f4 / 57.29578F);
        this.theHead.yaw = f3 / 57.29578F;
        this.leftEar.pitch = this.theHead.pitch;
        this.leftEar.yaw = this.theHead.yaw;
        this.rightEar.pitch = this.theHead.pitch;
        this.rightEar.yaw = this.theHead.yaw;
        this.snout.pitch = this.theHead.pitch;
        this.snout.yaw = this.theHead.yaw;
        this.tail.yaw = -this.theHead.yaw;
    }
}