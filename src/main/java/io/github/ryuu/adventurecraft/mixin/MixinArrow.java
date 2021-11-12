package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathsHelper;

import java.util.List;

public class MixinArrow extends Entity {
    private final int attackStrength;
    public int d;
    public int e;
    public int f;
    public int g;
    public boolean a;
    public int b;
    public LivingEntity c;
    protected int k;
    private int h;
    private boolean i;
    private int j;

    public MixinArrow(Level world) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.i = false;
        this.a = false;
        this.b = 0;
        this.k = 0;
        b(0.5F, 0.5F);
        this.attackStrength = 2;
    }

    public MixinArrow(Level world, double d, double d1, double d2) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.i = false;
        this.a = false;
        this.b = 0;
        this.k = 0;
        b(0.5F, 0.5F);
        e(d, d1, d2);
        this.bf = 0.0F;
        this.attackStrength = 2;
    }

    public MixinArrow(Level world, LivingEntity entityliving) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.i = false;
        this.a = false;
        this.b = 0;
        this.k = 0;
        this.c = entityliving;
        this.a = entityliving instanceof Player;
        b(0.5F, 0.5F);
        c(entityliving.aM, entityliving.aN + entityliving.w(), entityliving.aO, entityliving.aS, entityliving.aT);
        this.aM -= (MathsHelper.b(this.aS / 180.0F * 3.141593F) * 0.16F);
        this.aN -= 0.10000000149011612D;
        this.aO -= (MathsHelper.a(this.aS / 180.0F * 3.141593F) * 0.16F);
        e(this.aM, this.aN, this.aO);
        this.bf = 0.0F;
        this.aP = (-MathsHelper.a(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F));
        this.aR = (MathsHelper.b(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F));
        this.aQ = -MathsHelper.a(this.aT / 180.0F * 3.141593F);
        a(this.aP, this.aQ, this.aR, 1.5F, 1.0F);
        this.attackStrength = 2;
    }

    public MixinArrow(Level world, LivingEntity entityliving, int damage) {
        super(world);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.g = 0;
        this.h = 0;
        this.i = false;
        this.a = false;
        this.b = 0;
        this.k = 0;
        this.c = entityliving;
        this.a = entityliving instanceof Player;
        b(0.5F, 0.5F);
        c(entityliving.aM, entityliving.aN + entityliving.w(), entityliving.aO, entityliving.aS, entityliving.aT);
        this.aM -= (MathsHelper.b(this.aS / 180.0F * 3.141593F) * 0.16F);
        this.aN -= 0.10000000149011612D;
        this.aO -= (MathsHelper.a(this.aS / 180.0F * 3.141593F) * 0.16F);
        e(this.aM, this.aN, this.aO);
        this.bf = 0.0F;
        this.aP = (-MathsHelper.a(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F));
        this.aR = (MathsHelper.b(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F));
        this.aQ = -MathsHelper.a(this.aT / 180.0F * 3.141593F);
        a(this.aP, this.aQ, this.aR, 1.5F, 1.0F);
        this.attackStrength = damage;
    }

    protected void b() {
        this.collidesWithClipBlocks = false;
    }

    public void a(double d, double d1, double d2, float f, float f1) {
        float f2 = MathsHelper.a(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += this.bs.nextGaussian() * 0.007499999832361937D * f1;
        d1 += this.bs.nextGaussian() * 0.007499999832361937D * f1;
        d2 += this.bs.nextGaussian() * 0.007499999832361937D * f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        this.aP = d;
        this.aQ = d1;
        this.aR = d2;
        float f3 = MathsHelper.a(d * d + d2 * d2);
        this.aU = this.aS = (float) (Math.atan2(d, d2) * 180.0D / 3.1415927410125732D);
        this.aV = this.aT = (float) (Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D);
        this.j = 0;
    }

    public void a(double d, double d1, double d2) {
        this.aP = d;
        this.aQ = d1;
        this.aR = d2;
        if (this.aV == 0.0F && this.aU == 0.0F) {
            float f = MathsHelper.a(d * d + d2 * d2);
            this.aU = this.aS = (float) (Math.atan2(d, d2) * 180.0D / 3.1415927410125732D);
            this.aV = this.aT = (float) (Math.atan2(d1, f) * 180.0D / 3.1415927410125732D);
            this.aV = this.aT;
            this.aU = this.aS;
            c(this.aM, this.aN, this.aO, this.aS, this.aT);
            this.j = 0;
        }
    }

    public void w_() {
        super.w_();
        if (this.aV == 0.0F && this.aU == 0.0F) {
            float f = MathsHelper.a(this.aP * this.aP + this.aR * this.aR);
            this.aU = this.aS = (float) (Math.atan2(this.aP, this.aR) * 180.0D / 3.1415927410125732D);
            this.aV = this.aT = (float) (Math.atan2(this.aQ, f) * 180.0D / 3.1415927410125732D);
        }
        int i = this.aI.a(this.d, this.e, this.f);
        if (i > 0 && i != Blocks.clipBlock.bn && !LadderTile.isLadderID(i)) {
            MixinTile.m[i].a((xp) this.aI, this.d, this.e, this.f);
            eq axisalignedbb = MixinTile.m[i].e(this.aI, this.d, this.e, this.f);
            if (axisalignedbb != null && axisalignedbb.a(bt.b(this.aM, this.aN, this.aO)))
                this.i = true;
        }
        if (this.b > 0)
            this.b--;
        if (this.i) {
            int j = this.aI.a(this.d, this.e, this.f);
            int k = this.aI.e(this.d, this.e, this.f);
            if (j != this.g || k != this.h) {
                this.i = false;
                this.aP *= (this.bs.nextFloat() * 0.2F);
                this.aQ *= (this.bs.nextFloat() * 0.2F);
                this.aR *= (this.bs.nextFloat() * 0.2F);
                this.j = 0;
                this.k = 0;
                return;
            }
            this.j++;
            if (this.j == 1200)
                K();
            return;
        }
        this.k++;
        bt vec3d = bt.b(this.aM, this.aN, this.aO);
        bt vec3d1 = bt.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        vf movingobjectposition = this.aI.rayTraceBlocks2(vec3d, vec3d1, false, true, false);
        vec3d = bt.b(this.aM, this.aN, this.aO);
        vec3d1 = bt.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        if (movingobjectposition != null)
            vec3d1 = bt.b(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        Entity entity = null;
        List<Entity> list = this.aI.b(this, this.aW.a(this.aP, this.aQ, this.aR).b(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (int l = 0; l < list.size(); l++) {
            Entity entity1 = list.get(l);
            if (entity1.h_() && (entity1 != this.c || this.k >= 5)) {
                float f4 = 0.3F;
                eq axisalignedbb1 = entity1.aW.b(f4, f4, f4);
                vf movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
                if (movingobjectposition1 != null) {
                    double d1 = vec3d.c(movingobjectposition1.f);
                    if (d1 < d || d == 0.0D) {
                        entity = entity1;
                        d = d1;
                    }
                }
            }
        }
        if (entity != null)
            movingobjectposition = new vf(entity);
        if (movingobjectposition != null)
            if (movingobjectposition.g != null) {
                handleHitEntity(movingobjectposition);
            } else {
                this.d = movingobjectposition.b;
                this.e = movingobjectposition.c;
                this.f = movingobjectposition.d;
                this.g = this.aI.a(this.d, this.e, this.f);
                this.h = this.aI.e(this.d, this.e, this.f);
                this.aP = (float) (movingobjectposition.f.a - this.aM);
                this.aQ = (float) (movingobjectposition.f.b - this.aN);
                this.aR = (float) (movingobjectposition.f.c - this.aO);
                float f1 = MathsHelper.a(this.aP * this.aP + this.aQ * this.aQ + this.aR * this.aR);
                this.aM -= this.aP / f1 * 0.05000000074505806D;
                this.aN -= this.aQ / f1 * 0.05000000074505806D;
                this.aO -= this.aR / f1 * 0.05000000074505806D;
                this.aI.a(this, "random.drr", 1.0F, 1.2F / (this.bs.nextFloat() * 0.2F + 0.9F));
                this.i = true;
                this.b = 7;
            }
        this.aM += this.aP;
        this.aN += this.aQ;
        this.aO += this.aR;
        float f2 = MathsHelper.a(this.aP * this.aP + this.aR * this.aR);
        this.aS = (float) (Math.atan2(this.aP, this.aR) * 180.0D / 3.1415927410125732D);
        for (this.aT = (float) (Math.atan2(this.aQ, f2) * 180.0D / 3.1415927410125732D); this.aT - this.aV < -180.0F; this.aV -= 360.0F)
            ;
        for (; this.aT - this.aV >= 180.0F; this.aV += 360.0F) ;
        for (; this.aS - this.aU < -180.0F; this.aU -= 360.0F) ;
        for (; this.aS - this.aU >= 180.0F; this.aU += 360.0F) ;
        this.aT = this.aV + (this.aT - this.aV) * 0.2F;
        this.aS = this.aU + (this.aS - this.aU) * 0.2F;
        float f3 = 0.99F;
        float f5 = 0.03F;
        if (ag()) {
            for (int i1 = 0; i1 < 4; i1++) {
                float f6 = 0.25F;
                this.aI.a("bubble", this.aM - this.aP * f6, this.aN - this.aQ * f6, this.aO - this.aR * f6, this.aP, this.aQ, this.aR);
            }
            f3 = 0.8F;
        }
        this.aP *= f3;
        this.aQ *= f3;
        this.aR *= f3;
        this.aQ -= f5;
        e(this.aM, this.aN, this.aO);
    }

    public void handleHitEntity(vf movingobjectposition) {
        if (movingobjectposition.g instanceof LivingEntity && ((LivingEntity) movingobjectposition.g).protectedByShield(this.aJ, this.aK, this.aL)) {
            this.aI.a(this, "random.drr", 1.0F, 1.2F / (this.bs.nextFloat() * 0.2F + 0.9F));
            K();
        } else if (movingobjectposition.g.a((Entity) this.c, this.attackStrength)) {
            this.aI.a(this, "random.drr", 1.0F, 1.2F / (this.bs.nextFloat() * 0.2F + 0.9F));
            K();
        } else {
            this.aP *= -0.10000000149011612D;
            this.aQ *= -0.10000000149011612D;
            this.aR *= -0.10000000149011612D;
            this.aS += 180.0F;
            this.aU += 180.0F;
            this.k = 0;
        }
    }

    public void b(CompoundTag nbttagcompound) {
        nbttagcompound.put("xTile", (short) this.d);
        nbttagcompound.put("yTile", (short) this.e);
        nbttagcompound.put("zTile", (short) this.f);
        nbttagcompound.put("inTile", (byte) this.g);
        nbttagcompound.put("inData", (byte) this.h);
        nbttagcompound.put("shake", (byte) this.b);
        nbttagcompound.put("inGround", (byte) (this.i ? 1 : 0));
        nbttagcompound.put("player", this.a);
    }

    public void a(CompoundTag nbttagcompound) {
        this.d = nbttagcompound.d("xTile");
        this.e = nbttagcompound.d("yTile");
        this.f = nbttagcompound.d("zTile");
        this.g = nbttagcompound.c("inTile") & 0xFF;
        this.h = nbttagcompound.c("inData") & 0xFF;
        this.b = nbttagcompound.c("shake") & 0xFF;
        this.i = (nbttagcompound.c("inGround") == 1);
        this.a = nbttagcompound.m("player");
    }

    public void b(Player entityplayer) {
        if (this.aI.B)
            return;
        if (this.i && this.a && this.b <= 0 && entityplayer.c.a(new ItemInstance(ItemType.j, 1))) {
            this.aI.a(this, "random.pop", 0.2F, ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.b(this, 1);
            K();
        }
    }

    public float x_() {
        return 0.0F;
    }
}