package io.github.ryuu.adventurecraft.mixin.level.dimension;

import io.github.ryuu.adventurecraft.util.ChunkProviderHeightMapGenerate;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.dimension.Nether;
import net.minecraft.level.dimension.Overworld;
import net.minecraft.level.dimension.Skylands;
import net.minecraft.level.gen.BiomeSource;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.tile.FluidTile;
import net.minecraft.tile.Tile;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Dimension.class)
public abstract class MixinDimension {

    private final float[] field_2180 = new float[4];
    @Shadow()
    public Level level;
    public BiomeSource biomeSource;
    public boolean hasFog = false;
    public boolean field_2176 = false;
    public boolean fixedSpawnPos = false;
    public float[] field_2178 = new float[16];
    public int id = 0;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static Dimension getByID(int id) {
        if (id == -1) {
            return new Nether();
        }
        if (id == 0) {
            return new Overworld();
        }
        if (id == 1) {
            return new Skylands();
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    protected void method_1765() {
        float f = 0.05f;
        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0f - (float) i / 15.0f;
            this.field_2178[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public LevelSource createLevelSource() {
        if (this.level.properties.useImages) {
            return new ChunkProviderHeightMapGenerate(this.level, this.level.getSeed());
        }
        OverworldLevelSource c = new OverworldLevelSource(this.level, this.level.getSeed());
        LevelProperties w = this.level.properties;
        c.mapSize = w.mapSize;
        c.waterLevel = w.waterLevel;
        c.fractureHorizontal = w.fractureHorizontal;
        c.fractureVertical = w.fractureVertical;
        c.maxAvgDepth = w.maxAvgDepth;
        c.maxAvgHeight = w.maxAvgHeight;
        c.volatility1 = w.volatility1;
        c.volatility2 = w.volatility2;
        c.volatilityWeight1 = w.volatilityWeight1;
        c.volatilityWeight2 = w.volatilityWeight2;
        return c;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isValidSpawnPos(int x, int z) {
        int k = this.level.getTileAtSurface(x, z);
        return k != 0 && Tile.BY_ID[k] != null && !(Tile.BY_ID[k] instanceof FluidTile);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_1771(long l, float f) {
        int i = (int) (l % 24000L);
        float f1 = ((float) i + f) / 24000.0f - 0.25f;
        if (f1 < 0.0f) {
            f1 += 1.0f;
        }
        if (f1 > 1.0f) {
            f1 -= 1.0f;
        }
        float f2 = f1;
        f1 = 1.0f - (float) ((Math.cos((double) f1 * Math.PI) + 1.0) / 2.0);
        f1 = f2 + (f1 - f2) / 3.0f;
        return f1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float[] method_1761(float f, float f1) {
        float f4;
        float f2 = 0.4f;
        float f3 = MathsHelper.cos(f * 3.141593f * 2.0f) - 0.0f;
        if (f3 >= (f4 = -0.0f) - f2 && f3 <= f4 + f2) {
            float f5 = (f3 - f4) / f2 * 0.5f + 0.5f;
            float f6 = 1.0f - (1.0f - MathsHelper.sin(f5 * 3.141593f)) * 0.99f;
            f6 *= f6;
            this.field_2180[0] = f5 * 0.3f + 0.7f;
            this.field_2180[1] = f5 * f5 * 0.7f + 0.2f;
            this.field_2180[2] = f5 * f5 * 0.0f + 0.2f;
            this.field_2180[3] = f6;
            return this.field_2180;
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public Vec3f getSkyColour(float temperature, float humidity) {
        float f2 = MathsHelper.cos(temperature * 3.141593f * 2.0f) * 2.0f + 0.5f;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        float f3 = 0.7529412f;
        float f4 = 0.8470588f;
        float f5 = 1.0f;
        return Vec3f.from(f3 *= f2 * 0.94f + 0.06f, f4 *= f2 * 0.94f + 0.06f, f5 *= f2 * 0.91f + 0.09f);
    }
}
