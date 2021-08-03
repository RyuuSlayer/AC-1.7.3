package io.github.ryuu.adventurecraft.scripting;

import ix;
import iz;
import lw;

public class ScriptInventoryPlayer extends ScriptInventory {
    ix invPlayer;

    ScriptInventoryPlayer(ix i) {
        super((lw) i);
        this.invPlayer = i;
    }

    public int getSlotContainingItem(int itemID) {
        int i = this.invPlayer.f(itemID);
        if (i == -1)
            for (int j = 36; j < 40; j++) {
                iz k = this.invPlayer.f_(j);
                if (k != null && k.c == itemID)
                    return j;
            }
        return i;
    }

    public int getSlotContainingItemDamage(int itemID, int damage) {
        for (int i = 0; i < this.invPlayer.a(); i++) {
            iz j = this.invPlayer.f_(i);
            if (j != null && j.c == itemID && j.i() == damage)
                return i;
        }
        return -1;
    }

    public void setCurrentItem(int i) {
        this.invPlayer.a(i, false);
    }

    public void changeCurrentItem(int i) {
        this.invPlayer.b(i);
    }

    public boolean consumeItem(int itemID) {
        return this.invPlayer.c(itemID);
    }

    public boolean consumeItemAmount(int itemID, int damage, int amount) {
        return this.invPlayer.consumeItemAmount(itemID, damage, amount);
    }

    public int getArmorValue() {
        return this.invPlayer.f();
    }

    public void dropAllItems() {
        this.invPlayer.g();
    }

    public ScriptItem getCurrentItem() {
        iz i = this.invPlayer.b();
        if (i == null || i.c == 0)
            return null;
        return new ScriptItem(i);
    }

    public ScriptItem getOffhandItem() {
        iz i = this.invPlayer.getOffhandItem();
        if (i == null || i.c == 0)
            return null;
        return new ScriptItem(i);
    }

    public void swapOffhandWithMain() {
        this.invPlayer.swapOffhandWithMain();
    }

    public boolean addItem(ScriptItem item) {
        return this.invPlayer.a(item.item);
    }

    public ScriptItem getCursorItem() {
        iz i = this.invPlayer.i();
        if (i == null || i.c == 0)
            return null;
        return new ScriptItem(i);
    }

    public void setCursorItem(ScriptItem i) {
        if (i == null) {
            this.invPlayer.b(null);
        } else {
            this.invPlayer.b(i.item);
        }
    }
}
