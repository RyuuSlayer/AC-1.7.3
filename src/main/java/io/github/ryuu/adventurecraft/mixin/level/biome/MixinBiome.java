package io.github.ryuu.adventurecraft.mixin.level.biome;

import net.minecraft.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(Biome.class)
public abstract class MixinBiome {

    @Redirect(method = "<init>", at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private <E> boolean disableEntitySpawning(List<E> instance, E e) {
        return true;
    }
}
