package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.extensions.entity.ExFlyingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.projectile.ExSnowball;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Ghast.class)
public abstract class MixinGhast extends FlyingEntity implements MonsterEntityType {

    public MixinGhast(Level arg) {
        super(arg);
    }

    @Inject(method = "tickHandSwing", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/entity/projectile/Snowball;<init>(Lnet/minecraft/level/Level;Lnet/minecraft/entity/LivingEntity;DDD)V",
            shift = At.Shift.AFTER))
    private void setSnowballRadius(CallbackInfo ci, Snowball var17) {
        float strength = ((ExFlyingEntity) this).getAttackStrength();
        ((ExSnowball) var17).setRadius(strength);
    }
}
