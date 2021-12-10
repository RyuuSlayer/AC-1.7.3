package io.github.ryuu.adventurecraft.mixin.client.model;

import io.github.ryuu.adventurecraft.extensions.ExClass_552;
import io.github.ryuu.adventurecraft.extensions.client.ExModel;
import net.minecraft.class_290;
import net.minecraft.class_552;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ModelPart.class)
public abstract class MixinModelPart implements ExModel {
    @Shadow
    private int xTexOffset;

    @Shadow
    private int yTexOffset;

    @Shadow
    private final boolean compiled = false;

    @Shadow
    private int list;

    @Shadow
    public float pivotX;

    @Shadow
    public float pivotY;

    @Shadow
    public float pivotZ;

    @Shadow
    public float pitch;

    @Shadow
    public float yaw;

    @Shadow
    public float roll;

    @Shadow
    public boolean mirror;

    @Shadow
    public boolean visible;

    @Shadow
    public boolean hidden;

    @Shadow
    private class_290[] vertices;

    @Shadow
    private class_552[] field_2302;

    @Shadow protected abstract void compile(float f);

    private int tWidth;
    private int tHeight;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ModelPart(int j, int par2, CallbackInfo ci) {
        this.tWidth = 64;
        this.tHeight = 32;
    }

    @ModifyArgs(method = "addCuboid(FFFIIIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_552;<init>([Lnet/minecraft/class_290;IIII)V", ordinal = 0))
    private void changeField_2302(Args args, float f, float f1, float f2, int i, int j, int k, float f3) {
        args.set(1, this.xTexOffset + k + i);
        args.set(2, this.yTexOffset + k);
        args.set(3, this.xTexOffset + k + i + k);
        args.set(4, this.yTexOffset + k + j);
    }

    @Inject(method = "addCuboid(FFFIIIF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPart;mirror:Z", ordinal = 1))
    private void addVertexChanger(float f, float f1, float f2, int i, int j, int k, float f3, CallbackInfo ci) {
        ((ExClass_552) this.field_2302[0]).setVertexes(this.xTexOffset + k + i, this.yTexOffset + k, this.xTexOffset + k + i + k, this.yTexOffset + k + j, this.tWidth, this.tHeight);
        ((ExClass_552) this.field_2302[1]).setVertexes(this.xTexOffset, this.yTexOffset + k, this.xTexOffset + k, this.yTexOffset + k + j, this.tWidth, this.tHeight);
        ((ExClass_552) this.field_2302[2]).setVertexes(this.xTexOffset + k, this.yTexOffset, this.xTexOffset + k + i, this.yTexOffset + k, this.tWidth, this.tHeight);
        ((ExClass_552) this.field_2302[3]).setVertexes(this.xTexOffset + k + i, this.yTexOffset, this.xTexOffset + k + i + i, this.yTexOffset + k, this.tWidth, this.tHeight);
        ((ExClass_552) this.field_2302[4]).setVertexes(this.xTexOffset + k, this.yTexOffset + k, this.xTexOffset + k + i, this.yTexOffset + k + j, this.tWidth, this.tHeight);
        ((ExClass_552) this.field_2302[5]).setVertexes(this.xTexOffset + k + i + k, this.yTexOffset + k, this.xTexOffset + k + i + k + i, this.yTexOffset + k + j, this.tWidth, this.tHeight);
    }

    @ModifyArgs(method = "addCuboid(FFFIIIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_552;<init>([Lnet/minecraft/class_290;IIII)V", ordinal = 1))
    private void changeField_2302_1(Args args, float f, float f1, float f2, int i, int j, int k, float f3) {
        args.set(1, this.xTexOffset);
        args.set(2, this.yTexOffset + k);
        args.set(3, this.xTexOffset + k);
        args.set(4, this.yTexOffset + k + j);
    }

    @ModifyArgs(method = "addCuboid(FFFIIIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_552;<init>([Lnet/minecraft/class_290;IIII)V", ordinal = 2))
    private void changeField_2302_2(Args args, float f, float f1, float f2, int i, int j, int k, float f3) {
        args.set(1, this.xTexOffset + k);
        args.set(2, this.yTexOffset);
        args.set(3, this.xTexOffset + k + i);
        args.set(4, this.yTexOffset + k);
    }


    @ModifyArgs(method = "addCuboid(FFFIIIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_552;<init>([Lnet/minecraft/class_290;IIII)V", ordinal = 3))
    private void changeField_2302_3(Args args, float f, float f1, float f2, int i, int j, int k, float f3) {
        args.set(1, this.xTexOffset + k + i);
        args.set(2, this.yTexOffset);
        args.set(3, this.xTexOffset + k + i + i);
        args.set(4, this.yTexOffset + k);
    }

    @ModifyArgs(method = "addCuboid(FFFIIIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_552;<init>([Lnet/minecraft/class_290;IIII)V", ordinal = 4))
    private void changeField_2302_4(Args args, float f, float f1, float f2, int i, int j, int k, float f3) {
        args.set(1, this.xTexOffset + k);
        args.set(2, this.yTexOffset + k);
        args.set(3, this.xTexOffset + k + i);
        args.set(4, this.yTexOffset + k + j);
    }

    @ModifyArgs(method = "addCuboid(FFFIIIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_552;<init>([Lnet/minecraft/class_290;IIII)V", ordinal = 5))
    private void changeField_2302_5(Args args, float f, float f1, float f2, int i, int j, int k, float f3) {
        args.set(1, this.xTexOffset + k + i + k);
        args.set(2, this.yTexOffset + k);
        args.set(3, this.xTexOffset + k + i + k + i);
        args.set(4, this.yTexOffset + k + j);
    }


    @Override
    public void addBoxInverted(float f, float f1, float f2, int i, int j, int k, float f3) {
        this.vertices = new class_290[8];
        this.field_2302 = new class_552[6];
        float f4 = f + (float) i;
        float f5 = f1 + (float) j;
        float f6 = f2 + (float) k;
        f -= f3;
        f1 -= f3;
        f2 -= f3;
        f4 += f3;
        f5 += f3;
        f6 += f3;
        if (this.mirror) {
            float f7 = f4;
            f4 = f;
            f = f7;
        }
        class_290 positiontexturevertex = new class_290(f, f1, f2, 0.0f, 0.0f);
        class_290 positiontexturevertex1 = new class_290(f4, f1, f2, 0.0f, 8.0f);
        class_290 positiontexturevertex2 = new class_290(f4, f5, f2, 8.0f, 8.0f);
        class_290 positiontexturevertex3 = new class_290(f, f5, f2, 8.0f, 0.0f);
        class_290 positiontexturevertex4 = new class_290(f, f1, f6, 0.0f, 0.0f);
        class_290 positiontexturevertex5 = new class_290(f4, f1, f6, 0.0f, 8.0f);
        class_290 positiontexturevertex6 = new class_290(f4, f5, f6, 8.0f, 8.0f);
        class_290 positiontexturevertex7 = new class_290(f, f5, f6, 8.0f, 0.0f);
        this.vertices[0] = positiontexturevertex;
        this.vertices[1] = positiontexturevertex1;
        this.vertices[2] = positiontexturevertex2;
        this.vertices[3] = positiontexturevertex3;
        this.vertices[4] = positiontexturevertex4;
        this.vertices[5] = positiontexturevertex5;
        this.vertices[6] = positiontexturevertex6;
        this.vertices[7] = positiontexturevertex7;
        this.field_2302[0] = new class_552(new class_290[]{positiontexturevertex5, positiontexturevertex1, positiontexturevertex2, positiontexturevertex6}, this.xTexOffset + k + i + k, this.yTexOffset + k + j, this.xTexOffset + k + i, this.yTexOffset + k);
        ((ExClass_552) this.field_2302[0]).setVertexes(this.xTexOffset + k + i + k, this.yTexOffset + k + j, this.xTexOffset + k + i, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[1] = new class_552(new class_290[]{positiontexturevertex, positiontexturevertex4, positiontexturevertex7, positiontexturevertex3}, this.xTexOffset + k, this.yTexOffset + k + j, this.xTexOffset, this.yTexOffset + k);
        ((ExClass_552) this.field_2302[1]).setVertexes(this.xTexOffset + k, this.yTexOffset + k + j, this.xTexOffset, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[2] = new class_552(new class_290[]{positiontexturevertex5, positiontexturevertex4, positiontexturevertex, positiontexturevertex1}, this.xTexOffset + k + i + i, this.yTexOffset, this.xTexOffset + k + i, this.yTexOffset + k);
        ((ExClass_552) this.field_2302[2]).setVertexes(this.xTexOffset + k + i + i, this.yTexOffset, this.xTexOffset + k + i, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[3] = new class_552(new class_290[]{positiontexturevertex2, positiontexturevertex3, positiontexturevertex7, positiontexturevertex6}, this.xTexOffset + k + i, this.yTexOffset, this.xTexOffset + k, this.yTexOffset + k);
        ((ExClass_552) this.field_2302[3]).setVertexes(this.xTexOffset + k + i, this.yTexOffset, this.xTexOffset + k, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[4] = new class_552(new class_290[]{positiontexturevertex1, positiontexturevertex, positiontexturevertex3, positiontexturevertex2}, this.xTexOffset + k + i + k + i, this.yTexOffset + k + j, this.xTexOffset + k + i + k, this.yTexOffset + k);
        ((ExClass_552) this.field_2302[4]).setVertexes(this.xTexOffset + k + i + k + i, this.yTexOffset + k + j, this.xTexOffset + k + i + k, this.yTexOffset + k, this.tWidth, this.tHeight);
        this.field_2302[5] = new class_552(new class_290[]{positiontexturevertex4, positiontexturevertex5, positiontexturevertex6, positiontexturevertex7}, this.xTexOffset + k + i, this.yTexOffset + k + j, this.xTexOffset + k, this.yTexOffset + k);
        ((ExClass_552) this.field_2302[5]).setVertexes(this.xTexOffset + k + i, this.yTexOffset + k + j, this.xTexOffset + k, this.yTexOffset + k, this.tWidth, this.tHeight);
        if (this.mirror) {
            for (net.minecraft.class_552 class_552 : this.field_2302) {
                class_552.method_1925();
            }
        }
    }

    @Override
    public int getTWidth() {
        return tWidth;
    }

    @Override
    public int getTHeight() {
        return tHeight;
    }

    @Override
    public void setTWidth(int w) {
        tWidth = w;
    }

    @Override
    public void setTHeight(int h) {
        tHeight = h;
    }

    @Override
    public void setDimensions(int w, int h) {
        this.tWidth = w;
        this.tHeight = h;
    }
}
