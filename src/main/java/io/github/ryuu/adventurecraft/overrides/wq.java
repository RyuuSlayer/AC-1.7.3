package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.level.Level;

public class wq extends ls {
    public int attackStrength;

    public wq(Level world) {
        super(world);
        this.attackStrength = 1;
    }

    protected void b(float f) {
    }

    public void a_(float f, float f1) {
        if (ag()) {
            a(f, f1, 0.02F);
            b(this.aP, this.aQ, this.aR);
            this.aP *= 0.800000011920929D;
            this.aQ *= 0.800000011920929D;
            this.aR *= 0.800000011920929D;
        } else if (ah()) {
            a(f, f1, 0.02F);
            b(this.aP, this.aQ, this.aR);
            this.aP *= 0.5D;
            this.aQ *= 0.5D;
            this.aR *= 0.5D;
        } else {
            float f2 = 0.91F;
            if (this.aX) {
                f2 = 0.5460001F;
                int i = this.aI.a(in.b(this.aM), in.b(this.aW.b) - 1, in.b(this.aO));
                if (i > 0)
                    f2 = (uu.m[i]).bB * 0.91F;
            }
            float f3 = 0.1627714F / f2 * f2 * f2;
            a(f, f1, this.aX ? (0.1F * f3) : 0.02F);
            f2 = 0.91F;
            if (this.aX) {
                f2 = 0.5460001F;
                int j = this.aI.a(in.b(this.aM), in.b(this.aW.b) - 1, in.b(this.aO));
                if (j > 0)
                    f2 = (uu.m[j]).bB * 0.91F;
            }
            b(this.aP, this.aQ, this.aR);
            this.aP *= f2;
            this.aQ *= f2;
            this.aR *= f2;
        }
        this.ak = this.al;
        double d = this.aM - this.aJ;
        double d1 = this.aO - this.aL;
        float f4 = in.a(d * d + d1 * d1) * 4.0F;
        if (f4 > 1.0F)
            f4 = 1.0F;
        this.al += (f4 - this.al) * 0.4F;
        this.am += this.al;
    }

    public boolean p() {
        return false;
    }
}
