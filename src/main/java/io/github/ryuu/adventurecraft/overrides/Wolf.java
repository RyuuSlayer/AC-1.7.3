package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.animal.Animal;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.food.FoodItem;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;

import java.util.Iterator;
import java.util.List;

public class Wolf extends Animal {
    private boolean a;

    private float b;

    private float c;

    private boolean f;

    private boolean g;

    private float h;

    private float i;

    public int attackStrength;

    public Wolf(Level world) {
        super(world);
        this.a = false;
        this.O = "/mob/wolf.png";
        b(0.8F, 0.8F);
        this.aB = 1.1F;
        this.Y = 8;
        this.attackStrength = -1;
    }

    protected void b() {
        super.b();
        this.bD.a(16, Byte.valueOf((byte) 0));
        this.bD.a(17, "");
        this.bD.a(18, new Integer(this.Y));
    }

    protected boolean n() {
        return false;
    }

    public String q_() {
        if (D())
            return "/mob/wolf_tame.png";
        if (C())
            return "/mob/wolf_angry.png";
        return super.q_();
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("Angry", C());
        nbttagcompound.put("Sitting", B());
        if (A() == null) {
            nbttagcompound.put("Owner", "");
        } else {
            nbttagcompound.put("Owner", A());
        }
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        c(nbttagcompound.m("Angry"));
        b(nbttagcompound.m("Sitting"));
        String s = nbttagcompound.i("Owner");
        if (s.length() > 0) {
            a(s);
            d(true);
        }
    }

    protected boolean u() {
        return !D();
    }

    protected String g() {
        if (C())
            return "mob.wolf.growl";
        if (this.bs.nextInt(3) == 0) {
            if (D() && this.bD.b(18) < 10)
                return "mob.wolf.whine";
            return "mob.wolf.panting";
        }
        return "mob.wolf.bark";
    }

    protected String j_() {
        return "mob.wolf.hurt";
    }

    protected String i() {
        return "mob.wolf.death";
    }

    protected float k() {
        return 0.4F;
    }

    protected int j() {
        return -1;
    }

    protected void f_() {
        super.f_();
        if (!this.e && !F() && D() && this.aH == null) {
            Player entityplayer = this.aI.a(A());
            if (entityplayer != null) {
                float f = entityplayer.f((Entity) this);
                if (f > 5.0F)
                    c(entityplayer, f);
            } else if (!ag()) {
                b(true);
            }
        } else if (this.d == null && !F() && !D() && this.aI.r.nextInt(100) == 0) {
            List<net.minecraft.entity.Entity> list = this.aI.a(dl.class, eq.b(this.aM, this.aN, this.aO, this.aM + 1.0D, this.aN + 1.0D, this.aO + 1.0D).b(16.0D, 4.0D, 16.0D));
            if (!list.isEmpty())
                c(list.get(this.aI.r.nextInt(list.size())));
        }
        if (ag())
            b(false);
        if (!this.aI.B)
            this.bD.b(18, Integer.valueOf(this.Y));
    }

    public void o() {
        super.o();
        this.a = false;
        if (Y() && !F() && !C()) {
            Entity entity = Z();
            if (entity instanceof Player) {
                Player entityplayer = (Player) entity;
                ItemInstance itemstack = entityplayer.c.b();
                if (itemstack != null)
                    if (!D() && itemstack.c == ItemType.aV.bf) {
                        this.a = true;
                    } else if (D() && ItemType.c[itemstack.c] instanceof FoodItem) {
                        this.a = ((FoodItem) ItemType.c[itemstack.c]).m();
                    }
            }
        }
        if (!this.V && this.f && !this.g && !F() && this.aX) {
            this.g = true;
            this.h = 0.0F;
            this.i = 0.0F;
            this.aI.a((Entity) this, (byte) 8);
        }
    }

