package io.github.ryuu.adventurecraft.scripting;

import ls;
import wq;

public class ScriptEntityFlying extends ScriptEntityLiving {
    wq entityFlying;

    ScriptEntityFlying(wq e) {
        super((ls)e);
        this.entityFlying = e;
    }

    public void setAttackStrength(int i) {
        this.entityFlying.attackStrength = i;
    }

    public int getAttackStrength() {
        return this.entityFlying.attackStrength;
    }
}
