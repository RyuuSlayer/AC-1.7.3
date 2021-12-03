package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.StillFluidTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StillFluidTile.class)
public interface AccessStillFluidTile {
    @Invoker("<init>")
    static StillFluidTile newStillFluidTile(int i, Material arg) {
        throw new AssertionError();
    }
}
