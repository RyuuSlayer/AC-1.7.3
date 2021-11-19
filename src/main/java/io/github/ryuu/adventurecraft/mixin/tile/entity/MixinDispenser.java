package io.github.ryuu.adventurecraft.mixin.tile.entity;

import net.minecraft.inventory.Inventory;
import net.minecraft.tile.entity.Dispenser;
import net.minecraft.util.io.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(Dispenser.class)
public class MixinDispenser extends MixinTileEntity implements Inventory {

    private final Random rand = new Random();
    @Shadow()
    private MixinItemInstance[] contents = new MixinItemInstance[9];

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int getInvSize() {
        return 9;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public MixinItemInstance getInvItem(int i) {
        return this.contents[i];
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public MixinItemInstance takeInvItem(int index, int j) {
        if (this.contents[index] != null) {
            if (this.contents[index].count <= j && this.contents[index].count >= 0) {
                MixinItemInstance itemstack = this.contents[index];
                this.contents[index] = null;
                this.markDirty();
                return itemstack;
            }
            if (this.contents[index].count < 0) {
                MixinItemInstance item = this.contents[index].copy();
                item.count = 1;
                return item;
            }
            MixinItemInstance itemstack1 = this.contents[index].split(j);
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
    public MixinItemInstance getItemToDispense() {
        int i = -1;
        int j = 1;
        for (int k = 0; k < this.contents.length; ++k) {
            if (this.contents[k] == null || this.rand.nextInt(j++) != 0) continue;
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
    public void setInvItem(int i, MixinItemInstance itemstack) {
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
    public String getContainerName() {
        return "Trap";
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void readIdentifyingData(MixinCompoundTag tag) {
        super.readIdentifyingData(tag);
        ListTag nbttaglist = tag.getListTag("Items");
        this.contents = new MixinItemInstance[this.getInvSize()];
        for (int i = 0; i < nbttaglist.size(); ++i) {
            MixinCompoundTag nbttagcompound1 = (MixinCompoundTag) nbttaglist.get(i);
            int j = nbttagcompound1.getByte("Slot") & 0xFF;
            if (j < 0 || j >= this.contents.length) continue;
            this.contents[j] = new MixinItemInstance(nbttagcompound1);
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void writeIdentifyingData(MixinCompoundTag tag) {
        super.writeIdentifyingData(tag);
        ListTag nbttaglist = new ListTag();
        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] == null) continue;
            MixinCompoundTag nbttagcompound1 = new MixinCompoundTag();
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
    public int getMaxItemCount() {
        return 64;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean canPlayerUse(MixinPlayer entityplayer) {
        if (this.level.getTileEntity(this.x, this.y, this.z) != this) {
            return false;
        }
        return entityplayer.squaredDistanceTo((double) this.x + 0.5, (double) this.y + 0.5, (double) this.z + 0.5) <= 64.0;
    }
}
