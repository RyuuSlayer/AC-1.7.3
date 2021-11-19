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
import net.minecraft.entity.monster.Slime;
import net.minecraft.script.ScriptEntityLiving;
import io.github.ryuu.adventurecraft.mixin.item.MixinSlime;

public class ScriptEntitySlime extends ScriptEntityLiving {

    MixinSlime entitySlime;

    ScriptEntitySlime(MixinSlime e) {
        super(e);
        this.entitySlime = e;
    }

    public void setAttackStrength(int i) {
        this.entitySlime.attackStrength = i;
    }

    public int getAttackStrength() {
        return this.entitySlime.attackStrength;
    }

    public void setSlimeSize(int i) {
        this.entitySlime.setSize(i);
    }

    public int getSlimeSize() {
        return this.entitySlime.getSize();
    }
}
