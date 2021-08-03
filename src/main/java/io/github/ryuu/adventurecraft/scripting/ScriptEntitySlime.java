package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.Slime;

public class ScriptEntitySlime extends ScriptEntityLiving {
    Slime entitySlime;

    ScriptEntitySlime(Slime e) {
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
        this.entitySlime.e(i);
    }

    public int getSlimeSize() {
        return this.entitySlime.v();
    }
}
