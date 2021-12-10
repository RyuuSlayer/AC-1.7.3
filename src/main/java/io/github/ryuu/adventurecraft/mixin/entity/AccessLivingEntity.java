package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface AccessLivingEntity {

    @Accessor
    void setTexture(String texture);

    @Accessor
    int getField_1058();

    @Accessor
    void setField_1058(int value);

    @Accessor
    float getMovementSpeed();

    @Accessor
    void setMovementSpeed(float movementSpeed);

    @Accessor
    boolean isJumping();

    @Accessor
    void setJumping(boolean jumping);

    @Accessor
    void setField_1061(Entity entity);

    @Invoker
    void invokeOnKilledBy(Entity entity);

    @Invoker
    void invokeApplyDamage(int damage);
}
