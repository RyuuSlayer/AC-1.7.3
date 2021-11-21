package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.script.ScriptEntity;
import net.minecraft.script.ScriptItem;
import net.minecraft.script.ScriptVec3;
import net.minecraft.util.maths.Vec3f;

public class ScriptEntityLiving extends ScriptEntity {

    LivingEntity entityLiving;

    ScriptEntityLiving(LivingEntity e) {
        super(e);
        this.entityLiving = e;
    }

    public void playLivingSound() {
        this.entityLiving.playAmbientSound();
    }

    public void spawnExplosionParticle() {
        this.entityLiving.onSpawnedFromSpawner();
    }

    public void heal(int i) {
        this.entityLiving.addHealth(i);
    }

    public boolean isOnLadder() {
        return this.entityLiving.isOnLadder();
    }

    public ScriptEntity getLookTarget() {
        Entity e = this.entityLiving.method_922();
        return ScriptEntity.getEntityClass(e);
    }

    public void setLookTarget(ScriptEntity e) {
        this.entityLiving.field_1061 = e.entity;
    }

    public ScriptVec3 getLookVec() {
        Vec3f v = this.entityLiving.method_1320();
        return new ScriptVec3(v.x, v.y, v.z);
    }

    public int getHealth() {
        return this.entityLiving.health;
    }

    public void setHealth(int i) {
        this.entityLiving.health = i;
    }

    public int getMaxHealth() {
        return this.entityLiving.maxHealth;
    }

    public void setMaxHealth(int i) {
        this.entityLiving.maxHealth = i;
    }

    public String getTexture() {
        return this.entityLiving.method_1314();
    }

    public void setTexture(String s) {
        this.entityLiving.texture = s;
    }

    public int getHurtTime() {
        return this.entityLiving.hurtTime;
    }

    public int getHurtTimeResistance() {
        return this.entityLiving.field_1058;
    }

    public void setHurtTime(int i) {
        this.entityLiving.hurtTime = i;
    }

    public void setHurtTimeResistance(int i) {
        this.entityLiving.field_1058 = i;
    }

    public ScriptItem getHeldItem() {
        return new ScriptItem(this.entityLiving.heldItem);
    }

    public void setHeldItem(ScriptItem itemstack) {
        this.entityLiving.heldItem = itemstack.item;
    }

    public float getMoveSpeed() {
        return this.entityLiving.movementSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.entityLiving.movementSpeed = moveSpeed;
    }

    public int getTimesCanJumpInAir() {
        return this.entityLiving.timesCanJumpInAir;
    }

    public void setTimesCanJumpInAir(int i) {
        this.entityLiving.timesCanJumpInAir = i;
    }

    public boolean getCanWallJump() {
        return this.entityLiving.canWallJump;
    }

    public void setCanWallJump(boolean b) {
        this.entityLiving.canWallJump = b;
    }

    public int getJumpsInAirLeft() {
        return this.entityLiving.jumpsLeft;
    }

    public void setJumpsInAirLeft(int i) {
        this.entityLiving.jumpsLeft = i;
    }

    public double getGravity() {
        return this.entityLiving.gravity;
    }

    public void setGravity(double g) {
        this.entityLiving.gravity = g;
    }

    public double getJumpVelocity() {
        return this.entityLiving.jumpVelocity;
    }

    public void setJumpVelocity(double v) {
        this.entityLiving.jumpVelocity = v;
    }

    public double getJumpWallMultiplier() {
        return this.entityLiving.jumpWallMultiplier;
    }

    public void setJumpWallMultiplier(double m) {
        this.entityLiving.jumpWallMultiplier = m;
    }

    public double getJumpInAirMultiplier() {
        return this.entityLiving.jumpInAirMultiplier;
    }

    public void setJumpInAirMultiplier(double m) {
        this.entityLiving.jumpInAirMultiplier = m;
    }

    public boolean getShouldJump() {
        return this.entityLiving.jumping;
    }

    public void setShouldJump(boolean j) {
        this.entityLiving.jumping = j;
    }

    public float getAirControl() {
        return this.entityLiving.airControl;
    }

    public void setAirControl(float j) {
        this.entityLiving.airControl = j;
    }

    public void fireBullet(float spread, int damage) {
        UtilBullet.fireBullet(this.entityLiving.level, this.entityLiving, spread, damage);
    }

    public float getFov() {
        return this.entityLiving.fov;
    }

    public void setFov(float f) {
        this.entityLiving.fov = f;
    }

    public boolean getCanLookRandomly() {
        return this.entityLiving.canLookRandomly;
    }

    public void setCanLookRandomly(boolean b) {
        this.entityLiving.canLookRandomly = b;
    }

    public float getRandomLookVelocity() {
        return this.entityLiving.randomLookVelocity;
    }

    public void setRandomLookVelocity(float f) {
        this.entityLiving.randomLookVelocity = f;
    }

    public int getRandomLookNext() {
        return this.entityLiving.randomLookNext;
    }

    public void setRandomLookNext(int i) {
        this.entityLiving.randomLookNext = i;
    }

    public int getRandomLookRate() {
        return this.entityLiving.randomLookRate;
    }

    public void setRandomLookRate(int i) {
        this.entityLiving.randomLookRate = i;
    }

    public int getRandomLookRateVariation() {
        return this.entityLiving.randomLookRateVariation;
    }

    public void setRandomLookRateVariation(int i) {
        this.entityLiving.randomLookRateVariation = i;
    }
}
