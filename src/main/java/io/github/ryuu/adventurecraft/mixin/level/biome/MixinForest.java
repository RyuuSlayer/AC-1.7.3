package io.github.ryuu.adventurecraft.mixin.level.biome;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Forest.class)
public class MixinForest extends Biome {

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
