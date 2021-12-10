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

import java.util.*;

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

    @Accessor
    void setField_181(List value);

    @Accessor
    void setEntities(List value);

    @Accessor
    void setField_182(List value);

    @Accessor
    void setField_183(TreeSet value);

    @Accessor
    void setField_184(Set value);

    @Accessor
    void setTileEntities(List value);

    @Accessor
    void setField_185(List value);

    @Accessor
    void setPlayers(List value);

    @Accessor
    void setField_201(List value);

    @Accessor
    void setField_186(long value);

    @Accessor
    void setField_203(int value);

    @Accessor
    @Mutable
    void setUnusedIncrement(int value);

    @Accessor
    void setTime(long value);

    @Accessor
    void setField_212(int value);

    @Accessor
    void setRand(Random rand);

    @Accessor
    void setListeners(List value);

    @Accessor
    void setField_189(ArrayList value);

    @Accessor
    void setField_192(boolean value);

    @Accessor
    void setField_193(boolean value);

    @Accessor
    void setField_195(int value);

    @Accessor
    void setField_196(List value);

    @Invoker
    LevelSource invokeCreateChunkCache();

    @Invoker
    void invokeComputeSpawnPosition();

    @Invoker
    void invokeInitWeatherGradients();

    @Invoker
    void invokeMethod_235(int i, int j, int k, int i1);
}
