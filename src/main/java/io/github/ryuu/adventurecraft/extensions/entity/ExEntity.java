package io.github.ryuu.adventurecraft.extensions.entity;

public interface ExEntity {
    int getCollisionX();

    int getCollisionZ();

    void setCollidesWithClipBlocks(boolean collidesWithClipBlocks);

    boolean getCollidesWithClipBlocks();
}
