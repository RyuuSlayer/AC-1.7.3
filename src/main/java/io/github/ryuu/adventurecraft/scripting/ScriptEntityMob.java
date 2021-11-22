package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ScriptEntityMob extends ScriptEntityCreature {

    Monster entityMob;

    ScriptEntityMob(Monster e) {
        super(e);
        this.entityMob = e;
    }

    public void setAttackStrength(int i) {
        this.entityMob.attackDamage = i;
    }

    public int getAttackStrength() {
        return this.entityMob.attackDamage;
    }
}
