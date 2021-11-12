package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.level.Level;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.tile.Tile;

public class MixinLightCalculator {
    public final eb a;

    public int b;

    public int c;

    public int d;

    public int e;

    public int f;

    public int g;

    public MixinLightCalculator(eb enumskyblock, int i, int j, int k, int l, int i1, int j1) {
        this.a = enumskyblock;
        this.b = i;
        this.c = j;
        this.d = k;
        this.e = l;
        this.f = i1;
        this.g = j1;
    }

    public void a(Level world) {
        int i = this.e - this.b + 1;
        int j = this.f - this.c + 1;
        int k = this.g - this.d + 1;
        int l = i * j * k;
        if (l > 32768) {
            System.out.println("Light too large, skipping!");
            return;
        }
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;
        boolean flag1 = false;
        for (int k1 = this.b; k1 <= this.e; k1++) {
            for (int l1 = this.d; l1 <= this.g; l1++) {
                int i2 = k1 >> 4;
                int j2 = l1 >> 4;
                boolean flag2 = false;
                if (flag && i2 == i1 && j2 == j1) {
                    flag2 = flag1;
                } else {
                    flag2 = world.b(k1, 0, l1, 1);
                    if (flag2) {
                        Chunk chunk = world.c(k1 >> 4, l1 >> 4);
                        if (chunk.h())
                            flag2 = false;
                    }
                    flag1 = flag2;
                    i1 = i2;
                    j1 = j2;
                }
                if (flag2) {
                    if (this.c < 0)
                        this.c = 0;
                    if (this.f >= 128)
                        this.f = 127;
                    for (int k2 = this.c; k2 <= this.f; k2++) {
                        int l2 = world.a(this.a, k1, k2, l1);
                        int i3 = 0;
                        int j3 = world.a(k1, k2, l1);
                        int k3 = Tile.q[j3];
                        if (k3 == 0)
                            k3 = 1;
                        int l3 = 0;
                        if (this.a == eb.a) {
                            if (world.o(k1, k2, l1))
                                l3 = 15;
                        } else if (this.a == eb.b) {
                            if (Tile.m[j3] != null) {
                                l3 = Tile.m[j3].getBlockLightValue(world, k1, k2, l1);
                            } else {
                                l3 = Tile.s[j3];
                            }
                        }
                        if (k3 >= 15 && l3 == 0) {
                            i3 = 0;
                        } else {
                            int i4 = world.a(this.a, k1 - 1, k2, l1);
                            int k4 = world.a(this.a, k1 + 1, k2, l1);
                            int l4 = world.a(this.a, k1, k2 - 1, l1);
                            int i5 = world.a(this.a, k1, k2 + 1, l1);
                            int j5 = world.a(this.a, k1, k2, l1 - 1);
                            int k5 = world.a(this.a, k1, k2, l1 + 1);
                            i3 = i4;
                            if (k4 > i3)
                                i3 = k4;
                            if (l4 > i3)
                                i3 = l4;
                            if (i5 > i3)
                                i3 = i5;
                            if (j5 > i3)
                                i3 = j5;
                            if (k5 > i3)
                                i3 = k5;
                            i3 -= k3;
                            if (i3 < 0)
                                i3 = 0;
                            if (l3 > i3)
                                i3 = l3;
                        }
                        if (l2 != i3) {
                            world.b(this.a, k1, k2, l1, i3);
                            int j4 = i3 - 1;
                            if (j4 < 0)
                                j4 = 0;
                            world.a(this.a, k1 - 1, k2, l1, j4);
                            world.a(this.a, k1, k2 - 1, l1, j4);
                            world.a(this.a, k1, k2, l1 - 1, j4);
                            if (k1 + 1 >= this.e)
                                world.a(this.a, k1 + 1, k2, l1, j4);
                            if (k2 + 1 >= this.f)
                                world.a(this.a, k1, k2 + 1, l1, j4);
                            if (l1 + 1 >= this.g)
                                world.a(this.a, k1, k2, l1 + 1, j4);
                        }
                    }
                }
            }
        }
    }

    public boolean a(int i, int j, int k, int l, int i1, int j1) {
        if (i >= this.b && j >= this.c && k >= this.d && l <= this.e && i1 <= this.f && j1 <= this.g)
            return true;
        int k1 = 1;
        if (i >= this.b - k1 && j >= this.c - k1 && k >= this.d - k1 && l <= this.e + k1 && i1 <= this.f + k1 && j1 <= this.g + k1) {
            int l1 = this.e - this.b;
            int i2 = this.f - this.c;
            int j2 = this.g - this.d;
            if (i > this.b)
                i = this.b;
            if (j > this.c)
                j = this.c;
            if (k > this.d)
                k = this.d;
            if (l < this.e)
                l = this.e;
            if (i1 < this.f)
                i1 = this.f;
            if (j1 < this.g)
                j1 = this.g;
            int k2 = l - i;
            int l2 = i1 - j;
            int i3 = j1 - k;
            int j3 = l1 * i2 * j2;
            int k3 = k2 * l2 * i3;
            if (k3 - j3 <= 2) {
                this.b = i;
                this.c = j;
                this.d = k;
                this.e = l;
                this.f = i1;
                this.g = j1;
                return true;
            }
        }
        return false;
    }
}