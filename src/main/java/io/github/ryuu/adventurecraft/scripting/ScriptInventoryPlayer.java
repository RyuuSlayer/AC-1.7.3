package io.github.ryuu.adventurecraft.scripting;

public class ScriptInventoryPlayer extends ScriptInventory {

    MixinPlayerInventory invPlayer;

    ScriptInventoryPlayer(MixinPlayerInventory i) {
        super(i);
        this.invPlayer = i;
    }

    public int getSlotContainingItem(int itemID) {
        int i = this.invPlayer.getSlotWithItem(itemID);
        if (i == -1) {
            for (int j = 36; j < 40; ++j) {
                MixinItemInstance k = this.invPlayer.getInvItem(j);
                if (k == null || k.itemId != itemID) continue;
                return j;
            }
        }
        return i;
    }

    public int getSlotContainingItemDamage(int itemID, int damage) {
        for (int i = 0; i < this.invPlayer.getInvSize(); ++i) {
            MixinItemInstance j = this.invPlayer.getInvItem(i);
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
        return this.invPlayer.consumeItemAmount(itemID, damage, amount);
    }

    public int getArmorValue() {
        return this.invPlayer.method_687();
    }

    public void dropAllItems() {
        this.invPlayer.dropInventory();
    }

    public ScriptItem getCurrentItem() {
        MixinItemInstance i = this.invPlayer.getHeldItem();
        if (i == null || i.itemId == 0) {
            return null;
        }
        return new ScriptItem(i);
    }

    public void setCurrentItem(int i) {
        this.invPlayer.method_691(i, false);
    }

    public ScriptItem getOffhandItem() {
        MixinItemInstance i = this.invPlayer.getOffhandItem();
        if (i == null || i.itemId == 0) {
            return null;
        }
        return new ScriptItem(i);
    }

    public void swapOffhandWithMain() {
        this.invPlayer.swapOffhandWithMain();
    }

    public boolean addItem(ScriptItem item) {
        return this.invPlayer.pickupItem(item.item);
    }

    public ScriptItem getCursorItem() {
        MixinItemInstance i = this.invPlayer.getCursorItem();
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
