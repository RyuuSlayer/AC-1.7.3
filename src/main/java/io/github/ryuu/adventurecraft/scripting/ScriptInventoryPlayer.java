package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayerInventory;
import io.github.ryuu.adventurecraft.mixin.entity.player.AccessPlayerInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;

@SuppressWarnings("unused")
public class ScriptInventoryPlayer extends ScriptInventory {

    PlayerInventory invPlayer;

    public ScriptInventoryPlayer(PlayerInventory i) {
        super(i);
        this.invPlayer = i;
    }

    public int getSlotContainingItem(int itemID) {
        int i = ((AccessPlayerInventory)this.invPlayer).invokeGetSlotWithItem(itemID);
        if (i == -1) {
            for (int j = 36; j < 40; ++j) {
                ItemInstance k = this.invPlayer.getInvItem(j);
                if (k == null || k.itemId != itemID) continue;
                return j;
            }
        }
        return i;
    }

    public int getSlotContainingItemDamage(int itemID, int damage) {
        for (int i = 0; i < this.invPlayer.getInvSize(); ++i) {
            ItemInstance j = this.invPlayer.getInvItem(i);
            if (j == null || j.itemId != itemID || j.getDamage() != damage) continue;
            return i;
        }
        return -1;
    }

    public void changeCurrentItem(int i) {
        this.invPlayer.scrollInHotbar(i);
    }

    public boolean consumeItem(int itemID) {
        return this.invPlayer.decreaseAmountOfItem(itemID);
    }

    public boolean consumeItemAmount(int itemID, int damage, int amount) {
        return ((ExPlayerInventory)this.invPlayer).consumeItemAmount(itemID, damage, amount);
    }

    public int getArmorValue() {
        return this.invPlayer.method_687();
    }

    public void dropAllItems() {
        this.invPlayer.dropInventory();
    }

    public ScriptItem getCurrentItem() {
        ItemInstance i = this.invPlayer.getHeldItem();
        if (i == null || i.itemId == 0) {
            return null;
        }
        return new ScriptItem(i);
    }

    public void setCurrentItem(int i) {
        this.invPlayer.method_691(i, false);
    }

    public ScriptItem getOffhandItem() {
        ItemInstance i = ((ExPlayerInventory)this.invPlayer).getOffhandItem();
        if (i == null || i.itemId == 0) {
            return null;
        }
        return new ScriptItem(i);
    }

    public void swapOffhandWithMain() {
        ((ExPlayerInventory)this.invPlayer).swapOffhandWithMain();
    }

    public boolean addItem(ScriptItem item) {
        return this.invPlayer.pickupItem(item.item);
    }

    public ScriptItem getCursorItem() {
        ItemInstance i = this.invPlayer.getCursorItem();
        if (i == null || i.itemId == 0) {
            return null;
        }
        return new ScriptItem(i);
    }

    public void setCursorItem(ScriptItem i) {
        if (i == null) {
            this.invPlayer.setCursorItem(null);
        } else {
            this.invPlayer.setCursorItem(i.item);
        }
    }
}
