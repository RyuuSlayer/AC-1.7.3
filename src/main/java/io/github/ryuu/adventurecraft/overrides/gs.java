package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.PlayerTorch;

import java.util.Iterator;
import java.util.List;

public abstract class gs extends ls {
    public ix c;

    public dw d;

    public dw e;

    public byte f;

    public int g;

    public float h;

    public float i;

    public boolean j;

    public int k;

    public String l;

    public int m;

    public String n;

    public double o;

    public double p;

    public double q;

    public double r;

    public double s;

    public double t;

    protected boolean u;

    public br v;

    private int a;

    public float w;

    public float x;

    public float y;

    private br b;

    private br bN;

    public int z;

    protected boolean A;

    public float B;

    public float C;

    private int bO;

    public lx D;

    public boolean isSwingingOffhand;

    public int swingProgressIntOffhand;

    public float prevSwingProgressOffhand;

    public float swingProgressOffhand;

    public boolean swappedItems;

    public int numHeartPieces;

    public String cloakTexture;

    public gs(fd world) {
        super(world);
        this.c = new ix(this);
        this.f = 0;
        this.g = 0;
        this.j = false;
        this.k = 0;
        this.z = 20;
        this.A = false;
        this.bO = 0;
        this.D = null;
        this.d = (dw) new aa(this.c, !world.B);
        this.e = this.d;
        this.bf = 1.62F;
        br chunkcoordinates = world.u();
        c(chunkcoordinates.a + 0.5D, (chunkcoordinates.b + 1), chunkcoordinates.c + 0.5D, 0.0F, 0.0F);
        this.Y = 12;
        this.maxHealth = 12;
        this.R = "humanoid";
        this.Q = 180.0F;
        this.bu = 20;
        this.O = "/mob/char.png";
        this.isSwingingOffhand = false;
        this.swingProgressIntOffhand = 0;
        this.swappedItems = false;
        this.numHeartPieces = 0;
    }

    protected void b() {
        super.b();
        this.bD.a(16, Byte.valueOf((byte) 0));
    }

    public void w_() {
        if (N()) {
            this.a++;
            if (this.a > 100)
                this.a = 100;
            if (!this.aI.B)
                if (!am()) {
                    a(true, true, false);
                } else if (this.aI.f()) {
                    a(false, true, true);
                }
        } else if (this.a > 0) {
            this.a++;
            if (this.a >= 110)
                this.a = 0;
        }
        super.w_();
        if (!this.aI.B && this.e != null && !this.e.b(this)) {
            r();
            this.e = this.d;
        }
        this.o = this.r;
        this.p = this.s;
        this.q = this.t;
        double d = this.aM - this.r;
        double d1 = this.aN - this.s;
        double d2 = this.aO - this.t;
        double d3 = 10.0D;
        if (d > d3)
            this.o = this.r = this.aM;
        if (d2 > d3)
            this.q = this.t = this.aO;
        if (d1 > d3)
            this.p = this.s = this.aN;
        if (d < -d3)
            this.o = this.r = this.aM;
        if (d2 < -d3)
            this.q = this.t = this.aO;
        if (d1 < -d3)
            this.p = this.s = this.aN;
        this.r += d * 0.25D;
        this.t += d2 * 0.25D;
        this.s += d1 * 0.25D;
        a(jl.k, 1);
        if (this.aH == null)
            this.bN = null;
    }

    protected boolean y() {
        return (this.Y <= 0 || N());
    }

    protected void r() {
        this.e = this.d;
    }

    public void u_() {
        this.n = "http://s3.amazonaws.com/MinecraftCloaks/" + this.l + ".png";
        this.bB = this.n;
    }

    public void s_() {
        double d = this.aM;
        double d1 = this.aN;
        double d2 = this.aO;
        super.s_();
        this.h = this.i;
        this.i = 0.0F;
        j(this.aM - d, this.aN - d1, this.aO - d2);
    }

    public void t_() {
        this.bf = 1.62F;
        b(0.6F, 1.8F);
        super.t_();
        this.Y = this.maxHealth;
        this.ad = 0;
        this.be = false;
        this.bv = -this.bu;
    }

