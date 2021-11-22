package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.script.ScriptEntityLiving;

public class ScriptEntityFlying extends ScriptEntityLiving {

    FlyingEntity entityFlying;

    ScriptEntityFlying(FlyingEntity e) {
        super(e);
        this.entityFlying = e;
    }

    public void setAttackStrength(int i) {
        this.entityFlying.attackStrength = i;
    }

    public int getAttackStrength() {
        return this.entityFlying.attackStrength;
    }
}
