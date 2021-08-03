package io.github.ryuu.adventurecraft.entities.tile;

import java.util.ArrayList;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.src.NBTTagCompound;

public class TileEntityStorage extends TileEntityMinMax {
    byte[] blockIDs = null;

    byte[] metadatas = null;

    ArrayList<NBTTagCompound> tileEntities = new ArrayList<NBTTagCompound>();

    void setArea() {
        this.minX = ItemCursor.minX;
        this.minY = ItemCursor.minY;
        this.minZ = ItemCursor.minZ;
        this.maxX = ItemCursor.maxX;
        this.maxY = ItemCursor.maxY;
        this.maxZ = ItemCursor.maxZ;
        int dX = this.maxX - this.minX + 1;
        int dY = this.maxY - this.minY + 1;
        int dZ = this.maxZ - this.minZ + 1;
        int size = dX * dY * dZ;
        this.blockIDs = new byte[size];
        this.metadatas = new byte[size];
        saveCurrentArea();
    }

    void saveCurrentArea() {
        int offset = 0;
        this.tileEntities.clear();
        for (int x = this.minX; x <= this.maxX; x++) {
            for (int z = this.minZ; z <= this.maxZ; z++) {
                for (int y = this.minY; y <= this.maxY; y++) {
                    int blockID = this.d.a(x, y, z);
                    int metadata = this.d.e(x, y, z);
                    this.blockIDs[offset] = (byte) lm.translate128(blockID);
                    this.metadatas[offset] = (byte) metadata;
                    ow te = this.d.b(x, y, z);
                    if (te != null) {
                        nu tag = new nu();
                        te.b(tag);
                        this.tileEntities.add(tag);
                    }
                    offset++;
                }
            }
        }
        this.d.b(this.e, this.g).g();
    }

    void loadCurrentArea() {
        if (this.blockIDs == null)
            return;
        int offset = 0;
        for (int x = this.minX; x <= this.maxX; x++) {
            for (int z = this.minZ; z <= this.maxZ; z++) {
                for (int y = this.minY; y <= this.maxY; y++) {
                    int prevBlockID = this.d.a(x, y, z);
                    this.d.cancelBlockUpdate(x, y, z, prevBlockID);
                    int blockID = lm.translate256(this.blockIDs[offset]);
                    int metadata = this.metadatas[offset];
                    this.d.b(x, y, z, blockID, metadata);
                    this.d.p(x, y, z);
                    offset++;
                }
            }
        }
        for (nu tag : this.tileEntities) {
            ow te = ow.c(tag);
            this.d.a(te.e, te.f, te.g, te);
        }
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("blockIDs"))
            this.blockIDs = nbttagcompound.j("blockIDs");
        if (nbttagcompound.b("metadatas"))
            this.metadatas = nbttagcompound.j("metadatas");
        if (nbttagcompound.b("numTiles")) {
            this.tileEntities.clear();
            int numTiles = nbttagcompound.e("numTiles");
            for (int i = 0; i < numTiles; i++) {
                this.tileEntities.add(nbttagcompound.k(String.format("tile%d", new Object[]{Integer.valueOf(i)})));
            }
        }
        if (!nbttagcompound.b("acVersion"))
            if (Minecraft.minecraftInstance.f.x.originallyFromAC)
                Blocks.convertACVersion(this.blockIDs);
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        if (this.blockIDs != null)
            nbttagcompound.a("blockIDs", this.blockIDs);
        if (this.metadatas != null)
            nbttagcompound.a("metadatas", this.metadatas);
        if (!this.tileEntities.isEmpty()) {
            int i = 0;
            for (nu tag : this.tileEntities) {
                nbttagcompound.a(String.format("tile%d", new Object[]{Integer.valueOf(i)}), tag);
                i++;
            }
            nbttagcompound.a("numTiles", i);
        }
        nbttagcompound.a("acVersion", 0);
    }
}
