package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.entity.animal.Wolf;

public class ScriptEntityWolf extends ScriptEntityCreature {
    Wolf entityWolf;

    ScriptEntityWolf(Wolf e) {
        super(e);
        this.entityWolf = e;
    }

    public int getAttackStrength() {
        return this.entityWolf.attackStrength;
    }

    public void setAttackStrength(int i) {
        this.entityWolf.attackStrength = i;
    }

    public boolean isWolfSitting() {
        return this.entityWolf.B();
    }

    public void setWolfSitting(boolean flag) {
        this.entityWolf.b(flag);
    }

    public boolean isWolfAngry() {
        return this.entityWolf.C();
    }

    public void setWolfAngry(boolean flag) {
        this.entityWolf.c(flag);
    }

    public boolean isWolfTamed() {
        return this.entityWolf.D();
    }

    public void setWolfTamed(boolean flag) {
        this.entityWolf.d(flag);
    }

    public String getWolfOwner() {
        return this.entityWolf.A();
    }

    public void setWolfOwner(ScriptEntityPlayer player) {
        this.entityWolf.a(player.entityPlayer.l);
    }
}
