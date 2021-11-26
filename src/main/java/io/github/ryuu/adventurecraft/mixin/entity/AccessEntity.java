package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface AccessEntity {

    @Accessor
    void setFallDistance(float fallDistance);
}
