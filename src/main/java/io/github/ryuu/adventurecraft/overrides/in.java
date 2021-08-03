package io.github.ryuu.adventurecraft.overrides;

public class in {
    public static final float a(float f) {
        return a[(int)(f * 10430.38F) & 0xFFFF];
    }

    public static final float b(float f) {
        return a[(int)(f * 10430.38F + 16384.0F) & 0xFFFF];
    }

    public static final float c(float f) {
        return (float)Math.sqrt(f);
    }

    public static final float a(double d) {
        return (float)Math.sqrt(d);
    }

    public static int d(float f) {
        int i = (int)f;
        return (f >= i) ? i : (i - 1);
    }

    public static int b(double d) {
        int i = (int)d;
        return (d >= i) ? i : (i - 1);
    }

    public static int func_1108_b(double d) {
        return b(d);
    }

    public static float e(float f) {
        return (f < 0.0F) ? -f : f;
    }

    public static double a(double d, double d1) {
        if (d < 0.0D)
            d = -d;
        if (d1 < 0.0D)
            d1 = -d1;
        return (d <= d1) ? d1 : d;
    }

    public static int a(int i, int j) {
        if (i < 0)
            return -((-i - 1) / j) - 1;
        return i / j;
    }

    public static boolean a(String s) {
        return (s == null || s.length() == 0);
    }

    private static float[] a = new float[65536];

    static {
        for (int i = 0; i < 65536; i++)
            a[i] = (float)Math.sin(i * Math.PI * 2.0D / 65536.0D);
    }
}
