package io.github.ryuu.adventurecraft.entities.tile;

import java.util.ArrayList;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityStorage extends TileEntityMinMax {
    byte[] blockIDs = null;

    byte[] metadatas = null;

    ArrayList<CompoundTag> tileEntities = new ArrayList<>();

    public void setArea() {
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

    public void saveCurrentArea() {
        int offset = 0;
        this.tileEntities.clear();
        for (int x = this.minX; x <= this.maxX; x++) {
            for (int z = this.minZ; z <= this.maxZ; z++) {
                for (int y = this.minY; y <= this.maxY; y++) {
                    int blockID = this.d.a(x, y, z);
                    int metadata = this.d.e(x, y, z);
                    this.blockIDs[offset] = (byte) lm.translate128(blockID);
                    this.metadatas[offset] = (byte) metadata;
                    TileEntity te = this.d.b(x, y, z);
                    if (te != null) {
                        CompoundTag tag = new CompoundTag();
                        te.b(tag);
                        this.tileEntities.add(tag);
                    }
                    offset++;
                }
            }
        }
        this.d.b(this.e, this.g).g();
    }

    public void loadCurrentArea() {
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
        for (CompoundTag tag : this.tileEntities) {
            TileEntity te = TileEntity.c(tag);
            this.d.a(te.x, te.y, te.z, te);
        }
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.containsKey("blockIDs"))
            this.blockIDs = nbttagcompound.getByteArray("blockIDs");
        if (nbttagcompound.containsKey("metadatas"))
            this.metadatas = nbttagcompound.getByteArray("metadatas");
        if (nbttagcompound.containsKey("numTiles")) {
            this.tileEntities.clear();
            int numTiles = nbttagcompound.getInt("numTiles");
            for (int i = 0; i < numTiles; i++) {
                this.tileEntities.add(nbttagcompound.getCompoundTag(String.format("tile%d", new Object[]{Integer.valueOf(i)})));
            }
        }
        if (!nbttagcompound.containsKey("acVersion"))
            if (Minecraft.minecraftInstance.f.x.originallyFromAC)
                Blocks.convertACVersion(this.blockIDs);
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        if (this.blockIDs != null)
            nbttagcompound.put("blockIDs", this.blockIDs);
        if (this.metadatas != null)
            nbttagcompound.put("metadatas", this.metadatas);
        if (!this.tileEntities.isEmpty()) {
            int i = 0;
            for (CompoundTag tag : this.tileEntities) {
                nbttagcompound.put(String.format("tile%d", new Object[]{Integer.valueOf(i)}), tag);
                i++;
            }
            nbttagcompound.put("numTiles", i);
        }
        nbttagcompound.put("acVersion", 0);
    }
}
