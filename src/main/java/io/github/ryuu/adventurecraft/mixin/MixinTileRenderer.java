package io.github.ryuu.adventurecraft.mixin;

import java.util.Random;

import io.github.ryuu.adventurecraft.blocks.BlockOverlay;
import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import org.lwjgl.opengl.GL11;

public class MixinTileRenderer {
    public static boolean a = true;
    private final int O;
    private final Random rand;
    private final int curTextureNum;
    public TileView c;
    public boolean b;
    private int d;
    private boolean e;
    private boolean f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private boolean m;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private float s;
    private float t;
    private float u;
    private float v;
    private float w;
    private float x;
    private float y;
    private float z;
    private float A;
    private float B;
    private float C;
    private float D;
    private float E;
    private float F;
    private float G;
    private float H;
    private float I;
    private float J;
    private float K;
    private float L;
    private float M;
    private float N;
    private float P;
    private float Q;
    private float R;
    private float S;
    private float T;
    private float U;
    private float V;
    private float W;
    private float X;
    private float Y;
    private float Z;
    private float aa;
    private boolean ab;
    private boolean ac;
    private boolean ad;
    private boolean ae;
    private boolean af;
    private boolean ag;
    private boolean ah;
    private boolean ai;
    private boolean aj;
    private boolean ak;
    private boolean al;
    private boolean am;

    public MixinTileRenderer(TileView iblockaccess) {
        this.curTextureNum = -1;
        this.d = -1;
        this.e = false;
        this.f = false;
        this.b = true;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.O = 1;
        this.c = iblockaccess;
        this.rand = new Random();
    }

    public MixinTileRenderer() {
        this.curTextureNum = -1;
        this.d = -1;
        this.e = false;
        this.f = false;
        this.b = true;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.O = 1;
        this.rand = new Random();
    }

    public static boolean a(int i) {
        if (i == 0)
            return true;
        if (i == 13)
            return true;
        if (i == 10)
            return true;
        if (i == 11)
            return true;
        return (i == 16);
    }

    public void a(Tile block, int i, int j, int k, int l) {
        this.d = l;
        b(block, i, j, k);
        this.d = -1;
    }

    public void a(Tile block, int i, int j, int k) {
        this.f = true;
        b(block, i, j, k);
        this.f = false;
    }

    public boolean b(Tile block, int i, int j, int k) {
        int l = block.b();
        if (!block.shouldRender(this.c, i, j, k))
            return false;
        block.a(this.c, i, j, k);
        if (l == 0)
            return l(block, i, j, k);
        if (l == 4)
            return k(block, i, j, k);
        if (l == 13)
            return m(block, i, j, k);
        if (l == 1)
            return i(block, i, j, k);
        if (l == 6)
            return j(block, i, j, k);
        if (l == 2)
            return c(block, i, j, k);
        if (l == 3)
            return f(block, i, j, k);
        if (l == 5)
            return g(block, i, j, k);
        if (l == 8)
            return h(block, i, j, k);
        if (l == 7)
            return p(block, i, j, k);
        if (l == 9)
            return a((pc) block, i, j, k);
        if (l == 10)
            return o(block, i, j, k);
        if (l == 11)
            return n(block, i, j, k);
        if (l == 12)
            return e(block, i, j, k);
        if (l == 14)
            return q(block, i, j, k);
        if (l == 15)
            return r(block, i, j, k);
        if (l == 16)
            return b(block, i, j, k, false);
        if (l == 17)
            return c(block, i, j, k, true);
        if (l == 30) {
            if (this.c != null && this.d == -1) {
                int blockID = this.c.a(i, j + 1, k);
                if (blockID == 0 || !Tile.m[blockID].shouldRender(this.c, i, j + 1, k))
                    renderGrass(block, i, j, k);
            }
            return l(block, i, j, k);
        }
        if (l == 31) {
            boolean rendered = l(block, i, j, k);
            if (Minecraft.minecraftInstance.f.triggerManager.isActivated(i, j, k)) {
                Tessellator.a.a(1.0F, 1.0F, 1.0F);
                this.d = 99;
            } else {
                this.d = 115;
            }
            a(block, i, j + 0.25D, k, 0.0D, 0.0D);
            this.d = -1;
            return rendered;
        }
        if (l == 32)
            return renderSpikes(block, i, j, k);
        if (l == 33)
            return renderTable(block, i, j, k);
        if (l == 34)
            return renderChair(block, i, j, k);
        if (l == 35)
            return renderRope(block, i, j, k);
        if (l == 36)
            return renderBlockTree(block, i, j, k);
        if (l == 37)
            return renderBlockOverlay(block, i, j, k);
        if (l == 38)
            return renderBlockSlope(block, i, j, k);
        return false;
    }

    private boolean q(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        int l = this.c.e(i, j, k);
        int i1 = ve.d(l);
        boolean flag = ve.e(l);
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        float f4 = f1;
        float f5 = f1;
        float f6 = f1;
        float f7 = f;
        float f8 = f2;
        float f9 = f3;
        float f10 = f;
        float f11 = f2;
        float f12 = f3;
        float f13 = f;
        float f14 = f2;
        float f15 = f3;
        float f16 = block.d(this.c, i, j, k);
        tessellator.a(f7 * f16, f10 * f16, f13 * f16);
        int f17 = block.a(this.c, i, j, k, 0);
        int j1 = (f17 & 0xF) << 4;
        int k1 = f17 & 0xF0;
        double d = (j1 / 256.0F);
        double d2 = ((j1 + 16) - 0.01D) / 256.0D;
        double d4 = (k1 / 256.0F);
        double d6 = ((k1 + 16) - 0.01D) / 256.0D;
        double d8 = i + block.bs;
        double d10 = i + block.bv;
        double d12 = j + block.bt + 0.1875D;
        double d14 = k + block.bu;
        double d16 = k + block.bx;
        tessellator.a(d8, d12, d16, d, d6);
        tessellator.a(d8, d12, d14, d, d4);
        tessellator.a(d10, d12, d14, d2, d4);
        tessellator.a(d10, d12, d16, d2, d6);
        float f17a = block.d(this.c, i, j + 1, k);
        tessellator.a(f4 * f17a, f5 * f17a, f6 * f17a);
        j1 = block.a(this.c, i, j, k, 1);
        k1 = (j1 & 0xF) << 4;
        d = (j1 & 0xF0);
        double d1 = (k1 / 256.0F);
        double d3 = ((k1 + 16) - 0.01D) / 256.0D;
        double d5 = ((float) d / 256.0F);
        double d7 = (d + 16.0D - 0.01D) / 256.0D;
        double d9 = d1;
        double d11 = d3;
        double d13 = d5;
        double d15 = d5;
        double d17 = d1;
        double d18 = d3;
        double d19 = d7;
        double d20 = d7;
        if (i1 == 0) {
            d11 = d1;
            d13 = d7;
            d17 = d3;
            d20 = d5;
        } else if (i1 == 2) {
            d9 = d3;
            d15 = d7;
            d18 = d1;
            d19 = d5;
        } else if (i1 == 3) {
            d9 = d3;
            d15 = d7;
            d18 = d1;
            d19 = d5;
            d11 = d1;
            d13 = d7;
            d17 = d3;
            d20 = d5;
        }
        double d21 = i + block.bs;
        double d22 = i + block.bv;
        double d23 = j + block.bw;
        double d24 = k + block.bu;
        double d25 = k + block.bx;
        tessellator.a(d22, d23, d25, d17, d19);
        tessellator.a(d22, d23, d24, d9, d13);
        tessellator.a(d21, d23, d24, d11, d15);
        tessellator.a(d21, d23, d25, d18, d20);
        f17 = jj.a[i1];
        if (flag)
            f17 = jj.a[jj.b[i1]];
        j1 = 4;
        switch (i1) {
            case 0:
                j1 = 5;
                break;
            case 3:
                j1 = 2;
                break;
            case 1:
                j1 = 3;
                break;
        }
        if (f17 != 2 && (this.f || block.b(this.c, i, j, k - 1, 2))) {
            float f18 = block.d(this.c, i, j, k - 1);
            if (block.bu > 0.0D)
                f18 = f16;
            tessellator.a(f8 * f18, f11 * f18, f14 * f18);
            this.e = (j1 == 2);
            c(block, i, j, k, block.a(this.c, i, j, k, 2));
        }
        if (f17 != 3 && (this.f || block.b(this.c, i, j, k + 1, 3))) {
            float f19 = block.d(this.c, i, j, k + 1);
            if (block.bx < 1.0D)
                f19 = f16;
            tessellator.a(f8 * f19, f11 * f19, f14 * f19);
            this.e = (j1 == 3);
            d(block, i, j, k, block.a(this.c, i, j, k, 3));
        }
        if (f17 != 4 && (this.f || block.b(this.c, i - 1, j, k, 4))) {
            float f20 = block.d(this.c, i - 1, j, k);
            if (block.bs > 0.0D)
                f20 = f16;
            tessellator.a(f9 * f20, f12 * f20, f15 * f20);
            this.e = (j1 == 4);
            e(block, i, j, k, block.a(this.c, i, j, k, 4));
        }
        if (f17 != 5 && (this.f || block.b(this.c, i + 1, j, k, 5))) {
            float f21 = block.d(this.c, i + 1, j, k);
            if (block.bv < 1.0D)
                f21 = f16;
            tessellator.a(f9 * f21, f12 * f21, f15 * f21);
            this.e = (j1 == 5);
            f(block, i, j, k, block.a(this.c, i, j, k, 5));
        }
        this.e = false;
        return true;
    }

    public boolean c(Tile block, int i, int j, int k) {
        int l = this.c.e(i, j, k);
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f = 1.0F;
        tessellator.a(f, f, f);
        double d = 0.4000000059604645D;
        double d1 = 0.5D - d;
        double d2 = 0.20000000298023224D;
        if (l == 1) {
            a(block, i - d1, j + d2, k, -d, 0.0D);
        } else if (l == 2) {
            a(block, i + d1, j + d2, k, d, 0.0D);
        } else if (l == 3) {
            a(block, i, j + d2, k - d1, 0.0D, -d);
        } else if (l == 4) {
            a(block, i, j + d2, k + d1, 0.0D, d);
        } else {
            a(block, i, j, k, 0.0D, 0.0D);
        }
        return true;
    }

    private boolean r(Tile block, int i, int j, int k) {
        int l = this.c.e(i, j, k);
        int i1 = l & 0x3;
        int j1 = (l & 0xC) >> 2;
        l(block, i, j, k);
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f = (f + 1.0F) * 0.5F;
        tessellator.a(f, f, f);
        double d = -0.1875D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        double d3 = 0.0D;
        double d4 = 0.0D;
        switch (i1) {
            case 0:
                d4 = -0.3125D;
                d2 = wo.a[j1];
                break;
            case 2:
                d4 = 0.3125D;
                d2 = -wo.a[j1];
                break;
            case 3:
                d3 = -0.3125D;
                d1 = wo.a[j1];
                break;
            case 1:
                d3 = 0.3125D;
                d1 = -wo.a[j1];
                break;
        }
        a(block, i + d1, j + d, k + d2, 0.0D, 0.0D);
        a(block, i + d3, j + d, k + d4, 0.0D, 0.0D);
        int k1 = block.a(1);
        int l1 = (k1 & 0xF) << 4;
        int i2 = k1 & 0xF0;
        double d5 = (l1 / 256.0F);
        double d6 = ((l1 + 15.99F) / 256.0F);
        double d7 = (i2 / 256.0F);
        double d8 = ((i2 + 15.99F) / 256.0F);
        float f1 = 0.125F;
        float f2 = (i + 1);
        float f3 = (i + 1);
        float f4 = (i + 0);
        float f5 = (i + 0);
        float f6 = (k + 0);
        float f7 = (k + 1);
        float f8 = (k + 1);
        float f9 = (k + 0);
        float f10 = j + f1;
        if (i1 == 2) {
            f2 = f3 = (i + 0);
            f4 = f5 = (i + 1);
            f6 = f9 = (k + 1);
            f7 = f8 = (k + 0);
        } else if (i1 == 3) {
            f2 = f5 = (i + 0);
            f3 = f4 = (i + 1);
            f6 = f7 = (k + 0);
            f8 = f9 = (k + 1);
        } else if (i1 == 1) {
            f2 = f5 = (i + 1);
            f3 = f4 = (i + 0);
            f6 = f7 = (k + 1);
            f8 = f9 = (k + 0);
        }
        tessellator.a(f5, f10, f9, d5, d7);
        tessellator.a(f4, f10, f8, d5, d8);
        tessellator.a(f3, f10, f7, d6, d8);
        tessellator.a(f2, f10, f6, d6, d7);
        return true;
    }

    public void d(Tile block, int i, int j, int k) {
        this.f = true;
        b(block, i, j, k, true);
        this.f = false;
    }

    private boolean b(Tile block, int i, int j, int k, boolean flag) {
        int l = this.c.e(i, j, k);
        boolean flag1 = (flag || (l & 0x8) != 0);
        int i1 = jq.d(l);
        if (flag1) {
            switch (i1) {
                case 0:
                    this.g = 3;
                    this.h = 3;
                    this.i = 3;
                    this.j = 3;
                    block.a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;
                case 1:
                    block.a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                    break;
                case 2:
                    this.i = 1;
                    this.j = 2;
                    block.a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                    break;
                case 3:
                    this.i = 2;
                    this.j = 1;
                    this.k = 3;
                    this.l = 3;
                    block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                    break;
                case 4:
                    this.g = 1;
                    this.h = 2;
                    this.k = 2;
                    this.l = 1;
                    block.a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;
                case 5:
                    this.g = 2;
                    this.h = 1;
                    this.k = 1;
                    this.l = 2;
                    block.a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
                    break;
            }
            l(block, i, j, k);
            this.g = 0;
            this.h = 0;
            this.i = 0;
            this.j = 0;
            this.k = 0;
            this.l = 0;
            block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        } else {
            switch (i1) {
                case 0:
                    this.g = 3;
                    this.h = 3;
                    this.i = 3;
                    this.j = 3;
                    break;
                case 2:
                    this.i = 1;
                    this.j = 2;
                    break;
                case 3:
                    this.i = 2;
                    this.j = 1;
                    this.k = 3;
                    this.l = 3;
                    break;
                case 4:
                    this.g = 1;
                    this.h = 2;
                    this.k = 2;
                    this.l = 1;
                    break;
                case 5:
                    this.g = 2;
                    this.h = 1;
                    this.k = 1;
                    this.l = 2;
                    break;
            }
            l(block, i, j, k);
            this.g = 0;
            this.h = 0;
            this.i = 0;
            this.j = 0;
            this.k = 0;
            this.l = 0;
        }
        return true;
    }

    private void a(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6) {
        int i = 108;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        Tessellator tessellator = Tessellator.a;
        double d7 = ((j + 0) / 256.0F);
        double d8 = ((k + 0) / 256.0F);
        double d9 = (j + d6 - 0.01D) / 256.0D;
        double d10 = ((k + 4.0F) - 0.01D) / 256.0D;
        tessellator.a(f, f, f);
        tessellator.a(d, d3, d4, d9, d8);
        tessellator.a(d, d2, d4, d7, d8);
        tessellator.a(d1, d2, d5, d7, d10);
        tessellator.a(d1, d3, d5, d9, d10);
    }

    private void b(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6) {
        int i = 108;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        Tessellator tessellator = Tessellator.a;
        double d7 = ((j + 0) / 256.0F);
        double d8 = ((k + 0) / 256.0F);
        double d9 = (j + d6 - 0.01D) / 256.0D;
        double d10 = ((k + 4.0F) - 0.01D) / 256.0D;
        tessellator.a(f, f, f);
        tessellator.a(d, d2, d5, d9, d8);
        tessellator.a(d, d2, d4, d7, d8);
        tessellator.a(d1, d3, d4, d7, d10);
        tessellator.a(d1, d3, d5, d9, d10);
    }

    private void c(double d, double d1, double d2, double d3, double d4, double d5, float f, double d6) {
        int i = 108;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        Tessellator tessellator = Tessellator.a;
        double d7 = ((j + 0) / 256.0F);
        double d8 = ((k + 0) / 256.0F);
        double d9 = (j + d6 - 0.01D) / 256.0D;
        double d10 = ((k + 4.0F) - 0.01D) / 256.0D;
        tessellator.a(f, f, f);
        tessellator.a(d1, d2, d4, d9, d8);
        tessellator.a(d, d2, d4, d7, d8);
        tessellator.a(d, d3, d5, d7, d10);
        tessellator.a(d1, d3, d5, d9, d10);
    }

    public void a(Tile block, int i, int j, int k, boolean flag) {
        this.f = true;
        c(block, i, j, k, flag);
        this.f = false;
    }

