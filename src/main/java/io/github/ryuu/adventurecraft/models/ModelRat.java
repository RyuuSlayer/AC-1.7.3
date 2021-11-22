package io.github.ryuu.adventurecraft.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
        float offset = 20.0f;
        this.theHead = new ModelPart(0, 0);
        this.theHead.addCuboid(-1.5f, 0.0f, -3.0f, 3, 3, 3, 0.0f);
        this.theHead.setPivot(0.0f, offset, -4.0f);
        this.snout = new ModelPart(0, 6);
        this.snout.addCuboid(-1.5f, 1.0f, -5.0f, 3, 2, 2, 0.0f);
        this.snout.setPivot(0.0f, offset, -4.0f);
        this.leftEar = new ModelPart(10, 6);
        this.leftEar.addCuboid(-1.5f, -1.0f, -2.0f, 0, 1, 1, 0.0f);
        this.leftEar.setPivot(0.0f, offset, -4.0f);
        this.rightEar = new ModelPart(12, 6);
        this.rightEar.addCuboid(1.5f, -1.0f, -2.0f, 0, 1, 1, 0.0f);
        this.rightEar.setPivot(0.0f, offset, -4.0f);
        this.theBody = new ModelPart(0, 10);
        this.theBody.addCuboid(-2.0f, -0.5f, -4.0f, 4, 4, 8, 0.0f);
        this.theBody.setPivot(0.0f, 0.0f + offset, 0.0f);
        this.tail = new ModelPart(16, 0);
        this.tail.addCuboid(-0.5f, 0.0f, 0.0f, 1, 1, 7, 0.0f);
        this.tail.setPivot(0.0f, 2.0f + offset, 3.5f);
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glEnable((int) 2884);
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.theHead.render(f5);
        this.snout.render(f5);
        this.leftEar.render(f5);
        this.rightEar.render(f5);
        this.theBody.render(f5);
        this.tail.render(f5);
    }

    @Override
    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.theHead.pitch = -(f4 / 57.29578f);
        this.theHead.yaw = f3 / 57.29578f;
        this.leftEar.pitch = this.theHead.pitch;
        this.leftEar.yaw = this.theHead.yaw;
        this.rightEar.pitch = this.theHead.pitch;
        this.rightEar.yaw = this.theHead.yaw;
        this.snout.pitch = this.theHead.pitch;
        this.snout.yaw = this.theHead.yaw;
        this.tail.yaw = -this.theHead.yaw;
    }
}
