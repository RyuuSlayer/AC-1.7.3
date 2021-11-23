package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.monster.Slime;

public class ScriptEntitySlime extends ScriptEntityLiving {

    Slime entitySlime;

    ScriptEntitySlime(Slime e) {
        super(e);
        this.entitySlime = e;
    }

    public int getAttackStrength() {
        return this.entitySlime.attackStrength;
    }

    public void setAttackStrength(int i) {
        this.entitySlime.attackStrength = i;
    }

    public int getSlimeSize() {
        return this.entitySlime.getSize();
    }

    public void setSlimeSize(int i) {
        this.entitySlime.setSize(i);
    }
}
