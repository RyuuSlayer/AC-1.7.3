package io.github.ryuu.adventurecraft.util;

import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.structure.CactusPatch;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.MushroomPatch;
import net.minecraft.tile.Tile;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.noise.PerlinOctaveNoise;

import java.util.Random;

public class ChunkProviderHeightMapGenerate implements LevelSource {
    private final Random rand;

    private final PerlinOctaveNoise field_912_k;

    private final PerlinOctaveNoise field_911_l;

    private final PerlinOctaveNoise field_910_m;

    private final PerlinOctaveNoise field_908_o;
    private final Level worldObj;
    public PerlinOctaveNoise field_922_a;
    public PerlinOctaveNoise field_921_b;
    public PerlinOctaveNoise mobSpawnerNoise;
    double[] field_4185_d;
    double[] field_4184_e;
    double[] field_4183_f;
    double[] field_4182_g;
    double[] field_4181_h;
    int[][] unusedIntArray32x32;
    private double[] stoneNoise;
    private Biome[] biomesForGeneration;
    private double[] generatedTemperatures;

    public ChunkProviderHeightMapGenerate(Level world, long l) {
        this.stoneNoise = new double[256];
        this.unusedIntArray32x32 = new int[32][32];
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
        byte byte0 = 4;
        for (int i1 = 0; i1 < byte0; i1++) {
            for (int j1 = 0; j1 < byte0; j1++) {
                for (int k1 = 0; k1 < 16; k1++) {
                    for (int l1 = 0; l1 < 8; l1++) {
                        for (int i2 = 0; i2 < 4; i2++) {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            char c = ';
                            for (int k2 = 0; k2 < 4; k2++) {
                                int x = i * 16 + i1 * 4 + i2;
                                int y = j * 16 + j1 * 4 + k2;
                                double d17 = ad[(i1 * 4 + i2) * 16 + j1 * 4 + k2];
                                int l2 = 0;
                                int waterHeight = TerrainImage.getWaterHeight(x, y);
                                if (k1 * 8 + l1 < waterHeight)
                                    if (d17 < 0.5D && k1 * 8 + l1 >= waterHeight - 1) {
                                        l2 = Tile.aU.bn;
                                    } else {
                                        l2 = Tile.B.bn;
                                    }
                                int height = TerrainImage.getTerrainHeight(x, y);
                                if (k1 * 8 + l1 <= height)
                                    l2 = Tile.u.bn;
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
        double d = 0.03125D;
        this.stoneNoise = this.field_908_o.a(this.stoneNoise, (i * 16), (j * 16), 0.0D, 16, 16, 1, d * 2.0D, d * 2.0D, d * 2.0D);
        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                Biome mobspawnerbase = amobspawnerbase[k + l * 16];
                int i1 = (int) (this.stoneNoise[k + l * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte1 = mobspawnerbase.p;
                byte byte2 = mobspawnerbase.q;
                for (int k1 = 127; k1 >= 0; k1--) {
                    int x = k + (15 - i) * 16;
                    int z = l + (15 - j) * 16;
                    int waterHeight = TerrainImage.getWaterHeight(x, z);
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte3 = abyte0[l1];
                    if (byte3 == 0) {
                        j1 = -1;
                    } else if (byte3 == Tile.u.bn) {
                        if (j1 == -1) {
                            if (k1 >= waterHeight - 4 && k1 <= waterHeight + 1) {
                                byte1 = mobspawnerbase.p;
                                byte2 = mobspawnerbase.q;
                                if (TerrainImage.hasSandNearWaterEdge(x, z)) {
                                    byte1 = (byte) Tile.F.bn;
                                    byte2 = (byte) Tile.F.bn;
                                }
                            }
                            if (k1 < waterHeight && byte1 == 0)
                                byte1 = (byte) Tile.FLOWING_WATER.id;
                            j1 = i1;
                            if (k1 >= waterHeight - 1) {
                                abyte0[l1] = byte1;
                            } else {
                                abyte0[l1] = byte2;
                            }
                        } else if (j1 > 0) {
                            j1--;
                            abyte0[l1] = byte2;
                        }
                    }
                }
            }
        }
    }

    public Chunk c(int i, int j) {
        return b(i, j);
    }

    public Chunk b(int i, int j) {
        this.rand.setSeed(i * 341873128712L + j * 132897987541L);
        byte[] abyte0 = new byte[32768];
        Chunk chunk = new Chunk(this.worldObj, abyte0, i, j);
        this.biomesForGeneration = this.worldObj.getBiomeSource().getBiomes(this.biomesForGeneration, i * 16, j * 16, 16, 16);
        double[] ad = (this.worldObj.a()).a;
        generateTerrain(i, j, abyte0, this.biomesForGeneration, ad);
        replaceBlocksForBiome(i, j, abyte0, this.biomesForGeneration);
        chunk.c();
        chunk.o = false;
        return chunk;
    }

    private double[] func_4061_a(double[] ad, int i, int j, int k, int l, int i1, int j1) {
        if (ad == null)
            ad = new double[l * i1 * j1];
        double d = 684.412D;
        double d1 = 684.412D;
        double[] ad1 = (this.worldObj.a()).a;
        double[] ad2 = (this.worldObj.a()).b;
        this.field_4182_g = this.field_922_a.a(this.field_4182_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        this.field_4181_h = this.field_921_b.a(this.field_4181_h, i, k, l, j1, 200.0D, 200.0D, 0.5D);
        this.field_4185_d = this.field_910_m.a(this.field_4185_d, i, j, k, l, i1, j1, d / 80.0D, d1 / 160.0D, d / 80.0D);
        this.field_4184_e = this.field_912_k.a(this.field_4184_e, i, j, k, l, i1, j1, d, d1, d);
        this.field_4183_f = this.field_911_l.a(this.field_4183_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;
        for (int j2 = 0; j2 < l; j2++) {
            int k2 = j2 * i2 + i2 / 2;
            for (int l2 = 0; l2 < j1; l2++) {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (this.field_4182_g[l1] + 256.0D) / 512.0D;
                d5 *= d4;
                if (d5 > 1.0D)
                    d5 = 1.0D;
                double d6 = this.field_4181_h[l1] / 8000.0D;
                if (d6 < 0.0D)
                    d6 = -d6 * 0.3D;
                d6 = d6 * 3.0D - 2.0D;
                if (d6 < 0.0D) {
                    d6 /= 2.0D;
                    if (d6 < -1.0D)
                        d6 = -1.0D;
                    d6 /= 1.4D;
                    d6 /= 2.0D;
                    d5 = 0.0D;
                } else {
                    if (d6 > 1.0D)
                        d6 = 1.0D;
                    d6 /= 8.0D;
                }
                if (d5 < 0.0D)
                    d5 = 0.0D;
                d5 += 0.5D;
                d6 = d6 * i1 / 16.0D;
                double d7 = i1 / 2.0D + d6 * 4.0D;
                l1++;
                for (int j3 = 0; j3 < i1; j3++) {
                    double d8 = 0.0D;
                    double d9 = (j3 - d7) * 12.0D / d5;
                    if (d9 < 0.0D)
                        d9 *= 4.0D;
                    double d10 = this.field_4184_e[k1] / 512.0D;
                    double d11 = this.field_4183_f[k1] / 512.0D;
                    double d12 = (this.field_4185_d[k1] / 10.0D + 1.0D) / 2.0D;
                    if (d12 < 0.0D) {
                        d8 = d10;
                    } else if (d12 > 1.0D) {
                        d8 = d11;
                    } else {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= d9;
                    if (j3 > i1 - 4) {
                        double d13 = ((j3 - i1 - 4) / 3.0F);
                        d8 = d8 * (1.0D - d13) + -10.0D * d13;
                    }
                    ad[k1] = d8;
                    k1++;
                }
            }
        }
        return ad;
    }

    public boolean a(int i, int j) {
        return true;
    }

    public void a(LevelSource ichunkprovider, int i, int j) {
        gk.a = true;
        int k = i * 16;
        int l = j * 16;
        Biome mobspawnerbase = this.worldObj.a().a(k + 16, l + 16);
        this.rand.setSeed(this.worldObj.s());
        long l1 = this.rand.nextLong() / 2L * 2L + 1L;
        long l2 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(i * l1 + j * l2 ^ this.worldObj.s());
        double d = 0.25D;
        d = 0.5D;
        int k4 = (int) ((this.mobSpawnerNoise.a(k * d, l * d) / 8.0D + this.rand.nextDouble() * 4.0D + 4.0D) / 3.0D);
        int l7 = 0;
        if (this.rand.nextInt(10) == 0)
            l7++;
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
        for (int i11 = 0; i11 < l7; i11++) {
            int k15 = k + this.rand.nextInt(16) + 8;
            int j18 = l + this.rand.nextInt(16) + 8;
            Feature worldgenerator = mobspawnerbase.a(this.rand);
            worldgenerator.a(1.0D, 1.0D, 1.0D);
            worldgenerator.a(this.worldObj, this.rand, k15, this.worldObj.d(k15, j18), j18);
        }
        for (int j11 = 0; j11 < 2; j11++) {
            int l15 = k + this.rand.nextInt(16) + 8;
            int k18 = this.rand.nextInt(128);
            int i21 = l + this.rand.nextInt(16) + 8;
            (new MushroomPatch(Tile.DANDELION.id)).a(this.worldObj, this.rand, l15, k18, i21);
        }
        if (this.rand.nextInt(2) == 0) {
            int k11 = k + this.rand.nextInt(16) + 8;
            int i16 = this.rand.nextInt(128);
            int l18 = l + this.rand.nextInt(16) + 8;
            (new MushroomPatch(Tile.ROSE.id)).a(this.worldObj, this.rand, k11, i16, l18);
        }
        if (this.rand.nextInt(4) == 0) {
            int l11 = k + this.rand.nextInt(16) + 8;
            int j16 = this.rand.nextInt(128);
            int i19 = l + this.rand.nextInt(16) + 8;
            (new MushroomPatch(Tile.BROWN_MUSHROOM.id)).a(this.worldObj, this.rand, l11, j16, i19);
        }
        if (this.rand.nextInt(8) == 0) {
            int i12 = k + this.rand.nextInt(16) + 8;
            int k16 = this.rand.nextInt(128);
            int j19 = l + this.rand.nextInt(16) + 8;
            (new MushroomPatch(Tile.RED_MUSHROOM.id)).a(this.worldObj, this.rand, i12, k16, j19);
        }
        int l12 = 0;
        if (mobspawnerbase == Biome.DESERT)
            l12 += 10;
        for (int j17 = 0; j17 < l12; j17++) {
            int i20 = k + this.rand.nextInt(16) + 8;
            int k21 = this.rand.nextInt(128);
            int k22 = l + this.rand.nextInt(16) + 8;
            (new CactusPatch()).a(this.worldObj, this.rand, i20, k21, k22);
        }
        this.generatedTemperatures = this.worldObj.a().a(this.generatedTemperatures, k + 8, l + 8, 16, 16);
        for (int i18 = k + 8; i18 < k + 8 + 16; i18++) {
            for (int l20 = l + 8; l20 < l + 8 + 16; l20++) {
                int j22 = i18 - k + 8;
                int j23 = l20 - l + 8;
                int k23 = this.worldObj.e(i18, l20);
                double d1 = this.generatedTemperatures[j22 * 16 + j23];
                this.worldObj.setTemperatureValue(i18, l20, d1);
                if (d1 < 0.5D && k23 > 0 && k23 < 128 && this.worldObj.d(i18, k23, l20) && this.worldObj.f(i18, k23 - 1, l20).c() && this.worldObj.f(i18, k23 - 1, l20) != ln.s)
                    this.worldObj.f(i18, k23, l20, Tile.SNOW.id);
            }
        }
        gk.a = false;
    }

    public boolean a(boolean flag, ProgressListener iprogressupdate) {
        return true;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return true;
    }

    public String c() {
        return "RandomLevelSource";
    }
}
