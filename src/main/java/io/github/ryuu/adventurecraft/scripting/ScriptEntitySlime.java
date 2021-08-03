package io.github.ryuu.adventurecraft.scripting;

import ls;
import uw;

public class ScriptEntitySlime extends ScriptEntityLiving {
    uw entitySlime;

    ScriptEntitySlime(uw e) {
        super((ls)e);
        this.entitySlime = e;
    }

    public void setAttackStrength(int i) {
        this.entitySlime.attackStrength = i;
    }

    public int getAttackStrength() {
        return this.entitySlime.attackStrength;
    }

    public void setSlimeSize(int i) {
        this.entitySlime.e(i);
    }

    public int getSlimeSize() {
        return this.entitySlime.v();
    }
}
