package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

import java.util.Random;

public class MixinDispenser extends TileEntity implements lw {
    private final Random b = new Random();
    private ItemInstance[] a = new ItemInstance[9];

    public int a() {
        return 9;
    }

    public ItemInstance f_(int i) {
        return this.a[i];
    }

    public ItemInstance a(int i, int j) {
        if (this.a[i] != null) {
            if ((this.a[i]).a <= j && (this.a[i]).a >= 0) {
                ItemInstance itemstack = this.a[i];
                this.a[i] = null;
                y_();
                return itemstack;
            }
            if ((this.a[i]).a < 0) {
                ItemInstance item = this.a[i].k();
                item.a = 1;
                return item;
            }
            ItemInstance itemstack1 = this.a[i].a(j);
            if ((this.a[i]).a == 0)
                this.a[i] = null;
            y_();
            return itemstack1;
        }
        return null;
    }

    public ItemInstance b() {
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

    public void a(int i, ItemInstance itemstack) {
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
        ListTag nbttaglist = nbttagcompound.l("Items");
        this.a = new ItemInstance[a()];
        for (int i = 0; i < nbttaglist.c(); i++) {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.a(i);
            int j = nbttagcompound1.c("Slot") & 0xFF;
            if (j >= 0 && j < this.a.length)
                this.a[j] = new ItemInstance(nbttagcompound1);
        }
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        ListTag nbttaglist = new ListTag();
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.put("Slot", (byte) i);
                this.a[i].a(nbttagcompound1);
                nbttaglist.a(nbttagcompound1);
            }
        }
        nbttagcompound.put("Items", (ij) nbttaglist);
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
