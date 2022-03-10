package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.level.Level;
import net.minecraft.tile.DoorTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorTile.class)
public abstract class MixinDoorTile extends Tile {

    protected MixinDoorTile(int i, Material arg) {
        super(i, arg);
    }

    @Inject(method = "raycast", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/tile/DoorTile;method_1616(Lnet/minecraft/level/TileView;III)V",
            shift = At.Shift.AFTER))
    private void changeMinYOnRaycast(Level world, int x, int y, int z, Vec3d vec3d, Vec3d vec3d1, CallbackInfoReturnable<HitResult> cir) {
        int m = world.getTileMeta(x, y, z);
        if (this.material == Material.METAL && (m & 8) == 8) {
            this.minY = 0.8125;
        }
    }
}
