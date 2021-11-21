package io.github.ryuu.adventurecraft.entities.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityNpcPath extends TileEntityMinMax {

    private int entityID;

    private EntityNPC npc = null;

    public static EntityNPC lastEntity = null;

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.entityID = tag.getInt("entityID");
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("entityID", this.entityID);
    }

    public EntityNPC getNPC() {
        if (this.npc == null || this.npc.id != this.entityID) {
            Entity e;
            if (this.level != null && (e = this.level.getEntityByID(this.entityID)) instanceof EntityNPC) {
                this.npc = (EntityNPC) e;
                return this.npc;
            }
        } else {
            return this.npc;
        }
        return null;
    }

    public void setEntityToLastSelected() {
        if (lastEntity != null) {
            this.entityID = TileEntityNpcPath.lastEntity.id;
            this.npc = lastEntity;
        }
    }

    void pathEntity() {
        EntityNPC ent = this.getNPC();
        if (ent != null) {
            this.npc.pathToPosition(this.x, this.y, this.z);
            if (this.isSet()) {
                this.npc.triggerOnPath = this;
            }
        }
    }

    void pathFinished() {
        if (this.isSet()) {
            this.level.triggerManager.addArea(this.x, this.y, this.z, new TriggerArea(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
            this.level.triggerManager.removeArea(this.x, this.y, this.z);
        }
    }
}
