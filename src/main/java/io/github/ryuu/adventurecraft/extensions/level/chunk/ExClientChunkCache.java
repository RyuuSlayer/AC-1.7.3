package io.github.ryuu.adventurecraft.extensions.level.chunk;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.ChunkIO;
import net.minecraft.level.source.LevelSource;

public interface ExClientChunkCache {

    void init(Level world, ChunkIO ichunkloader, LevelSource ichunkprovider);

    void updateVeryFar();
}
