package io.github.ryuu.adventurecraft.util;

class EditAction {
    EditAction nextAction;

    int x;

    int y;

    int z;

    int prevBlockID;

    int prevMetadata;

    nu prevNBT;

    int newBlockID;

    int newMetadata;

    nu newNBT;

    AC_EditAction(int i, int j, int k, int pID, int pMeta, nu pNBT, int nID, int nMeta, nu nNBT) {
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

    void undo(fd world) {
        world.b(this.x, this.y, this.z, this.prevBlockID, this.prevMetadata);
        if (this.prevNBT != null) {
            ow te = ow.c(this.prevNBT);
            world.a(te.e, te.f, te.g, te);
        }
        if (this.nextAction != null)
            this.nextAction.undo(world);
    }

    void redo(fd world) {
        world.b(this.x, this.y, this.z, this.newBlockID, this.newMetadata);
        if (this.newNBT != null) {
            ow te = ow.c(this.newNBT);
            world.a(te.e, te.f, te.g, te);
        }
        if (this.nextAction != null)
            this.nextAction.redo(world);
    }
}
