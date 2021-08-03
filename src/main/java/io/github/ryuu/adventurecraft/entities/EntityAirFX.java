package io.github.ryuu.adventurecraft.entities;

import net.minecraft.client.particle.Particle;
import net.minecraft.level.Level;

public class EntityAirFX extends Particle {
    public EntityAirFX(Level world, double d, double d1, double d2) {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        this.b = 0;
        this.i = this.j = this.k = 1.0F;
        this.h = 0.0F;
        this.g /= 2.0F;
        this.aP = this.aQ = this.aR = 0.0D;
    }

    public int c_() {
        return 0;
    }
}
