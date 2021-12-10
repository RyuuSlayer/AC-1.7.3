package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.FlowingFluidTile;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FlowingFluidTile.class)
public abstract class MixinFlowingFluidTile extends FluidTile {

    protected MixinFlowingFluidTile(int id, Material material) {
        super(id, material);
    }

    @Inject(method = "onScheduledTick", at = @At("HEAD"), cancellable = true)
    private void stopTickWhenDebug(Level level, int x, int y, int z, Random rand, CallbackInfo ci) {
        if (DebugMode.active) {
            ci.cancel();
        }
    }
}
