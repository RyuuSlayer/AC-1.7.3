package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.SandTile;
import net.minecraft.tile.StillFluidTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SandTile.class)
public interface AccessSandTile {
    @Invoker("<init>")
    static SandTile newSandTile(int i, int j) {
        throw new AssertionError();
    }
}
