package io.github.ryuu.adventurecraft.util;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;

import java.io.IOException;

public class MapChunkLoader implements ChunkIO {
    private final ChunkIO;

    private final ChunkIO saveRegion;

    public MapChunkLoader(ChunkIO mapR, ChunkIO saveR) {
        this.mapRegion = mapR;
        this.saveRegion = saveR;
    }

    public Chunk a(Level world, int i, int j) throws IOException {
        Chunk returnChunk = this.saveRegion.a(world, i, j);
        if (returnChunk == null)
            returnChunk = this.mapRegion.a(world, i, j);
        return returnChunk;
    }

    public void a(Level world, Chunk chunk) {
        try {
            this.saveRegion.a(world, chunk);
            if (DebugMode.levelEditing)
                this.mapRegion.a(world, chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void b(Level world, Chunk chunk) {
    }

    public void a() {
    }

    public void b() {
    }
}
