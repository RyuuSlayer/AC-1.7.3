package io.github.ryuu.adventurecraft.scripting;

import java.util.ArrayList;
import java.util.List;

import io.github.ryuu.adventurecraft.entities.EntityLivingScript;
import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.util.UtilBullet; jc;
import net.minecraft.entity.*;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
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
            return new ScriptEntityPlayer((Player) e);
        if (e instanceof Monster)
            return new ScriptEntityMob((Monster) e);
        if (e instanceof Wolf)
            return new ScriptEntityWolf((Wolf) e);
        if (e instanceof WalkingEntity)
            return new ScriptEntityCreature((WalkingEntity) e);
        if (e instanceof FlyingEntity)
            return new ScriptEntityFlying((FlyingEntity) e);
        if (e instanceof EntityNPC)
            return new ScriptEntityNPC((EntityNPC) e);
        if (e instanceof EntityLivingScript)
            return new ScriptEntityLivingScript((EntityLivingScript) e);
        if (e instanceof Slime)
            return new ScriptEntitySlime((Slime) e);
        if (e instanceof LivingEntity)
            return new ScriptEntityLiving((LivingEntity) e);
        if (e instanceof Arrow)
            return new ScriptEntityArrow((Arrow) e);
        return new ScriptEntity(e);
    }

    public int getEntityID() {
        return this.entity.id;
    }

    public ScriptVec3 getPosition() {
        return new ScriptVec3(this.entity.prevX, this.entity.prevY, this.entity.prevZ);
    }

    public void setPosition(ScriptVec3 p) {
        setPosition(p.x, p.y, p.z);
    }

    ScriptVec3 getPosition(float f) {
        float iF = 1.0F - f;
        return new ScriptVec3(iF * this.entity.prevX + f * this.entity.x, iF * this.entity.prevY + f * this.entity.y, iF * this.entity.prevZ + f * this.entity.z);
    }

    public void setPosition(double x, double y, double z) {
        this.entity.setPosition(x, y, z);
    }

    public ScriptVecRot getRotation() {
        return new ScriptVecRot(this.entity.yaw, this.entity.pitch);
    }

    ScriptVecRot getRotation(float f) {
        float iF = 1.0F - f;
        return new ScriptVecRot(iF * this.entity.prevYaw + f * this.entity.yaw, iF * this.entity.prevPitch + f * this.entity.pitch);
    }

    public void setRotation(float yaw, float pitch) {
        this.entity.setRotation(yaw, pitch);
    }

    public ScriptVec3 getVelocity() {
        return new ScriptVec3(this.entity.velocityX, this.entity.velocityY, this.entity.velocityZ);
    }

    public void setVelocity(ScriptVec3 v) {
        setVelocity(v.x, v.y, v.z);
    }

    public void setVelocity(double x, double y, double z) {
        this.entity.setVelocity(x, y, z);
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
        return this.entity.isAlive();
    }

    public boolean isRiding() {
        return this.entity.method_1360();
    }

    public boolean isSneaking() {
        return this.entity.method_1373();
    }

    public ScriptEntity[] getEntitiesWithinRange(double dist) {
        Box bb = Box.create(this.entity.x - dist, this.entity.y - dist, this.entity.z - dist, this.entity.x + dist, this.entity.y + dist, this.entity.z + dist);
        List entities = this.entity.level.getEntities(this.entity, bb);
        List<ScriptEntity> scriptEntities = new ArrayList<>();
        double sqDist = dist * dist;
        for (Object ent : entities) {
            Entity e = (Entity) ent;
            if (e.method_1352(this.entity) < sqDist)
                scriptEntities.add(getEntityClass(e));
        }
        int i = 0;
        ScriptEntity[] returnList = new ScriptEntity[scriptEntities.size()];
        for (ScriptEntity e : scriptEntities)
            returnList[i++] = e;
        return returnList;
    }

    public ScriptEntity dropItem(ScriptItem item) {
        return getEntityClass(this.entity.dropItem(item.item, 0.0F));
    }

    public boolean isInsideOfWater() {
        return this.entity.isInFluid(Material.WATER);
    }

    public boolean isInsideOfLava() {
        return this.entity.isInFluid(Material.LAVA);
    }

    public boolean getImmuneToFire() {
        return this.entity.immuneToFire;
    }

    public void setImmuneToFire(boolean i) {
        this.entity.immuneToFire = i;
    }

    public int getFireLevel() {
        return this.entity.fire;
    }

    public void setFireLevel(int f) {
        this.entity.fire = f;
    }

    public int getFireResistance() {
        return this.entity.field_1646;
    }

    public void setFireResistance(int f) {
        this.entity.field_1646 = f;
    }

    public int getAir() {
        return this.entity.air;
    }

    public void setAir(int i) {
        this.entity.air = i;
    }

    public int getMaxAir() {
        return this.entity.field_1648;
    }

    public void setMaxAir(int i) {
        this.entity.field_1648 = i;
    }

    public int getStunned() {
        return this.entity.stunned;
    }

    public void setStunned(int i) {
        this.entity.stunned = i;
    }

    public boolean attackEntityFrom(ScriptEntity e, int i) {
        return this.entity.damage(e.entity, i);
    }

    public String getClassType() {
        if (this.entity instanceof Player)
            return "Player";
        return EntityRegistry.getEntityStringClimbing(this.entity);
    }

    public boolean getCollidesWithClipBlocks() {
        return this.entity.collidesWithClipBlocks;
    }

    public void setCollidesWithClipBlocks(boolean b) {
        this.entity.collidesWithClipBlocks = b;
    }

    public float getHeight() {
        return this.entity.height;
    }

    public void setHeight(float h) {
        this.entity.height = h;
        this.entity.setPosition(this.entity.x, this.entity.y, this.entity.z);
    }

    public float getWidth() {
        return this.entity.width;
    }

    public void setWidth(float w) {
        this.entity.width = w;
        this.entity.setPosition(this.entity.x, this.entity.y, this.entity.z);
    }

    public boolean getIsFlying() {
        return this.entity.isFlying;
    }

    public void setIsFlying(boolean b) {
        this.entity.isFlying = b;
    }

    public boolean getOnGround() {
        return this.entity.onGround;
    }

    public Object[] rayTrace(ScriptVec3 start, ScriptVec3 end) {
        return rayTrace(start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public Object[] rayTrace(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        Object[] results = new Object[3];
        HitResult hit = UtilBullet.rayTrace(this.entity.level, this.entity, Vec3f.from(startX, startY, startZ), Vec3f.from(endX, endY, endZ));
        if (hit != null) {
            results[0] = new ScriptVec3(hit.field_1988.x, hit.field_1988.y, hit.field_1988.z);
            if (hit.type == HitType.a) {
                results[1] = new ScriptVec3(hit.x, hit.y, hit.z);
            } else {
                results[2] = getEntityClass(hit.field_1989);
            }
        }
        return results;
    }

    public float getyOffset() {
        return this.entity.standingEyeHeight;
    }

    public void setyOffset(float y) {
        this.entity.standingEyeHeight = y;
    }
}