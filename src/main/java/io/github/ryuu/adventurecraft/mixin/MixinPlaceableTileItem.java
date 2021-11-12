package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.client.colour.FoliageColour;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.FancyTile;
import net.minecraft.tile.Tile;

import java.util.Random;

public class MixinPlaceableTileItem extends FancyTile {
    private final int c;

    int[] a;

    protected MixinPlaceableTileItem(int i, int j) {
        super(i, j, ln.i, false);
        this.c = j;
        b(true);
    }

    public int b(int i) {
        if ((i & 0x1) == 1)
            return FoliageColour.a();
        if ((i & 0x2) == 2)
            return FoliageColour.b();
        return FoliageColour.c();
    }

    public int b(TileView iblockaccess, int i, int j, int k) {
        int l = iblockaccess.e(i, j, k);
        if ((l & 0x1) == 1)
            return FoliageColour.a();
        if ((l & 0x2) == 2)
            return FoliageColour.b();
        iblockaccess.a().a(i, k, 1, 1);
        double d = (iblockaccess.a()).a[0];
        double d1 = (iblockaccess.a()).b[0];
        return FoliageColour.a(d, d1);
    }

    public void b(Level world, int i, int j, int k) {
        int l = 1;
        int i1 = l + 1;
        if (world.a(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1))
            for (int j1 = -l; j1 <= l; j1++) {
                for (int k1 = -l; k1 <= l; k1++) {
                    for (int l1 = -l; l1 <= l; l1++) {
                        int i2 = world.a(i + j1, j + k1, k + l1);
                        if (i2 == Tile.L.bn) {
                            int j2 = world.e(i + j1, j + k1, k + l1);
                            world.e(i + j1, j + k1, k + l1, j2 | 0x8);
                        }
                    }
                }
            }
    }

    public void a(Level world, int i, int j, int k, Random random) {
    }

    private void h(Level world, int i, int j, int k) {
        g(world, i, j, k, world.e(i, j, k));
        world.f(i, j, k, 0);
    }

    public int a(Random random) {
        return (random.nextInt(20) != 0) ? 0 : 1;
    }

    public int a(int i, Random random) {
        return Tile.z.bn;
    }

    public void a(Level world, Player entityplayer, int i, int j, int k, int l) {
        if (!world.B && entityplayer.G() != null && (entityplayer.G()).c == ItemType.bc.bf) {
            entityplayer.a(jl.C[this.bn], 1);
            a(world, i, j, k, new ItemInstance(Tile.L.bn, 1, l & 0x3));
        } else {
            super.a(world, entityplayer, i, j, k, l);
        }
    }

    protected int b_(int i) {
        return i & 0x3;
    }

    public boolean c() {
        return !this.b;
    }

    public int a(int i, int j) {
        if ((j & 0x3) == 1)
            return this.bm + 80;
        return this.bm;
    }

    public void a(boolean flag) {
        this.b = flag;
        this.bm = this.c + (flag ? 0 : 1);
    }

    public void b(Level world, int i, int j, int k, Entity entity) {
        super.b(world, i, j, k, entity);
    }
}
