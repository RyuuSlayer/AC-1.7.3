package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.entities.EntityNPC;
import io.github.ryuu.adventurecraft.util.TriggerArea;

public class TileEntityNpcPath extends TileEntityMinMax {
    private int entityID;

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.entityID = nbttagcompound.e("entityID");
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("entityID", this.entityID);
    }

    public EntityNPC getNPC() {
        if (this.npc == null || this.npc.aD != this.entityID) {
            if (this.d != null) {
                sn e = this.d.getEntityByID(this.entityID);
                if (e instanceof EntityNPC) {
                    this.npc = (EntityNPC)e;
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
            this.entityID = lastEntity.aD;
            this.npc = lastEntity;
        }
    }

    void pathEntity() {
        EntityNPC ent = getNPC();
        if (ent != null) {
            this.npc.pathToPosition(this.e, this.f, this.g);
            if (isSet())
                this.npc.triggerOnPath = this;
        }
    }

    void pathFinished() {
        if (isSet()) {
            this.d.triggerManager.addArea(this.e, this.f, this.g, new TriggerArea(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ));
            this.d.triggerManager.removeArea(this.e, this.f, this.g);
        }
    }

    private EntityNPC npc = null;

    public static EntityNPC lastEntity = null;
}