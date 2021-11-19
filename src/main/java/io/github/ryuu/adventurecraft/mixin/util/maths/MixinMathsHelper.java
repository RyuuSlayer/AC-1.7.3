package io.github.ryuu.adventurecraft.mixin.util.maths;

import net.minecraft.util.maths.MathsHelper;

public class MixinMathsHelper {
    private static float[] TABLE = new float[65536];

    public static final float sin(float value) {
        return TABLE[(int)(value * 10430.38f) & 0xFFFF];
    }

    public static final float cos(float value) {
        return TABLE[(int)(value * 10430.38f + 16384.0f) & 0xFFFF];
    }

    public static final float sqrt(float value) {
        return (float)Math.sqrt(value);
    }

    public static final float sqrt(double value) {
        return (float)Math.sqrt(value);
    }

    public static int floor(float value) {
        int i = (int)value;
        return value >= (float)i ? i : i - 1;
    }

    public static int floor(double value) {
        int i = (int)value;
        return value >= (double)i ? i : i - 1;
    }

    public static int func_1108_b(double d) {
        return MathsHelper.floor(d);
    }

    public static float abs(float value) {
        return value < 0.0f ? -value : value;
    }

    public static double absMax(double arg0, double arg1) {
        if (arg0 < 0.0) {
            arg0 = -arg0;
        }
        if (arg1 < 0.0) {
            arg1 = -arg1;
        }
        return arg0 <= arg1 ? arg1 : arg0;
    }

    public static int method_650(int i, int j) {
        if (i < 0) {
            return -((-i - 1) / j) - 1;
        }
        return i / j;
    }

    public static boolean isStringEmpty(String string) {
        return string == null || string.length() == 0;
    }

    static {
        for (int i = 0; i < 65536; ++i) {
            MathsHelper.TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
        }
    }
}