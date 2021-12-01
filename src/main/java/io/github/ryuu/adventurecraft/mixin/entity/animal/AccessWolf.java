package io.github.ryuu.adventurecraft.mixin.entity.animal;

import net.minecraft.entity.animal.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Wolf.class)
public interface AccessWolf {

    @Invoker
    void invokeSpawnBoneParticles(boolean flag);
}
