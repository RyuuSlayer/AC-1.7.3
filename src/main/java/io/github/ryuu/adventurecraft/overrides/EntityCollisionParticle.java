package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.mixin.Level;
import net.minecraft.client.particle.Particle;
import org.lwjgl.opengl.GL11;

public class EntityCollisionParticle extends Particle {
    private final Entity a;

    private final Entity o;

    private int p;

    private int q;

    private final float r;

    public EntityCollisionParticle(Level world, Entity entity, Entity entity1, float f) {
        super(world, entity.aM, entity.aN, entity.aO, entity.aP, entity.aQ, entity.aR);
        this.p = 0;
        this.q = 0;
        this.a = entity;
        this.o = entity1;
        this.q = 3;
        this.r = f;
    }

    public void a(nw tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = (this.p + f) / this.q;
        f6 *= f6;
        double d = this.a.aM;
        double d1 = this.a.aN;
        double d2 = this.a.aO;
        double d3 = this.o.bl + (this.o.aM - this.o.bl) * f;
        double d4 = this.o.bm + (this.o.aN - this.o.bm) * f + this.r;
        double d5 = this.o.bn + (this.o.aO - this.o.bn) * f;
        double d6 = d + (d3 - d) * f6;
        double d7 = d1 + (d4 - d1) * f6;
        double d8 = d2 + (d5 - d2) * f6;
        int i = in.b(d6);
        int j = in.b(d7 + (this.bf / 2.0F));
        int k = in.b(d8);
        float f7 = this.aI.c(i, j, k);
        d6 -= l;
        d7 -= m;
        d8 -= n;
        GL11.glColor4f(f7, f7, f7, 1.0F);
        th.a.a(this.a, (float) d6, (float) d7, (float) d8, this.a.aS, f);
    }

    public void w_() {
        this.p++;
        if (this.p == this.q)
            K();
    }

    public int c_() {
        return 5;
    }
}