    public void w_() {
        super.w_();
        this.c = this.b;
        if (this.a) {
            this.b += (1.0F - this.b) * 0.4F;
        } else {
            this.b += (0.0F - this.b) * 0.4F;
        }
        if (this.a)
            this.aC = 10;
        if (af()) {
            this.f = true;
            this.g = false;
            this.h = 0.0F;
            this.i = 0.0F;
        } else if ((this.f || this.g) && this.g) {
            if (this.h == 0.0F)
                this.aI.a((net.minecraft.entity.Entity) this, "mob.wolf.shake", k(), (this.bs.nextFloat() - this.bs.nextFloat()) * 0.2F + 1.0F);
            this.i = this.h;
            this.h += 0.05F;
            if (this.i >= 2.0F) {
                this.f = false;
                this.g = false;
                this.i = 0.0F;
                this.h = 0.0F;
            }
            if (this.h > 0.4F) {
                float f = (float) this.aW.b;
                int i = (int) (MathsHelper.a((this.h - 0.4F) * 3.141593F) * 7.0F);
                for (int j = 0; j < i; j++) {
                    float f1 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg * 0.5F;
                    float f2 = (this.bs.nextFloat() * 2.0F - 1.0F) * this.bg * 0.5F;
                    this.aI.a("splash", this.aM + f1, (f + 0.8F), this.aO + f2, this.aP, this.aQ, this.aR);
                }
            }
        }
    }

    public boolean v() {
        return this.f;
    }

    public float b_(float f) {
        return 0.75F + (this.i + (this.h - this.i) * f) / 2.0F * 0.25F;
    }

    public float a(float f, float f1) {
        float f2 = (this.i + (this.h - this.i) * f + f1) / 1.8F;
        if (f2 < 0.0F) {
            f2 = 0.0F;
        } else if (f2 > 1.0F) {
            f2 = 1.0F;
        }
        return MathsHelper.a(f2 * 3.141593F) * MathsHelper.a(f2 * 3.141593F * 11.0F) * 0.15F * 3.141593F;
    }

    public float c(float f) {
        return (this.c + (this.b - this.c) * f) * 0.15F * 3.141593F;
    }

    public float w() {
        return this.bh * 0.8F;
    }

    protected int x() {
        if (B())
            return 20;
        return super.x();
    }

