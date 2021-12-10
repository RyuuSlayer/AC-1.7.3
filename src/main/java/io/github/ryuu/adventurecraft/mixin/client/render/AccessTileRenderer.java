package io.github.ryuu.adventurecraft.mixin.client.render;

import net.minecraft.client.render.TileRenderer;
import net.minecraft.level.TileView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TileRenderer.class)
public interface AccessTileRenderer {

    @Accessor
    void setField_82(TileView value);
}
