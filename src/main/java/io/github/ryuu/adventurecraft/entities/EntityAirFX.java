package io.github.ryuu.adventurecraft.entities;

import net.minecraft.client.particle.Particle;
import net.minecraft.level.Level;

public class EntityAirFX extends Particle {
    public EntityAirFX(Level world, double d, double d1, double d2) {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        this.field_2635 = 0;
        this.field_2642 = this.field_2643 = this.field_2644 = 1.0F;
        this.field_2641 = 0.0F;
        this.field_2640 /= 2.0F;
        this.velocityX = this.velocityY = this.velocityZ = 0.0D;
    }

    @Override
    public int method_2003() {
        return 0;
    }
}
