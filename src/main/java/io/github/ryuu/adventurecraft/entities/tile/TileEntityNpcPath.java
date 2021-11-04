package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.entity.Entity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityNpcPath extends TileEntityMinMax {
    private int entityID;

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        this.entityID = nbttagcompound.getInt("entityID");
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        nbttagcompound.put("entityID", this.entityID);
    }

    public EntityNPC getNPC() {
        if (this.npc == null || this.npc.id != this.entityID) {
            if (this.level != null) {
                Entity e = this.level.getEntityByID(this.entityID);
                if (e instanceof EntityNPC) {
                    this.npc = (EntityNPC) e;
                    return this.npc;
                }
            }
        } else {
            return this.npc;
        }
        return null;
    }

    public void setEntityToLastSelected() {
        if (lastEntity != null) {
            this.entityID = lastEntity.id;
            this.npc = lastEntity;
        }
    }

    public void pathEntity() {
        EntityNPC ent = getNPC();
        if (ent != null) {
            this.npc.pathToPosition(this.x, this.y, this.z);
            if (isSet())
                this.npc.triggerOnPath = this;
        }
    }

    public void pathFinished() {
        if (isSet()) {
            this.level.triggerManager.addArea(this.x, this.y, this.z, new TriggerArea(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
            this.level.triggerManager.removeArea(this.x, this.y, this.z);
        }
    }

    private EntityNPC npc = null;

    public static EntityNPC lastEntity = null;
}