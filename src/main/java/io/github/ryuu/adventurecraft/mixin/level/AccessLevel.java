package io.github.ryuu.adventurecraft.mixin.level;

import net.minecraft.level.Level;
import net.minecraft.level.source.LevelSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Level.class)
public interface AccessLevel {

    @Accessor
    LevelSource getCache();
}
