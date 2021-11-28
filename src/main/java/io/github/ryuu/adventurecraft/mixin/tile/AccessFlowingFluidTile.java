package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.FlowingFluidTile;
import net.minecraft.tile.GrassTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FlowingFluidTile.class)
public interface AccessFlowingFluidTile {
    @Invoker("<init>")
    static FlowingFluidTile newFlowingFluidTile(int i, Material arg) {
        throw new AssertionError();
    }
}
