/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.WalkingEntity;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.monster.Slime;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.script.ScriptEntityArrow;
import net.minecraft.script.ScriptEntityCreature;
import net.minecraft.script.ScriptEntityFlying;
import net.minecraft.script.ScriptEntityLiving;
import net.minecraft.script.ScriptEntityLivingScript;
import net.minecraft.script.ScriptEntityMob;
import net.minecraft.script.ScriptEntityNPC;
import net.minecraft.script.ScriptEntityPlayer;
import net.minecraft.script.ScriptEntitySlime;
import net.minecraft.script.ScriptEntityWolf;
import net.minecraft.script.ScriptItem;
import net.minecraft.script.ScriptVec3;
import net.minecraft.script.ScriptVecRot;
import net.minecraft.tile.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinFlyingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinWolf;
import io.github.ryuu.adventurecraft.mixin.item.MixinMonster;
import io.github.ryuu.adventurecraft.mixin.item.MixinLivingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinSlime;
import io.github.ryuu.adventurecraft.mixin.item.MixinWalkingEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinArrow;

public class ScriptEntity {

    MixinEntity entity;

    ScriptEntity(MixinEntity e) {
        this.entity = e;
    }

    public static ScriptEntity getEntityClass(MixinEntity e) {
        if (e == null) {
            return null;
        }
        if (e instanceof MixinPlayer) {
            return new ScriptEntityPlayer((MixinPlayer) e);
        }
        if (e instanceof MixinMonster) {
            return new ScriptEntityMob((MixinMonster) e);
        }
        if (e instanceof MixinWolf) {
            return new ScriptEntityWolf((MixinWolf) e);
        }
        if (e instanceof MixinWalkingEntity) {
            return new ScriptEntityCreature((MixinWalkingEntity) e);
        }
        if (e instanceof MixinFlyingEntity) {
            return new ScriptEntityFlying((MixinFlyingEntity) e);
        }
        if (e instanceof EntityNPC) {
            return new ScriptEntityNPC((EntityNPC) e);
        }
        if (e instanceof EntityLivingScript) {
            return new ScriptEntityLivingScript((EntityLivingScript) e);
        }
        if (e instanceof MixinSlime) {
            return new ScriptEntitySlime((MixinSlime) e);
        }
        if (e instanceof MixinLivingEntity) {
            return new ScriptEntityLiving((MixinLivingEntity) e);
        }
        if (e instanceof MixinArrow) {
            return new ScriptEntityArrow((MixinArrow) e);
        }
        return new ScriptEntity(e);
    }

    public int getEntityID() {
        return this.entity.id;
    }

    public ScriptVec3 getPosition() {
        return new ScriptVec3(this.entity.x, this.entity.y, this.entity.z);
    }

    ScriptVec3 getPosition(float f) {
        float iF = 1.0f - f;
        return new ScriptVec3((double) iF * this.entity.prevX + (double) f * this.entity.x, (double) iF * this.entity.prevY + (double) f * this.entity.y, (double) iF * this.entity.prevZ + (double) f * this.entity.z);
    }

    public void setPosition(ScriptVec3 p) {
        this.setPosition(p.x, p.y, p.z);
    }

    public void setPosition(double x, double y, double z) {
        this.entity.setPosition(x, y, z);
    }

    public ScriptVecRot getRotation() {
        return new ScriptVecRot(this.entity.yaw, this.entity.pitch);
    }

    ScriptVecRot getRotation(float f) {
        float iF = 1.0f - f;
        return new ScriptVecRot(iF * this.entity.prevYaw + f * this.entity.yaw, iF * this.entity.prevPitch + f * this.entity.pitch);
    }

    public void setRotation(float yaw, float pitch) {
        this.entity.setRotation(yaw, pitch);
    }

    public ScriptVec3 getVelocity() {
        return new ScriptVec3(this.entity.velocityX, this.entity.velocityY, this.entity.velocityZ);
    }

    public void setVelocity(ScriptVec3 v) {
        this.setVelocity(v.x, v.y, v.z);
    }

    public void setVelocity(double x, double y, double z) {
        this.entity.setVelocity(x, y, z);
    }

    public void addVelocity(ScriptVec3 v) {
        this.addVelocity(v.x, v.y, v.z);
    }

    public void addVelocity(double x, double y, double z) {
        this.entity.method_1322(x, y, z);
    }

    public void setDead() {
        this.entity.remove();
    }

    public void mountEntity(ScriptEntity e) {
        if (e != null) {
            this.entity.startRiding(e.entity);
        } else {
            this.entity.startRiding(null);
        }
    }

    public ScriptEntity getMountedEntity() {
        return ScriptEntity.getEntityClass(this.entity.vehicle);
    }

    public boolean isBurning() {
        return this.entity.method_1359();
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
        Box bb = Box.getOrCreate(this.entity.x - dist, this.entity.y - dist, this.entity.z - dist, this.entity.x + dist, this.entity.y + dist, this.entity.z + dist);
        List entities = this.entity.level.getEntities(this.entity, bb);
        ArrayList scriptEntities = new ArrayList();
        double sqDist = dist * dist;
        for (Object ent : entities) {
            MixinEntity e = (MixinEntity) ent;
            if (!(e.method_1352(this.entity) < sqDist))
                continue;
            scriptEntities.add((Object) ScriptEntity.getEntityClass(e));
        }
        int i = 0;
        ScriptEntity[] returnList = new ScriptEntity[scriptEntities.size()];
        for (ScriptEntity e : scriptEntities) {
            returnList[i++] = e;
        }
        return returnList;
    }

    public ScriptEntity dropItem(ScriptItem item) {
        return ScriptEntity.getEntityClass(this.entity.dropItem(item.item, 0.0f));
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
        if (this.entity instanceof MixinPlayer) {
            return "Player";
        }
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

    public void setIsFlying(boolean b) {
        this.entity.isFlying = b;
    }

    public boolean getIsFlying() {
        return this.entity.isFlying;
    }

    public boolean getOnGround() {
        return this.entity.onGround;
    }

    public Object[] rayTrace(ScriptVec3 start, ScriptVec3 end) {
        return this.rayTrace(start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public Object[] rayTrace(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        Object[] results = new Object[3];
        HitResult hit = UtilBullet.rayTrace(this.entity.level, this.entity, Vec3f.from(startX, startY, startZ), Vec3f.from(endX, endY, endZ));
        if (hit != null) {
            results[0] = new ScriptVec3(hit.field_1988.x, hit.field_1988.y, hit.field_1988.z);
            if (hit.type == HitType.TILE) {
                results[1] = new ScriptVec3(hit.x, hit.y, hit.z);
            } else {
                results[2] = ScriptEntity.getEntityClass(hit.field_1989);
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
