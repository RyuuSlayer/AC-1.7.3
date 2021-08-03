package io.github.ryuu.adventurecraft.scripting;

import ii;
import ls;
import sn;

public class ScriptEntityCreature extends ScriptEntityLiving {
    ii entityCreature;

    ScriptEntityCreature(ii e) {
        super((ls) e);
        this.entityCreature = e;
    }

    public void setTarget(ScriptEntity e) {
        this.entityCreature.c(e.entity);
    }

    public ScriptEntity getTarget() {
        return ScriptEntity.getEntityClass(this.entityCreature.G());
    }

    public boolean hasPath() {
        return this.entityCreature.F();
    }

    public void pathToEntity(ScriptEntity e) {
        this.entityCreature.a(this.entityCreature.aI.a((sn) this.entityCreature, e.entity, 1.0F));
    }

    public void pathToBlock(int x, int y, int z) {
        this.entityCreature.a(this.entityCreature.aI.a((sn) this.entityCreature, x, y, z, 1.0F));
    }

    public boolean getCanForgetTargetRandomly() {
        return this.entityCreature.canForgetTargetRandomly;
    }

    public void setCanForgetTargetRandomly(boolean b) {
        this.entityCreature.canForgetTargetRandomly = b;
    }

    public boolean getCanPathRandomly() {
        return this.entityCreature.canPathRandomly;
    }

    public void setCanPathRandomly(boolean b) {
        this.entityCreature.canPathRandomly = b;
    }
}
