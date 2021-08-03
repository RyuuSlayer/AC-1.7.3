package io.github.ryuu.adventurecraft.overrides;

public class qm extends xw {
    private final Tile a;

    public qm(fd world, double d, double d1, double d2, double d3, double d4, double d5, Tile block, int i, int j) {
        super(world, d, d1, d2, d3, d4, d5);
        this.a = block;
        this.b = block.a(i, j);
        this.h = block.bz;
        this.i = this.j = this.k = 0.6F;
        this.g /= 2.0F;
    }

    public qm a(int i, int j, int k) {
        if (this.a == Tile.v)
            return this;
        int l = this.a.b((xp) this.aI, i, j, k);
        this.i *= (l >> 16 & 0xFF) / 255.0F;
        this.j *= (l >> 8 & 0xFF) / 255.0F;
        this.k *= (l & 0xFF) / 255.0F;
        return this;
    }

    public int c_() {
        int t = this.a.getTextureNum();
        if (t == 2)
            return 3;
        if (t == 3)
            return 4;
        return 1;
    }

    public void a(nw tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = ((this.b % 16) + this.c / 4.0F) / 16.0F;
        float f7 = f6 + 0.01560938F;
        float f8 = ((this.b / 16) + this.d / 4.0F) / 16.0F;
        float f9 = f8 + 0.01560938F;
        float f10 = 0.1F * this.g;
        float f11 = (float) (this.aJ + (this.aM - this.aJ) * f - l);
        float f12 = (float) (this.aK + (this.aN - this.aK) * f - m);
        float f13 = (float) (this.aL + (this.aO - this.aL) * f - n);
        float f14 = a(f);
        tessellator.a(f14 * this.i, f14 * this.j, f14 * this.k);
        tessellator.a((f11 - f1 * f10 - f4 * f10), (f12 - f2 * f10), (f13 - f3 * f10 - f5 * f10), f6, f9);
        tessellator.a((f11 - f1 * f10 + f4 * f10), (f12 + f2 * f10), (f13 - f3 * f10 + f5 * f10), f6, f8);
        tessellator.a((f11 + f1 * f10 + f4 * f10), (f12 + f2 * f10), (f13 + f3 * f10 + f5 * f10), f7, f8);
        tessellator.a((f11 + f1 * f10 - f4 * f10), (f12 - f2 * f10), (f13 + f3 * f10 - f5 * f10), f7, f9);
    }
}
