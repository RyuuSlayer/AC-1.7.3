package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.items.ItemCursor;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTriggerPushable;

public class GuiTriggerPushable extends Screen {

    private TileEntityTriggerPushable trigger;

    public GuiTriggerPushable(TileEntityTriggerPushable triggerClicked) {
        this.trigger = triggerClicked;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.buttons.add((Object) new OptionButton(0, 4, 40, "Use Current Selection"));
        OptionButton b = new OptionButton(1, 4, 60, "Trigger Target");
        if (this.trigger.resetOnTrigger) {
            b.text = "Reset Target";
        }
        this.buttons.add((Object) b);
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.trigger.minX = ItemCursor.minX;
            this.trigger.minY = ItemCursor.minY;
            this.trigger.minZ = ItemCursor.minZ;
            this.trigger.maxX = ItemCursor.maxX;
            this.trigger.maxY = ItemCursor.maxY;
            this.trigger.maxZ = ItemCursor.maxZ;
        } else if (button.id == 1) {
            this.trigger.resetOnTrigger = !this.trigger.resetOnTrigger;
            button.text = this.trigger.resetOnTrigger ? "Reset Target" : "Trigger Target";
        }
        this.trigger.level.getChunk(this.trigger.x, this.trigger.z).method_885();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, String.format((String) "Min: (%d, %d, %d)", (Object[]) new Object[] { this.trigger.minX, this.trigger.minY, this.trigger.minZ }), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format((String) "Max: (%d, %d, %d)", (Object[]) new Object[] { this.trigger.maxX, this.trigger.maxY, this.trigger.maxZ }), 4, 24, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(TileEntityTriggerPushable triggerClicked) {
        Minecraft.minecraftInstance.openScreen(new GuiTriggerPushable(triggerClicked));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
