package io.github.ryuu.adventurecraft.mixin.level.source;

import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.gen.Cave;
import net.minecraft.level.gen.OverworldCave;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.structure.*;
import net.minecraft.tile.SandTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.noise.PerlinOctaveNoise;

import java.util.Random;

public class MixinOverworldLevelSource implements LevelSource {
    private Random rand;
    private PerlinOctaveNoise upperInterpolationNoise;
    private PerlinOctaveNoise lowerInterpolationNoise;
    private PerlinOctaveNoise interpolationNoise;
    private PerlinOctaveNoise beachNoise;
    private PerlinOctaveNoise surfaceDepthNoise;
    public PerlinOctaveNoise biomeNoise;
    public PerlinOctaveNoise depthNoise;
    public PerlinOctaveNoise treeNoise;
    private Level level;
    private double[] noises;
    private double[] sandNoises = new double[256];
    private double[] gravelNoises = new double[256];
    private double[] surfaceDepthNoises = new double[256];
    private Cave cave = new OverworldCave();
    private Biome[] biomes;
    double[] interpolationNoises;
    double[] upperInterpolationNoises;
    double[] lowerInterpolationNoises;
    double[] biomeNoises;
    double[] depthNoises;
    int[][] unusedVals = new int[32][32];
    private double[] temperatureNoises;
    public double mapSize = 250.0;
    public int waterLevel = 64;
    public double fractureHorizontal = 1.0;
    public double fractureVertical = 1.0;
    public double maxAvgDepth = 0.0;
    public double maxAvgHeight = 0.0;
    public double volatility1 = 1.0;
    public double volatility2 = 1.0;
    public double volatilityWeight1 = 0.0;
    public double volatilityWeight2 = 1.0;

    public MixinOverworldLevelSource(Level level, long seed) {
        this.level = level;
        this.rand = new Random(seed);
        this.upperInterpolationNoise = new PerlinOctaveNoise(this.rand, 16);
        this.lowerInterpolationNoise = new PerlinOctaveNoise(this.rand, 16);
        this.interpolationNoise = new PerlinOctaveNoise(this.rand, 8);
        this.beachNoise = new PerlinOctaveNoise(this.rand, 4);
        this.surfaceDepthNoise = new PerlinOctaveNoise(this.rand, 4);
        this.biomeNoise = new PerlinOctaveNoise(this.rand, 10);
        this.depthNoise = new PerlinOctaveNoise(this.rand, 16);
        this.treeNoise = new PerlinOctaveNoise(this.rand, 8);
    }

