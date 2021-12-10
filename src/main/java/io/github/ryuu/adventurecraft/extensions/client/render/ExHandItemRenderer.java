package io.github.ryuu.adventurecraft.extensions.client.render;

public interface ExHandItemRenderer {

    void renderItemInFirstPerson(float f, float swingProgress, float otherHand);

    boolean hasItem();
}
