package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.animal.ExWolf;
import net.minecraft.entity.animal.Wolf;

@SuppressWarnings("unused")
public class ScriptEntityWolf extends ScriptEntityCreature {

    Wolf entityWolf;

    public ScriptEntityWolf(Wolf entity) {
        super(entity);
        this.entityWolf = entity;
    }

    public int getAttackStrength() {
        return ((ExWolf) this.entityWolf).getAttackStrength();
    }

    public void setAttackStrength(int i) {
        ((ExWolf) this.entityWolf).setAttackStrength(i);
    }

    public boolean isWolfSitting() {
        return this.entityWolf.isSitting();
    }

    public void setWolfSitting(boolean flag) {
        this.entityWolf.setSitting(flag);
    }

    public boolean isWolfAngry() {
        return this.entityWolf.isAngry();
    }

    public void setWolfAngry(boolean flag) {
        this.entityWolf.setAngry(flag);
    }

    public boolean isWolfTamed() {
        return this.entityWolf.isTamed();
    }

    public void setWolfTamed(boolean flag) {
        this.entityWolf.setHasOwner(flag);
    }

    public String getWolfOwner() {
        return this.entityWolf.getOwner();
    }

    public void setWolfOwner(ScriptEntityPlayer player) {
        this.entityWolf.setOwner(player.entityPlayer.name);
    }
}
