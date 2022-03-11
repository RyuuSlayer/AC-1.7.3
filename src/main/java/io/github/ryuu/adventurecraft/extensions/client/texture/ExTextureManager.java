package io.github.ryuu.adventurecraft.extensions.client.texture;

import io.github.ryuu.adventurecraft.util.TextureAnimated;
import io.github.ryuu.adventurecraft.util.Vec2;

import java.awt.image.BufferedImage;

public interface ExTextureManager {

    void replaceTexture(String texToReplace, String replacement);

    void revertTextures();

    void loadTexture(int texID, String texName);

    BufferedImage getTextureImage(String texName);

    Vec2 getTextureResolution(String texName);

    void clearTextureAnimations();

    void registerTextureAnimation(String name, TextureAnimated animTex);

    void unregisterTextureAnimation(String name);

    void updateTextureAnimations();
}
