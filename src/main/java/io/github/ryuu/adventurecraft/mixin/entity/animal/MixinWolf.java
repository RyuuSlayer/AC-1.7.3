package io.github.ryuu.adventurecraft.mixin.entity.animal;

import io.github.ryuu.adventurecraft.extensions.entity.animal.ExWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.animal.Animal;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Wolf.class)
public abstract class MixinWolf extends Animal implements AccessWolf, ExWolf {

    private int attackStrength = -1;

    public MixinWolf(Level arg) {
        super(arg);
    }

    @Redirect(method = "method_637", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"))
    protected boolean changeAttackStrength(Entity instance, Entity arg, int i) {
        if (this.attackStrength != -1) {
            i = this.attackStrength;
        }
        return entity.damage(this, i);
    }

    @Override
    public int getAttackStrength() {
        return attackStrength;
    }

    @Override
    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
}
