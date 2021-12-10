package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ThrownSnowball;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownSnowball.class)
public abstract class MixinThrownSnowball extends Entity {

    public MixinThrownSnowball(Level world) {
        super(world);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        ((ExEntity) this).setCollidesWithClipBlocks(false);
    }
}
