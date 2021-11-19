package io.github.ryuu.adventurecraft.mixin.entity.player;

import net.minecraft.client.Minecraft;
import net.minecraft.container.Container;
import net.minecraft.container.slot.CraftingResultSlot;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.RecipeRegistry;
import net.minecraft.tile.Tile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerContainer.class)
public class MixinPlayerContainer extends Container {

    @Shadow()
    public CraftingInventory craftingInv = new CraftingInventory(this, 2, 2);

    public Inventory resultInv = new CraftingResultInventory();

    public boolean local = false;

    public MixinPlayerContainer(MixinPlayerInventory inventoryplayer) {
        this(inventoryplayer, true);
    }

    public MixinPlayerContainer(MixinPlayerInventory inventoryplayer, boolean local) {
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
            final int j1 = j;
            this.addSlot(new Slot(inventoryplayer, inventoryplayer.getInvSize() - 1 - j, 8, 8 + j * 18) {

                /**
                 * @author Ryuu, TechPizza, Phil
                 */
                @Overwrite()
                public int getMaxStackCount() {
                    return 1;
                }

                /**
                 * @author Ryuu, TechPizza, Phil
                 */
                @Overwrite()
                public boolean canInsert(MixinItemInstance itemInstance) {
                    if (itemInstance.getType() instanceof MixinArmourItem) {
                        return ((MixinArmourItem) itemInstance.getType()).armourSlot == j1;
                    }
                    if (itemInstance.getType().id == Tile.PUMPKIN.id) {
                        return j1 == 0;
                    }
                    return false;
                }
            });
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onContentsChanged(Inventory iinventory) {
        this.resultInv.setInvItem(0, RecipeRegistry.getInstance().getCraftingOutput(this.craftingInv));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onClosed(MixinPlayer entityplayer) {
        super.onClosed(entityplayer);
        for (int i = 0; i < 4; ++i) {
            MixinItemInstance itemstack = this.craftingInv.getInvItem(i);
            if (itemstack == null) continue;
            entityplayer.dropItem(itemstack);
            this.craftingInv.setInvItem(i, null);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canUse(MixinPlayer entityplayer) {
        return true;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public MixinItemInstance transferSlot(int index) {
        MixinItemInstance itemstack = null;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            MixinItemInstance itemstack1 = slot.getItem();
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
