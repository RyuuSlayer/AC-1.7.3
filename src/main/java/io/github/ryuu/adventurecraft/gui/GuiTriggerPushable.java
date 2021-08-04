package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerPushable;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;

public class GuiTriggerPushable extends Screen {
    private final TileEntityTriggerPushable trigger;

    public GuiTriggerPushable(TileEntityTriggerPushable triggerClicked) {
        this.trigger = triggerClicked;
    }

    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        this.buttons.add(new OptionButton(0, 4, 40, "Use Current Selection"));
        OptionButton b = new OptionButton(1, 4, 60, "Trigger Target");
        if (this.trigger.resetOnTrigger)
            b.text = "Reset Target";
        this.buttons.add(b);
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            this.trigger.minX = ItemCursor.minX;
            this.trigger.minY = ItemCursor.minY;
            this.trigger.minZ = ItemCursor.minZ;
            this.trigger.maxX = ItemCursor.maxX;
            this.trigger.maxY = ItemCursor.maxY;
            this.trigger.maxZ = ItemCursor.maxZ;
        } else if (guibutton.id == 1) {
            this.trigger.resetOnTrigger = !this.trigger.resetOnTrigger;
            if (this.trigger.resetOnTrigger) {
                guibutton.text = "Reset Target";
            } else {
                guibutton.text = "Trigger Target";
            }
        }
        this.trigger.level.getChunk(this.trigger.x, this.trigger.z).method_885();
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        drawTextWithShadow(this.textManager, String.format("Min: (%d, %d, %d)", this.trigger.minX, this.trigger.minY, this.trigger.minZ), 4, 4, 14737632);
        drawTextWithShadow(this.textManager, String.format("Max: (%d, %d, %d)", this.trigger.maxX, this.trigger.maxY, this.trigger.maxZ), 4, 24, 14737632);
        super.render(i, j, f);
    }

    public static void showUI(TileEntityTriggerPushable triggerClicked) {
        Minecraft.minecraftInstance.a(new GuiTriggerPushable(triggerClicked));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
