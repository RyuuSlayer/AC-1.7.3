package io.github.ryuu.adventurecraft.extensions.entity.player;

import io.github.ryuu.adventurecraft.extensions.entity.ExWalkingEntity;

public interface ExPlayer extends ExWalkingEntity {

    boolean usingUmbrella();

    int getHeartPieces();

    void setHeartPieces(int heartPieces);

    boolean getSwappedItems();

    void setSwappedItems(boolean swappedItems);

    String getCloakTexture();

    void setCloakTexture(String cloakTexture);

    float getSwingOffhandProgress(float f);

    void swingOffhandItem();
}
