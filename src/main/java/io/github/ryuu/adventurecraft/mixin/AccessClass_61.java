package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.class_61;
import net.minecraft.util.maths.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(class_61.class)
public interface AccessClass_61 {

    @Accessor
    @Mutable
    void setField_2690(int field_2690);

    @Accessor
    Vec3i[] getField_2691();

    @Accessor
    @Mutable
    void setField_2691(Vec3i[] field_2691);

    @Accessor
    int getField_2692();

    @Accessor
    void setField_2692(int field_2692);
}
