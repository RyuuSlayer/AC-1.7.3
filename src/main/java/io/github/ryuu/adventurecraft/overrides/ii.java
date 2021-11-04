package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.util.IEntityPather;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

public class ii extends ls implements IEntityPather {
    private dh a;

    protected Entity d;

    protected boolean e;

    public boolean canForgetTargetRandomly;

    public int timeBeforeForget;

    public boolean canPathRandomly;

    public ii(Level world) {
        super(world);
        this.canForgetTargetRandomly = true;
        this.timeBeforeForget = 0;
        this.canPathRandomly = true;
        this.e = false;
    }

    protected boolean e_() {
        return false;
    }

    protected void f_() {
        this.e = e_();
        float f = 16.0F;
        if (this.d == null) {
            this.d = g_();
            if (this.d != null) {
                this.a = this.aI.a(this, this.d, f);
                this.timeBeforeForget = 40;
            }
        } else if (!this.d.W()) {
            this.d = null;
        } else {
            float f1 = this.d.f(this);
            if (e(this.d)) {
                a(this.d, f1);
            } else {
                b(this.d, f1);
            }
        }
        boolean canEntityBeSeen = false;
        if (this.d != null)
            canEntityBeSeen = e(this.d);
        if (!this.e && this.d != null && (this.a == null || (this.bs.nextInt(5) == 0 && this.a.needNewPath(this.d))) && canEntityBeSeen) {
            this.a = this.aI.a(this, this.d, f);
        } else if (this.canPathRandomly && !this.e && ((this.a == null && this.bs.nextInt(80) == 0) || this.bs.nextInt(80) == 0)) {
            E();
        }
        if (this.d != null && this.a == null && !canEntityBeSeen) {
            if (this.timeBeforeForget-- <= 0)
                this.d = null;
        } else {
            this.timeBeforeForget = 40;
        }
        int i = in.b(this.aW.b + 0.5D);
        boolean flag = ag();
        boolean flag1 = ah();
        this.aT = 0.0F;
        if (this.a == null || (this.canForgetTargetRandomly && this.bs.nextInt(300) == 0)) {
            super.f_();
            this.a = null;
            return;
        }
        bt vec3d = this.a.a(this);
        for (double d = (this.bg * 2.0F); vec3d != null && vec3d.d(this.aM, vec3d.b, this.aO) < d * d; ) {
            this.a.a();
            if (this.a.b()) {
                vec3d = null;
                this.a = null;
                continue;
            }
            vec3d = this.a.a(this);
        }
        this.az = false;
        if (vec3d != null) {
            double d1 = vec3d.a - this.aM;
            double d2 = vec3d.c - this.aO;
            double d3 = vec3d.b - i;
            float f2 = (float) (Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
            float f3 = f2 - this.aS;
            this.ax = this.aB;
            for (; f3 < -180.0F; f3 += 360.0F) ;
            for (; f3 >= 180.0F; f3 -= 360.0F) ;
            if (f3 > 30.0F)
                f3 = 30.0F;
            if (f3 < -30.0F)
                f3 = -30.0F;
            this.aS += f3;
            if (this.e && this.d != null) {
                double d4 = this.d.aM - this.aM;
                double d5 = this.d.aO - this.aO;
                float f5 = this.aS;
                this.aS = (float) (Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f4 = (f5 - this.aS + 90.0F) * 3.141593F / 180.0F;
                this.aw = -in.a(f4) * this.ax * 1.0F;
                this.ax = in.b(f4) * this.ax * 1.0F;
            }
            if (d3 > 0.0D)
                this.az = true;
        } else if (this.d != null) {
            a(this.d, 30.0F, 30.0F);
        }
        if (this.aY && !F())
            this.az = true;
        if (this.bs.nextFloat() < 0.8F && (flag || flag1))
            this.az = true;
    }

    protected void E() {
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0F;
        for (int l = 0; l < 10; l++) {
            int i1 = in.b(this.aM + this.bs.nextInt(13) - 6.0D);
            int j1 = in.b(this.aN + this.bs.nextInt(7) - 3.0D);
            int k1 = in.b(this.aO + this.bs.nextInt(13) - 6.0D);
            float f1 = a(i1, j1, k1);
            if (f1 > f) {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }
        if (flag)
            this.a = this.aI.a(this, i, j, k, 10.0F);
    }

    protected void a(Entity entity, float f) {
    }

    protected void b(Entity entity, float f) {
    }

    protected float a(int i, int j, int k) {
        return 0.0F;
    }

    protected Entity g_() {
        return null;
    }

    public boolean d() {
        int i = in.b(this.aM);
        int j = in.b(this.aW.b);
        int k = in.b(this.aO);
        return (super.d() && a(i, j, k) >= 0.0F);
    }

    public boolean F() {
        return (this.a != null);
    }

    public void a(dh pathentity) {
        this.a = pathentity;
    }

    public Entity G() {
        return this.d;
    }

    public void c(Entity entity) {
        this.d = entity;
    }

    public dh getCurrentPath() {
        return this.a;
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("canPathRandomly", this.canPathRandomly);
        nbttagcompound.a("canForgetTargetRandomly", this.canForgetTargetRandomly);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("canPathRandomly"))
            this.canPathRandomly = nbttagcompound.m("canPathRandomly");
        if (nbttagcompound.b("canForgetTargetRandomly"))
            this.canPathRandomly = nbttagcompound.m("canForgetTargetRandomly");
    }
}
