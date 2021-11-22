package io.github.ryuu.adventurecraft.mixin.tile;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GlassTile.class)
public class MixinGlassTile extends TranslucentTile {

    public MixinGlassTile(int i, int j, Material material, boolean flag) {
        super(i, j, material, flag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_1619() {
        return 1;
    }
}
