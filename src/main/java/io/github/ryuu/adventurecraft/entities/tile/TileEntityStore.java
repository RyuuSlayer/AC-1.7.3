package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityStore extends TileEntity {
    public int buyItemID = Items.bomb.bf;

    public int buyItemAmount = 3;

    public int buyItemDamage;

    public int buySupply;

    public int buySupplyLeft = 1;

    public int sellItemID = Items.shotgun.bf;

    public int sellItemAmount = 1;

    public int sellItemDamage = 0;

    public TriggerArea tradeTrigger;

    public void a(CompoundTag nbttagcompound) {
        super.a(nbttagcompound);
        this.buyItemID = nbttagcompound.getInt("buyItemID");
        this.buyItemAmount = nbttagcompound.getInt("buyItemAmount");
        this.buyItemDamage = nbttagcompound.getInt("buyItemDamage");
        this.buySupply = nbttagcompound.getInt("buySupply");
        this.buySupplyLeft = nbttagcompound.getInt("buySupplyLeft");
        this.sellItemID = nbttagcompound.getInt("sellItemID");
        this.sellItemAmount = nbttagcompound.getInt("sellItemAmount");
        this.sellItemDamage = nbttagcompound.getInt("sellItemDamage");
        if (nbttagcompound.containsKey("tradeTrigger")) {
            this.tradeTrigger = TriggerArea.getFromTagCompound(nbttagcompound.k("tradeTrigger"));
        } else {
            this.tradeTrigger = null;
        }
    }

    public void b(CompoundTag nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.put("buyItemID", this.buyItemID);
        nbttagcompound.put("buyItemAmount", this.buyItemAmount);
        nbttagcompound.put("buyItemDamage", this.buyItemDamage);
        nbttagcompound.put("buySupply", this.buySupply);
        nbttagcompound.put("buySupplyLeft", this.buySupplyLeft);
        nbttagcompound.put("sellItemID", this.sellItemID);
        nbttagcompound.put("sellItemAmount", this.sellItemAmount);
        nbttagcompound.put("sellItemDamage", this.sellItemDamage);
        if (this.tradeTrigger != null)
            nbttagcompound.put("tradeTrigger", this.tradeTrigger.getTagCompound());
    }
}