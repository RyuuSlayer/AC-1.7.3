package io.github.ryuu.adventurecraft.util;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3f;

public class UtilBullet {
    public static void fireBullet(Level worldObj, LivingEntity e, float spread, int damage) {
        HitResult hit = findHit(worldObj, e, spread);
        if (hit != null) {
            Vec3f hitLoc = hit.f;
            Minecraft.minecraftInstance.g.a("smoke", hitLoc.a, hitLoc.b, hitLoc.c, 0.0D, 0.0D, 0.0D);
            if (hit.a == jg.b) {
                Entity attacking = hit.g;
                attacking.attackEntityFromMulti(e, damage);
            }
        }
    }

    public static HitResult rayTraceBlocks(Level worldObj, Vec3f start, Vec3f end) {
        return worldObj.rayTraceBlocks2(start, end, false, false, false);
    }

    static HitResult findHit(Level worldObj, LivingEntity e, float spread) {
        double d = 256.0D;
        Vec3f pos = e.e(1.0F);
        Vec3f lookDir = e.f(1.0F);
        lookDir.a += spread * (2.0D * rand.nextDouble() - 1.0D);
        lookDir.b += spread * (2.0D * rand.nextDouble() - 1.0D);
        lookDir.c += spread * (2.0D * rand.nextDouble() - 1.0D);
        Vec3f hitLoc = pos.c(lookDir.a * d, lookDir.b * d, lookDir.c * d);
        if (e.bf == 0.0F)
            pos.b += (e.bh / 2.0F);
        return rayTrace(worldObj, e, pos, hitLoc);
    }

    public static HitResult rayTrace(Level worldObj, Entity ignore, Vec3f startVec, Vec3f endVec) {
        Vec3f hitLoc = endVec;
        Vec3f temp = Vec3f.b(startVec.a, startVec.b, startVec.c);
        HitResult hit = rayTraceBlocks(worldObj, temp, endVec);
        if (hit != null)
            hitLoc = hit.f;
        double d = hitLoc.c(startVec);
        Entity hitEntity = null;
        float f1 = 1.0F;
        Box aabb = Box.b(Math.min(startVec.a, hitLoc.a), Math.min(startVec.b, hitLoc.b), Math.min(startVec.c, hitLoc.c), Math.max(startVec.a, hitLoc.a), Math.max(startVec.b, hitLoc.b), Math.max(startVec.c, hitLoc.c)).b(f1, f1, f1);
        List<Entity> list = worldObj.b(ignore, aabb);
        double d2 = d;
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entity.h_()) {
                float f2 = entity.m_();
                Box axisalignedbb = entity.aW.b(f2, f2, f2);
                HitResult movingobjectposition = axisalignedbb.a(startVec, hitLoc);
                if (axisalignedbb.a(startVec)) {
                    if (0.0D < d2) {
                        hitEntity = entity;
                        hitLoc = startVec;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = startVec.c(movingobjectposition.f);
                    if (d3 < d2) {
                        hitEntity = entity;
                        hitLoc = movingobjectposition.f;
                        d2 = d3;
                    }
                }
            }
        }
        if (hitEntity != null) {
            hit = new HitResult(hitEntity);
            hit.f = hitLoc;
        }
        return hit;
    }

    static Random rand = new Random();
}