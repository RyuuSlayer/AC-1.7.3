package io.github.ryuu.adventurecraft.scripting;

import bt;
import java.util.ArrayList;
import java.util.List;

import io.github.ryuu.adventurecraft.entities.EntityLivingScript;
import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import jc;
import jg;
import ln;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;

public class ScriptEntity {
    Entity entity;

    ScriptEntity(Entity e) {
        this.entity = e;
    }

    public static ScriptEntity getEntityClass(Entity e) {
        if (e == null)
            return null;
        if (e instanceof Player)
            return new ScriptEntityPlayer((Player)e);
        if (e instanceof Monster)
            return new ScriptEntityMob((Monster)e);
        if (e instanceof Wolf)
            return new ScriptEntityWolf((Wolf)e);
        if (e instanceof WalkingEntity)
            return new ScriptEntityCreature((WalkingEntity)e);
        if (e instanceof FlyingEntity)
            return new ScriptEntityFlying((FlyingEntity)e);
        if (e instanceof EntityNPC)
            return new ScriptEntityNPC((EntityNPC)e);
        if (e instanceof EntityLivingScript)
            return new ScriptEntityLivingScript((EntityLivingScript)e);
        if (e instanceof Slime)
            return new ScriptEntitySlime((Slime)e);
        if (e instanceof LivingEntity)
            return new ScriptEntityLiving((LivingEntity)e);
        if (e instanceof Arrow)
            return new ScriptEntityArrow((Arrow) e);
        return new ScriptEntity(e);
    }

    public int getEntityID() {
        return this.entity.aD;
    }

    public ScriptVec3 getPosition() {
        return new ScriptVec3(this.entity.aM, this.entity.aN, this.entity.aO);
    }

    ScriptVec3 getPosition(float f) {
        float iF = 1.0F - f;
        return new ScriptVec3(iF * this.entity.aJ + f * this.entity.aM, iF * this.entity.aK + f * this.entity.aN, iF * this.entity.aL + f * this.entity.aO);
    }

    public void setPosition(ScriptVec3 p) {
        setPosition(p.x, p.y, p.z);
    }

    public void setPosition(double x, double y, double z) {
        this.entity.e(x, y, z);
    }

    public ScriptVecRot getRotation() {
        return new ScriptVecRot(this.entity.aS, this.entity.aT);
    }

    ScriptVecRot getRotation(float f) {
        float iF = 1.0F - f;
        return new ScriptVecRot(iF * this.entity.aU + f * this.entity.aS, iF * this.entity.aV + f * this.entity.aT);
    }

    public void setRotation(float yaw, float pitch) {
        this.entity.c(yaw, pitch);
    }

    public ScriptVec3 getVelocity() {
        return new ScriptVec3(this.entity.aP, this.entity.aQ, this.entity.aR);
    }

    public void setVelocity(ScriptVec3 v) {
        setVelocity(v.x, v.y, v.z);
    }

    public void setVelocity(double x, double y, double z) {
        this.entity.a(x, y, z);
    }

    public void addVelocity(ScriptVec3 v) {
        addVelocity(v.x, v.y, v.z);
    }

    public void addVelocity(double x, double y, double z) {
        this.entity.d(x, y, z);
    }

    public void setDead() {
        this.entity.K();
    }

    public void mountEntity(ScriptEntity e) {
        if (e != null) {
            this.entity.i(e.entity);
        } else {
            this.entity.i(null);
        }
    }

    public ScriptEntity getMountedEntity() {
        return getEntityClass(this.entity.aH);
    }

    public boolean isBurning() {
        return this.entity.ak();
    }

    public boolean isAlive() {
        return this.entity.W();
    }

    public boolean isRiding() {
        return this.entity.al();
    }

    public boolean isSneaking() {
        return this.entity.t();
    }

