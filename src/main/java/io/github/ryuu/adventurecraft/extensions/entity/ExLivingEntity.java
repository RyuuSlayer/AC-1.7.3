package io.github.ryuu.adventurecraft.extensions.entity;

import net.minecraft.item.ItemInstance;

public interface ExLivingEntity extends ExEntity {

    boolean protectedByShield();

    boolean protectedByShield(double x, double y, double z);

    float getFov();

    void setFov(float fov);

    float getExtraFov();

    int getStunned();

    void setStunned(int stunned);

    int getMaxHealth();

    void setMaxHealth(int i);

    ItemInstance getHeldItem();

    void setHeldItem(ItemInstance itemInstance);

    int getTimesCanJumpInAir();

    void setTimesCanJumpInAir(int timesCanJumpInAir);

    boolean getCanWallJump();

    void setCanWallJump(boolean canWallJump);

    int getJumpsLeft();

    void setJumpsLeft(int jumpsLeft);

    double getJumpVelocity();

    void setJumpVelocity(double jumpVelocity);

    double getGravity();

    void setGravity(double gravity);

    double getJumpWallMultiplier();

    void setJumpWallMultiplier(double jumpWallMultiplier);

    double getJumpInAirMultiplier();

    void setJumpInAirMultiplier(double jumpInAirMultiplier);

    float getAirControl();

    void setAirControl(float airControl);

    boolean getCanLookRandomly();

    void setCanLookRandomly(boolean canLookRandomly);

    float getRandomLookVelocity();

    void setRandomLookVelocity(float randomLookVelocity);

    int getRandomLookNext();

    void setRandomLookNext(int randomLookNext);

    int getRandomLookRate();

    void setRandomLookRate(int randomLookRate);

    int getRandomLookRateVariation();

    void setRandomLookRateVariation(int randomLookRateVariation);
}
