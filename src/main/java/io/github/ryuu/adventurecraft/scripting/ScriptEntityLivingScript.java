package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.entities.EntityLivingScript;

public class ScriptEntityLivingScript extends ScriptEntityLiving {
    EntityLivingScript entityLivingScript;

    ScriptEntityLivingScript(EntityLivingScript e) {
        super(e);
        this.entityLivingScript = e;
    }

    public boolean isPathing() {
        return this.entityLivingScript.isPathing();
    }

    public void clearPath() {
        this.entityLivingScript.clearPathing();
    }

    public void pathToEntity(ScriptEntity e) {
        this.entityLivingScript.pathToEntity(e.entity);
    }

    public void pathToBlock(int x, int y, int z) {
        this.entityLivingScript.pathToPosition(x, y, z);
    }

    public String getOnCreated() {
        return this.entityLivingScript.onCreated;
    }

    public void setOnCreated(String s) {
        this.entityLivingScript.onCreated = s;
        this.entityLivingScript.runCreatedScript();
    }

    public String getOnUpdated() {
        return this.entityLivingScript.onUpdate;
    }

    public void setOnUpdated(String s) {
        this.entityLivingScript.onUpdate = s;
    }

    public String getOnPathReached() {
        return this.entityLivingScript.onPathReached;
    }

    public void setOnPathReached(String s) {
        this.entityLivingScript.onPathReached = s;
    }

    public String getOnAttacked() {
        return this.entityLivingScript.onAttacked;
    }

    public void setOnAttacked(String s) {
        this.entityLivingScript.onAttacked = s;
    }

    public String getOnDeath() {
        return this.entityLivingScript.onDeath;
    }

    public void setOnDeath(String s) {
        this.entityLivingScript.onDeath = s;
    }

    public String getOnInteraction() {
        return this.entityLivingScript.onInteraction;
    }

    public void setOnInteraction(String s) {
        this.entityLivingScript.onInteraction = s;
    }
}
