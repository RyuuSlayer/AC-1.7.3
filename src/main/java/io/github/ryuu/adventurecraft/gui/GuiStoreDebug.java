package io.github.ryuu.adventurecraft.gui;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.item.ItemInstance;

public class GuiStoreDebug extends Screen {

    private TileEntityStore store;

    private GuiSlider2 supply;

    public GuiStoreDebug(TileEntityStore s) {
        this.store = s;
    }

    @Override
    public void init() {
        OptionButton b = new OptionButton(0, 4, 0, "Set Items");
        this.buttons.add((Object) b);
        this.supply = new GuiSlider2(6, 4, 26, 10, String.format((String) "Supply: %d", (Object[]) new Object[] { this.store.buySupply }), (float) this.store.buySupply / 9.0f);
        if (this.store.buySupply == -1) {
            this.supply.text = "Supply: Infinite";
            this.supply.sliderValue = 0.0f;
        }
        this.buttons.add((Object) this.supply);
        b = new OptionButton(1, 4, 48, "Set Trade Trigger");
        if (this.store.tradeTrigger != null) {
            b.text = "Clear Trade Trigger";
        }
        this.buttons.add((Object) b);
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
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
        } else if (button.id == 1) {
            if (this.store.tradeTrigger != null) {
                this.store.tradeTrigger = null;
                button.text = "Set Trade Trigger";
            } else {
                this.store.tradeTrigger = new TriggerArea(ItemCursor.minX, ItemCursor.minY, ItemCursor.minZ, ItemCursor.maxX, ItemCursor.maxY, ItemCursor.maxZ);
                button.text = "Clear Trade Trigger";
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.store.buySupply = (int) (this.supply.sliderValue * 9.0f);
        if (this.store.buySupply != 0) {
            this.supply.text = String.format((String) "Supply: %d", (Object[]) new Object[] { this.store.buySupply });
        } else {
            this.supply.text = "Supply: Infinite";
            this.store.buySupply = -1;
        }
        this.store.buySupplyLeft = this.store.buySupply;
        super.render(mouseX, mouseY, delta);
        this.minecraft.storeGUI.setBuyItem(this.store.buyItemID, this.store.buyItemAmount, this.store.buyItemDamage);
        this.minecraft.storeGUI.setSellItem(this.store.sellItemID, this.store.sellItemAmount, this.store.sellItemDamage);
        this.minecraft.storeGUI.setSupplyLeft(this.store.buySupply);
        this.minecraft.updateStoreGUI();
        this.minecraft.storeGUI.render(mouseX, mouseY, delta);
        this.store.level.getChunk(this.store.x, this.store.z).method_885();
    }

    public static void showUI(TileEntityStore s) {
        Minecraft.minecraftInstance.openScreen(new GuiStoreDebug(s));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
