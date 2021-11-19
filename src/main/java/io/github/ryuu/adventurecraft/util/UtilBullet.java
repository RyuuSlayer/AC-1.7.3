package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.util.List
 *  java.util.Random
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;

public class UtilBullet {

    static Random rand = new Random();

    public static void fireBullet(Level worldObj, LivingEntity e, float spread, int damage) {
        HitResult hit = UtilBullet.findHit(worldObj, e, spread);
        if (hit != null) {
            Vec3f hitLoc = hit.field_1988;
            Minecraft.minecraftInstance.worldRenderer.addParticle("smoke", hitLoc.x, hitLoc.y, hitLoc.z, 0.0, 0.0, 0.0);
            if (hit.type == HitType.ENTITY) {
                Entity attacking = hit.field_1989;
                attacking.attackEntityFromMulti(e, damage);
            }
        }
    }

    public static HitResult rayTraceBlocks(Level worldObj, Vec3f start, Vec3f end) {
        return worldObj.rayTraceBlocks2(start, end, false, false, false);
    }

    static HitResult findHit(Level worldObj, LivingEntity e, float spread) {
        double d = 256.0;
        Vec3f pos = e.method_931(1.0f);
        Vec3f lookDir = e.method_926(1.0f);
        lookDir.x += (double) spread * (2.0 * rand.nextDouble() - 1.0);
        lookDir.y += (double) spread * (2.0 * rand.nextDouble() - 1.0);
        lookDir.z += (double) spread * (2.0 * rand.nextDouble() - 1.0);
        Vec3f hitLoc = pos.method_1301(lookDir.x * d, lookDir.y * d, lookDir.z * d);
        if (e.standingEyeHeight == 0.0f) {
            pos.y += (double) (e.height / 2.0f);
        }
        return UtilBullet.rayTrace(worldObj, e, pos, hitLoc);
    }

    public static HitResult rayTrace(Level worldObj, Entity ignore, Vec3f startVec, Vec3f endVec) {
        Vec3f hitLoc = endVec;
        Vec3f temp = Vec3f.from(startVec.x, startVec.y, startVec.z);
        HitResult hit = UtilBullet.rayTraceBlocks(worldObj, temp, endVec);
        if (hit != null) {
            hitLoc = hit.field_1988;
        }
        double d = hitLoc.method_1294(startVec);
        Entity hitEntity = null;
        float f1 = 1.0f;
        Box aabb = Box.getOrCreate(Math.min((double) startVec.x, (double) hitLoc.x), Math.min((double) startVec.y, (double) hitLoc.y), Math.min((double) startVec.z, (double) hitLoc.z), Math.max((double) startVec.x, (double) hitLoc.x), Math.max((double) startVec.y, (double) hitLoc.y), Math.max((double) startVec.z, (double) hitLoc.z)).expand(f1, f1, f1);
        List list = worldObj.getEntities(ignore, aabb);
        double d2 = d;
        for (int i = 0; i < list.size(); ++i) {
            double d3;
            Entity entity = (Entity) list.get(i);
            if (!entity.method_1356())
                continue;
            float f2 = entity.method_1369();
            Box axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
            HitResult movingobjectposition = axisalignedbb.method_89(startVec, hitLoc);
            if (axisalignedbb.method_88(startVec)) {
                if (!(0.0 < d2))
                    continue;
                hitEntity = entity;
                hitLoc = startVec;
                d2 = 0.0;
                continue;
            }
            if (movingobjectposition == null || !((d3 = startVec.method_1294(movingobjectposition.field_1988)) < d2))
                continue;
            hitEntity = entity;
            hitLoc = movingobjectposition.field_1988;
            d2 = d3;
        }
        if (hitEntity != null) {
            hit = new HitResult(hitEntity);
            hit.field_1988 = hitLoc;
        }
        return hit;
    }
}
