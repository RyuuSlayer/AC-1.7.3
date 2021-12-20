package io.github.ryuu.adventurecraft.mixin.client.particle;

import net.minecraft.client.particle.EntityCollisionParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityCollisionParticle.class)
public abstract class MixinEntityCollisionParticle {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public int method_2003() {
        return 5;
    }
}
