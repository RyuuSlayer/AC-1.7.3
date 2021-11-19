package io.github.ryuu.adventurecraft.mixin.level.biome;

import net.minecraft.level.biome.Biome;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.PineTree;
import net.minecraft.level.structure.SpruceTree;

import java.util.Random;

public class MixinTaiga extends Biome {
    public Feature getTree(Random rand) {
        if (rand.nextInt(3) == 0) {
            return new PineTree();
        }
        return new SpruceTree();
    }
}