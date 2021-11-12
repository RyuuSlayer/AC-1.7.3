package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;

import java.util.Random;

public class MixinOverworldLevelSource implements cl {
    private final Random j;

    private final uf k;

    private final uf l;

    private final uf m;

    private final uf n;

    private final uf o;
    private final Level p;
    private final fv u;
    public uf a;
    public uf b;
    public uf c;
    public double mapSize;
    public int waterLevel;
    public double fractureHorizontal;
    public double fractureVertical;
    public double maxAvgDepth;
    public double maxAvgHeight;
    public double volatility1;
    public double volatility2;
    public double volatilityWeight1;
    public double volatilityWeight2;
    double[] d;
    double[] e;
    double[] f;
    double[] g;
    double[] h;
    int[][] i;
    private double[] q;
    private double[] r;
    private double[] s;
    private double[] t;
    private kd[] v;
    private double[] w;

    public MixinOverworldLevelSource(Level world, long l) {
        this.mapSize = 250.0D;
        this.waterLevel = 64;
        this.fractureHorizontal = 1.0D;
        this.fractureVertical = 1.0D;
        this.maxAvgDepth = 0.0D;
        this.maxAvgHeight = 0.0D;
        this.volatility1 = 1.0D;
        this.volatility2 = 1.0D;
        this.volatilityWeight1 = 0.0D;
        this.volatilityWeight2 = 1.0D;
        this.r = new double[256];
        this.s = new double[256];
        this.t = new double[256];
        this.u = (fv) new sq();
        this.i = new int[32][32];
        this.p = world;
        this.j = new Random(l);
        this.k = new uf(this.j, 16);
        this.l = new uf(this.j, 16);
        this.m = new uf(this.j, 8);
        this.n = new uf(this.j, 4);
        this.o = new uf(this.j, 4);
        this.a = new uf(this.j, 10);
        this.b = new uf(this.j, 16);
        this.c = new uf(this.j, 8);
    }

