package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;

public class ScriptInventory {
    Inventory inv;

    ScriptInventory(Inventory i) {
        this.inv = i;
    }

    public int getSizeInventory() {
        return this.inv.a();
    }

    public String getName() {
        return this.inv.c();
    }

    public int getStackLimit() {
        return this.inv.d();
    }

    public ScriptItem getItemInSlot(int i) {
        ItemInstance item = this.inv.f_(i);
        if (item == null || item.c == 0)
            return null;
        return new ScriptItem(item);
    }

    public ScriptItem decrementItem(int slot, int amount) {
        ItemInstance item = this.inv.a(slot, amount);
        if (item == null || item.c == 0)
            return null;
        return new ScriptItem(item);
    }

    public void setSlot(int slot, ScriptItem item) {
        this.inv.a(slot, item.item);
    }

    public void emptySlot(int slot) {
        this.inv.a(slot, null);
    }
}
