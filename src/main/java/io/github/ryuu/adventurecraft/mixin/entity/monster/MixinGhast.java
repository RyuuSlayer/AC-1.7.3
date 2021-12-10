package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.extensions.entity.ExFlyingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.projectile.ExSnowball;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.projectile.Snowball;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Vec3f;
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
            value = "INVOKE",
            target = "Lnet/minecraft/level/Level;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.BEFORE))
    private void setSnowballRadius(CallbackInfo ci, double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15, Snowball var17, double var18, Vec3f var20) {
        float strength = ((ExFlyingEntity) this).getAttackStrength();
        ((ExSnowball) var17).setRadius(strength);
    }
}
