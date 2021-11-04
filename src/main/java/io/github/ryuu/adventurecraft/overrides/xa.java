package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.util.ChunkProviderHeightMapGenerate;
import net.minecraft.level.Level;

public abstract class xa {
    public Level a;

    public xv b;

    public boolean c = false;

    public boolean d = false;

    public boolean e = false;

    public float[] f = new float[16];

    public int g = 0;

    private final float[] h = new float[4];

    public final void a(Level world) {
        this.a = world;
        a();
        e();
    }

    protected void e() {
        float f = 0.05F;
        for (int i = 0; i <= 15; i++) {
            float f1 = 1.0F - i / 15.0F;
            this.f[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }

    protected void a() {
        this.b = new xv(this.a);
    }

    public cl b() {
        if (this.a.x.useImages)
            return (cl) new ChunkProviderHeightMapGenerate(this.a, this.a.s());
        yf c = new yf(this.a, this.a.s());
        LevelProperties w = this.a.x;
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

    public boolean a(int i, int j) {
        int k = this.a.a(i, j);
        return (k != 0 && uu.m[k] != null && !(uu.m[k] instanceof rp));
    }

    public float a(long l, float f) {
        int i = (int) (l % 24000L);
        float f1 = (i + f) / 24000.0F - 0.25F;
        if (f1 < 0.0F)
            f1++;
        if (f1 > 1.0F)
            f1--;
        float f2 = f1;
        f1 = 1.0F - (float) ((Math.cos(f1 * Math.PI) + 1.0D) / 2.0D);
        f1 = f2 + (f1 - f2) / 3.0F;
        return f1;
    }

    public float[] a(float f, float f1) {
        float f2 = 0.4F;
        float f3 = MathsHelper.b(f * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0.0F;
        if (f3 >= f4 - f2 && f3 <= f4 + f2) {
            float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathsHelper.a(f5 * 3.141593F)) * 0.99F;
            f6 *= f6;
            this.h[0] = f5 * 0.3F + 0.7F;
            this.h[1] = f5 * f5 * 0.7F + 0.2F;
            this.h[2] = f5 * f5 * 0.0F + 0.2F;
            this.h[3] = f6;
            return this.h;
        }
        return null;
    }

    public bt b(float f, float f1) {
        float f2 = MathsHelper.b(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;
        float f3 = 0.7529412F;
        float f4 = 0.8470588F;
        float f5 = 1.0F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return bt.b(f3, f4, f5);
    }

    public boolean f() {
        return true;
    }

    public static xa a(int i) {
        if (i == -1)
            return (xa) new wd();
        if (i == 0)
            return (xa) new rh();
        if (i == 1)
            return (xa) new ay();
        return null;
    }

    public float d() {
        return 108.0F;
    }

    public boolean c() {
        return true;
    }
}
