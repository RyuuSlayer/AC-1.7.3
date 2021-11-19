package io.github.ryuu.adventurecraft.scripting;

public class ScriptEntityFlying extends ScriptEntityLiving {

    MixinFlyingEntity entityFlying;

    ScriptEntityFlying(MixinFlyingEntity e) {
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
