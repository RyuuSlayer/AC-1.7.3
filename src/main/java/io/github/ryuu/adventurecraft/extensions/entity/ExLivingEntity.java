package io.github.ryuu.adventurecraft.extensions.entity;

public interface ExLivingEntity {

    double getGravity();

    int getMaxHealth();

    void setMaxHealth(int i);

    boolean protectedByShield();

    boolean protectedByShield(double x, double y, double z);

}
