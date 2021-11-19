package io.github.ryuu.adventurecraft.scripting;

public class ScriptEntityMob extends ScriptEntityCreature {

    MixinMonster entityMob;

    ScriptEntityMob(MixinMonster e) {
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