    public void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures) {
        int byte0 = 4;
        int k = byte0 + 1;
        int byte2 = 17;
        int l = byte0 + 1;
        this.noises = this.calculateNoise(this.noises, chunkX * byte0, 0, chunkZ * byte0, k, byte2, l);
        for (int xOuter = 0; xOuter < byte0; ++xOuter) {
            for (int zOuter = 0; zOuter < byte0; ++zOuter) {
                for (int yOuter = 0; yOuter < 16; ++yOuter) {
                    double d = 0.125;
                    double d1 = this.noises[((xOuter + 0) * l + (zOuter + 0)) * byte2 + (yOuter + 0)];
                    double d2 = this.noises[((xOuter + 0) * l + (zOuter + 1)) * byte2 + (yOuter + 0)];
                    double d3 = this.noises[((xOuter + 1) * l + (zOuter + 0)) * byte2 + (yOuter + 0)];
                    double d4 = this.noises[((xOuter + 1) * l + (zOuter + 1)) * byte2 + (yOuter + 0)];
                    double d5 = (this.noises[((xOuter + 0) * l + (zOuter + 0)) * byte2 + (yOuter + 1)] - d1) * d;
                    double d6 = (this.noises[((xOuter + 0) * l + (zOuter + 1)) * byte2 + (yOuter + 1)] - d2) * d;
                    double d7 = (this.noises[((xOuter + 1) * l + (zOuter + 0)) * byte2 + (yOuter + 1)] - d3) * d;
                    double d8 = (this.noises[((xOuter + 1) * l + (zOuter + 1)) * byte2 + (yOuter + 1)] - d4) * d;
                    for (int yInner = 0; yInner < 8; ++yInner) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int xInner = 0; xInner < 4; ++xInner) {
                            int j2 = xInner + xOuter * 4 << 11 | 0 + zOuter * 4 << 7 | yOuter * 8 + yInner;
                            int c = 128;
                            double d14 = 0.25;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for (int zInner = 0; zInner < 4; ++zInner) {
                                int x = Math.abs(chunkX * 16 + (xOuter * 4 + xInner));
                                int y = Math.abs(chunkZ * 16 + (zOuter * 4 + zInner));
                                double reduceBy = Math.max(Math.sqrt(x * x + y * y) - this.mapSize, 0.0) / 2.0;
                                double d17 = temperatures[(xOuter * 4 + xInner) * 16 + (zOuter * 4 + zInner)];
                                int l2 = 0;
                                if (yOuter * 8 + yInner < this.waterLevel) {
                                    l2 = d17 < 0.5 && yOuter * 8 + yInner >= this.waterLevel - 1 ? Tile.ICE.id : Tile.STILL_WATER.id;
                                }
                                if (d15 - reduceBy > 0.0) {
                                    l2 = Tile.STONE.id;
                                }
                                tiles[j2] = (byte)l2;
                                j2 += c;
                                d15 += d16;
                            }
                            d10 += d12;
                            d11 += d13;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
        double d = 0.03125;
        this.sandNoises = this.beachNoise.sample(this.sandNoises, chunkX * 16, chunkZ * 16, 0.0, 16, 16, 1, d, d, 1.0);
        this.gravelNoises = this.beachNoise.sample(this.gravelNoises, chunkX * 16, 109.0134, chunkZ * 16, 16, 1, 16, d, 1.0, d);
        this.surfaceDepthNoises = this.surfaceDepthNoise.sample(this.surfaceDepthNoises, chunkX * 16, chunkZ * 16, 0.0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome biomegenbase = biomes[k + l * 16];
                boolean flag = this.sandNoises[k + l * 16] + this.rand.nextDouble() * 0.2 > 0.0;
                boolean flag1 = this.gravelNoises[k + l * 16] + this.rand.nextDouble() * 0.2 > 3.0;
                int i1 = (int)(this.surfaceDepthNoises[k + l * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
                int j1 = -1;
                byte byte1 = biomegenbase.topTileId;
                byte byte2 = biomegenbase.underTileId;
                for (int k1 = 127; k1 >= 0; --k1) {
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte3 = tiles[l1];
                    if (byte3 == 0) {
                        j1 = -1;
                        continue;
                    }
                    if (byte3 != Tile.STONE.id) continue;
                    if (j1 == -1) {
                        if (i1 <= 0) {
                            byte1 = 0;
                            byte2 = (byte)Tile.STONE.id;
                        } else if (k1 >= this.waterLevel - 4 && k1 <= this.waterLevel + 1) {
                            byte1 = biomegenbase.topTileId;
                            byte2 = biomegenbase.underTileId;
                            if (flag1) {
                                byte1 = 0;
                            }
                            if (flag1) {
                                byte2 = (byte)Tile.GRAVEL.id;
                            }
                            if (flag) {
                                byte1 = (byte)Tile.SAND.id;
                            }
                            if (flag) {
                                byte2 = (byte)Tile.SAND.id;
                            }
                        }
                        if (k1 < this.waterLevel && byte1 == 0) {
                            byte1 = (byte)Tile.STILL_WATER.id;
                        }
                        j1 = i1;
                        if (k1 >= this.waterLevel - 1) {
                            tiles[l1] = byte1;
                            continue;
                        }
                        tiles[l1] = byte2;
                        continue;
                    }
                    if (j1 <= 0) continue;
                    tiles[l1] = byte2;
                    if (--j1 != 0 || byte2 != Tile.SAND.id) continue;
                    j1 = this.rand.nextInt(4);
                    byte2 = (byte)Tile.SANDSTONE.id;
                }
            }
        }
    }

    public Chunk loadChunk(int x, int z) {
        return this.getChunk(x, z);
    }

    public Chunk getChunk(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        byte[] abyte0 = new byte[32768];
        Chunk chunk = new Chunk(this.level, abyte0, x, z);
        this.biomes = this.level.getBiomeSource().getBiomes(this.biomes, x * 16, z * 16, 16, 16);
        double[] ad = this.level.getBiomeSource().temperatureNoises;
        this.shapeChunk(x, z, abyte0, this.biomes, ad);
        this.buildSurface(x, z, abyte0, this.biomes);
        this.cave.generate(this, this.level, x, z, abyte0);
        chunk.generateHeightmap();
        chunk.shouldSave = false;
        return chunk;
    }

    private double[] calculateNoise(double[] noises, int chunkX, int chunkY, int chunkZ, int noiseResolutionX, int noiseResolutionY, int noiseResolutionZ) {
        if (noises == null) {
            noises = new double[noiseResolutionX * noiseResolutionY * noiseResolutionZ];
        }
        double d = 684.412 * this.fractureHorizontal;
        double d1 = 684.412 * this.fractureVertical;
        double[] ad1 = this.level.getBiomeSource().temperatureNoises;
        double[] ad2 = this.level.getBiomeSource().rainfallNoises;
        this.biomeNoises = this.biomeNoise.sample(this.biomeNoises, chunkX, chunkZ, noiseResolutionX, noiseResolutionZ, 1.121, 1.121, 0.5);
        this.depthNoises = this.depthNoise.sample(this.depthNoises, chunkX, chunkZ, noiseResolutionX, noiseResolutionZ, 200.0, 200.0, 0.5);
        this.interpolationNoises = this.interpolationNoise.sample(this.interpolationNoises, chunkX, chunkY, chunkZ, noiseResolutionX, noiseResolutionY, noiseResolutionZ, d / 80.0, d1 / 160.0, d / 80.0);
        this.upperInterpolationNoises = this.upperInterpolationNoise.sample(this.upperInterpolationNoises, chunkX, chunkY, chunkZ, noiseResolutionX, noiseResolutionY, noiseResolutionZ, d, d1, d);
        this.lowerInterpolationNoises = this.lowerInterpolationNoise.sample(this.lowerInterpolationNoises, chunkX, chunkY, chunkZ, noiseResolutionX, noiseResolutionY, noiseResolutionZ, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / noiseResolutionX;
        for (int j2 = 0; j2 < noiseResolutionX; ++j2) {
            int k2 = j2 * i2 + i2 / 2;
            for (int l2 = 0; l2 < noiseResolutionZ; ++l2) {
                double d6;
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0 - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0 - d4;
                double d5 = (this.biomeNoises[l1] + 256.0) / 512.0;
                if ((d5 *= d4) > 1.0) {
                    d5 = 1.0;
                }
                if ((d6 = this.depthNoises[l1] / 8000.0) < 0.0) {
                    d6 = -d6 * 0.3;
                }
                if ((d6 = d6 * 3.0 - 2.0) < 0.0) {
                    if ((d6 /= 2.0) < -1.0) {
                        d6 = -1.0;
                    }
                    d6 -= this.maxAvgDepth;
                    d6 /= 1.4;
                    d6 /= 2.0;
                    d5 = 0.0;
                } else {
                    if (d6 > 1.0) {
                        d6 = 1.0;
                    }
                    d6 += this.maxAvgHeight;
                    d6 /= 8.0;
                }
                if (d5 < 0.0) {
                    d5 = 0.0;
                }
                d5 += 0.5;
                d6 = d6 * (double)noiseResolutionY / 16.0;
                double d7 = (double)noiseResolutionY / 2.0 + d6 * 4.0;
                ++l1;
                for (int j3 = 0; j3 < noiseResolutionY; ++j3) {
                    double d8 = 0.0;
                    double d9 = ((double)j3 - d7) * 12.0 / d5;
                    if (d9 < 0.0) {
                        d9 *= 4.0;
                    }
                    double d10 = this.upperInterpolationNoises[k1] / 512.0 * this.volatility1;
                    double d11 = this.lowerInterpolationNoises[k1] / 512.0 * this.volatility2;
                    double d12 = (this.interpolationNoises[k1] / 10.0 + 1.0) / 2.0;
                    d8 = d12 < this.volatilityWeight1 ? d10 : (d12 > this.volatilityWeight2 ? d11 : d10 + (d11 - d10) * d12);
                    d8 -= d9;
                    if (j3 > noiseResolutionY - 4) {
                        double d13 = (float)(j3 - (noiseResolutionY - 4)) / 3.0f;
                        d8 = d8 * (1.0 - d13) + -10.0 * d13;
                    }
                    noises[k1] = d8;
                    ++k1;
                }
            }
        }
        return noises;
    }

    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return true;
    }

    public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
        SandTile.fallInstantly = true;
        int k = chunkX * 16;
        int l = chunkZ * 16;
        Biome biomegenbase = this.level.getBiomeSource().getBiome(k + 16, l + 16);
        this.rand.setSeed(this.level.getSeed());
        long l1 = this.rand.nextLong() / 2L * 2L + 1L;
        long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)chunkX * l1 + (long)chunkZ * l2 ^ this.level.getSeed());
        double d = 0.25;
        if (this.rand.nextInt(4) == 0) {
            int i1 = k + this.rand.nextInt(16) + 8;
            int l4 = this.rand.nextInt(128);
            int i8 = l + this.rand.nextInt(16) + 8;
            new Lake(Tile.STILL_WATER.id).generate(this.level, this.rand, i1, l4, i8);
        }
        if (this.rand.nextInt(8) == 0) {
            int j1 = k + this.rand.nextInt(16) + 8;
            int i5 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int j8 = l + this.rand.nextInt(16) + 8;
            if (i5 < 64 || this.rand.nextInt(10) == 0) {
                new Lake(Tile.STILL_LAVA.id).generate(this.level, this.rand, j1, i5, j8);
            }
        }
        for (int k1 = 0; k1 < 8; ++k1) {
            int j5 = k + this.rand.nextInt(16) + 8;
            int k8 = this.rand.nextInt(128);
            int j11 = l + this.rand.nextInt(16) + 8;
            new MobSpawnerRoom().generate(this.level, this.rand, j5, k8, j11);
        }
        for (int i2 = 0; i2 < 10; ++i2) {
            int k5 = k + this.rand.nextInt(16);
            int l8 = this.rand.nextInt(128);
            int k11 = l + this.rand.nextInt(16);
            new ClayDisk(32).generate(this.level, this.rand, k5, l8, k11);
        }
        for (int j2 = 0; j2 < 20; ++j2) {
            int l5 = k + this.rand.nextInt(16);
            int i9 = this.rand.nextInt(128);
            int l11 = l + this.rand.nextInt(16);
            new Ore(Tile.DIRT.id, 32).generate(this.level, this.rand, l5, i9, l11);
        }
        for (int k2 = 0; k2 < 10; ++k2) {
            int i6 = k + this.rand.nextInt(16);
            int j9 = this.rand.nextInt(128);
            int i12 = l + this.rand.nextInt(16);
            new Ore(Tile.GRAVEL.id, 32).generate(this.level, this.rand, i6, j9, i12);
        }
        for (int i3 = 0; i3 < 20; ++i3) {
            int j6 = k + this.rand.nextInt(16);
            int k9 = this.rand.nextInt(128);
            int j12 = l + this.rand.nextInt(16);
            new Ore(Tile.COAL_ORE.id, 16).generate(this.level, this.rand, j6, k9, j12);
        }
        for (int j3 = 0; j3 < 20; ++j3) {
            int k6 = k + this.rand.nextInt(16);
            int l9 = this.rand.nextInt(64);
            int k12 = l + this.rand.nextInt(16);
            new Ore(Tile.IRON_ORE.id, 8).generate(this.level, this.rand, k6, l9, k12);
        }
        for (int k3 = 0; k3 < 2; ++k3) {
            int l6 = k + this.rand.nextInt(16);
            int i10 = this.rand.nextInt(32);
            int l12 = l + this.rand.nextInt(16);
            new Ore(Tile.GOLD_ORE.id, 8).generate(this.level, this.rand, l6, i10, l12);
        }
        for (int l3 = 0; l3 < 8; ++l3) {
            int i7 = k + this.rand.nextInt(16);
            int j10 = this.rand.nextInt(16);
            int i13 = l + this.rand.nextInt(16);
            new Ore(Tile.REDSTONE_ORE.id, 7).generate(this.level, this.rand, i7, j10, i13);
        }
        for (int i4 = 0; i4 < 1; ++i4) {
            int j7 = k + this.rand.nextInt(16);
            int k10 = this.rand.nextInt(16);
            int j13 = l + this.rand.nextInt(16);
            new Ore(Tile.DIAMOND_ORE.id, 7).generate(this.level, this.rand, j7, k10, j13);
        }
        for (int j4 = 0; j4 < 1; ++j4) {
            int k7 = k + this.rand.nextInt(16);
            int l10 = this.rand.nextInt(16) + this.rand.nextInt(16);
            int k13 = l + this.rand.nextInt(16);
            new Ore(Tile.LAPIS_LAZULI_ORE.id, 6).generate(this.level, this.rand, k7, l10, k13);
        }
        d = 0.5;
        int k4 = (int)((this.treeNoise.sample((double)k * d, (double)l * d) / 8.0 + this.rand.nextDouble() * 4.0 + 4.0) / 3.0);
        int l7 = 0;
        if (this.rand.nextInt(10) == 0) {
            ++l7;
        }
        if (biomegenbase == Biome.FOREST) {
            l7 += k4 + 5;
        }
        if (biomegenbase == Biome.RAINFOREST) {
            l7 += k4 + 5;
        }
        if (biomegenbase == Biome.SEASONAL_FOREST) {
            l7 += k4 + 2;
        }
        if (biomegenbase == Biome.TAIGA) {
            l7 += k4 + 5;
        }
        if (biomegenbase == Biome.DESERT) {
            l7 -= 20;
        }
        if (biomegenbase == Biome.TUNDRA) {
            l7 -= 20;
        }
        if (biomegenbase == Biome.PLAINS) {
            l7 -= 20;
        }
        for (int i11 = 0; i11 < l7; ++i11) {
            int l13 = k + this.rand.nextInt(16) + 8;
            int j14 = l + this.rand.nextInt(16) + 8;
            Feature worldgenerator = biomegenbase.getTree(this.rand);
            worldgenerator.setupTreeGeneration(1.0, 1.0, 1.0);
            worldgenerator.generate(this.level, this.rand, l13, this.level.getHeight(l13, j14), j14);
        }
        int byte0 = 0;
        if (biomegenbase == Biome.FOREST) {
            byte0 = 2;
        }
        if (biomegenbase == Biome.SEASONAL_FOREST) {
            byte0 = 4;
        }
        if (biomegenbase == Biome.TAIGA) {
            byte0 = 2;
        }
        if (biomegenbase == Biome.PLAINS) {
            byte0 = 3;
        }
        for (int i14 = 0; i14 < byte0; ++i14) {
            int k14 = k + this.rand.nextInt(16) + 8;
            int l16 = this.rand.nextInt(128);
            int k19 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.DANDELION.id).generate(this.level, this.rand, k14, l16, k19);
        }
        int byte1 = 0;
        if (biomegenbase == Biome.FOREST) {
            byte1 = 2;
        }
        if (biomegenbase == Biome.RAINFOREST) {
            byte1 = 10;
        }
        if (biomegenbase == Biome.SEASONAL_FOREST) {
            byte1 = 2;
        }
        if (biomegenbase == Biome.TAIGA) {
            byte1 = 1;
        }
        if (biomegenbase == Biome.PLAINS) {
            byte1 = 10;
        }
        for (int l14 = 0; l14 < byte1; ++l14) {
            int byte2 = 1;
            if (biomegenbase == Biome.RAINFOREST && this.rand.nextInt(3) != 0) {
                byte2 = 2;
            }
            int l19 = k + this.rand.nextInt(16) + 8;
            int k22 = this.rand.nextInt(128);
            int j24 = l + this.rand.nextInt(16) + 8;
            new TallgrassPatch(Tile.TALLGRASS.id, byte2).generate(this.level, this.rand, l19, k22, j24);
        }
        byte1 = 0;
        if (biomegenbase == Biome.DESERT) {
            byte1 = 2;
        }
        for (int i15 = 0; i15 < byte1; ++i15) {
            int i17 = k + this.rand.nextInt(16) + 8;
            int i20 = this.rand.nextInt(128);
            int l22 = l + this.rand.nextInt(16) + 8;
            new DeadbushPatch(Tile.DEADBUSH.id).generate(this.level, this.rand, i17, i20, l22);
        }
        if (this.rand.nextInt(2) == 0) {
            int j15 = k + this.rand.nextInt(16) + 8;
            int j17 = this.rand.nextInt(128);
            int j20 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.ROSE.id).generate(this.level, this.rand, j15, j17, j20);
        }
        if (this.rand.nextInt(4) == 0) {
            int k15 = k + this.rand.nextInt(16) + 8;
            int k17 = this.rand.nextInt(128);
            int k20 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.BROWN_MUSHROOM.id).generate(this.level, this.rand, k15, k17, k20);
        }
        if (this.rand.nextInt(8) == 0) {
            int l15 = k + this.rand.nextInt(16) + 8;
            int l17 = this.rand.nextInt(128);
            int l20 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.RED_MUSHROOM.id).generate(this.level, this.rand, l15, l17, l20);
        }
        for (int i16 = 0; i16 < 10; ++i16) {
            int i18 = k + this.rand.nextInt(16) + 8;
            int i21 = this.rand.nextInt(128);
            int i23 = l + this.rand.nextInt(16) + 8;
            new ReedsPatch().generate(this.level, this.rand, i18, i21, i23);
        }
        if (this.rand.nextInt(32) == 0) {
            int j16 = k + this.rand.nextInt(16) + 8;
            int j18 = this.rand.nextInt(128);
            int j21 = l + this.rand.nextInt(16) + 8;
            new PumpkinPatch().generate(this.level, this.rand, j16, j18, j21);
        }
        int k16 = 0;
        if (biomegenbase == Biome.DESERT) {
            k16 += 10;
        }
        for (int k18 = 0; k18 < k16; ++k18) {
            int k21 = k + this.rand.nextInt(16) + 8;
            int j23 = this.rand.nextInt(128);
            int k24 = l + this.rand.nextInt(16) + 8;
            new CactusPatch().generate(this.level, this.rand, k21, j23, k24);
        }
        for (int l18 = 0; l18 < 50; ++l18) {
            int l21 = k + this.rand.nextInt(16) + 8;
            int k23 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int l24 = l + this.rand.nextInt(16) + 8;
            new Spring(Tile.FLOWING_WATER.id).generate(this.level, this.rand, l21, k23, l24);
        }
        for (int i19 = 0; i19 < 20; ++i19) {
            int i22 = k + this.rand.nextInt(16) + 8;
            int l23 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            int i25 = l + this.rand.nextInt(16) + 8;
            new Spring(Tile.FLOWING_LAVA.id).generate(this.level, this.rand, i22, l23, i25);
        }
        this.temperatureNoises = this.level.getBiomeSource().getTemperatures(this.temperatureNoises, k + 8, l + 8, 16, 16);
        for (int j19 = k + 8; j19 < k + 8 + 16; ++j19) {
            for (int j22 = l + 8; j22 < l + 8 + 16; ++j22) {
                int i24 = j19 - (k + 8);
                int j25 = j22 - (l + 8);
                int k25 = this.level.getOceanFloorHeight(j19, j22);
                double d1 = this.temperatureNoises[i24 * 16 + j25] - (double)(k25 - 64) / 64.0 * 0.3;
                if (!(d1 < 0.5) || k25 <= 0 || k25 >= 128 || !this.level.isAir(j19, k25, j22) || !this.level.getMaterial(j19, k25 - 1, j22).blocksMovement() || this.level.getMaterial(j19, k25 - 1, j22) == Material.ICE) continue;
                this.level.setTile(j19, k25, j22, Tile.SNOW.id);
            }
        }
        SandTile.fallInstantly = false;
    }

    public boolean saveChunks(boolean flag, ProgressListener listener) {
        return true;
    }

    public boolean method_1801() {
        return false;
    }

    public boolean isClean() {
        return true;
    }

    public String toString() {
        return "RandomLevelSource";
    }
}