package io.github.ryuu.adventurecraft.extensions.client.texture;

import io.github.ryuu.adventurecraft.util.TextureAnimated;
import io.github.ryuu.adventurecraft.util.Vec2;
import net.minecraft.client.render.TextureBinder;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public interface ExTextureManager {
    void adventurecraft$replaceTexture(String texToReplace, String replacement);

    void adventurecraft$revertTextures();

    void adventurecraft$loadTexture(int texID, String texName);

    BufferedImage adventurecraft$getTextureImage(String texName);

    Vec2 adventurecraft$getTextureResolution(String texName);

    void adventurecraft$clearTextureAnimations();

    void adventurecraft$registerTextureAnimation(String name, TextureAnimated animTex);

    void adventurecraft$unregisterTextureAnimation(String name);

    void adventurecraft$updateTextureAnimations();
}
