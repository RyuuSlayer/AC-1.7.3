package io.github.ryuu.adventurecraft.overrides;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Biome {
    public byte p = (byte) Tile.v.bn;

    public byte q = (byte) Tile.w.bn;

    public int r = 5169201;

    protected List s = new ArrayList();

    protected List t = new ArrayList();

    protected List u = new ArrayList();

    private boolean w = true;

    private Biome e() {
        this.w = false;
        return this;
    }

    public static void a() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++)
                x[i + j * 64] = a(i / 63.0F, j / 63.0F);
        }
        h.p = h.q = (byte) Tile.F.bn;
        Biome.j.p = Biome.j.q = (byte) Tile.F.bn;
    }

    public pg a(Random random) {
        if (random.nextInt(10) == 0)
            return (pg)new ih();
        return (pg)new yh();
    }

    protected Biome b() {
        this.v = true;
        return this;
    }

    protected Biome a(String s) {
        this.n = s;
        return this;
    }

    protected Biome a(int i) {
        this.r = i;
        return this;
    }

    protected Biome b(int i) {
        this.o = i;
        return this;
    }

    public static Biome a(double d, double d1) {
        int i = (int)(d * 63.0D);
        int j = (int)(d1 * 63.0D);
        return x[i + j * 64];
    }

    public static Biome a(float f, float f1) {
        f1 *= f;
        if (f < 0.1F)
            return k;
        if (f1 < 0.2F) {
            if (f < 0.5F)
                return k;
            if (f < 0.95F)
                return e;
            return h;
        }
        if (f1 > 0.5F && f < 0.7F)
            return b;
        if (f < 0.5F)
            return g;
        if (f < 0.97F) {
            if (f1 < 0.35F)
                return Biome.f;
            return d;
        }
        if (f1 < 0.45F)
            return i;
        if (f1 < 0.9F)
            return c;
        return a;
    }

    public int a(float f) {
        f /= 3.0F;
        if (f < -1.0F)
            f = -1.0F;
        if (f > 1.0F)
            f = 1.0F;
        return Color.getHSBColor(0.6222222F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
    }

    public List a(lk enumcreaturetype) {
        if (enumcreaturetype == lk.a)
            return this.s;
        if (enumcreaturetype == lk.b)
            return this.t;
        if (enumcreaturetype == lk.c)
            return this.u;
        return null;
    }

    public boolean c() {
        return this.v;
    }

    public boolean d() {
        if (this.v)
            return false;
        return this.w;
    }

    public static final Biome a = (new yj()).b(588342).a("Rainforest").a(2094168);

    public static final Biome b = (new tf()).b(522674).a("Swampland").a(9154376);

    public static final Biome c = (new Biome()).b(10215459).a("Seasonal Forest");

    public static final Biome d = (new rb()).b(353825).a("Forest").a(5159473);

    public static final Biome e = (new fs()).b(14278691).a("Savanna");

    public static final Biome f = (new Biome()).b(10595616).a("Shrubland");

    public static final Biome g = (new g()).b(3060051).a("Taiga").b().a(8107825);

    public static final Biome h = (new fs()).b(16421912).a("Desert").e();

    public static final Biome i = (new fs()).b(16767248).a("Plains");

    public static final Biome j = (new fs()).b(16772499).a("Ice Desert").b().e().a(12899129);

    public static final Biome k = (new Biome()).b(5762041).a("Tundra").b().a(12899129);

    public static final Biome l = (new t()).b(16711680).a("Hell").e();

    public static final Biome m = (new ry()).b(8421631).a("Sky").e();

    private static Biome[] x = new Biome[4096];

    public String n;

    public int o;

    private boolean v;

    static {
        a();
    }
}
