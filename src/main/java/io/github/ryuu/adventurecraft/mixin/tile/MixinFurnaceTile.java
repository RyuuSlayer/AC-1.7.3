package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.level.Level;
import net.minecraft.tile.FurnaceTile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceTile.class)
public abstract class MixinFurnaceTile extends TileWithEntity {

    protected MixinFurnaceTile(int i, Material arg) {
        super(i, arg);
    }

    @Inject(method = "method_1403", at = @At("TAIL"))
    private static void extraValidate(boolean flag, Level world, int i, int j, int k, CallbackInfo ci, TileEntity var6)  {
        var6.validate();
    }
}
