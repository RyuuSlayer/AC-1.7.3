package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.IceTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TranslucentTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceTile.class)
public abstract class MixinIceTile extends TranslucentTile {

    protected MixinIceTile(int i, int j, Material arg, boolean flag) {
        super(i, j, arg, flag);
    }

    @Inject(method = "afterBreak", at = @At("TAIL"))
    private void setWaterAfterBreak(Level world, Player entityplayer, int i, int j, int k, int l, CallbackInfo ci) {
        Material material = world.getMaterial(i, j - 1, k);
        if (material.blocksMovement() || material.isLiquid()) {
            world.setTile(i, j, k, Tile.FLOWING_WATER.id);
        }
    }

    @Inject(method = "onScheduledTick", at = @At("HEAD"), cancellable = true)
    public void onScheduledTick(Level level, int x, int y, int z, Random rand, CallbackInfo ci) {
        if (!((ExLevelProperties)level.getProperties()).getIceMelts()) {
            ci.cancel();
        }
    }
}
