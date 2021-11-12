package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.level.biome.Biome;

import java.util.Random;

public class MixinTaiga extends Biome {
    public pg a(Random random) {
        if (random.nextInt(3) == 0)
            return (pg) new pw();
        return (pg) new ws();
    }
}
