package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.player.Player;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

import java.util.Random;

public class MixinDispenser extends TileEntity implements lw {
    private final Random b = new Random();
    private iz[] a = new iz[9];

    public int a() {
        return 9;
    }

    public iz f_(int i) {
        return this.a[i];
    }

    public iz a(int i, int j) {
        if (this.a[i] != null) {
            if ((this.a[i]).a <= j && (this.a[i]).a >= 0) {
                iz itemstack = this.a[i];
                this.a[i] = null;
                y_();
                return itemstack;
            }
            if ((this.a[i]).a < 0) {
                iz item = this.a[i].k();
                item.a = 1;
                return item;
            }
            iz itemstack1 = this.a[i].a(j);
            if ((this.a[i]).a == 0)
                this.a[i] = null;
            y_();
            return itemstack1;
        }
        return null;
    }

    public iz b() {
        int i = -1;
        int j = 1;
        for (int k = 0; k < this.a.length; k++) {
            if (this.a[k] != null && this.b.nextInt(j++) == 0)
                i = k;
        }
        if (i >= 0)
            return a(i, 1);
        return null;
    }

    public void a(int i, iz itemstack) {
        this.a[i] = itemstack;
        if (itemstack != null && itemstack.a > d())
            itemstack.a = d();
        y_();
    }

    public String c() {
        return "Trap";
    }

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        sp nbttaglist = nbttagcompound.l("Items");
        this.a = new iz[a()];
        for (int i = 0; i < nbttaglist.c(); i++) {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.a(i);
            int j = nbttagcompound1.c("Slot") & 0xFF;
            if (j >= 0 && j < this.a.length)
                this.a[j] = new iz(nbttagcompound1);
        }
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        sp nbttaglist = new sp();
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.a("Slot", (byte) i);
                this.a[i].a(nbttagcompound1);
                nbttaglist.a(nbttagcompound1);
            }
        }
        nbttagcompound.a("Items", (ij) nbttaglist);
    }

    public int d() {
        return 64;
    }

    public boolean a_(Player entityplayer) {
        if (this.d.b(this.e, this.f, this.g) != this)
            return false;
        return (entityplayer.g(this.e + 0.5D, this.f + 0.5D, this.g + 0.5D) <= 64.0D);
    }
}
