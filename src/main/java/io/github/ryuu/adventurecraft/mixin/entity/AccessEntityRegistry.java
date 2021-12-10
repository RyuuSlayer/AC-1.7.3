package io.github.ryuu.adventurecraft.mixin.entity;

import net.minecraft.entity.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityRegistry.class)
public interface AccessEntityRegistry {

    @Accessor("CLASS_TO_STRING_ID")
    static Map<Class<?>, String> getClassToStringId() {
        throw new AssertionError();
    }
}
