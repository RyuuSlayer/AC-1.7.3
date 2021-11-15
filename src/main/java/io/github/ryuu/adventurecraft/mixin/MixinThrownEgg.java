package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.animal.Chicken;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.maths.Vec3f;

import java.util.List;

public class MixinThrownEgg extends Entity {
    public int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private LivingEntity g;
    private int h;
    private int i;

    public MixinThrownEgg(Level world) {
        super(world);
        this.b = -1;
        this.c = -1;
        this.d = -1;
        this.e = 0;
        this.f = false;
        this.a = 0;
        this.i = 0;
        b(0.25F, 0.25F);
        this.collidesWithClipBlocks = false;
    }

    public MixinThrownEgg(Level world, LivingEntity entityliving) {
        super(world);
        this.b = -1;
        this.c = -1;
        this.d = -1;
        this.e = 0;
        this.f = false;
        this.a = 0;
        this.i = 0;
        this.g = entityliving;
        b(0.25F, 0.25F);
        c(entityliving.aM, entityliving.aN + entityliving.w(), entityliving.aO, entityliving.aS, entityliving.aT);
        this.aM -= (MathsHelper.b(this.aS / 180.0F * 3.141593F) * 0.16F);
        this.aN -= 0.10000000149011612D;
        this.aO -= (MathsHelper.a(this.aS / 180.0F * 3.141593F) * 0.16F);
        e(this.aM, this.aN, this.aO);
        this.bf = 0.0F;
        float f = 0.4F;
        this.aP = (-MathsHelper.a(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F) * f);
        this.aR = (MathsHelper.b(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F) * f);
        this.aQ = (-MathsHelper.a(this.aT / 180.0F * 3.141593F) * f);
        a(this.aP, this.aQ, this.aR, 1.5F, 1.0F);
    }

    public MixinThrownEgg(Level world, double d, double d1, double d2) {
        super(world);
        this.b = -1;
        this.c = -1;
        this.d = -1;
        this.e = 0;
        this.f = false;
        this.a = 0;
        this.i = 0;
        this.h = 0;
        b(0.25F, 0.25F);
        e(d, d1, d2);
        this.bf = 0.0F;
    }

    protected void b() {
    }

    public boolean a(double d) {
        double d1 = this.aW.c() * 4.0D;
        d1 *= 64.0D;
        return (d < d1 * d1);
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
        this.h = 0;
    }

    public void a(double d, double d1, double d2) {
        this.aP = d;
        this.aQ = d1;
        this.aR = d2;
        if (this.aV == 0.0F && this.aU == 0.0F) {
            float f = MathsHelper.a(d * d + d2 * d2);
            this.aU = this.aS = (float) (Math.atan2(d, d2) * 180.0D / 3.1415927410125732D);
            this.aV = this.aT = (float) (Math.atan2(d1, f) * 180.0D / 3.1415927410125732D);
        }
    }

