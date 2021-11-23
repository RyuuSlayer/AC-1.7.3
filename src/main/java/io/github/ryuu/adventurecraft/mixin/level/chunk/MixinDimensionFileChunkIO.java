package io.github.ryuu.adventurecraft.mixin.level.chunk;

import io.github.ryuu.adventurecraft.blocks.Blocks;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Mixin(DimensionFileChunkIO.class)
public class MixinDimensionFileChunkIO implements ChunkIO {

    @Shadow()
    private final File field_1701;

    private final File levelDir;

    private final boolean field_1702;

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
    public static void method_1480(Chunk chunk, Level level, CompoundTag tag) {
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
            for (Entity entity : chunk.entities[i]) {
                chunk.field_969 = true;
                CompoundTag nbttagcompound1 = new CompoundTag();
                if (!entity.method_1343(nbttagcompound1)) continue;
                nbttaglist.add(nbttagcompound1);
            }
        }
        tag.put("Entities", nbttaglist);
        ListTag nbttaglist1 = new ListTag();
        for (TileEntity tileentity : chunk.tileEntities.values()) {
            CompoundTag nbttagcompound2 = new CompoundTag();
            tileentity.writeIdentifyingData(nbttagcompound2);
            nbttaglist1.add(nbttagcompound2);
        }
        tag.put("TileEntities", nbttaglist1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static Chunk loadChunk(Level level, CompoundTag tag) {
        ListTag nbttaglist1;
        ListTag nbttaglist;
        int i = tag.getInt("xPos");
        int j = tag.getInt("zPos");
        Chunk chunk = new Chunk(level, i, j, false);
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
                CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.get(k);
                Entity entity = EntityRegistry.create(nbttagcompound1, level);
                chunk.field_969 = true;
                if (entity == null) continue;
                chunk.addEntity(entity);
            }
        }
        if ((nbttaglist1 = tag.getListTag("TileEntities")) != null) {
            for (int l = 0; l < nbttaglist1.size(); ++l) {
                CompoundTag nbttagcompound2 = (CompoundTag) nbttaglist1.get(l);
                TileEntity tileentity = TileEntity.method_1068(nbttagcompound2);
                if (tileentity == null) continue;
                chunk.addTileEntity(tileentity);
            }
        }
        return chunk;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private File method_1478(int i, int j) {
        String s = "c." + Integer.toString(i, 36) + "." + Integer.toString(j, 36) + ".dat";
        String s1 = Integer.toString(i & 0x3F, 36);
        String s2 = Integer.toString(j & 0x3F, 36);
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
    public Chunk getChunk(Level level, int xPos, int zPos) throws IOException {
        File file = this.method_1478(xPos, zPos);
        if (file != null && file.exists()) {
            try {
                FileInputStream fileinputstream = new FileInputStream(file);
                CompoundTag nbttagcompound = NBTIO.readGzipped(fileinputstream);
                if (!nbttagcompound.containsKey("Level")) {
                    System.out.println("Chunk file at " + xPos + "," + zPos + " is missing level data, skipping");
                    return null;
                }
                if (!nbttagcompound.getCompoundTag("Level").containsKey("Blocks")) {
                    System.out.println("Chunk file at " + xPos + "," + zPos + " is missing block data, skipping");
                    return null;
                }
                Chunk chunk = DimensionFileChunkIO.loadChunk(level, nbttagcompound.getCompoundTag("Level"));
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
    public void saveChunk(Level level, Chunk chunk) throws IOException {
        level.checkSessionLock();
        File file = this.method_1478(chunk.x, chunk.z);
        if (file.exists()) {
            LevelProperties worldinfo = level.getProperties();
            worldinfo.setSizeOnDisk(worldinfo.getSizeOnDisk() - file.length());
        }
        try {
            File file1 = new File(this.field_1701, "tmp_chunk.dat");
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            CompoundTag nbttagcompound = new CompoundTag();
            CompoundTag nbttagcompound1 = new CompoundTag();
            nbttagcompound.put("Level", (AbstractTag) nbttagcompound1);
            DimensionFileChunkIO.method_1480(chunk, level, nbttagcompound1);
            NBTIO.writeGzipped(nbttagcompound, fileoutputstream);
            fileoutputstream.close();
            if (file.exists()) {
                file.delete();
            }
            file1.renameTo(file);
            LevelProperties worldinfo1 = level.getProperties();
            worldinfo1.setSizeOnDisk(worldinfo1.getSizeOnDisk() + file.length());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
