package io.github.ryuu.adventurecraft.mixin.level.biome;

import java.util.Random;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.biome.Taiga;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.PineTree;
import net.minecraft.level.structure.SpruceTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Taiga.class)
public class MixinTaiga extends Biome {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public Feature getTree(Random rand) {
        if (rand.nextInt(3) == 0) {
            return new PineTree();
        }
        return new SpruceTree();
    }
}
