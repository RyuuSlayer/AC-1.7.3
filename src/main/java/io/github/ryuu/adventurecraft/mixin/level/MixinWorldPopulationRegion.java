package io.github.ryuu.adventurecraft.mixin.level;

import io.github.ryuu.adventurecraft.blocks.BlockStairMulti;
import io.github.ryuu.adventurecraft.util.LightCache;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.level.WorldPopulationRegion;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WorldPopulationRegion.class)
public abstract class MixinWorldPopulationRegion implements TileView {

    @Shadow
    private Level level;

    @Shadow
    public abstract int method_142(int i, int j, int k, boolean flag);

    @Shadow
    public abstract int method_143(int i, int j, int k);

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Override
    @Overwrite
    public float method_1784(int i, int j, int k, int l) {
        float light = LightCache.cache.getLightValue(i, j, k);
        if (light >= 0.0f) {
            return light;
        }
        int lightValue = this.method_143(i, j, k);
        if (lightValue < l) {
            lightValue = l;
        }
        float torchLight = PlayerTorch.getTorchLight(this.level, i, j, k);
        if (lightValue < torchLight) {
            int floorValue = (int) Math.floor(torchLight);
            if (floorValue == 15) {
                return this.level.dimension.field_2178[15];
            }
            int ceilValue = (int) Math.ceil(torchLight);
            float lerpValue = torchLight - floorValue;
            return (1.0f - lerpValue) * this.level.dimension.field_2178[floorValue] + lerpValue * this.level.dimension.field_2178[ceilValue];
        }
        light = this.level.dimension.field_2178[lightValue];
        LightCache.cache.setLightValue(i, j, k, light);
        return light;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Override
    @Overwrite
    public float getBrightness(int i, int j, int k) {
        float torchLight;
        float l = LightCache.cache.getLightValue(i, j, k);
        if (l >= 0.0f) {
            return l;
        }
        int lightValue = this.method_143(i, j, k);
        if ((float) lightValue < (torchLight = PlayerTorch.getTorchLight(this.level, i, j, k))) {
            int floorValue = (int) Math.floor(torchLight);
            if (floorValue == 15) {
                return this.level.dimension.field_2178[15];
            }
            int ceilValue = (int) Math.ceil(torchLight);
            float lerpValue = torchLight - (float) floorValue;
            return (1.0f - lerpValue) * this.level.dimension.field_2178[floorValue] + lerpValue * this.level.dimension.field_2178[ceilValue];
        }
        l = this.level.dimension.field_2178[lightValue];
        LightCache.cache.setLightValue(i, j, k, l);
        return l;
    }

    @Inject(method = "method_142", locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true, at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/level/WorldPopulationRegion;getTileId(III)I",
            shift = At.Shift.AFTER))
    private void afterGetTileInMethod_142(int i, int j, int k, boolean flag, CallbackInfoReturnable<Integer> cir, int var5) {
        if (Tile.BY_ID[var5] instanceof BlockStairMulti) {
            int k1 = this.method_142(i, j + 1, k, false);
            int i2 = this.method_142(i + 1, j, k, false);
            int j2 = this.method_142(i - 1, j, k, false);
            int k2 = this.method_142(i, j, k + 1, false);
            int l2 = this.method_142(i, j, k - 1, false);
            if (i2 > k1) {
                k1 = i2;
            }
            if (j2 > k1) {
                k1 = j2;
            }
            if (k2 > k1) {
                k1 = k2;
            }
            if (l2 > k1) {
                k1 = l2;
            }
            cir.setReturnValue(k1);
        }
    }
}
