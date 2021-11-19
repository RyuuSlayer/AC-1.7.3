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
import net.minecraft.entity.FlyingEntity;
import net.minecraft.script.ScriptEntityLiving;
import io.github.ryuu.adventurecraft.mixin.item.MixinFlyingEntity;

public class ScriptEntityFlying extends ScriptEntityLiving {

    MixinFlyingEntity entityFlying;

    ScriptEntityFlying(MixinFlyingEntity e) {
        super(e);
        this.entityFlying = e;
    }

    public void setAttackStrength(int i) {
        this.entityFlying.attackStrength = i;
    }

    public int getAttackStrength() {
        return this.entityFlying.attackStrength;
    }
}
