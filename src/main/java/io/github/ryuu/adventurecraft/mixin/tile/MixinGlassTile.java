package io.github.ryuu.adventurecraft.mixin.tile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.tile.GlassTile;
import net.minecraft.tile.TranslucentTile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GlassTile.class)
public abstract class MixinGlassTile extends TranslucentTile {

    protected MixinGlassTile(int i, int j, Material arg, boolean flag) {
        super(i, j, arg, flag);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Override
    @Overwrite
    public int method_1619() {
        return 1;
    }
}
