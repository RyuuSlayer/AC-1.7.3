package io.github.ryuu.adventurecraft.mixin.entity.monster;

import io.github.ryuu.adventurecraft.mixin.entity.MixinWalkingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MonsterEntityType;
import net.minecraft.entity.monster.Monster;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Monster.class)
public abstract class MixinMonster extends MixinWalkingEntity implements MonsterEntityType {

    @Shadow
    protected int attackDamage = 2;

    public MixinMonster(Level world) {
        super(world);
        this.health = 20;
    }

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
