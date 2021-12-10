package io.github.ryuu.adventurecraft.mixin.level.chunk;

import io.github.ryuu.adventurecraft.Main;
import io.github.ryuu.adventurecraft.extensions.level.chunk.ExClientChunkCache;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.AcChunkCache;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.chunk.ClientChunkCache;
import net.minecraft.level.chunk.DummyChunk;
import net.minecraft.level.source.LevelSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientChunkCache.class)
public abstract class MixinClientChunkCache implements LevelSource, ExClientChunkCache {

    @Shadow
    private Chunk nullChunk;

    @Shadow
    private LevelSource chunkGenerator;

    @Shadow
    private ChunkIO io;

    @Shadow
    private Level level;

    @Shadow
    int cachedX;

    @Shadow
    int cachedZ;

    @Shadow
    private Chunk[] cache;

    boolean isVeryFar;
    int mask;
    int chunksWide;

    public void init(Level world, ChunkIO ichunkloader, LevelSource ichunkprovider) {
        this.isVeryFar = AccessMinecraft.getInstance().options.viewDistance != 0;
        this.updateVeryFar();
        this.cachedX = -999999999;
        this.cachedZ = -999999999;
        this.nullChunk = new DummyChunk(world, new byte[32768], 0, 0);
        this.level = world;
        this.io = ichunkloader;
        this.chunkGenerator = ichunkprovider;
    }

    @Shadow
    public abstract boolean isSpawnChunk(int i, int j);

    @Override
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

    @ModifyConstant(method = "isChunkLoaded", constant = @Constant(intValue = 31))
    private int changeIsChunkLoadedToChunkMask(int oldValue) {
        return this.mask;
    }

    @ModifyConstant(method = "isChunkLoaded", constant = @Constant(intValue = 32))
    private int changeIsChunkLoadedToChunksWide(int oldValue) {
        return this.chunksWide;
    }

    @ModifyConstant(method = "getChunk", constant = @Constant(intValue = 31))
    private int changeGetChunkToChunkMask(int oldValue) {
        return this.mask;
    }

    @ModifyConstant(method = "getChunk", constant = @Constant(intValue = 32))
    private int changeGetChunkToChunksWide(int oldValue) {
        return this.chunksWide;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
        Chunk chunk = this.getChunk(chunkX, chunkZ);
        if (!chunk.decorated) {
            chunk.decorated = true;
            if (this.chunkGenerator != null) {
                AcChunkCache.chunkIsNotPopulating = false;
                this.chunkGenerator.decorate(levelSource, chunkX, chunkZ);
                chunk.shouldSave = false;
                AcChunkCache.chunkIsNotPopulating = true;
            }
        }
    }
}
