package io.github.ryuu.adventurecraft.mixin.client.colour;

import net.minecraft.client.colour.FoliageColour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoliageColour.class)
public interface AccessFoliageColour {

    @Accessor
    static int[] getMap() {
        throw new AssertionError();
    }
}
