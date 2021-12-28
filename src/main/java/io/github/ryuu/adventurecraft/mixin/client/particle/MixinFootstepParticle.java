package io.github.ryuu.adventurecraft.mixin.client.particle;

import net.minecraft.client.particle.FootstepParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FootstepParticle.class)
public abstract class MixinFootstepParticle {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int method_2003() {
        return 5;
    }
}
