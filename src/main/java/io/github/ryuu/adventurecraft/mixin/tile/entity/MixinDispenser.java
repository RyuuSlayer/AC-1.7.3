package io.github.ryuu.adventurecraft.mixin.tile.entity;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.entity.Dispenser;
import net.minecraft.tile.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Dispenser.class)
public abstract class MixinDispenser extends TileEntity implements Inventory {

    @Shadow
    private ItemInstance[] contents;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite
    public ItemInstance takeInvItem(int index, int j) {
        if (this.contents[index] != null) {
            if (this.contents[index].count <= j && this.contents[index].count >= 0) {
                ItemInstance itemstack = this.contents[index];
                this.contents[index] = null;
                this.markDirty();
                return itemstack;
            }
            if (this.contents[index].count < 0) {
                ItemInstance item = this.contents[index].copy();
                item.count = 1;
                return item;
            }
            ItemInstance itemstack1 = this.contents[index].split(j);
            if (this.contents[index].count == 0) {
                this.contents[index] = null;
            }
            this.markDirty();
            return itemstack1;
        }
        return null;
    }

}
