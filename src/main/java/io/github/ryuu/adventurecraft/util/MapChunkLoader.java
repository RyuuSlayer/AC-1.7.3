package io.github.ryuu.adventurecraft.util;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;

import java.io.IOException;

public class MapChunkLoader implements ChunkIO {

    private final ChunkIO mapRegion;

    private final ChunkIO saveRegion;

    public MapChunkLoader(ChunkIO mapR, ChunkIO saveR) {
        this.mapRegion = mapR;
        this.saveRegion = saveR;
    }

    @Override
    public Chunk getChunk(Level level, int xPos, int zPos) throws IOException {
        Chunk returnChunk = this.saveRegion.getChunk(level, xPos, zPos);
        if (returnChunk == null) {
            returnChunk = this.mapRegion.getChunk(level, xPos, zPos);
        }
        return returnChunk;
    }

    @Override
    public void saveChunk(Level level, Chunk chunk) {
        try {
            this.saveRegion.saveChunk(level, chunk);
            if (DebugMode.levelEditing) {
                this.mapRegion.saveChunk(level, chunk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareChunk(Level level, Chunk chunk) {
    }

    @Override
    public void method_810() {
    }

    @Override
    public void method_813() {
    }
}
