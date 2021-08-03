package io.github.ryuu.adventurecraft.scripting;

import bt;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import io.github.ryuu.adventurecraft.util.UtilBullet;
import jc;
import jg;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import sn;

public class ScriptWorld {
    Level worldObj;

    ScriptWorld(Level w) {
        this.worldObj = w;
    }

    public int getBlockID(int x, int y, int z) {
        return this.worldObj.a(x, y, z);
    }

    public void setBlockID(int x, int y, int z, int id) {
        this.worldObj.f(x, y, z, id);
    }

    public int getMetadata(int x, int y, int z) {
        return this.worldObj.e(x, y, z);
    }

    public void setMetadata(int x, int y, int z, int m) {
        this.worldObj.d(x, y, z, m);
    }

    public void setBlockIDAndMetadata(int x, int y, int z, int id, int m) {
        this.worldObj.b(x, y, z, id, m);
    }

    public float getLightValue(int x, int y, int z) {
        return this.worldObj.getLightValue(x, y, z);
    }

    public void triggerBlock(int blockX, int blockY, int blockZ) {
        triggerArea(blockX, blockY, blockZ, blockX, blockY, blockZ);
    }

    public void triggerArea(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.worldObj.triggerManager.addArea(0, -1, 0, new TriggerArea(minX, minY, minZ, maxX, maxY, maxZ));
        this.worldObj.triggerManager.removeArea(0, -1, 0);
    }

    public void setTriggerArea(int blockX, int blockY, int blockZ, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.worldObj.triggerManager.addArea(blockX, blockY, blockZ, new TriggerArea(minX, minY, minZ, maxX, maxY, maxZ));
    }

    public void setTriggerArea(int blockX, int blockY, int blockZ, int areaID, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.worldObj.triggerManager.addArea(blockX, blockY, blockZ, areaID, new TriggerArea(minX, minY, minZ, maxX, maxY, maxZ));
    }

    public void removeTriggerArea(int blockX, int blockY, int blockZ, int areaID) {
        this.worldObj.triggerManager.removeArea(blockX, blockY, blockZ, areaID);
    }

    public void removeTriggerAreas(int blockX, int blockY, int blockZ) {
        this.worldObj.triggerManager.removeArea(blockX, blockY, blockZ);
    }

    public ScriptEntity spawnEntity(String entityType, double x, double y, double z) {
        sn e = jc.a(entityType, this.worldObj);
        if (e != null) {
            e.e(x, y, z);
            this.worldObj.g(e);
        }
        return ScriptEntity.getEntityClass(e);
    }

    public ScriptEntity getEntityByID(int entityID) {
        return ScriptEntity.getEntityClass(this.worldObj.getEntityByID(entityID));
    }

    public Object[] rayTraceBlocks(ScriptVec3 start, ScriptVec3 end) {
        return rayTraceBlocks(start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public Object[] rayTraceBlocks(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        Object[] results = new Object[2];
        HitResult hit = UtilBullet.rayTraceBlocks(this.worldObj, bt.b(startX, startY, startZ), bt.b(endX, endY, endZ));
        if (hit != null) {
            results[0] = new ScriptVec3(hit.f.a, hit.f.b, hit.f.c);
            results[1] = new ScriptVec3(hit.b, hit.c, hit.d);
        }
        return results;
    }

    public Object[] rayTrace(ScriptVec3 start, ScriptVec3 end) {
        return rayTrace(start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public Object[] rayTrace(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        Object[] results = new Object[3];
        HitResult hit = UtilBullet.rayTrace(this.worldObj, null, bt.b(startX, startY, startZ), bt.b(endX, endY, endZ));
        if (hit != null) {
            results[0] = new ScriptVec3(hit.f.a, hit.f.b, hit.f.c);
            if (hit.a == jg.a) {
                results[1] = new ScriptVec3(hit.b, hit.c, hit.d);
            } else {
                results[2] = ScriptEntity.getEntityClass(hit.g);
            }
        }
        return results;
    }
}