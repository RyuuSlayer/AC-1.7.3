package io.github.ryuu.adventurecraft.util;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import java.io.IOException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.chunk.ChunkIO;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinChunk;

public class MapChunkLoader implements ChunkIO {

    private final ChunkIO mapRegion;

    private final ChunkIO saveRegion;

    public MapChunkLoader(ChunkIO mapR, ChunkIO saveR) {
        this.mapRegion = mapR;
        this.saveRegion = saveR;
    }

    @Override
    public MixinChunk getChunk(MixinLevel level, int xPos, int zPos) throws IOException {
        MixinChunk returnChunk = this.saveRegion.getChunk(level, xPos, zPos);
        if (returnChunk == null) {
            returnChunk = this.mapRegion.getChunk(level, xPos, zPos);
        }
        return returnChunk;
    }

    @Override
    public void saveChunk(MixinLevel level, MixinChunk chunk) {
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
    public void prepareChunk(MixinLevel level, MixinChunk chunk) {
    }

    @Override
    public void method_810() {
    }

    @Override
    public void method_813() {
    }
}
