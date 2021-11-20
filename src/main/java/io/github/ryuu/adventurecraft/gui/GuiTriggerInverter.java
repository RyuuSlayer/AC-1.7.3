package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.level.Level;

public class GuiTriggerInverter extends MixinScreen {

    private TileEntityTriggerInverter trigger;

    private int blockX;

    private int blockY;

    private int blockZ;

    private MixinLevel world;

    public GuiTriggerInverter(MixinLevel w, int x, int y, int z, TileEntityTriggerInverter triggerClicked) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.trigger = triggerClicked;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.buttons.add((Object) new OptionButton(0, 4, 40, "Use Current Selection"));
    }

    @Override
    protected void buttonClicked(Button button) {
        int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
        if (blockID == Blocks.triggerInverter.id) {
            Blocks.triggerInverter.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
        }
        this.world.getChunk(this.blockX, this.blockZ).method_885();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, String.format((String) "Min: (%d, %d, %d)", (Object[]) new Object[] { this.trigger.minX, this.trigger.minY, this.trigger.minZ }), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format((String) "Max: (%d, %d, %d)", (Object[]) new Object[] { this.trigger.maxX, this.trigger.maxY, this.trigger.maxZ }), 4, 24, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(MixinLevel w, int x, int y, int z, TileEntityTriggerInverter triggerClicked) {
        Minecraft.minecraftInstance.openScreen(new GuiTriggerInverter(w, x, y, z, triggerClicked));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
