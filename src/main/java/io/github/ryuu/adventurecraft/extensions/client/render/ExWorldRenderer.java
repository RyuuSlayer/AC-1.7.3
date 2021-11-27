package io.github.ryuu.adventurecraft.extensions.client.render;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;

public interface ExWorldRenderer {

    void drawCursorSelection(LivingEntity entityplayer, ItemInstance itemstack, float f);

    void drawEntityPath(Entity e, LivingEntity entityplayer, float f);

    void drawEntityFOV(LivingEntity e, LivingEntity entityplayer, float f);

    Particle spawnParticleR(String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ);

    void updateAllTheRenderers();

    void resetAll();

     void resetForDeath();
}
