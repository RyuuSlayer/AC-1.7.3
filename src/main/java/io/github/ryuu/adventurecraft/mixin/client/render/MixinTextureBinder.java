package io.github.ryuu.adventurecraft.mixin.client.render;

import io.github.ryuu.adventurecraft.extensions.client.render.ExTextureBinder;
import net.minecraft.client.render.TextureBinder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextureBinder.class)
public class MixinTextureBinder implements ExTextureBinder {

    @Shadow
    public byte[] grid = new byte[262144];

    @Shadow
    public int field_1412;

    @Shadow
    public boolean render3d = false;

    @Shadow
    public int textureId = 0;

    @Shadow
    public int field_1415 = 1;

    @Shadow
    public int renderMode = 0;

    @Override
    public String getTexture() {
        if (this.renderMode == 0) {
            return "/terrain.png";
        }
        if (this.renderMode == 1) {
            return "/gui/items.png";
        }
        return "/gui/items.png";
    }
}
