package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.mixin.entity.monster.AccessMonster;
import net.minecraft.entity.monster.Monster;

@SuppressWarnings("unused")
public class ScriptEntityMob extends ScriptEntityCreature {

    Monster entityMob;

    public ScriptEntityMob(Monster entity) {
        super(entity);
        this.entityMob = entity;
    }

    public int getAttackStrength() {
        return ((AccessMonster)this.entityMob).getAttackDamage();
    }

    public void setAttackStrength(int i) {
        ((AccessMonster)this.entityMob).setAttackDamage(i);
    }
}
