package io.github.ryuu.adventurecraft.mixin.client.render;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface AccessGameRenderer {

    @Accessor
    float getField_2365();

    @Accessor
    void setField_2365(float value);
}
