package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.level.Level;
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
    void invokeAfterTileItemCreated();

    @Invoker
    void invokeDrop(Level arg, int i, int j, int k, int i1);
}
