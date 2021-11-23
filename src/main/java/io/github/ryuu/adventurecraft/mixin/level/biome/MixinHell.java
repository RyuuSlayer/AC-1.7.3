package io.github.ryuu.adventurecraft.mixin.level.biome;

import net.minecraft.level.biome.Biome;
import net.minecraft.level.biome.Hell;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Hell.class)
public class MixinHell extends Biome {
}