    private void c(Entity entity, float f) {
        dh pathentity = this.aI.a((Entity) this, entity, 16.0F);
        if (pathentity == null && f > 12.0F) {
            int i = MathsHelper.b(entity.aM) - 2;
            int j = MathsHelper.b(entity.aO) - 2;
            int k = MathsHelper.b(entity.aW.b);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.aI.h(i + l, k - 1, j + i1) && !this.aI.h(i + l, k, j + i1) && !this.aI.h(i + l, k + 1, j + i1)) {
                        c(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.aS, this.aT);
                        return;
                    }
                }
            }
        } else {
            a(pathentity);
        }
    }

    protected boolean e_() {
        return (B() || this.g);
    }

    public boolean a(net.minecraft.entity.Entity entity, int i) {
        b(false);
        if (entity != null && !(entity instanceof Player) && !(entity instanceof Arrow))
            i = (i + 1) / 2;
        if (super.a(entity, i)) {
            if (!D() && !C()) {
                if (entity instanceof Player) {
                    c(true);
                    this.d = entity;
                }
                if (entity instanceof Arrow && ((Arrow) entity).c != null)
                    entity = ((Arrow) entity).c;
                if (entity instanceof LivingEntity) {
                    List list = this.aI.a(Wolf.class, eq.b(this.aM, this.aN, this.aO, this.aM + 1.0D, this.aN + 1.0D, this.aO + 1.0D).b(16.0D, 4.0D, 16.0D));
                    Iterator<Entity> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Entity entity1 = iterator.next();
                        Wolf entitywolf = (Wolf) entity1;
                        if (!entitywolf.D() && entitywolf.d == null) {
                            entitywolf.d = entity;
                            if (entity instanceof Player)
                                entitywolf.c(true);
                        }
                    }
                }
            } else if (entity != this && entity != null) {
                if (D() && entity instanceof Player && ((Player) entity).l.equalsIgnoreCase(A()))
                    return true;
                this.d = entity;
            }
            return true;
        }
        return false;
    }

    protected Entity g_() {
        if (C())
            return this.aI.a((Entity) this, 16.0D);
        return null;
    }

    protected void a(Entity entity, float f) {
        if (f > 2.0F && f < 6.0F && this.bs.nextInt(10) == 0) {
            if (this.aX) {
                double d = entity.aM - this.aM;
                double d1 = entity.aO - this.aO;
                float f1 = MathsHelper.a(d * d + d1 * d1);
                this.aP = d / f1 * 0.5D * 0.800000011920929D + this.aP * 0.20000000298023224D;
                this.aR = d1 / f1 * 0.5D * 0.800000011920929D + this.aR * 0.20000000298023224D;
                this.aQ = 0.4000000059604645D;
            }
        } else if (f < 1.5D && entity.aW.e > this.aW.b && entity.aW.b < this.aW.e) {
            this.ae = 20;
            int byte0 = 2;
            if (D())
                byte0 = 4;
            if (this.attackStrength != -1)
                byte0 = this.attackStrength;
            entity.a((net.minecraft.entity.Entity) this, byte0);
        }
    }

    public boolean a(Player entityplayer) {
        ItemInstance itemstack = entityplayer.c.b();
        if (!D()) {
            if (itemstack != null && itemstack.c == ItemType.aV.bf && !C()) {
                itemstack.a--;
                if (itemstack.a <= 0)
                    entityplayer.c.a(entityplayer.c.c, null);
                if (!this.aI.B)
                    if (this.bs.nextInt(3) == 0) {
                        d(true);
                        a((dh) null);
                        b(true);
                        this.Y = 20;
                        a(entityplayer.l);
                        a(true);
                        this.aI.a((Entity) this, (byte) 7);
                    } else {
                        a(false);
                        this.aI.a((Entity) this, (byte) 6);
                    }
                return true;
            }
        } else {
            if (itemstack != null && ItemType.c[itemstack.c] instanceof FoodItem) {
                FoodItem itemfood = (FoodItem) ItemType.c[itemstack.c];
                if (itemfood.m() && this.bD.b(18) < 20) {
                    itemstack.a--;
                    if (itemstack.a <= 0)
                        entityplayer.c.a(entityplayer.c.c, null);
                    c(((FoodItem) ItemType.ao).l());
                    return true;
                }
            }
            if (entityplayer.l.equalsIgnoreCase(A())) {
                if (!this.aI.B) {
                    b(!B());
                    this.az = false;
                    a((dh) null);
                }
                return true;
            }
        }
        return false;
    }

    void a(boolean flag) {
        String s = "heart";
        if (!flag)
            s = "smoke";
        for (int i = 0; i < 7; i++) {
            double d = this.bs.nextGaussian() * 0.02D;
            double d1 = this.bs.nextGaussian() * 0.02D;
            double d2 = this.bs.nextGaussian() * 0.02D;
            this.aI.a(s, this.aM + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, this.aN + 0.5D + (this.bs.nextFloat() * this.bh), this.aO + (this.bs.nextFloat() * this.bg * 2.0F) - this.bg, d, d1, d2);
        }
    }

    public void a(byte byte0) {
        if (byte0 == 7) {
            a(true);
        } else if (byte0 == 6) {
            a(false);
        } else if (byte0 == 8) {
            this.g = true;
            this.h = 0.0F;
            this.i = 0.0F;
        } else {
            super.a(byte0);
        }
    }

    public float z() {
        if (C())
            return 1.53938F;
        if (D())
            return (0.55F - (20 - this.bD.b(18)) * 0.02F) * 3.141593F;
        return 0.6283185F;
    }

    public int l() {
        return 8;
    }

    public String A() {
        return this.bD.c(17);
    }

    public void a(String s) {
        this.bD.b(17, s);
    }

    public boolean B() {
        return ((this.bD.a(16) & 0x1) != 0);
    }

    public void b(boolean flag) {
        byte byte0 = this.bD.a(16);
        if (flag) {
            this.bD.b(16, Byte.valueOf((byte) (byte0 | 0x1)));
        } else {
            this.bD.b(16, Byte.valueOf((byte) (byte0 & 0xFFFFFFFE)));
        }
    }

    public boolean C() {
        return ((this.bD.a(16) & 0x2) != 0);
    }

    public void c(boolean flag) {
        byte byte0 = this.bD.a(16);
        if (flag) {
            this.bD.b(16, Byte.valueOf((byte) (byte0 | 0x2)));
        } else {
            this.bD.b(16, Byte.valueOf((byte) (byte0 & 0xFFFFFFFD)));
        }
    }

    public boolean D() {
        return ((this.bD.a(16) & 0x4) != 0);
    }

    public void d(boolean flag) {
        byte byte0 = this.bD.a(16);
        if (flag) {
            this.bD.b(16, Byte.valueOf((byte) (byte0 | 0x4)));
        } else {
            this.bD.b(16, Byte.valueOf((byte) (byte0 & 0xFFFFFFFB)));
        }
    }
}
