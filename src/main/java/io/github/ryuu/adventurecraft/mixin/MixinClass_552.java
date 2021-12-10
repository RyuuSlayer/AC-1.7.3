package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.extensions.ExClass_552;
import net.minecraft.class_290;
import net.minecraft.class_552;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(class_552.class)
public class MixinClass_552 implements ExClass_552 {

    @Shadow
    public class_290[] field_2518;

    @ModifyArgs(method = "<init>([Lnet/minecraft/class_290;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_290;method_983(FF)Lnet/minecraft/class_290;", ordinal = 0))
    private void Class_552(Args args, class_290[] args1, int i, int j, int k, int l) {
        float f = 0.0015625f;
        float f1 = 0.003125f;
        if (k < i) {
            f = -f;
        }
        if (l < j) {
            f1 = -f1;
        }
        args.set(0, (float) k / (float) 64 - f);
        args.set(1, (float) j / (float) 32 + f1);
    }

    @ModifyArgs(method = "<init>([Lnet/minecraft/class_290;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_290;method_983(FF)Lnet/minecraft/class_290;", ordinal = 1))
    private void Class_552_1(Args args, class_290[] args1, int i, int j, int k, int l) {
        float f = 0.0015625f;
        float f1 = 0.003125f;
        if (k < i) {
            f = -f;
        }
        if (l < j) {
            f1 = -f1;
        }
        args.set(0, (float) i / (float) 64 - f);
        args.set(1, (float) j / (float) 32 + f1);
    }

    @ModifyArgs(method = "<init>([Lnet/minecraft/class_290;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_290;method_983(FF)Lnet/minecraft/class_290;", ordinal = 2))
    private void Class_552_2(Args args, class_290[] args1, int i, int j, int k, int l) {
        float f = 0.0015625f;
        float f1 = 0.003125f;
        if (k < i) {
            f = -f;
        }
        if (l < j) {
            f1 = -f1;
        }
        args.set(0, (float) i / (float) 64 - f);
        args.set(1, (float) l / (float) 32 + f1);
    }

    @ModifyArgs(method = "<init>([Lnet/minecraft/class_290;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_290;method_983(FF)Lnet/minecraft/class_290;", ordinal = 3))
    private void Class_552_3(Args args, class_290[] args1, int i, int j, int k, int l) {
        float f = 0.0015625f;
        float f1 = 0.003125f;
        if (k < i) {
            f = -f;
        }
        if (l < j) {
            f1 = -f1;
        }
        args.set(0, (float) k / (float) 64 - f);
        args.set(1, (float) l / (float) 32 + f1);
    }

    @Override
    public void setVertexes(int i, int j, int k, int l, int tw, int th) {
        float f = 0.0015625f;
        float f1 = 0.003125f;
        if (k < i) {
            f = -f;
        }
        if (l < j) {
            f1 = -f1;
        }
        field_2518[0] = field_2518[0].method_983((float) k / (float) tw - f, (float) j / (float) th + f1);
        field_2518[1] = field_2518[1].method_983((float) i / (float) tw + f, (float) j / (float) th + f1);
        field_2518[2] = field_2518[2].method_983((float) i / (float) tw + f, (float) l / (float) th - f1);
        field_2518[3] = field_2518[3].method_983((float) k / (float) tw - f, (float) l / (float) th - f1);
    }
}
