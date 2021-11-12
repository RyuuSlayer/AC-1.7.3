package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.level.biome.Biome;

import java.util.Random;

public class MixinForest extends Biome {
    public pg a(Random random) {
        if (random.nextInt(5) == 0)
            return (pg) new k();
        if (random.nextInt(3) == 0)
            return (pg) new ih();
        return (pg) new yh();
    }
}
