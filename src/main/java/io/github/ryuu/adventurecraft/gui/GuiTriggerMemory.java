package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.level.Level;

public class GuiTriggerMemory extends Screen {

    private TileEntityTriggerMemory trigger;

    private int blockX;

    private int blockY;

    private int blockZ;

    private Level world;

    public GuiTriggerMemory(Level w, int x, int y, int z, TileEntityTriggerMemory triggerClicked) {
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
        OptionButton b = new OptionButton(1, 4, 60, "Activate on Trigger");
        if (this.trigger.activateOnDetrigger) {
            b.text = "Activate on Detrigger";
        }
        this.buttons.add((Object) b);
        b = new OptionButton(2, 4, 80, "Reset on Death");
        if (!this.trigger.resetOnDeath) {
            b.text = "Don't Reset on Death";
        }
        this.buttons.add((Object) b);
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.triggerMemory.id) {
                Blocks.triggerMemory.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
            }
        } else if (button.id == 1) {
            boolean bl = this.trigger.activateOnDetrigger = !this.trigger.activateOnDetrigger;
            button.text = this.trigger.activateOnDetrigger ? "Activate on Detrigger" : "Activate on Trigger";
        } else if (button.id == 2) {
            this.trigger.resetOnDeath = !this.trigger.resetOnDeath;
            button.text = this.trigger.resetOnDeath ? "Reset on Death" : "Don't Reset on Death";
        }
        this.world.getChunk(this.blockX, this.blockZ).method_885();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, String.format((String) "Min: (%d, %d, %d)", (Object[]) new Object[] { this.trigger.minX, this.trigger.minY, this.trigger.minZ }), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format((String) "Max: (%d, %d, %d)", (Object[]) new Object[] { this.trigger.maxX, this.trigger.maxY, this.trigger.maxZ }), 4, 24, 0xE0E0E0);
        if (this.trigger.isActivated) {
            this.drawTextWithShadow(this.textManager, "Memory Set", 4, 104, 0xE0E0E0);
        } else {
            this.drawTextWithShadow(this.textManager, "Memory Unset", 4, 104, 0xE0E0E0);
        }
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(Level w, int x, int y, int z, TileEntityTriggerMemory triggerClicked) {
        Minecraft.minecraftInstance.openScreen(new GuiTriggerMemory(w, x, y, z, triggerClicked));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
