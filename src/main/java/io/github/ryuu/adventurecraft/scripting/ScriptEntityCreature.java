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
import net.minecraft.entity.WalkingEntity;
import net.minecraft.script.ScriptEntity;
import net.minecraft.script.ScriptEntityLiving;
import io.github.ryuu.adventurecraft.mixin.item.MixinWalkingEntity;

public class ScriptEntityCreature extends ScriptEntityLiving {

    MixinWalkingEntity entityCreature;

    ScriptEntityCreature(MixinWalkingEntity e) {
        super(e);
        this.entityCreature = e;
    }

    public void setTarget(ScriptEntity e) {
        this.entityCreature.method_636(e.entity);
    }

    public ScriptEntity getTarget() {
        return ScriptEntity.getEntityClass(this.entityCreature.method_634());
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
