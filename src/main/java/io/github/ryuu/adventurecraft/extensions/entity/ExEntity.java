package io.github.ryuu.adventurecraft.extensions.entity;

import net.minecraft.entity.Entity;

public interface ExEntity {

    boolean isFlying();

    void setFlying(boolean flying);

    int getCollisionX();

    int getCollisionZ();

    void setCollidesWithClipBlocks(boolean collidesWithClipBlocks);

    boolean getCollidesWithClipBlocks();

    boolean attackEntityFromMulti(Entity entity, int i);
}