    private boolean c(Tile block, int i, int j, int k, boolean flag) {
        int l = this.c.e(i, j, k);
        int i1 = h.c(l);
        float f = block.d(this.c, i, j, k);
        float f1 = flag ? 1.0F : 0.5F;
        double d = flag ? 16.0D : 8.0D;
        switch (i1) {
            case 0:
                this.g = 3;
                this.h = 3;
                this.i = 3;
                this.j = 3;
                block.a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                l(block, i, j, k);
                a((i + 0.375F), (i + 0.625F), (j + 0.25F), (j + 0.25F + f1), (k + 0.625F), (k + 0.625F), f * 0.8F, d);
                a((i + 0.625F), (i + 0.375F), (j + 0.25F), (j + 0.25F + f1), (k + 0.375F), (k + 0.375F), f * 0.8F, d);
                a((i + 0.375F), (i + 0.375F), (j + 0.25F), (j + 0.25F + f1), (k + 0.375F), (k + 0.625F), f * 0.6F, d);
                a((i + 0.625F), (i + 0.625F), (j + 0.25F), (j + 0.25F + f1), (k + 0.625F), (k + 0.375F), f * 0.6F, d);
                break;
            case 1:
                block.a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                l(block, i, j, k);
                a((i + 0.375F), (i + 0.625F), (j - 0.25F + 1.0F - f1), (j - 0.25F + 1.0F), (k + 0.625F), (k + 0.625F), f * 0.8F, d);
                a((i + 0.625F), (i + 0.375F), (j - 0.25F + 1.0F - f1), (j - 0.25F + 1.0F), (k + 0.375F), (k + 0.375F), f * 0.8F, d);
                a((i + 0.375F), (i + 0.375F), (j - 0.25F + 1.0F - f1), (j - 0.25F + 1.0F), (k + 0.375F), (k + 0.625F), f * 0.6F, d);
                a((i + 0.625F), (i + 0.625F), (j - 0.25F + 1.0F - f1), (j - 0.25F + 1.0F), (k + 0.625F), (k + 0.375F), f * 0.6F, d);
                break;
            case 2:
                this.i = 1;
                this.j = 2;
                block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                l(block, i, j, k);
                b((i + 0.375F), (i + 0.375F), (j + 0.625F), (j + 0.375F), (k + 0.25F), (k + 0.25F + f1), f * 0.6F, d);
                b((i + 0.625F), (i + 0.625F), (j + 0.375F), (j + 0.625F), (k + 0.25F), (k + 0.25F + f1), f * 0.6F, d);
                b((i + 0.375F), (i + 0.625F), (j + 0.375F), (j + 0.375F), (k + 0.25F), (k + 0.25F + f1), f * 0.5F, d);
                b((i + 0.625F), (i + 0.375F), (j + 0.625F), (j + 0.625F), (k + 0.25F), (k + 0.25F + f1), f, d);
                break;
            case 3:
                this.i = 2;
                this.j = 1;
                this.k = 3;
                this.l = 3;
                block.a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                l(block, i, j, k);
                b((i + 0.375F), (i + 0.375F), (j + 0.625F), (j + 0.375F), (k - 0.25F + 1.0F - f1), (k - 0.25F + 1.0F), f * 0.6F, d);
                b((i + 0.625F), (i + 0.625F), (j + 0.375F), (j + 0.625F), (k - 0.25F + 1.0F - f1), (k - 0.25F + 1.0F), f * 0.6F, d);
                b((i + 0.375F), (i + 0.625F), (j + 0.375F), (j + 0.375F), (k - 0.25F + 1.0F - f1), (k - 0.25F + 1.0F), f * 0.5F, d);
                b((i + 0.625F), (i + 0.375F), (j + 0.625F), (j + 0.625F), (k - 0.25F + 1.0F - f1), (k - 0.25F + 1.0F), f, d);
                break;
            case 4:
                this.g = 1;
                this.h = 2;
                this.k = 2;
                this.l = 1;
                block.a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                l(block, i, j, k);
                c((i + 0.25F), (i + 0.25F + f1), (j + 0.375F), (j + 0.375F), (k + 0.625F), (k + 0.375F), f * 0.5F, d);
                c((i + 0.25F), (i + 0.25F + f1), (j + 0.625F), (j + 0.625F), (k + 0.375F), (k + 0.625F), f, d);
                c((i + 0.25F), (i + 0.25F + f1), (j + 0.375F), (j + 0.625F), (k + 0.375F), (k + 0.375F), f * 0.6F, d);
                c((i + 0.25F), (i + 0.25F + f1), (j + 0.625F), (j + 0.375F), (k + 0.625F), (k + 0.625F), f * 0.6F, d);
                break;
            case 5:
                this.g = 2;
                this.h = 1;
                this.k = 1;
                this.l = 2;
                block.a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                l(block, i, j, k);
                c((i - 0.25F + 1.0F - f1), (i - 0.25F + 1.0F), (j + 0.375F), (j + 0.375F), (k + 0.625F), (k + 0.375F), f * 0.5F, d);
                c((i - 0.25F + 1.0F - f1), (i - 0.25F + 1.0F), (j + 0.625F), (j + 0.625F), (k + 0.375F), (k + 0.625F), f, d);
                c((i - 0.25F + 1.0F - f1), (i - 0.25F + 1.0F), (j + 0.375F), (j + 0.625F), (k + 0.375F), (k + 0.375F), f * 0.6F, d);
                c((i - 0.25F + 1.0F - f1), (i - 0.25F + 1.0F), (j + 0.625F), (j + 0.375F), (k + 0.625F), (k + 0.625F), f * 0.6F, d);
                break;
        }
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    public boolean e(Tile block, int i, int j, int k) {
        int l = this.c.e(i, j, k);
        int i1 = l & 0x7;
        boolean flag = ((l & 0x8) > 0);
        Tessellator tessellator = Tessellator.a;
        boolean flag1 = (this.d >= 0);
        if (!flag1)
            this.d = Tile.x.bm;
        float f = 0.25F;
        float f1 = 0.1875F;
        float f2 = 0.1875F;
        if (i1 == 5) {
            block.a(0.5F - f1, 0.0F, 0.5F - f, 0.5F + f1, f2, 0.5F + f);
        } else if (i1 == 6) {
            block.a(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, f2, 0.5F + f1);
        } else if (i1 == 4) {
            block.a(0.5F - f1, 0.5F - f, 1.0F - f2, 0.5F + f1, 0.5F + f, 1.0F);
        } else if (i1 == 3) {
            block.a(0.5F - f1, 0.5F - f, 0.0F, 0.5F + f1, 0.5F + f, f2);
        } else if (i1 == 2) {
            block.a(1.0F - f2, 0.5F - f, 0.5F - f1, 1.0F, 0.5F + f, 0.5F + f1);
        } else if (i1 == 1) {
            block.a(0.0F, 0.5F - f, 0.5F - f1, f2, 0.5F + f, 0.5F + f1);
        }
        l(block, i, j, k);
        if (!flag1)
            this.d = -1;
        float f3 = block.d(this.c, i, j, k);
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f3 = 1.0F;
        tessellator.a(f3, f3, f3);
        int j1 = block.a(0);
        if (this.d >= 0)
            j1 = this.d;
        int k1 = (j1 & 0xF) << 4;
        int l1 = j1 & 0xF0;
        float f4 = k1 / 256.0F;
        float f5 = (k1 + 15.99F) / 256.0F;
        float f6 = l1 / 256.0F;
        float f7 = (l1 + 15.99F) / 256.0F;
        bt[] avec3d = new bt[8];
        float f8 = 0.0625F;
        float f9 = 0.0625F;
        float f10 = 0.625F;
        avec3d[0] = bt.b(-f8, 0.0D, -f9);
        avec3d[1] = bt.b(f8, 0.0D, -f9);
        avec3d[2] = bt.b(f8, 0.0D, f9);
        avec3d[3] = bt.b(-f8, 0.0D, f9);
        avec3d[4] = bt.b(-f8, f10, -f9);
        avec3d[5] = bt.b(f8, f10, -f9);
        avec3d[6] = bt.b(f8, f10, f9);
        avec3d[7] = bt.b(-f8, f10, f9);
        for (int i2 = 0; i2 < 8; i2++) {
            if (flag) {
                (avec3d[i2]).c -= 0.0625D;
                avec3d[i2].a(0.6981317F);
            } else {
                (avec3d[i2]).c += 0.0625D;
                avec3d[i2].a(-0.6981317F);
            }
            if (i1 == 6)
                avec3d[i2].b(1.570796F);
            if (i1 < 5) {
                (avec3d[i2]).b -= 0.375D;
                avec3d[i2].a(1.570796F);
                if (i1 == 4)
                    avec3d[i2].b(0.0F);
                if (i1 == 3)
                    avec3d[i2].b(3.141593F);
                if (i1 == 2)
                    avec3d[i2].b(1.570796F);
                if (i1 == 1)
                    avec3d[i2].b(-1.570796F);
                (avec3d[i2]).a += i + 0.5D;
                (avec3d[i2]).b += (j + 0.5F);
                (avec3d[i2]).c += k + 0.5D;
            } else {
                (avec3d[i2]).a += i + 0.5D;
                (avec3d[i2]).b += (j + 0.125F);
                (avec3d[i2]).c += k + 0.5D;
            }
        }
        bt vec3d = null;
        bt vec3d1 = null;
        bt vec3d2 = null;
        bt vec3d3 = null;
        for (int j2 = 0; j2 < 6; j2++) {
            if (j2 == 0) {
                f4 = (k1 + 7) / 256.0F;
                f5 = ((k1 + 9) - 0.01F) / 256.0F;
                f6 = (l1 + 6) / 256.0F;
                f7 = ((l1 + 8) - 0.01F) / 256.0F;
            } else if (j2 == 2) {
                f4 = (k1 + 7) / 256.0F;
                f5 = ((k1 + 9) - 0.01F) / 256.0F;
                f6 = (l1 + 6) / 256.0F;
                f7 = ((l1 + 16) - 0.01F) / 256.0F;
            }
            if (j2 == 0) {
                vec3d = avec3d[0];
                vec3d1 = avec3d[1];
                vec3d2 = avec3d[2];
                vec3d3 = avec3d[3];
            } else if (j2 == 1) {
                vec3d = avec3d[7];
                vec3d1 = avec3d[6];
                vec3d2 = avec3d[5];
                vec3d3 = avec3d[4];
            } else if (j2 == 2) {
                vec3d = avec3d[1];
                vec3d1 = avec3d[0];
                vec3d2 = avec3d[4];
                vec3d3 = avec3d[5];
            } else if (j2 == 3) {
                vec3d = avec3d[2];
                vec3d1 = avec3d[1];
                vec3d2 = avec3d[5];
                vec3d3 = avec3d[6];
            } else if (j2 == 4) {
                vec3d = avec3d[3];
                vec3d1 = avec3d[2];
                vec3d2 = avec3d[6];
                vec3d3 = avec3d[7];
            } else if (j2 == 5) {
                vec3d = avec3d[0];
                vec3d1 = avec3d[3];
                vec3d2 = avec3d[7];
                vec3d3 = avec3d[4];
            }
            tessellator.a(vec3d.a, vec3d.b, vec3d.c, f4, f7);
            tessellator.a(vec3d1.a, vec3d1.b, vec3d1.c, f5, f7);
            tessellator.a(vec3d2.a, vec3d2.b, vec3d2.c, f5, f6);
            tessellator.a(vec3d3.a, vec3d3.b, vec3d3.c, f4, f6);
        }
        return true;
    }

    public boolean f(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        int l = block.a(0);
        if (this.d >= 0)
            l = this.d;
        float f = block.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        int i1 = (l & 0xF) << 4;
        int j1 = l & 0xF0;
        double d = (i1 / 256.0F);
        double d2 = ((i1 + 15.99F) / 256.0F);
        double d4 = (j1 / 256.0F);
        double d6 = ((j1 + 15.99F) / 256.0F);
        float f1 = 1.4F;
        if (this.c.h(i, j - 1, k) || Tile.as.c(this.c, i, j - 1, k)) {
            double d8 = i + 0.5D + 0.2D;
            double d9 = i + 0.5D - 0.2D;
            double d12 = k + 0.5D + 0.2D;
            double d14 = k + 0.5D - 0.2D;
            double d16 = i + 0.5D - 0.3D;
            double d18 = i + 0.5D + 0.3D;
            double d20 = k + 0.5D - 0.3D;
            double d22 = k + 0.5D + 0.3D;
            tessellator.a(d16, (j + f1), (k + 1), d2, d4);
            tessellator.a(d8, (j + 0), (k + 1), d2, d6);
            tessellator.a(d8, (j + 0), (k + 0), d, d6);
            tessellator.a(d16, (j + f1), (k + 0), d, d4);
            tessellator.a(d18, (j + f1), (k + 0), d2, d4);
            tessellator.a(d9, (j + 0), (k + 0), d2, d6);
            tessellator.a(d9, (j + 0), (k + 1), d, d6);
            tessellator.a(d18, (j + f1), (k + 1), d, d4);
            d = (i1 / 256.0F);
            d2 = ((i1 + 15.99F) / 256.0F);
            d4 = ((j1 + 16) / 256.0F);
            d6 = ((j1 + 15.99F + 16.0F) / 256.0F);
            tessellator.a((i + 1), (j + f1), d22, d2, d4);
            tessellator.a((i + 1), (j + 0), d14, d2, d6);
            tessellator.a((i + 0), (j + 0), d14, d, d6);
            tessellator.a((i + 0), (j + f1), d22, d, d4);
            tessellator.a((i + 0), (j + f1), d20, d2, d4);
            tessellator.a((i + 0), (j + 0), d12, d2, d6);
            tessellator.a((i + 1), (j + 0), d12, d, d6);
            tessellator.a((i + 1), (j + f1), d20, d, d4);
            d8 = i + 0.5D - 0.5D;
            d9 = i + 0.5D + 0.5D;
            d12 = k + 0.5D - 0.5D;
            d14 = k + 0.5D + 0.5D;
            d16 = i + 0.5D - 0.4D;
            d18 = i + 0.5D + 0.4D;
            d20 = k + 0.5D - 0.4D;
            d22 = k + 0.5D + 0.4D;
            tessellator.a(d16, (j + f1), (k + 0), d, d4);
            tessellator.a(d8, (j + 0), (k + 0), d, d6);
            tessellator.a(d8, (j + 0), (k + 1), d2, d6);
            tessellator.a(d16, (j + f1), (k + 1), d2, d4);
            tessellator.a(d18, (j + f1), (k + 1), d, d4);
            tessellator.a(d9, (j + 0), (k + 1), d, d6);
            tessellator.a(d9, (j + 0), (k + 0), d2, d6);
            tessellator.a(d18, (j + f1), (k + 0), d2, d4);
            d = (i1 / 256.0F);
            d2 = ((i1 + 15.99F) / 256.0F);
            d4 = (j1 / 256.0F);
            d6 = ((j1 + 15.99F) / 256.0F);
            tessellator.a((i + 0), (j + f1), d22, d, d4);
            tessellator.a((i + 0), (j + 0), d14, d, d6);
            tessellator.a((i + 1), (j + 0), d14, d2, d6);
            tessellator.a((i + 1), (j + f1), d22, d2, d4);
            tessellator.a((i + 1), (j + f1), d20, d, d4);
            tessellator.a((i + 1), (j + 0), d12, d, d6);
            tessellator.a((i + 0), (j + 0), d12, d2, d6);
            tessellator.a((i + 0), (j + f1), d20, d2, d4);
        } else {
            float f3 = 0.2F;
            float f4 = 0.0625F;
            if ((i + j + k & 0x1) == 1) {
                d = (i1 / 256.0F);
                d2 = ((i1 + 15.99F) / 256.0F);
                d4 = ((j1 + 16) / 256.0F);
                d6 = ((j1 + 15.99F + 16.0F) / 256.0F);
            }
            if ((i / 2 + j / 2 + k / 2 & 0x1) == 1) {
                double d10 = d2;
                d2 = d;
                d = d10;
            }
            if (Tile.as.c(this.c, i - 1, j, k)) {
                tessellator.a((i + f3), (j + f1 + f4), (k + 1), d2, d4);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 1), d2, d6);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 0), d, d6);
                tessellator.a((i + f3), (j + f1 + f4), (k + 0), d, d4);
                tessellator.a((i + f3), (j + f1 + f4), (k + 0), d, d4);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 0), d, d6);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 1), d2, d6);
                tessellator.a((i + f3), (j + f1 + f4), (k + 1), d2, d4);
            }
            if (Tile.as.c(this.c, i + 1, j, k)) {
                tessellator.a(((i + 1) - f3), (j + f1 + f4), (k + 0), d, d4);
                tessellator.a((i + 1 - 0), ((j + 0) + f4), (k + 0), d, d6);
                tessellator.a((i + 1 - 0), ((j + 0) + f4), (k + 1), d2, d6);
                tessellator.a(((i + 1) - f3), (j + f1 + f4), (k + 1), d2, d4);
                tessellator.a(((i + 1) - f3), (j + f1 + f4), (k + 1), d2, d4);
                tessellator.a((i + 1 - 0), ((j + 0) + f4), (k + 1), d2, d6);
                tessellator.a((i + 1 - 0), ((j + 0) + f4), (k + 0), d, d6);
                tessellator.a(((i + 1) - f3), (j + f1 + f4), (k + 0), d, d4);
            }
            if (Tile.as.c(this.c, i, j, k - 1)) {
                tessellator.a((i + 0), (j + f1 + f4), (k + f3), d2, d4);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 0), d2, d6);
                tessellator.a((i + 1), ((j + 0) + f4), (k + 0), d, d6);
                tessellator.a((i + 1), (j + f1 + f4), (k + f3), d, d4);
                tessellator.a((i + 1), (j + f1 + f4), (k + f3), d, d4);
                tessellator.a((i + 1), ((j + 0) + f4), (k + 0), d, d6);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 0), d2, d6);
                tessellator.a((i + 0), (j + f1 + f4), (k + f3), d2, d4);
            }
            if (Tile.as.c(this.c, i, j, k + 1)) {
                tessellator.a((i + 1), (j + f1 + f4), ((k + 1) - f3), d, d4);
                tessellator.a((i + 1), ((j + 0) + f4), (k + 1 - 0), d, d6);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 1 - 0), d2, d6);
                tessellator.a((i + 0), (j + f1 + f4), ((k + 1) - f3), d2, d4);
                tessellator.a((i + 0), (j + f1 + f4), ((k + 1) - f3), d2, d4);
                tessellator.a((i + 0), ((j + 0) + f4), (k + 1 - 0), d2, d6);
                tessellator.a((i + 1), ((j + 0) + f4), (k + 1 - 0), d, d6);
                tessellator.a((i + 1), (j + f1 + f4), ((k + 1) - f3), d, d4);
            }
            if (Tile.as.c(this.c, i, j + 1, k)) {
                double d11 = i + 0.5D + 0.5D;
                double d13 = i + 0.5D - 0.5D;
                double d15 = k + 0.5D + 0.5D;
                double d17 = k + 0.5D - 0.5D;
                double d19 = i + 0.5D - 0.5D;
                double d21 = i + 0.5D + 0.5D;
                double d23 = k + 0.5D - 0.5D;
                double d24 = k + 0.5D + 0.5D;
                double d1 = (i1 / 256.0F);
                double d3 = ((i1 + 15.99F) / 256.0F);
                double d5 = (j1 / 256.0F);
                double d7 = ((j1 + 15.99F) / 256.0F);
                j++;
                float f2 = -0.2F;
                if ((i + j + k & 0x1) == 0) {
                    tessellator.a(d19, (j + f2), (k + 0), d3, d5);
                    tessellator.a(d11, (j + 0), (k + 0), d3, d7);
                    tessellator.a(d11, (j + 0), (k + 1), d1, d7);
                    tessellator.a(d19, (j + f2), (k + 1), d1, d5);
                    d1 = (i1 / 256.0F);
                    d3 = ((i1 + 15.99F) / 256.0F);
                    d5 = ((j1 + 16) / 256.0F);
                    d7 = ((j1 + 15.99F + 16.0F) / 256.0F);
                    tessellator.a(d21, (j + f2), (k + 1), d3, d5);
                    tessellator.a(d13, (j + 0), (k + 1), d3, d7);
                    tessellator.a(d13, (j + 0), (k + 0), d1, d7);
                    tessellator.a(d21, (j + f2), (k + 0), d1, d5);
                } else {
                    tessellator.a((i + 0), (j + f2), d24, d3, d5);
                    tessellator.a((i + 0), (j + 0), d17, d3, d7);
                    tessellator.a((i + 1), (j + 0), d17, d1, d7);
                    tessellator.a((i + 1), (j + f2), d24, d1, d5);
                    d1 = (i1 / 256.0F);
                    d3 = ((i1 + 15.99F) / 256.0F);
                    d5 = ((j1 + 16) / 256.0F);
                    d7 = ((j1 + 15.99F + 16.0F) / 256.0F);
                    tessellator.a((i + 1), (j + f2), d23, d3, d5);
                    tessellator.a((i + 1), (j + 0), d15, d3, d7);
                    tessellator.a((i + 0), (j + 0), d15, d1, d7);
                    tessellator.a((i + 0), (j + f2), d23, d1, d5);
                }
            }
        }
        return true;
    }

    public boolean g(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        int l = this.c.e(i, j, k);
        int i1 = block.a(1, l);
        if (this.d >= 0)
            i1 = this.d;
        float f = block.d(this.c, i, j, k);
        float f1 = l / 15.0F;
        float f2 = f1 * 0.6F + 0.4F;
        if (l == 0)
            f2 = 0.3F;
        float f3 = f1 * f1 * 0.7F - 0.5F;
        float f4 = f1 * f1 * 0.6F - 0.7F;
        if (f3 < 0.0F)
            f3 = 0.0F;
        if (f4 < 0.0F)
            f4 = 0.0F;
        tessellator.a(f * f2, f * f3, f * f4);
        int j1 = (i1 & 0xF) << 4;
        int k1 = i1 & 0xF0;
        double d = (j1 / 256.0F);
        double d2 = ((j1 + 15.99F) / 256.0F);
        double d4 = (k1 / 256.0F);
        double d6 = ((k1 + 15.99F) / 256.0F);
        boolean flag = (sm.e(this.c, i - 1, j, k, 1) || (!this.c.h(i - 1, j, k) && sm.e(this.c, i - 1, j - 1, k, -1)));
        boolean flag1 = (sm.e(this.c, i + 1, j, k, 3) || (!this.c.h(i + 1, j, k) && sm.e(this.c, i + 1, j - 1, k, -1)));
        boolean flag2 = (sm.e(this.c, i, j, k - 1, 2) || (!this.c.h(i, j, k - 1) && sm.e(this.c, i, j - 1, k - 1, -1)));
        boolean flag3 = (sm.e(this.c, i, j, k + 1, 0) || (!this.c.h(i, j, k + 1) && sm.e(this.c, i, j - 1, k + 1, -1)));
        if (!this.c.h(i, j + 1, k)) {
            if (this.c.h(i - 1, j, k) && sm.e(this.c, i - 1, j + 1, k, -1))
                flag = true;
            if (this.c.h(i + 1, j, k) && sm.e(this.c, i + 1, j + 1, k, -1))
                flag1 = true;
            if (this.c.h(i, j, k - 1) && sm.e(this.c, i, j + 1, k - 1, -1))
                flag2 = true;
            if (this.c.h(i, j, k + 1) && sm.e(this.c, i, j + 1, k + 1, -1))
                flag3 = true;
        }
        float f5 = (i + 0);
        float f6 = (i + 1);
        float f7 = (k + 0);
        float f8 = (k + 1);
        byte byte0 = 0;
        if ((flag || flag1) && !flag2 && !flag3)
            byte0 = 1;
        if ((flag2 || flag3) && !flag1 && !flag)
            byte0 = 2;
        if (byte0 != 0) {
            d = ((j1 + 16) / 256.0F);
            d2 = (((j1 + 16) + 15.99F) / 256.0F);
            d4 = (k1 / 256.0F);
            d6 = ((k1 + 15.99F) / 256.0F);
        }
        if (byte0 == 0) {
            if (flag1 || flag2 || flag3 || flag) {
                if (!flag)
                    f5 += 0.3125F;
                if (!flag)
                    d += 0.01953125D;
                if (!flag1)
                    f6 -= 0.3125F;
                if (!flag1)
                    d2 -= 0.01953125D;
                if (!flag2)
                    f7 += 0.3125F;
                if (!flag2)
                    d4 += 0.01953125D;
                if (!flag3)
                    f8 -= 0.3125F;
                if (!flag3)
                    d6 -= 0.01953125D;
            }
            tessellator.a(f6, (j + 0.015625F), f8, d2, d6);
            tessellator.a(f6, (j + 0.015625F), f7, d2, d4);
            tessellator.a(f5, (j + 0.015625F), f7, d, d4);
            tessellator.a(f5, (j + 0.015625F), f8, d, d6);
            tessellator.a(f, f, f);
            tessellator.a(f6, (j + 0.015625F), f8, d2, d6 + 0.0625D);
            tessellator.a(f6, (j + 0.015625F), f7, d2, d4 + 0.0625D);
            tessellator.a(f5, (j + 0.015625F), f7, d, d4 + 0.0625D);
            tessellator.a(f5, (j + 0.015625F), f8, d, d6 + 0.0625D);
        } else if (byte0 == 1) {
            tessellator.a(f6, (j + 0.015625F), f8, d2, d6);
            tessellator.a(f6, (j + 0.015625F), f7, d2, d4);
            tessellator.a(f5, (j + 0.015625F), f7, d, d4);
            tessellator.a(f5, (j + 0.015625F), f8, d, d6);
            tessellator.a(f, f, f);
            tessellator.a(f6, (j + 0.015625F), f8, d2, d6 + 0.0625D);
            tessellator.a(f6, (j + 0.015625F), f7, d2, d4 + 0.0625D);
            tessellator.a(f5, (j + 0.015625F), f7, d, d4 + 0.0625D);
            tessellator.a(f5, (j + 0.015625F), f8, d, d6 + 0.0625D);
        } else if (byte0 == 2) {
            tessellator.a(f6, (j + 0.015625F), f8, d2, d6);
            tessellator.a(f6, (j + 0.015625F), f7, d, d6);
            tessellator.a(f5, (j + 0.015625F), f7, d, d4);
            tessellator.a(f5, (j + 0.015625F), f8, d2, d4);
            tessellator.a(f, f, f);
            tessellator.a(f6, (j + 0.015625F), f8, d2, d6 + 0.0625D);
            tessellator.a(f6, (j + 0.015625F), f7, d, d6 + 0.0625D);
            tessellator.a(f5, (j + 0.015625F), f7, d, d4 + 0.0625D);
            tessellator.a(f5, (j + 0.015625F), f8, d2, d4 + 0.0625D);
        }
        if (!this.c.h(i, j + 1, k)) {
            double d1 = ((j1 + 16) / 256.0F);
            double d3 = (((j1 + 16) + 15.99F) / 256.0F);
            double d5 = (k1 / 256.0F);
            double d7 = ((k1 + 15.99F) / 256.0F);
            if (this.c.h(i - 1, j, k) && this.c.a(i - 1, j + 1, k) == Tile.aw.bn) {
                tessellator.a(f * f2, f * f3, f * f4);
                tessellator.a((i + 0.015625F), ((j + 1) + 0.021875F), (k + 1), d3, d5);
                tessellator.a((i + 0.015625F), (j + 0), (k + 1), d1, d5);
                tessellator.a((i + 0.015625F), (j + 0), (k + 0), d1, d7);
                tessellator.a((i + 0.015625F), ((j + 1) + 0.021875F), (k + 0), d3, d7);
                tessellator.a(f, f, f);
                tessellator.a((i + 0.015625F), ((j + 1) + 0.021875F), (k + 1), d3, d5 + 0.0625D);
                tessellator.a((i + 0.015625F), (j + 0), (k + 1), d1, d5 + 0.0625D);
                tessellator.a((i + 0.015625F), (j + 0), (k + 0), d1, d7 + 0.0625D);
                tessellator.a((i + 0.015625F), ((j + 1) + 0.021875F), (k + 0), d3, d7 + 0.0625D);
            }
            if (this.c.h(i + 1, j, k) && this.c.a(i + 1, j + 1, k) == Tile.aw.bn) {
                tessellator.a(f * f2, f * f3, f * f4);
                tessellator.a(((i + 1) - 0.015625F), (j + 0), (k + 1), d1, d7);
                tessellator.a(((i + 1) - 0.015625F), ((j + 1) + 0.021875F), (k + 1), d3, d7);
                tessellator.a(((i + 1) - 0.015625F), ((j + 1) + 0.021875F), (k + 0), d3, d5);
                tessellator.a(((i + 1) - 0.015625F), (j + 0), (k + 0), d1, d5);
                tessellator.a(f, f, f);
                tessellator.a(((i + 1) - 0.015625F), (j + 0), (k + 1), d1, d7 + 0.0625D);
                tessellator.a(((i + 1) - 0.015625F), ((j + 1) + 0.021875F), (k + 1), d3, d7 + 0.0625D);
                tessellator.a(((i + 1) - 0.015625F), ((j + 1) + 0.021875F), (k + 0), d3, d5 + 0.0625D);
                tessellator.a(((i + 1) - 0.015625F), (j + 0), (k + 0), d1, d5 + 0.0625D);
            }
            if (this.c.h(i, j, k - 1) && this.c.a(i, j + 1, k - 1) == Tile.aw.bn) {
                tessellator.a(f * f2, f * f3, f * f4);
                tessellator.a((i + 1), (j + 0), (k + 0.015625F), d1, d7);
                tessellator.a((i + 1), ((j + 1) + 0.021875F), (k + 0.015625F), d3, d7);
                tessellator.a((i + 0), ((j + 1) + 0.021875F), (k + 0.015625F), d3, d5);
                tessellator.a((i + 0), (j + 0), (k + 0.015625F), d1, d5);
                tessellator.a(f, f, f);
                tessellator.a((i + 1), (j + 0), (k + 0.015625F), d1, d7 + 0.0625D);
                tessellator.a((i + 1), ((j + 1) + 0.021875F), (k + 0.015625F), d3, d7 + 0.0625D);
                tessellator.a((i + 0), ((j + 1) + 0.021875F), (k + 0.015625F), d3, d5 + 0.0625D);
                tessellator.a((i + 0), (j + 0), (k + 0.015625F), d1, d5 + 0.0625D);
            }
            if (this.c.h(i, j, k + 1) && this.c.a(i, j + 1, k + 1) == Tile.aw.bn) {
                tessellator.a(f * f2, f * f3, f * f4);
                tessellator.a((i + 1), ((j + 1) + 0.021875F), ((k + 1) - 0.015625F), d3, d5);
                tessellator.a((i + 1), (j + 0), ((k + 1) - 0.015625F), d1, d5);
                tessellator.a((i + 0), (j + 0), ((k + 1) - 0.015625F), d1, d7);
                tessellator.a((i + 0), ((j + 1) + 0.021875F), ((k + 1) - 0.015625F), d3, d7);
                tessellator.a(f, f, f);
                tessellator.a((i + 1), ((j + 1) + 0.021875F), ((k + 1) - 0.015625F), d3, d5 + 0.0625D);
                tessellator.a((i + 1), (j + 0), ((k + 1) - 0.015625F), d1, d5 + 0.0625D);
                tessellator.a((i + 0), (j + 0), ((k + 1) - 0.015625F), d1, d7 + 0.0625D);
                tessellator.a((i + 0), ((j + 1) + 0.021875F), ((k + 1) - 0.015625F), d3, d7 + 0.0625D);
            }
        }
        return true;
    }

    public boolean a(pc blockrail, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        int l = this.c.e(i, j, k);
        int i1 = blockrail.a(0, l);
        if (this.d >= 0)
            i1 = this.d;
        if (blockrail.i())
            l &= 0x7;
        float f = blockrail.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        int j1 = (i1 & 0xF) << 4;
        int k1 = i1 & 0xF0;
        double d = (j1 / 256.0F);
        double d1 = ((j1 + 15.99F) / 256.0F);
        double d2 = (k1 / 256.0F);
        double d3 = ((k1 + 15.99F) / 256.0F);
        float f1 = 0.0625F;
        float f2 = (i + 1);
        float f3 = (i + 1);
        float f4 = (i + 0);
        float f5 = (i + 0);
        float f6 = (k + 0);
        float f7 = (k + 1);
        float f8 = (k + 1);
        float f9 = (k + 0);
        float f10 = j + f1;
        float f11 = j + f1;
        float f12 = j + f1;
        float f13 = j + f1;
        if (l == 1 || l == 2 || l == 3 || l == 7) {
            f2 = f5 = (i + 1);
            f3 = f4 = (i + 0);
            f6 = f7 = (k + 1);
            f8 = f9 = (k + 0);
        } else if (l == 8) {
            f2 = f3 = (i + 0);
            f4 = f5 = (i + 1);
            f6 = f9 = (k + 1);
            f7 = f8 = (k + 0);
        } else if (l == 9) {
            f2 = f5 = (i + 0);
            f3 = f4 = (i + 1);
            f6 = f7 = (k + 0);
            f8 = f9 = (k + 1);
        }
        if (l == 2 || l == 4) {
            f10++;
            f13++;
        } else if (l == 3 || l == 5) {
            f11++;
            f12++;
        }
        tessellator.a(f2, f10, f6, d1, d2);
        tessellator.a(f3, f11, f7, d1, d3);
        tessellator.a(f4, f12, f8, d, d3);
        tessellator.a(f5, f13, f9, d, d2);
        tessellator.a(f5, f13, f9, d, d2);
        tessellator.a(f4, f12, f8, d, d3);
        tessellator.a(f3, f11, f7, d1, d3);
        tessellator.a(f2, f10, f6, d1, d2);
        return true;
    }

    public boolean h(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        int m = this.c.e(i, j, k);
        int l = block.a(0, m);
        if (this.d >= 0)
            l = this.d;
        float f = block.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        int i1 = (l & 0xF) << 4;
        int j1 = l & 0xF0;
        double d = (i1 / 256.0F);
        double d1 = ((i1 + 15.99F) / 256.0F);
        double d2 = (j1 / 256.0F);
        double d3 = ((j1 + 15.99F) / 256.0F);
        int k1 = m % 4 + 2;
        float f1 = 0.0F;
        float f2 = 0.025F;
        if (k1 == 5) {
            tessellator.a((i + f2), ((j + 1) + f1), ((k + 1) + f1), d, d2);
            tessellator.a((i + f2), ((j + 0) - f1), ((k + 1) + f1), d, d3);
            tessellator.a((i + f2), ((j + 0) - f1), ((k + 0) - f1), d1, d3);
            tessellator.a((i + f2), ((j + 1) + f1), ((k + 0) - f1), d1, d2);
            tessellator.a((i + f2), ((j + 0) - f1), ((k + 1) + f1), d, d3);
            tessellator.a((i + f2), ((j + 1) + f1), ((k + 1) + f1), d, d2);
            tessellator.a((i + f2), ((j + 1) + f1), ((k + 0) - f1), d1, d2);
            tessellator.a((i + f2), ((j + 0) - f1), ((k + 0) - f1), d1, d3);
        }
        if (k1 == 4) {
            tessellator.a(((i + 1) - f2), ((j + 0) - f1), ((k + 1) + f1), d1, d3);
            tessellator.a(((i + 1) - f2), ((j + 1) + f1), ((k + 1) + f1), d1, d2);
            tessellator.a(((i + 1) - f2), ((j + 1) + f1), ((k + 0) - f1), d, d2);
            tessellator.a(((i + 1) - f2), ((j + 0) - f1), ((k + 0) - f1), d, d3);
            tessellator.a(((i + 1) - f2), ((j + 0) - f1), ((k + 0) - f1), d, d3);
            tessellator.a(((i + 1) - f2), ((j + 1) + f1), ((k + 0) - f1), d, d2);
            tessellator.a(((i + 1) - f2), ((j + 1) + f1), ((k + 1) + f1), d1, d2);
            tessellator.a(((i + 1) - f2), ((j + 0) - f1), ((k + 1) + f1), d1, d3);
        }
        if (k1 == 3) {
            tessellator.a(((i + 1) + f1), ((j + 0) - f1), (k + f2), d1, d3);
            tessellator.a(((i + 1) + f1), ((j + 1) + f1), (k + f2), d1, d2);
            tessellator.a(((i + 0) - f1), ((j + 1) + f1), (k + f2), d, d2);
            tessellator.a(((i + 0) - f1), ((j + 0) - f1), (k + f2), d, d3);
            tessellator.a(((i + 0) - f1), ((j + 0) - f1), (k + f2), d, d3);
            tessellator.a(((i + 0) - f1), ((j + 1) + f1), (k + f2), d, d2);
            tessellator.a(((i + 1) + f1), ((j + 1) + f1), (k + f2), d1, d2);
            tessellator.a(((i + 1) + f1), ((j + 0) - f1), (k + f2), d1, d3);
        }
        if (k1 == 2) {
            tessellator.a(((i + 1) + f1), ((j + 1) + f1), ((k + 1) - f2), d, d2);
            tessellator.a(((i + 1) + f1), ((j + 0) - f1), ((k + 1) - f2), d, d3);
            tessellator.a(((i + 0) - f1), ((j + 0) - f1), ((k + 1) - f2), d1, d3);
            tessellator.a(((i + 0) - f1), ((j + 1) + f1), ((k + 1) - f2), d1, d2);
            tessellator.a(((i + 0) - f1), ((j + 1) + f1), ((k + 1) - f2), d1, d2);
            tessellator.a(((i + 0) - f1), ((j + 0) - f1), ((k + 1) - f2), d1, d3);
            tessellator.a(((i + 1) + f1), ((j + 0) - f1), ((k + 1) - f2), d, d3);
            tessellator.a(((i + 1) + f1), ((j + 1) + f1), ((k + 1) - f2), d, d2);
        }
        return true;
    }

    public boolean i(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        int l = block.b(this.c, i, j, k);
        float f1 = (l >> 16 & 0xFF) / 255.0F;
        float f2 = (l >> 8 & 0xFF) / 255.0F;
        float f3 = (l & 0xFF) / 255.0F;
        if (GameRenderer.a) {
            float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        tessellator.a(f * f1, f * f2, f * f3);
        double d = i;
        double d1 = j;
        double d2 = k;
        if (block == Tile.Y) {
            long l1 = (i * 3129871) ^ k * 116129781L ^ j;
            l1 = l1 * l1 * 42317861L + l1 * 11L;
            d += (((float) (l1 >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
            d1 += (((float) (l1 >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
            d2 += (((float) (l1 >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
        }
        a(block, this.c.e(i, j, k), d, d1, d2);
        return true;
    }

    public boolean j(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        b(block, this.c.e(i, j, k), i, (j - 0.0625F), k);
        return true;
    }

    public void a(Tile block, double d, double d1, double d2, double d3, double d4) {
        Tessellator tessellator = Tessellator.a;
        int i = block.a(0);
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        float f = j / 256.0F;
        float f1 = (j + 15.99F) / 256.0F;
        float f2 = k / 256.0F;
        float f3 = (k + 15.99F) / 256.0F;
        double d5 = f + 0.02734375D;
        double d6 = f2 + 0.0234375D;
        double d7 = f + 0.03515625D;
        double d8 = f2 + 0.03125D;
        d += 0.5D;
        d2 += 0.5D;
        double d9 = d - 0.5D;
        double d10 = d + 0.5D;
        double d11 = d2 - 0.5D;
        double d12 = d2 + 0.5D;
        double d13 = 0.0625D;
        double d14 = 0.625D;
        tessellator.a(d + d3 * (1.0D - d14) - d13, d1 + d14, d2 + d4 * (1.0D - d14) - d13, d5, d6);
        tessellator.a(d + d3 * (1.0D - d14) - d13, d1 + d14, d2 + d4 * (1.0D - d14) + d13, d5, d8);
        tessellator.a(d + d3 * (1.0D - d14) + d13, d1 + d14, d2 + d4 * (1.0D - d14) + d13, d7, d8);
        tessellator.a(d + d3 * (1.0D - d14) + d13, d1 + d14, d2 + d4 * (1.0D - d14) - d13, d7, d6);
        tessellator.a(d - d13, d1 + 1.0D, d11, f, f2);
        tessellator.a(d - d13 + d3, d1 + 0.0D, d11 + d4, f, f3);
        tessellator.a(d - d13 + d3, d1 + 0.0D, d12 + d4, f1, f3);
        tessellator.a(d - d13, d1 + 1.0D, d12, f1, f2);
        tessellator.a(d + d13, d1 + 1.0D, d12, f, f2);
        tessellator.a(d + d3 + d13, d1 + 0.0D, d12 + d4, f, f3);
        tessellator.a(d + d3 + d13, d1 + 0.0D, d11 + d4, f1, f3);
        tessellator.a(d + d13, d1 + 1.0D, d11, f1, f2);
        tessellator.a(d9, d1 + 1.0D, d2 + d13, f, f2);
        tessellator.a(d9 + d3, d1 + 0.0D, d2 + d13 + d4, f, f3);
        tessellator.a(d10 + d3, d1 + 0.0D, d2 + d13 + d4, f1, f3);
        tessellator.a(d10, d1 + 1.0D, d2 + d13, f1, f2);
        tessellator.a(d10, d1 + 1.0D, d2 - d13, f, f2);
        tessellator.a(d10 + d3, d1 + 0.0D, d2 - d13 + d4, f, f3);
        tessellator.a(d9 + d3, d1 + 0.0D, d2 - d13 + d4, f1, f3);
        tessellator.a(d9, d1 + 1.0D, d2 - d13, f1, f2);
    }

    public void a(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.a;
        int j = block.a(0, i);
        if (this.d >= 0)
            j = this.d;
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (k / 256.0F);
        double d4 = ((k + 15.99F) / 256.0F);
        double d5 = (l / 256.0F);
        double d6 = ((l + 15.99F) / 256.0F);
        double d7 = d + 0.5D - 0.44999998807907104D;
        double d8 = d + 0.5D + 0.44999998807907104D;
        double d9 = d2 + 0.5D - 0.44999998807907104D;
        double d10 = d2 + 0.5D + 0.44999998807907104D;
        tessellator.a(d7, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d10, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d9, d4, d5);
        if (this.d < 0) {
            j = block.a(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (k / 256.0F);
            d4 = ((k + 15.99F) / 256.0F);
            d5 = (l / 256.0F);
            d6 = ((l + 15.99F) / 256.0F);
        }
        tessellator.a(d7, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d9, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d10, d4, d5);
    }

    public void renderCrossedSquaresUpsideDown(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.a;
        int j = block.a(0, i);
        if (this.d >= 0)
            j = this.d;
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (k / 256.0F);
        double d4 = ((k + 15.99F) / 256.0F);
        double d5 = (l / 256.0F);
        double d6 = ((l + 15.99F) / 256.0F);
        double d7 = d + 0.5D - 0.44999998807907104D;
        double d8 = d + 0.5D + 0.44999998807907104D;
        double d9 = d2 + 0.5D - 0.44999998807907104D;
        double d10 = d2 + 0.5D + 0.44999998807907104D;
        tessellator.a(d7, d1 + 0.0D, d9, d3, d5);
        tessellator.a(d7, d1 + 1.0D, d9, d3, d6);
        tessellator.a(d8, d1 + 1.0D, d10, d4, d6);
        tessellator.a(d8, d1 + 0.0D, d10, d4, d5);
        tessellator.a(d8, d1 + 0.0D, d10, d3, d5);
        tessellator.a(d8, d1 + 1.0D, d10, d3, d6);
        tessellator.a(d7, d1 + 1.0D, d9, d4, d6);
        tessellator.a(d7, d1 + 0.0D, d9, d4, d5);
        if (this.d < 0) {
            j = block.a(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (k / 256.0F);
            d4 = ((k + 15.99F) / 256.0F);
            d5 = (l / 256.0F);
            d6 = ((l + 15.99F) / 256.0F);
        }
        tessellator.a(d7, d1 + 0.0D, d10, d3, d5);
        tessellator.a(d7, d1 + 1.0D, d10, d3, d6);
        tessellator.a(d8, d1 + 1.0D, d9, d4, d6);
        tessellator.a(d8, d1 + 0.0D, d9, d4, d5);
        tessellator.a(d8, d1 + 0.0D, d9, d3, d5);
        tessellator.a(d8, d1 + 1.0D, d9, d3, d6);
        tessellator.a(d7, d1 + 1.0D, d10, d4, d6);
        tessellator.a(d7, d1 + 0.0D, d10, d4, d5);
    }

    public void renderCrossedSquaresEast(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.a;
        int j = block.a(0, i);
        if (this.d >= 0)
            j = this.d;
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (k / 256.0F);
        double d4 = ((k + 15.99F) / 256.0F);
        double d5 = (l / 256.0F);
        double d6 = ((l + 15.99F) / 256.0F);
        double d7 = d1 + 0.5D - 0.44999998807907104D;
        double d8 = d1 + 0.5D + 0.44999998807907104D;
        double d9 = d2 + 0.5D - 0.44999998807907104D;
        double d10 = d2 + 0.5D + 0.44999998807907104D;
        tessellator.a(d + 1.0D, d7, d9, d3, d5);
        tessellator.a(d + 0.0D, d7, d9, d3, d6);
        tessellator.a(d + 0.0D, d8, d10, d4, d6);
        tessellator.a(d + 1.0D, d8, d10, d4, d5);
        tessellator.a(d + 1.0D, d8, d10, d3, d5);
        tessellator.a(d + 0.0D, d8, d10, d3, d6);
        tessellator.a(d + 0.0D, d7, d9, d4, d6);
        if (this.d < 0) {
            j = block.a(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (k / 256.0F);
            d4 = ((k + 15.99F) / 256.0F);
            d5 = (l / 256.0F);
            d6 = ((l + 15.99F) / 256.0F);
        }
        tessellator.a(d + 1.0D, d7, d9, d4, d5);
        tessellator.a(d + 1.0D, d7, d10, d3, d5);
        tessellator.a(d + 0.0D, d7, d10, d3, d6);
        tessellator.a(d + 0.0D, d8, d9, d4, d6);
        tessellator.a(d + 1.0D, d8, d9, d4, d5);
        tessellator.a(d + 1.0D, d8, d9, d3, d5);
        tessellator.a(d + 0.0D, d8, d9, d3, d6);
        tessellator.a(d + 0.0D, d7, d10, d4, d6);
        tessellator.a(d + 1.0D, d7, d10, d4, d5);
    }

    public void renderCrossedSquaresWest(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.a;
        int j = block.a(0, i);
        if (this.d >= 0)
            j = this.d;
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (k / 256.0F);
        double d4 = ((k + 15.99F) / 256.0F);
        double d5 = (l / 256.0F);
        double d6 = ((l + 15.99F) / 256.0F);
        double d7 = d1 + 0.5D - 0.44999998807907104D;
        double d8 = d1 + 0.5D + 0.44999998807907104D;
        double d9 = d2 + 0.5D - 0.44999998807907104D;
        double d10 = d2 + 0.5D + 0.44999998807907104D;
        tessellator.a(d + 0.0D, d7, d9, d3, d5);
        tessellator.a(d + 1.0D, d7, d9, d3, d6);
        tessellator.a(d + 1.0D, d8, d10, d4, d6);
        tessellator.a(d + 0.0D, d8, d10, d4, d5);
        tessellator.a(d + 0.0D, d8, d10, d3, d5);
        tessellator.a(d + 1.0D, d8, d10, d3, d6);
        tessellator.a(d + 1.0D, d7, d9, d4, d6);
        tessellator.a(d + 0.0D, d7, d9, d4, d5);
        if (this.d < 0) {
            j = block.a(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (k / 256.0F);
            d4 = ((k + 15.99F) / 256.0F);
            d5 = (l / 256.0F);
            d6 = ((l + 15.99F) / 256.0F);
        }
        tessellator.a(d + 0.0D, d7, d10, d3, d5);
        tessellator.a(d + 1.0D, d7, d10, d3, d6);
        tessellator.a(d + 1.0D, d8, d9, d4, d6);
        tessellator.a(d + 0.0D, d8, d9, d4, d5);
        tessellator.a(d + 0.0D, d8, d9, d3, d5);
        tessellator.a(d + 1.0D, d8, d9, d3, d6);
        tessellator.a(d + 1.0D, d7, d10, d4, d6);
        tessellator.a(d + 0.0D, d7, d10, d4, d5);
    }

    public void renderCrossedSquaresNorth(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.a;
        int j = block.a(0, i);
        if (this.d >= 0)
            j = this.d;
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (k / 256.0F);
        double d4 = ((k + 15.99F) / 256.0F);
        double d5 = (l / 256.0F);
        double d6 = ((l + 15.99F) / 256.0F);
        double d7 = d1 + 0.5D - 0.44999998807907104D;
        double d8 = d1 + 0.5D + 0.44999998807907104D;
        double d9 = d + 0.5D - 0.44999998807907104D;
        double d10 = d + 0.5D + 0.44999998807907104D;
        tessellator.a(d9, d7, d2 + 1.0D, d3, d5);
        tessellator.a(d9, d7, d2 + 0.0D, d3, d6);
        tessellator.a(d10, d8, d2 + 0.0D, d4, d6);
        tessellator.a(d10, d8, d2 + 1.0D, d4, d5);
        tessellator.a(d10, d8, d2 + 1.0D, d3, d5);
        tessellator.a(d10, d8, d2 + 0.0D, d3, d6);
        tessellator.a(d9, d7, d2 + 0.0D, d4, d6);
        tessellator.a(d9, d7, d2 + 1.0D, d4, d5);
        if (this.d < 0) {
            j = block.a(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (k / 256.0F);
            d4 = ((k + 15.99F) / 256.0F);
            d5 = (l / 256.0F);
            d6 = ((l + 15.99F) / 256.0F);
        }
        tessellator.a(d10, d7, d2 + 1.0D, d3, d5);
        tessellator.a(d10, d7, d2 + 0.0D, d3, d6);
        tessellator.a(d9, d8, d2 + 0.0D, d4, d6);
        tessellator.a(d9, d8, d2 + 1.0D, d4, d5);
        tessellator.a(d9, d8, d2 + 1.0D, d3, d5);
        tessellator.a(d9, d8, d2 + 0.0D, d3, d6);
        tessellator.a(d10, d7, d2 + 0.0D, d4, d6);
        tessellator.a(d10, d7, d2 + 1.0D, d4, d5);
    }

    public void renderCrossedSquaresSouth(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.a;
        int j = block.a(0, i);
        if (this.d >= 0)
            j = this.d;
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (k / 256.0F);
        double d4 = ((k + 15.99F) / 256.0F);
        double d5 = (l / 256.0F);
        double d6 = ((l + 15.99F) / 256.0F);
        double d7 = d1 + 0.5D - 0.44999998807907104D;
        double d8 = d1 + 0.5D + 0.44999998807907104D;
        double d9 = d + 0.5D - 0.44999998807907104D;
        double d10 = d + 0.5D + 0.44999998807907104D;
        tessellator.a(d9, d7, d2 + 0.0D, d3, d5);
        tessellator.a(d9, d7, d2 + 1.0D, d3, d6);
        tessellator.a(d10, d8, d2 + 1.0D, d4, d6);
        tessellator.a(d10, d8, d2 + 0.0D, d4, d5);
        tessellator.a(d10, d8, d2 + 0.0D, d3, d5);
        tessellator.a(d10, d8, d2 + 1.0D, d3, d6);
        tessellator.a(d9, d7, d2 + 1.0D, d4, d6);
        tessellator.a(d9, d7, d2 + 0.0D, d4, d5);
        if (this.d < 0) {
            j = block.a(1, i);
            k = (j & 0xF) << 4;
            l = j & 0xF0;
            d3 = (k / 256.0F);
            d4 = ((k + 15.99F) / 256.0F);
            d5 = (l / 256.0F);
            d6 = ((l + 15.99F) / 256.0F);
        }
        tessellator.a(d10, d7, d2 + 0.0D, d3, d5);
        tessellator.a(d10, d7, d2 + 1.0D, d3, d6);
        tessellator.a(d9, d8, d2 + 1.0D, d4, d6);
        tessellator.a(d9, d8, d2 + 0.0D, d4, d5);
        tessellator.a(d9, d8, d2 + 0.0D, d3, d5);
        tessellator.a(d9, d8, d2 + 1.0D, d3, d6);
        tessellator.a(d10, d7, d2 + 1.0D, d4, d6);
        tessellator.a(d10, d7, d2 + 0.0D, d4, d5);
    }

    public void b(Tile block, int i, double d, double d1, double d2) {
        Tessellator tessellator = Tessellator.a;
        int j = block.a(0, i);
        if (this.d >= 0)
            j = this.d;
        int k = (j & 0xF) << 4;
        int l = j & 0xF0;
        double d3 = (k / 256.0F);
        double d4 = ((k + 15.99F) / 256.0F);
        double d5 = (l / 256.0F);
        double d6 = ((l + 15.99F) / 256.0F);
        double d7 = d + 0.5D - 0.25D;
        double d8 = d + 0.5D + 0.25D;
        double d9 = d2 + 0.5D - 0.5D;
        double d10 = d2 + 0.5D + 0.5D;
        tessellator.a(d7, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d10, d4, d5);
        tessellator.a(d7, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d9, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d9, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d10, d4, d5);
        d7 = d + 0.5D - 0.5D;
        d8 = d + 0.5D + 0.5D;
        d9 = d2 + 0.5D - 0.25D;
        d10 = d2 + 0.5D + 0.25D;
        tessellator.a(d7, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d9, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d9, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d10, d4, d5);
        tessellator.a(d7, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d10, d4, d5);
    }

    public boolean k(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        boolean flag = block.b(this.c, i, j + 1, k, 1);
        boolean flag1 = block.b(this.c, i, j - 1, k, 0);
        boolean[] aflag = new boolean[4];
        aflag[0] = block.b(this.c, i, j, k - 1, 2);
        aflag[1] = block.b(this.c, i, j, k + 1, 3);
        aflag[2] = block.b(this.c, i - 1, j, k, 4);
        aflag[3] = block.b(this.c, i + 1, j, k, 5);
        if (!flag && !flag1 && !aflag[0] && !aflag[1] && !aflag[2] && !aflag[3])
            return false;
        int color = block.b(this.c, i, j, k);
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        boolean flag2 = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        double d = 0.0D;
        double d1 = 1.0D;
        ln material = block.bA;
        int i1 = this.c.e(i, j, k);
        float f7 = a(i, j, k, material);
        float f8 = a(i, j, k + 1, material);
        float f9 = a(i + 1, j, k + 1, material);
        float f10 = a(i + 1, j, k, material);
        if (this.f || flag) {
            flag2 = true;
            int j1 = block.a(1, i1);
            float f12 = (float) rp.a(this.c, i, j, k, material);
            if (f12 > -999.0F)
                j1 = block.a(2, i1);
            int i2 = (j1 & 0xF) << 4;
            int k2 = j1 & 0xF0;
            double d2 = (i2 + 8.0D) / 256.0D;
            double d3 = (k2 + 8.0D) / 256.0D;
            if (f12 < -999.0F) {
                f12 = 0.0F;
            } else {
                d2 = ((i2 + 16) / 256.0F);
                d3 = ((k2 + 16) / 256.0F);
            }
            float f14 = MathsHelper.a(f12) * 8.0F / 256.0F;
            float f16 = MathsHelper.b(f12) * 8.0F / 256.0F;
            float f18 = block.d(this.c, i, j, k);
            tessellator.a(f4 * f18 * red, f4 * f18 * green, f4 * f18 * blue);
            tessellator.a((i + 0), (j + f7), (k + 0), d2 - f16 - f14, d3 - f16 + f14);
            tessellator.a((i + 0), (j + f8), (k + 1), d2 - f16 + f14, d3 + f16 + f14);
            tessellator.a((i + 1), (j + f9), (k + 1), d2 + f16 + f14, d3 + f16 - f14);
            tessellator.a((i + 1), (j + f10), (k + 0), d2 + f16 - f14, d3 - f16 - f14);
        }
        if (this.f || flag1) {
            float f11 = block.d(this.c, i, j - 1, k);
            tessellator.a(red * f3 * f11, green * f3 * f11, blue * f3 * f11);
            a(block, i, j, k, block.a(0));
            flag2 = true;
        }
        for (int k1 = 0; k1 < 4; k1++) {
            int l1 = i;
            int j2 = j;
            int l2 = k;
            if (k1 == 0)
                l2--;
            if (k1 == 1)
                l2++;
            if (k1 == 2)
                l1--;
            if (k1 == 3)
                l1++;
            int i3 = block.a(k1 + 2, i1);
            int j3 = (i3 & 0xF) << 4;
            int k3 = i3 & 0xF0;
            if (this.f || aflag[k1]) {
                float f13, f15, f17, f19, f20, f21;
                if (k1 == 0) {
                    f13 = f7;
                    f15 = f10;
                    f17 = i;
                    f20 = (i + 1);
                    f19 = k;
                    f21 = k;
                } else if (k1 == 1) {
                    f13 = f9;
                    f15 = f8;
                    f17 = (i + 1);
                    f20 = i;
                    f19 = (k + 1);
                    f21 = (k + 1);
                } else if (k1 == 2) {
                    f13 = f8;
                    f15 = f7;
                    f17 = i;
                    f20 = i;
                    f19 = (k + 1);
                    f21 = k;
                } else {
                    f13 = f10;
                    f15 = f9;
                    f17 = (i + 1);
                    f20 = (i + 1);
                    f19 = k;
                    f21 = (k + 1);
                }
                flag2 = true;
                double d4 = ((j3 + 0) / 256.0F);
                double d5 = ((j3 + 16) - 0.01D) / 256.0D;
                double d6 = ((k3 + (1.0F - f13) * 16.0F) / 256.0F);
                double d7 = ((k3 + (1.0F - f15) * 16.0F) / 256.0F);
                double d8 = ((k3 + 16) - 0.01D) / 256.0D;
                float f22 = block.d(this.c, l1, j2, l2);
                if (k1 < 2) {
                    f22 *= f5;
                } else {
                    f22 *= f6;
                }
                tessellator.a(f4 * f22 * red, f4 * f22 * green, f4 * f22 * blue);
                tessellator.a(f17, (j + f13), f19, d4, d6);
                tessellator.a(f20, (j + f15), f21, d5, d7);
                tessellator.a(f20, (j + 0), f21, d5, d8);
                tessellator.a(f17, (j + 0), f19, d4, d8);
            }
        }
        block.bt = d;
        block.bw = d1;
        return flag2;
    }

    private float a(int i, int j, int k, ln material) {
        int l = 0;
        float f = 0.0F;
        for (int i1 = 0; i1 < 4; i1++) {
            int j1 = i - (i1 & 0x1);
            int k1 = j;
            int l1 = k - (i1 >> 1 & 0x1);
            if (this.c.f(j1, k1 + 1, l1) == material)
                return 1.0F;
            ln material1 = this.c.f(j1, k1, l1);
            if (material1 == material) {
                int i2 = this.c.e(j1, k1, l1);
                if (i2 >= 8 || i2 == 0) {
                    f += rp.d(i2) * 10.0F;
                    l += 10;
                }
                f += rp.d(i2);
                l++;
            } else if (!material1.a()) {
                f++;
                l++;
            }
        }
        return 1.0F - f / l;
    }

    public void a(Tile block, Level world, int i, int j, int k) {
        GL11.glTranslatef(-i, -j, -k);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        startRenderingBlocks(world);
        b(block, i, j, k);
        stopRenderingBlocks();
    }

    public void startRenderingBlocks(Level world) {
        this.c = world;
        if (Minecraft.v())
            GL11.glShadeModel(7425);
        Tessellator.a.b();
        this.f = true;
    }

    public void stopRenderingBlocks() {
        this.f = false;
        Tessellator.a.a();
        if (Minecraft.v())
            GL11.glShadeModel(7424);
        this.c = null;
    }

    public boolean l(Tile block, int i, int j, int k) {
        int l = block.b(this.c, i, j, k);
        float f = (l >> 16 & 0xFF) / 255.0F;
        float f1 = (l >> 8 & 0xFF) / 255.0F;
        float f2 = (l & 0xFF) / 255.0F;
        if (GameRenderer.a) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        if (Minecraft.v())
            return a(block, i, j, k, f, f1, f2);
        return b(block, i, j, k, f, f1, f2);
    }

    public boolean a(Tile block, int i, int j, int k, float f, float f1, float f2) {
        this.m = true;
        boolean flag = false;
        float f3 = this.n;
        float f10 = this.n;
        float f17 = this.n;
        float f24 = this.n;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = true;
        boolean flag6 = true;
        this.n = block.d(this.c, i, j, k);
        this.o = block.d(this.c, i - 1, j, k);
        this.p = block.d(this.c, i, j - 1, k);
        this.q = block.d(this.c, i, j, k - 1);
        this.r = block.d(this.c, i + 1, j, k);
        this.s = block.d(this.c, i, j + 1, k);
        this.t = block.d(this.c, i, j, k + 1);
        this.ac = Tile.r[this.c.a(i + 1, j + 1, k)];
        this.ak = Tile.r[this.c.a(i + 1, j - 1, k)];
        this.ag = Tile.r[this.c.a(i + 1, j, k + 1)];
        this.ai = Tile.r[this.c.a(i + 1, j, k - 1)];
        this.ad = Tile.r[this.c.a(i - 1, j + 1, k)];
        this.al = Tile.r[this.c.a(i - 1, j - 1, k)];
        this.af = Tile.r[this.c.a(i - 1, j, k - 1)];
        this.ah = Tile.r[this.c.a(i - 1, j, k + 1)];
        this.ae = Tile.r[this.c.a(i, j + 1, k + 1)];
        this.ab = Tile.r[this.c.a(i, j + 1, k - 1)];
        this.am = Tile.r[this.c.a(i, j - 1, k + 1)];
        this.aj = Tile.r[this.c.a(i, j - 1, k - 1)];
        boolean isGrass = (block.bn == Tile.v.bn);
        if (isGrass || this.d >= 0)
            flag1 = flag3 = flag4 = flag5 = flag6 = false;
        if (this.f || block.b(this.c, i, j - 1, k, 0)) {
            if (this.O > 0) {
                j--;
                this.v = block.d(this.c, i - 1, j, k);
                this.x = block.d(this.c, i, j, k - 1);
                this.y = block.d(this.c, i, j, k + 1);
                this.A = block.d(this.c, i + 1, j, k);
                if (this.aj || this.al) {
                    this.u = block.d(this.c, i - 1, j, k - 1);
                } else {
                    this.u = this.v;
                }
                if (this.am || this.al) {
                    this.w = block.d(this.c, i - 1, j, k + 1);
                } else {
                    this.w = this.v;
                }
                if (this.aj || this.ak) {
                    this.z = block.d(this.c, i + 1, j, k - 1);
                } else {
                    this.z = this.A;
                }
                if (this.am || this.ak) {
                    this.B = block.d(this.c, i + 1, j, k + 1);
                } else {
                    this.B = this.A;
                }
                j++;
                f4 = (this.w + this.v + this.y + this.p) / 4.0F;
                f25 = (this.y + this.p + this.B + this.A) / 4.0F;
                f18 = (this.p + this.x + this.A + this.z) / 4.0F;
                f11 = (this.v + this.u + this.p + this.x) / 4.0F;
            } else {
                f4 = f11 = f18 = f25 = this.p;
            }
            this.P = this.Q = this.R = this.S = (flag1 ? f : 1.0F) * 0.5F;
            this.T = this.U = this.V = this.W = (flag1 ? f1 : 1.0F) * 0.5F;
            this.X = this.Y = this.Z = this.aa = (flag1 ? f2 : 1.0F) * 0.5F;
            float lerpLeft = 1.0F - (float) Math.max(block.bs, 0.0D);
            float lerpRight = 1.0F - (float) Math.min(block.bv, 1.0D);
            float lerpTop = (float) Math.min(block.bx, 1.0D);
            float lerpBottom = (float) Math.max(block.bu, 0.0D);
            float topLeft = lerpTop * (lerpLeft * f4 + (1.0F - lerpLeft) * f25) + (1.0F - lerpTop) * (lerpLeft * f11 + (1.0F - lerpLeft) * f18);
            float bottomLeft = lerpBottom * (lerpLeft * f4 + (1.0F - lerpLeft) * f25) + (1.0F - lerpBottom) * (lerpLeft * f11 + (1.0F - lerpLeft) * f18);
            float topRight = lerpTop * (lerpRight * f4 + (1.0F - lerpRight) * f25) + (1.0F - lerpTop) * (lerpRight * f11 + (1.0F - lerpRight) * f18);
            float bottomRight = lerpBottom * (lerpRight * f4 + (1.0F - lerpRight) * f25) + (1.0F - lerpBottom) * (lerpRight * f11 + (1.0F - lerpRight) * f18);
            float f4 = topLeft;
            float f25 = topRight;
            float f11 = bottomLeft;
            float f18 = bottomRight;
            this.P *= f4;
            this.T *= f4;
            this.X *= f4;
            this.Q *= f11;
            this.U *= f11;
            this.Y *= f11;
            this.R *= f18;
            this.V *= f18;
            this.Z *= f18;
            this.S *= f25;
            this.W *= f25;
            this.aa *= f25;
            a(block, i, j, k, block.a(this.c, i, j, k, 0));
            flag = true;
        }
        if (this.f || block.b(this.c, i, j + 1, k, 1)) {
            if (this.O > 0) {
                j++;
                this.D = block.d(this.c, i - 1, j, k);
                this.H = block.d(this.c, i + 1, j, k);
                this.F = block.d(this.c, i, j, k - 1);
                this.I = block.d(this.c, i, j, k + 1);
                if (this.ab || this.ad) {
                    this.C = block.d(this.c, i - 1, j, k - 1);
                } else {
                    this.C = this.D;
                }
                if (this.ab || this.ac) {
                    this.G = block.d(this.c, i + 1, j, k - 1);
                } else {
                    this.G = this.H;
                }
                if (this.ae || this.ad) {
                    this.E = block.d(this.c, i - 1, j, k + 1);
                } else {
                    this.E = this.D;
                }
                if (this.ae || this.ac) {
                    this.J = block.d(this.c, i + 1, j, k + 1);
                } else {
                    this.J = this.H;
                }
                j--;
                f26 = (this.E + this.D + this.I + this.s) / 4.0F;
                f5 = (this.I + this.s + this.J + this.H) / 4.0F;
                f12 = (this.s + this.F + this.H + this.G) / 4.0F;
                f19 = (this.D + this.C + this.s + this.F) / 4.0F;
            } else {
                f5 = f12 = f19 = f26 = this.s;
            }
            this.P = this.Q = this.R = this.S = flag2 ? f : 1.0F;
            this.T = this.U = this.V = this.W = flag2 ? f1 : 1.0F;
            this.X = this.Y = this.Z = this.aa = flag2 ? f2 : 1.0F;
            float lerpLeft = 1.0F - (float) Math.max(block.bs, 0.0D);
            float lerpRight = 1.0F - (float) Math.min(block.bv, 1.0D);
            float lerpTop = (float) Math.max(block.bu, 0.0D);
            float lerpBottom = (float) Math.min(block.bx, 1.0D);
            float topLeft = lerpTop * (lerpLeft * f26 + (1.0F - lerpLeft) * f5) + (1.0F - lerpTop) * (lerpLeft * f19 + (1.0F - lerpLeft) * f12);
            float topRight = lerpTop * (lerpRight * f26 + (1.0F - lerpRight) * f5) + (1.0F - lerpTop) * (lerpRight * f19 + (1.0F - lerpRight) * f12);
            float bottomLeft = lerpBottom * (lerpLeft * f26 + (1.0F - lerpLeft) * f5) + (1.0F - lerpBottom) * (lerpLeft * f19 + (1.0F - lerpLeft) * f12);
            float bottomRight = lerpBottom * (lerpRight * f26 + (1.0F - lerpRight) * f5) + (1.0F - lerpBottom) * (lerpRight * f19 + (1.0F - lerpRight) * f12);
            float f19 = topLeft;
            float f26 = bottomLeft;
            float f5 = bottomRight;
            float f12 = topRight;
            this.P *= f5;
            this.T *= f5;
            this.X *= f5;
            this.Q *= f12;
            this.U *= f12;
            this.Y *= f12;
            this.R *= f19;
            this.V *= f19;
            this.Z *= f19;
            this.S *= f26;
            this.W *= f26;
            this.aa *= f26;
            b(block, i, j, k, block.a(this.c, i, j, k, 1));
            flag = true;
        }
        if (this.f || block.b(this.c, i, j, k - 1, 2)) {
            if (this.O > 0) {
                k--;
                this.K = block.d(this.c, i - 1, j, k);
                this.x = block.d(this.c, i, j - 1, k);
                this.F = block.d(this.c, i, j + 1, k);
                this.L = block.d(this.c, i + 1, j, k);
                if (this.af || this.aj) {
                    this.u = block.d(this.c, i - 1, j - 1, k);
                } else {
                    this.u = this.K;
                }
                if (this.af || this.ab) {
                    this.C = block.d(this.c, i - 1, j + 1, k);
                } else {
                    this.C = this.K;
                }
                if (this.ai || this.aj) {
                    this.z = block.d(this.c, i + 1, j - 1, k);
                } else {
                    this.z = this.L;
                }
                if (this.ai || this.ab) {
                    this.G = block.d(this.c, i + 1, j + 1, k);
                } else {
                    this.G = this.L;
                }
                k++;
                f6 = (this.K + this.C + this.q + this.F) / 4.0F;
                f13 = (this.q + this.F + this.L + this.G) / 4.0F;
                f20 = (this.x + this.q + this.z + this.L) / 4.0F;
                f27 = (this.u + this.K + this.x + this.q) / 4.0F;
            } else {
                f6 = f13 = f20 = f27 = this.q;
            }
            this.P = this.Q = this.R = this.S = (flag3 ? f : 1.0F) * 0.8F;
            this.T = this.U = this.V = this.W = (flag3 ? f1 : 1.0F) * 0.8F;
            this.X = this.Y = this.Z = this.aa = (flag3 ? f2 : 1.0F) * 0.8F;
            float lerpLeft = (float) Math.min(block.bv, 1.0D);
            float lerpRight = (float) Math.max(block.bs, 0.0D);
            float lerpTop = (float) Math.min(block.bw, 1.0D);
            float lerpBottom = (float) Math.max(block.bt, 0.0D);
            float topLeft = lerpTop * (lerpLeft * f13 + (1.0F - lerpLeft) * f6) + (1.0F - lerpTop) * (lerpLeft * f20 + (1.0F - lerpLeft) * f27);
            float bottomLeft = lerpBottom * (lerpLeft * f13 + (1.0F - lerpLeft) * f6) + (1.0F - lerpBottom) * (lerpLeft * f20 + (1.0F - lerpLeft) * f27);
            float topRight = lerpTop * (lerpRight * f13 + (1.0F - lerpRight) * f6) + (1.0F - lerpTop) * (lerpRight * f20 + (1.0F - lerpRight) * f27);
            float bottomRight = lerpBottom * (lerpRight * f13 + (1.0F - lerpRight) * f6) + (1.0F - lerpBottom) * (lerpRight * f20 + (1.0F - lerpRight) * f27);
            float f13 = topLeft;
            float f20 = bottomLeft;
            float f27 = bottomRight;
            float f6 = topRight;
            this.P *= f6;
            this.T *= f6;
            this.X *= f6;
            this.Q *= f13;
            this.U *= f13;
            this.Y *= f13;
            this.R *= f20;
            this.V *= f20;
            this.Z *= f20;
            this.S *= f27;
            this.W *= f27;
            this.aa *= f27;
            int l = block.a(this.c, i, j, k, 2);
            c(block, i, j, k, l);
            if (a && isGrass && this.d < 0) {
                this.P *= f;
                this.Q *= f;
                this.R *= f;
                this.S *= f;
                this.T *= f1;
                this.U *= f1;
                this.V *= f1;
                this.W *= f1;
                this.X *= f2;
                this.Y *= f2;
                this.Z *= f2;
                this.aa *= f2;
                c(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.f || block.b(this.c, i, j, k + 1, 3)) {
            if (this.O > 0) {
                k++;
                this.M = block.d(this.c, i - 1, j, k);
                this.N = block.d(this.c, i + 1, j, k);
                this.y = block.d(this.c, i, j - 1, k);
                this.I = block.d(this.c, i, j + 1, k);
                if (this.ah || this.am) {
                    this.w = block.d(this.c, i - 1, j - 1, k);
                } else {
                    this.w = this.M;
                }
                if (this.ah || this.ae) {
                    this.E = block.d(this.c, i - 1, j + 1, k);
                } else {
                    this.E = this.M;
                }
                if (this.ag || this.am) {
                    this.B = block.d(this.c, i + 1, j - 1, k);
                } else {
                    this.B = this.N;
                }
                if (this.ag || this.ae) {
                    this.J = block.d(this.c, i + 1, j + 1, k);
                } else {
                    this.J = this.N;
                }
                k--;
                f7 = (this.M + this.E + this.t + this.I) / 4.0F;
                f28 = (this.t + this.I + this.N + this.J) / 4.0F;
                f21 = (this.y + this.t + this.B + this.N) / 4.0F;
                f14 = (this.w + this.M + this.y + this.t) / 4.0F;
            } else {
                f7 = f14 = f21 = f28 = this.t;
            }
            this.P = this.Q = this.R = this.S = (flag4 ? f : 1.0F) * 0.8F;
            this.T = this.U = this.V = this.W = (flag4 ? f1 : 1.0F) * 0.8F;
            this.X = this.Y = this.Z = this.aa = (flag4 ? f2 : 1.0F) * 0.8F;
            float lerpLeft = (float) Math.min(1.0D - block.bs, 1.0D);
            float lerpRight = (float) Math.max(1.0D - block.bv, 0.0D);
            float lerpTop = (float) Math.min(block.bw, 1.0D);
            float lerpBottom = (float) Math.max(block.bt, 0.0D);
            float topLeft = lerpTop * (lerpLeft * f7 + (1.0F - lerpLeft) * f28) + (1.0F - lerpTop) * (lerpLeft * f14 + (1.0F - lerpLeft) * f21);
            float bottomLeft = lerpBottom * (lerpLeft * f7 + (1.0F - lerpLeft) * f28) + (1.0F - lerpBottom) * (lerpLeft * f14 + (1.0F - lerpLeft) * f21);
            float topRight = lerpTop * (lerpRight * f7 + (1.0F - lerpRight) * f28) + (1.0F - lerpTop) * (lerpRight * f14 + (1.0F - lerpRight) * f21);
            float bottomRight = lerpBottom * (lerpRight * f7 + (1.0F - lerpRight) * f28) + (1.0F - lerpBottom) * (lerpRight * f14 + (1.0F - lerpRight) * f21);
            float f7 = topLeft;
            float f14 = bottomLeft;
            float f21 = bottomRight;
            float f28 = topRight;
            this.P *= f7;
            this.T *= f7;
            this.X *= f7;
            this.Q *= f14;
            this.U *= f14;
            this.Y *= f14;
            this.R *= f21;
            this.V *= f21;
            this.Z *= f21;
            this.S *= f28;
            this.W *= f28;
            this.aa *= f28;
            int i1 = block.a(this.c, i, j, k, 3);
            d(block, i, j, k, block.a(this.c, i, j, k, 3));
            if (a && isGrass && this.d < 0) {
                this.P *= f;
                this.Q *= f;
                this.R *= f;
                this.S *= f;
                this.T *= f1;
                this.U *= f1;
                this.V *= f1;
                this.W *= f1;
                this.X *= f2;
                this.Y *= f2;
                this.Z *= f2;
                this.aa *= f2;
                d(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.f || block.b(this.c, i - 1, j, k, 4)) {
            if (this.O > 0) {
                i--;
                this.v = block.d(this.c, i, j - 1, k);
                this.K = block.d(this.c, i, j, k - 1);
                this.M = block.d(this.c, i, j, k + 1);
                this.D = block.d(this.c, i, j + 1, k);
                if (this.af || this.al) {
                    this.u = block.d(this.c, i, j - 1, k - 1);
                } else {
                    this.u = this.K;
                }
                if (this.ah || this.al) {
                    this.w = block.d(this.c, i, j - 1, k + 1);
                } else {
                    this.w = this.M;
                }
                if (this.af || this.ad) {
                    this.C = block.d(this.c, i, j + 1, k - 1);
                } else {
                    this.C = this.K;
                }
                if (this.ah || this.ad) {
                    this.E = block.d(this.c, i, j + 1, k + 1);
                } else {
                    this.E = this.M;
                }
                i++;
                f29 = (this.v + this.w + this.o + this.M) / 4.0F;
                f8 = (this.o + this.M + this.D + this.E) / 4.0F;
                f15 = (this.K + this.o + this.C + this.D) / 4.0F;
                f22 = (this.u + this.v + this.K + this.o) / 4.0F;
            } else {
                f8 = f15 = f22 = f29 = this.o;
            }
            this.P = this.Q = this.R = this.S = (flag5 ? f : 1.0F) * 0.6F;
            this.T = this.U = this.V = this.W = (flag5 ? f1 : 1.0F) * 0.6F;
            this.X = this.Y = this.Z = this.aa = (flag5 ? f2 : 1.0F) * 0.6F;
            float lerpLeft = (float) Math.min(1.0D - block.bu, 1.0D);
            float lerpRight = (float) Math.max(1.0D - block.bx, 0.0D);
            float lerpTop = (float) Math.min(block.bw, 1.0D);
            float lerpBottom = (float) Math.max(block.bt, 0.0D);
            float topLeft = lerpTop * (lerpLeft * f15 + (1.0F - lerpLeft) * f8) + (1.0F - lerpTop) * (lerpLeft * f22 + (1.0F - lerpLeft) * f29);
            float bottomLeft = lerpBottom * (lerpLeft * f15 + (1.0F - lerpLeft) * f8) + (1.0F - lerpBottom) * (lerpLeft * f22 + (1.0F - lerpLeft) * f29);
            float topRight = lerpTop * (lerpRight * f15 + (1.0F - lerpRight) * f8) + (1.0F - lerpTop) * (lerpRight * f22 + (1.0F - lerpRight) * f29);
            float bottomRight = lerpBottom * (lerpRight * f15 + (1.0F - lerpRight) * f8) + (1.0F - lerpBottom) * (lerpRight * f22 + (1.0F - lerpRight) * f29);
            float f15 = topLeft;
            float f22 = bottomLeft;
            float f29 = bottomRight;
            float f8 = topRight;
            this.P *= f8;
            this.T *= f8;
            this.X *= f8;
            this.Q *= f15;
            this.U *= f15;
            this.Y *= f15;
            this.R *= f22;
            this.V *= f22;
            this.Z *= f22;
            this.S *= f29;
            this.W *= f29;
            this.aa *= f29;
            int j1 = block.a(this.c, i, j, k, 4);
            e(block, i, j, k, j1);
            if (a && isGrass && this.d < 0) {
                this.P *= f;
                this.Q *= f;
                this.R *= f;
                this.S *= f;
                this.T *= f1;
                this.U *= f1;
                this.V *= f1;
                this.W *= f1;
                this.X *= f2;
                this.Y *= f2;
                this.Z *= f2;
                this.aa *= f2;
                e(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.f || block.b(this.c, i + 1, j, k, 5)) {
            if (this.O > 0) {
                i++;
                this.A = block.d(this.c, i, j - 1, k);
                this.L = block.d(this.c, i, j, k - 1);
                this.N = block.d(this.c, i, j, k + 1);
                this.H = block.d(this.c, i, j + 1, k);
                if (this.ak || this.ai) {
                    this.z = block.d(this.c, i, j - 1, k - 1);
                } else {
                    this.z = this.L;
                }
                if (this.ak || this.ag) {
                    this.B = block.d(this.c, i, j - 1, k + 1);
                } else {
                    this.B = this.N;
                }
                if (this.ac || this.ai) {
                    this.G = block.d(this.c, i, j + 1, k - 1);
                } else {
                    this.G = this.L;
                }
                if (this.ac || this.ag) {
                    this.J = block.d(this.c, i, j + 1, k + 1);
                } else {
                    this.J = this.N;
                }
                i--;
                f9 = (this.A + this.B + this.r + this.N) / 4.0F;
                f30 = (this.r + this.N + this.H + this.J) / 4.0F;
                f23 = (this.L + this.r + this.G + this.H) / 4.0F;
                f16 = (this.z + this.A + this.L + this.r) / 4.0F;
            } else {
                f9 = f16 = f23 = f30 = this.r;
            }
            this.P = this.Q = this.R = this.S = (flag6 ? f : 1.0F) * 0.6F;
            this.T = this.U = this.V = this.W = (flag6 ? f1 : 1.0F) * 0.6F;
            this.X = this.Y = this.Z = this.aa = (flag6 ? f2 : 1.0F) * 0.6F;
            float lerpLeft = (float) Math.min(1.0D - block.bu, 1.0D);
            float lerpRight = (float) Math.max(1.0D - block.bx, 0.0D);
            float lerpTop = (float) Math.min(block.bw, 1.0D);
            float lerpBottom = (float) Math.max(block.bt, 0.0D);
            float topLeft = lerpTop * (lerpLeft * f23 + (1.0F - lerpLeft) * f30) + (1.0F - lerpTop) * (lerpLeft * f16 + (1.0F - lerpLeft) * f9);
            float bottomLeft = lerpBottom * (lerpLeft * f23 + (1.0F - lerpLeft) * f30) + (1.0F - lerpBottom) * (lerpLeft * f16 + (1.0F - lerpLeft) * f9);
            float topRight = lerpTop * (lerpRight * f23 + (1.0F - lerpRight) * f30) + (1.0F - lerpTop) * (lerpRight * f16 + (1.0F - lerpRight) * f9);
            float bottomRight = lerpBottom * (lerpRight * f23 + (1.0F - lerpRight) * f30) + (1.0F - lerpBottom) * (lerpRight * f16 + (1.0F - lerpRight) * f9);
            float f9 = bottomRight;
            float f16 = bottomLeft;
            float f23 = topLeft;
            float f30 = topRight;
            this.P *= f9;
            this.T *= f9;
            this.X *= f9;
            this.Q *= f16;
            this.U *= f16;
            this.Y *= f16;
            this.R *= f23;
            this.V *= f23;
            this.Z *= f23;
            this.S *= f30;
            this.W *= f30;
            this.aa *= f30;
            int k1 = block.a(this.c, i, j, k, 5);
            f(block, i, j, k, k1);
            if (a && isGrass && this.d < 0) {
                this.P *= f;
                this.Q *= f;
                this.R *= f;
                this.S *= f;
                this.T *= f1;
                this.U *= f1;
                this.V *= f1;
                this.W *= f1;
                this.X *= f2;
                this.Y *= f2;
                this.Z *= f2;
                this.aa *= f2;
                f(block, i, j, k, 38);
            }
            flag = true;
        }
        this.m = false;
        return flag;
    }

    public boolean b(Tile block, int i, int j, int k, float f, float f1, float f2) {
        this.m = false;
        Tessellator tessellator = Tessellator.a;
        boolean flag = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f4 * f;
        float f8 = f4 * f1;
        float f9 = f4 * f2;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;
        boolean isGrass = (block == Tile.v);
        if (!isGrass) {
            f10 *= f;
            f11 *= f;
            f12 *= f;
            f13 *= f1;
            f14 *= f1;
            f15 *= f1;
            f16 *= f2;
            f17 *= f2;
            f18 *= f2;
        }
        float f19 = block.d(this.c, i, j, k);
        if (this.f || block.b(this.c, i, j - 1, k, 0)) {
            float f20 = block.d(this.c, i, j - 1, k);
            tessellator.a(f10 * f20, f13 * f20, f16 * f20);
            a(block, i, j, k, block.a(this.c, i, j, k, 0));
            flag = true;
        }
        if (this.f || block.b(this.c, i, j + 1, k, 1)) {
            float f21 = block.d(this.c, i, j + 1, k);
            if (block.bw != 1.0D && !block.bA.d())
                f21 = f19;
            tessellator.a(f7 * f21, f8 * f21, f9 * f21);
            b(block, i, j, k, block.a(this.c, i, j, k, 1));
            flag = true;
        }
        if (this.f || block.b(this.c, i, j, k - 1, 2)) {
            float f22 = block.d(this.c, i, j, k - 1);
            if (block.bu > 0.0D)
                f22 = f19;
            tessellator.a(f11 * f22, f14 * f22, f17 * f22);
            int l = block.a(this.c, i, j, k, 2);
            c(block, i, j, k, l);
            if (a && isGrass && this.d < 0) {
                tessellator.a(f11 * f22 * f, f14 * f22 * f1, f17 * f22 * f2);
                c(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.f || block.b(this.c, i, j, k + 1, 3)) {
            float f23 = block.d(this.c, i, j, k + 1);
            if (block.bx < 1.0D)
                f23 = f19;
            tessellator.a(f11 * f23, f14 * f23, f17 * f23);
            int i1 = block.a(this.c, i, j, k, 3);
            d(block, i, j, k, i1);
            if (a && isGrass && this.d < 0) {
                tessellator.a(f11 * f23 * f, f14 * f23 * f1, f17 * f23 * f2);
                d(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.f || block.b(this.c, i - 1, j, k, 4)) {
            float f24 = block.d(this.c, i - 1, j, k);
            if (block.bs > 0.0D)
                f24 = f19;
            tessellator.a(f12 * f24, f15 * f24, f18 * f24);
            int j1 = block.a(this.c, i, j, k, 4);
            e(block, i, j, k, j1);
            if (a && isGrass && this.d < 0) {
                tessellator.a(f12 * f24 * f, f15 * f24 * f1, f18 * f24 * f2);
                e(block, i, j, k, 38);
            }
            flag = true;
        }
        if (this.f || block.b(this.c, i + 1, j, k, 5)) {
            float f25 = block.d(this.c, i + 1, j, k);
            if (block.bv < 1.0D)
                f25 = f19;
            tessellator.a(f12 * f25, f15 * f25, f18 * f25);
            int k1 = block.a(this.c, i, j, k, 5);
            f(block, i, j, k, k1);
            if (a && isGrass && this.d < 0) {
                tessellator.a(f12 * f25 * f, f15 * f25 * f1, f18 * f25 * f2);
                f(block, i, j, k, 38);
            }
            flag = true;
        }
        return flag;
    }

    public boolean m(Tile block, int i, int j, int k) {
        int l = block.b(this.c, i, j, k);
        float f = (l >> 16 & 0xFF) / 255.0F;
        float f1 = (l >> 8 & 0xFF) / 255.0F;
        float f2 = (l & 0xFF) / 255.0F;
        if (GameRenderer.a) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        return c(block, i, j, k, f, f1, f2);
    }

    public boolean c(Tile block, int i, int j, int k, float f, float f1, float f2) {
        Tessellator tessellator = Tessellator.a;
        boolean flag = false;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f3 * f;
        float f8 = f4 * f;
        float f9 = f5 * f;
        float f10 = f6 * f;
        float f11 = f3 * f1;
        float f12 = f4 * f1;
        float f13 = f5 * f1;
        float f14 = f6 * f1;
        float f15 = f3 * f2;
        float f16 = f4 * f2;
        float f17 = f5 * f2;
        float f18 = f6 * f2;
        float f19 = 0.0625F;
        float f20 = block.d(this.c, i, j, k);
        if (this.f || block.b(this.c, i, j - 1, k, 0)) {
            float f21 = block.d(this.c, i, j - 1, k);
            tessellator.a(f7 * f21, f11 * f21, f15 * f21);
            a(block, i, j, k, block.a(this.c, i, j, k, 0));
            flag = true;
        }
        if (this.f || block.b(this.c, i, j + 1, k, 1)) {
            float f22 = block.d(this.c, i, j + 1, k);
            if (block.bw != 1.0D && !block.bA.d())
                f22 = f20;
            tessellator.a(f8 * f22, f12 * f22, f16 * f22);
            b(block, i, j, k, block.a(this.c, i, j, k, 1));
            flag = true;
        }
        if (this.f || block.b(this.c, i, j, k - 1, 2)) {
            float f23 = block.d(this.c, i, j, k - 1);
            if (block.bu > 0.0D)
                f23 = f20;
            tessellator.a(f9 * f23, f13 * f23, f17 * f23);
            tessellator.c(0.0F, 0.0F, f19);
            c(block, i, j, k, block.a(this.c, i, j, k, 2));
            tessellator.c(0.0F, 0.0F, -f19);
            flag = true;
        }
        if (this.f || block.b(this.c, i, j, k + 1, 3)) {
            float f24 = block.d(this.c, i, j, k + 1);
            if (block.bx < 1.0D)
                f24 = f20;
            tessellator.a(f9 * f24, f13 * f24, f17 * f24);
            tessellator.c(0.0F, 0.0F, -f19);
            d(block, i, j, k, block.a(this.c, i, j, k, 3));
            tessellator.c(0.0F, 0.0F, f19);
            flag = true;
        }
        if (this.f || block.b(this.c, i - 1, j, k, 4)) {
            float f25 = block.d(this.c, i - 1, j, k);
            if (block.bs > 0.0D)
                f25 = f20;
            tessellator.a(f10 * f25, f14 * f25, f18 * f25);
            tessellator.c(f19, 0.0F, 0.0F);
            e(block, i, j, k, block.a(this.c, i, j, k, 4));
            tessellator.c(-f19, 0.0F, 0.0F);
            flag = true;
        }
        if (this.f || block.b(this.c, i + 1, j, k, 5)) {
            float f26 = block.d(this.c, i + 1, j, k);
            if (block.bv < 1.0D)
                f26 = f20;
            tessellator.a(f10 * f26, f14 * f26, f18 * f26);
            tessellator.c(-f19, 0.0F, 0.0F);
            f(block, i, j, k, block.a(this.c, i, j, k, 5));
            tessellator.c(f19, 0.0F, 0.0F);
            flag = true;
        }
        return flag;
    }

    public boolean n(Tile block, int i, int j, int k) {
        boolean flag = false;
        float f = 0.375F;
        float f1 = 0.625F;
        block.a(f, 0.0F, f, f1, 1.0F, f1);
        l(block, i, j, k);
        flag = true;
        boolean flag1 = false;
        boolean flag2 = false;
        if (this.c.a(i - 1, j, k) == block.bn || this.c.a(i + 1, j, k) == block.bn)
            flag1 = true;
        if (this.c.a(i, j, k - 1) == block.bn || this.c.a(i, j, k + 1) == block.bn)
            flag2 = true;
        boolean flag3 = (this.c.a(i - 1, j, k) == block.bn);
        boolean flag4 = (this.c.a(i + 1, j, k) == block.bn);
        boolean flag5 = (this.c.a(i, j, k - 1) == block.bn);
        boolean flag6 = (this.c.a(i, j, k + 1) == block.bn);
        if (!flag1 && !flag2)
            flag1 = true;
        f = 0.4375F;
        f1 = 0.5625F;
        float f2 = 0.75F;
        float f3 = 0.9375F;
        float f4 = flag3 ? 0.0F : f;
        float f5 = flag4 ? 1.0F : f1;
        float f6 = flag5 ? 0.0F : f;
        float f7 = flag6 ? 1.0F : f1;
        if (flag1) {
            block.a(f4, f2, f, f5, f3, f1);
            l(block, i, j, k);
            flag = true;
        }
        if (flag2) {
            block.a(f, f2, f6, f1, f3, f7);
            l(block, i, j, k);
            flag = true;
        }
        f2 = 0.375F;
        f3 = 0.5625F;
        if (flag1) {
            block.a(f4, f2, f, f5, f3, f1);
            l(block, i, j, k);
            flag = true;
        }
        if (flag2) {
            block.a(f, f2, f6, f1, f3, f7);
            l(block, i, j, k);
            flag = true;
        }
        f = (f - 0.5F) * 0.707F + 0.5F;
        f1 = (f1 - 0.5F) * 0.707F + 0.5F;
        if (this.c.a(i - 1, j, k + 1) == block.bn && !flag6 && !flag3) {
            Tessellator tessellator = Tessellator.a;
            int texture = block.a(this.c, i, j, k, 0);
            int u = (texture & 0xF) << 4;
            int v = texture & 0xF0;
            double u1 = u / 256.0D;
            double u2 = (u + 16.0D - 0.01D) / 256.0D;
            double v1 = (v + 16.0D * f3 - 1.0D) / 256.0D;
            double v2 = (v + 16.0D * f2 - 1.0D - 0.01D) / 256.0D;
            float b1 = this.c.c(i, j, k);
            float b2 = this.c.c(i - 1, j, k + 1);
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f1 + i), (f2 + j), (f1 + k), u1, v2);
            tessellator.a((f1 + i), (f3 + j), (f1 + k), u1, v1);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f1 + i - 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a((f1 + i - 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f + i - 1.0F), (f2 + j), (f + k + 1.0F), u2, v2);
            tessellator.a((f + i - 1.0F), (f3 + j), (f + k + 1.0F), u2, v1);
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f + i), (f3 + j), (f + k), u1, v1);
            tessellator.a((f + i), (f2 + j), (f + k), u1, v2);
            v1 = (v + 16.0D * f3) / 256.0D;
            v2 = (v + 16.0D * f3 + 2.0D - 0.01D) / 256.0D;
            tessellator.a(b2 * 0.5F, b2 * 0.5F, b2 * 0.5F);
            tessellator.a((f1 + i - 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a((f + i - 1.0F), (f2 + j), (f + k + 1.0F), u2, v2);
            tessellator.a(b1 * 0.5F, b1 * 0.5F, b1 * 0.5F);
            tessellator.a((f + i), (f2 + j), (f + k), u1, v2);
            tessellator.a((f1 + i), (f2 + j), (f1 + k), u1, v1);
            tessellator.a(b2, b2, b2);
            tessellator.a((f + i - 1.0F), (f3 + j), (f + k + 1.0F), u2, v1);
            tessellator.a((f1 + i - 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a(b1, b1, b1);
            tessellator.a((f1 + i), (f3 + j), (f1 + k), u1, v2);
            tessellator.a((f + i), (f3 + j), (f + k), u1, v1);
            f2 = 0.75F;
            f3 = 0.9375F;
            v1 = (v + 16.0D * f3 - 1.0D) / 256.0D;
            v2 = (v + 16.0D * f2 - 1.0D - 0.01D) / 256.0D;
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f1 + i), (f2 + j), (f1 + k), u1, v2);
            tessellator.a((f1 + i), (f3 + j), (f1 + k), u1, v1);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f1 + i - 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a((f1 + i - 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f + i - 1.0F), (f2 + j), (f + k + 1.0F), u2, v2);
            tessellator.a((f + i - 1.0F), (f3 + j), (f + k + 1.0F), u2, v1);
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f + i), (f3 + j), (f + k), u1, v1);
            tessellator.a((f + i), (f2 + j), (f + k), u1, v2);
            v1 = (v + 16.0D * f3) / 256.0D;
            v2 = (v + 16.0D * f3 - 2.0D - 0.01D) / 256.0D;
            tessellator.a(b2 * 0.5F, b2 * 0.5F, b2 * 0.5F);
            tessellator.a((f1 + i - 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a((f + i - 1.0F), (f2 + j), (f + k + 1.0F), u2, v2);
            tessellator.a(b1 * 0.5F, b1 * 0.5F, b1 * 0.5F);
            tessellator.a((f + i), (f2 + j), (f + k), u1, v2);
            tessellator.a((f1 + i), (f2 + j), (f1 + k), u1, v1);
            tessellator.a(b2, b2, b2);
            tessellator.a((f + i - 1.0F), (f3 + j), (f + k + 1.0F), u2, v1);
            tessellator.a((f1 + i - 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a(b1, b1, b1);
            tessellator.a((f1 + i), (f3 + j), (f1 + k), u1, v2);
            tessellator.a((f + i), (f3 + j), (f + k), u1, v1);
        }
        if (this.c.a(i + 1, j, k + 1) == block.bn && !flag6 && !flag4) {
            f2 = 0.375F;
            f3 = 0.5625F;
            Tessellator tessellator = Tessellator.a;
            int texture = block.a(this.c, i, j, k, 0);
            int u = (texture & 0xF) << 4;
            int v = texture & 0xF0;
            double u1 = u / 256.0D;
            double u2 = (u + 16.0D - 0.01D) / 256.0D;
            double v1 = (v + 16.0D * f3 - 1.0D) / 256.0D;
            double v2 = (v + 16.0D * f2 - 1.0D - 0.01D) / 256.0D;
            float b1 = this.c.c(i, j, k);
            float b2 = this.c.c(i - 1, j, k + 1);
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f1 + i), (f2 + j), (f + k), u1, v2);
            tessellator.a((f1 + i), (f3 + j), (f + k), u1, v1);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f1 + i + 1.0F), (f3 + j), (f + k + 1.0F), u2, v1);
            tessellator.a((f1 + i + 1.0F), (f2 + j), (f + k + 1.0F), u2, v2);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f + i + 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a((f + i + 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f + i), (f3 + j), (f1 + k), u1, v1);
            tessellator.a((f + i), (f2 + j), (f1 + k), u1, v2);
            v1 = (v + 16.0D * f3) / 256.0D;
            v2 = (v + 16.0D * f3 + 2.0D - 0.01D) / 256.0D;
            tessellator.a(b2 * 0.5F, b2 * 0.5F, b2 * 0.5F);
            tessellator.a((f1 + i + 1.0F), (f2 + j), (f + k + 1.0F), u2, v1);
            tessellator.a((f + i + 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a(b1 * 0.5F, b1 * 0.5F, b1 * 0.5F);
            tessellator.a((f + i), (f2 + j), (f1 + k), u1, v2);
            tessellator.a((f1 + i), (f2 + j), (f + k), u1, v1);
            tessellator.a(b2, b2, b2);
            tessellator.a((f + i + 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a((f1 + i + 1.0F), (f3 + j), (f + k + 1.0F), u2, v2);
            tessellator.a(b1, b1, b1);
            tessellator.a((f1 + i), (f3 + j), (f + k), u1, v2);
            tessellator.a((f + i), (f3 + j), (f1 + k), u1, v1);
            f2 = 0.75F;
            f3 = 0.9375F;
            v1 = (v + 16.0D * f3 - 1.0D) / 256.0D;
            v2 = (v + 16.0D * f2 - 1.0D - 0.01D) / 256.0D;
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f1 + i), (f2 + j), (f + k), u1, v2);
            tessellator.a((f1 + i), (f3 + j), (f + k), u1, v1);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f1 + i + 1.0F), (f3 + j), (f + k + 1.0F), u2, v1);
            tessellator.a((f1 + i + 1.0F), (f2 + j), (f + k + 1.0F), u2, v2);
            tessellator.a(b2 * 0.7F, b2 * 0.7F, b2 * 0.7F);
            tessellator.a((f + i + 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a((f + i + 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a(b1 * 0.7F, b1 * 0.7F, b1 * 0.7F);
            tessellator.a((f + i), (f3 + j), (f1 + k), u1, v1);
            tessellator.a((f + i), (f2 + j), (f1 + k), u1, v2);
            v1 = (v + 16.0D * f3) / 256.0D;
            v2 = (v + 16.0D * f3 - 2.0D - 0.01D) / 256.0D;
            tessellator.a(b2 * 0.5F, b2 * 0.5F, b2 * 0.5F);
            tessellator.a((f1 + i + 1.0F), (f2 + j), (f + k + 1.0F), u2, v1);
            tessellator.a((f + i + 1.0F), (f2 + j), (f1 + k + 1.0F), u2, v2);
            tessellator.a(b1 * 0.5F, b1 * 0.5F, b1 * 0.5F);
            tessellator.a((f + i), (f2 + j), (f1 + k), u1, v2);
            tessellator.a((f1 + i), (f2 + j), (f + k), u1, v1);
            tessellator.a(b2, b2, b2);
            tessellator.a((f + i + 1.0F), (f3 + j), (f1 + k + 1.0F), u2, v1);
            tessellator.a((f1 + i + 1.0F), (f3 + j), (f + k + 1.0F), u2, v2);
            tessellator.a(b1, b1, b1);
            tessellator.a((f1 + i), (f3 + j), (f + k), u1, v2);
            tessellator.a((f + i), (f3 + j), (f1 + k), u1, v1);
        }
        block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return flag;
    }

    public boolean o(Tile block, int i, int j, int k) {
        boolean flag = false;
        int l = this.c.e(i, j, k) & 0x3;
        block.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        l(block, i, j, k);
        if (l == 0) {
            Tile b = Tile.m[this.c.a(i - 1, j, k)];
            if (b != null && b.b() == 10) {
                int n = this.c.e(i - 1, j, k) & 0x3;
                if (n == 2) {
                    block.a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    l(block, i, j, k);
                } else if (n == 3) {
                    block.a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    l(block, i, j, k);
                }
            }
            int m = this.c.e(i + 1, j, k) & 0x3;
            b = Tile.m[this.c.a(i + 1, j, k)];
            if (b != null && b.b() == 10 && (m == 2 || m == 3)) {
                if (m == 2) {
                    block.a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    l(block, i, j, k);
                } else if (m == 3) {
                    block.a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    l(block, i, j, k);
                }
            } else {
                block.a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                l(block, i, j, k);
            }
            flag = true;
        } else if (l == 1) {
            int m = this.c.e(i - 1, j, k) & 0x3;
            Tile b = Tile.m[this.c.a(i - 1, j, k)];
            if (b != null && b.b() == 10 && (m == 2 || m == 3)) {
                if (m == 3) {
                    block.a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    l(block, i, j, k);
                } else {
                    block.a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    l(block, i, j, k);
                }
            } else {
                block.a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 1.0F);
                l(block, i, j, k);
            }
            b = Tile.m[this.c.a(i + 1, j, k)];
            if (b != null && b.b() == 10) {
                m = this.c.e(i + 1, j, k) & 0x3;
                if (m == 2) {
                    block.a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    l(block, i, j, k);
                } else if (m == 3) {
                    block.a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    l(block, i, j, k);
                }
            }
            flag = true;
        } else if (l == 2) {
            Tile b = Tile.m[this.c.a(i, j, k - 1)];
            if (b != null && b.b() == 10) {
                int n = this.c.e(i, j, k - 1) & 0x3;
                if (n == 1) {
                    block.a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    l(block, i, j, k);
                } else if (n == 0) {
                    block.a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    l(block, i, j, k);
                }
            }
            int m = this.c.e(i, j, k + 1) & 0x3;
            b = Tile.m[this.c.a(i, j, k + 1)];
            if (b != null && b.b() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    block.a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    l(block, i, j, k);
                } else {
                    block.a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    l(block, i, j, k);
                }
            } else {
                block.a(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                l(block, i, j, k);
            }
            flag = true;
        } else if (l == 3) {
            Tile b = Tile.m[this.c.a(i, j, k + 1)];
            if (b != null && b.b() == 10) {
                int n = this.c.e(i, j, k + 1) & 0x3;
                if (n == 1) {
                    block.a(0.0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
                    l(block, i, j, k);
                } else if (n == 0) {
                    block.a(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
                    l(block, i, j, k);
                }
            }
            int m = this.c.e(i, j, k - 1) & 0x3;
            b = Tile.m[this.c.a(i, j, k - 1)];
            if (b != null && b.b() == 10 && (m == 0 || m == 1)) {
                if (m == 0) {
                    block.a(0.5F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                    l(block, i, j, k);
                } else {
                    block.a(0.0F, 0.5F, 0.0F, 0.5F, 1.0F, 0.5F);
                    l(block, i, j, k);
                }
            } else {
                block.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 0.5F);
                l(block, i, j, k);
            }
            flag = true;
        }
        block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return flag;
    }

    public boolean renderBlockSlope(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        int l = this.c.e(i, j, k) & 0x3;
        int texture = block.a(this.c, i, j, k, 0);
        int u = (texture & 0xF) << 4;
        int v = texture & 0xF0;
        double u1 = u / 256.0D;
        double u2 = (u + 16.0D - 0.01D) / 256.0D;
        double v1 = v / 256.0D;
        double v2 = (v + 16.0D - 0.01D) / 256.0D;
        float b = block.d(this.c, i, j, k);
        tessellator.a(0.5F * b, 0.5F * b, 0.5F * b);
        block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        tessellator.a(i, j, k, u1, v1);
        tessellator.a((i + 1), j, k, u2, v1);
        tessellator.a((i + 1), j, (k + 1), u2, v2);
        tessellator.a(i, j, (k + 1), u1, v2);
        if (l == 0) {
            Tile nB = Tile.m[this.c.a(i - 1, j, k)];
            int m = this.c.e(i - 1, j, k) & 0x3;
            if (nB != null && nB.b() == 38 && (m == 2 || m == 3)) {
                if (m == 2) {
                    tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a(i, j, k, u2, v2);
                } else if (m == 3) {
                    tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                }
                tessellator.a(i, j, k, u1, v2);
                tessellator.a(i, j, (k + 1), u2, v2);
                tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                tessellator.a((i + 1), (j + 1), k, u1, v1);
                tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                tessellator.a((i + 1), j, k, u1, v2);
                tessellator.a((i + 1), (j + 1), k, u1, v1);
                tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                tessellator.a((i + 1), j, (k + 1), u2, v2);
            } else {
                m = this.c.e(i + 1, j, k) & 0x3;
                nB = Tile.m[this.c.a(i + 1, j, k)];
                if (nB != null && nB.b() == 38 && (m == 2 || m == 3)) {
                    if (m == 2) {
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a((i + 1), j, k, u1, v2);
                        tessellator.a(i, j, k, u2, v2);
                        tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                        tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, j, k, u1, v2);
                        tessellator.a(i, j, (k + 1), u2, v2);
                        tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                        tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                        tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                        tessellator.a(i, j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                    } else if (m == 3) {
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a(i, j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                        tessellator.a((i + 1), (j + 1), k, u2, v1);
                        tessellator.a((i + 1), (j + 1), k, u2, v1);
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, j, k, u1, v2);
                        tessellator.a(i, j, (k + 1), u2, v2);
                        tessellator.a((i + 1), (j + 1), k, u1, v1);
                        tessellator.a((i + 1), (j + 1), k, u1, v1);
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a((i + 1), (j + 1), k, u1, v1);
                        tessellator.a((i + 1), j, k, u1, v2);
                        tessellator.a(i, j, k, u2, v2);
                        tessellator.a(i, j, k, u2, v2);
                    }
                } else {
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                }
            }
        } else if (l == 1) {
            Tile nB = Tile.m[this.c.a(i + 1, j, k)];
            int m = this.c.e(i + 1, j, k) & 0x3;
            if (nB != null && nB.b() == 38 && (m == 2 || m == 3)) {
                if (m == 2) {
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), k, u2, v1);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a(i, j, k, u2, v2);
                } else {
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                }
                tessellator.a(i, (j + 1), k, u2, v1);
                tessellator.a(i, (j + 1), (k + 1), u1, v1);
                tessellator.a((i + 1), j, (k + 1), u1, v2);
                tessellator.a((i + 1), j, k, u2, v2);
                tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                tessellator.a(i, j, k, u1, v2);
                tessellator.a(i, j, (k + 1), u2, v2);
                tessellator.a(i, (j + 1), (k + 1), u2, v1);
                tessellator.a(i, (j + 1), k, u1, v1);
            } else {
                m = this.c.e(i - 1, j, k) & 0x3;
                nB = Tile.m[this.c.a(i - 1, j, k)];
                if (nB != null && nB.b() == 38 && (m == 2 || m == 3)) {
                    if (m == 3) {
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a(i, j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                        tessellator.a(i, (j + 1), k, u1, v1);
                        tessellator.a(i, (j + 1), k, u1, v1);
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, (j + 1), k, u2, v1);
                        tessellator.a(i, (j + 1), k, u2, v1);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, k, u2, v2);
                        tessellator.a(i, (j + 1), k, u2, v1);
                        tessellator.a((i + 1), j, k, u1, v2);
                        tessellator.a(i, j, k, u2, v2);
                        tessellator.a(i, j, k, u2, v2);
                    } else {
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a(i, (j + 1), (k + 1), u2, v1);
                        tessellator.a(i, (j + 1), (k + 1), u2, v1);
                        tessellator.a((i + 1), j, k, u1, v2);
                        tessellator.a(i, j, k, u2, v2);
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, (j + 1), (k + 1), u1, v1);
                        tessellator.a(i, (j + 1), (k + 1), u1, v1);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, k, u2, v2);
                        tessellator.a(i, (j + 1), (k + 1), u1, v1);
                        tessellator.a(i, j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                    }
                } else {
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), k, u2, v1);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), k, u2, v1);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a(i, j, k, u2, v2);
                }
            }
        } else if (l == 2) {
            int m = this.c.e(i, j, k - 1) & 0x3;
            Tile nB = Tile.m[this.c.a(i, j, k - 1)];
            if (nB != null && nB.b() == 38 && (m == 0 || m == 1)) {
                if (m == 1) {
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), k, u2, v1);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                } else if (m == 0) {
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, k, u1, v2);
                }
                tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                tessellator.a((i + 1), j, (k + 1), u2, v2);
                tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                tessellator.a(i, (j + 1), (k + 1), u1, v1);
                tessellator.a(i, j, (k + 1), u1, v2);
                tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                tessellator.a(i, (j + 1), (k + 1), u2, v1);
                tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                tessellator.a((i + 1), j, k, u1, v2);
                tessellator.a(i, j, k, u2, v2);
            } else {
                m = this.c.e(i, j, k + 1) & 0x3;
                nB = Tile.m[this.c.a(i, j, k + 1)];
                if (nB != null && nB.b() == 38 && (m == 0 || m == 1)) {
                    if (m == 0) {
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, j, k, u1, v2);
                        tessellator.a(i, j, (k + 1), u2, v2);
                        tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                        tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                        tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                        tessellator.a((i + 1), j, k, u1, v2);
                        tessellator.a(i, j, k, u2, v2);
                        tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                        tessellator.a((i + 1), j, k, u2, v2);
                        tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                    } else {
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, (j + 1), (k + 1), u1, v1);
                        tessellator.a(i, (j + 1), (k + 1), u1, v1);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, k, u2, v2);
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a(i, (j + 1), (k + 1), u2, v1);
                        tessellator.a(i, (j + 1), (k + 1), u2, v1);
                        tessellator.a((i + 1), j, k, u1, v2);
                        tessellator.a(i, j, k, u2, v2);
                        tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                        tessellator.a(i, j, (k + 1), u2, v2);
                        tessellator.a(i, (j + 1), (k + 1), u2, v1);
                        tessellator.a(i, j, k, u1, v2);
                        tessellator.a(i, j, k, u1, v2);
                    }
                } else {
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a(i, j, k, u2, v2);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                }
            }
        } else if (l == 3) {
            int m = this.c.e(i, j, k + 1) & 0x3;
            Tile nB = Tile.m[this.c.a(i, j, k + 1)];
            if (nB != null && nB.b() == 38 && (m == 0 || m == 1)) {
                if (m == 1) {
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), (k + 1), u2, v1);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), k, u2, v1);
                    tessellator.a(i, (j + 1), (k + 1), u1, v1);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                } else if (m == 0) {
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a((i + 1), j, k, u1, v2);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), (k + 1), u2, v1);
                    tessellator.a((i + 1), (j + 1), k, u1, v1);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, k, u1, v2);
                }
                tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                tessellator.a(i, (j + 1), k, u1, v1);
                tessellator.a((i + 1), (j + 1), k, u2, v1);
                tessellator.a((i + 1), j, k, u2, v2);
                tessellator.a(i, j, k, u1, v2);
                tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                tessellator.a(i, j, (k + 1), u1, v2);
                tessellator.a((i + 1), j, (k + 1), u2, v2);
                tessellator.a((i + 1), (j + 1), k, u2, v1);
                tessellator.a(i, (j + 1), k, u1, v1);
            } else {
                m = this.c.e(i, j, k - 1) & 0x3;
                nB = Tile.m[this.c.a(i, j, k - 1)];
                if (nB != null && nB.b() == 38 && (m == 0 || m == 1)) {
                    if (m == 0) {
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, j, k, u1, v2);
                        tessellator.a(i, j, (k + 1), u2, v2);
                        tessellator.a((i + 1), (j + 1), k, u1, v1);
                        tessellator.a((i + 1), (j + 1), k, u1, v1);
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a(i, j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                        tessellator.a((i + 1), (j + 1), k, u2, v1);
                        tessellator.a((i + 1), (j + 1), k, u2, v1);
                        tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                        tessellator.a((i + 1), j, k, u2, v2);
                        tessellator.a((i + 1), (j + 1), k, u2, v1);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                    } else {
                        tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                        tessellator.a(i, (j + 1), k, u2, v1);
                        tessellator.a(i, (j + 1), k, u2, v1);
                        tessellator.a((i + 1), j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, k, u2, v2);
                        tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                        tessellator.a(i, j, (k + 1), u1, v2);
                        tessellator.a((i + 1), j, (k + 1), u2, v2);
                        tessellator.a(i, (j + 1), k, u1, v1);
                        tessellator.a(i, (j + 1), k, u1, v1);
                        tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                        tessellator.a(i, j, (k + 1), u2, v2);
                        tessellator.a(i, (j + 1), k, u1, v1);
                        tessellator.a(i, j, k, u1, v2);
                        tessellator.a(i, j, k, u1, v2);
                    }
                } else {
                    tessellator.a(0.8F * b, 0.8F * b, 0.8F * b);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(0.9F * b, 0.9F * b, 0.9F * b);
                    tessellator.a(i, j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u2, v2);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(0.6F * b, 0.6F * b, 0.6F * b);
                    tessellator.a((i + 1), j, k, u2, v2);
                    tessellator.a((i + 1), (j + 1), k, u2, v1);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a((i + 1), j, (k + 1), u1, v2);
                    tessellator.a(i, j, (k + 1), u2, v2);
                    tessellator.a(i, (j + 1), k, u1, v1);
                    tessellator.a(i, j, k, u1, v2);
                    tessellator.a(i, j, k, u1, v2);
                }
            }
        }
        return true;
    }

    public boolean p(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        DoorTile blockdoor = (DoorTile) block;
        boolean flag = false;
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        float f4 = block.d(this.c, i, j, k);
        float f5 = block.d(this.c, i, j - 1, k);
        if (blockdoor.bt > 0.0D)
            f5 = f4;
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f5 = 1.0F;
        tessellator.a(f * f5, f * f5, f * f5);
        a(block, i, j, k, block.a(this.c, i, j, k, 0));
        flag = true;
        f5 = block.d(this.c, i, j + 1, k);
        if (blockdoor.bw < 1.0D)
            f5 = f4;
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f5 = 1.0F;
        tessellator.a(f1 * f5, f1 * f5, f1 * f5);
        b(block, i, j, k, block.a(this.c, i, j, k, 1));
        flag = true;
        f5 = block.d(this.c, i, j, k - 1);
        if (blockdoor.bu > 0.0D)
            f5 = f4;
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f5 = 1.0F;
        tessellator.a(f2 * f5, f2 * f5, f2 * f5);
        int l = block.a(this.c, i, j, k, 2);
        if (l < 0) {
            this.e = true;
            l = -l;
        }
        c(block, i, j, k, l);
        flag = true;
        this.e = false;
        f5 = block.d(this.c, i, j, k + 1);
        if (blockdoor.bx < 1.0D)
            f5 = f4;
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f5 = 1.0F;
        tessellator.a(f2 * f5, f2 * f5, f2 * f5);
        l = block.a(this.c, i, j, k, 3);
        if (l < 0) {
            this.e = true;
            l = -l;
        }
        d(block, i, j, k, l);
        flag = true;
        this.e = false;
        f5 = block.d(this.c, i - 1, j, k);
        if (blockdoor.bs > 0.0D)
            f5 = f4;
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f5 = 1.0F;
        tessellator.a(f3 * f5, f3 * f5, f3 * f5);
        l = block.a(this.c, i, j, k, 4);
        if (l < 0) {
            this.e = true;
            l = -l;
        }
        e(block, i, j, k, l);
        flag = true;
        this.e = false;
        f5 = block.d(this.c, i + 1, j, k);
        if (blockdoor.bv < 1.0D)
            f5 = f4;
        if (block.getBlockLightValue(this.c, i, j, k) > 0)
            f5 = 1.0F;
        tessellator.a(f3 * f5, f3 * f5, f3 * f5);
        l = block.a(this.c, i, j, k, 5);
        if (l < 0) {
            this.e = true;
            l = -l;
        }
        f(block, i, j, k, l);
        flag = true;
        this.e = false;
        return flag;
    }

    public void a(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.a;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = (j + block.bs * 16.0D) / 256.0D;
        double d4 = (j + block.bv * 16.0D - 0.01D) / 256.0D;
        double d5 = (k + block.bu * 16.0D) / 256.0D;
        double d6 = (k + block.bx * 16.0D - 0.01D) / 256.0D;
        if (block.bs < 0.0D || block.bv > 1.0D) {
            d3 = ((j + 0.0F) / 256.0F);
            d4 = ((j + 15.99F) / 256.0F);
        }
        if (block.bu < 0.0D || block.bx > 1.0D) {
            d5 = ((k + 0.0F) / 256.0F);
            d6 = ((k + 15.99F) / 256.0F);
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if (this.l == 2) {
            d3 = (j + block.bu * 16.0D) / 256.0D;
            d5 = ((k + 16) - block.bv * 16.0D) / 256.0D;
            d4 = (j + block.bx * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bs * 16.0D) / 256.0D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else if (this.l == 1) {
            d3 = ((j + 16) - block.bx * 16.0D) / 256.0D;
            d5 = (k + block.bs * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bu * 16.0D) / 256.0D;
            d6 = (k + block.bv * 16.0D) / 256.0D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d3 = d7;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else if (this.l == 3) {
            d3 = ((j + 16) - block.bs * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bv * 16.0D - 0.01D) / 256.0D;
            d5 = ((k + 16) - block.bu * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bx * 16.0D - 0.01D) / 256.0D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }
        double d11 = d + block.bs;
        double d12 = d + block.bv;
        double d13 = d1 + block.bt;
        double d14 = d2 + block.bu;
        double d15 = d2 + block.bx;
        if (this.m) {
            tessellator.a(this.P, this.T, this.X);
            tessellator.a(d11, d13, d15, d8, d10);
            tessellator.a(this.Q, this.U, this.Y);
            tessellator.a(d11, d13, d14, d3, d5);
            tessellator.a(this.R, this.V, this.Z);
            tessellator.a(d12, d13, d14, d7, d9);
            tessellator.a(this.S, this.W, this.aa);
            tessellator.a(d12, d13, d15, d4, d6);
        } else {
            tessellator.a(d11, d13, d15, d8, d10);
            tessellator.a(d11, d13, d14, d3, d5);
            tessellator.a(d12, d13, d14, d7, d9);
            tessellator.a(d12, d13, d15, d4, d6);
        }
    }

    public void b(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.a;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = (j + block.bs * 16.0D) / 256.0D;
        double d4 = (j + block.bv * 16.0D - 0.01D) / 256.0D;
        double d5 = (k + block.bu * 16.0D) / 256.0D;
        double d6 = (k + block.bx * 16.0D - 0.01D) / 256.0D;
        if (block.bs < 0.0D || block.bv > 1.0D) {
            d3 = ((j + 0.0F) / 256.0F);
            d4 = ((j + 15.99F) / 256.0F);
        }
        if (block.bu < 0.0D || block.bx > 1.0D) {
            d5 = ((k + 0.0F) / 256.0F);
            d6 = ((k + 15.99F) / 256.0F);
        }
        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if (this.k == 1) {
            d3 = (j + block.bu * 16.0D) / 256.0D;
            d5 = ((k + 16) - block.bv * 16.0D) / 256.0D;
            d4 = (j + block.bx * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bs * 16.0D) / 256.0D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else if (this.k == 2) {
            d3 = ((j + 16) - block.bx * 16.0D) / 256.0D;
            d5 = (k + block.bs * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bu * 16.0D) / 256.0D;
            d6 = (k + block.bv * 16.0D) / 256.0D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
            d3 = d7;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else if (this.k == 3) {
            d3 = ((j + 16) - block.bs * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bv * 16.0D - 0.01D) / 256.0D;
            d5 = ((k + 16) - block.bu * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bx * 16.0D - 0.01D) / 256.0D;
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }
        double d11 = d + block.bs;
        double d12 = d + block.bv;
        double d13 = d1 + block.bw;
        double d14 = d2 + block.bu;
        double d15 = d2 + block.bx;
        if (this.m) {
            tessellator.a(this.P, this.T, this.X);
            tessellator.a(d12, d13, d15, d4, d6);
            tessellator.a(this.Q, this.U, this.Y);
            tessellator.a(d12, d13, d14, d7, d9);
            tessellator.a(this.R, this.V, this.Z);
            tessellator.a(d11, d13, d14, d3, d5);
            tessellator.a(this.S, this.W, this.aa);
            tessellator.a(d11, d13, d15, d8, d10);
        } else {
            tessellator.a(d12, d13, d15, d4, d6);
            tessellator.a(d12, d13, d14, d7, d9);
            tessellator.a(d11, d13, d14, d3, d5);
            tessellator.a(d11, d13, d15, d8, d10);
        }
    }

    public void c(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.a;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = (j + block.bs * 16.0D) / 256.0D;
        double d4 = (j + block.bv * 16.0D - 0.01D) / 256.0D;
        double d5 = ((k + 16) - block.bw * 16.0D) / 256.0D;
        double d6 = ((k + 16) - block.bt * 16.0D - 0.01D) / 256.0D;
        if (this.e) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.bs < 0.0D || block.bv > 1.0D) {
            d3 = ((j + 0.0F) / 256.0F);
            d4 = ((j + 15.99F) / 256.0F);
        }
        if (block.bt < 0.0D || block.bw > 1.0D) {
            d5 = ((k + 0.0F) / 256.0F);
            d6 = ((k + 15.99F) / 256.0F);
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.g == 2) {
            d3 = (j + block.bt * 16.0D) / 256.0D;
            d5 = ((k + 16) - block.bs * 16.0D) / 256.0D;
            d4 = (j + block.bw * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bv * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.g == 1) {
            d3 = ((j + 16) - block.bw * 16.0D) / 256.0D;
            d5 = (k + block.bv * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bt * 16.0D) / 256.0D;
            d6 = (k + block.bs * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.g == 3) {
            d3 = ((j + 16) - block.bs * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bv * 16.0D - 0.01D) / 256.0D;
            d5 = (k + block.bw * 16.0D) / 256.0D;
            d6 = (k + block.bt * 16.0D - 0.01D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.bs;
        double d13 = d + block.bv;
        double d14 = d1 + block.bt;
        double d15 = d1 + block.bw;
        double d16 = d2 + block.bu;
        if (this.m) {
            tessellator.a(this.P, this.T, this.X);
            tessellator.a(d12, d15, d16, d8, d10);
            tessellator.a(this.Q, this.U, this.Y);
            tessellator.a(d13, d15, d16, d3, d5);
            tessellator.a(this.R, this.V, this.Z);
            tessellator.a(d13, d14, d16, d9, d11);
            tessellator.a(this.S, this.W, this.aa);
            tessellator.a(d12, d14, d16, d4, d6);
        } else {
            tessellator.a(d12, d15, d16, d8, d10);
            tessellator.a(d13, d15, d16, d3, d5);
            tessellator.a(d13, d14, d16, d9, d11);
            tessellator.a(d12, d14, d16, d4, d6);
        }
    }

    public void d(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.a;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = (j + block.bs * 16.0D) / 256.0D;
        double d4 = (j + block.bv * 16.0D - 0.01D) / 256.0D;
        double d5 = ((k + 16) - block.bw * 16.0D) / 256.0D;
        double d6 = ((k + 16) - block.bt * 16.0D - 0.01D) / 256.0D;
        if (this.e) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.bs < 0.0D || block.bv > 1.0D) {
            d3 = ((j + 0.0F) / 256.0F);
            d4 = ((j + 15.99F) / 256.0F);
        }
        if (block.bt < 0.0D || block.bw > 1.0D) {
            d5 = ((k + 0.0F) / 256.0F);
            d6 = ((k + 15.99F) / 256.0F);
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.h == 1) {
            d3 = (j + block.bt * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bs * 16.0D) / 256.0D;
            d4 = (j + block.bw * 16.0D) / 256.0D;
            d5 = ((k + 16) - block.bv * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.h == 2) {
            d3 = ((j + 16) - block.bw * 16.0D) / 256.0D;
            d5 = (k + block.bs * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bt * 16.0D) / 256.0D;
            d6 = (k + block.bv * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.h == 3) {
            d3 = ((j + 16) - block.bs * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bv * 16.0D - 0.01D) / 256.0D;
            d5 = (k + block.bw * 16.0D) / 256.0D;
            d6 = (k + block.bt * 16.0D - 0.01D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.bs;
        double d13 = d + block.bv;
        double d14 = d1 + block.bt;
        double d15 = d1 + block.bw;
        double d16 = d2 + block.bx;
        if (this.m) {
            tessellator.a(this.P, this.T, this.X);
            tessellator.a(d12, d15, d16, d3, d5);
            tessellator.a(this.Q, this.U, this.Y);
            tessellator.a(d12, d14, d16, d9, d11);
            tessellator.a(this.R, this.V, this.Z);
            tessellator.a(d13, d14, d16, d4, d6);
            tessellator.a(this.S, this.W, this.aa);
            tessellator.a(d13, d15, d16, d8, d10);
        } else {
            tessellator.a(d12, d15, d16, d3, d5);
            tessellator.a(d12, d14, d16, d9, d11);
            tessellator.a(d13, d14, d16, d4, d6);
            tessellator.a(d13, d15, d16, d8, d10);
        }
    }

    public void e(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.a;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = (j + block.bu * 16.0D) / 256.0D;
        double d4 = (j + block.bx * 16.0D - 0.01D) / 256.0D;
        double d5 = (k + (1.0D - block.bw) * 16.0D) / 256.0D;
        double d6 = (k + (1.0D - block.bt) * 16.0D - 0.01D) / 256.0D;
        if (this.e) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.bu < 0.0D || block.bx > 1.0D) {
            d3 = ((j + 0.0F) / 256.0F);
            d4 = ((j + 15.99F) / 256.0F);
        }
        if (block.bt < 0.0D || block.bw > 1.0D) {
            d5 = ((k + 0.0F) / 256.0F);
            d6 = ((k + 15.99F) / 256.0F);
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.j == 1) {
            d3 = (j + block.bt * 16.0D) / 256.0D;
            d5 = ((k + 16) - block.bx * 16.0D) / 256.0D;
            d4 = (j + block.bw * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bu * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.j == 2) {
            d3 = ((j + 16) - block.bw * 16.0D) / 256.0D;
            d5 = (k + block.bu * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bt * 16.0D) / 256.0D;
            d6 = (k + block.bx * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.j == 3) {
            d3 = ((j + 16) - block.bu * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bx * 16.0D - 0.01D) / 256.0D;
            d5 = (k + block.bw * 16.0D) / 256.0D;
            d6 = (k + block.bt * 16.0D - 0.01D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.bs;
        double d13 = d1 + block.bt;
        double d14 = d1 + block.bw;
        double d15 = d2 + block.bu;
        double d16 = d2 + block.bx;
        if (this.m) {
            tessellator.a(this.P, this.T, this.X);
            tessellator.a(d12, d14, d16, d8, d10);
            tessellator.a(this.Q, this.U, this.Y);
            tessellator.a(d12, d14, d15, d3, d5);
            tessellator.a(this.R, this.V, this.Z);
            tessellator.a(d12, d13, d15, d9, d11);
            tessellator.a(this.S, this.W, this.aa);
            tessellator.a(d12, d13, d16, d4, d6);
        } else {
            tessellator.a(d12, d14, d16, d8, d10);
            tessellator.a(d12, d14, d15, d3, d5);
            tessellator.a(d12, d13, d15, d9, d11);
            tessellator.a(d12, d13, d16, d4, d6);
        }
    }

    public void f(Tile block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.a;
        if (this.d >= 0)
            i = this.d;
        int j = (i & 0xF) << 4;
        int k = i & 0xF0;
        double d3 = (j + block.bu * 16.0D) / 256.0D;
        double d4 = (j + block.bx * 16.0D - 0.01D) / 256.0D;
        double d5 = (k + (1.0D - block.bw) * 16.0D) / 256.0D;
        double d6 = (k + (1.0D - block.bt) * 16.0D - 0.01D) / 256.0D;
        if (this.e) {
            double d7 = d3;
            d3 = d4;
            d4 = d7;
        }
        if (block.bu < 0.0D || block.bx > 1.0D) {
            d3 = ((j + 0.0F) / 256.0F);
            d4 = ((j + 15.99F) / 256.0F);
        }
        if (block.bt < 0.0D || block.bw > 1.0D) {
            d5 = ((k + 0.0F) / 256.0F);
            d6 = ((k + 15.99F) / 256.0F);
        }
        double d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.i == 2) {
            d3 = (j + block.bt * 16.0D) / 256.0D;
            d5 = ((k + 16) - block.bu * 16.0D) / 256.0D;
            d4 = (j + block.bw * 16.0D) / 256.0D;
            d6 = ((k + 16) - block.bx * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.i == 1) {
            d3 = ((j + 16) - block.bw * 16.0D) / 256.0D;
            d5 = (k + block.bx * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bt * 16.0D) / 256.0D;
            d6 = (k + block.bu * 16.0D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
            d3 = d8;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.i == 3) {
            d3 = ((j + 16) - block.bu * 16.0D) / 256.0D;
            d4 = ((j + 16) - block.bx * 16.0D - 0.01D) / 256.0D;
            d5 = (k + block.bw * 16.0D) / 256.0D;
            d6 = (k + block.bt * 16.0D - 0.01D) / 256.0D;
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }
        double d12 = d + block.bv;
        double d13 = d1 + block.bt;
        double d14 = d1 + block.bw;
        double d15 = d2 + block.bu;
        double d16 = d2 + block.bx;
        if (this.m) {
            tessellator.a(this.P, this.T, this.X);
            tessellator.a(d12, d13, d16, d9, d11);
            tessellator.a(this.Q, this.U, this.Y);
            tessellator.a(d12, d13, d15, d4, d6);
            tessellator.a(this.R, this.V, this.Z);
            tessellator.a(d12, d14, d15, d8, d10);
            tessellator.a(this.S, this.W, this.aa);
            tessellator.a(d12, d14, d16, d3, d5);
        } else {
            tessellator.a(d12, d13, d16, d9, d11);
            tessellator.a(d12, d13, d15, d4, d6);
            tessellator.a(d12, d14, d15, d8, d10);
            tessellator.a(d12, d14, d16, d3, d5);
        }
    }

    public void a(Tile block, int i, float f) {
        Tessellator tessellator = Tessellator.a;
        if (this.b) {
            int j = block.b(i);
            float f1 = (j >> 16 & 0xFF) / 255.0F;
            float f3 = (j >> 8 & 0xFF) / 255.0F;
            float f5 = (j & 0xFF) / 255.0F;
            GL11.glColor4f(f1 * f, f3 * f, f5 * f, 1.0F);
        }
        int k = block.b();
        if (k == 0 || k == 16) {
            if (k == 16)
                i = 1;
            block.g();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.b();
            tessellator.b(0.0F, -1.0F, 0.0F);
            a(block, 0.0D, 0.0D, 0.0D, block.a(0, i));
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 1.0F, 0.0F);
            b(block, 0.0D, 0.0D, 0.0D, block.a(1, i));
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 0.0F, -1.0F);
            c(block, 0.0D, 0.0D, 0.0D, block.a(2, i));
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 0.0F, 1.0F);
            d(block, 0.0D, 0.0D, 0.0D, block.a(3, i));
            tessellator.a();
            tessellator.b();
            tessellator.b(-1.0F, 0.0F, 0.0F);
            e(block, 0.0D, 0.0D, 0.0D, block.a(4, i));
            tessellator.a();
            tessellator.b();
            tessellator.b(1.0F, 0.0F, 0.0F);
            f(block, 0.0D, 0.0D, 0.0D, block.a(5, i));
            tessellator.a();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        } else if (k == 1) {
            tessellator.b();
            tessellator.b(0.0F, -1.0F, 0.0F);
            a(block, i, -0.5D, -0.5D, -0.5D);
            tessellator.a();
        } else if (k == 13) {
            block.g();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float f2 = 0.0625F;
            tessellator.b();
            tessellator.b(0.0F, -1.0F, 0.0F);
            a(block, 0.0D, 0.0D, 0.0D, block.a(0, i));
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 1.0F, 0.0F);
            b(block, 0.0D, 0.0D, 0.0D, block.a(1, i));
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 0.0F, -1.0F);
            tessellator.c(0.0F, 0.0F, f2);
            c(block, 0.0D, 0.0D, 0.0D, block.a(2, i));
            tessellator.c(0.0F, 0.0F, -f2);
            tessellator.a();
            tessellator.b();
            tessellator.b(0.0F, 0.0F, 1.0F);
            tessellator.c(0.0F, 0.0F, -f2);
            d(block, 0.0D, 0.0D, 0.0D, block.a(3, i));
            tessellator.c(0.0F, 0.0F, f2);
            tessellator.a();
            tessellator.b();
            tessellator.b(-1.0F, 0.0F, 0.0F);
            tessellator.c(f2, 0.0F, 0.0F);
            e(block, 0.0D, 0.0D, 0.0D, block.a(4, i));
            tessellator.c(-f2, 0.0F, 0.0F);
            tessellator.a();
            tessellator.b();
            tessellator.b(1.0F, 0.0F, 0.0F);
            tessellator.c(-f2, 0.0F, 0.0F);
            f(block, 0.0D, 0.0D, 0.0D, block.a(5, i));
            tessellator.c(f2, 0.0F, 0.0F);
            tessellator.a();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        } else if (k == 6) {
            tessellator.b();
            tessellator.b(0.0F, -1.0F, 0.0F);
            b(block, i, -0.5D, -0.5D, -0.5D);
            tessellator.a();
        } else if (k == 2) {
            tessellator.b();
            tessellator.b(0.0F, -1.0F, 0.0F);
            a(block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D);
            tessellator.a();
        } else if (k == 10) {
            for (int l = 0; l < 2; l++) {
                if (l == 0)
                    block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                if (l == 1)
                    block.a(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.b();
                tessellator.b(0.0F, -1.0F, 0.0F);
                a(block, 0.0D, 0.0D, 0.0D, block.a(0, i));
                tessellator.a();
                tessellator.b();
                tessellator.b(0.0F, 1.0F, 0.0F);
                b(block, 0.0D, 0.0D, 0.0D, block.a(1, i));
                tessellator.a();
                tessellator.b();
                tessellator.b(0.0F, 0.0F, -1.0F);
                c(block, 0.0D, 0.0D, 0.0D, block.a(2, i));
                tessellator.a();
                tessellator.b();
                tessellator.b(0.0F, 0.0F, 1.0F);
                d(block, 0.0D, 0.0D, 0.0D, block.a(3, i));
                tessellator.a();
                tessellator.b();
                tessellator.b(-1.0F, 0.0F, 0.0F);
                e(block, 0.0D, 0.0D, 0.0D, block.a(4, i));
                tessellator.a();
                tessellator.b();
                tessellator.b(1.0F, 0.0F, 0.0F);
                f(block, 0.0D, 0.0D, 0.0D, block.a(5, i));
                tessellator.a();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
        } else if (k == 11) {
            for (int i1 = 0; i1 < 4; i1++) {
                float f4 = 0.125F;
                if (i1 == 0)
                    block.a(0.5F - f4, 0.0F, 0.0F, 0.5F + f4, 1.0F, f4 * 2.0F);
                if (i1 == 1)
                    block.a(0.5F - f4, 0.0F, 1.0F - f4 * 2.0F, 0.5F + f4, 1.0F, 1.0F);
                f4 = 0.0625F;
                if (i1 == 2)
                    block.a(0.5F - f4, 1.0F - f4 * 3.0F, -f4 * 2.0F, 0.5F + f4, 1.0F - f4, 1.0F + f4 * 2.0F);
                if (i1 == 3)
                    block.a(0.5F - f4, 0.5F - f4 * 3.0F, -f4 * 2.0F, 0.5F + f4, 0.5F - f4, 1.0F + f4 * 2.0F);
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.b();
                tessellator.b(0.0F, -1.0F, 0.0F);
                a(block, 0.0D, 0.0D, 0.0D, block.a(0));
                tessellator.a();
                tessellator.b();
                tessellator.b(0.0F, 1.0F, 0.0F);
                b(block, 0.0D, 0.0D, 0.0D, block.a(1));
                tessellator.a();
                tessellator.b();
                tessellator.b(0.0F, 0.0F, -1.0F);
                c(block, 0.0D, 0.0D, 0.0D, block.a(2));
                tessellator.a();
                tessellator.b();
                tessellator.b(0.0F, 0.0F, 1.0F);
                d(block, 0.0D, 0.0D, 0.0D, block.a(3));
                tessellator.a();
                tessellator.b();
                tessellator.b(-1.0F, 0.0F, 0.0F);
                e(block, 0.0D, 0.0D, 0.0D, block.a(4));
                tessellator.a();
                tessellator.b();
                tessellator.b(1.0F, 0.0F, 0.0F);
                f(block, 0.0D, 0.0D, 0.0D, block.a(5));
                tessellator.a();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            block.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public boolean renderGrass(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j + 1, k);
        int l = block.b(this.c, i, j, k);
        float r = (l >> 16 & 0xFF) / 255.0F;
        float g = (l >> 8 & 0xFF) / 255.0F;
        float b = (l & 0xFF) / 255.0F;
        int metadata = this.c.e(i, j, k);
        float multiplier = Tile.v.grassMultiplier(metadata);
        if (multiplier < 0.0F)
            return false;
        r *= multiplier;
        g *= multiplier;
        b *= multiplier;
        tessellator.a(f * r, f * g, f * b);
        double d = i;
        double d1 = (j - 0.0625F + 1.0F);
        double d2 = k;
        this.rand.setSeed((i * i * 3121 + i * 45238971 + k * k * 418711 + k * 13761 + j));
        j = 168;
        int u = (j & 0xF) << 4;
        int v = j & 0xF0;
        u += this.rand.nextInt(32);
        double d3 = (u / 256.0F);
        double d4 = ((u + 15.99F) / 256.0F);
        double d5 = (v / 256.0F);
        double d6 = ((v + 15.99F) / 256.0F);
        double d7 = d + 0.5D - 0.44999998807907104D;
        double d8 = d + 0.5D + 0.44999998807907104D;
        double d9 = d2 + 0.5D - 0.44999998807907104D;
        double d10 = d2 + 0.5D + 0.44999998807907104D;
        tessellator.a(d7, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d10, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d9, d4, d5);
        u = (j & 0xF) << 4;
        v = j & 0xF0;
        u += this.rand.nextInt(32);
        d3 = (u / 256.0F);
        d4 = ((u + 15.99F) / 256.0F);
        d5 = (v / 256.0F);
        d6 = ((v + 15.99F) / 256.0F);
        tessellator.a(d7, d1 + 1.0D, d10, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d8, d1 + 1.0D, d9, d4, d5);
        tessellator.a(d8, d1 + 1.0D, d9, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d7, d1 + 1.0D, d10, d4, d5);
        return true;
    }

    public boolean renderSpikes(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        if (this.c.g(i, j - 1, k)) {
            a(block, this.c.e(i, j, k), i, j, k);
        } else if (this.c.g(i, j + 1, k)) {
            renderCrossedSquaresUpsideDown(block, this.c.e(i, j, k), i, j, k);
        } else if (this.c.g(i - 1, j, k)) {
            renderCrossedSquaresEast(block, this.c.e(i, j, k), i, j, k);
        } else if (this.c.g(i + 1, j, k)) {
            renderCrossedSquaresWest(block, this.c.e(i, j, k), i, j, k);
        } else if (this.c.g(i, j, k - 1)) {
            renderCrossedSquaresNorth(block, this.c.e(i, j, k), i, j, k);
        } else if (this.c.g(i, j, k + 1)) {
            renderCrossedSquaresSouth(block, this.c.e(i, j, k), i, j, k);
        } else {
            a(block, this.c.e(i, j, k), i, j, k);
        }
        return true;
    }

    public boolean renderTable(Tile block, int i, int j, int k) {
        boolean rendered = l(block, i, j, k);
        boolean north = (this.c.a(i, j, k + 1) != Blocks.tableBlocks.bn);
        boolean south = (this.c.a(i, j, k - 1) != Blocks.tableBlocks.bn);
        boolean west = (this.c.a(i - 1, j, k) != Blocks.tableBlocks.bn);
        boolean east = (this.c.a(i + 1, j, k) != Blocks.tableBlocks.bn);
        if (west && south) {
            block.a(0.0F, 0.0F, 0.0F, 0.1875F, 0.875F, 0.1875F);
            rendered |= l(block, i, j, k);
        }
        if (east && south) {
            block.a(0.8125F, 0.0F, 0.0F, 1.0F, 0.875F, 0.1875F);
            rendered |= l(block, i, j, k);
        }
        if (east && north) {
            block.a(0.8125F, 0.0F, 0.8125F, 1.0F, 0.875F, 1.0F);
            rendered |= l(block, i, j, k);
        }
        if (west && north) {
            block.a(0.0F, 0.0F, 0.8125F, 0.1875F, 0.875F, 1.0F);
            rendered |= l(block, i, j, k);
        }
        block.a(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
        return rendered;
    }

    public boolean renderChair(Tile block, int i, int j, int k) {
        boolean rendered = l(block, i, j, k);
        int side = this.c.e(i, j, k) % 4;
        switch (side) {
            case 0:
                block.a(0.125F, 0.625F, 0.125F, 0.25F, 1.25F, 0.875F);
                rendered |= l(block, i, j, k);
                break;
            case 1:
                block.a(0.125F, 0.625F, 0.125F, 0.875F, 1.25F, 0.25F);
                rendered |= l(block, i, j, k);
                break;
            case 2:
                block.a(0.75F, 0.625F, 0.125F, 0.875F, 1.25F, 0.875F);
                rendered |= l(block, i, j, k);
                break;
            case 3:
                block.a(0.125F, 0.625F, 0.75F, 0.875F, 1.25F, 0.875F);
                rendered |= l(block, i, j, k);
                break;
        }
        block.a(0.125F, 0.0F, 0.125F, 0.25F, 0.5F, 0.25F);
        rendered |= l(block, i, j, k);
        block.a(0.75F, 0.0F, 0.125F, 0.875F, 0.5F, 0.25F);
        rendered |= l(block, i, j, k);
        block.a(0.75F, 0.0F, 0.75F, 0.875F, 0.5F, 0.875F);
        rendered |= l(block, i, j, k);
        block.a(0.125F, 0.0F, 0.75F, 0.25F, 0.5F, 0.875F);
        rendered |= l(block, i, j, k);
        block.a(0.125F, 0.5F, 0.125F, 0.875F, 0.625F, 0.875F);
        return rendered;
    }

    public boolean renderRope(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        int m = this.c.e(i, j, k) % 3;
        if (m == 0) {
            a(block, this.c.e(i, j, k), i, j, k);
        } else if (m == 1) {
            renderCrossedSquaresEast(block, this.c.e(i, j, k), i, j, k);
        } else {
            renderCrossedSquaresNorth(block, this.c.e(i, j, k), i, j, k);
        }
        return true;
    }

    public boolean renderBlockTree(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        Object o = this.c.b(i, j, k);
        TileEntityTree obj = null;
        if (o instanceof TileEntityTree)
            obj = (TileEntityTree) o;
        double d = i;
        double d1 = j;
        double d2 = k;
        int m = this.c.e(i, j, k);
        int t = block.a(0, m);
        if (this.d >= 0)
            t = this.d;
        int u = (t & 0xF) << 4;
        int v = t & 0xF0;
        double d3 = (u / 256.0F);
        double d4 = ((u + 15.99F) / 256.0F);
        double d5 = (v / 256.0F);
        double d6 = ((v + 15.99F) / 256.0F);
        double size = 1.0D;
        if (obj != null)
            size = obj.size;
        double d7 = d + 0.5D - 0.44999998807907104D * size;
        double d8 = d + 0.5D + 0.44999998807907104D * size;
        double d9 = d2 + 0.5D - 0.44999998807907104D * size;
        double d10 = d2 + 0.5D + 0.44999998807907104D * size;
        tessellator.a(d7, d1 + size, d9, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d8, d1 + size, d10, d4, d5);
        tessellator.a(d8, d1 + size, d10, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d7, d1 + size, d9, d4, d5);
        if (this.d < 0) {
            t = block.a(1, m);
            u = (t & 0xF) << 4;
            v = t & 0xF0;
            d3 = (u / 256.0F);
            d4 = ((u + 15.99F) / 256.0F);
            d5 = (v / 256.0F);
            d6 = ((v + 15.99F) / 256.0F);
        }
        tessellator.a(d7, d1 + size, d10, d3, d5);
        tessellator.a(d7, d1 + 0.0D, d10, d3, d6);
        tessellator.a(d8, d1 + 0.0D, d9, d4, d6);
        tessellator.a(d8, d1 + size, d9, d4, d5);
        tessellator.a(d8, d1 + size, d9, d3, d5);
        tessellator.a(d8, d1 + 0.0D, d9, d3, d6);
        tessellator.a(d7, d1 + 0.0D, d10, d4, d6);
        tessellator.a(d7, d1 + size, d10, d4, d5);
        return true;
    }

    public boolean renderBlockOverlay(Tile block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.a;
        float f = block.d(this.c, i, j, k);
        tessellator.a(f, f, f);
        int m = this.c.e(i, j, k);
        int t = block.a(0, m);
        ((BlockOverlay) block).updateBounds(this.c, i, j, k);
        if (this.c.g(i, j - 1, k)) {
            b(block, i, j, k, t);
        } else if (this.c.g(i, j + 1, k)) {
            a(block, i, j, k, t);
        } else if (this.c.g(i - 1, j, k)) {
            f(block, i, j, k, t);
        } else if (this.c.g(i + 1, j, k)) {
            e(block, i, j, k, t);
        } else if (this.c.g(i, j, k - 1)) {
            d(block, i, j, k, t);
        } else if (this.c.g(i, j, k + 1)) {
            c(block, i, j, k, t);
        } else {
            b(block, i, j, k, t);
        }
        return true;
    }
}
