package io.github.ryuu.adventurecraft.mixin.tile.entity;

import java.util.Random;
import net.minecraft.entity.player.Player;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Dispenser.class)
public class MixinDispenser extends TileEntity implements Inventory {

    @Shadow()
    private ItemInstance[] contents = new ItemInstance[9];

    private Random rand = new Random();

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public ItemInstance getInvItem(int i) {
        return this.contents[i];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public ItemInstance getItemToDispense() {
        int i = -1;
        int j = 1;
        for (int k = 0; k < this.contents.length; ++k) {
            if (this.contents[k] == null || this.rand.nextInt(j++) != 0)
                continue;
            i = k;
        }
        if (i >= 0) {
            return this.takeInvItem(i, 1);
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void setInvItem(int i, ItemInstance itemstack) {
        this.contents[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxItemCount()) {
            itemstack.count = this.getMaxItemCount();
        }
        this.markDirty();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        ListTag nbttaglist = tag.getListTag("Items");
        this.contents = new ItemInstance[this.getInvSize()];
        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.get(i);
            int j = nbttagcompound1.getByte("Slot") & 0xFF;
            if (j < 0 || j >= this.contents.length)
                continue;
            this.contents[j] = new ItemInstance(nbttagcompound1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        ListTag nbttaglist = new ListTag();
        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] == null)
                continue;
            CompoundTag nbttagcompound1 = new CompoundTag();
            nbttagcompound1.put("Slot", (byte) i);
            this.contents[i].toTag(nbttagcompound1);
            nbttaglist.add(nbttagcompound1);
        }
        tag.put("Items", nbttaglist);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlayerUse(Player entityplayer) {
        if (this.level.getTileEntity(this.x, this.y, this.z) != this) {
            return false;
        }
        return entityplayer.squaredDistanceTo((double) this.x + 0.5, (double) this.y + 0.5, (double) this.z + 0.5) <= 64.0;
    }
}
