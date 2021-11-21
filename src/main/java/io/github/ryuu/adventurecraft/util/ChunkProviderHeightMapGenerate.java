package io.github.ryuu.adventurecraft.util;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.structure.CactusPatch;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.MushroomPatch;
import net.minecraft.tile.SandTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.noise.PerlinOctaveNoise;

public class ChunkProviderHeightMapGenerate implements LevelSource {

    private Random rand;

    private PerlinOctaveNoise field_912_k;

    private PerlinOctaveNoise field_911_l;

    private PerlinOctaveNoise field_910_m;

    private PerlinOctaveNoise field_908_o;

    public PerlinOctaveNoise field_922_a;

    public PerlinOctaveNoise field_921_b;

    public PerlinOctaveNoise mobSpawnerNoise;

    private Level worldObj;

    private double[] stoneNoise = new double[256];

    private Biome[] biomesForGeneration;

    double[] field_4185_d;

    double[] field_4184_e;

    double[] field_4183_f;

    double[] field_4182_g;

    double[] field_4181_h;

    int[][] unusedIntArray32x32 = new int[32][32];

    private double[] generatedTemperatures;

    public ChunkProviderHeightMapGenerate(Level world, long l) {
        this.worldObj = world;
        this.rand = new Random(l);
        this.field_912_k = new PerlinOctaveNoise(this.rand, 16);
        this.field_911_l = new PerlinOctaveNoise(this.rand, 16);
        this.field_910_m = new PerlinOctaveNoise(this.rand, 8);
        this.field_908_o = new PerlinOctaveNoise(this.rand, 4);
        this.field_922_a = new PerlinOctaveNoise(this.rand, 10);
        this.field_921_b = new PerlinOctaveNoise(this.rand, 16);
        this.mobSpawnerNoise = new PerlinOctaveNoise(this.rand, 8);
    }

