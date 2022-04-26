package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.extensions.client.render.ExTextureBinder;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.render.TextureBinder;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureBinder.class)
public abstract class MixinTextureBinder implements ExTextureBinder {

    @Shadow
    public byte[] grid;

    @Shadow
    public int renderMode;

    @Shadow
    public boolean render3d;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initGrid(int i, CallbackInfo ci) {
        grid = new byte[262144];
    }

    @Override
    public String getTexture() {
        if (this.renderMode == 0) {
            return "/assets/terrain.png";
        }
        return "/assets/items.png";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public void bindTexture(TextureManager manager) {
        GL11.glBindTexture(3553, manager.getTextureId(this.getTexture()));
    }

    @Override
    public void onTick(Vec2 resolution) {
    }
}
