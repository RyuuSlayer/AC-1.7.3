package io.github.ryuu.adventurecraft.entities.tile;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.src.NBTTagCompound
 */
import java.util.ArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;

public class TileEntityStorage extends TileEntityMinMax {

    byte[] blockIDs = null;

    byte[] metadatas = null;

    ArrayList<NBTTagCompound> tileEntities = new ArrayList();

    TileEntityStorage() {
    }

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
        this.saveCurrentArea();
    }

    void saveCurrentArea() {
        int offset = 0;
        this.tileEntities.clear();
        for (int x = this.minX; x <= this.maxX; ++x) {
            for (int z = this.minZ; z <= this.maxZ; ++z) {
                for (int y = this.minY; y <= this.maxY; ++y) {
                    int blockID = this.level.getTileId(x, y, z);
                    int metadata = this.level.getTileMeta(x, y, z);
                    this.blockIDs[offset] = (byte) Chunk.translate128(blockID);
                    this.metadatas[offset] = (byte) metadata;
                    TileEntity te = this.level.getTileEntity(x, y, z);
                    if (te != null) {
                        CompoundTag tag = new CompoundTag();
                        te.writeIdentifyingData(tag);
                        this.tileEntities.add((Object) tag);
                    }
                    ++offset;
                }
            }
        }
        this.level.getChunk(this.x, this.z).method_885();
    }

    void loadCurrentArea() {
        if (this.blockIDs == null) {
            return;
        }
        int offset = 0;
        for (int x = this.minX; x <= this.maxX; ++x) {
            for (int z = this.minZ; z <= this.maxZ; ++z) {
                for (int y = this.minY; y <= this.maxY; ++y) {
                    int prevBlockID = this.level.getTileId(x, y, z);
                    this.level.cancelBlockUpdate(x, y, z, prevBlockID);
                    int blockID = Chunk.translate256(this.blockIDs[offset]);
                    byte metadata = this.metadatas[offset];
                    this.level.method_201(x, y, z, blockID, metadata);
                    this.level.removeTileEntity(x, y, z);
                    ++offset;
                }
            }
        }
        for (CompoundTag tag : this.tileEntities) {
            TileEntity te = TileEntity.method_1068(tag);
            this.level.setTileEntity(te.x, te.y, te.z, te);
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        if (tag.containsKey("blockIDs")) {
            this.blockIDs = tag.getByteArray("blockIDs");
        }
        if (tag.containsKey("metadatas")) {
            this.metadatas = tag.getByteArray("metadatas");
        }
        if (tag.containsKey("numTiles")) {
            this.tileEntities.clear();
            int numTiles = tag.getInt("numTiles");
            for (int i = 0; i < numTiles; ++i) {
                this.tileEntities.add((Object) tag.getCompoundTag(String.format((String) "tile%d", (Object[]) new Object[] { i })));
            }
        }
        if (!tag.containsKey("acVersion") && Minecraft.minecraftInstance.level.properties.originallyFromAC) {
            Blocks.convertACVersion(this.blockIDs);
        }
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        if (this.blockIDs != null) {
            tag.put("blockIDs", this.blockIDs);
        }
        if (this.metadatas != null) {
            tag.put("metadatas", this.metadatas);
        }
        if (!this.tileEntities.isEmpty()) {
            int i = 0;
            for (CompoundTag tag2 : this.tileEntities) {
                tag.put(String.format((String) "tile%d", (Object[]) new Object[] { i }), (AbstractTag) tag2);
                ++i;
            }
            tag.put("numTiles", i);
        }
        tag.put("acVersion", 0);
    }
}
