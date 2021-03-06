package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.util.io.CompoundTag;

public class TileEntityStore extends TileEntity {

    public int buyItemID;

    public int buyItemAmount;

    public int buyItemDamage;

    public int buySupply;

    public int buySupplyLeft;

    public int sellItemID;

    public int sellItemAmount;

    public int sellItemDamage;

    public TriggerArea tradeTrigger;

    public TileEntityStore() {
        this.buyItemID = Items.bomb.id;
        this.buyItemAmount = 3;
        this.buySupplyLeft = 1;
        this.sellItemID = Items.shotgun.id;
        this.sellItemAmount = 1;
        this.sellItemDamage = 0;
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        this.buyItemID = tag.getInt("buyItemID");
        this.buyItemAmount = tag.getInt("buyItemAmount");
        this.buyItemDamage = tag.getInt("buyItemDamage");
        this.buySupply = tag.getInt("buySupply");
        this.buySupplyLeft = tag.getInt("buySupplyLeft");
        this.sellItemID = tag.getInt("sellItemID");
        this.sellItemAmount = tag.getInt("sellItemAmount");
        this.sellItemDamage = tag.getInt("sellItemDamage");
        this.tradeTrigger = tag.containsKey("tradeTrigger") ? TriggerArea.getFromTagCompound(tag.getCompoundTag("tradeTrigger")) : null;
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        tag.put("buyItemID", this.buyItemID);
        tag.put("buyItemAmount", this.buyItemAmount);
        tag.put("buyItemDamage", this.buyItemDamage);
        tag.put("buySupply", this.buySupply);
        tag.put("buySupplyLeft", this.buySupplyLeft);
        tag.put("sellItemID", this.sellItemID);
        tag.put("sellItemAmount", this.sellItemAmount);
        tag.put("sellItemDamage", this.sellItemDamage);
        if (this.tradeTrigger != null) {
            tag.put("tradeTrigger", this.tradeTrigger.getTagCompound());
        }
    }
}
