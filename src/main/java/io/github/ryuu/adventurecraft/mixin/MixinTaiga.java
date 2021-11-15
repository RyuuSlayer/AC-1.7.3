package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.level.biome.Biome;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.PineTree;
import net.minecraft.level.structure.SpruceTree;

import java.util.Random;

public class MixinTaiga extends Biome {
    public Feature a(Random random) {
        if (random.nextInt(3) == 0)
            return new PineTree();
        return new SpruceTree();
    }
}