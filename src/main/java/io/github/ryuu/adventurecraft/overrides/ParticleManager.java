package io.github.ryuu.adventurecraft.overrides;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.mixin.Level;
import net.minecraft.client.render.Tessellator;
import net.minecraft.level.Level;
import org.lwjgl.opengl.GL11;

public class ParticleManager {
    private final List[] b = new List[6];
    private final TextureManager c;
    private final Random d = new Random();
    protected Level a;

    public ParticleManager(Level world, TextureManager renderengine) {
        if (world != null)
            this.a = world;
        this.c = renderengine;
        for (int i = 0; i < 6; i++)
            this.b[i] = new ArrayList();
    }

    public void a(xw entityfx) {
        int i = entityfx.c_();
        if (this.b[i].size() >= 4000)
            this.b[i].remove(0);
        this.b[i].add(entityfx);
    }

    public void a() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < this.b[i].size(); j++) {
                xw entityfx = this.b[i].get(j);
                entityfx.w_();
                if (entityfx.be)
                    this.b[i].remove(j--);
            }
        }
    }

    public List getEffectsWithinAABB(eq axisalignedbb) {
        ArrayList<Entity> arraylist = new ArrayList();
        for (int i = 0; i < 6; i++) {
            for (Object obj : this.b[i]) {
                Entity p = (Entity) obj;
                if (axisalignedbb.a <= p.aM && axisalignedbb.d >= p.aM && axisalignedbb.b <= p.aN && axisalignedbb.e >= p.aN && axisalignedbb.c <= p.aO && axisalignedbb.f >= p.aO)
                    arraylist.add(p);
            }
        }
        return arraylist;
    }

    public void a(Entity entity, float f) {
        float f1 = MathsHelper.b(entity.aS * 3.141593F / 180.0F);
        float f2 = MathsHelper.a(entity.aS * 3.141593F / 180.0F);
        float f3 = -f2 * MathsHelper.a(entity.aT * 3.141593F / 180.0F);
        float f4 = f1 * MathsHelper.a(entity.aT * 3.141593F / 180.0F);
        float f5 = MathsHelper.b(entity.aT * 3.141593F / 180.0F);
        xw.l = entity.bl + (entity.aM - entity.bl) * f;
        xw.m = entity.bm + (entity.aN - entity.bm) * f;
        xw.n = entity.bn + (entity.aO - entity.bn) * f;
        for (int i = 0; i < 5; i++) {
            if (this.b[i].size() != 0) {
                int j = 0;
                if (i == 0)
                    j = this.c.b("/particles.png");
                if (i == 1)
                    j = this.c.b("/terrain.png");
                if (i == 2)
                    j = this.c.b("/gui/items.png");
                if (i == 3)
                    j = this.c.b("/terrain2.png");
                if (i == 4)
                    j = this.c.b("/terrain3.png");
                GL11.glBindTexture(3553, j);
                Tessellator tessellator = Tessellator.a;
                tessellator.b();
                for (int k = 0; k < this.b[i].size(); k++) {
                    xw entityfx = this.b[i].get(k);
                    entityfx.a(tessellator, f, f1, f5, f2, f3, f4);
                }
                tessellator.a();
            }
        }
    }

    public void b(Entity entity, float f) {
        byte byte0 = 5;
        if (this.b[byte0].size() == 0)
            return;
        Tessellator tessellator = Tessellator.a;
        for (int i = 0; i < this.b[byte0].size(); i++) {
            xw entityfx = this.b[byte0].get(i);
            entityfx.a(tessellator, f, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    public void a(Level world) {
        this.a = world;
        for (int i = 0; i < 6; i++)
            this.b[i].clear();
    }

    public void a(int i, int j, int k, int l, int i1) {
        if (l == 0)
            return;
        Tile block = Tile.m[l];
        int j1 = 4;
        for (int k1 = 0; k1 < j1; k1++) {
            for (int l1 = 0; l1 < j1; l1++) {
                for (int i2 = 0; i2 < j1; i2++) {
                    double d = i + (k1 + 0.5D) / j1;
                    double d1 = j + (l1 + 0.5D) / j1;
                    double d2 = k + (i2 + 0.5D) / j1;
                    int j2 = this.d.nextInt(6);
                    a((new TileParticle(this.a, d, d1, d2, d - i - 0.5D, d1 - j - 0.5D, d2 - k - 0.5D, block, j2, i1)).a(i, j, k));
                }
            }
        }
    }

    public void a(int i, int j, int k, int l) {
        int i1 = this.a.a(i, j, k);
        if (i1 == 0)
            return;
        Tile block = Tile.m[i1];
        float f = 0.1F;
        double d = i + this.d.nextDouble() * (block.bv - block.bs - (f * 2.0F)) + f + block.bs;
        double d1 = j + this.d.nextDouble() * (block.bw - block.bt - (f * 2.0F)) + f + block.bt;
        double d2 = k + this.d.nextDouble() * (block.bx - block.bu - (f * 2.0F)) + f + block.bu;
        if (l == 0)
            d1 = j + block.bt - f;
        if (l == 1)
            d1 = j + block.bw + f;
        if (l == 2)
            d2 = k + block.bu - f;
        if (l == 3)
            d2 = k + block.bx + f;
        if (l == 4)
            d = i + block.bs - f;
        if (l == 5)
            d = i + block.bv + f;
        a((new TileParticle(this.a, d, d1, d2, 0.0D, 0.0D, 0.0D, block, l, this.a.e(i, j, k))).a(i, j, k).c(0.2F).d(0.6F));
    }

    public String b() {
        return "" + (this.b[0].size() + this.b[1].size() + this.b[2].size());
    }
}
