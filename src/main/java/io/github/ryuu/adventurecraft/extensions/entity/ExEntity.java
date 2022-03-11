package io.github.ryuu.adventurecraft.extensions.entity;

import net.minecraft.entity.Entity;

public interface ExEntity {

    boolean isFlying();

    void setFlying(boolean flying);

    int getCollisionX();

    int getCollisionZ();

    boolean getCollidesWithClipBlocks();

    void setCollidesWithClipBlocks(boolean collidesWithClipBlocks);

    int getStunned();

    void setStunned(int stunned);

    boolean attackEntityFromMulti(Entity entity, int i);
}
