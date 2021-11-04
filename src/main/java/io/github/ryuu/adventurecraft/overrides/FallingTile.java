package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class FallingTile extends Entity {
    public int a;

    public int metadata;

    public int b;

    public double startX;

    public double startZ;

    public FallingTile(Level world) {
        super(world);
        this.b = 0;
        b(0.98F, 0.98F);
        this.bh = 0.98F;
        this.bf = this.bh / 2.0F;
    }

    public FallingTile(Level world, double d, double d1, double d2, int i) {
        super(world);
        this.b = 0;
        this.a = i;
        this.aF = true;
        b(0.98F, 0.98F);
        this.bh = 0.98F;
        this.bf = this.bh / 2.0F;
        e(d, d1, d2);
        this.aP = 0.0D;
        this.aQ = 0.0D;
        this.aR = 0.0D;
        this.aJ = d;
        this.aK = d1;
        this.aL = d2;
        this.startX = d;
        this.startZ = d2;
    }

    protected boolean n() {
        return false;
    }

    protected void b() {
    }

    public boolean h_() {
        return !this.be;
    }

    public void w_() {
        if (this.a == 0) {
            K();
            return;
        }
        this.aJ = this.aM;
        this.aK = this.aN;
        this.aL = this.aO;
        this.b++;
        this.aQ -= 0.03999999910593033D;
        b(this.aP, this.aQ, this.aR);
        this.aP *= 0.9800000190734863D;
        this.aQ *= 0.9800000190734863D;
        this.aR *= 0.9800000190734863D;
        int i = MathsHelper.b(this.aM);
        int j = MathsHelper.b(this.aN);
        int k = MathsHelper.b(this.aO);
        if (this.aI.a(i, j, k) == this.a)
            this.aI.f(i, j, k, 0);
        if (this.aX && Math.abs(this.aP) < 0.01D && Math.abs(this.aR) < 0.01D) {
            this.aP *= 0.699999988079071D;
            this.aR *= 0.699999988079071D;
            this.aQ *= -0.5D;
            if (!gk.c_(this.aI, i, j - 1, k)) {
                K();
                if ((!this.aI.a(this.a, i, j, k, true, 1) || !this.aI.b(i, j, k, this.a, this.metadata)) && !this.aI.B)
                    b(this.a, 1);
            } else {
                e(i + 0.5D, this.aN, k + 0.5D);
                this.aP = 0.0D;
                this.aR = 0.0D;
            }
        } else if (this.b > 100 && !this.aI.B) {
            b(this.a, 1);
            K();
        }
        if (Math.abs(this.aM - this.startX) >= 1.0D) {
            this.aP = 0.0D;
            e(i + 0.5D, this.aN, this.aO);
        }
        if (Math.abs(this.aO - this.startZ) >= 1.0D) {
            this.aR = 0.0D;
            e(this.aM, this.aN, k + 0.5D);
        }
    }

    protected void b(CompoundTag nbttagcompound) {
        nbttagcompound.a("Tile", (byte) this.a);
        nbttagcompound.a("EntityID", this.aD);
    }

    protected void a(CompoundTag nbttagcompound) {
        this.a = nbttagcompound.c("Tile") & 0xFF;
        if (nbttagcompound.b("EntityID"))
            this.aD = nbttagcompound.e("EntityID");
    }

    public float x_() {
        return 0.0F;
    }

    public Level k() {
        return this.aI;
    }
}
