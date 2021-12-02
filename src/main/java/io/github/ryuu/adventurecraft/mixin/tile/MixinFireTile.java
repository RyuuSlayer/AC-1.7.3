package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.level.Level;
import net.minecraft.tile.FireTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FireTile.class)
public abstract class MixinFireTile extends Tile {

    protected MixinFireTile(int i, Material arg) {
        super(i, arg);
    }

    @Inject(method = "onScheduledTick", at = @At("HEAD"), cancellable = true)
    private void disableTickInDebugMode(Level level, int x, int y, int z, Random rand, CallbackInfo ci) {
        if (DebugMode.active) {
            ci.cancel();
        }
    }
}
