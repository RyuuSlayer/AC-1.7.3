package io.github.ryuu.adventurecraft.extensions.client.render;

import net.minecraft.client.render.HandItemRenderer;

public interface ExGameRenderer {

    HandItemRenderer getOffHandItemRenderer();

    float getFarPlane();
}