    public ScriptEntity[] getEntitiesWithinRange(double dist) {
        Box bb = Box.b(this.entity.aM - dist, this.entity.aN - dist, this.entity.aO - dist, this.entity.aM + dist, this.entity.aN + dist, this.entity.aO + dist);
        List entities = this.entity.aI.b(this.entity, bb);
        List<ScriptEntity> scriptEntities = new ArrayList<>();
        double sqDist = dist * dist;
        for (Object ent : entities) {
            Entity e = (Entity)ent;
            if (e.g(this.entity) < sqDist)
                scriptEntities.add(getEntityClass(e));
        }
        int i = 0;
        ScriptEntity[] returnList = new ScriptEntity[scriptEntities.size()];
        for (ScriptEntity e : scriptEntities)
            returnList[i++] = e;
        return returnList;
    }

    public ScriptEntity dropItem(ScriptItem item) {
        return getEntityClass((Entity)this.entity.a(item.item, 0.0F));
    }

    public boolean isInsideOfWater() {
        return this.entity.a(ln.g);
    }

    public boolean isInsideOfLava() {
        return this.entity.a(ln.h);
    }

    public boolean getImmuneToFire() {
        return this.entity.bC;
    }

    public void setImmuneToFire(boolean i) {
        this.entity.bC = i;
    }

    public int getFireLevel() {
        return this.entity.bv;
    }

    public void setFireLevel(int f) {
        this.entity.bv = f;
    }

    public int getFireResistance() {
        return this.entity.bu;
    }

    public void setFireResistance(int f) {
        this.entity.bu = f;
    }

    public int getAir() {
        return this.entity.bz;
    }

    public void setAir(int i) {
        this.entity.bz = i;
    }

    public int getMaxAir() {
        return this.entity.bw;
    }

    public void setMaxAir(int i) {
        this.entity.bw = i;
    }

    public int getStunned() {
        return this.entity.stunned;
    }

    public void setStunned(int i) {
        this.entity.stunned = i;
    }

    public boolean attackEntityFrom(ScriptEntity e, int i) {
        return this.entity.a(e.entity, i);
    }

    public String getClassType() {
        if (this.entity instanceof Player)
            return "Player";
        return jc.getEntityStringClimbing(this.entity);
    }

    public boolean getCollidesWithClipBlocks() {
        return this.entity.collidesWithClipBlocks;
    }

    public void setCollidesWithClipBlocks(boolean b) {
        this.entity.collidesWithClipBlocks = b;
    }

    public float getHeight() {
        return this.entity.bh;
    }

    public void setHeight(float h) {
        this.entity.bh = h;
        this.entity.e(this.entity.aM, this.entity.aN, this.entity.aO);
    }

    public float getWidth() {
        return this.entity.bg;
    }

    public void setWidth(float w) {
        this.entity.bg = w;
        this.entity.e(this.entity.aM, this.entity.aN, this.entity.aO);
    }

    public void setIsFlying(boolean b) {
        this.entity.isFlying = b;
    }

    public boolean getIsFlying() {
        return this.entity.isFlying;
    }

    public boolean getOnGround() {
        return this.entity.aX;
    }

    public Object[] rayTrace(ScriptVec3 start, ScriptVec3 end) {
        return rayTrace(start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public Object[] rayTrace(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        Object[] results = new Object[3]
        HitResult hit = UtilBullet.rayTrace(this.entity.aI, this.entity, bt.b(startX, startY, startZ), Vec3f.b(endX, endY, endZ));
        if (hit != null) {
            results[0] = new ScriptVec3(hit.f.a, hit.f.b, hit.f.c);
            if (hit.a == jg.a) {
                results[1] = new ScriptVec3(hit.b, hit.c, hit.d);
            } else {
                results[2] = getEntityClass(hit.g);
            }
        }
        return results;
    }

    public float getyOffset() {
        return this.entity.bf;
    }

    public void setyOffset(float y) {
        this.entity.bf = y;
    }
}
