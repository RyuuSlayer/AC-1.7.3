package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.entities.EntityArrowBomb;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.projectile.Arrow;
import net.minecraft.entity.projectile.ThrownEgg;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.entity.Dispenser;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.maths.MathsHelper;

import java.util.Random;

public class MixinDispenserTile extends rw {
    private final Random a;

    protected MixinDispenserTile(int i) {
        super(i, ln.e);
        this.a = new Random();
        this.bm = 45;
    }

    public int e() {
        return 4;
    }

    public int a(int i, Random random) {
        return uu.Q.bn;
    }

    public void c(Level world, int i, int j, int k) {
        super.c(world, i, j, k);
        h(world, i, j, k);
    }

    private void h(Level world, int i, int j, int k) {
        if (world.B)
            return;
        int l = world.a(i, j, k - 1);
        int i1 = world.a(i, j, k + 1);
        int j1 = world.a(i - 1, j, k);
        int k1 = world.a(i + 1, j, k);
        byte byte0 = 3;
        if (uu.o[l] && !uu.o[i1])
            byte0 = 3;
        if (uu.o[i1] && !uu.o[l])
            byte0 = 2;
        if (uu.o[j1] && !uu.o[k1])
            byte0 = 5;
        if (uu.o[k1] && !uu.o[j1])
            byte0 = 4;
        world.d(i, j, k, byte0);
    }

    public int a(TileView iblockaccess, int i, int j, int k, int l) {
        if (l == 1)
            return this.bm + 17;
        if (l == 0)
            return this.bm + 17;
        int i1 = iblockaccess.e(i, j, k);
        if (l != i1)
            return this.bm;
        return this.bm + 1;
    }

    public int a(int i) {
        if (i == 1)
            return this.bm + 17;
        if (i == 0)
            return this.bm + 17;
        if (i == 3)
            return this.bm + 1;
        return this.bm;
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (!DebugMode.active)
            return false;
        if (world.B)
            return true;
        Dispenser tileentitydispenser = (Dispenser) world.b(i, j, k);
        entityplayer.a(tileentitydispenser);
        return true;
    }

    private void c(Level world, int i, int j, int k, Random random) {
        int l = world.e(i, j, k);
        int i1 = 0;
        int j1 = 0;
        if (l == 3) {
            j1 = 1;
        } else if (l == 2) {
            j1 = -1;
        } else if (l == 5) {
            i1 = 1;
        } else {
            i1 = -1;
        }
        Dispenser tileentitydispenser = (Dispenser) world.b(i, j, k);
        ItemInstance itemstack = tileentitydispenser.b();
        double d = i + i1 * 0.6D + 0.5D;
        double d1 = j + 0.5D;
        double d2 = k + j1 * 0.6D + 0.5D;
        if (itemstack == null) {
            world.e(1001, i, j, k, 0);
        } else {
            if (itemstack.c == ItemType.j.bf) {
                Arrow entityarrow = new Arrow(world, d, d1, d2);
                entityarrow.a(i1, 0.10000000149011612D, j1, 1.1F, 6.0F);
                entityarrow.a = true;
                world.b((Entity) entityarrow);
                world.e(1002, i, j, k, 0);
            } else if (itemstack.c == ItemType.aN.bf) {
                ThrownEgg entityegg = new ThrownEgg(world, d, d1, d2);
                entityegg.a(i1, 0.10000000149011612D, j1, 1.1F, 6.0F);
                world.b((Entity) entityegg);
                world.e(1002, i, j, k, 0);
            } else if (itemstack.c == ItemType.aB.bf) {
                by entitysnowball = new by(world, d, d1, d2);
                entitysnowball.a(i1, 0.10000000149011612D, j1, 1.1F, 6.0F);
                world.b((Entity) entitysnowball);
                world.e(1002, i, j, k, 0);
            } else if (itemstack.c == Items.bombArow.bf) {
                EntityArrowBomb aC_EntityArrowBomb = new EntityArrowBomb(world, d, d1, d2);
                aC_EntityArrowBomb.a(i1, 0.10000000149011612D, j1, 1.1F, 6.0F);
                world.b((Entity) aC_EntityArrowBomb);
                world.e(1002, i, j, k, 0);
            } else {
                ItemEntity entityitem = new ItemEntity(world, d, d1 - 0.3D, d2, itemstack);
                double d3 = random.nextDouble() * 0.1D + 0.2D;
                entityitem.aP = i1 * d3;
                entityitem.aQ = 0.20000000298023224D;
                entityitem.aR = j1 * d3;
                entityitem.aP += random.nextGaussian() * 0.007499999832361937D * 6.0D;
                entityitem.aQ += random.nextGaussian() * 0.007499999832361937D * 6.0D;
                entityitem.aR += random.nextGaussian() * 0.007499999832361937D * 6.0D;
                world.b((Entity) entityitem);
                world.e(1000, i, j, k, 0);
            }
            world.e(2000, i, j, k, i1 + 1 + (j1 + 1) * 3);
        }
    }

    public void b(Level world, int i, int j, int k, int l) {
        if (l > 0 && uu.m[l].f()) {
            boolean flag = (world.s(i, j, k) || world.s(i, j + 1, k));
            if (flag)
                world.c(i, j, k, this.bn, e());
        }
    }

    public void a(Level world, int i, int j, int k, Random random) {
        if (world.s(i, j, k) || world.s(i, j + 1, k))
            c(world, i, j, k, random);
    }

    protected TileEntity a_() {
        return new Dispenser();
    }

    public void a(Level world, int i, int j, int k, LivingEntity entityliving) {
        int l = MathsHelper.b((entityliving.aS * 4.0F / 360.0F) + 0.5D) & 0x3;
        if (l == 0)
            world.d(i, j, k, 2);
        if (l == 1)
            world.d(i, j, k, 5);
        if (l == 2)
            world.d(i, j, k, 3);
        if (l == 3)
            world.d(i, j, k, 4);
    }

    public void b(Level world, int i, int j, int k) {
        Dispenser tileentitydispenser = (Dispenser) world.b(i, j, k);
        for (int l = 0; l < tileentitydispenser.a(); l++) {
            ItemInstance itemstack = tileentitydispenser.f_(l);
            if (itemstack != null) {
                float f = this.a.nextFloat() * 0.8F + 0.1F;
                float f1 = this.a.nextFloat() * 0.8F + 0.1F;
                float f2 = this.a.nextFloat() * 0.8F + 0.1F;
                while (itemstack.a > 0) {
                    int i1 = this.a.nextInt(21) + 10;
                    if (i1 > itemstack.a)
                        i1 = itemstack.a;
                    itemstack.a -= i1;
                    ItemEntity entityitem = new ItemEntity(world, (i + f), (j + f1), (k + f2), new ItemInstance(itemstack.c, i1, itemstack.i()));
                    float f3 = 0.05F;
                    entityitem.aP = ((float) this.a.nextGaussian() * f3);
                    entityitem.aQ = ((float) this.a.nextGaussian() * f3 + 0.2F);
                    entityitem.aR = ((float) this.a.nextGaussian() * f3);
                    world.b((Entity) entityitem);
                }
            }
        }
        super.b(world, i, j, k);
    }
}