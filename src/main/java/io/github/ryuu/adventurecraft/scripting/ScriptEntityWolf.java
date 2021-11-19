package io.github.ryuu.adventurecraft.scripting;

public class ScriptEntityWolf extends ScriptEntityCreature {

    MixinWolf entityWolf;

    ScriptEntityWolf(MixinWolf e) {
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
