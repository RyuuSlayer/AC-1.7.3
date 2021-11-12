package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.chunk.ChunkSubData;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class MixinDimensionFileChunkIO implements ChunkIO {
    private final File a;

    private final File levelDir;

    private final boolean b;

    public MixinDimensionFileChunkIO(File file, boolean flag) {
        this.a = file;
        this.levelDir = null;
        this.b = flag;
    }

    public MixinDimensionFileChunkIO(File file, File levelFile, boolean flag) {
        this.a = file;
        this.levelDir = levelFile;
        this.b = flag;
    }

    public static void a(Chunk chunk, Level world, CompoundTag nbttagcompound) {
        world.r();
        nbttagcompound.put("xPos", chunk.j);
        nbttagcompound.put("zPos", chunk.k);
        nbttagcompound.put("LastUpdate", world.t());
        nbttagcompound.put("Blocks", chunk.b);
        nbttagcompound.put("Data", chunk.e.a);
        nbttagcompound.put("SkyLight", chunk.f.a);
        nbttagcompound.put("BlockLight", chunk.g.a);
        nbttagcompound.put("HeightMap", chunk.h);
        nbttagcompound.put("TerrainPopulated", chunk.n);
        nbttagcompound.put("acVersion", 0);
        chunk.q = false;
        ListTag nbttaglist = new ListTag();
        for (int i = 0; i < chunk.m.length; i++) {
            Iterator<?> iterator = chunk.m[i].iterator();
            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();
                chunk.q = true;
                CompoundTag nbttagcompound1 = new CompoundTag();
                if (entity.c(nbttagcompound1))
                    nbttaglist.a((AbstractTag) nbttagcompound1);
            }
        }
        nbttagcompound.put("Entities", nbttaglist);
        ListTag nbttaglist1 = new ListTag();
        for (Iterator<TileEntity> iterator1 = chunk.l.values().iterator(); iterator1.hasNext(); nbttaglist1.a((AbstractTag) nbttagcompound2)) {
            TileEntity tileentity = iterator1.next();
            CompoundTag nbttagcompound2 = new CompoundTag();
            tileentity.b(nbttagcompound2);
        }
        nbttagcompound.put("TileEntities", nbttaglist1);
    }

    public static Chunk a(Level world, CompoundTag nbttagcompound) {
        int i = nbttagcompound.getInt("xPos");
        int j = nbttagcompound.getInt("zPos");
        Chunk chunk = new Chunk(world, i, j, false);
        chunk.b = nbttagcompound.getByteArray("Blocks");
        chunk.e = new ChunkSubData(nbttagcompound.getByteArray("Data"));
        chunk.f = new ChunkSubData(nbttagcompound.getByteArray("SkyLight"));
        chunk.g = new ChunkSubData(nbttagcompound.getByteArray("BlockLight"));
        chunk.h = nbttagcompound.getByteArray("HeightMap");
        chunk.n = nbttagcompound.getBoolean("TerrainPopulated");
        if (!chunk.e.a())
            chunk.e = new ChunkSubData(chunk.b.length);
        if (!nbttagcompound.containsKey("acVersion"))
            if (world.x.originallyFromAC)
                Blocks.convertACVersion(chunk.b);
        if (chunk.h == null || !chunk.f.a()) {
            chunk.h = new byte[256];
            chunk.f = new ChunkSubData(chunk.b.length);
            chunk.c();
        }
        if (!chunk.g.a()) {
            chunk.g = new ChunkSubData(chunk.b.length);
            chunk.a();
        }
        ListTag nbttaglist = nbttagcompound.getListTag("Entities");
        if (nbttaglist != null)
            for (int k = 0; k < nbttaglist.c(); k++) {
                CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.a(k);
                Entity entity = EntityRegistry.a(nbttagcompound1, world);
                chunk.q = true;
                if (entity != null)
                    chunk.a(entity);
            }
        ListTag nbttaglist1 = nbttagcompound.l("TileEntities");
        if (nbttaglist1 != null)
            for (int l = 0; l < nbttaglist1.c(); l++) {
                CompoundTag nbttagcompound2 = (CompoundTag) nbttaglist1.a(l);
                TileEntity tileentity = TileEntity.c(nbttagcompound2);
                if (tileentity != null)
                    chunk.a(tileentity);
            }
        return chunk;
    }

    private File a(int i, int j) {
        String s = "c." + Integer.toString(i, 36) + "." + Integer.toString(j, 36) + ".dat";
        String s1 = Integer.toString(i & 0x3F, 36);
        String s2 = Integer.toString(j & 0x3F, 36);
        File file = new File(this.a, s1);
        if (!file.exists())
            if (this.b) {
                file.mkdir();
            } else {
                return null;
            }
        file = new File(file, s2);
        if (!file.exists())
            if (this.b) {
                file.mkdir();
            } else {
                return null;
            }
        file = new File(file, s);
        if (!file.exists() && !this.b)
            return null;
        return file;
    }

    public Chunk a(Level world, int i, int j) throws IOException {
        File file = a(i, j);
        if (file != null && file.exists())
            try {
                FileInputStream fileinputstream = new FileInputStream(file);
                CompoundTag nbttagcompound = as.a(fileinputstream);
                if (!nbttagcompound.containsKey("Level")) {
                    System.out.println("Chunk file at " + i + "," + j + " is missing level data, skipping");
                    return null;
                }
                if (!nbttagcompound.getCompoundTag("Level").b("Blocks")) {
                    System.out.println("Chunk file at " + i + "," + j + " is missing block data, skipping");
                    return null;
                }
                Chunk chunk = a(world, nbttagcompound.getCompoundTag("Level"));
                if (!chunk.a(i, j)) {
                    System.out.println("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.j + ", " + chunk.k + ")");
                    nbttagcompound.put("xPos", i);
                    nbttagcompound.put("zPos", j);
                    chunk = a(world, nbttagcompound.getCompoundTag("Level"));
                }
                chunk.i();
                return chunk;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        return null;
    }

    public void a(Level world, Chunk chunk) throws IOException {
        world.r();
        File file = a(chunk.j, chunk.k);
        if (file.exists()) {
            LevelProperties worldinfo = world.x();
            worldinfo.b(worldinfo.g() - file.length());
        }
        try {
            File file1 = new File(this.a, "tmp_chunk.dat");
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            CompoundTag nbttagcompound = new CompoundTag();
            CompoundTag nbttagcompound1 = new CompoundTag();
            nbttagcompound.put("Level", (AbstractTag) nbttagcompound1);
            a(chunk, world, nbttagcompound1);
            as.a(nbttagcompound, fileoutputstream);
            fileoutputstream.close();
            if (file.exists())
                file.delete();
            file1.renameTo(file);
            LevelProperties worldinfo1 = world.x();
            worldinfo1.b(worldinfo1.g() + file.length());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void a() {
    }

    public void b() {
    }

    public void b(Level world, Chunk chunk) throws IOException {
    }
}
