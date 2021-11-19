/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.monster.Monster;
import net.minecraft.script.ScriptEntityCreature;
import io.github.ryuu.adventurecraft.mixin.item.MixinMonster;

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
