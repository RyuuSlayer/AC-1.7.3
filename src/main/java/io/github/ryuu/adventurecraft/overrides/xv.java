package io.github.ryuu.adventurecraft.overrides;

import java.util.Random;

public class xv {
    private ug e;

    private ug f;

    private ug g;

    public double[] a;

    public double[] b;

    public double[] c;

    public kd[] d;

    private fd worldObj;

    protected xv() {}

    public xv(fd world) {
        this.e = new ug(new Random(world.s() * 9871L), 4);
        this.f = new ug(new Random(world.s() * 39811L), 4);
        this.g = new ug(new Random(world.s() * 543321L), 2);
        this.worldObj = world;
    }

    public kd a(yy chunkcoordintpair) {
        return a(chunkcoordintpair.a << 4, chunkcoordintpair.b << 4);
    }

    public kd a(int i, int j) {
        return a(i, j, 1, 1)[0];
    }

    public double b(int i, int j) {
        this.a = this.e.a(this.a, i, j, 1, 1, 0.02500000037252903D, 0.02500000037252903D, 0.5D);
        return this.a[0];
    }

    public kd[] a(int i, int j, int k, int l) {
        this.d = a(this.d, i, j, k, l);
        return this.d;
    }

    public double[] a(double[] ad, int i, int j, int k, int l) {
        if (ad == null || ad.length < k * l)
            ad = new double[k * l];
        if (!this.worldObj.x.useImages) {
            ad = this.e.a(ad, i, j, k, l, 0.02500000037252903D, 0.02500000037252903D, 0.25D);
            this.c = this.g.a(this.c, i, j, k, l, 0.25D, 0.25D, 0.5882352941176471D);
        }
        int i1 = 0;
        for (int j1 = 0; j1 < k; j1++) {
            for (int k1 = 0; k1 < l; k1++) {
                if (this.worldObj.x.useImages) {
                    int x = i + j1;
                    int y = j + k1;
                    ad[i1] = TerrainImage.getTerrainTemperature(x, y);
                } else {
                    double d = this.c[i1] * 1.1D + 0.5D;
                    double d1 = 0.01D;
                    double d2 = 1.0D - d1;
                    double d3 = (ad[i1] * 0.15D + 0.7D) * d2 + d * d1;
                    d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
                    if (d3 < 0.0D)
                        d3 = 0.0D;
                    if (d3 > 1.0D)
                        d3 = 1.0D;
                    ad[i1] = d3;
                }
                i1++;
            }
        }
        return ad;
    }

    public kd[] a(kd[] amobspawnerbase, int i, int j, int k, int l) {
        if (amobspawnerbase == null || amobspawnerbase.length < k * l)
            amobspawnerbase = new kd[k * l];
        this.a = this.e.a(this.a, i, j, k, k, 0.02500000037252903D, 0.02500000037252903D, 0.25D);
        this.b = this.f.a(this.b, i, j, k, k, 0.05000000074505806D, 0.05000000074505806D, 0.3333333333333333D);
        this.c = this.g.a(this.c, i, j, k, k, 0.25D, 0.25D, 0.5882352941176471D);
        int i1 = 0;
        double d3 = 0.0D;
        double d4 = 0.0D;
        for (int j1 = 0; j1 < k; j1++) {
            for (int k1 = 0; k1 < l; k1++) {
                if (this.worldObj.x.useImages) {
                    int x = i + j1;
                    int y = j + k1;
                    d3 = TerrainImage.getTerrainTemperature(x, y);
                    d4 = TerrainImage.getTerrainHumidity(x, y);
                } else {
                    double d = this.c[i1] * 1.1D + 0.5D;
                    double d1 = 0.01D;
                    double d2 = 1.0D - d1;
                    d3 = (this.a[i1] * 0.15D + 0.7D) * d2 + d * d1;
                    d1 = 0.002D;
                    d2 = 1.0D - d1;
                    d4 = (this.b[i1] * 0.15D + 0.5D) * d2 + d * d1;
                    d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
                    if (d3 < 0.0D)
                        d3 = 0.0D;
                    if (d4 < 0.0D)
                        d4 = 0.0D;
                    if (d3 > 1.0D)
                        d3 = 1.0D;
                    if (d4 > 1.0D)
                        d4 = 1.0D;
                }
                this.a[i1] = d3;
                this.b[i1] = d4;
                amobspawnerbase[i1++] = kd.a(d3, d4);
            }
        }
        return amobspawnerbase;
    }
}
