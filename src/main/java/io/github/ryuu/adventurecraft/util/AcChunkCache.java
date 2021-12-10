package io.github.ryuu.adventurecraft.util;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.chunk.DummyChunk;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.ProgressListener;

public class AcChunkCache implements LevelSource {

    public static boolean chunkIsNotPopulating = true;

    private Chunk nullChunk;
    private LevelSource chunkGenerator;
    private ChunkIO io;
    private Chunk[] cache;
    private Level level;
    int cachedX;
    int cachedZ;
    private Chunk cachedChunk;
    private int spawnX;
    private int spawnZ;

    boolean isVeryFar;
    int mask;
    int chunksWide;

    public AcChunkCache(Level world, ChunkIO ichunkloader, LevelSource ichunkprovider) {
        this.isVeryFar = AccessMinecraft.getInstance().options.viewDistance != 0;
        this.updateVeryFar();
        this.cachedX = -999999999;
        this.cachedZ = -999999999;
        this.nullChunk = new DummyChunk(world, new byte[32768], 0, 0);
        this.level = world;
        this.io = ichunkloader;
        this.chunkGenerator = ichunkprovider;
    }

    public void updateVeryFar() {
        boolean curVeryFar = AccessMinecraft.getInstance().options.viewDistance == 0;
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

    public void setSpawnChunk(int i, int j) {
        this.spawnX = i;
        this.spawnZ = j;
    }

    public boolean isSpawnChunk(int i, int j) {
        byte var3 = 15;
        return i >= this.spawnX - var3 && j >= this.spawnZ - var3 && i <= this.spawnX + var3 && j <= this.spawnZ + var3;
    }

    @Override
    public boolean isChunkLoaded(int i, int j) {
        if (!this.isSpawnChunk(i, j)) {
            return false;
        } else if (i == this.cachedX && j == this.cachedZ && this.cachedChunk != null) {
            return true;
        } else {
            int var3 = i & this.mask;
            int var4 = j & this.mask;
            int var5 = var3 + var4 * this.chunksWide;
            return this.cache[var5] != null && (this.cache[var5] == this.nullChunk || this.cache[var5].equals(i, j));
        }
    }

    @Override
    public Chunk loadChunk(int i, int j) {
        return this.getChunk(i, j);
    }

    @Override
    public Chunk getChunk(int i, int j) {
        if (i == this.cachedX && j == this.cachedZ && this.cachedChunk != null) {
            return this.cachedChunk;
        } else if (!this.level.forceLoadChunks && !this.isSpawnChunk(i, j)) {
            return this.nullChunk;
        } else {
            int var3 = i & this.mask;
            int var4 = j & this.mask;
            int var5 = var3 + var4 * this.chunksWide;
            if (!this.isChunkLoaded(i, j)) {
                if (this.cache[var5] != null) {
                    this.cache[var5].method_883();
                    this.method_1241(this.cache[var5]);
                    this.method_1240(this.cache[var5]);
                }

                Chunk var6 = this.method_1244(i, j);
                if (var6 == null) {
                    if (this.chunkGenerator == null) {
                        var6 = this.nullChunk;
                    } else {
                        var6 = this.chunkGenerator.getChunk(i, j);
                        var6.method_890();
                    }
                }

                this.cache[var5] = var6;
                var6.method_878();
                if (this.cache[var5] != null) {
                    this.cache[var5].method_881();
                }

                if (!this.cache[var5].decorated && this.isChunkLoaded(i + 1, j + 1) && this.isChunkLoaded(i, j + 1) && this.isChunkLoaded(i + 1, j)) {
                    this.decorate(this, i, j);
                }

                if (this.isChunkLoaded(i - 1, j) && !this.getChunk(i - 1, j).decorated && this.isChunkLoaded(i - 1, j + 1) && this.isChunkLoaded(i, j + 1) && this.isChunkLoaded(i - 1, j)) {
                    this.decorate(this, i - 1, j);
                }

                if (this.isChunkLoaded(i, j - 1) && !this.getChunk(i, j - 1).decorated && this.isChunkLoaded(i + 1, j - 1) && this.isChunkLoaded(i, j - 1) && this.isChunkLoaded(i + 1, j)) {
                    this.decorate(this, i, j - 1);
                }

                if (this.isChunkLoaded(i - 1, j - 1) && !this.getChunk(i - 1, j - 1).decorated && this.isChunkLoaded(i - 1, j - 1) && this.isChunkLoaded(i, j - 1) && this.isChunkLoaded(i - 1, j)) {
                    this.decorate(this, i - 1, j - 1);
                }
            }

            this.cachedX = i;
            this.cachedZ = j;
            this.cachedChunk = this.cache[var5];
            return this.cache[var5];
        }
    }

    private Chunk method_1244(int i, int j) {
        if (this.io == null) {
            return this.nullChunk;
        } else {
            try {
                Chunk var3 = this.io.getChunk(this.level, i, j);
                if (var3 != null) {
                    var3.lastUpdate = this.level.getLevelTime();
                }

                return var3;
            } catch (Exception var4) {
                var4.printStackTrace();
                return this.nullChunk;
            }
        }
    }

    private void method_1240(Chunk arg) {
        if (this.io != null) {
            try {
                this.io.prepareChunk(this.level, arg);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    private void method_1241(Chunk arg) {
        if (this.io != null) {
            try {
                arg.lastUpdate = this.level.getLevelTime();
                this.io.saveChunk(this.level, arg);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    @Override
    public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
        Chunk chunk = this.getChunk(chunkX, chunkZ);
        if (!chunk.decorated) {
            chunk.decorated = true;
            if (this.chunkGenerator != null) {
                chunkIsNotPopulating = false;
                this.chunkGenerator.decorate(levelSource, chunkX, chunkZ);
                chunk.shouldSave = false;
                chunkIsNotPopulating = true;
            }
        }
    }

    @Override
    public boolean saveChunks(boolean flag, ProgressListener arg) {
        int var3 = 0;
        int var4 = 0;
        int var5;
        if (arg != null) {
            for (var5 = 0; var5 < this.cache.length; ++var5) {
                if (this.cache[var5] != null && this.cache[var5].method_871(flag)) {
                    ++var4;
                }
            }
        }

        var5 = 0;

        for (int var6 = 0; var6 < this.cache.length; ++var6) {
            if (this.cache[var6] != null) {
                if (flag && !this.cache[var6].field_968) {
                    this.method_1240(this.cache[var6]);
                }

                if (this.cache[var6].method_871(flag)) {
                    this.method_1241(this.cache[var6]);
                    this.cache[var6].shouldSave = false;
                    ++var3;
                    if (var3 == 2 && !flag) {
                        return false;
                    }

                    if (arg != null) {
                        ++var5;
                        if (var5 % 10 == 0) {
                            arg.progressStagePercentage(var5 * 100 / var4);
                        }
                    }
                }
            }
        }

        if (flag) {
            if (this.io == null) {
                return true;
            }

            this.io.method_813();
        }

        return true;
    }

    @Override
    public boolean method_1801() {
        if (this.io != null) {
            this.io.method_810();
        }

        return this.chunkGenerator.method_1801();
    }

    @Override
    public boolean isClean() {
        return true;
    }

    @Override
    public String toString() {
        return "ChunkCache: " + this.cache.length;
    }
}
