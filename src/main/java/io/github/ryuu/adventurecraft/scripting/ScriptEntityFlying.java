package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.ExFlyingEntity;
import net.minecraft.entity.FlyingEntity;

@SuppressWarnings("unused")
public class ScriptEntityFlying extends ScriptEntityLiving {

    FlyingEntity entityFlying;

    public ScriptEntityFlying(FlyingEntity entity) {
        super(entity);
        this.entityFlying = entity;
    }

    public int getAttackStrength() {
        return ((ExFlyingEntity)this).getAttackStrength();
    }

    public void setAttackStrength(int i) {
        ((ExFlyingEntity)this).setAttackStrength(i);
    }
}
