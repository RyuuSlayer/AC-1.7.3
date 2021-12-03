package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Zombie;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public abstract class MixinZombie extends Monster {

    public MixinZombie(Level arg) {
        super(arg);
    }

    @Inject(method = "updateDespawnCounter", at = @At("HEAD"), cancellable = true)
    private void cancelIfBurnDisabled(CallbackInfo ci) {
        if (!((ExLevelProperties) this.level.getProperties()).getMobsBurn()) {
            ci.cancel();
        }
    }
}
