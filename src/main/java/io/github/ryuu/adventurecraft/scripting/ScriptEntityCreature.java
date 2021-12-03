package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.ExWalkingEntity;
import net.minecraft.entity.WalkingEntity;

@SuppressWarnings("unused")
public class ScriptEntityCreature extends ScriptEntityLiving {

    WalkingEntity entityCreature;

    public ScriptEntityCreature(WalkingEntity entity) {
        super(entity);
        this.entityCreature = entity;
    }

    public ScriptEntity getTarget() {
        return ScriptEntity.getEntityClass(this.entityCreature.method_634());
    }

    public void setTarget(ScriptEntity e) {
        this.entityCreature.method_636(e.entity);
    }

    public boolean hasPath() {
        return this.entityCreature.method_633();
    }

    public void pathToEntity(ScriptEntity e) {
        this.entityCreature.setTarget(this.entityCreature.level.method_192(this.entityCreature, e.entity, 1.0f));
    }

    public void pathToBlock(int x, int y, int z) {
        this.entityCreature.setTarget(this.entityCreature.level.method_189(this.entityCreature, x, y, z, 1.0f));
    }

    public boolean getCanForgetTargetRandomly() {
        return ((ExWalkingEntity)this.entityCreature).canForgetTargetRandomly();
    }

    public void setCanForgetTargetRandomly(boolean b) {
        ((ExWalkingEntity)this.entityCreature).setForgetTargetRandomly(b);
    }

    public boolean getCanPathRandomly() {
        return ((ExWalkingEntity)this.entityCreature).canPathRandomly();
    }

    public void setCanPathRandomly(boolean b) {
        ((ExWalkingEntity)this.entityCreature).setPathRandomly(b);
    }
}
