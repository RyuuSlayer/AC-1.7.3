package io.github.ryuu.adventurecraft.util;

import net.minecraft.level.Level;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

class EditAction {
    EditAction nextAction;

    int x;

    int y;

    int z;

    int prevBlockID;

    int prevMetadata;

    CompoundTag prevNBT;

    int newBlockID;

    int newMetadata;

    CompoundTag newNBT;

    EditAction(int i, int j, int k, int pID, int pMeta, CompoundTag pNBT, int nID, int nMeta, CompoundTag nNBT) {
        this.x = i;
        this.y = j;
        this.z = k;
        this.prevBlockID = pID;
        this.prevMetadata = pMeta;
        this.prevNBT = pNBT;
        this.newBlockID = nID;
        this.newMetadata = nMeta;
        this.newNBT = nNBT;
        this.nextAction = null;
    }

    void undo(Level world) {
        world.b(this.x, this.y, this.z, this.prevBlockID, this.prevMetadata);
        if (this.prevNBT != null) {
            TileEntity te = TileEntity.c(this.prevNBT);
            world.a(te.e, te.f, te.g, te);
        }
        if (this.nextAction != null)
            this.nextAction.undo(world);
    }

    void redo(Level world) {
        world.b(this.x, this.y, this.z, this.newBlockID, this.newMetadata);
        if (this.newNBT != null) {
            TileEntity te = TileEntity.c(this.newNBT);
            world.a(te.e, te.f, te.g, te);
        }
        if (this.nextAction != null)
            this.nextAction.redo(world);
    }
}
