package io.github.ryuu.adventurecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

    MixinCompoundTag prevNBT;

    int newBlockID;

    int newMetadata;

    MixinCompoundTag newNBT;

    EditAction(int i, int j, int k, int pID, int pMeta, MixinCompoundTag pNBT, int nID, int nMeta, MixinCompoundTag nNBT) {
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

    void undo(MixinLevel world) {
        world.method_201(this.x, this.y, this.z, this.prevBlockID, this.prevMetadata);
        if (this.prevNBT != null) {
            MixinTileEntity te = TileEntity.method_1068(this.prevNBT);
            world.setTileEntity(te.x, te.y, te.z, te);
        }
        if (this.nextAction != null) {
            this.nextAction.undo(world);
        }
    }

    void redo(MixinLevel world) {
        world.method_201(this.x, this.y, this.z, this.newBlockID, this.newMetadata);
        if (this.newNBT != null) {
            MixinTileEntity te = TileEntity.method_1068(this.newNBT);
            world.setTileEntity(te.x, te.y, te.z, te);
        }
        if (this.nextAction != null) {
            this.nextAction.redo(world);
        }
    }
}
