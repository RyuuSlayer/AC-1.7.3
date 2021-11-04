package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.level.biome.Biome;

import java.util.Random;

public class Forest extends Biome {
    public pg a(Random random) {
        if (random.nextInt(5) == 0)
            return (pg) new k();
        if (random.nextInt(3) == 0)
            return (pg) new ih();
        return (pg) new yh();
    }
}