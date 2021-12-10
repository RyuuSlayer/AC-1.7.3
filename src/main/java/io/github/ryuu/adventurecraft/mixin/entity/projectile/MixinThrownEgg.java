package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEgg.class)
public abstract class MixinThrownEgg extends Entity {

    public MixinThrownEgg(Level arg) {
        super(arg);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        ((ExEntity) this).setCollidesWithClipBlocks(false);
    }
}
