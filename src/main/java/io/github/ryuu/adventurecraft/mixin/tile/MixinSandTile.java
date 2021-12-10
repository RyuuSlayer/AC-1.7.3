package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import io.github.ryuu.adventurecraft.extensions.entity.ExFallingTile;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.entity.FallingTile;
import net.minecraft.level.Level;
import net.minecraft.tile.SandTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SandTile.class)
public abstract class MixinSandTile extends Tile implements IBlockColor {

    protected MixinSandTile(int i, Material arg) {
        super(i, arg);
    }

    @Inject(method = "method_436", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.BEFORE))
    private void setMetadataBeforeSpawn(Level world, int i, int j, int k, CallbackInfo ci, int var5, int var6, int var7, int var8, FallingTile var9) {
        int metadata = world.getTileMeta(i, j, k);
        ((ExFallingTile) var9).setMetadata(metadata);
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        if (meta == 0) {
            return this.tex;
        }
        return 228 + meta - 1;
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        if (ExTile.subTypes[this.id] > 0) {
            int metadata = world.getTileMeta(i, j, k);
            world.setTileMeta(i, j, k, (metadata + 1) % ExTile.subTypes[this.id]);
        }
    }
}
