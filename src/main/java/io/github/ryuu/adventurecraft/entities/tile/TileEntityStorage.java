package io.github.ryuu.adventurecraft.entities.tile;

import java.util.ArrayList;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.level.chunk.Chunk;
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
                    int blockID = this.level.getTileId(x, y, z);
                    int metadata = this.level.getTileMeta(x, y, z);
                    this.blockIDs[offset] = (byte) Chunk.translate128(blockID);
                    this.metadatas[offset] = (byte) metadata;
                    TileEntity te = this.level.getTileEntity(x, y, z);
                    if (te != null) {
                        CompoundTag tag = new CompoundTag();
                        te.writeIdentifyingData(tag);
                        this.tileEntities.add(tag);
                    }
                    offset++;
                }
            }
        }
        this.level.getChunk(this.x, this.z).method_885();
    }

    public void loadCurrentArea() {
        if (this.blockIDs == null)
            return;
        int offset = 0;
        for (int x = this.minX; x <= this.maxX; x++) {
            for (int z = this.minZ; z <= this.maxZ; z++) {
                for (int y = this.minY; y <= this.maxY; y++) {
                    int prevBlockID = this.level.getTileId(x, y, z);
                    this.level.cancelBlockUpdate(x, y, z, prevBlockID);
                    int blockID = Chunk.translate256(this.blockIDs[offset]);
                    int metadata = this.metadatas[offset];
                    this.level.method_201(x, y, z, blockID, metadata);
                    this.level.removeTileEntity(x, y, z);
                    offset++;
                }
            }
        }
        for (CompoundTag tag : this.tileEntities) {
            TileEntity te = TileEntity.method_1068(tag);
            this.level.setTileEntity(te.x, te.y, te.z, te);
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag nbttagcompound) {
        super.readIdentifyingData(nbttagcompound);
        if (nbttagcompound.containsKey("blockIDs"))
            this.blockIDs = nbttagcompound.getByteArray("blockIDs");
        if (nbttagcompound.containsKey("metadatas"))
            this.metadatas = nbttagcompound.getByteArray("metadatas");
        if (nbttagcompound.containsKey("numTiles")) {
            this.tileEntities.clear();
            int numTiles = nbttagcompound.getInt("numTiles");
            for (int i = 0; i < numTiles; i++) {
                this.tileEntities.add(nbttagcompound.getCompoundTag(String.format("tile%d", i)));
            }
        }
        if (!nbttagcompound.containsKey("acVersion"))
            if (Minecraft.minecraftInstance.f.x.originallyFromAC)
                Blocks.convertACVersion(this.blockIDs);
    }

    @Override
    public void writeIdentifyingData(CompoundTag nbttagcompound) {
        super.writeIdentifyingData(nbttagcompound);
        if (this.blockIDs != null)
            nbttagcompound.put("blockIDs", this.blockIDs);
        if (this.metadatas != null)
            nbttagcompound.put("metadatas", this.metadatas);
        if (!this.tileEntities.isEmpty()) {
            int i = 0;
            for (CompoundTag tag : this.tileEntities) {
                nbttagcompound.put(String.format("tile%d", i), tag);
                i++;
            }
            nbttagcompound.put("numTiles", i);
        }
        nbttagcompound.put("acVersion", 0);
    }
}
