package io.github.ryuu.adventurecraft.mixin.tile;

import net.minecraft.tile.GlassTile;
import net.minecraft.tile.TranslucentTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

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
    public int getDropCount(Random rand) {
        return 0;
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
