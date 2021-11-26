package io.github.ryuu.adventurecraft.mixin.entity.player;

import io.github.ryuu.adventurecraft.extensions.entity.ExLivingEntity;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayer;
import io.github.ryuu.adventurecraft.extensions.entity.player.ExPlayerInventory;
import io.github.ryuu.adventurecraft.extensions.items.ExItemInstance;
import io.github.ryuu.adventurecraft.extensions.items.ExItemType;
import io.github.ryuu.adventurecraft.items.IItemReload;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import org.mozilla.javascript.Scriptable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerInventory.class)
public abstract class MixinPlayerInventory implements Inventory, ExPlayerInventory {

    @Shadow
    public ItemInstance[] main;
    @Shadow
    public ItemInstance[] armour;
    @Shadow
    public int selectedHotbarSlot;
    @Shadow
    public Player player;

    @Shadow
    protected abstract int getSlotWithItem(int i);
    @Shadow
    protected abstract int findNextEmptySlot();
    @Shadow
    protected abstract int findItem(ItemInstance arg);

    public int offhandItem;
    public int[] consumeInventory;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Player par1, CallbackInfo ci) {
        this.offhandItem = 1;
        this.consumeInventory = new int[36];
    }

    public ItemInstance getOffhandItem() {
        return this.main[this.offhandItem];
    }

    public void swapOffhandWithMain() {
        int t = this.selectedHotbarSlot;
        this.selectedHotbarSlot = this.offhandItem;
        this.offhandItem = t;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Environment(EnvType.CLIENT)
    @Overwrite
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
    @Overwrite
    private int method_685(ItemInstance itemstack) {
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
            this.main[k] = new ItemInstance(i, 0, itemstack.getDamage());
            onAddToSlot(ItemType.byId[i], this.player, k, itemstack.getDamage());
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
    @Overwrite
    public void inventoryTick() {
        for (int i = 0; i < this.main.length; ++i) {
            if (this.main[i] != null) {
                ItemInstance itemStack = this.main[i];
                ExItemInstance exStack = (ExItemInstance)itemStack;
                itemStack.inventoryTick(this.player.level, this.player, i, this.selectedHotbarSlot == i);
                if (exStack.getTimeLeft() > 0) {
                    exStack.setTimeLeft(exStack.getTimeLeft() - 1);
                }
                if ((i == this.selectedHotbarSlot || i == this.offhandItem) && exStack.getTimeLeft() == 0 && exStack.isReloading()) {
                    IItemReload item = (IItemReload) ItemType.byId[itemStack.itemId];
                    item.reload(itemStack, this.player.level, this.player);
                }
                if (itemStack.getDamage() > 0 && ((ExItemType)ItemType.byId[itemStack.itemId]).canDecrementDamage()) {
                    itemStack.setDamage(itemStack.getDamage() - 1);
                }
            }
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean decreaseAmountOfItem(int i) {
        int j = this.getSlotWithItem(i);
        if (j < 0) {
            return false;
        }
        if (--this.main[j].count == 0) {
            int d = this.main[j].getDamage();
            this.main[j] = null;
            onRemovedFromSlot(ItemType.byId[i], this.player, j, d);
        }
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite
    public boolean pickupItem(ItemInstance item) {
        int i;
        if (item.itemId == Items.heart.id) {
            item.count = 0;
            this.player.addHealth(4);
            return true;
        }
        ExPlayer exPlayer = (ExPlayer) this.player;
        if (item.itemId == Items.heartContainer.id) {
            item.count = 0;
            exPlayer.setMaxHealth(exPlayer.getMaxHealth() + 4);
            this.player.addHealth(exPlayer.getMaxHealth());
            return true;
        }
        if (item.itemId == Items.heartPiece.id) {
            item.count = 0;
            exPlayer.setHeartPieces(exPlayer.getHeartPieces() + 1);
            if (exPlayer.getHeartPieces() >= 4) {
                exPlayer.setHeartPieces(0);
                exPlayer.setMaxHealth(exPlayer.getMaxHealth() + 4);
                this.player.addHealth(exPlayer.getMaxHealth());
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
            onAddToSlot(ItemType.byId[item.itemId], this.player, i, item.getDamage());
            return true;
        }
        return false;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public ItemInstance takeInvItem(int index, int j) {
        int slotID = index;
        ItemInstance[] aitemstack = this.main;
        if (index >= this.main.length) {
            aitemstack = this.armour;
            index -= this.main.length;
        }
        if (aitemstack[index] != null) {
            if (aitemstack[index].count <= j) {
                ItemInstance itemstack = aitemstack[index];
                aitemstack[index] = null;
                onRemovedFromSlot(ItemType.byId[itemstack.itemId], this.player, slotID, itemstack.getDamage());
                return itemstack;
            }
            ItemInstance itemstack1 = aitemstack[index].split(j);
            if (aitemstack[index].count == 0) {
                aitemstack[index] = null;
                onRemovedFromSlot(ItemType.byId[itemstack1.itemId], this.player, slotID, itemstack1.getDamage());
            }
            return itemstack1;
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public void setInvItem(int i, ItemInstance itemstack) {
        int slotNumber = i;
        ItemInstance[] aitemstack = this.main;
        if (i >= aitemstack.length) {
            i -= aitemstack.length;
            aitemstack = this.armour;
        }
        ItemInstance prevItem = aitemstack[i];
        aitemstack[i] = itemstack;
        if (prevItem != null) {
            onRemovedFromSlot(ItemType.byId[prevItem.itemId], this.player, slotNumber, prevItem.getDamage());
        }
        if (itemstack != null) {
            onAddToSlot(ItemType.byId[itemstack.itemId], this.player, slotNumber, itemstack.getDamage());
        }
    }

    @Inject(method = "damageArmour", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemInstance;method_700(Lnet/minecraft/entity/player/Player;)V",
            shift = At.Shift.AFTER))
    public void doSlotRemoveCallbacksOnDamageArmour(CallbackInfo ci, int var2) {
        ItemInstance item = this.armour[var2];
        onRemovedFromSlot(ItemType.byId[item.itemId], this.player, var2 + this.main.length, item.getDamage());
    }

    @Inject(method = "dropInventory", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/Player;dropItem(Lnet/minecraft/item/ItemInstance;Z)V",
            shift = At.Shift.AFTER,
            ordinal = 0))
    public void doSlotRemoveCallbacksOnDropMain(CallbackInfo ci, int var1) {
        ItemInstance item = this.main[var1];
        onRemovedFromSlot(ItemType.byId[item.itemId], this.player, var1, item.getDamage());
    }

    @Inject(method = "dropInventory", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/Player;dropItem(Lnet/minecraft/item/ItemInstance;Z)V",
            shift = At.Shift.AFTER,
            ordinal = 1))
    public void doSlotRemoveCallbacksOnDropArmour(CallbackInfo ci, int var1) {
        ItemInstance item = this.armour[var1];
        onRemovedFromSlot(ItemType.byId[item.itemId], this.player, var1 + this.main.length, item.getDamage());
    }

    public boolean consumeItemAmount(int itemID, int damage, int amount) {
        int i;
        int slotsToUse = 0;
        int amountLeft = amount;
        for (i = 0; i < 36; ++i) {
            if (this.main[i] != null && this.main[i].itemId == itemID && this.main[i].getDamage() == damage) {
                this.consumeInventory[slotsToUse++] = i;
                if ((amountLeft -= this.main[i].count) <= 0) break;
            }
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
            onRemovedFromSlot(ItemType.byId[itemID], this.player, slot, damage);
        }
        return true;
    }


    private static void onAddToSlot(ItemType itemType, Player entityPlayer, int slot, int damage) {
        Scriptable scope = AccessMinecraft.getInstance().level.scope;
        scope.put("slotID", scope, slot);
        if (itemType.method_462()) {
            AccessMinecraft.getInstance().level.scriptHandler.runScript(String.format("item_onAddToSlot_%d_%d.js", new Object[]{itemType.id, damage}), scope, false);
        } else {
            AccessMinecraft.getInstance().level.scriptHandler.runScript(String.format("item_onAddToSlot_%d.js", new Object[]{itemType.id}), scope, false);
        }

        ((ExItemType) itemType).onAddToSlot(entityPlayer, slot, damage);
    }

    private static void onRemovedFromSlot(ItemType itemType, Player entityPlayer, int slot, int damage) {
        Scriptable scope = AccessMinecraft.getInstance().level.scope;
        scope.put("slotID", scope, slot);
        if (itemType.method_462()) {
            AccessMinecraft.getInstance().level.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d_%d.js", new Object[]{itemType.id, damage}), scope, false);
        } else {
            AccessMinecraft.getInstance().level.scriptHandler.runScript(String.format("item_onRemovedFromSlot_%d.js", new Object[]{itemType.id}), scope, false);
        }

        ((ExItemType) itemType).onRemovedFromSlot(entityPlayer, slot, damage);
    }

    @Override
    public int getOffhandItemID() {
        return offhandItem;
    }
}
