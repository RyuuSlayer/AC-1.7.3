package io.github.ryuu.adventurecraft.extensions.client.render;

import net.minecraft.level.Level;

public interface ExTileRenderer {

    void startRenderingBlocks(Level world);

    void stopRenderingBlocks();
}
