package io.github.ryuu.adventurecraft.extensions.entity;

public interface ExWalkingEntity extends ExLivingEntity {

    boolean canForgetTargetRandomly();

    void setForgetTargetRandomly(boolean forgetTargetRandomly);

    boolean canPathRandomly();

    void setPathRandomly(boolean pathRandomly);
}
