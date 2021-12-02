package io.github.ryuu.adventurecraft.mixin.client.render;

public interface ExHandItemRenderer {

    void renderItemInFirstPerson(float f, float swingProgress, float otherHand);

    boolean hasItem();
}
