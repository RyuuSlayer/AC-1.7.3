package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.mixin.entity.AccessLivingEntity;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.maths.Vec3d;

@SuppressWarnings("unused")
public class ScriptEntityLiving extends ScriptEntity {

    LivingEntity entityLiving;

    ScriptEntityLiving(LivingEntity entity) {
        super(entity);
        this.entityLiving = entity;
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
        ((AccessLivingEntity) this.entityLiving).setField_1061(e.entity);
    }

    public ScriptVec3 getLookVec() {
        Vec3d v = this.entityLiving.getRotation();
        return new ScriptVec3(v.x, v.y, v.z);
    }

    public int getHealth() {
        return this.entityLiving.health;
    }

    public void setHealth(int i) {
        this.entityLiving.health = i;
    }

    public int getMaxHealth() {
        return ((ExLivingEntity) this.entityLiving).getMaxHealth();
    }

    public void setMaxHealth(int i) {
        ((ExLivingEntity) this.entityLiving).setMaxHealth(i);
    }

    public String getTexture() {
        return this.entityLiving.method_1314();
    }

    public void setTexture(String s) {
        ((AccessLivingEntity) this.entityLiving).setTexture(s);
    }

    public int getHurtTime() {
        return this.entityLiving.hurtTime;
    }

    public void setHurtTime(int i) {
        this.entityLiving.hurtTime = i;
    }

    public int getHurtTimeResistance() {
        return ((AccessLivingEntity) this.entityLiving).getField_1058();
    }

    public void setHurtTimeResistance(int i) {
        ((AccessLivingEntity) this.entityLiving).setField_1058(i);
    }

    public ScriptItem getHeldItem() {
        return new ScriptItem(((ExLivingEntity) this.entityLiving).getHeldItem());
    }

    public void setHeldItem(ScriptItem itemstack) {
        ((ExLivingEntity) this.entityLiving).setHeldItem(itemstack.item);
    }

    public float getMoveSpeed() {
        return ((AccessLivingEntity) this.entityLiving).getMovementSpeed();
    }

    public void setMoveSpeed(float moveSpeed) {
        ((AccessLivingEntity) this.entityLiving).setMovementSpeed(moveSpeed);
    }

    public int getTimesCanJumpInAir() {
        return ((ExLivingEntity) this.entityLiving).getTimesCanJumpInAir();
    }

    public void setTimesCanJumpInAir(int i) {
        ((ExLivingEntity) this.entityLiving).setTimesCanJumpInAir(i);
    }

    public boolean getCanWallJump() {
        return ((ExLivingEntity) this.entityLiving).getCanWallJump();
    }

    public void setCanWallJump(boolean b) {
        ((ExLivingEntity) this.entityLiving).setCanWallJump(b);
    }

    public int getJumpsInAirLeft() {
        return ((ExLivingEntity) this.entityLiving).getJumpsLeft();
    }

    public void setJumpsInAirLeft(int i) {
        ((ExLivingEntity) this.entityLiving).setJumpsLeft(i);
    }

    public double getGravity() {
        return ((ExLivingEntity) this.entityLiving).getGravity();
    }

    public void setGravity(double g) {
        ((ExLivingEntity) this.entityLiving).setGravity(g);
    }

    public double getJumpVelocity() {
        return ((ExLivingEntity) this.entityLiving).getJumpVelocity();
    }

    public void setJumpVelocity(double v) {
        ((ExLivingEntity) this.entityLiving).setJumpVelocity(v);
    }

    public double getJumpWallMultiplier() {
        return ((ExLivingEntity) this.entityLiving).getJumpWallMultiplier();
    }

    public void setJumpWallMultiplier(double m) {
        ((ExLivingEntity) this.entityLiving).setJumpWallMultiplier(m);
    }

    public double getJumpInAirMultiplier() {
        return ((ExLivingEntity) this.entityLiving).getJumpInAirMultiplier();
    }

    public void setJumpInAirMultiplier(double m) {
        ((ExLivingEntity) this.entityLiving).setJumpInAirMultiplier(m);
    }

    public boolean getShouldJump() {
        return ((AccessLivingEntity) this.entityLiving).isJumping();
    }

    public void setShouldJump(boolean j) {
        ((AccessLivingEntity) this.entityLiving).setJumping(j);
    }

    public float getAirControl() {
        return ((ExLivingEntity) this.entityLiving).getAirControl();
    }

    public void setAirControl(float j) {
        ((ExLivingEntity) this.entityLiving).setAirControl(j);
    }

    public void fireBullet(float spread, int damage) {
        UtilBullet.fireBullet(this.entityLiving.level, this.entityLiving, spread, damage);
    }

    public float getFov() {
        return ((ExLivingEntity) this.entityLiving).getFov();
    }

    public void setFov(float f) {
        ((ExLivingEntity) this.entityLiving).setFov(f);
    }

    public boolean getCanLookRandomly() {
        return ((ExLivingEntity) this.entityLiving).getCanLookRandomly();
    }

    public void setCanLookRandomly(boolean b) {
        ((ExLivingEntity) this.entityLiving).setCanLookRandomly(b);
    }

    public float getRandomLookVelocity() {
        return ((ExLivingEntity) this.entityLiving).getRandomLookVelocity();
    }

    public void setRandomLookVelocity(float f) {
        ((ExLivingEntity) this.entityLiving).setRandomLookVelocity(f);
    }

    public int getRandomLookNext() {
        return ((ExLivingEntity) this.entityLiving).getRandomLookNext();
    }

    public void setRandomLookNext(int i) {
        ((ExLivingEntity) this.entityLiving).setRandomLookNext(i);
    }

    public int getRandomLookRate() {
        return ((ExLivingEntity) this.entityLiving).getRandomLookRate();
    }

    public void setRandomLookRate(int i) {
        ((ExLivingEntity) this.entityLiving).setRandomLookRate(i);
    }

    public int getRandomLookRateVariation() {
        return ((ExLivingEntity) this.entityLiving).getRandomLookRateVariation();
    }

    public void setRandomLookRateVariation(int i) {
        ((ExLivingEntity) this.entityLiving).setRandomLookRateVariation(i);
    }
}
