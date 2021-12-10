package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.Main;
import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import io.github.ryuu.adventurecraft.extensions.client.options.ExGameOptions;
import io.github.ryuu.adventurecraft.extensions.tile.ExGrassTile;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.AcChunkCache;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.GrassTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GrassTile.class)
public abstract class MixinGrassTile extends Tile implements IBlockColor, ExGrassTile {

    protected MixinGrassTile(int id) {
        super(id, Material.ORGANIC);
        this.tex = 3;
        this.setTicksRandomly(true);
    }

    @Inject(method = "method_1626", at = @At(
            value = "RETURN",
            ordinal = 0))
    private void returnMetadataInstead(TileView iblockaccess, int i, int j, int k, int l, CallbackInfoReturnable<Integer> cir) {
        int metadata = iblockaccess.getTileMeta(i, j, k);
        if (metadata == 0) {
            cir.setReturnValue(0);
        } else {
            cir.setReturnValue(232 + metadata - 1);
        }
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        if (meta == 0) {
            return 0;
        }
        return 232 + meta - 1;
    }

    @Inject(method = "onScheduledTick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;setTile(IIII)Z",
            shift = At.Shift.BEFORE))
    private void beforeSetTile(Level level, int x, int y, int z, Random rand, CallbackInfo ci) {
        AcChunkCache.chunkIsNotPopulating = false;
    }

    @Inject(method = "onScheduledTick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;setTile(IIII)Z",
            shift = At.Shift.AFTER))
    private void afterSetTile(Level level, int x, int y, int z, Random rand, CallbackInfo ci) {
        AcChunkCache.chunkIsNotPopulating = true;
    }

    @Override
    public int method_1621() {
        if (((ExGameOptions)AccessMinecraft.getInstance().options).isGrass3d()) {
            return 30;
        }
        return super.method_1621();
    }

    @Override
    public void incrementColor(Level world, int i, int j, int k) {
        int metadata = world.getTileMeta(i, j, k);
        world.setTileMeta(i, j, k, (metadata + 1) % ExTile.subTypes[this.id]);
    }

    @Override
    public float grassMultiplier(int metadata) {
        switch (metadata) {
            case 2: {
                return 0.62f;
            }
            case 3: {
                return 0.85f;
            }
            case 4: {
                return -1.0f;
            }
        }
        return 1.0f;
    }
}
