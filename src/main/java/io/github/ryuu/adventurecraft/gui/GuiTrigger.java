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
import net.minecraft.level.Level;

public class GuiTrigger extends Screen {

    private TileEntityTrigger trigger;

    private int blockX;

    private int blockY;

    private int blockZ;

    private Level world;

    public GuiTrigger(Level w, int x, int y, int z, TileEntityTrigger triggerClicked) {
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
            if (blockID == Blocks.triggerBlock.id) {
                Blocks.triggerBlock.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
            }
        } else if (button.id == 1) {
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.triggerBlock.id) {
                Blocks.triggerBlock.setTriggerReset(this.world, this.blockX, this.blockY, this.blockZ, !this.trigger.resetOnTrigger);
            }
            button.text = this.trigger.resetOnTrigger ? "Reset Target" : "Trigger Target";
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

    public static void showUI(Level w, int x, int y, int z, TileEntityTrigger triggerClicked) {
        Minecraft.minecraftInstance.openScreen(new GuiTrigger(w, x, y, z, triggerClicked));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
