package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.item.ItemInstance;

public class ScriptItem {
    public ItemInstance item;

    public ScriptItem(ItemInstance i) {
        this.item = i;
    }

    public ScriptItem(int itemID) {
        this.item = new ItemInstance(itemID, 1, 0);
    }

    public ScriptItem(int itemID, int quantity) {
        this.item = new ItemInstance(itemID, quantity, 0);
    }

    public ScriptItem(int itemID, int quantity, int damage) {
        this.item = new ItemInstance(itemID, quantity, damage);
    }

    public int getItemID() {
        return this.item.c;
    }

    public void setItemID(int itemID) {
        this.item.c = itemID;
    }

    public int getQuantity() {
        return this.item.a;
    }

    public void setQuantity(int i) {
        this.item.a = i;
    }

    public int getDamage() {
        return this.item.i();
    }

    public void setDamage(int i) {
        this.item.b(i);
    }

    public int getMaxDamage() {
        return this.item.j();
    }

    public ScriptItem copy() {
        return new ScriptItem(this.item.k());
    }
}
