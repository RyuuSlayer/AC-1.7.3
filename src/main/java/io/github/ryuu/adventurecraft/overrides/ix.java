package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.items.IItemReload;
import io.github.ryuu.adventurecraft.items.Items;

public class ix implements lw {
    public iz[] a;

    public iz[] b;

    public int c;

    public gs d;

    private iz f;

    public boolean e;

    public int offhandItem;

    public int[] consumeInventory;

    public ix(gs entityplayer) {
        this.a = new iz[36];
        this.b = new iz[4];
        this.c = 0;
        this.e = false;
        this.d = entityplayer;
        this.offhandItem = 1;
        this.consumeInventory = new int[36];
    }

    public iz b() {
        if (this.c < 9 && this.c >= 0)
            return this.a[this.c];
        return null;
    }

    public iz getOffhandItem() {
        return this.a[this.offhandItem];
    }

    public void swapOffhandWithMain() {
        int t = this.c;
        this.c = this.offhandItem;
        this.offhandItem = t;
    }

    public int f(int i) {
        for (int j = 0; j < this.a.length; j++) {
            if (this.a[j] != null && (this.a[j]).c == i)
                return j;
        }
        return -1;
    }

    private int d(iz itemstack) {
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i] != null && (this.a[i]).c == itemstack.c && this.a[i].d() && (this.a[i]).a < this.a[i].c() && (this.a[i]).a < d() && (!this.a[i].f() || this.a[i].i() == itemstack.i()))
                return i;
        }
        return -1;
    }

    private int j() {
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i] == null)
                return i;
        }
        return -1;
    }

    public void a(int i, boolean flag) {
        int j = f(i);
        if (j >= 0 && j < 9) {
            this.c = j;
            return;
        }
    }

    public void b(int i) {
        if (i > 0)
            i = 1;
        if (i < 0)
            i = -1;
        int prevCurrentItem = this.c;
        for (this.c -= i; this.c < 0; this.c += 9) ;
        for (; this.c >= 9; this.c -= 9) ;
        if (this.c == this.offhandItem)
            this.offhandItem = prevCurrentItem;
    }

    private int e(iz itemstack) {
        int i = itemstack.c;
        int j = itemstack.a;
        int k = d(itemstack);
        if (k < 0)
            k = j();
        if (k < 0)
            return j;
        if (this.a[k] == null) {
            this.a[k] = new iz(i, 0, itemstack.i());
            ItemType.c[i].onAddToSlot(this.d, k, itemstack.i());
        }
        int l = j;
        if (l > this.a[k].c() - (this.a[k]).a)
            l = this.a[k].c() - (this.a[k]).a;
        if (l > d() - (this.a[k]).a)
            l = d() - (this.a[k]).a;
        if (l == 0)
            return j;
        j -= l;
        (this.a[k]).a += l;
        (this.a[k]).b = 5;
        return j;
    }

    public void e() {
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i] != null) {
                iz itemStack = this.a[i];
                itemStack.a(this.d.aI, this.d, i, (this.c == i));
                if (itemStack.timeLeft > 0)
                    itemStack.timeLeft--;
                if ((i == this.c || i == this.offhandItem) && itemStack.timeLeft == 0 && itemStack.isReloading) {
                    IItemReload item = (IItemReload) ItemType.c[itemStack.c];
                    item.reload(itemStack, this.d.aI, this.d);
                }
                if (itemStack.i() > 0 && (ItemType.c[itemStack.c]).decrementDamage)
                    itemStack.b(itemStack.i() - 1);
            }
        }
    }

    public boolean c(int i) {
        int j = f(i);
        if (j < 0)
            return false;
        if (--(this.a[j]).a == 0) {
            int d = this.a[j].i();
            this.a[j] = null;
            ItemType.c[i].onRemovedFromSlot(this.d, j, d);
        }
        return true;
    }

    public boolean a(iz itemstack) {
        if (itemstack.c == Items.heart.bf) {
            itemstack.a = 0;
            this.d.c(4);
            return true;
        }
        if (itemstack.c == Items.heartContainer.bf) {
            itemstack.a = 0;
            this.d.maxHealth += 4;
            this.d.c(this.d.maxHealth);
            return true;
        }
        if (itemstack.c == Items.heartPiece.bf) {
            itemstack.a = 0;
            this.d.numHeartPieces++;
            if (this.d.numHeartPieces >= 4) {
                this.d.numHeartPieces = 0;
                this.d.maxHealth += 4;
                this.d.c(this.d.maxHealth);
            }
            return true;
        }
        if (!itemstack.g()) {
            itemstack.a = e(itemstack);
            if (itemstack.a == 0)
                return true;
        }
        int i = j();
        if (i >= 0) {
            this.a[i] = itemstack.k();
            (this.a[i]).b = 5;
            itemstack.a = 0;
            ItemType.c[itemstack.c].onAddToSlot(this.d, i, itemstack.i());
            return true;
        }
        return false;
    }

    public iz a(int i, int j) {
        int slotID = i;
        iz[] aitemstack = this.a;
        if (i >= this.a.length) {
            aitemstack = this.b;
            i -= this.a.length;
        }
        if (aitemstack[i] != null) {
            if ((aitemstack[i]).a <= j) {
                iz itemstack = aitemstack[i];
                aitemstack[i] = null;
                ItemType.c[itemstack.c].onRemovedFromSlot(this.d, slotID, itemstack.i());
                return itemstack;
            }
            iz itemstack1 = aitemstack[i].a(j);
            if ((aitemstack[i]).a == 0) {
                aitemstack[i] = null;
                ItemType.c[itemstack1.c].onRemovedFromSlot(this.d, slotID, itemstack1.i());
            }
            return itemstack1;
        }
        return null;
    }

    public void a(int i, iz itemstack) {
        int slotNumber = i;
        iz[] aitemstack = this.a;
        if (i >= aitemstack.length) {
            i -= aitemstack.length;
            aitemstack = this.b;
        }
        iz prevItem = aitemstack[i];
        aitemstack[i] = itemstack;
        if (prevItem != null)
            ItemType.c[prevItem.c].onRemovedFromSlot(this.d, slotNumber, prevItem.i());
        if (itemstack != null)
            ItemType.c[itemstack.c].onAddToSlot(this.d, slotNumber, itemstack.i());
    }

    public float a(Tile block) {
        float f = 1.0F;
        if (this.a[this.c] != null)
            f *= this.a[this.c].a(block);
        return f;
    }

    public sp a(sp nbttaglist) {
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i] != null) {
                nu nbttagcompound = new nu();
                nbttagcompound.a("Slot", (byte) i);
                this.a[i].a(nbttagcompound);
                nbttaglist.a(nbttagcompound);
            }
        }
        for (int j = 0; j < this.b.length; j++) {
            if (this.b[j] != null) {
                nu nbttagcompound1 = new nu();
                nbttagcompound1.a("Slot", (byte) (j + 100));
                this.b[j].a(nbttagcompound1);
                nbttaglist.a(nbttagcompound1);
            }
        }
        return nbttaglist;
    }

    public void b(sp nbttaglist) {
        this.a = new iz[36];
        this.b = new iz[4];
        for (int i = 0; i < nbttaglist.c(); i++) {
            nu nbttagcompound = (nu) nbttaglist.a(i);
            int j = nbttagcompound.c("Slot") & 0xFF;
            iz itemstack = new iz(nbttagcompound);
            if (itemstack.a() != null) {
                if (j >= 0 && j < this.a.length)
                    this.a[j] = itemstack;
                if (j >= 100 && j < this.b.length + 100)
                    this.b[j - 100] = itemstack;
            }
        }
    }

    public int a() {
        return this.a.length + 4;
    }

    public iz f_(int i) {
        iz[] aitemstack = this.a;
        if (i >= aitemstack.length) {
            i -= aitemstack.length;
            aitemstack = this.b;
        }
        return aitemstack[i];
    }

    public String c() {
        return "Inventory";
    }

    public int d() {
        return 64;
    }

    public int a(sn entity) {
        iz itemstack = f_(this.c);
        if (itemstack != null)
            return itemstack.a(entity);
        return 1;
    }

    public boolean b(Tile block) {
        if (block.bA.i())
            return true;
        iz itemstack = f_(this.c);
        if (itemstack != null)
            return itemstack.b(block);
        return false;
    }

    public iz d(int i) {
        return this.b[i];
    }

    public int f() {
        float i = 0.0F;
        int j = 0;
        int k = 0;
        for (int l = 0; l < this.b.length; l++) {
            if (this.b[l] != null && this.b[l].a() instanceof wa) {
                int i1 = this.b[l].j();
                int j1 = this.b[l].h();
                int k1 = i1 - j1;
                j += k1;
                k += i1;
                i += ((wa) this.b[l].a()).bl;
            }
        }
        if (k == 0)
            return 0;
        return (int) ((i - 1.0F) * j) / k + 1;
    }

    public void e(int i) {
        for (int j = 0; j < this.b.length; j++) {
            if (this.b[j] != null && this.b[j].a() instanceof wa) {
                this.b[j].a(i, this.d);
                if ((this.b[j]).a == 0) {
                    int prevItemID = (this.b[j]).c;
                    int prevDamage = this.b[j].i();
                    this.b[j].a(this.d);
                    this.b[j] = null;
                    ItemType.c[prevItemID].onRemovedFromSlot(this.d, j + this.a.length, prevDamage);
                }
            }
        }
    }

    public void g() {
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i] != null) {
                iz prevItem = this.a[i];
                this.d.a(this.a[i], true);
                this.a[i] = null;
                ItemType.c[prevItem.c].onRemovedFromSlot(this.d, i, prevItem.i());
            }
        }
        for (int j = 0; j < this.b.length; j++) {
            if (this.b[j] != null) {
                iz prevItem = this.b[j];
                this.d.a(this.b[j], true);
                this.b[j] = null;
                ItemType.c[prevItem.c].onRemovedFromSlot(this.d, j + this.a.length, prevItem.i());
            }
        }
    }

    public void y_() {
        this.e = true;
    }

    public void b(iz itemstack) {
        this.f = itemstack;
        this.d.b(itemstack);
    }

    public iz i() {
        return this.f;
    }

    public boolean a_(gs entityplayer) {
        if (this.d.be)
            return false;
        return (entityplayer.g(this.d) <= 64.0D);
    }

    public boolean c(iz itemstack) {
        for (int i = 0; i < this.b.length; i++) {
            if (this.b[i] != null && this.b[i].c(itemstack))
                return true;
        }
        for (int j = 0; j < this.a.length; j++) {
            if (this.a[j] != null && this.a[j].c(itemstack))
                return true;
        }
        return false;
    }

    public boolean consumeItemAmount(int itemID, int damage, int amount) {
        int slotsToUse = 0;
        int amountLeft = amount;
        int i;
        for (i = 0; i < 36; i++) {
            if (this.a[i] != null && (this.a[i]).c == itemID && this.a[i].i() == damage) {
                this.consumeInventory[slotsToUse++] = i;
                amountLeft -= (this.a[i]).a;
                if (amountLeft <= 0)
                    break;
            }
        }
        if (amountLeft > 0)
            return false;
        for (i = 0; i < slotsToUse; i++) {
            int slot = this.consumeInventory[i];
            if ((this.a[slot]).a > amount) {
                (this.a[slot]).a -= amount;
            } else {
                amount -= (this.a[slot]).a;
                (this.a[slot]).a = 0;
                this.a[slot] = null;
                ItemType.c[itemID].onRemovedFromSlot(this.d, slot, damage);
            }
        }
        return true;
    }
}
