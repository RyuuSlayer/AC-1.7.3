package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.extensions.ExClass_108;
import io.github.ryuu.adventurecraft.extensions.ExClass_61;
import net.minecraft.class_108;
import net.minecraft.class_61;
import net.minecraft.entity.Entity;
import net.minecraft.util.maths.Vec3i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_61.class)
public abstract class MixinClass_61 implements AccessClass_61, ExClass_61 {

    @Final
    @Shadow
    private Vec3i[] field_2691;

    @Final
    @Shadow
    public int field_2690;

    @Override
    public class_108 getP() {
        return p;
    }

    @Override
    public void setP(class_108 p) {
        this.p = p;
    }

    @Override
    public Vec3i getClearSize() {
        return clearSize;
    }

    @Override
    public void setClearSize(Vec3i clearSize) {
        this.clearSize = clearSize;
    }

    public class_108 p;
    public Vec3i clearSize;

    @Inject(method = "method_2040", at = @At("TAIL"))
    private void simplifyPath(CallbackInfo ci) {
        if (this.p != null) {
            ((ExClass_108) this.p).simplifyPath((class_61) (Object) this, this.clearSize);
        }
    }

    @Override
    public boolean needNewPath(Entity entity) {
        if (this.field_2690 > 0) {
            double dX = entity.x - this.field_2691[this.field_2690 - 1].x - 0.5;
            double dY = entity.y - entity.standingEyeHeight - this.field_2691[this.field_2690 - 1].y;
            double dZ = entity.z - this.field_2691[this.field_2690 - 1].z - 0.5;
            return dX * dX + dY * dY + dZ * dZ > 6.0;
        }
        return false;
    }
}
