package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.extensions.entity.monster.ExSlime;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public abstract class MixinSlime extends LivingEntity implements MonsterEntityType, ExSlime {

    private int attackStrength = -1;

    public MixinSlime(Level arg) {
        super(arg);
    }

    @Shadow
    public abstract int getSize();

    @Inject(method = "tickHandSwing", at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/entity/monster/Slime;getSize()I",
            ordinal = 1,
            shift = At.Shift.AFTER))
    private void reduceMovement(CallbackInfo ci) {
        float length = (float) Math.sqrt(this.perpendicularMovement * this.perpendicularMovement + this.parallelMovement * this.parallelMovement);
        this.perpendicularMovement /= length;
        this.parallelMovement /= length;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void remove() {
        super.remove();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void onPlayerCollision(Player entityplayer) {
        int i = this.getSize();
        int j = i;
        if (this.attackStrength != -1) {
            j = this.attackStrength;
        }
        if ((i > 1 || this.attackStrength != -1) && this.method_928(entityplayer) && this.distanceTo(entityplayer) < 0.6 * i && entityplayer.damage(this, j)) {
            this.level.playSound(this, "mob.slimeattack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
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
