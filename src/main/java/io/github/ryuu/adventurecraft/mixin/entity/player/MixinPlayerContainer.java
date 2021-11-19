package io.github.ryuu.adventurecraft.mixin.entity.player;

import net.minecraft.client.Minecraft;
import net.minecraft.container.Container;
import net.minecraft.container.slot.CraftingResultSlot;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.recipe.RecipeRegistry;

public class MixinPlayerContainer extends Container {
    public CraftingInventory craftingInv = new CraftingInventory(this, 2, 2);
    public Inventory resultInv = new CraftingResultInventory();
    public boolean local = false;

    public MixinPlayerContainer(PlayerInventory inventoryplayer) {
        this(inventoryplayer, true);
    }

    public MixinPlayerContainer(PlayerInventory inventoryplayer, boolean local) {
        this.local = local;
        if (Minecraft.minecraftInstance.level.properties.allowsInventoryCrafting) {
            this.addSlot(new CraftingResultSlot(inventoryplayer.player, this.craftingInv, this.resultInv, 0, 144, 52));
            for (int i = 0; i < 2; ++i) {
                for (int i1 = 0; i1 < 2; ++i1) {
                    this.addSlot(new Slot(this.craftingInv, i1 + i * 2, 88 + i1 * 18, 26 + i * 18 + 16));
                }
            }
        }
        for (int j = 0; j < 4; ++j) {
            int j1 = j;
            this.addSlot(new PlayerContainer$1(this, inventoryplayer, inventoryplayer.getInvSize() - 1 - j, 8, 8 + j * 18, j1));
        }
        for (int k = 0; k < 3; ++k) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(inventoryplayer, k1 + (k + 1) * 9, 8 + k1 * 18, 84 + k * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventoryplayer, l, 8 + l * 18, 142));
        }
        this.onContentsChanged(this.craftingInv);
    }

    public void onContentsChanged(Inventory iinventory) {
        this.resultInv.setInvItem(0, RecipeRegistry.getInstance().getCraftingOutput(this.craftingInv));
    }

    public void onClosed(Player entityplayer) {
        super.onClosed(entityplayer);
        for (int i = 0; i < 4; ++i) {
            ItemInstance itemstack = this.craftingInv.getInvItem(i);
            if (itemstack == null) continue;
            entityplayer.dropItem(itemstack);
            this.craftingInv.setInvItem(i, null);
        }
    }

    public boolean canUse(Player entityplayer) {
        return true;
    }

    public ItemInstance transferSlot(int index) {
        ItemInstance itemstack = null;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemInstance itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.insertItem(itemstack1, 9, 45, true);
            } else if (index >= 9 && index < 36) {
                this.insertItem(itemstack1, 36, 45, false);
            } else if (index >= 36 && index < 45) {
                this.insertItem(itemstack1, 9, 36, false);
            } else {
                this.insertItem(itemstack1, 9, 45, false);
            }
            if (itemstack1.count == 0) {
                slot.setStack(null);
            } else {
                slot.markDirty();
            }
            if (itemstack1.count != itemstack.count) {
                slot.onCrafted(itemstack1);
            } else {
                return null;
            }
        }
        return itemstack;
    }
}