    protected void f_() {
        if (this.j) {
            this.k++;
            if (this.k == 8) {
                this.k = 0;
                this.j = false;
            }
        } else {
            this.k = 0;
        }
        this.X = this.k / 8.0F;
        if (this.isSwingingOffhand) {
            this.swingProgressIntOffhand++;
            if (this.swingProgressIntOffhand == 8) {
                this.swingProgressIntOffhand = 0;
                this.isSwingingOffhand = false;
            }
        } else {
            this.swingProgressIntOffhand = 0;
        }
        this.swingProgressOffhand = this.swingProgressIntOffhand / 8.0F;
    }

    public void U() {
        this.prevSwingProgressOffhand = this.swingProgressOffhand;
        super.U();
    }

    public void o() {
        if (this.aI.q == 0 && this.Y < this.maxHealth && this.bt % 20 * 12 == 0)
            c(1);
        this.c.e();
        this.h = this.i;
        super.o();
        float f = in.a(this.aP * this.aP + this.aR * this.aR);
        float f1 = (float) Math.atan(-this.aQ * 0.20000000298023224D) * 15.0F;
        if (f > 0.1F)
            f = 0.1F;
        if (!this.aX || this.Y <= 0)
            f = 0.0F;
        if (this.aX || this.Y <= 0)
            f1 = 0.0F;
        this.i += (f - this.i) * 0.4F;
        this.ag += (f1 - this.ag) * 0.8F;
        if (this.Y > 0) {
            List<sn> list = this.aI.b(this, this.aW.b(1.0D, 0.0D, 1.0D));
            if (list != null)
                for (int j = 0; j < list.size(); j++) {
                    sn entity = list.get(j);
                    if (!entity.be)
                        j(entity);
                }
        }
        iz mainItem = this.c.a[this.c.c];
        iz offItem = this.c.a[this.c.offhandItem];
        boolean litFromItem = (mainItem != null && ItemType.c[mainItem.c].isLighting(mainItem));
        int i = litFromItem | ((offItem != null && ItemType.c[offItem.c].isLighting(offItem)) ? 1 : 0);
        if (i != 0 || (mainItem != null && (mainItem.c == Tile.ar.bn || mainItem.c == Blocks.lights1.bn)) || (offItem != null && (offItem.c == Tile.ar.bn || offItem.c == Blocks.lights1.bn))) {
            PlayerTorch.setTorchState(this.aI, true);
            PlayerTorch.setTorchPos(this.aI, (float) this.aM, (float) this.aN, (float) this.aO);
        } else {
            PlayerTorch.setTorchState(this.aI, false);
        }
        if (this.aQ < -0.2D)
            if (usingUmbrella())
                this.aQ = -0.2D;
        if (!this.aX) {
            if (this.c.b() != null && (this.c.b()).c == Items.umbrella.bf)
                this.c.b().b(1);
            if (this.c.getOffhandItem() != null && (this.c.getOffhandItem()).c == Items.umbrella.bf)
                this.c.getOffhandItem().b(1);
        }
    }

    private boolean handleLantern(iz i) {
        if (i == null)
            return false;
        if (i.c != AC_Items.lantern.bf)
            return false;
        if (i.i() < i.j()) {
            i.b(i.i() + 1);
            PlayerTorch.setTorchState(this.aI, true);
            PlayerTorch.setTorchPos(this.aI, (float) this.aM, (float) this.aN, (float) this.aO);
        }
        if (i.i() == i.j())
            if (this.c.c(Items.oil.bf)) {
                i.b(0);
            } else {
                return false;
            }
        return true;
    }

    private void j(sn entity) {
        entity.b(this);
    }

    public int C() {
        return this.g;
    }

    public void b(sn entity) {
        super.b(entity);
        b(0.2F, 0.2F);
        e(this.aM, this.aN, this.aO);
        this.aQ = 0.10000000149011612D;
        if (this.l.equals("Notch"))
            a(new iz(ItemType.h, 1), true);
        if (entity != null) {
            this.aP = (-in.b((this.ac + this.aS) * 3.141593F / 180.0F) * 0.1F);
            this.aR = (-in.a((this.ac + this.aS) * 3.141593F / 180.0F) * 0.1F);
        } else {
            this.aP = this.aR = 0.0D;
        }
        this.bf = 0.1F;
        a(jl.y, 1);
    }

