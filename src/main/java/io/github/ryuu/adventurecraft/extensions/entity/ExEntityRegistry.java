package io.github.ryuu.adventurecraft.extensions.entity;

import io.github.ryuu.adventurecraft.mixin.entity.MixinEntityRegistry;
import net.minecraft.entity.Entity;

public interface ExEntityRegistry {

    static String getEntityStringClimbing(Entity entity) {
        String returning = null;
        for (Class obj = entity.getClass(); returning == null && obj != null; obj = obj.getSuperclass()) {
            returning = (String) MixinEntityRegistry.getClassToStringId().get(obj);
        }
        return returning;
    }
}
