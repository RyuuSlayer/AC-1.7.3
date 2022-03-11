package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.extensions.entity.ExEntity;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.Vec3d;

import java.util.List;
import java.util.Random;

public class UtilBullet {

    static Random rand = new Random();

    public static void fireBullet(Level worldObj, LivingEntity e, float spread, int damage) {
        HitResult hit = UtilBullet.findHit(worldObj, e, spread);
        if (hit != null) {
            Vec3d hitLoc = hit.pos;
            AccessMinecraft.getInstance().worldRenderer.addParticle("smoke", hitLoc.x, hitLoc.y, hitLoc.z, 0.0, 0.0, 0.0);
            if (hit.type == HitType.ENTITY) {
                Entity attacking = hit.entity;
                ((ExEntity) attacking).attackEntityFromMulti(e, damage);
            }
        }
    }

    public static HitResult rayTraceBlocks(Level world, Vec3d start, Vec3d end) {
        return ((ExLevel) world).rayTraceBlocks2(start, end, false, false, false);
    }

    static HitResult findHit(Level worldObj, LivingEntity e, float spread) {
        double d = 256.0;
        Vec3d pos = e.getPos(1.0f);
        Vec3d lookDir = e.getRotation(1.0f);
        lookDir.x += (double) spread * (2.0 * rand.nextDouble() - 1.0);
        lookDir.y += (double) spread * (2.0 * rand.nextDouble() - 1.0);
        lookDir.z += (double) spread * (2.0 * rand.nextDouble() - 1.0);
        Vec3d hitLoc = pos.add(lookDir.x * d, lookDir.y * d, lookDir.z * d);
        if (e.standingEyeHeight == 0.0f) {
            pos.y += e.height / 2.0f;
        }
        return UtilBullet.rayTrace(worldObj, e, pos, hitLoc);
    }

    public static HitResult rayTrace(Level worldObj, Entity ignore, Vec3d startVec, Vec3d endVec) {
        Vec3d hitLoc = endVec;
        Vec3d temp = Vec3d.getOrCreate(startVec.x, startVec.y, startVec.z);
        HitResult hit = UtilBullet.rayTraceBlocks(worldObj, temp, endVec);
        if (hit != null) {
            hitLoc = hit.pos;
        }
        double d = hitLoc.getDistance(startVec);
        Entity hitEntity = null;
        float f1 = 1.0f;
        Box aabb = Box.getOrCreate(Math.min(startVec.x, hitLoc.x), Math.min(startVec.y, hitLoc.y), Math.min(startVec.z, hitLoc.z), Math.max(startVec.x, hitLoc.x), Math.max(startVec.y, hitLoc.y), Math.max(startVec.z, hitLoc.z)).expand(f1, f1, f1);
        List list = worldObj.getEntities(ignore, aabb);
        double d2 = d;
        for (Object o : list) {
            double d3;
            Entity entity = (Entity) o;
            if (!entity.method_1356()) continue;
            float f2 = entity.method_1369();
            Box axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
            HitResult movingobjectposition = axisalignedbb.method_89(startVec, hitLoc);
            if (axisalignedbb.isVectorIn(startVec)) {
                if (!(0.0 < d2)) continue;
                hitEntity = entity;
                hitLoc = startVec;
                d2 = 0.0;
                continue;
            }
            if (movingobjectposition == null || !((d3 = startVec.getDistance(movingobjectposition.pos)) < d2))
                continue;
            hitEntity = entity;
            hitLoc = movingobjectposition.pos;
            d2 = d3;
        }
        if (hitEntity != null) {
            hit = new HitResult(hitEntity);
            hit.pos = hitLoc;
        }
        return hit;
    }
}
