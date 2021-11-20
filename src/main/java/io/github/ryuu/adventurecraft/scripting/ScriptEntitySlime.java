package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.monster.Slime;
import net.minecraft.script.ScriptEntityLiving;

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
