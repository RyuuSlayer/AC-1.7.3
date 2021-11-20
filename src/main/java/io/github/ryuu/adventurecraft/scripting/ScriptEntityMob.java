package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.monster.Monster;
import net.minecraft.script.ScriptEntityCreature;

public class ScriptEntityMob extends ScriptEntityCreature {

    MixinMonster entityMob;

    ScriptEntityMob(MixinMonster e) {
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