    public void c(sn entity, int i) {
        this.g += i;
        if (entity instanceof gs) {
            a(jl.A, 1);
        } else {
            a(jl.z, 1);
        }
    }

    public void D() {
        a(this.c.a(this.c.c, 1), false);
    }

    public void a(iz itemstack) {
        a(itemstack, false);
    }

    public void a(iz itemstack, boolean flag) {
        if (itemstack == null)
            return;
        hl entityitem = new hl(this.aI, this.aM, this.aN - 0.30000001192092896D + w(), this.aO, itemstack);
        entityitem.c = 40;
        float f = 0.1F;
        if (flag) {
            float f2 = this.bs.nextFloat() * 0.5F;
            float f4 = this.bs.nextFloat() * 3.141593F * 2.0F;
            entityitem.aP = (-in.a(f4) * f2);
            entityitem.aR = (in.b(f4) * f2);
            entityitem.aQ = 0.20000000298023224D;
        } else {
            float f1 = 0.3F;
            entityitem.aP = (-in.a(this.aS / 180.0F * 3.141593F) * in.b(this.aT / 180.0F * 3.141593F) * f1);
            entityitem.aR = (in.b(this.aS / 180.0F * 3.141593F) * in.b(this.aT / 180.0F * 3.141593F) * f1);
            entityitem.aQ = (-in.a(this.aT / 180.0F * 3.141593F) * f1 + 0.1F);
            f1 = 0.02F;
            float f3 = this.bs.nextFloat() * 3.141593F * 2.0F;
            f1 *= this.bs.nextFloat();
            entityitem.aP += Math.cos(f3) * f1;
            entityitem.aQ += ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.1F);
            entityitem.aR += Math.sin(f3) * f1;
        }
        a(entityitem);
        a(jl.v, 1);
    }

    protected void a(hl entityitem) {
        this.aI.b(entityitem);
    }

    public float a(Tile block) {
        float f = this.c.a(block);
        if (a(ln.g))
            f /= 5.0F;
        if (!this.aX)
            f /= 5.0F;
        return f;
    }

    public boolean b(Tile block) {
        return this.c.b(block);
    }

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        sp nbttaglist = nbttagcompound.l("Inventory");
        this.c.b(nbttaglist);
        this.m = nbttagcompound.e("Dimension");
        this.u = nbttagcompound.m("Sleeping");
        this.a = nbttagcompound.d("SleepTimer");
        if (this.u) {
            this.v = new br(in.b(this.aM), in.b(this.aN), in.b(this.aO));
            a(true, true, false);
        }
        if (nbttagcompound.b("SpawnX") && nbttagcompound.b("SpawnY") && nbttagcompound.b("SpawnZ"))
            this.b = new br(nbttagcompound.e("SpawnX"), nbttagcompound.e("SpawnY"), nbttagcompound.e("SpawnZ"));
        this.numHeartPieces = nbttagcompound.e("NumHeartPieces");
        if (this.maxHealth < 12) {
            this.Y = this.Y * 12 / this.maxHealth;
            this.maxHealth = 12;
        }
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Inventory", (ij) this.c.a(new sp()));
        nbttagcompound.a("Dimension", this.m);
        nbttagcompound.a("Sleeping", this.u);
        nbttagcompound.a("SleepTimer", (short) this.a);
        if (this.b != null) {
            nbttagcompound.a("SpawnX", this.b.a);
            nbttagcompound.a("SpawnY", this.b.b);
            nbttagcompound.a("SpawnZ", this.b.c);
        }
        nbttagcompound.a("NumHeartPieces", this.numHeartPieces);
    }

    public void a(lw iinventory) {
    }

    public void displayGUIPalette() {
    }

    public void a(int i, int j, int k) {
    }

    public void b(sn entity, int i) {
    }

    public float w() {
        return 0.12F;
    }

    protected void E() {
        this.bf = 1.62F;
    }

    public boolean a(sn entity, int i) {
        this.av = 0;
        if (this.Y <= 0)
            return false;
        if (N())
            a(true, true, false);
        if (entity instanceof gz || entity instanceof sl) {
            if (this.aI.q == 0)
                i = 0;
            if (this.aI.q == 1)
                i = i / 3 + 1;
            if (this.aI.q == 3)
                i = i * 3 / 2;
        }
        if (i == 0)
            return false;
        Object obj = entity;
        if (obj instanceof sl && ((sl) obj).c != null)
            obj = ((sl) obj).c;
        if (obj instanceof ls)
            a((ls) obj, false);
        a(jl.x, i);
        return super.a(entity, i);
    }

    public boolean attackEntityFromMulti(sn entity, int i) {
        this.av = 0;
        if (this.Y <= 0)
            return false;
        if (N())
            a(true, true, false);
        if (entity instanceof gz || entity instanceof sl) {
            if (this.aI.q == 0)
                i = 0;
            if (this.aI.q == 1)
                i = i / 3 + 1;
            if (this.aI.q == 3)
                i = i * 3 / 2;
        }
        if (i == 0)
            return false;
        Object obj = entity;
        if (obj instanceof sl && ((sl) obj).c != null)
            obj = ((sl) obj).c;
        if (obj instanceof ls)
            a((ls) obj, false);
        a(jl.x, i);
        return super.attackEntityFromMulti(entity, i);
    }

    protected boolean F() {
        return false;
    }

    protected void a(ls entityliving, boolean flag) {
        if (entityliving instanceof gb || entityliving instanceof Ghast)
            return;
        if (entityliving instanceof gi) {
            gi entitywolf = (gi) entityliving;
            if (entitywolf.D() && this.l.equals(entitywolf.A()))
                return;
        }
        if (entityliving instanceof gs && !F())
            return;
        List list = this.aI.a(gi.class, eq.b(this.aM, this.aN, this.aO, this.aM + 1.0D, this.aN + 1.0D, this.aO + 1.0D).b(16.0D, 4.0D, 16.0D));
        Iterator<sn> iterator = list.iterator();
        while (iterator.hasNext()) {
            sn entity = iterator.next();
            gi entitywolf1 = (gi) entity;
            if (entitywolf1.D() && entitywolf1.G() == null && this.l.equals(entitywolf1.A()) && (!flag || !entitywolf1.B())) {
                entitywolf1.b(false);
                entitywolf1.c(entityliving);
            }
        }
    }

    protected void b(int i) {
        if (DebugMode.active)
            return;
        int j = 25 - this.c.f();
        int k = i * j + this.bO;
        i = k / 25;
        this.bO = k % 25;
        super.b(i);
    }

    public void a(sk tileentityfurnace) {
    }

    public void a(Dispenser tileentitydispenser) {
    }

    public void a(yk tileentitysign) {
    }

    public void c(sn entity) {
        if (entity.a(this))
            return;
        iz itemstack = G();
        if (itemstack != null && entity instanceof ls) {
            itemstack.a((ls) entity);
            if (itemstack.a == 0) {
                itemstack.a(this);
                H();
            }
        }
    }

    public iz G() {
        return this.c.b();
    }

    public void H() {
        this.c.a(this.c.c, null);
    }

    public double I() {
        return (this.bf - 0.5F);
    }

    public void J() {
        if (this.swappedItems) {
            swingOffhandItem();
            return;
        }
        this.k = -1;
        this.j = true;
    }

    public void swingOffhandItem() {
        this.swingProgressIntOffhand = -1;
        this.isSwingingOffhand = true;
    }

    public float getSwingOffhandProgress(float f) {
        float f1 = this.swingProgressOffhand - this.prevSwingProgressOffhand;
        if (f1 < 0.0F)
            f1++;
        return this.prevSwingProgressOffhand + f1 * f;
    }

    public void d(sn entity) {
        int i = this.c.a(entity);
        if (i > 0) {
            if (this.aQ < 0.0D)
                i++;
            entity.a(this, i);
            iz itemstack = G();
            if (itemstack != null && entity instanceof ls) {
                itemstack.a((ls) entity, this);
                if (itemstack.a == 0) {
                    itemstack.a(this);
                    H();
                }
            }
            if (entity instanceof ls) {
                if (entity.W())
                    a((ls) entity, true);
                a(jl.w, i);
            }
        }
    }

    public void p_() {
    }

    public abstract void v();

    public void b(iz itemstack) {
    }

    public void K() {
        super.K();
        this.d.a(this);
        if (this.e != null)
            this.e.a(this);
    }

    public boolean protectedByShield() {
        iz curItem = this.c.b();
        iz offItem = this.c.getOffhandItem();
        if ((curItem != null && curItem.c == Items.woodenShield.bf) || (offItem != null && offItem.c == Items.woodenShield.bf))
            return (getSwingOffhandProgress(1.0F) == 0.0F);
        return false;
    }

    public boolean L() {
        return (!this.u && super.L());
    }

    public cw b(int i, int j, int k) {
        if (!this.aI.B) {
            if (N() || !W())
                return cw.e;
            if (this.aI.t.c)
                return cw.b;
            if (this.aI.f())
                return cw.c;
            if (Math.abs(this.aM - i) > 3.0D || Math.abs(this.aN - j) > 2.0D || Math.abs(this.aO - k) > 3.0D)
                return cw.d;
        }
        b(0.2F, 0.2F);
        this.bf = 0.2F;
        if (this.aI.i(i, j, k)) {
            int l = this.aI.e(i, j, k);
            int i1 = ve.d(l);
            float f = 0.5F;
            float f1 = 0.5F;
            switch (i1) {
                case 0:
                    f1 = 0.9F;
                    break;
                case 2:
                    f1 = 0.1F;
                    break;
                case 1:
                    f = 0.1F;
                    break;
                case 3:
                    f = 0.9F;
                    break;
            }
            e(i1);
            e((i + f), (j + 0.9375F), (k + f1));
        } else {
            e((i + 0.5F), (j + 0.9375F), (k + 0.5F));
        }
        this.u = true;
        this.a = 0;
        this.v = new br(i, j, k);
        this.aP = this.aR = this.aQ = 0.0D;
        if (!this.aI.B)
            this.aI.y();
        return cw.a;
    }

    private void e(int i) {
        this.w = 0.0F;
        this.y = 0.0F;
        switch (i) {
            case 0:
                this.y = -1.8F;
                break;
            case 2:
                this.y = 1.8F;
                break;
            case 1:
                this.w = 1.8F;
                break;
            case 3:
                this.w = -1.8F;
                break;
        }
    }

    public void a(boolean flag, boolean flag1, boolean flag2) {
        b(0.6F, 1.8F);
        E();
        br chunkcoordinates = this.v;
        br chunkcoordinates1 = this.v;
        if (chunkcoordinates != null && this.aI.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c) == Tile.T.bn) {
            ve.a(this.aI, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, false);
            br chunkcoordinates2 = ve.f(this.aI, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, 0);
            if (chunkcoordinates2 == null)
                chunkcoordinates2 = new br(chunkcoordinates.a, chunkcoordinates.b + 1, chunkcoordinates.c);
            e((chunkcoordinates2.a + 0.5F), (chunkcoordinates2.b + this.bf + 0.1F), (chunkcoordinates2.c + 0.5F));
        }
        this.u = false;
        if (!this.aI.B && flag1)
            this.aI.y();
        if (flag) {
            this.a = 0;
        } else {
            this.a = 100;
        }
        if (flag2)
            a(this.v);
    }

    private boolean am() {
        return (this.aI.a(this.v.a, this.v.b, this.v.c) == Tile.T.bn);
    }

    public static br a(fd world, br chunkcoordinates) {
        cl ichunkprovider = world.w();
        ichunkprovider.c(chunkcoordinates.a - 3 >> 4, chunkcoordinates.c - 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a + 3 >> 4, chunkcoordinates.c - 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a - 3 >> 4, chunkcoordinates.c + 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a + 3 >> 4, chunkcoordinates.c + 3 >> 4);
        if (world.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c) != Tile.T.bn)
            return null;
        br chunkcoordinates1 = ve.f(world, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, 0);
        return chunkcoordinates1;
    }

    public float M() {
        if (this.v != null) {
            int i = this.aI.e(this.v.a, this.v.b, this.v.c);
            int j = ve.d(i);
            switch (j) {
                case 0:
                    return 90.0F;
                case 1:
                    return 0.0F;
                case 2:
                    return 270.0F;
                case 3:
                    return 180.0F;
            }
        }
        return 0.0F;
    }

    public boolean N() {
        return this.u;
    }

    public boolean O() {
        return (this.u && this.a >= 100);
    }

    public int P() {
        return this.a;
    }

    public void b(String s) {
    }

    public br Q() {
        return this.b;
    }

    public void a(br chunkcoordinates) {
        if (chunkcoordinates != null) {
            this.b = new br(chunkcoordinates);
        } else {
            this.b = null;
        }
    }

    public void a(vr statbase) {
        a(statbase, 1);
    }

    public void a(vr statbase, int i) {
    }

    protected void R() {
        super.R();
        a(jl.u, 1);
    }

    public void a_(float f, float f1) {
        double d = this.aM;
        double d1 = this.aN;
        double d2 = this.aO;
        super.a_(f, f1);
        i(this.aM - d, this.aN - d1, this.aO - d2);
    }

    private void i(double d, double d1, double d2) {
        if (this.aH != null)
            return;
        if (a(ln.g)) {
            int i = Math.round(in.a(d * d + d1 * d1 + d2 * d2) * 100.0F);
            if (i > 0)
                a(jl.q, i);
        } else if (ag()) {
            int j = Math.round(in.a(d * d + d2 * d2) * 100.0F);
            if (j > 0)
                a(jl.m, j);
        } else if (p()) {
            if (d1 > 0.0D)
                a(jl.o, (int) Math.round(d1 * 100.0D));
        } else if (this.aX) {
            int k = Math.round(in.a(d * d + d2 * d2) * 100.0F);
            if (k > 0)
                a(jl.l, k);
        } else {
            int l = Math.round(in.a(d * d + d2 * d2) * 100.0F);
            if (l > 25)
                a(jl.p, l);
        }
    }

    private void j(double d, double d1, double d2) {
        if (this.aH != null) {
            int i = Math.round(in.a(d * d + d1 * d1 + d2 * d2) * 100.0F);
            if (i > 0)
                if (this.aH instanceof yl) {
                    a(jl.r, i);
                    if (this.bN == null) {
                        this.bN = new br(in.b(this.aM), in.b(this.aN), in.b(this.aO));
                    } else if (this.bN.a(in.b(this.aM), in.b(this.aN), in.b(this.aO)) >= 1000.0D) {
                        a((vr) ep.q, 1);
                    }
                } else if (this.aH instanceof fz) {
                    a(jl.s, i);
                } else if (this.aH instanceof wh) {
                    a(jl.t, i);
                }
        }
    }

    protected void b(float f) {
        if (f >= 2.0F)
            a(jl.n, (int) Math.round(f * 100.0D));
        super.b(f);
    }

    public void a(ls entityliving) {
        if (entityliving instanceof gz)
            a((vr) ep.s);
    }

    public int c(iz itemstack) {
        int i = super.c(itemstack);
        if (itemstack.c == ItemType.aP.bf && this.D != null)
            i = itemstack.b() + 16;
        return i;
    }

    public void S() {
        if (this.z > 0) {
            this.z = 10;
            return;
        }
        this.A = true;
    }

    public double getGravity() {
        if ((AC_Items.hookshot.mainHookshot != null && AC_Items.hookshot.mainHookshot.attachedToSurface) || (AC_Items.hookshot.offHookshot != null && AC_Items.hookshot.offHookshot.attachedToSurface))
            return 0.0D;
        return super.getGravity();
    }

    public boolean usingUmbrella() {
        return ((this.c.b() != null && (this.c.b()).c == AC_Items.umbrella.bf) || (this.c.getOffhandItem() != null && (this.c.getOffhandItem()).c == AC_Items.umbrella.bf));
    }
}
