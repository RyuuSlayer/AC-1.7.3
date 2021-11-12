package io.github.ryuu.adventurecraft.mixin.overrides;

import net.minecraft.util.maths.Vec3f;

public class dh {
    public d[] b;

    public int a;

    public int c;

    public fw p;

    public d clearSize;

    public dh(d[] apathpoint) {
        this.b = apathpoint;
        this.a = apathpoint.length;
    }

    public void a() {
        this.c++;
        if (this.p != null)
            this.p.simplifyPath(this, this.clearSize);
    }

    public boolean b() {
        return (this.c >= this.b.length);
    }

    public d c() {
        if (this.a > 0)
            return this.b[this.a - 1];
        return null;
    }

    public Vec3f a(Entity entity) {
        double d3 = (this.b[this.c]).a + (int) (entity.bg + 1.0F) * 0.5D;
        double d1 = (this.b[this.c]).b;
        double d2 = (this.b[this.c]).c + (int) (entity.bg + 1.0F) * 0.5D;
        return Vec3f.b(d3, d1, d2);
    }

    public boolean needNewPath(Entity entity) {
        if (this.a > 0) {
            double dX = entity.aM - (this.b[this.a - 1]).a - 0.5D;
            double dY = entity.aN - entity.bf - (this.b[this.a - 1]).b;
            double dZ = entity.aO - (this.b[this.a - 1]).c - 0.5D;
            return (dX * dX + dY * dY + dZ * dZ > 6.0D);
        }
        return false;
    }
}