    public void a(int chunkX, int chunkZ, byte[] abyte0, kd[] amobspawnerbase, double[] ad) {
        byte byte0 = 4;
        int k = byte0 + 1;
        byte byte2 = 17;
        int l = byte0 + 1;
        this.q = a(this.q, chunkX * byte0, 0, chunkZ * byte0, k, byte2, l);
        for (int xOuter = 0; xOuter < byte0; xOuter++) {
            for (int zOuter = 0; zOuter < byte0; zOuter++) {
                for (int yOuter = 0; yOuter < 16; yOuter++) {
                    double d = 0.125D;
                    double d1 = this.q[((xOuter + 0) * l + zOuter + 0) * byte2 + yOuter + 0];
                    double d2 = this.q[((xOuter + 0) * l + zOuter + 1) * byte2 + yOuter + 0];
                    double d3 = this.q[((xOuter + 1) * l + zOuter + 0) * byte2 + yOuter + 0];
                    double d4 = this.q[((xOuter + 1) * l + zOuter + 1) * byte2 + yOuter + 0];
                    double d5 = (this.q[((xOuter + 0) * l + zOuter + 0) * byte2 + yOuter + 1] - d1) * d;
                    double d6 = (this.q[((xOuter + 0) * l + zOuter + 1) * byte2 + yOuter + 1] - d2) * d;
                    double d7 = (this.q[((xOuter + 1) * l + zOuter + 0) * byte2 + yOuter + 1] - d3) * d;
                    double d8 = (this.q[((xOuter + 1) * l + zOuter + 1) * byte2 + yOuter + 1] - d4) * d;
                    for (int yInner = 0; yInner < 8; yInner++) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int xInner = 0; xInner < 4; xInner++) {
                            int j2 = xInner + xOuter * 4 << 11 | 0 + zOuter * 4 << 7 | yOuter * 8 + yInner;
                            char c = ';
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for (int zInner = 0; zInner < 4; zInner++) {
                                int x = Math.abs(chunkX * 16 + xOuter * 4 + xInner);
                                int y = Math.abs(chunkZ * 16 + zOuter * 4 + zInner);
                                double reduceBy = Math.max(Math.sqrt((x * x + y * y)) - this.mapSize, 0.0D) / 2.0D;
                                double d17 = ad[(xOuter * 4 + xInner) * 16 + zOuter * 4 + zInner];
                                int l2 = 0;
                                if (yOuter * 8 + yInner < this.waterLevel)
                                    if (d17 < 0.5D && yOuter * 8 + yInner >= this.waterLevel - 1) {
                                        l2 = uu.aU.bn;
                                    } else {
                                        l2 = uu.C.bn;
                                    }
                                if (d15 - reduceBy > 0.0D)
                                    l2 = uu.u.bn;
                                abyte0[j2] = (byte) l2;
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

    public void a(int i, int j, byte[] abyte0, kd[] abiomegenbase) {
        double d = 0.03125D;
        this.r = this.n.a(this.r, (i * 16), (j * 16), 0.0D, 16, 16, 1, d, d, 1.0D);
        this.s = this.n.a(this.s, (i * 16), 109.0134D, (j * 16), 16, 1, 16, d, 1.0D, d);
        this.t = this.o.a(this.t, (i * 16), (j * 16), 0.0D, 16, 16, 1, d * 2.0D, d * 2.0D, d * 2.0D);
        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                kd biomegenbase = abiomegenbase[k + l * 16];
                boolean flag = (this.r[k + l * 16] + this.j.nextDouble() * 0.2D > 0.0D);
                boolean flag1 = (this.s[k + l * 16] + this.j.nextDouble() * 0.2D > 3.0D);
                int i1 = (int) (this.t[k + l * 16] / 3.0D + 3.0D + this.j.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte1 = biomegenbase.p;
                byte byte2 = biomegenbase.q;
                for (int k1 = 127; k1 >= 0; k1--) {
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte3 = abyte0[l1];
                    if (byte3 == 0) {
                        j1 = -1;
                    } else if (byte3 == uu.u.bn) {
                        if (j1 == -1) {
                            if (i1 <= 0) {
                                byte1 = 0;
                                byte2 = (byte) uu.u.bn;
                            } else if (k1 >= this.waterLevel - 4 && k1 <= this.waterLevel + 1) {
                                byte1 = biomegenbase.p;
                                byte2 = biomegenbase.q;
                                if (flag1)
                                    byte1 = 0;
                                if (flag1)
                                    byte2 = (byte) uu.G.bn;
                                if (flag)
                                    byte1 = (byte) uu.F.bn;
                                if (flag)
                                    byte2 = (byte) uu.F.bn;
                            }
                            if (k1 < this.waterLevel && byte1 == 0)
                                byte1 = (byte) uu.C.bn;
                            j1 = i1;
                            if (k1 >= this.waterLevel - 1) {
                                abyte0[l1] = byte1;
                            } else {
                                abyte0[l1] = byte2;
                            }
                        } else if (j1 > 0) {
                            j1--;
                            abyte0[l1] = byte2;
                            if (j1 == 0 && byte2 == uu.F.bn) {
                                j1 = this.j.nextInt(4);
                                byte2 = (byte) uu.R.bn;
                            }
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
        this.j.setSeed(i * 341873128712L + j * 132897987541L);
        byte[] abyte0 = new byte[32768];
        Chunk chunk = new Chunk(this.p, abyte0, i, j);
        this.v = this.p.a().a(this.v, i * 16, j * 16, 16, 16);
        double[] ad = (this.p.a()).a;
        a(i, j, abyte0, this.v, ad);
        a(i, j, abyte0, this.v);
        this.u.a(this, this.p, i, j, abyte0);
        chunk.c();
        chunk.o = false;
        return chunk;
    }

    private double[] a(double[] ad, int i, int j, int k, int sizeX, int sizeY, int sizeZ) {
        if (ad == null)
            ad = new double[sizeX * sizeY * sizeZ];
        double d = 684.412D * this.fractureHorizontal;
        double d1 = 684.412D * this.fractureVertical;
        double[] ad1 = (this.p.a()).a;
        double[] ad2 = (this.p.a()).b;
        this.g = this.a.a(this.g, i, k, sizeX, sizeZ, 1.121D, 1.121D, 0.5D);
        this.h = this.b.a(this.h, i, k, sizeX, sizeZ, 200.0D, 200.0D, 0.5D);
        this.d = this.m.a(this.d, i, j, k, sizeX, sizeY, sizeZ, d / 80.0D, d1 / 160.0D, d / 80.0D);
        this.e = this.k.a(this.e, i, j, k, sizeX, sizeY, sizeZ, d, d1, d);
        this.f = this.l.a(this.f, i, j, k, sizeX, sizeY, sizeZ, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / sizeX;
        for (int j2 = 0; j2 < sizeX; j2++) {
            int k2 = j2 * i2 + i2 / 2;
            for (int l2 = 0; l2 < sizeZ; l2++) {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (this.g[l1] + 256.0D) / 512.0D;
                d5 *= d4;
                if (d5 > 1.0D)
                    d5 = 1.0D;
                double d6 = this.h[l1] / 8000.0D;
                if (d6 < 0.0D)
                    d6 = -d6 * 0.3D;
                d6 = d6 * 3.0D - 2.0D;
                if (d6 < 0.0D) {
                    d6 /= 2.0D;
                    if (d6 < -1.0D)
                        d6 = -1.0D;
                    d6 -= this.maxAvgDepth;
                    d6 /= 1.4D;
                    d6 /= 2.0D;
                    d5 = 0.0D;
                } else {
                    if (d6 > 1.0D)
                        d6 = 1.0D;
                    d6 += this.maxAvgHeight;
                    d6 /= 8.0D;
                }
                if (d5 < 0.0D)
                    d5 = 0.0D;
                d5 += 0.5D;
                d6 = d6 * sizeY / 16.0D;
                double d7 = sizeY / 2.0D + d6 * 4.0D;
                l1++;
                for (int j3 = 0; j3 < sizeY; j3++) {
                    double d8 = 0.0D;
                    double d9 = (j3 - d7) * 12.0D / d5;
                    if (d9 < 0.0D)
                        d9 *= 4.0D;
                    double d10 = this.e[k1] / 512.0D * this.volatility1;
                    double d11 = this.f[k1] / 512.0D * this.volatility2;
                    double d12 = (this.d[k1] / 10.0D + 1.0D) / 2.0D;
                    if (d12 < this.volatilityWeight1) {
                        d8 = d10;
                    } else if (d12 > this.volatilityWeight2) {
                        d8 = d11;
                    } else {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= d9;
                    if (j3 > sizeY - 4) {
                        double d13 = ((j3 - sizeY - 4) / 3.0F);
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

    public void a(cl ichunkprovider, int i, int j) {
        gk.a = true;
        int k = i * 16;
        int l = j * 16;
        kd biomegenbase = this.p.a().a(k + 16, l + 16);
        this.j.setSeed(this.p.s());
        long l1 = this.j.nextLong() / 2L * 2L + 1L;
        long l2 = this.j.nextLong() / 2L * 2L + 1L;
        this.j.setSeed(i * l1 + j * l2 ^ this.p.s());
        double d = 0.25D;
        if (this.j.nextInt(4) == 0) {
            int i1 = k + this.j.nextInt(16) + 8;
            int l4 = this.j.nextInt(128);
            int i8 = l + this.j.nextInt(16) + 8;
            (new dj(uu.C.bn)).a(this.p, this.j, i1, l4, i8);
        }
        if (this.j.nextInt(8) == 0) {
            int j1 = k + this.j.nextInt(16) + 8;
            int i5 = this.j.nextInt(this.j.nextInt(120) + 8);
            int j8 = l + this.j.nextInt(16) + 8;
            if (i5 < 64 || this.j.nextInt(10) == 0)
                (new dj(uu.E.bn)).a(this.p, this.j, j1, i5, j8);
        }
        for (int k1 = 0; k1 < 8; k1++) {
            int j5 = k + this.j.nextInt(16) + 8;
            int k8 = this.j.nextInt(128);
            int j11 = l + this.j.nextInt(16) + 8;
            (new er()).a(this.p, this.j, j5, k8, j11);
        }
        for (int i2 = 0; i2 < 10; i2++) {
            int k5 = k + this.j.nextInt(16);
            int l8 = this.j.nextInt(128);
            int k11 = l + this.j.nextInt(16);
            (new ms(32)).a(this.p, this.j, k5, l8, k11);
        }
        for (int j2 = 0; j2 < 20; j2++) {
            int l5 = k + this.j.nextInt(16);
            int i9 = this.j.nextInt(128);
            int l11 = l + this.j.nextInt(16);
            (new fl(uu.w.bn, 32)).a(this.p, this.j, l5, i9, l11);
        }
        for (int k2 = 0; k2 < 10; k2++) {
            int i6 = k + this.j.nextInt(16);
            int j9 = this.j.nextInt(128);
            int i12 = l + this.j.nextInt(16);
            (new fl(uu.G.bn, 32)).a(this.p, this.j, i6, j9, i12);
        }
        for (int i3 = 0; i3 < 20; i3++) {
            int j6 = k + this.j.nextInt(16);
            int k9 = this.j.nextInt(128);
            int j12 = l + this.j.nextInt(16);
            (new fl(uu.J.bn, 16)).a(this.p, this.j, j6, k9, j12);
        }
        for (int j3 = 0; j3 < 20; j3++) {
            int k6 = k + this.j.nextInt(16);
            int l9 = this.j.nextInt(64);
            int k12 = l + this.j.nextInt(16);
            (new fl(uu.I.bn, 8)).a(this.p, this.j, k6, l9, k12);
        }
        for (int k3 = 0; k3 < 2; k3++) {
            int l6 = k + this.j.nextInt(16);
            int i10 = this.j.nextInt(32);
            int l12 = l + this.j.nextInt(16);
            (new fl(uu.H.bn, 8)).a(this.p, this.j, l6, i10, l12);
        }
        for (int l3 = 0; l3 < 8; l3++) {
            int i7 = k + this.j.nextInt(16);
            int j10 = this.j.nextInt(16);
            int i13 = l + this.j.nextInt(16);
            (new fl(uu.aO.bn, 7)).a(this.p, this.j, i7, j10, i13);
        }
        for (int i4 = 0; i4 < 1; i4++) {
            int j7 = k + this.j.nextInt(16);
            int k10 = this.j.nextInt(16);
            int j13 = l + this.j.nextInt(16);
            (new fl(uu.ax.bn, 7)).a(this.p, this.j, j7, k10, j13);
        }
        for (int j4 = 0; j4 < 1; j4++) {
            int k7 = k + this.j.nextInt(16);
            int l10 = this.j.nextInt(16) + this.j.nextInt(16);
            int k13 = l + this.j.nextInt(16);
            (new fl(uu.O.bn, 6)).a(this.p, this.j, k7, l10, k13);
        }
        d = 0.5D;
        int k4 = (int) ((this.c.a(k * d, l * d) / 8.0D + this.j.nextDouble() * 4.0D + 4.0D) / 3.0D);
        int l7 = 0;
        if (this.j.nextInt(10) == 0)
            l7++;
        if (biomegenbase == kd.d)
            l7 += k4 + 5;
        if (biomegenbase == kd.a)
            l7 += k4 + 5;
        if (biomegenbase == kd.c)
            l7 += k4 + 2;
        if (biomegenbase == kd.g)
            l7 += k4 + 5;
        if (biomegenbase == kd.h)
            l7 -= 20;
        if (biomegenbase == kd.k)
            l7 -= 20;
        if (biomegenbase == kd.i)
            l7 -= 20;
        for (int i11 = 0; i11 < l7; i11++) {
            int l13 = k + this.j.nextInt(16) + 8;
            int j14 = l + this.j.nextInt(16) + 8;
            pg worldgenerator = biomegenbase.a(this.j);
            worldgenerator.a(1.0D, 1.0D, 1.0D);
            worldgenerator.a(this.p, this.j, l13, this.p.d(l13, j14), j14);
        }
        byte byte0 = 0;
        if (biomegenbase == kd.d)
            byte0 = 2;
        if (biomegenbase == kd.c)
            byte0 = 4;
        if (biomegenbase == kd.g)
            byte0 = 2;
        if (biomegenbase == kd.i)
            byte0 = 3;
        for (int i14 = 0; i14 < byte0; i14++) {
            int k14 = k + this.j.nextInt(16) + 8;
            int l16 = this.j.nextInt(128);
            int k19 = l + this.j.nextInt(16) + 8;
            (new be(uu.ae.bn)).a(this.p, this.j, k14, l16, k19);
        }
        byte byte1 = 0;
        if (biomegenbase == kd.d)
            byte1 = 2;
        if (biomegenbase == kd.a)
            byte1 = 10;
        if (biomegenbase == kd.c)
            byte1 = 2;
        if (biomegenbase == kd.g)
            byte1 = 1;
        if (biomegenbase == kd.i)
            byte1 = 10;
        for (int l14 = 0; l14 < byte1; l14++) {
            byte byte2 = 1;
            if (biomegenbase == kd.a && this.j.nextInt(3) != 0)
                byte2 = 2;
            int l19 = k + this.j.nextInt(16) + 8;
            int k22 = this.j.nextInt(128);
            int j24 = l + this.j.nextInt(16) + 8;
            (new ap(uu.Y.bn, byte2)).a(this.p, this.j, l19, k22, j24);
        }
        byte1 = 0;
        if (biomegenbase == kd.h)
            byte1 = 2;
        for (int i15 = 0; i15 < byte1; i15++) {
            int i17 = k + this.j.nextInt(16) + 8;
            int i20 = this.j.nextInt(128);
            int l22 = l + this.j.nextInt(16) + 8;
            (new kt(uu.Z.bn)).a(this.p, this.j, i17, i20, l22);
        }
        if (this.j.nextInt(2) == 0) {
            int j15 = k + this.j.nextInt(16) + 8;
            int j17 = this.j.nextInt(128);
            int j20 = l + this.j.nextInt(16) + 8;
            (new be(uu.af.bn)).a(this.p, this.j, j15, j17, j20);
        }
        if (this.j.nextInt(4) == 0) {
            int k15 = k + this.j.nextInt(16) + 8;
            int k17 = this.j.nextInt(128);
            int k20 = l + this.j.nextInt(16) + 8;
            (new be(uu.ag.bn)).a(this.p, this.j, k15, k17, k20);
        }
        if (this.j.nextInt(8) == 0) {
            int l15 = k + this.j.nextInt(16) + 8;
            int l17 = this.j.nextInt(128);
            int l20 = l + this.j.nextInt(16) + 8;
            (new be(uu.ah.bn)).a(this.p, this.j, l15, l17, l20);
        }
        for (int i16 = 0; i16 < 10; i16++) {
            int i18 = k + this.j.nextInt(16) + 8;
            int i21 = this.j.nextInt(128);
            int i23 = l + this.j.nextInt(16) + 8;
            (new ir()).a(this.p, this.j, i18, i21, i23);
        }
        if (this.j.nextInt(32) == 0) {
            int j16 = k + this.j.nextInt(16) + 8;
            int j18 = this.j.nextInt(128);
            int j21 = l + this.j.nextInt(16) + 8;
            (new wx()).a(this.p, this.j, j16, j18, j21);
        }
        int k16 = 0;
        if (biomegenbase == kd.h)
            k16 += 10;
        for (int k18 = 0; k18 < k16; k18++) {
            int k21 = k + this.j.nextInt(16) + 8;
            int j23 = this.j.nextInt(128);
            int k24 = l + this.j.nextInt(16) + 8;
            (new fx()).a(this.p, this.j, k21, j23, k24);
        }
        for (int l18 = 0; l18 < 50; l18++) {
            int l21 = k + this.j.nextInt(16) + 8;
            int k23 = this.j.nextInt(this.j.nextInt(120) + 8);
            int l24 = l + this.j.nextInt(16) + 8;
            (new xo(uu.B.bn)).a(this.p, this.j, l21, k23, l24);
        }
        for (int i19 = 0; i19 < 20; i19++) {
            int i22 = k + this.j.nextInt(16) + 8;
            int l23 = this.j.nextInt(this.j.nextInt(this.j.nextInt(112) + 8) + 8);
            int i25 = l + this.j.nextInt(16) + 8;
            (new xo(uu.D.bn)).a(this.p, this.j, i22, l23, i25);
        }
        this.w = this.p.a().a(this.w, k + 8, l + 8, 16, 16);
        for (int j19 = k + 8; j19 < k + 8 + 16; j19++) {
            for (int j22 = l + 8; j22 < l + 8 + 16; j22++) {
                int i24 = j19 - k + 8;
                int j25 = j22 - l + 8;
                int k25 = this.p.e(j19, j22);
                double d1 = this.w[i24 * 16 + j25] - (k25 - 64) / 64.0D * 0.3D;
                if (d1 < 0.5D && k25 > 0 && k25 < 128 && this.p.d(j19, k25, j22) && this.p.f(j19, k25 - 1, j22).c() && this.p.f(j19, k25 - 1, j22) != ln.s)
                    this.p.f(j19, k25, j22, uu.aT.bn);
            }
        }
        gk.a = false;
    }

    public boolean a(boolean flag, yb iprogressupdate) {
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
