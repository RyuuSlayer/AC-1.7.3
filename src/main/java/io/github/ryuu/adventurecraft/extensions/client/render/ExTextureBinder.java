package io.github.ryuu.adventurecraft.extensions.client.render;

import io.github.ryuu.adventurecraft.util.Vec2;

public interface ExTextureBinder {

    String getTexture();

    void onTick(Vec2 resolution);
}
