package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.level.Level;

public class GuiTrigger extends Screen {
    private final TileEntityTrigger trigger;

    private final int blockX;

    private final int blockY;

    private final int blockZ;

    private final Level world;

    public GuiTrigger(Level w, int x, int y, int z, TileEntityTrigger triggerClicked) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.trigger = triggerClicked;
    }

    public static void showUI(Level w, int x, int y, int z, TileEntityTrigger triggerClicked) {
        Minecraft.minecraftInstance.a(new GuiTrigger(w, x, y, z, triggerClicked));
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
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.triggerBlock.id)
                Blocks.triggerBlock.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
        } else if (guibutton.id == 1) {
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.triggerBlock.id)
                Blocks.triggerBlock.setTriggerReset(this.world, this.blockX, this.blockY, this.blockZ, !this.trigger.resetOnTrigger);
            if (this.trigger.resetOnTrigger) {
                guibutton.text = "Reset Target";
            } else {
                guibutton.text = "Trigger Target";
            }
        }
        this.world.getChunk(this.blockX, this.blockZ).method_885();
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        drawTextWithShadow(this.textManager, String.format("Min: (%d, %d, %d)", this.trigger.minX, this.trigger.minY, this.trigger.minZ), 4, 4, 14737632);
        drawTextWithShadow(this.textManager, String.format("Max: (%d, %d, %d)", this.trigger.maxX, this.trigger.maxY, this.trigger.maxZ), 4, 24, 14737632);
        super.render(i, j, f);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}