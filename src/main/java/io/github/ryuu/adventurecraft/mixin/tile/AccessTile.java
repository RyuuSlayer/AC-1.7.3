package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Tile.class)
public interface AccessTile {

    @Accessor
    float getHardness();

    @Accessor
    float getResistance();

    @Invoker
    Tile invokeHardness(float hardness);

    @Invoker
    void invokeAfterTileItemCreated();
}