    public void w_() {
        this.bl = this.aM;
        this.bm = this.aN;
        this.bn = this.aO;
        super.w_();
        if (this.a > 0)
            this.a--;
        if (this.f) {
            int i = this.aI.a(this.b, this.c, this.d);
            if (i != this.e) {
                this.f = false;
                this.aP *= (this.bs.nextFloat() * 0.2F);
                this.aQ *= (this.bs.nextFloat() * 0.2F);
                this.aR *= (this.bs.nextFloat() * 0.2F);
                this.h = 0;
                this.i = 0;
            } else {
                this.h++;
                if (this.h == 1200)
                    K();
                return;
            }
        } else {
            this.i++;
        }
        Vec3f vec3d = Vec3f.b(this.aM, this.aN, this.aO);
        Vec3f vec3d1 = Vec3f.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        HitResult movingobjectposition = this.aI.rayTraceBlocks2(vec3d, vec3d1, false, true, false);
        vec3d = Vec3f.b(this.aM, this.aN, this.aO);
        vec3d1 = Vec3f.b(this.aM + this.aP, this.aN + this.aQ, this.aO + this.aR);
        if (movingobjectposition != null)
            vec3d1 = Vec3f.b(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
        if (!this.aI.B) {
            Entity entity = null;
            List<Entity> list = this.aI.b(this, this.aW.a(this.aP, this.aQ, this.aR).b(1.0D, 1.0D, 1.0D));
            double d = 0.0D;
            for (int i1 = 0; i1 < list.size(); i1++) {
                Entity entity1 = list.get(i1);
                if (entity1.h_() && (entity1 != this.g || this.i >= 5)) {
                    float f4 = 0.3F;
                    Box axisalignedbb = entity1.aW.b(f4, f4, f4);
                    HitResult movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
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
                movingobjectposition = new HitResult(entity);
        }
        if (movingobjectposition != null) {
            if (movingobjectposition.g != null)
                if (!movingobjectposition.g.a((Entity) this.g, 0)) ;
            if (!this.aI.B && this.bs.nextInt(8) == 0) {
                byte byte0 = 1;
                if (this.bs.nextInt(32) == 0)
                    byte0 = 4;
                for (int k = 0; k < byte0; k++) {
                    Chicken entitychicken = new Chicken(this.aI);
                    entitychicken.c(this.aM, this.aN, this.aO, this.aS, 0.0F);
                    this.aI.b((Entity) entitychicken);
                }
            }
            for (int j = 0; j < 8; j++)
                this.aI.a("snowballpoof", this.aM, this.aN, this.aO, 0.0D, 0.0D, 0.0D);
            K();
        }
        this.aM += this.aP;
        this.aN += this.aQ;
        this.aO += this.aR;
        float f = MathsHelper.a(this.aP * this.aP + this.aR * this.aR);
        this.aS = (float) (Math.atan2(this.aP, this.aR) * 180.0D / 3.1415927410125732D);
        for (this.aT = (float) (Math.atan2(this.aQ, f) * 180.0D / 3.1415927410125732D); this.aT - this.aV < -180.0F; this.aV -= 360.0F)
            ;
        for (; this.aT - this.aV >= 180.0F; this.aV += 360.0F) ;
        for (; this.aS - this.aU < -180.0F; this.aU -= 360.0F) ;
        for (; this.aS - this.aU >= 180.0F; this.aU += 360.0F) ;
        this.aT = this.aV + (this.aT - this.aV) * 0.2F;
        this.aS = this.aU + (this.aS - this.aU) * 0.2F;
        float f1 = 0.99F;
        float f2 = 0.03F;
        if (ag()) {
            for (int l = 0; l < 4; l++) {
                float f3 = 0.25F;
                this.aI.a("bubble", this.aM - this.aP * f3, this.aN - this.aQ * f3, this.aO - this.aR * f3, this.aP, this.aQ, this.aR);
            }
            f1 = 0.8F;
        }
        this.aP *= f1;
        this.aQ *= f1;
        this.aR *= f1;
        this.aQ -= f2;
        e(this.aM, this.aN, this.aO);
    }

    public void b(CompoundTag nbttagcompound) {
        nbttagcompound.put("xTile", (short) this.b);
        nbttagcompound.put("yTile", (short) this.c);
        nbttagcompound.put("zTile", (short) this.d);
        nbttagcompound.put("inTile", (byte) this.e);
        nbttagcompound.put("shake", (byte) this.a);
        nbttagcompound.put("inGround", (byte) (this.f ? 1 : 0));
    }

    public void a(CompoundTag nbttagcompound) {
        this.b = nbttagcompound.getShort("xTile");
        this.c = nbttagcompound.getShort("yTile");
        this.d = nbttagcompound.getShort("zTile");
        this.e = nbttagcompound.getByte("inTile") & 0xFF;
        this.a = nbttagcompound.getByte("shake") & 0xFF;
        this.f = (nbttagcompound.getByte("inGround") == 1);
    }

    public void b(Player entityplayer) {
        if (this.f && this.g == entityplayer && this.a <= 0 && entityplayer.c.a(new ItemInstance(ItemType.j, 1))) {
            this.aI.a(this, "random.pop", 0.2F, ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.b(this, 1);
            K();
        }
    }

    public float x_() {
        return 0.0F;
    }
}
