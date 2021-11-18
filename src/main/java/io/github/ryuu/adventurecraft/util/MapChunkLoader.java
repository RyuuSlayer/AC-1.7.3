package io.github.ryuu.adventurecraft.util;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;

import java.io.IOException;

public class MapChunkLoader implements ChunkIO {
    private final ChunkIO mapRegion;;
    private final ChunkIO saveRegion;

    public MapChunkLoader(ChunkIO mapR, ChunkIO saveR) {
        this.mapRegion = mapR;
        this.saveRegion = saveR;
    }

    public Chunk getChunk(Level world, int i, int j) throws IOException {
        Chunk returnChunk = this.saveRegion.getChunk(world, i, j);
        if (returnChunk == null)
            returnChunk = this.mapRegion.getChunk(world, i, j);
        return returnChunk;
    }

    public void saveChunk(Level world, Chunk chunk) {
        try {
            this.saveRegion.saveChunk(world, chunk);
            if (DebugMode.levelEditing)
                this.mapRegion.saveChunk(world, chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareChunk(Level world, Chunk chunk) {
    }

    public void method_810() {
    }

    public void method_813() {
    }
}