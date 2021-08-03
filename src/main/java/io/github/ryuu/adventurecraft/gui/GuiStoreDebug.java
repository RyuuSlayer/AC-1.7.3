package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.client.Minecraft;

public class GuiStoreDebug extends da {
    private final TileEntityStore store;

    private GuiSlider2 supply;

    public GuiStoreDebug(TileEntityStore s) {
        this.store = s;
    }

    public void b() {
        ab b = new ab(0, 4, 0, "Set Items");
        this.e.add(b);
        this.supply = new GuiSlider2(6, 4, 26, 10, String.format("Supply: %d", Integer.valueOf(this.store.buySupply)), this.store.buySupply / 9.0F);
        if (this.store.buySupply == -1) {
            this.supply.e = "Supply: Infinite";
            this.supply.sliderValue = 0.0F;
        }
        this.e.add(this.supply);
        b = new ab(1, 4, 48, "Set Trade Trigger");
        if (this.store.tradeTrigger != null)
            b.e = "Clear Trade Trigger";
        this.e.add(b);
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            iz item = this.b.h.G();
            if (item != null) {
                this.store.buyItemID = item.c;
                this.store.buyItemDamage = item.i();
                this.store.buyItemAmount = item.a;
            } else {
                this.store.buyItemID = 0;
            }
            item = this.b.h.c.getOffhandItem();
            if (item != null) {
                this.store.sellItemID = item.c;
                this.store.sellItemDamage = item.i();
                this.store.sellItemAmount = item.a;
            } else {
                this.store.sellItemID = 0;
            }
        } else if (guibutton.f == 1) {
            if (this.store.tradeTrigger != null) {
                this.store.tradeTrigger = null;
                guibutton.e = "Set Trade Trigger";
            } else {
                this.store.tradeTrigger = new TriggerArea(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
                guibutton.e = "Clear Trade Trigger";
            }
        }
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        this.store.buySupply = (int) (this.supply.sliderValue * 9.0F);
        if (this.store.buySupply != 0) {
            this.supply.e = String.format("Supply: %d", new Object[]{Integer.valueOf(this.store.buySupply)});
        } else {
            this.supply.e = "Supply: Infinite";
            this.store.buySupply = -1;
        }
        this.store.buySupplyLeft = this.store.buySupply;
        super.a(i, j, f);
        this.b.storeGUI.setBuyItem(this.store.buyItemID, this.store.buyItemAmount, this.store.buyItemDamage);
        this.b.storeGUI.setSellItem(this.store.sellItemID, this.store.sellItemAmount, this.store.sellItemDamage);
        this.b.storeGUI.setSupplyLeft(this.store.buySupply);
        this.b.updateStoreGUI();
        this.b.storeGUI.a(i, j, f);
        this.store.d.b(this.store.e, this.store.g).g();
    }

    public static void showUI(TileEntityStore s) {
        Minecraft.minecraftInstance.a(new AC_GuiStoreDebug(s));
    }

    public boolean c() {
        return false;
    }
}