package io.github.ryuu.adventurecraft.util;

import java.io.IOException;

public class MapChunkLoader implements bf {
    private final bf mapRegion;

    private final bf saveRegion;

    public MapChunkLoader(bf mapR, bf saveR) {
        this.mapRegion = mapR;
        this.saveRegion = saveR;
    }

    public lm a(fd world, int i, int j) throws IOException {
        lm returnChunk = this.saveRegion.a(world, i, j);
        if (returnChunk == null)
            returnChunk = this.mapRegion.a(world, i, j);
        return returnChunk;
    }

    public void a(fd world, lm chunk) {
        try {
            this.saveRegion.a(world, chunk);
            if (DebugMode.levelEditing)
                this.mapRegion.a(world, chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void b(fd world, lm chunk) {}

    public void a() {}

    public void b() {}
}
