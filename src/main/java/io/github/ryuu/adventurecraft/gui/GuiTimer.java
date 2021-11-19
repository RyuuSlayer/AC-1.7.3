package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.level.Level;

public class GuiTimer extends Screen {
    private final TileEntityTimer timer;
    private final int blockX;
    private final int blockY;
    private final int blockZ;
    private final Level world;
    boolean ignoreNext;
    boolean useTextFields;

    GuiSlider2 activeTime;

    GuiSlider2 deactiveTime;

    GuiSlider2 delayTime;

    private Textbox activeTimeText;

    private Textbox deactiveTimeText;

    private Textbox delayTimeText;

    public GuiTimer(Level w, int x, int y, int z, TileEntityTimer timerClicked) {
        this.ignoreNext = false;
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.timer = timerClicked;
    }

    public static void showUI(Level w, int x, int y, int z, TileEntityTimer timerClicked) {
        Minecraft.minecraftInstance.a(new GuiTimer(w, x, y, z, timerClicked));
    }

    @Override
    public void tick() {
        if (this.useTextFields) {
            this.delayTimeText.tick();
            this.activeTimeText.tick();
            this.deactiveTimeText.tick();
        }
    }

    @Override
    public void init() {
        this.buttons.clear();
        this.buttons.add(new OptionButton(0, 4, 40, "Use Current Selection"));
        OptionButton b = new OptionButton(1, 4, 60, "Trigger Target");
        if (this.timer.resetOnTrigger)
            b.text = "Reset Target";
        this.buttons.add(b);
        if (!this.useTextFields) {
            this.delayTime = new GuiSlider2(4, 4, 80, 10, String.format("Delay for: %.2fs", this.timer.timeDelay / 20.0F), this.timer.timeDelay / 20.0F / 60.0F);
            this.buttons.add(this.delayTime);
            this.activeTime = new GuiSlider2(2, 4, 100, 10, String.format("Active for: %.2fs", this.timer.timeActive / 20.0F), this.timer.timeActive / 20.0F / 60.0F);
            this.buttons.add(this.activeTime);
            this.deactiveTime = new GuiSlider2(3, 4, 120, 10, String.format("Deactive for: %.2fs", this.timer.timeDeactive / 20.0F), this.timer.timeDeactive / 20.0F / 60.0F);
            this.buttons.add(this.deactiveTime);
        } else {
            this.delayTimeText = new Textbox(this, this.textManager, 80, 81, 70, 16, String.format("%.2f", this.timer.timeDelay / 20.0F));
            this.activeTimeText = new Textbox(this, this.textManager, 80, 101, 70, 16, String.format("%.2f", this.timer.timeActive / 20.0F));
            this.deactiveTimeText = new Textbox(this, this.textManager, 80, 121, 70, 16, String.format("%.2f", this.timer.timeDeactive / 20.0F));
        }
        this.buttons.add(new OptionButton(5, 4, 140, "Switch Input Mode"));
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0) {
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.timer.id)
                Blocks.timer.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
        } else if (guibutton.id == 1) {
            this.timer.resetOnTrigger = !this.timer.resetOnTrigger;
            if (this.timer.resetOnTrigger) {
                guibutton.text = "Reset Target";
            } else {
                guibutton.text = "Trigger Target";
            }
        } else if (guibutton.id == 5) {
            if (!this.ignoreNext) {
                this.useTextFields = !this.useTextFields;
                init();
                this.ignoreNext = true;
            }
        }
    }

    @Override
    public void render(int i, int j, float f) {
        this.ignoreNext = false;
        fill(0, 0, this.width, this.height, -2147483648);
        drawTextWithShadow(this.textManager, String.format("Min: (%d, %d, %d)", this.timer.minX, this.timer.minY, this.timer.minZ), 4, 4, 14737632);
        drawTextWithShadow(this.textManager, String.format("Max: (%d, %d, %d)", this.timer.maxX, this.timer.maxY, this.timer.maxZ), 4, 24, 14737632);
        if (!this.timer.active && this.timer.canActivate) {
            drawTextWithShadow(this.textManager, "State: Ready", 4, 164, 14737632);
        } else {
            if (this.timer.active) {
                drawTextWithShadow(this.textManager, "State: Active", 4, 164, 14737632);
            } else if (!this.timer.canActivate) {
                drawTextWithShadow(this.textManager, "State: Deactive", 4, 164, 14737632);
            }
            if (this.timer.ticksDelay > 0) {
                drawTextWithShadow(this.textManager, String.format("Delay: %.2f", this.timer.ticksDelay * 0.05F), 4, 184, 14737632);
            } else {
                drawTextWithShadow(this.textManager, String.format("Time: %.2f", this.timer.ticks * 0.05F), 4, 184, 14737632);
            }
        }
        if (!this.useTextFields) {
            this.timer.timeActive = (int) (this.activeTime.sliderValue * 60.0F * 20.0F);
            this.timer.timeDeactive = (int) (this.deactiveTime.sliderValue * 60.0F * 20.0F);
            this.timer.timeDelay = (int) (this.delayTime.sliderValue * 60.0F * 20.0F);
            this.delayTime.text = String.format("Delay for: %.2fs", this.timer.timeDelay / 20.0F);
            this.activeTime.text = String.format("Active for: %.2fs", this.timer.timeActive / 20.0F);
            this.deactiveTime.text = String.format("Deactive for: %.2fs", this.timer.timeDeactive / 20.0F);
        } else {
            drawTextWithShadow(this.textManager, "Delay For:", 4, 84, 14737632);
            drawTextWithShadow(this.textManager, "Active For:", 4, 104, 14737632);
            drawTextWithShadow(this.textManager, "Deactive For:", 4, 124, 14737632);
            this.activeTimeText.method_1883();
            this.deactiveTimeText.method_1883();
            this.delayTimeText.method_1883();
            try {
                Float value = Float.valueOf(this.activeTimeText.method_1876());
                if (value != null)
                    this.timer.timeActive = (int) (value * 20.0F);
            } catch (NumberFormatException e) {
            }
            try {
                Float value = Float.valueOf(this.deactiveTimeText.method_1876());
                if (value != null)
                    this.timer.timeDeactive = (int) (value * 20.0F);
            } catch (NumberFormatException e) {
            }
            try {
                Float value = Float.valueOf(this.delayTimeText.method_1876());
                if (value != null)
                    this.timer.timeDelay = (int) (value * 20.0F);
            } catch (NumberFormatException e) {
            }
        }
        this.world.getChunk(this.blockX, this.blockZ).method_885();
        super.render(i, j, f);
    }

    @Override
    protected void keyPressed(char c, int i) {
        if (this.useTextFields) {
            if (this.activeTimeText.field_2420 && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                this.activeTimeText.method_1877(c, i);
            if (this.deactiveTimeText.field_2420 && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                this.deactiveTimeText.method_1877(c, i);
            if (this.delayTimeText.field_2420 && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                this.delayTimeText.method_1877(c, i);
        }
        super.keyPressed(c, i);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        if (this.useTextFields) {
            this.delayTimeText.method_1879(i, j, k);
            this.activeTimeText.method_1879(i, j, k);
            this.deactiveTimeText.method_1879(i, j, k);
        }
        super.mouseClicked(i, j, k);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}