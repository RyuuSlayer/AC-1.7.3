package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.level.Explosion;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Snowball.class)
public abstract class MixinSnowball extends Entity {

    private float radius;

    public MixinSnowball(Level world) {
        super(world);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init() {
        this.radius = 1.0f;
        ((ExEntity) this).setCollidesWithClipBlocks(false);
    }

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;createExplosion(Lnet/minecraft/entity/Entity;DDDFZ)Lnet/minecraft/level/Explosion;"))
    private Explosion changeExplosionRadius(Level instance, Entity e, double x, double y, double z, float r, boolean b) {
        return instance.createExplosion(e, x, y, z, this.radius, b);
    }
}
