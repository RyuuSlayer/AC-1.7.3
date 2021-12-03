package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.mixin.entity.MixinWalkingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Monster.class)
public abstract class MixinMonster extends MixinWalkingEntity implements AccessMonster, MonsterEntityType {

    @Shadow
    protected int attackDamage = 2;

    @Override
    public boolean attackEntityFromMulti(Entity entity, int i) {
        this.timeBeforeForget = 40;
        if (super.attackEntityFromMulti(entity, i)) {
            if (this.passenger == entity || this.vehicle == entity) {
                return true;
            }
            this.entity = entity;
            return true;
        }
        return false;
    }
}
