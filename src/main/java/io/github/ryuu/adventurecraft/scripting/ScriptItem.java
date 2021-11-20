package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemInstance;

public class ScriptItem {

    public MixinItemInstance item;

    public ScriptItem(MixinItemInstance i) {
        this.item = i;
    }

    public ScriptItem(int itemID) {
        this.item = new MixinItemInstance(itemID, 1, 0);
    }

    public ScriptItem(int itemID, int quantity) {
        this.item = new MixinItemInstance(itemID, quantity, 0);
    }

    public ScriptItem(int itemID, int quantity, int damage) {
        this.item = new MixinItemInstance(itemID, quantity, damage);
    }

    public int getItemID() {
        return this.item.itemId;
    }

    public void setItemID(int itemID) {
        this.item.itemId = itemID;
    }

    public int getQuantity() {
        return this.item.count;
    }

    public void setQuantity(int i) {
        this.item.count = i;
    }

    public int getDamage() {
        return this.item.getDamage();
    }

    public void setDamage(int i) {
        this.item.setDamage(i);
    }

    public int getMaxDamage() {
        return this.item.method_723();
    }

    public ScriptItem copy() {
        return new ScriptItem(this.item.copy());
    }
}
