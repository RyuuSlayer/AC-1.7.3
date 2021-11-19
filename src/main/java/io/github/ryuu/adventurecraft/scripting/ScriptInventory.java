package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;

public class ScriptInventory {
    Inventory inv;

    ScriptInventory(Inventory i) {
        this.inv = i;
    }

    public int getSizeInventory() {
        return this.inv.getInvSize();
    }

    public String getName() {
        return this.inv.getContainerName();
    }

    public int getStackLimit() {
        return this.inv.getMaxItemCount();
    }

    public ScriptItem getItemInSlot(int i) {
        ItemInstance item = this.inv.getInvItem(i);
        if (item == null || item.itemId == 0)
            return null;
        return new ScriptItem(item);
    }

    public ScriptItem decrementItem(int slot, int amount) {
        ItemInstance item = this.inv.takeInvItem(slot, amount);
        if (item == null || item.itemId == 0)
            return null;
        return new ScriptItem(item);
    }

    public void setSlot(int slot, ScriptItem item) {
        this.inv.setInvItem(slot, item.item);
    }

    public void emptySlot(int slot) {
        this.inv.setInvItem(slot, null);
    }
}