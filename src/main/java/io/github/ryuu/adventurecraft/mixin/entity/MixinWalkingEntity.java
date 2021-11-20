package io.github.ryuu.adventurecraft.mixin.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
import net.minecraft.class_61;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WalkingEntity.class)
public class MixinWalkingEntity extends LivingEntity implements IEntityPather {

    @Shadow()
    private class_61 field_661;

    protected Entity entity;

    protected boolean field_663 = false;

    public boolean canForgetTargetRandomly = true;

    public int timeBeforeForget = 0;

    public boolean canPathRandomly = true;

    public MixinWalkingEntity(Level world) {
        super(world);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void tickHandSwing() {
        this.field_663 = this.method_640();
        float f = 16.0f;
        if (this.entity == null) {
            this.entity = this.method_638();
            if (this.entity != null) {
                this.field_661 = this.level.method_192(this, this.entity, f);
                this.timeBeforeForget = 40;
            }
        } else if (!this.entity.isAlive()) {
            this.entity = null;
        } else {
            float f1 = this.entity.distanceTo(this);
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
        if (!this.field_663 && this.entity != null && (this.field_661 == null || this.rand.nextInt(5) == 0 && this.field_661.needNewPath(this.entity)) && canEntityBeSeen) {
            this.field_661 = this.level.method_192(this, this.entity, f);
        } else if (this.canPathRandomly && !this.field_663 && (this.field_661 == null && this.rand.nextInt(80) == 0 || this.rand.nextInt(80) == 0)) {
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
        if (this.field_661 == null || this.canForgetTargetRandomly && this.rand.nextInt(300) == 0) {
            super.tickHandSwing();
            this.field_661 = null;
            return;
        }
        Vec3f vec3d = this.field_661.method_2041(this);
        double d = this.width * 2.0f;
        while (vec3d != null && vec3d.method_1303(this.x, vec3d.y, this.z) < d * d) {
            this.field_661.method_2040();
            if (this.field_661.method_2042()) {
                vec3d = null;
                this.field_661 = null;
                continue;
            }
            vec3d = this.field_661.method_2041(this);
        }
        this.jumping = false;
        if (vec3d != null) {
            float f3;
            double d1 = vec3d.x - this.x;
            double d2 = vec3d.z - this.z;
            double d3 = vec3d.y - (double) i;
            float f2 = (float) (Math.atan2((double) d2, (double) d1) * 180.0 / 3.1415927410125732) - 90.0f;
            this.parallelMovement = this.movementSpeed;
            for (f3 = f2 - this.yaw; f3 < -180.0f; f3 += 360.0f) {
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
                this.yaw = (float) (Math.atan2((double) d5, (double) d4) * 180.0 / 3.1415927410125732) - 90.0f;
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_632() {
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0f;
        for (int l = 0; l < 10; ++l) {
            int k1;
            int j1;
            int i1 = MathsHelper.floor(this.x + (double) this.rand.nextInt(13) - 6.0);
            float f1 = this.getPathfindingFavour(i1, j1 = MathsHelper.floor(this.y + (double) this.rand.nextInt(7) - 3.0), k1 = MathsHelper.floor(this.z + (double) this.rand.nextInt(13) - 6.0));
            if (!(f1 > f))
                continue;
            f = f1;
            i = i1;
            j = j1;
            k = k1;
            flag = true;
        }
        if (flag) {
            this.field_661 = this.level.method_189(this, i, j, k, 10.0f);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canSpawn() {
        int i = MathsHelper.floor(this.x);
        int j = MathsHelper.floor(this.boundingBox.minY);
        int k = MathsHelper.floor(this.z);
        return super.canSpawn() && this.getPathfindingFavour(i, j, k) >= 0.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setTarget(class_61 pathentity) {
        this.field_661 = pathentity;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public class_61 getCurrentPath() {
        return this.field_661;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.put("canPathRandomly", this.canPathRandomly);
        tag.put("canForgetTargetRandomly", this.canForgetTargetRandomly);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.containsKey("canPathRandomly")) {
            this.canPathRandomly = tag.getBoolean("canPathRandomly");
        }
        if (tag.containsKey("canForgetTargetRandomly")) {
            this.canPathRandomly = tag.getBoolean("canForgetTargetRandomly");
        }
    }
}