    public void generateTerrain(int i, int j, byte[] abyte0, Biome[] amobspawnerbase, double[] ad) {
        int byte0 = 4;
        for (int i1 = 0; i1 < byte0; ++i1) {
            for (int j1 = 0; j1 < byte0; ++j1) {
                for (int k1 = 0; k1 < 16; ++k1) {
                    for (int l1 = 0; l1 < 8; ++l1) {
                        for (int i2 = 0; i2 < 4; ++i2) {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            int c = 128;
                            for (int k2 = 0; k2 < 4; ++k2) {
                                int height;
                                int x = i * 16 + (i1 * 4 + i2);
                                int y = j * 16 + (j1 * 4 + k2);
                                double d17 = ad[(i1 * 4 + i2) * 16 + (j1 * 4 + k2)];
                                int l2 = 0;
                                int waterHeight = TerrainImage.getWaterHeight(x, y);
                                if (k1 * 8 + l1 < waterHeight) {
                                    l2 = d17 < 0.5 && k1 * 8 + l1 >= waterHeight - 1 ? Tile.ICE.id : Tile.FLOWING_WATER.id;
                                }
                                if (k1 * 8 + l1 <= (height = TerrainImage.getTerrainHeight(x, y))) {
                                    l2 = Tile.STONE.id;
                                }
                                abyte0[j2] = (byte) l2;
                                j2 += c;
                            }
                        }
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int i, int j, byte[] abyte0, Biome[] amobspawnerbase) {
        double d = 0.03125;
        this.stoneNoise = this.field_908_o.sample(this.stoneNoise, i * 16, j * 16, 0.0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome mobspawnerbase = amobspawnerbase[k + l * 16];
                int i1 = (int) (this.stoneNoise[k + l * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
                int j1 = -1;
                byte byte1 = mobspawnerbase.topTileId;
                byte byte2 = mobspawnerbase.underTileId;
                for (int k1 = 127; k1 >= 0; --k1) {
                    int x = k + (15 - i) * 16;
                    int z = l + (15 - j) * 16;
                    int waterHeight = TerrainImage.getWaterHeight(x, z);
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte3 = abyte0[l1];
                    if (byte3 == 0) {
                        j1 = -1;
                        continue;
                    }
                    if (byte3 != Tile.STONE.id)
                        continue;
                    if (j1 == -1) {
                        if (k1 >= waterHeight - 4 && k1 <= waterHeight + 1) {
                            byte1 = mobspawnerbase.topTileId;
                            byte2 = mobspawnerbase.underTileId;
                            if (TerrainImage.hasSandNearWaterEdge(x, z)) {
                                byte1 = (byte) Tile.SAND.id;
                                byte2 = (byte) Tile.SAND.id;
                            }
                        }
                        if (k1 < waterHeight && byte1 == 0) {
                            byte1 = (byte) Tile.FLOWING_WATER.id;
                        }
                        j1 = i1;
                        if (k1 >= waterHeight - 1) {
                            abyte0[l1] = byte1;
                            continue;
                        }
                        abyte0[l1] = byte2;
                        continue;
                    }
                    if (j1 <= 0)
                        continue;
                    --j1;
                    abyte0[l1] = byte2;
                }
            }
        }
    }

    @Override
    public Chunk loadChunk(int x, int z) {
        return this.getChunk(x, z);
    }

    @Override
    public Chunk getChunk(int x, int z) {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        byte[] abyte0 = new byte[32768];
        Chunk chunk = new Chunk(this.worldObj, abyte0, x, z);
        this.biomesForGeneration = this.worldObj.getBiomeSource().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        double[] ad = this.worldObj.getBiomeSource().temperatureNoises;
        this.generateTerrain(x, z, abyte0, this.biomesForGeneration, ad);
        this.replaceBlocksForBiome(x, z, abyte0, this.biomesForGeneration);
        chunk.generateHeightmap();
        chunk.shouldSave = false;
        return chunk;
    }

    private double[] func_4061_a(double[] ad, int i, int j, int k, int l, int i1, int j1) {
        if (ad == null) {
            ad = new double[l * i1 * j1];
        }
        double d = 684.412;
        double d1 = 684.412;
        double[] ad1 = this.worldObj.getBiomeSource().temperatureNoises;
        double[] ad2 = this.worldObj.getBiomeSource().rainfallNoises;
        this.field_4182_g = this.field_922_a.sample(this.field_4182_g, i, k, l, j1, 1.121, 1.121, 0.5);
        this.field_4181_h = this.field_921_b.sample(this.field_4181_h, i, k, l, j1, 200.0, 200.0, 0.5);
        this.field_4185_d = this.field_910_m.sample(this.field_4185_d, i, j, k, l, i1, j1, d / 80.0, d1 / 160.0, d / 80.0);
        this.field_4184_e = this.field_912_k.sample(this.field_4184_e, i, j, k, l, i1, j1, d, d1, d);
        this.field_4183_f = this.field_911_l.sample(this.field_4183_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;
        for (int j2 = 0; j2 < l; ++j2) {
            int k2 = j2 * i2 + i2 / 2;
            for (int l2 = 0; l2 < j1; ++l2) {
                double d6;
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0 - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0 - d4;
                double d5 = (this.field_4182_g[l1] + 256.0) / 512.0;
                if ((d5 *= d4) > 1.0) {
                    d5 = 1.0;
                }
                if ((d6 = this.field_4181_h[l1] / 8000.0) < 0.0) {
                    d6 = -d6 * 0.3;
                }
                if ((d6 = d6 * 3.0 - 2.0) < 0.0) {
                    if ((d6 /= 2.0) < -1.0) {
                        d6 = -1.0;
                    }
                    d6 /= 1.4;
                    d6 /= 2.0;
                    d5 = 0.0;
                } else {
                    if (d6 > 1.0) {
                        d6 = 1.0;
                    }
                    d6 /= 8.0;
                }
                if (d5 < 0.0) {
                    d5 = 0.0;
                }
                d5 += 0.5;
                d6 = d6 * (double) i1 / 16.0;
                double d7 = (double) i1 / 2.0 + d6 * 4.0;
                ++l1;
                for (int j3 = 0; j3 < i1; ++j3) {
                    double d8 = 0.0;
                    double d9 = ((double) j3 - d7) * 12.0 / d5;
                    if (d9 < 0.0) {
                        d9 *= 4.0;
                    }
                    double d10 = this.field_4184_e[k1] / 512.0;
                    double d11 = this.field_4183_f[k1] / 512.0;
                    double d12 = (this.field_4185_d[k1] / 10.0 + 1.0) / 2.0;
                    d8 = d12 < 0.0 ? d10 : (d12 > 1.0 ? d11 : d10 + (d11 - d10) * d12);
                    d8 -= d9;
                    if (j3 > i1 - 4) {
                        double d13 = (float) (j3 - (i1 - 4)) / 3.0f;
                        d8 = d8 * (1.0 - d13) + -10.0 * d13;
                    }
                    ad[k1] = d8;
                    ++k1;
                }
            }
        }
        return ad;
    }

    @Override
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
        SandTile.fallInstantly = true;
        int k = chunkX * 16;
        int l = chunkZ * 16;
        Biome mobspawnerbase = this.worldObj.getBiomeSource().getBiome(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long l1 = this.rand.nextLong() / 2L * 2L + 1L;
        long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ this.worldObj.getSeed());
        double d = 0.25;
        d = 0.5;
        int k4 = (int) ((this.mobSpawnerNoise.sample((double) k * d, (double) l * d) / 8.0 + this.rand.nextDouble() * 4.0 + 4.0) / 3.0);
        int l7 = 0;
        if (this.rand.nextInt(10) == 0) {
            ++l7;
        }
        if (mobspawnerbase == Biome.FOREST) {
            l7 += k4 + 5;
        } else if (mobspawnerbase == Biome.RAINFOREST) {
            l7 += k4 + 5;
        } else if (mobspawnerbase == Biome.SEASONAL_FOREST) {
            l7 += k4 + 2;
        } else if (mobspawnerbase == Biome.TAIGA) {
            l7 += k4 + 5;
        } else if (mobspawnerbase == Biome.DESERT) {
            l7 -= 20;
        } else if (mobspawnerbase == Biome.TUNDRA) {
            l7 -= 20;
        } else if (mobspawnerbase == Biome.PLAINS) {
            l7 -= 20;
        }
        for (int i11 = 0; i11 < l7; ++i11) {
            int k15 = k + this.rand.nextInt(16) + 8;
            int j18 = l + this.rand.nextInt(16) + 8;
            Feature worldgenerator = mobspawnerbase.getTree(this.rand);
            worldgenerator.setupTreeGeneration(1.0, 1.0, 1.0);
            worldgenerator.generate(this.worldObj, this.rand, k15, this.worldObj.getHeight(k15, j18), j18);
        }
        for (int j11 = 0; j11 < 2; ++j11) {
            int l15 = k + this.rand.nextInt(16) + 8;
            int k18 = this.rand.nextInt(128);
            int i21 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.DANDELION.id).generate(this.worldObj, this.rand, l15, k18, i21);
        }
        if (this.rand.nextInt(2) == 0) {
            int k11 = k + this.rand.nextInt(16) + 8;
            int i16 = this.rand.nextInt(128);
            int l18 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.ROSE.id).generate(this.worldObj, this.rand, k11, i16, l18);
        }
        if (this.rand.nextInt(4) == 0) {
            int l11 = k + this.rand.nextInt(16) + 8;
            int j16 = this.rand.nextInt(128);
            int i19 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.BROWN_MUSHROOM.id).generate(this.worldObj, this.rand, l11, j16, i19);
        }
        if (this.rand.nextInt(8) == 0) {
            int i12 = k + this.rand.nextInt(16) + 8;
            int k16 = this.rand.nextInt(128);
            int j19 = l + this.rand.nextInt(16) + 8;
            new MushroomPatch(Tile.RED_MUSHROOM.id).generate(this.worldObj, this.rand, i12, k16, j19);
        }
        int l12 = 0;
        if (mobspawnerbase == Biome.DESERT) {
            l12 += 10;
        }
        for (int j17 = 0; j17 < l12; ++j17) {
            int i20 = k + this.rand.nextInt(16) + 8;
            int k21 = this.rand.nextInt(128);
            int k22 = l + this.rand.nextInt(16) + 8;
            new CactusPatch().generate(this.worldObj, this.rand, i20, k21, k22);
        }
        this.generatedTemperatures = this.worldObj.getBiomeSource().getTemperatures(this.generatedTemperatures, k + 8, l + 8, 16, 16);
        for (int i18 = k + 8; i18 < k + 8 + 16; ++i18) {
            for (int l20 = l + 8; l20 < l + 8 + 16; ++l20) {
                int j22 = i18 - (k + 8);
                int j23 = l20 - (l + 8);
                int k23 = this.worldObj.getOceanFloorHeight(i18, l20);
                double d1 = this.generatedTemperatures[j22 * 16 + j23];
                this.worldObj.setTemperatureValue(i18, l20, d1);
                if (!(d1 < 0.5) || k23 <= 0 || k23 >= 128 || !this.worldObj.isAir(i18, k23, l20) || !this.worldObj.getMaterial(i18, k23 - 1, l20).blocksMovement() || this.worldObj.getMaterial(i18, k23 - 1, l20) == Material.ICE)
                    continue;
                this.worldObj.setTile(i18, k23, l20, Tile.SNOW.id);
            }
        }
        SandTile.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean flag, ProgressListener listener) {
        return true;
    }

    @Override
    public boolean method_1801() {
        return false;
    }

    @Override
    public boolean isClean() {
        return true;
    }

    @Override
    public String toString() {
        return "RandomLevelSource";
    }
}
