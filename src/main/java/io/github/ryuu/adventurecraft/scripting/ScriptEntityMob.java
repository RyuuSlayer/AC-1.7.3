package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.monster.Monster;

public class ScriptEntityMob extends ScriptEntityCreature {

    Monster entityMob;

    ScriptEntityMob(Monster e) {
        super(e);
        this.entityMob = e;
    }

    public int getAttackStrength() {
        return this.entityMob.attackDamage;
    }

    public void setAttackStrength(int i) {
        this.entityMob.attackDamage = i;
    }
}
