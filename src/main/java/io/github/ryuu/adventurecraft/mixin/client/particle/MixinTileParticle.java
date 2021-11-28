package io.github.ryuu.adventurecraft.mixin.client.particle;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TileParticle;
import net.minecraft.client.render.Tessellator;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileParticle.class)
public class MixinTileParticle extends Particle {

    @Shadow
    private Tile field_2383;

    public MixinTileParticle(Level arg, double d, double d1, double d2, double d3, double d4, double d5) {
        super(arg, d, d1, d2, d3, d4, d5);
    }

    @Inject(method = "method_2003", at = @At("HEAD"), cancellable = true)
    private void method_2003(CallbackInfoReturnable<Integer> cir) {
        int t = ((ExTile) this.field_2383).adventurecraft$getTextureNum();
        if (t == 2) {
            cir.setReturnValue(3);
        }
        if (t == 3) {
            cir.setReturnValue(4);
        }
    }
}
