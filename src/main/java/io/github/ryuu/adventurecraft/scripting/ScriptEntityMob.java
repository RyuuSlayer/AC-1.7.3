package io.github.ryuu.adventurecraft.scripting;

import gz;
import ii;

public class ScriptEntityMob extends ScriptEntityCreature {
    gz entityMob;

    ScriptEntityMob(gz e) {
        super((ii) e);
        this.entityMob = e;
    }

    public void setAttackStrength(int i) {
        this.entityMob.c = i;
    }

    public int getAttackStrength() {
        return this.entityMob.c;
    }
}
