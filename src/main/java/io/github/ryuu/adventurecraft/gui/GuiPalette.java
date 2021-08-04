package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.util.InventoryDebug;
import net.minecraft.client.gui.screen.container.DoubleChestScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.inventory.Inventory;
import org.lwjgl.opengl.GL11;

public class GuiPalette extends DoubleChestScreen {
    private final InventoryDebug palette;

    GuiSlider2 extraWidth;

    GuiSlider2 extraDepth;

    private Button a;

    public GuiPalette(Inventory iinventory, InventoryDebug p) {
        super(iinventory, (Inventory) p);
        this.palette = p;
    }

    @Override
    public void init() {
        super.init();
        this.extraDepth = new GuiSlider2(50, this.width / 2 + 2, 3, 10, String.format("Extra Depth: %d", Integer.valueOf(this.minecraft.interactionManager.destroyExtraDepth)), this.minecraft.interactionManager.destroyExtraDepth / 16.0F);
        this.extraWidth = new GuiSlider2(50, this.width / 2 - 2 - this.extraDepth.width, 3, 10, String.format("Extra Width: %d", Integer.valueOf(this.minecraft.interactionManager.destroyExtraWidth)), this.minecraft.interactionManager.destroyExtraWidth / 5.0F);
        this.buttons.add(this.extraDepth);
        this.buttons.add(this.extraWidth);
    }

    @Override
    protected void keyPressed(char c, int i) {
        super.keyPressed(c, i);
        if (i == 65 && this.palette.firstItem > 1) {
            this.palette.fillInventoryBackwards(this.palette.firstItem - 1);
        } else if (i == 66 && !this.palette.atEnd) {
            this.palette.fillInventory(this.palette.lastItem + 1);
        }
    }

    @Override
    public void render(int i, int j, float f) {
        super.render(i, j, f);
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        for (int k = 0; k < this.buttons.size(); k++) {
            Button guibutton = this.buttons.get(k);
            guibutton.render(this.minecraft, i, j);
        }
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
        this.minecraft.interactionManager.destroyExtraDepth = (int) Math.min(16.0F * this.extraDepth.sliderValue, 15.0F);
        this.minecraft.interactionManager.destroyExtraWidth = (int) Math.min(5.0F * this.extraWidth.sliderValue, 4.0F);
        this.extraWidth.text = String.format("Extra Width: %d", Integer.valueOf(this.minecraft.interactionManager.destroyExtraWidth));
        this.extraDepth.text = String.format("Extra Depth: %d", Integer.valueOf(this.minecraft.interactionManager.destroyExtraDepth));
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        if (k == 0)
            for (int l = 0; l < this.buttons.size(); l++) {
                Button guibutton = this.buttons.get(l);
                if (guibutton.isMouseOver(this.minecraft, i, j)) {
                    this.a = guibutton;
                    this.minecraft.soundHelper.playSound("random.click", 1.0F, 1.0F);
                    buttonClicked(guibutton);
                }
            }
        super.mouseClicked(i, j, k);
    }

    @Override
    protected void mouseReleased(int i, int j, int k) {
        if (this.a != null && k == 0) {
            this.a.mouseReleased(i, j);
            this.a = null;
        }
        super.mouseReleased(i, j, k);
    }
}