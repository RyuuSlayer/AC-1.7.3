package io.github.ryuu.adventurecraft.mixin.entity;

import io.github.ryuu.adventurecraft.extensions.ExClass_61;
import io.github.ryuu.adventurecraft.extensions.entity.ExWalkingEntity;
import io.github.ryuu.adventurecraft.util.IEntityPather;
import net.minecraft.class_61;
import net.minecraft.entity.Entity;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WalkingEntity.class)
public abstract class MixinWalkingEntity extends MixinLivingEntity implements IEntityPather, ExWalkingEntity {

    public boolean forgetTargetRandomly = true;

    public int timeBeforeForget = 0;

    public boolean pathRandomly = true;

    @Shadow
    protected Entity entity;

    @Shadow
    protected boolean field_663;

    @Shadow
    private class_61 field_661;

    @Shadow
    protected abstract void method_632();

    @Shadow
    public abstract boolean method_633();

    @Shadow
    protected abstract void method_637(Entity arg, float f);

    @Shadow
    protected abstract Entity method_638();

    @Shadow
    protected abstract void method_639(Entity arg, float f);

    @Shadow
    protected abstract boolean method_640();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void tickHandSwing() {
        this.field_663 = this.method_640();
        float f = 16.0f;
        if (this.entity == null) {
            this.entity = this.method_638();
            if (this.entity != null) {
                this.field_661 = this.level.method_192((Entity) (Object) this, this.entity, f);
                this.timeBeforeForget = 40;
            }
        } else if (!this.entity.isAlive()) {
            this.entity = null;
        } else {
            float f1 = this.entity.distanceTo((Entity) (Object) this);
            if (this.method_928(this.entity)) {
                this.method_637(this.entity, f1);
            } else {
                this.method_639(this.entity, f1);
            }
        }
        boolean canEntityBeSeen = false;
        if (this.entity != null) {
            canEntityBeSeen = this.method_928(this.entity);
        }
        if (!this.field_663 && this.entity != null && (this.field_661 == null || this.rand.nextInt(5) == 0 && ((ExClass_61)this.field_661).needNewPath(this.entity)) && canEntityBeSeen) {
            this.field_661 = this.level.method_192((Entity) (Object) this, this.entity, f);
        } else if (this.pathRandomly && !this.field_663 && (this.field_661 == null && this.rand.nextInt(80) == 0 || this.rand.nextInt(80) == 0)) {
            this.method_632();
        }
        if (this.entity != null && this.field_661 == null && !canEntityBeSeen) {
            if (this.timeBeforeForget-- <= 0) {
                this.entity = null;
            }
        } else {
            this.timeBeforeForget = 40;
        }
        int i = MathsHelper.floor(this.boundingBox.minY + 0.5);
        boolean flag = this.method_1334();
        boolean flag1 = this.method_1335();
        this.pitch = 0.0f;
        if (this.field_661 == null || this.forgetTargetRandomly && this.rand.nextInt(300) == 0) {
            super.tickHandSwing();
            this.field_661 = null;
            return;
        }
        Vec3d vec3d = this.field_661.method_2041((Entity) (Object) this);
        double d = this.width * 2.0f;
        while (vec3d != null && vec3d.getDistanceSquared(this.x, vec3d.y, this.z) < d * d) {
            this.field_661.method_2040();
            if (this.field_661.method_2042()) {
                vec3d = null;
                this.field_661 = null;
                continue;
            }
            vec3d = this.field_661.method_2041((Entity) (Object) this);
        }
        this.jumping = false;
        if (vec3d != null) {
            double d1 = vec3d.x - this.x;
            double d2 = vec3d.z - this.z;
            double d3 = vec3d.y - i;
            float f2 = (float) (Math.atan2(d2, d1) * 180.0 / 3.1415927410125732) - 90.0f;
            this.parallelMovement = this.movementSpeed;
            float f3 = f2 - this.yaw;
            while (f3 < -180.0f) {
                f3 += 360.0f;
            }
            while (f3 >= 180.0f) {
                f3 -= 360.0f;
            }
            if (f3 > 30.0f) {
                f3 = 30.0f;
            }
            if (f3 < -30.0f) {
                f3 = -30.0f;
            }
            this.yaw += f3;
            if (this.field_663 && this.entity != null) {
                double d4 = this.entity.x - this.x;
                double d5 = this.entity.z - this.z;
                float f5 = this.yaw;
                this.yaw = (float) (Math.atan2(d5, d4) * 180.0 / 3.1415927410125732) - 90.0f;
                float f4 = (f5 - this.yaw + 90.0f) * 3.141593f / 180.0f;
                this.perpendicularMovement = -MathsHelper.sin(f4) * this.parallelMovement * 1.0f;
                this.parallelMovement = MathsHelper.cos(f4) * this.parallelMovement * 1.0f;
            }
            if (d3 > 0.0) {
                this.jumping = true;
            }
        } else if (this.entity != null) {
            this.method_924(this.entity, 30.0f, 30.0f);
        }
        if (this.field_1624 && !this.method_633()) {
            this.jumping = true;
        }
        if (this.rand.nextFloat() < 0.8f && (flag || flag1)) {
            this.jumping = true;
        }
    }

    public class_61 getCurrentPath() {
        return this.field_661;
    }

    @Override
    public void writeACDataToTag(CompoundTag tag) {
        tag.put("canPathRandomly", this.pathRandomly);
        tag.put("canForgetTargetRandomly", this.forgetTargetRandomly);
    }

    @Override
    protected void readACDataFromTag(CompoundTag tag) {
        if (tag.containsKey("canPathRandomly")) {
            this.pathRandomly = tag.getBoolean("canPathRandomly");
        }
        if (tag.containsKey("canForgetTargetRandomly")) {
            this.pathRandomly = tag.getBoolean("canForgetTargetRandomly");
        }
    }

    @Override
    public boolean canForgetTargetRandomly() {
        return forgetTargetRandomly;
    }

    @Override
    public void setForgetTargetRandomly(boolean forgetTargetRandomly){
        this.forgetTargetRandomly = forgetTargetRandomly;
    }

    @Override
    public boolean canPathRandomly() {
        return pathRandomly;
    }

    @Override
    public void setPathRandomly(boolean pathRandomly) {
        this.pathRandomly = pathRandomly;
    }
}
