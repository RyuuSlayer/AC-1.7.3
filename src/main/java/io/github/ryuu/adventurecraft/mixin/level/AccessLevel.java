package io.github.ryuu.adventurecraft.mixin.level;

import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.source.LevelSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Level.class)
public interface AccessLevel {

    @Accessor
    LevelSource getCache();

    @Accessor
    void setCache(LevelSource cache);

    @Accessor
    void setProperties(LevelProperties levelProperties);

    @Accessor
    @Mutable
    void setDimension(Dimension dimension);

    @Accessor
    @Mutable
    void setDimensionData(DimensionData dimensionData);

    @Invoker
    LevelSource invokeCreateChunkCache();

    @Invoker
    void invokeComputeSpawnPosition();

    @Invoker
    void invokeInitWeatherGradients();
}
