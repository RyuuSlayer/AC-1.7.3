package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.maths.Vec3f;

public class ScriptEntityLiving extends ScriptEntity {
    LivingEntity entityLiving;

    ScriptEntityLiving(LivingEntity e) {
        super(e);
        this.entityLiving = e;
    }

    public void playLivingSound() {
        this.entityLiving.T();
    }

    public void spawnExplosionParticle() {
        this.entityLiving.V();
    }

    public void heal(int i) {
        this.entityLiving.c(i);
    }

    public boolean isOnLadder() {
        return this.entityLiving.p();
    }

    public ScriptEntity getLookTarget() {
        Entity e = this.entityLiving.Z();
        return ScriptEntity.getEntityClass(e);
    }

    public void setLookTarget(ScriptEntity e) {
        this.entityLiving.b = e.entity;
    }

    public ScriptVec3 getLookVec() {
        Vec3f v = this.entityLiving.ac();
        return new ScriptVec3(v.a, v.b, v.c);
    }

    public int getHealth() {
        return this.entityLiving.Y;
    }

    public void setHealth(int i) {
        this.entityLiving.Y = i;
    }

    public int getMaxHealth() {
        return this.entityLiving.maxHealth;
    }

    public void setMaxHealth(int i) {
        this.entityLiving.maxHealth = i;
    }

    public String getTexture() {
        return this.entityLiving.q_();
    }

    public void setTexture(String s) {
        this.entityLiving.O = s;
    }

    public int getHurtTime() {
        return this.entityLiving.aa;
    }

    public void setHurtTime(int i) {
        this.entityLiving.aa = i;
    }

    public int getHurtTimeResistance() {
        return this.entityLiving.au;
    }

    public void setHurtTimeResistance(int i) {
        this.entityLiving.au = i;
    }

    public ScriptItem getHeldItem() {
        return new ScriptItem(this.entityLiving.heldItem);
    }

    public void setHeldItem(ScriptItem itemstack) {
        this.entityLiving.heldItem = itemstack.item;
    }

    public float getMoveSpeed() {
        return this.entityLiving.aB;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.entityLiving.aB = moveSpeed;
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
        return this.entityLiving.az;
    }

    public void setShouldJump(boolean j) {
        this.entityLiving.az = j;
    }

    public float getAirControl() {
        return this.entityLiving.airControl;
    }

    public void setAirControl(float j) {
        this.entityLiving.airControl = j;
    }

    public void fireBullet(float spread, int damage) {
        UtilBullet.fireBullet(this.entityLiving.aI, this.entityLiving, spread, damage);
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
