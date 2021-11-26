package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.ExFlyingEntity;
import net.minecraft.entity.FlyingEntity;

public class ScriptEntityFlying extends ScriptEntityLiving {

    FlyingEntity entityFlying;

    ScriptEntityFlying(FlyingEntity e) {
        super(e);
        this.entityFlying = e;
    }

    public int getAttackStrength() {
        return ((ExFlyingEntity)this).getAttackStrength();
    }

    public void setAttackStrength(int i) {
        ((ExFlyingEntity)this).setAttackStrength(i);
    }
}
