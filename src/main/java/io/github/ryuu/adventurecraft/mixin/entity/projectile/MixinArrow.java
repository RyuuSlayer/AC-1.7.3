package io.github.ryuu.adventurecraft.mixin.entity.projectile;

import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.projectile.ExArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public abstract class MixinArrow extends Entity implements ExArrow {

    public int attackStrength = 2;

    @Shadow
    public LivingEntity field_1576;

    public MixinArrow(Level arg) {
        super(arg);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void initDataTracker(CallbackInfo ci) {
        ((ExEntity) this).setCollidesWithClipBlocks(false);
    }

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"))
    public boolean handleHitEntity(Entity instance, Entity arg, int i) {
        if (instance instanceof LivingEntity && ((ExLivingEntity) instance).protectedByShield(this.prevX, this.prevY, this.prevZ)) {
            return true;
        } else {
            return instance.damage(this.field_1576, this.attackStrength);
        }
    }

    @Override
    public int getAttackDamage() {
        return this.attackStrength;
    }

    @Override
    public void setAttackDamage(int attackDamage) {
        this.attackStrength = attackDamage;
    }
}
