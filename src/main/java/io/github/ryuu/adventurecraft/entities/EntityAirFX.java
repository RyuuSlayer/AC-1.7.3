package io.github.ryuu.adventurecraft.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.level.Level;

public class EntityAirFX extends Particle {

    public EntityAirFX(MixinLevel world, double d, double d1, double d2) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.field_2635 = 0;
        this.field_2644 = 1.0f;
        this.field_2643 = 1.0f;
        this.field_2642 = 1.0f;
        this.field_2641 = 0.0f;
        this.field_2640 /= 2.0f;
        this.velocityZ = 0.0;
        this.velocityY = 0.0;
        this.velocityX = 0.0;
    }

    @Override
    public int method_2003() {
        return 0;
    }
}
