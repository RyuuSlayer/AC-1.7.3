package io.github.ryuu.adventurecraft.mixin.client.texture;

import net.minecraft.client.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(TextureManager.class)
public interface AccessTextureManager {

    @Accessor("TEXTURE_ID_MAP")
    HashMap<String, Integer> getTextureIdMap();
}
