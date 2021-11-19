package io.github.ryuu.adventurecraft.mixin.level.gen;

import net.minecraft.level.biome.Biome;
import net.minecraft.level.gen.BiomeSource;
import net.minecraft.util.maths.Vec2i;
import net.minecraft.util.noise.SimplexOctaveNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(BiomeSource.class)
public class MixinBiomeSource {

    public double[] temperatureNoises;
    public double[] rainfallNoises;
    public double[] detailNoises;
    public MixinBiome[] biomes;
    @Shadow()
    private SimplexOctaveNoise temperatureNoise;
    private SimplexOctaveNoise rainfallNoise;
    private SimplexOctaveNoise detailNoise;
    private MixinLevel worldObj;

    protected MixinBiomeSource() {
    }

    public MixinBiomeSource(MixinLevel level) {
        this.temperatureNoise = new SimplexOctaveNoise(new Random(level.getSeed() * 9871L), 4);
        this.rainfallNoise = new SimplexOctaveNoise(new Random(level.getSeed() * 39811L), 4);
        this.detailNoise = new SimplexOctaveNoise(new Random(level.getSeed() * 543321L), 2);
        this.worldObj = level;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinBiome getBiomeInChunk(Vec2i pos) {
        return this.getBiome(pos.x << 4, pos.z << 4);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinBiome getBiome(int x, int z) {
        return this.getBiomes(x, z, 1, 1)[0];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double getTemperature(int x, int z) {
        this.temperatureNoises = this.temperatureNoise.sample(this.temperatureNoises, x, z, 1, 1, 0.025f, 0.025f, 0.5);
        return this.temperatureNoises[0];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinBiome[] getBiomes(int x, int z, int xSize, int zSize) {
        this.biomes = this.getBiomes(this.biomes, x, z, xSize, zSize);
        return this.biomes;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public double[] getTemperatures(double[] temperatures, int x, int z, int xSize, int zSize) {
        if (temperatures == null || temperatures.length < xSize * zSize) {
            temperatures = new double[xSize * zSize];
        }
        if (!this.worldObj.properties.useImages) {
            temperatures = this.temperatureNoise.sample(temperatures, x, z, xSize, zSize, 0.025f, 0.025f, 0.25);
            this.detailNoises = this.detailNoise.sample(this.detailNoises, x, z, xSize, zSize, 0.25, 0.25, 0.5882352941176471);
        }
        int i1 = 0;
        for (int j1 = 0; j1 < xSize; ++j1) {
            for (int k1 = 0; k1 < zSize; ++k1) {
                if (this.worldObj.properties.useImages) {
                    int x2 = x + j1;
                    int y = z + k1;
                    temperatures[i1] = TerrainImage.getTerrainTemperature(x2, y);
                } else {
                    double d = this.detailNoises[i1] * 1.1 + 0.5;
                    double d1 = 0.01;
                    double d2 = 1.0 - d1;
                    double d3 = (temperatures[i1] * 0.15 + 0.7) * d2 + d * d1;
                    if ((d3 = 1.0 - (1.0 - d3) * (1.0 - d3)) < 0.0) {
                        d3 = 0.0;
                    }
                    if (d3 > 1.0) {
                        d3 = 1.0;
                    }
                    temperatures[i1] = d3;
                }
                ++i1;
            }
        }
        return temperatures;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinBiome[] getBiomes(MixinBiome[] biomes, int x, int z, int xSize, int zSize) {
        if (biomes == null || biomes.length < xSize * zSize) {
            biomes = new MixinBiome[xSize * zSize];
        }
        this.temperatureNoises = this.temperatureNoise.sample(this.temperatureNoises, x, z, xSize, xSize, 0.025f, 0.025f, 0.25);
        this.rainfallNoises = this.rainfallNoise.sample(this.rainfallNoises, x, z, xSize, xSize, 0.05f, 0.05f, 0.3333333333333333);
        this.detailNoises = this.detailNoise.sample(this.detailNoises, x, z, xSize, xSize, 0.25, 0.25, 0.5882352941176471);
        int i1 = 0;
        double d3 = 0.0;
        double d4 = 0.0;
        for (int j1 = 0; j1 < xSize; ++j1) {
            for (int k1 = 0; k1 < zSize; ++k1) {
                if (this.worldObj.properties.useImages) {
                    int x2 = x + j1;
                    int y = z + k1;
                    d3 = TerrainImage.getTerrainTemperature(x2, y);
                    d4 = TerrainImage.getTerrainHumidity(x2, y);
                } else {
                    double d = this.detailNoises[i1] * 1.1 + 0.5;
                    double d1 = 0.01;
                    double d2 = 1.0 - d1;
                    d3 = (this.temperatureNoises[i1] * 0.15 + 0.7) * d2 + d * d1;
                    d1 = 0.002;
                    d2 = 1.0 - d1;
                    d4 = (this.rainfallNoises[i1] * 0.15 + 0.5) * d2 + d * d1;
                    if ((d3 = 1.0 - (1.0 - d3) * (1.0 - d3)) < 0.0) {
                        d3 = 0.0;
                    }
                    if (d4 < 0.0) {
                        d4 = 0.0;
                    }
                    if (d3 > 1.0) {
                        d3 = 1.0;
                    }
                    if (d4 > 1.0) {
                        d4 = 1.0;
                    }
                }
                this.temperatureNoises[i1] = d3;
                this.rainfallNoises[i1] = d4;
                biomes[i1++] = Biome.getBiome(d3, d4);
            }
        }
        return biomes;
    }
}
