package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface AccessEntity {

    @Accessor
    boolean isImmuneToFire();

    @Accessor
    void setImmuneToFire(boolean immuneToFire);

    @Accessor
    void setFallDistance(float fallDistance);

    @Accessor
    int getField_1648();

    @Accessor
    void setField_1648(int value);

    @Accessor
    static int getField_1590() {
        throw new AssertionError();
    }

    @Accessor
    static void setField_1590(int nextEntityId) {
        throw new AssertionError();
    }

    @Invoker("setRotation")
    void invokeSetRotation(float yaw, float pitch);
}
