package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import io.github.ryuu.adventurecraft.util.TriggerManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityNpcPath extends TileEntityMinMax {

    public static EntityNPC lastEntity = null;
    private int entityID;
    private EntityNPC npc = null;

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
            if (this.level != null) {
                Entity e = ((ExLevel) this.level).getEntityByID(this.entityID);
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
            this.entityID = TileEntityNpcPath.lastEntity.id;
            this.npc = lastEntity;
        }
    }

    public void pathEntity() {
        EntityNPC ent = this.getNPC();
        if (ent != null) {
            this.npc.pathToPosition(this.x, this.y, this.z);
            if (this.isSet()) {
                this.npc.triggerOnPath = this;
            }
        }
    }

    public void pathFinished() {
        if (this.isSet()) {
            TriggerManager triggerManager = ((ExLevel) this.level).getTriggerManager();
            triggerManager.addArea(this.x, this.y, this.z, new TriggerArea(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
            triggerManager.removeArea(this.x, this.y, this.z);
        }
    }
}
