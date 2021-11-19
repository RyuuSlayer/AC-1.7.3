/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.entity.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.armour.ArmourItem;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinPlayer;
import io.github.ryuu.adventurecraft.mixin.item.MixinTile;
import io.github.ryuu.adventurecraft.mixin.item.MixinCompoundTag;
import io.github.ryuu.adventurecraft.mixin.item.MixinEntity;
import io.github.ryuu.adventurecraft.mixin.item.MixinItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinArmourItem;

@Mixin(PlayerInventory.class)
public class MixinPlayerInventory implements Inventory {

    @Shadow()
    public MixinItemInstance[] main = new MixinItemInstance[36];

    public MixinItemInstance[] armour = new MixinItemInstance[4];

    public int selectedHotbarSlot = 0;

    public MixinPlayer player;

    private MixinItemInstance cursorStack;

    public boolean dirty = false;

    public int offhandItem;

    public int[] consumeInventory;

    public MixinPlayerInventory(MixinPlayer player) {
        this.player = player;
        this.offhandItem = 1;
        this.consumeInventory = new int[36];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance getHeldItem() {
        if (this.selectedHotbarSlot < 9 && this.selectedHotbarSlot >= 0) {
            return this.main[this.selectedHotbarSlot];
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance getOffhandItem() {
        return this.main[this.offhandItem];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void swapOffhandWithMain() {
        int t = this.selectedHotbarSlot;
        this.selectedHotbarSlot = this.offhandItem;
        this.offhandItem = t;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static int method_684() {
        return 9;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getSlotWithItem(int id) {
        for (int j = 0; j < this.main.length; ++j) {
            if (this.main[j] == null || this.main[j].itemId != id)
                continue;
            return j;
        }
        return -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int findItem(MixinItemInstance item) {
        for (int i = 0; i < this.main.length; ++i) {
            if (this.main[i] == null || this.main[i].itemId != item.itemId || !this.main[i].method_715() || this.main[i].count >= this.main[i].method_709() || this.main[i].count >= this.getMaxItemCount() || this.main[i].method_719() && this.main[i].getDamage() != item.getDamage())
                continue;
            return i;
        }
        return -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int findNextEmptySlot() {
        for (int i = 0; i < this.main.length; ++i) {
            if (this.main[i] != null)
                continue;
            return i;
        }
        return -1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_691(int i, boolean flag) {
        int j = this.getSlotWithItem(i);
        if (j >= 0 && j < 9) {
            this.selectedHotbarSlot = j;
            return;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void scrollInHotbar(int i) {
        if (i > 0) {
            i = 1;
        }
        if (i < 0) {
            i = -1;
        }
        int prevCurrentItem = this.selectedHotbarSlot;
        this.selectedHotbarSlot -= i;
        while (this.selectedHotbarSlot < 0) {
            this.selectedHotbarSlot += 9;
        }
        while (this.selectedHotbarSlot >= 9) {
            this.selectedHotbarSlot -= 9;
        }
        if (this.selectedHotbarSlot == this.offhandItem) {
            this.offhandItem = prevCurrentItem;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    private int method_685(MixinItemInstance itemstack) {
        int l;
        int i = itemstack.itemId;
        int j = itemstack.count;
        int k = this.findItem(itemstack);
        if (k < 0) {
            k = this.findNextEmptySlot();
        }
        if (k < 0) {
            return j;
        }
        if (this.main[k] == null) {
            this.main[k] = new MixinItemInstance(i, 0, itemstack.getDamage());
            ItemType.byId[i].onAddToSlot(this.player, k, itemstack.getDamage());
        }
        if ((l = j) > this.main[k].method_709() - this.main[k].count) {
            l = this.main[k].method_709() - this.main[k].count;
        }
        if (l > this.getMaxItemCount() - this.main[k].count) {
            l = this.getMaxItemCount() - this.main[k].count;
        }
        if (l == 0) {
            return j;
        }
        this.main[k].count += l;
        this.main[k].cooldown = 5;
        return j -= l;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void inventoryTick() {
        for (int i = 0; i < this.main.length; ++i) {
            if (this.main[i] == null)
                continue;
            MixinItemInstance itemStack = this.main[i];
            itemStack.inventoryTick(this.player.level, this.player, i, this.selectedHotbarSlot == i);
            if (itemStack.timeLeft > 0) {
                --itemStack.timeLeft;
            }
            if ((i == this.selectedHotbarSlot || i == this.offhandItem) && itemStack.timeLeft == 0 && itemStack.isReloading) {
                IItemReload item = (IItemReload) ((Object) ItemType.byId[itemStack.itemId]);
                item.reload(itemStack, this.player.level, this.player);
            }
            if (itemStack.getDamage() <= 0 || !ItemType.byId[itemStack.itemId].decrementDamage)
                continue;
            itemStack.setDamage(itemStack.getDamage() - 1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean decreaseAmountOfItem(int i) {
        int j = this.getSlotWithItem(i);
        if (j < 0) {
            return false;
        }
        if (--this.main[j].count == 0) {
            int d = this.main[j].getDamage();
            this.main[j] = null;
            ItemType.byId[i].onRemovedFromSlot(this.player, j, d);
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean pickupItem(MixinItemInstance item) {
        int i;
        if (item.itemId == Items.heart.id) {
            item.count = 0;
            this.player.addHealth(4);
            return true;
        }
        if (item.itemId == Items.heartContainer.id) {
            item.count = 0;
            this.player.maxHealth += 4;
            this.player.addHealth(this.player.maxHealth);
            return true;
        }
        if (item.itemId == Items.heartPiece.id) {
            item.count = 0;
            ++this.player.numHeartPieces;
            if (this.player.numHeartPieces >= 4) {
                this.player.numHeartPieces = 0;
                this.player.maxHealth += 4;
                this.player.addHealth(this.player.maxHealth);
            }
            return true;
        }
        if (!item.method_720()) {
            item.count = this.method_685(item);
            if (item.count == 0) {
                return true;
            }
        }
        if ((i = this.findNextEmptySlot()) >= 0) {
            this.main[i] = item.copy();
            this.main[i].cooldown = 5;
            item.count = 0;
            ItemType.byId[item.itemId].onAddToSlot(this.player, i, item.getDamage());
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public MixinItemInstance takeInvItem(int index, int j) {
        int slotID = index;
        MixinItemInstance[] aitemstack = this.main;
        if (index >= this.main.length) {
            aitemstack = this.armour;
            index -= this.main.length;
        }
        if (aitemstack[index] != null) {
            if (aitemstack[index].count <= j) {
                MixinItemInstance itemstack = aitemstack[index];
                aitemstack[index] = null;
                ItemType.byId[itemstack.itemId].onRemovedFromSlot(this.player, slotID, itemstack.getDamage());
                return itemstack;
            }
            MixinItemInstance itemstack1 = aitemstack[index].split(j);
            if (aitemstack[index].count == 0) {
                aitemstack[index] = null;
                ItemType.byId[itemstack1.itemId].onRemovedFromSlot(this.player, slotID, itemstack1.getDamage());
            }
            return itemstack1;
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void setInvItem(int i, MixinItemInstance itemstack) {
        int slotNumber = i;
        MixinItemInstance[] aitemstack = this.main;
        if (i >= aitemstack.length) {
            i -= aitemstack.length;
            aitemstack = this.armour;
        }
        MixinItemInstance prevItem = aitemstack[i];
        aitemstack[i] = itemstack;
        if (prevItem != null) {
            ItemType.byId[prevItem.itemId].onRemovedFromSlot(this.player, slotNumber, prevItem.getDamage());
        }
        if (itemstack != null) {
            ItemType.byId[itemstack.itemId].onAddToSlot(this.player, slotNumber, itemstack.getDamage());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public float method_674(MixinTile block) {
        float f = 1.0f;
        if (this.main[this.selectedHotbarSlot] != null) {
            f *= this.main[this.selectedHotbarSlot].method_708(block);
        }
        return f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public ListTag toTag(ListTag nbttaglist) {
        for (int i = 0; i < this.main.length; ++i) {
            if (this.main[i] == null)
                continue;
            MixinCompoundTag nbttagcompound = new MixinCompoundTag();
            nbttagcompound.put("Slot", (byte) i);
            this.main[i].toTag(nbttagcompound);
            nbttaglist.add(nbttagcompound);
        }
        for (int j = 0; j < this.armour.length; ++j) {
            if (this.armour[j] == null)
                continue;
            MixinCompoundTag nbttagcompound1 = new MixinCompoundTag();
            nbttagcompound1.put("Slot", (byte) (j + 100));
            this.armour[j].toTag(nbttagcompound1);
            nbttaglist.add(nbttagcompound1);
        }
        return nbttaglist;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void fromTag(ListTag nbttaglist) {
        this.main = new MixinItemInstance[36];
        this.armour = new MixinItemInstance[4];
        for (int i = 0; i < nbttaglist.size(); ++i) {
            MixinCompoundTag nbttagcompound = (MixinCompoundTag) nbttaglist.get(i);
            int j = nbttagcompound.getByte("Slot") & 0xFF;
            MixinItemInstance itemstack = new MixinItemInstance(nbttagcompound);
            if (itemstack.getType() == null)
                continue;
            if (j >= 0 && j < this.main.length) {
                this.main[j] = itemstack;
            }
            if (j < 100 || j >= this.armour.length + 100)
                continue;
            this.armour[j - 100] = itemstack;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getInvSize() {
        return this.main.length + 4;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public MixinItemInstance getInvItem(int i) {
        MixinItemInstance[] aitemstack = this.main;
        if (i >= aitemstack.length) {
            i -= aitemstack.length;
            aitemstack = this.armour;
        }
        return aitemstack[i];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public String getContainerName() {
        return "Inventory";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getMaxItemCount() {
        return 64;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_672(MixinEntity entity) {
        MixinItemInstance itemstack = this.getInvItem(this.selectedHotbarSlot);
        if (itemstack != null) {
            return itemstack.method_707(entity);
        }
        return 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isUsingEffectiveTool(MixinTile block) {
        if (block.material.doesRequireTool()) {
            return true;
        }
        MixinItemInstance itemstack = this.getInvItem(this.selectedHotbarSlot);
        if (itemstack != null) {
            return itemstack.isEffectiveOn(block);
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance getArmourItem(int i) {
        return this.armour[i];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int method_687() {
        float i = 0.0f;
        int j = 0;
        int k = 0;
        for (int l = 0; l < this.armour.length; ++l) {
            if (this.armour[l] == null || !(this.armour[l].getType() instanceof MixinArmourItem))
                continue;
            int i1 = this.armour[l].method_723();
            int j1 = this.armour[l].method_721();
            int k1 = i1 - j1;
            j += k1;
            k += i1;
            i += ((MixinArmourItem) this.armour[l].getType()).bl;
        }
        if (k == 0) {
            return 0;
        }
        return (int) ((i - 1.0f) * (float) j) / k + 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void damageArmour(int i) {
        for (int j = 0; j < this.armour.length; ++j) {
            if (this.armour[j] == null || !(this.armour[j].getType() instanceof MixinArmourItem))
                continue;
            this.armour[j].applyDamage(i, this.player);
            if (this.armour[j].count != 0)
                continue;
            int prevItemID = this.armour[j].itemId;
            int prevDamage = this.armour[j].getDamage();
            this.armour[j].method_700(this.player);
            this.armour[j] = null;
            ItemType.byId[prevItemID].onRemovedFromSlot(this.player, j + this.main.length, prevDamage);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void dropInventory() {
        MixinItemInstance prevItem;
        for (int i = 0; i < this.main.length; ++i) {
            if (this.main[i] == null)
                continue;
            prevItem = this.main[i];
            this.player.dropItem(this.main[i], true);
            this.main[i] = null;
            ItemType.byId[prevItem.itemId].onRemovedFromSlot(this.player, i, prevItem.getDamage());
        }
        for (int j = 0; j < this.armour.length; ++j) {
            if (this.armour[j] == null)
                continue;
            prevItem = this.armour[j];
            this.player.dropItem(this.armour[j], true);
            this.armour[j] = null;
            ItemType.byId[prevItem.itemId].onRemovedFromSlot(this.player, j + this.main.length, prevItem.getDamage());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void markDirty() {
        this.dirty = true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void setCursorItem(MixinItemInstance itemstack) {
        this.cursorStack = itemstack;
        this.player.updateCursorItem(itemstack);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public MixinItemInstance getCursorItem() {
        return this.cursorStack;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlayerUse(MixinPlayer entityplayer) {
        if (this.player.removed) {
            return false;
        }
        return entityplayer.method_1352(this.player) <= 64.0;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean containsItem(MixinItemInstance itemstack) {
        for (int i = 0; i < this.armour.length; ++i) {
            if (this.armour[i] == null || !this.armour[i].isItemEqualIgnoreDamage(itemstack))
                continue;
            return true;
        }
        for (int j = 0; j < this.main.length; ++j) {
            if (this.main[j] == null || !this.main[j].isItemEqualIgnoreDamage(itemstack))
                continue;
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean consumeItemAmount(int itemID, int damage, int amount) {
        int i;
        int slotsToUse = 0;
        int amountLeft = amount;
        for (i = 0; i < 36; ++i) {
            if (this.main[i] == null || this.main[i].itemId != itemID || this.main[i].getDamage() != damage)
                continue;
            this.consumeInventory[slotsToUse++] = i;
            if ((amountLeft -= this.main[i].count) <= 0)
                break;
        }
        if (amountLeft > 0) {
            return false;
        }
        for (i = 0; i < slotsToUse; ++i) {
            int slot = this.consumeInventory[i];
            if (this.main[slot].count > amount) {
                this.main[slot].count -= amount;
                continue;
            }
            amount -= this.main[slot].count;
            this.main[slot].count = 0;
            this.main[slot] = null;
            ItemType.byId[itemID].onRemovedFromSlot(this.player, slot, damage);
        }
        return true;
    }
}
