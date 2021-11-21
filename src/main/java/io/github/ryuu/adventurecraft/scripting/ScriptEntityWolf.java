package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.script.ScriptEntityCreature;
import net.minecraft.script.ScriptEntityPlayer;

public class ScriptEntityWolf extends ScriptEntityCreature {

    Wolf entityWolf;

    ScriptEntityWolf(Wolf e) {
        super(e);
        this.entityWolf = e;
    }

    public void setAttackStrength(int i) {
        this.entityWolf.attackStrength = i;
    }

    public int getAttackStrength() {
        return this.entityWolf.attackStrength;
    }

    public void setWolfSitting(boolean flag) {
        this.entityWolf.setSitting(flag);
    }

    public boolean isWolfSitting() {
        return this.entityWolf.isSitting();
    }

    public void setWolfAngry(boolean flag) {
        this.entityWolf.setAngry(flag);
    }

    public boolean isWolfAngry() {
        return this.entityWolf.isAngry();
    }

    public void setWolfTamed(boolean flag) {
        this.entityWolf.setHasOwner(flag);
    }

    public boolean isWolfTamed() {
        return this.entityWolf.isTamed();
    }

    public void setWolfOwner(ScriptEntityPlayer player) {
        this.entityWolf.setOwner(player.entityPlayer.name);
    }

    public String getWolfOwner() {
        return this.entityWolf.getOwner();
    }
}
