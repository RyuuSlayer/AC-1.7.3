/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.level.chunk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.chunk.ChunkSubData;
import net.minecraft.level.chunk.DimensionFileChunkIO;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;
import net.minecraft.util.io.NBTIO;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinTileEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinChunk;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevelProperties;

@Mixin(DimensionFileChunkIO.class)
public class MixinDimensionFileChunkIO implements ChunkIO {

    @Shadow()
    private File field_1701;

    private File levelDir;

    private boolean field_1702;

    public MixinDimensionFileChunkIO(File file, boolean flag) {
        this.field_1701 = file;
        this.levelDir = null;
        this.field_1702 = flag;
    }

    public MixinDimensionFileChunkIO(File file, File levelFile, boolean flag) {
        this.field_1701 = file;
        this.levelDir = levelFile;
        this.field_1702 = flag;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private File method_1478(int i, int j) {
        String s = "c." + Integer.toString((int) i, (int) 36) + "." + Integer.toString((int) j, (int) 36) + ".dat";
        String s1 = Integer.toString((int) (i & 0x3F), (int) 36);
        String s2 = Integer.toString((int) (j & 0x3F), (int) 36);
        File file = new File(this.field_1701, s1);
        if (!file.exists()) {
            if (this.field_1702) {
                file.mkdir();
            } else {
                return null;
            }
        }
        if (!(file = new File(file, s2)).exists()) {
            if (this.field_1702) {
                file.mkdir();
            } else {
                return null;
            }
        }
        if (!(file = new File(file, s)).exists() && !this.field_1702) {
            return null;
        }
        return file;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public MixinChunk getChunk(MixinLevel level, int xPos, int zPos) throws IOException {
        File file = this.method_1478(xPos, zPos);
        if (file != null && file.exists()) {
            try {
                FileInputStream fileinputstream = new FileInputStream(file);
                MixinCompoundTag nbttagcompound = NBTIO.readGzipped((InputStream) fileinputstream);
                if (!nbttagcompound.containsKey("Level")) {
                    System.out.println("Chunk file at " + xPos + "," + zPos + " is missing level data, skipping");
                    return null;
                }
                if (!nbttagcompound.getCompoundTag("Level").containsKey("Blocks")) {
                    System.out.println("Chunk file at " + xPos + "," + zPos + " is missing block data, skipping");
                    return null;
                }
                MixinChunk chunk = DimensionFileChunkIO.loadChunk(level, nbttagcompound.getCompoundTag("Level"));
                if (!chunk.equals(xPos, zPos)) {
                    System.out.println("Chunk file at " + xPos + "," + zPos + " is in the wrong location; relocating. (Expected " + xPos + ", " + zPos + ", got " + chunk.x + ", " + chunk.z + ")");
                    nbttagcompound.put("xPos", xPos);
                    nbttagcompound.put("zPos", zPos);
                    chunk = DimensionFileChunkIO.loadChunk(level, nbttagcompound.getCompoundTag("Level"));
                }
                chunk.method_890();
                return chunk;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void saveChunk(MixinLevel level, MixinChunk chunk) throws IOException {
        level.checkSessionLock();
        File file = this.method_1478(chunk.x, chunk.z);
        if (file.exists()) {
            MixinLevelProperties worldinfo = level.getProperties();
            worldinfo.setSizeOnDisk(worldinfo.getSizeOnDisk() - file.length());
        }
        try {
            File file1 = new File(this.field_1701, "tmp_chunk.dat");
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            MixinCompoundTag nbttagcompound = new MixinCompoundTag();
            MixinCompoundTag nbttagcompound1 = new MixinCompoundTag();
            nbttagcompound.put("Level", (AbstractTag) nbttagcompound1);
            DimensionFileChunkIO.method_1480(chunk, level, nbttagcompound1);
            NBTIO.writeGzipped(nbttagcompound, (OutputStream) fileoutputstream);
            fileoutputstream.close();
            if (file.exists()) {
                file.delete();
            }
            file1.renameTo(file);
            MixinLevelProperties worldinfo1 = level.getProperties();
            worldinfo1.setSizeOnDisk(worldinfo1.getSizeOnDisk() + file.length());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static void method_1480(MixinChunk chunk, MixinLevel level, MixinCompoundTag tag) {
        level.checkSessionLock();
        tag.put("xPos", chunk.x);
        tag.put("zPos", chunk.z);
        tag.put("LastUpdate", level.getLevelTime());
        tag.put("Blocks", chunk.tiles);
        tag.put("Data", chunk.meta.data);
        tag.put("SkyLight", chunk.skylight.data);
        tag.put("BlockLight", chunk.blocklight.data);
        tag.put("HeightMap", chunk.heightmap);
        tag.put("TerrainPopulated", chunk.decorated);
        tag.put("acVersion", 0);
        chunk.field_969 = false;
        ListTag nbttaglist = new ListTag();
        for (int i = 0; i < chunk.entities.length; ++i) {
            for (MixinEntity entity : chunk.entities[i]) {
                chunk.field_969 = true;
                MixinCompoundTag nbttagcompound1 = new MixinCompoundTag();
                if (!entity.method_1343(nbttagcompound1))
                    continue;
                nbttaglist.add(nbttagcompound1);
            }
        }
        tag.put("Entities", nbttaglist);
        ListTag nbttaglist1 = new ListTag();
        for (MixinTileEntity tileentity : chunk.tileEntities.values()) {
            MixinCompoundTag nbttagcompound2 = new MixinCompoundTag();
            tileentity.writeIdentifyingData(nbttagcompound2);
            nbttaglist1.add(nbttagcompound2);
        }
        tag.put("TileEntities", nbttaglist1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static MixinChunk loadChunk(MixinLevel level, MixinCompoundTag tag) {
        ListTag nbttaglist1;
        ListTag nbttaglist;
        int i = tag.getInt("xPos");
        int j = tag.getInt("zPos");
        MixinChunk chunk = new MixinChunk(level, i, j, false);
        chunk.tiles = tag.getByteArray("Blocks");
        chunk.meta = new ChunkSubData(tag.getByteArray("Data"));
        chunk.skylight = new ChunkSubData(tag.getByteArray("SkyLight"));
        chunk.blocklight = new ChunkSubData(tag.getByteArray("BlockLight"));
        chunk.heightmap = tag.getByteArray("HeightMap");
        chunk.decorated = tag.getBoolean("TerrainPopulated");
        if (!chunk.meta.hasData()) {
            chunk.meta = new ChunkSubData(chunk.tiles.length);
        }
        if (!tag.containsKey("acVersion") && level.properties.originallyFromAC) {
            Blocks.convertACVersion(chunk.tiles);
        }
        if (chunk.heightmap == null || !chunk.skylight.hasData()) {
            chunk.heightmap = new byte[256];
            chunk.skylight = new ChunkSubData(chunk.tiles.length);
            chunk.generateHeightmap();
        }
        if (!chunk.blocklight.hasData()) {
            chunk.blocklight = new ChunkSubData(chunk.tiles.length);
            chunk.method_857();
        }
        if ((nbttaglist = tag.getListTag("Entities")) != null) {
            for (int k = 0; k < nbttaglist.size(); ++k) {
                MixinCompoundTag nbttagcompound1 = (MixinCompoundTag) nbttaglist.get(k);
                MixinEntity entity = EntityRegistry.create(nbttagcompound1, level);
                chunk.field_969 = true;
                if (entity == null)
                    continue;
                chunk.addEntity(entity);
            }
        }
        if ((nbttaglist1 = tag.getListTag("TileEntities")) != null) {
            for (int l = 0; l < nbttaglist1.size(); ++l) {
                MixinCompoundTag nbttagcompound2 = (MixinCompoundTag) nbttaglist1.get(l);
                MixinTileEntity tileentity = TileEntity.method_1068(nbttagcompound2);
                if (tileentity == null)
                    continue;
                chunk.addTileEntity(tileentity);
            }
        }
        return chunk;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_810() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_813() {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void prepareChunk(MixinLevel level, MixinChunk chunk) throws IOException {
    }
}
