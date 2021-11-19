package io.github.ryuu.adventurecraft.mixin.level.biome;

import net.minecraft.level.biome.Biome;
import net.minecraft.level.structure.BirchTree;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.LargeOak;
import net.minecraft.level.structure.OakTree;

import java.util.Random;

public class MixinForest extends Biome {
    public Feature getTree(Random rand) {
        if (rand.nextInt(5) == 0) {
            return new BirchTree();
        }
        if (rand.nextInt(3) == 0) {
            return new LargeOak();
        }
        return new OakTree();
    }
}