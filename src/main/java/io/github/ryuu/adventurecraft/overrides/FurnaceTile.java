package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.entity.FurnaceEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;

import java.util.Random;

public class FurnaceTile extends TileWithEntity {
    private static boolean c = false;
    private final Random a;
    private final boolean b;

    protected FurnaceTile(int i, boolean flag) {
        super(i, ln.e);
        this.a = new Random();
        this.b = flag;
        this.bm = 45;
    }

    public static void a(boolean flag, Level world, int i, int j, int k) {
        int l = world.e(i, j, k);
        TileEntity tileentity = world.b(i, j, k);
        c = true;
        if (flag) {
            world.f(i, j, k, Tile.aD.bn);
        } else {
            world.f(i, j, k, Tile.aC.bn);
        }
        c = false;
        world.d(i, j, k, l);
        tileentity.j();
        world.a(i, j, k, tileentity);
        tileentity.j();
    }

    public int a(int i, Random random) {
        return Tile.aC.bn;
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
        if (Tile.o[l] && !Tile.o[i1])
            byte0 = 3;
        if (Tile.o[i1] && !Tile.o[l])
            byte0 = 2;
        if (Tile.o[j1] && !Tile.o[k1])
            byte0 = 5;
        if (Tile.o[k1] && !Tile.o[j1])
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
        if (this.b)
            return this.bm + 16;
        return this.bm - 1;
    }

    public void b(Level world, int i, int j, int k, Random random) {
        if (!this.b)
            return;
        int l = world.e(i, j, k);
        float f = i + 0.5F;
        float f1 = j + 0.0F + random.nextFloat() * 6.0F / 16.0F;
        float f2 = k + 0.5F;
        float f3 = 0.52F;
        float f4 = random.nextFloat() * 0.6F - 0.3F;
        if (l == 4) {
            world.a("smoke", (f - f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
            world.a("flame", (f - f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
        } else if (l == 5) {
            world.a("smoke", (f + f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
            world.a("flame", (f + f3), f1, (f2 + f4), 0.0D, 0.0D, 0.0D);
        } else if (l == 2) {
            world.a("smoke", (f + f4), f1, (f2 - f3), 0.0D, 0.0D, 0.0D);
            world.a("flame", (f + f4), f1, (f2 - f3), 0.0D, 0.0D, 0.0D);
        } else if (l == 3) {
            world.a("smoke", (f + f4), f1, (f2 + f3), 0.0D, 0.0D, 0.0D);
            world.a("flame", (f + f4), f1, (f2 + f3), 0.0D, 0.0D, 0.0D);
        }
    }

    public int a(int i) {
        if (i == 1)
            return this.bm + 17;
        if (i == 0)
            return this.bm + 17;
        if (i == 3)
            return this.bm - 1;
        return this.bm;
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (world.B)
            return true;
        FurnaceEntity tileentityfurnace = (FurnaceEntity) world.b(i, j, k);
        entityplayer.a(tileentityfurnace);
        return true;
    }

    protected TileEntity a_() {
        return (TileEntity) new FurnaceEntity();
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
        if (!c) {
            FurnaceEntity tileentityfurnace = (FurnaceEntity) world.b(i, j, k);
            for (int l = 0; l < tileentityfurnace.a(); l++) {
                ItemInstance itemstack = tileentityfurnace.f_(l);
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
        }
        super.b(world, i, j, k);
    }
}
