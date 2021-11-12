package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.PlayerTorch;
import net.minecraft.achievement.Achievements;
import net.minecraft.container.Container;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FurnaceEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.animal.Wolf;
import net.minecraft.entity.monster.Ghast;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.source.LevelSource;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.tile.BedTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.Dispenser;
import net.minecraft.tile.entity.Sign;
import net.minecraft.tile.material.Material;
import net.minecraft.util.SleepStatus;
import net.minecraft.util.Vec3i;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.DoubleTag;
import net.minecraft.util.io.ListTag;
import net.minecraft.util.maths.MathsHelper;

import java.util.Iterator;
import java.util.List;

public abstract class MixinPlayer extends LivingEntity {
    public PlayerInventory c;

    public Container d;

    public Container e;

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
    public Vec3i v;
    public float w;
    public float x;
    public float y;
    public int z;
    public float B;
    public float C;
    public DoubleTag D;
    public boolean isSwingingOffhand;
    public int swingProgressIntOffhand;
    public float prevSwingProgressOffhand;
    public float swingProgressOffhand;
    public boolean swappedItems;
    public int numHeartPieces;
    public String cloakTexture;
    protected boolean u;
    protected boolean A;
    private int a;
    private Vec3i b;
    private Vec3i bN;
    private int bO;

    public MixinPlayer(Level world) {
        super(world);
        this.c = new PlayerInventory(this);
        this.f = 0;
        this.g = 0;
        this.j = false;
        this.k = 0;
        this.z = 20;
        this.A = false;
        this.bO = 0;
        this.D = null;
        this.d = new PlayerContainer(this.c, !world.B);
        this.e = this.d;
        this.bf = 1.62F;
        Vec3i chunkcoordinates = world.u();
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

    public static Vec3i a(Level world, Vec3i chunkcoordinates) {
        LevelSource ichunkprovider = world.w();
        ichunkprovider.c(chunkcoordinates.a - 3 >> 4, chunkcoordinates.c - 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a + 3 >> 4, chunkcoordinates.c - 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a - 3 >> 4, chunkcoordinates.c + 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a + 3 >> 4, chunkcoordinates.c + 3 >> 4);
        if (world.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c) != Tile.T.bn)
            return null;
        Vec3i chunkcoordinates1 = BedTile.f(world, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, 0);
        return chunkcoordinates1;
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
        a(Stats.k, 1);
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
        float f = MathsHelper.a(this.aP * this.aP + this.aR * this.aR);
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
            List<Entity> list = this.aI.b(this, this.aW.b(1.0D, 0.0D, 1.0D));
            if (list != null)
                for (int j = 0; j < list.size(); j++) {
                    Entity entity = list.get(j);
                    if (!entity.be)
                        j(entity);
                }
        }
        ItemInstance mainItem = this.c.a[this.c.c];
        ItemInstance offItem = this.c.a[this.c.offhandItem];
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

