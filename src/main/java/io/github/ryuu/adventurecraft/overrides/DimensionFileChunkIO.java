package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.util.io.CompoundTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class DimensionFileChunkIO implements ChunkIO {
    private final File a;

    private final File levelDir;

    private final boolean b;

    public DimensionFileChunkIO(File file, boolean flag) {
        this.a = file;
        this.levelDir = null;
        this.b = flag;
    }

    public DimensionFileChunkIO(File file, File levelFile, boolean flag) {
        this.a = file;
        this.levelDir = levelFile;
        this.b = flag;
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

    public lm a(Level world, int i, int j) throws IOException {
        File file = a(i, j);
        if (file != null && file.exists())
            try {
                FileInputStream fileinputstream = new FileInputStream(file);
                CompoundTag nbttagcompound = as.a(fileinputstream);
                if (!nbttagcompound.b("Level")) {
                    System.out.println("Chunk file at " + i + "," + j + " is missing level data, skipping");
                    return null;
                }
                if (!nbttagcompound.k("Level").b("Blocks")) {
                    System.out.println("Chunk file at " + i + "," + j + " is missing block data, skipping");
                    return null;
                }
                lm chunk = a(world, nbttagcompound.k("Level"));
                if (!chunk.a(i, j)) {
                    System.out.println("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.j + ", " + chunk.k + ")");
                    nbttagcompound.a("xPos", i);
                    nbttagcompound.a("zPos", j);
                    chunk = a(world, nbttagcompound.k("Level"));
                }
                chunk.i();
                return chunk;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        return null;
    }

    public void a(Level world, lm chunk) throws IOException {
        world.r();
        File file = a(chunk.j, chunk.k);
        if (file.exists()) {
            LevelProperties worldinfo = world.x();
            worldinfo.b(worldinfo.g() - file.length());
        }
        try {
            File file1 = new File(this.a, "tmp_chunk.dat");
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            nu nbttagcompound = new nu();
            nu nbttagcompound1 = new nu();
            nbttagcompound.a("Level", (ij) nbttagcompound1);
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

    public static void a(lm chunk, Level world, nu nbttagcompound) {
        world.r();
        nbttagcompound.a("xPos", chunk.j);
        nbttagcompound.a("zPos", chunk.k);
        nbttagcompound.a("LastUpdate", world.t());
        nbttagcompound.a("Blocks", chunk.b);
        nbttagcompound.a("Data", chunk.e.a);
        nbttagcompound.a("SkyLight", chunk.f.a);
        nbttagcompound.a("BlockLight", chunk.g.a);
        nbttagcompound.a("HeightMap", chunk.h);
        nbttagcompound.a("TerrainPopulated", chunk.n);
        nbttagcompound.a("acVersion", 0);
        chunk.q = false;
        sp nbttaglist = new sp();
        for (int i = 0; i < chunk.m.length; i++) {
            Iterator<?> iterator = chunk.m[i].iterator();
            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();
                chunk.q = true;
                CompoundTag nbttagcompound1 = new nu();
                if (entity.c(nbttagcompound1))
                    nbttaglist.a((ij) nbttagcompound1);
            }
        }
        nbttagcompound.a("Entities", (ij) nbttaglist);
        sp nbttaglist1 = new sp();
        for (Iterator<TileEntity> iterator1 = chunk.l.values().iterator(); iterator1.hasNext(); nbttaglist1.a((ij) nbttagcompound2)) {
            TileEntity tileentity = iterator1.next();
            nu nbttagcompound2 = new nu();
            tileentity.b(nbttagcompound2);
        }
        nbttagcompound.a("TileEntities", (ij) nbttaglist1);
    }

    public static lm a(Level world, nu nbttagcompound) {
        int i = nbttagcompound.e("xPos");
        int j = nbttagcompound.e("zPos");
        lm chunk = new lm(world, i, j, false);
        chunk.b = nbttagcompound.j("Blocks");
        chunk.e = new wi(nbttagcompound.j("Data"));
        chunk.f = new wi(nbttagcompound.j("SkyLight"));
        chunk.g = new wi(nbttagcompound.j("BlockLight"));
        chunk.h = nbttagcompound.j("HeightMap");
        chunk.n = nbttagcompound.m("TerrainPopulated");
        if (!chunk.e.a())
            chunk.e = new wi(chunk.b.length);
        if (!nbttagcompound.b("acVersion"))
            if (world.x.originallyFromAC)
                Blocks.convertACVersion(chunk.b);
        if (chunk.h == null || !chunk.f.a()) {
            chunk.h = new byte[256];
            chunk.f = new wi(chunk.b.length);
            chunk.c();
        }
        if (!chunk.g.a()) {
            chunk.g = new wi(chunk.b.length);
            chunk.a();
        }
        sp nbttaglist = nbttagcompound.l("Entities");
        if (nbttaglist != null)
            for (int k = 0; k < nbttaglist.c(); k++) {
                nu nbttagcompound1 = (nu) nbttaglist.a(k);
                Entity entity = jc.a(nbttagcompound1, world);
                chunk.q = true;
                if (entity != null)
                    chunk.a(entity);
            }
        sp nbttaglist1 = nbttagcompound.l("TileEntities");
        if (nbttaglist1 != null)
            for (int l = 0; l < nbttaglist1.c(); l++) {
                nu nbttagcompound2 = (nu) nbttaglist1.a(l);
                TileEntity tileentity = TileEntity.c(nbttagcompound2);
                if (tileentity != null)
                    chunk.a(tileentity);
            }
        return chunk;
    }

    public void a() {
    }

    public void b() {
    }

    public void b(Level world, lm chunk) throws IOException {
    }
}
