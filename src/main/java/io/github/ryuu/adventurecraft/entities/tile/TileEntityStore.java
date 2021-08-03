package io.github.ryuu.adventurecraft.entities.tile;

import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.tile.entity.TileEntity;

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

    public void a(nu nbttagcompound) {
        super.a(nbttagcompound);
        this.buyItemID = nbttagcompound.e("buyItemID");
        this.buyItemAmount = nbttagcompound.e("buyItemAmount");
        this.buyItemDamage = nbttagcompound.e("buyItemDamage");
        this.buySupply = nbttagcompound.e("buySupply");
        this.buySupplyLeft = nbttagcompound.e("buySupplyLeft");
        this.sellItemID = nbttagcompound.e("sellItemID");
        this.sellItemAmount = nbttagcompound.e("sellItemAmount");
        this.sellItemDamage = nbttagcompound.e("sellItemDamage");
        if (nbttagcompound.b("tradeTrigger")) {
            this.tradeTrigger = TriggerArea.getFromTagCompound(nbttagcompound.k("tradeTrigger"));
        } else {
            this.tradeTrigger = null;
        }
    }

    public void b(nu nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("buyItemID", this.buyItemID);
        nbttagcompound.a("buyItemAmount", this.buyItemAmount);
        nbttagcompound.a("buyItemDamage", this.buyItemDamage);
        nbttagcompound.a("buySupply", this.buySupply);
        nbttagcompound.a("buySupplyLeft", this.buySupplyLeft);
        nbttagcompound.a("sellItemID", this.sellItemID);
        nbttagcompound.a("sellItemAmount", this.sellItemAmount);
        nbttagcompound.a("sellItemDamage", this.sellItemDamage);
        if (this.tradeTrigger != null)
            nbttagcompound.a("tradeTrigger", this.tradeTrigger.getTagCompound());
    }
}