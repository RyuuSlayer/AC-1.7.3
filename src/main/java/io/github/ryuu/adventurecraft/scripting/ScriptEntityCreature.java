package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.WalkingEntity;

public class ScriptEntityCreature extends ScriptEntityLiving {
    WalkingEntity entityCreature;

    ScriptEntityCreature(WalkingEntity e) {
        super(e);
        this.entityCreature = e;
    }

    public ScriptEntity getTarget() {
        return ScriptEntity.getEntityClass(this.entityCreature.G());
    }

    public void setTarget(ScriptEntity e) {
        this.entityCreature.c(e.entity);
    }

    public boolean hasPath() {
        return this.entityCreature.F();
    }

    public void pathToEntity(ScriptEntity e) {
        this.entityCreature.a(this.entityCreature.aI.a((Entity) this.entityCreature, e.entity, 1.0F));
    }

    public void pathToBlock(int x, int y, int z) {
        this.entityCreature.a(this.entityCreature.aI.a((Entity) this.entityCreature, x, y, z, 1.0F));
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
