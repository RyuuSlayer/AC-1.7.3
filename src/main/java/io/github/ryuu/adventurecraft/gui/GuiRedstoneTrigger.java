package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;

public class GuiRedstoneTrigger extends MixinScreen {

    private final TileEntityRedstoneTrigger trigger;

    private final int blockX;

    private final int blockY;

    private final int blockZ;

    private final MixinLevel world;

    public GuiRedstoneTrigger(MixinLevel w, int x, int y, int z, TileEntityRedstoneTrigger triggerClicked) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.trigger = triggerClicked;
    }

    public static void showUI(MixinLevel w, int x, int y, int z, TileEntityRedstoneTrigger triggerClicked) {
        Minecraft.minecraftInstance.openScreen(new GuiRedstoneTrigger(w, x, y, z, triggerClicked));
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
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.redstoneTrigger.id) {
                Blocks.redstoneTrigger.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
            }
        } else if (button.id == 1) {
            this.trigger.resetOnTrigger = !this.trigger.resetOnTrigger;
            button.text = this.trigger.resetOnTrigger ? "Reset Target" : "Trigger Target";
        }
        this.world.getChunk(this.blockX, this.blockZ).method_885();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, String.format("Min: (%d, %d, %d)", new Object[]{this.trigger.minX, this.trigger.minY, this.trigger.minZ}), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format("Max: (%d, %d, %d)", new Object[]{this.trigger.maxX, this.trigger.maxY, this.trigger.maxZ}), 4, 24, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
