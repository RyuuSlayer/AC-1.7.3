package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.extensions.entity.monster.ExSlime;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Slime.class)
public abstract class MixinSlime extends LivingEntity implements MonsterEntityType, ExSlime {

    @Shadow
    public float field_1951;

    @Shadow
    public float field_1952;

    @Shadow
    private int field_1953 ;

    private int attackStrength = -1;

    @Shadow
    public abstract int getSize();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void tickHandSwing() {
        this.method_920();
        Player entityplayer = this.level.getClosestPlayerTo(this, 16.0);
        if (entityplayer != null) {
            this.method_924(entityplayer, 10.0f, 20.0f);
        }
        if (this.onGround && this.field_1953-- <= 0) {
            this.field_1953 = this.rand.nextInt(20) + 10;
            if (entityplayer != null) {
                this.field_1953 /= 3;
            }
            this.jumping = true;
            if (this.getSize() > 1) {
                this.level.playSound(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.field_1951 = 1.0f;
            this.perpendicularMovement = 1.0f - this.rand.nextFloat() * 2.0f;
            this.parallelMovement = 1 * this.getSize();
            float length = (float) Math.sqrt(this.perpendicularMovement * this.perpendicularMovement + this.parallelMovement * this.parallelMovement);
            this.perpendicularMovement /= length;
            this.parallelMovement /= length;
        } else {
            this.jumping = false;
            if (this.onGround) {
                this.parallelMovement = 0.0f;
                this.perpendicularMovement = 0.0f;
            }
        }
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
        int i;
        int j = i = this.getSize();
        if (this.attackStrength != -1) {
            j = this.attackStrength;
        }
        if ((i > 1 || this.attackStrength != -1) && this.method_928(entityplayer) && (double) this.distanceTo(entityplayer) < 0.6 * (double) i && entityplayer.damage(this, j)) {
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
