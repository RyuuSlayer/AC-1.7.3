package io.github.ryuu.adventurecraft.mixin.client.render.entity.tile;

import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.tile.PistonRenderer;
import net.minecraft.client.render.entity.tile.TileEntityRenderer;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.Piston;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PistonRenderer.class)
public abstract class MixinPistonRenderer extends TileEntityRenderer {

    @Shadow
    private TileRenderer field_1131;

    @Redirect(method = "render(Lnet/minecraft/tile/entity/Piston;DDDF)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/tile/PistonRenderer;bindTexture(Ljava/lang/String;)V"))
    private void stopDefaultBindTexture(PistonRenderer instance, String s) {
    }

    @Inject(method = "render(Lnet/minecraft/tile/entity/Piston;DDDF)V", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/entity/tile/PistonRenderer;bindTexture(Ljava/lang/String;)V",
            shift = At.Shift.AFTER))
    private void bindCustomTexture(Piston tileentitypiston, double d, double d1, double d2, float f, CallbackInfo ci, Tile var9) {
        int textureNum = ((ExTile) var9).getTextureNum();
        String textureName = textureNum == 0 ? "/terrain.png" : String.format("/terrain%d.png", textureNum);
        this.bindTexture(textureName);
    }

    @Override
    public void refreshLevel(Level world) {
        this.field_1131 = new TileRenderer(world);
    }
}
