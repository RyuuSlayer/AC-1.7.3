package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.client.Minecraft;

public class PlayerContainer extends dw {
    public mq a;

    public lw b;

    public boolean c;

    public PlayerContainer(ix inventoryplayer) {
        this(inventoryplayer, true);
    }

    public PlayerContainer(ix inventoryplayer, boolean flag) {
        this.a = new mq(this, 2, 2);
        this.b = (lw)new wl();
        this.c = false;
        this.c = flag;
        if (Minecraft.minecraftInstance.f.x.allowsInventoryCrafting) {
            a((gp)new yv(inventoryplayer.d, (lw)this.a, this.b, 0, 144, 52));
            for (int i = 0; i < 2; i++) {
                for (int i1 = 0; i1 < 2; i1++)
                    a(new gp((lw)this.a, i1 + i * 2, 88 + i1 * 18, 26 + i * 18 + 16));
            }
        }
        for (int j = 0; j < 4; j++) {
            int j1 = j;
            a((gp)new pv(this, inventoryplayer, inventoryplayer.a() - 1 - j, 8, 8 + j * 18, j1));
        }
        for (int k = 0; k < 3; k++) {
            for (int k1 = 0; k1 < 9; k1++)
                a(new gp(inventoryplayer, k1 + (k + 1) * 9, 8 + k1 * 18, 84 + k * 18));
        }
        for (int l = 0; l < 9; l++)
            a(new gp(inventoryplayer, l, 8 + l * 18, 142));
        a((lw)this.a);
    }

    public void a(lw iinventory) {
        this.b.a(0, hk.a().a(this.a));
    }

    public void a(gs entityplayer) {
        super.a(entityplayer);
        for (int i = 0; i < 4; i++) {
            iz itemstack = this.a.f_(i);
            if (itemstack != null) {
                entityplayer.a(itemstack);
                this.a.a(i, null);
            }
        }
    }

    public boolean b(gs entityplayer) {
        return true;
    }

    public iz a(int i) {
        iz itemstack = null;
        gp slot = this.e.get(i);
        if (slot != null && slot.b()) {
            iz itemstack1 = slot.a();
            itemstack = itemstack1.k();
            if (i == 0) {
                a(itemstack1, 9, 45, true);
            } else if (i >= 9 && i < 36) {
                a(itemstack1, 36, 45, false);
            } else if (i >= 36 && i < 45) {
                a(itemstack1, 9, 36, false);
            } else {
                a(itemstack1, 9, 45, false);
            }
            if (itemstack1.a == 0) {
                slot.c(null);
            } else {
                slot.c();
            }
            if (itemstack1.a != itemstack.a) {
                slot.a(itemstack1);
            } else {
                return null;
            }
        }
        return itemstack;
    }
}
