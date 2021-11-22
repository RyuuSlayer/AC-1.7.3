package io.github.ryuu.adventurecraft.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Class_61.class)
public class MixinClass_61 {

    @Shadow()
    public Vec3i[] field_2691;

    public int field_2690;

    public int field_2692;

    public class_108 p;

    public Vec3i clearSize;

    public MixinClass_61(Vec3i[] apathpoint) {
        this.field_2691 = apathpoint;
        this.field_2690 = apathpoint.length;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_2040() {
        ++this.field_2692;
        if (this.p != null) {
            this.p.simplifyPath(this, this.clearSize);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f method_2041(Entity entity) {
        double d = (double) this.field_2691[this.field_2692].x + (double) ((int) (entity.width + 1.0f)) * 0.5;
        double d1 = this.field_2691[this.field_2692].y;
        double d2 = (double) this.field_2691[this.field_2692].z + (double) ((int) (entity.width + 1.0f)) * 0.5;
        return Vec3f.from(d, d1, d2);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean needNewPath(Entity entity) {
        if (this.field_2690 > 0) {
            double dX = entity.x - (double) this.field_2691[this.field_2690 - 1].x - 0.5;
            double dY = entity.y - (double) entity.standingEyeHeight - (double) this.field_2691[this.field_2690 - 1].y;
            double dZ = entity.z - (double) this.field_2691[this.field_2690 - 1].z - 0.5;
            return dX * dX + dY * dY + dZ * dZ > 6.0;
        }
        return false;
    }
}
