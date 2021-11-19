package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.FlyingEntity;

public class ScriptEntityFlying extends ScriptEntityLiving {
    FlyingEntity entityFlying;

    ScriptEntityFlying(FlyingEntity e) {
        super(e);
        this.entityFlying = e;
    }

    public int getAttackStrength() {
        return this.entityFlying.attackStrength;
    }

    public void setAttackStrength(int i) {
        this.entityFlying.attackStrength = i;
    }
}