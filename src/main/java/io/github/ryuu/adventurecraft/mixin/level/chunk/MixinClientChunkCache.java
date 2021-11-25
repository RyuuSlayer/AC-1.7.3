package io.github.ryuu.adventurecraft.mixin.level.chunk;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.level.chunk.DummyChunk;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.ProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

@Mixin(ClientChunkCache.class)
public class MixinClientChunkCache implements LevelSource {

    @Shadow()
    private final Chunk nullChunk;
    private final LevelSource chunkGenerator;
    private final ChunkIO io;
    private final Level level;
    int cachedX;
    int cachedZ;
    boolean isVeryFar;
    int mask;
    int chunksWide;
    private Chunk[] cache;
    private Chunk cachedChunk;
    private int spawnX;
    private int spawnZ;

    public MixinClientChunkCache(Level world, ChunkIO ichunkloader, LevelSource ichunkprovider) {
        this.isVeryFar = AccessMinecraft.getInstance().options.viewDistance != 0;
        this.updateVeryFar();
        this.cachedX = -999999999;
        this.cachedZ = -999999999;
        this.nullChunk = new DummyChunk(world, new byte[32768], 0, 0);
        this.level = world;
        this.io = ichunkloader;
        this.chunkGenerator = ichunkprovider;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void updateVeryFar() {
        boolean curVeryFar;
        boolean bl = curVeryFar = AccessMinecraft.getInstance().options.viewDistance == 0;
        if (this.isVeryFar != curVeryFar) {
            this.isVeryFar = curVeryFar;
            this.cachedX = -999999999;
            this.cachedZ = -999999999;
            if (this.cache != null) {
                this.saveChunks(true, null);
            }
            Chunk[] oldChunks = this.cache;
            if (this.isVeryFar) {
                this.cache = new Chunk[4096];
                this.mask = 63;
                this.chunksWide = 64;
            } else {
                this.cache = new Chunk[1024];
                this.mask = 31;
                this.chunksWide = 32;
            }
            if (oldChunks != null) {
                for (Chunk c : oldChunks) {
                    if (c == null || !this.isSpawnChunk(c.x, c.z)) continue;
                    int k = c.x & this.mask;
                    int l = c.z & this.mask;
                    int i1 = k + l * this.chunksWide;
                    this.cache[i1] = c;
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setSpawnChunk(int spawnX, int spawnZ) {
        this.spawnX = spawnX;
        this.spawnZ = spawnZ;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isSpawnChunk(int chunkX, int chunkZ) {
        return chunkX >= this.spawnX - this.mask && chunkZ >= this.spawnZ - this.mask && chunkX <= this.spawnX + this.mask && chunkZ <= this.spawnZ + this.mask;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        if (!this.isSpawnChunk(chunkX, chunkZ)) {
            return false;
        }
        if (chunkX == this.cachedX && chunkZ == this.cachedZ && this.cachedChunk != null) {
            return true;
        }
        int k = chunkX & this.mask;
        int l = chunkZ & this.mask;
        int i1 = k + l * this.chunksWide;
        return this.cache[i1] != null && (this.cache[i1] == this.nullChunk || this.cache[i1].equals(chunkX, chunkZ));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Chunk loadChunk(int x, int z) {
        return this.getChunk(x, z);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Chunk getChunk(int x, int z) {
        if (x == this.cachedX && z == this.cachedZ && this.cachedChunk != null) {
            return this.cachedChunk;
        }
        if (!this.level.forceLoadChunks && !this.isSpawnChunk(x, z)) {
            return this.nullChunk;
        }
        int k = x & this.mask;
        int l = z & this.mask;
        int i1 = k + l * this.chunksWide;
        if (!this.isChunkLoaded(x, z)) {
            Chunk chunk;
            if (this.cache[i1] != null) {
                this.cache[i1].method_883();
                this.method_1241(this.cache[i1]);
                this.method_1240(this.cache[i1]);
            }
            if ((chunk = this.method_1244(x, z)) == null) {
                if (this.chunkGenerator == null) {
                    chunk = this.nullChunk;
                } else {
                    chunk = this.chunkGenerator.getChunk(x, z);
                    chunk.method_890();
                }
            }
            this.cache[i1] = chunk;
            chunk.method_878();
            if (this.cache[i1] != null) {
                this.cache[i1].method_881();
            }
            if (!this.cache[i1].decorated && this.isChunkLoaded(x + 1, z + 1) && this.isChunkLoaded(x, z + 1) && this.isChunkLoaded(x + 1, z)) {
                this.decorate(this, x, z);
            }
            if (this.isChunkLoaded(x - 1, z) && !this.getChunk(x - 1, z).decorated && this.isChunkLoaded(x - 1, z + 1) && this.isChunkLoaded(x, z + 1) && this.isChunkLoaded(x - 1, z)) {
                this.decorate(this, x - 1, z);
            }
            if (this.isChunkLoaded(x, z - 1) && !this.getChunk(x, z - 1).decorated && this.isChunkLoaded(x + 1, z - 1) && this.isChunkLoaded(x, z - 1) && this.isChunkLoaded(x + 1, z)) {
                this.decorate(this, x, z - 1);
            }
            if (this.isChunkLoaded(x - 1, z - 1) && !this.getChunk(x - 1, z - 1).decorated && this.isChunkLoaded(x - 1, z - 1) && this.isChunkLoaded(x, z - 1) && this.isChunkLoaded(x - 1, z)) {
                this.decorate(this, x - 1, z - 1);
            }
        }
        this.cachedX = x;
        this.cachedZ = z;
        this.cachedChunk = this.cache[i1];
        return this.cache[i1];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private Chunk method_1244(int i, int j) {
        if (this.io == null) {
            return this.nullChunk;
        }
        try {
            Chunk chunk = this.io.getChunk(this.level, i, j);
            if (chunk != null) {
                chunk.lastUpdate = this.level.getLevelTime();
            }
            return chunk;
        } catch (Exception exception) {
            exception.printStackTrace();
            return this.nullChunk;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private void method_1241(Chunk chunk) {
        if (this.io == null) {
            return;
        }
        try {
            chunk.lastUpdate = this.level.getLevelTime();
            this.io.saveChunk(this.level, chunk);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
        Chunk chunk = this.getChunk(chunkX, chunkZ);
        if (!chunk.decorated) {
            chunk.decorated = true;
            if (this.chunkGenerator != null) {
                Chunk.isNotPopulating = false;
                this.chunkGenerator.decorate(levelSource, chunkX, chunkZ);
                chunk.shouldSave = false;
                Chunk.isNotPopulating = true;
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean saveChunks(boolean flag, ProgressListener listener) {
        int i = 0;
        int j = 0;
        if (listener != null) {
            for (Chunk chunk : this.cache) {
                if (chunk == null || !chunk.method_871(flag)) continue;
                ++j;
            }
        }
        int l = 0;
        for (Chunk chunk : this.cache) {
            if (chunk == null) continue;
            if (flag && !chunk.field_968) {
                this.method_1240(chunk);
            }
            if (!chunk.method_871(flag)) continue;
            this.method_1241(chunk);
            chunk.shouldSave = false;
            if (++i == 2 && !flag) {
                return false;
            }
            if (listener == null || ++l % 10 != 0) continue;
            listener.progressStagePercentage(l * 100 / j);
        }
        if (flag) {
            if (this.io == null) {
                return true;
            }
            this.io.method_813();
        }
        return true;
    }
}
