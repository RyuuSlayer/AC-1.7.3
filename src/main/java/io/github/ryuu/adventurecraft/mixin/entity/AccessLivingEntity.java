package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface AccessLivingEntity {

    @Accessor
    boolean isJumping();

    @Invoker
    void invokeOnKilledBy(Entity entity);
}
