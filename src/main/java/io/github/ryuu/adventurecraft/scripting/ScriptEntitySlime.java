package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.monster.ExSlime;
import net.minecraft.entity.monster.Slime;

public class ScriptEntitySlime extends ScriptEntityLiving {

    Slime entitySlime;

    public ScriptEntitySlime(Slime e) {
        super(e);
        this.entitySlime = e;
    }

    public int getAttackStrength() {
        return ((ExSlime)this.entitySlime).getAttackStrength();
    }

    public void setAttackStrength(int i) {
        ((ExSlime)this.entitySlime).setAttackStrength(i);
    }

    public int getSlimeSize() {
        return this.entitySlime.getSize();
    }

    public void setSlimeSize(int i) {
        this.entitySlime.setSize(i);
    }
}