    private boolean handleLantern(ItemInstance i) {
        if (i == null)
            return false;
        if (i.c != Items.lantern.bf)
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

    private void j(Entity entity) {
        entity.b(this);
    }

    public int C() {
        return this.g;
    }

    public void b(Entity entity) {
        super.b(entity);
        b(0.2F, 0.2F);
        e(this.aM, this.aN, this.aO);
        this.aQ = 0.10000000149011612D;
        if (this.l.equals("Notch"))
            a(new ItemInstance(ItemType.h, 1), true);
        if (entity != null) {
            this.aP = (-MathsHelper.b((this.ac + this.aS) * 3.141593F / 180.0F) * 0.1F);
            this.aR = (-MathsHelper.a((this.ac + this.aS) * 3.141593F / 180.0F) * 0.1F);
        } else {
            this.aP = this.aR = 0.0D;
        }
        this.bf = 0.1F;
        a(Stats.y, 1);
    }

    public void c(Entity entity, int i) {
        this.g += i;
        if (entity instanceof Player) {
            a(Stats.A, 1);
        } else {
            a(Stats.z, 1);
        }
    }

    public void D() {
        a(this.c.a(this.c.c, 1), false);
    }

    public void a(ItemInstance itemstack) {
        a(itemstack, false);
    }

    public void a(ItemInstance itemstack, boolean flag) {
        if (itemstack == null)
            return;
        ItemEntity entityitem = new ItemEntity(this.aI, this.aM, this.aN - 0.30000001192092896D + w(), this.aO, itemstack);
        entityitem.c = 40;
        float f = 0.1F;
        if (flag) {
            float f2 = this.bs.nextFloat() * 0.5F;
            float f4 = this.bs.nextFloat() * 3.141593F * 2.0F;
            entityitem.aP = (-MathsHelper.a(f4) * f2);
            entityitem.aR = (MathsHelper.b(f4) * f2);
            entityitem.aQ = 0.20000000298023224D;
        } else {
            float f1 = 0.3F;
            entityitem.aP = (-MathsHelper.a(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F) * f1);
            entityitem.aR = (MathsHelper.b(this.aS / 180.0F * 3.141593F) * MathsHelper.b(this.aT / 180.0F * 3.141593F) * f1);
            entityitem.aQ = (-MathsHelper.a(this.aT / 180.0F * 3.141593F) * f1 + 0.1F);
            f1 = 0.02F;
            float f3 = this.bs.nextFloat() * 3.141593F * 2.0F;
            f1 *= this.bs.nextFloat();
            entityitem.aP += Math.cos(f3) * f1;
            entityitem.aQ += ((this.bs.nextFloat() - this.bs.nextFloat()) * 0.1F);
            entityitem.aR += Math.sin(f3) * f1;
        }
        a(entityitem);
        a(Stats.v, 1);
    }

    protected void a(ItemEntity entityitem) {
        this.aI.b(entityitem);
    }

    public float a(Tile block) {
        float f = this.c.a(block);
        if (a(Material.WATER))
            f /= 5.0F;
        if (!this.aX)
            f /= 5.0F;
        return f;
    }

    public boolean b(Tile block) {
        return this.c.b(block);
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getListTag("Inventory");
        this.c.b(nbttaglist);
        this.m = nbttagcompound.getInt("Dimension");
        this.u = nbttagcompound.getBoolean("Sleeping");
        this.a = nbttagcompound.getShort("SleepTimer");
        if (this.u) {
            this.v = new Vec3i(MathsHelper.b(this.aM), MathsHelper.b(this.aN), MathsHelper.b(this.aO));
            a(true, true, false);
        }
        if (nbttagcompound.b("SpawnX") && nbttagcompound.b("SpawnY") && nbttagcompound.b("SpawnZ"))
            this.b = new Vec3i(nbttagcompound.e("SpawnX"), nbttagcompound.e("SpawnY"), nbttagcompound.e("SpawnZ"));
        this.numHeartPieces = nbttagcompound.e("NumHeartPieces");
        if (this.maxHealth < 12) {
            this.Y = this.Y * 12 / this.maxHealth;
            this.maxHealth = 12;
        }
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("Inventory", (AbstractTag) this.c.a(new ListTag()));
        nbttagcompound.put("Dimension", this.m);
        nbttagcompound.put("Sleeping", this.u);
        nbttagcompound.put("SleepTimer", (short) this.a);
        if (this.b != null) {
            nbttagcompound.put("SpawnX", this.b.a);
            nbttagcompound.put("SpawnY", this.b.b);
            nbttagcompound.put("SpawnZ", this.b.c);
        }
        nbttagcompound.a("NumHeartPieces", this.numHeartPieces);
    }

    public void a(Inventory iinventory) {
    }

    public void displayGUIPalette() {
    }

    public void a(int i, int j, int k) {
    }

    public void b(Entity entity, int i) {
    }

    public float w() {
        return 0.12F;
    }

    protected void E() {
        this.bf = 1.62F;
    }

    public boolean a(Entity entity, int i) {
        this.av = 0;
        if (this.Y <= 0)
            return false;
        if (N())
            a(true, true, false);
        if (entity instanceof Monster || entity instanceof Arrow) {
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
        if (obj instanceof Arrow && ((Arrow) obj).c != null)
            obj = ((Arrow) obj).c;
        if (obj instanceof LivingEntity)
            a((LivingEntity) obj, false);
        a(Stats.damageDealt, i);
        return super.a(entity, i);
    }

    public boolean attackEntityFromMulti(Entity entity, int i) {
        this.av = 0;
        if (this.Y <= 0)
            return false;
        if (N())
            a(true, true, false);
        if (entity instanceof Monster || entity instanceof Arrow) {
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
        if (obj instanceof Arrow && ((Arrow) obj).c != null)
            obj = ((Arrow) obj).c;
        if (obj instanceof LivingEntity)
            a((LivingEntity) obj, false);
        a(Stats.damageTaken, i);
        return super.attackEntityFromMulti(entity, i);
    }

    protected boolean F() {
        return false;
    }

    protected void a(LivingEntity entityliving, boolean flag) {
        if (entityliving instanceof gb || entityliving instanceof Ghast)
            return;
        if (entityliving instanceof Wolf) {
            Wolf entitywolf = (Wolf) entityliving;
            if (entitywolf.D() && this.l.equals(entitywolf.A()))
                return;
        }
        if (entityliving instanceof Player && !F())
            return;
        List list = this.aI.a(Wolf.class, eq.b(this.aM, this.aN, this.aO, this.aM + 1.0D, this.aN + 1.0D, this.aO + 1.0D).b(16.0D, 4.0D, 16.0D));
        Iterator<Entity> iterator = list.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            Wolf entitywolf1 = (Wolf) entity;
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

    public void a(FurnaceEntity tileentityfurnace) {
    }

    public void a(Dispenser tileentitydispenser) {
    }

    public void a(Sign tileentitysign) {
    }

    public void c(Entity entity) {
        if (entity.a(this))
            return;
        ItemInstance itemstack = G();
        if (itemstack != null && entity instanceof LivingEntity) {
            itemstack.a((LivingEntity) entity);
            if (itemstack.a == 0) {
                itemstack.a(this);
                H();
            }
        }
    }

    public ItemInstance G() {
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

    public void d(Entity entity) {
        int i = this.c.a(entity);
        if (i > 0) {
            if (this.aQ < 0.0D)
                i++;
            entity.a(this, i);
            ItemInstance itemstack = G();
            if (itemstack != null && entity instanceof LivingEntity) {
                itemstack.a((LivingEntity) entity, this);
                if (itemstack.a == 0) {
                    itemstack.a(this);
                    H();
                }
            }
            if (entity instanceof LivingEntity) {
                if (entity.W())
                    a((LivingEntity) entity, true);
                a(Stats.damageDealt, i);
            }
        }
    }

    public void p_() {
    }

    public abstract void v();

    public void b(ItemInstance itemstack) {
    }

    public void K() {
        super.K();
        this.d.a(this);
        if (this.e != null)
            this.e.a(this);
    }

    public boolean protectedByShield() {
        ItemInstance curItem = this.c.b();
        ItemInstance offItem = this.c.getOffhandItem();
        if ((curItem != null && curItem.c == Items.woodenShield.bf) || (offItem != null && offItem.c == Items.woodenShield.bf))
            return (getSwingOffhandProgress(1.0F) == 0.0F);
        return false;
    }

    public boolean L() {
        return (!this.u && super.L());
    }

    public SleepStatus b(int i, int j, int k) {
        if (!this.aI.B) {
            if (N() || !W())
                return SleepStatus.e;
            if (this.aI.t.c)
                return SleepStatus.b;
            if (this.aI.f())
                return SleepStatus.c;
            if (Math.abs(this.aM - i) > 3.0D || Math.abs(this.aN - j) > 2.0D || Math.abs(this.aO - k) > 3.0D)
                return SleepStatus.d;
        }
        b(0.2F, 0.2F);
        this.bf = 0.2F;
        if (this.aI.i(i, j, k)) {
            int l = this.aI.e(i, j, k);
            int i1 = BedTile.d(l);
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
        this.v = new Vec3i(i, j, k);
        this.aP = this.aR = this.aQ = 0.0D;
        if (!this.aI.B)
            this.aI.y();
        return SleepStatus.a;
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
        Vec3i chunkcoordinates = this.v;
        Vec3i chunkcoordinates1 = this.v;
        if (chunkcoordinates != null && this.aI.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c) == Tile.T.bn) {
            BedTile.a(this.aI, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, false);
            Vec3i chunkcoordinates2 = BedTile.f(this.aI, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, 0);
            if (chunkcoordinates2 == null)
                chunkcoordinates2 = new Vec3i(chunkcoordinates.a, chunkcoordinates.b + 1, chunkcoordinates.c);
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

    public float M() {
        if (this.v != null) {
            int i = this.aI.e(this.v.a, this.v.b, this.v.c);
            int j = BedTile.d(i);
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

    public Vec3i Q() {
        return this.b;
    }

    public void a(Vec3i chunkcoordinates) {
        if (chunkcoordinates != null) {
            this.b = new Vec3i(chunkcoordinates);
        } else {
            this.b = null;
        }
    }

    public void a(Stat statbase) {
        a(statbase, 1);
    }

    public void a(Stat statbase, int i) {
    }

    protected void R() {
        super.R();
        a(Stats.jump, 1);
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
            int i = Math.round(MathsHelper.a(d * d + d1 * d1 + d2 * d2) * 100.0F);
            if (i > 0)
                a(Stats.diveOneCm, i);
        } else if (ag()) {
            int j = Math.round(MathsHelper.a(d * d + d2 * d2) * 100.0F);
            if (j > 0)
                a(Stats.swimOneCm, j);
        } else if (p()) {
            if (d1 > 0.0D)
                a(Stats.climbOneCm, (int) Math.round(d1 * 100.0D));
        } else if (this.aX) {
            int k = Math.round(MathsHelper.a(d * d + d2 * d2) * 100.0F);
            if (k > 0)
                a(Stats.walkOneCm, k);
        } else {
            int l = Math.round(MathsHelper.a(d * d + d2 * d2) * 100.0F);
            if (l > 25)
                a(Stats.flyOneCm, l);
        }
    }

    private void j(double d, double d1, double d2) {
        if (this.aH != null) {
            int i = Math.round(MathsHelper.a(d * d + d1 * d1 + d2 * d2) * 100.0F);
            if (i > 0)
                if (this.aH instanceof yl) {
                    a(Stats.minecartOneCm, i);
                    if (this.bN == null) {
                        this.bN = new Vec3i(MathsHelper.b(this.aM), MathsHelper.b(this.aN), MathsHelper.b(this.aO));
                    } else if (this.bN.a(MathsHelper.b(this.aM), MathsHelper.b(this.aN), MathsHelper.b(this.aO)) >= 1000.0D) {
                        a(Achievements.MINE_WOOD, 1);
                    }
                } else if (this.aH instanceof fz) {
                    a(Stats.boatOneCm, i);
                } else if (this.aH instanceof wh) {
                    a(Stats.pigOneCm, i);
                }
        }
    }

    protected void b(float f) {
        if (f >= 2.0F)
            a(Stats.fallOneCm, (int) Math.round(f * 100.0D));
        super.b(f);
    }

    public void a(LivingEntity entityliving) {
        if (entityliving instanceof Monster)
            a(Achievements.KILL_ENEMY);
    }

    public int c(ItemInstance itemstack) {
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
        if ((Items.hookshot.mainHookshot != null && Items.hookshot.mainHookshot.attachedToSurface) || (Items.hookshot.offHookshot != null && Items.hookshot.offHookshot.attachedToSurface))
            return 0.0D;
        return super.getGravity();
    }

    public boolean usingUmbrella() {
        return ((this.c.b() != null && (this.c.b()).c == Items.umbrella.bf) || (this.c.getOffhandItem() != null && (this.c.getOffhandItem()).c == Items.umbrella.bf));
    }
}
