package io.github.ryuu.adventurecraft.mixin.level.dimension;

import io.github.ryuu.adventurecraft.extensions.level.ExLevelProperties;
import io.github.ryuu.adventurecraft.extensions.level.source.ExOverworldLevelSource;
import io.github.ryuu.adventurecraft.util.ChunkProviderHeightMapGenerate;
import net.minecraft.level.Level;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Dimension.class)
public abstract class MixinDimension {

    @Shadow
    public Level level;

    @Shadow
    public int id;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public LevelSource createLevelSource() {
        ExLevelProperties w = (ExLevelProperties) this.level.getProperties();
        if (w.isUsingBiomeImages()) {
            return new ChunkProviderHeightMapGenerate(this.level, this.level.getSeed());
        }
        OverworldLevelSource source = new OverworldLevelSource(this.level, this.level.getSeed());
        ExOverworldLevelSource c = (ExOverworldLevelSource) source;
        c.setMapSize(w.getMapSize());
        c.setWaterLevel(w.getWaterLevel());
        c.setFractureHorizontal(w.getFractureHorizontal());
        c.setFractureVertical(w.getFractureVertical());
        c.setMaxAvgDepth(w.getMaxAvgDepth());
        c.setMaxAvgHeight(w.getMaxAvgHeight());
        c.setVolatility1(w.getVolatility1());
        c.setVolatility2(w.getVolatility2());
        c.setVolatilityWeight1(w.getVolatilityWeight1());
        c.setVolatilityWeight2(w.getVolatilityWeight2());
        return source;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean isValidSpawnPos(int x, int z) {
        int k = this.level.getTileAtSurface(x, z);
        return k != 0 && Tile.BY_ID[k] != null && !(Tile.BY_ID[k] instanceof FluidTile);
    }
}
