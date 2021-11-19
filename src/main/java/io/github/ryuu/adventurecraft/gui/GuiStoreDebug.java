package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityStore;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.util.TriggerArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.item.ItemInstance;

public class GuiStoreDebug extends Screen {
    private final TileEntityStore store;

    private GuiSlider2 supply;

    public GuiStoreDebug(TileEntityStore s) {
        this.store = s;
    }

    public static void showUI(TileEntityStore s) {
        Minecraft.minecraftInstance.a(new GuiStoreDebug(s));
    }

    @Override
    public void init() {
        OptionButton b = new OptionButton(0, 4, 0, "Set Items");
        this.buttons.add(b);
        this.supply = new GuiSlider2(6, 4, 26, 10, String.format("Supply: %d", this.store.buySupply), this.store.buySupply / 9.0F);
        if (this.store.buySupply == -1) {
            this.supply.text = "Supply: Infinite";
            this.supply.sliderValue = 0.0F;
        }
        this.buttons.add(this.supply);
        b = new OptionButton(1, 4, 48, "Set Trade Trigger");
        if (this.store.tradeTrigger != null)
            b.text = "Clear Trade Trigger";
        this.buttons.add(b);
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            ItemInstance item = this.minecraft.player.getHeldItem();
            if (item != null) {
                this.store.buyItemID = item.itemId;
                this.store.buyItemDamage = item.getDamage();
                this.store.buyItemAmount = item.count;
            } else {
                this.store.buyItemID = 0;
            }
            item = this.minecraft.player.inventory.getOffhandItem();
            if (item != null) {
                this.store.sellItemID = item.itemId;
                this.store.sellItemDamage = item.getDamage();
                this.store.sellItemAmount = item.count;
            } else {
                this.store.sellItemID = 0;
            }
        } else if (guibutton.id == 1) {
            if (this.store.tradeTrigger != null) {
                this.store.tradeTrigger = null;
                guibutton.text = "Set Trade Trigger";
            } else {
                this.store.tradeTrigger = new TriggerArea(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
                guibutton.text = "Clear Trade Trigger";
            }
        }
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        this.store.buySupply = (int) (this.supply.sliderValue * 9.0F);
        if (this.store.buySupply != 0) {
            this.supply.text = String.format("Supply: %d", this.store.buySupply);
        } else {
            this.supply.text = "Supply: Infinite";
            this.store.buySupply = -1;
        }
        this.store.buySupplyLeft = this.store.buySupply;
        super.render(i, j, f);
        this.minecraft.storeGUI.setBuyItem(this.store.buyItemID, this.store.buyItemAmount, this.store.buyItemDamage);
        this.minecraft.storeGUI.setSellItem(this.store.sellItemID, this.store.sellItemAmount, this.store.sellItemDamage);
        this.minecraft.storeGUI.setSupplyLeft(this.store.buySupply);
        this.minecraft.updateStoreGUI();
        this.minecraft.storeGUI.a(i, j, f);
        this.store.level.getChunk(this.store.x, this.store.z).method_885();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}