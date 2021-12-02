package io.github.ryuu.adventurecraft.mixin.tile;

import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.TileView;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FluidTile.class)
public abstract class MixinFluidTile extends Tile {

    protected MixinFluidTile(int i, Material arg) {
        super(i, arg);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Override
    @Overwrite
    public int getTint(TileView iblockaccess, int i, int j, int k) {
        if (!TerrainImage.isWaterLoaded || this.id != Tile.FLOWING_WATER.id && this.id != Tile.STILL_WATER.id) {
            return -1;
        }
        return TerrainImage.getWaterColor(i, k);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public boolean method_1571(int i, boolean flag) {
        return DebugMode.active && DebugMode.isFluidHittable || flag && i == 0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    // This should still override Tile.method_1576()
    public boolean method_1576() {
        return DebugMode.active && DebugMode.isFluidHittable;
    }
}
