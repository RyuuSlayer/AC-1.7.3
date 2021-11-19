/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Random
 */
package io.github.ryuu.adventurecraft.mixin.level.biome;

import java.util.Random;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.biome.Forest;
import net.minecraft.level.structure.BirchTree;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.LargeOak;
import net.minecraft.level.structure.OakTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinBiome;

@Mixin(Forest.class)
public class MixinForest extends MixinBiome {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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
