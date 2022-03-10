package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTimer;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.level.Level;

import java.util.List;

public class GuiTimer extends Screen {

    private final TileEntityTimer timer;
    private final int blockX;
    private final int blockY;
    private final int blockZ;
    private final Level world;
    boolean ignoreNext = false;
    boolean useTextFields;
    GuiSlider2 activeTime;
    GuiSlider2 deactiveTime;
    GuiSlider2 delayTime;
    private Textbox activeTimeText;

    private Textbox deactiveTimeText;

    private Textbox delayTimeText;

    public GuiTimer(Level w, int x, int y, int z, TileEntityTimer timerClicked) {
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.timer = timerClicked;
    }

    public static void showUI(Level w, int x, int y, int z, TileEntityTimer timerClicked) {
        AccessMinecraft.getInstance().openScreen(new GuiTimer(w, x, y, z, timerClicked));
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
        List<Button> buttons = (List<Button>)this.buttons;
        buttons.clear();
        buttons.add(new OptionButton(0, 4, 40, "Use Current Selection"));
        OptionButton b = new OptionButton(1, 4, 60, "Trigger Target");
        if (this.timer.resetOnTrigger) {
            b.text = "Reset Target";
        }
        buttons.add(b);
        if (!this.useTextFields) {
            this.delayTime = new GuiSlider2(4, 4, 80, 10, String.format("Delay for: %.2fs", this.timer.timeDelay / 20.0f), (float) this.timer.timeDelay / 20.0f / 60.0f);
            buttons.add(this.delayTime);
            this.activeTime = new GuiSlider2(2, 4, 100, 10, String.format("Active for: %.2fs", this.timer.timeActive / 20.0f), (float) this.timer.timeActive / 20.0f / 60.0f);
            buttons.add(this.activeTime);
            this.deactiveTime = new GuiSlider2(3, 4, 120, 10, String.format("Deactive for: %.2fs", this.timer.timeDeactive / 20.0f), (float) this.timer.timeDeactive / 20.0f / 60.0f);
            buttons.add(this.deactiveTime);
        } else {
            this.delayTimeText = new Textbox(this, this.textManager, 80, 81, 70, 16, String.format("%.2f", (float) this.timer.timeDelay / 20.0f));
            this.activeTimeText = new Textbox(this, this.textManager, 80, 101, 70, 16, String.format("%.2f", (float) this.timer.timeActive / 20.0f));
            this.deactiveTimeText = new Textbox(this, this.textManager, 80, 121, 70, 16, String.format("%.2f", (float) this.timer.timeDeactive / 20.0f));
        }
        buttons.add(new OptionButton(5, 4, 140, "Switch Input Mode"));
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.timer.id) {
                Blocks.timer.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
            }
        } else if (button.id == 1) {
            this.timer.resetOnTrigger = !this.timer.resetOnTrigger;
            button.text = this.timer.resetOnTrigger ? "Reset Target" : "Trigger Target";
        } else if (button.id == 5 && !this.ignoreNext) {
            this.useTextFields = !this.useTextFields;
            this.init();
            this.ignoreNext = true;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.ignoreNext = false;
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, String.format("Min: (%d, %d, %d)", this.timer.minX, this.timer.minY, this.timer.minZ), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format("Max: (%d, %d, %d)", this.timer.maxX, this.timer.maxY, this.timer.maxZ), 4, 24, 0xE0E0E0);
        if (!this.timer.active && this.timer.canActivate) {
            this.drawTextWithShadow(this.textManager, "State: Ready", 4, 164, 0xE0E0E0);
        } else {
            if (this.timer.active) {
                this.drawTextWithShadow(this.textManager, "State: Active", 4, 164, 0xE0E0E0);
            } else {
                this.drawTextWithShadow(this.textManager, "State: Deactive", 4, 164, 0xE0E0E0);
            }
            if (this.timer.ticksDelay > 0) {
                this.drawTextWithShadow(this.textManager, String.format("Delay: %.2f", this.timer.ticksDelay * 0.05f), 4, 184, 0xE0E0E0);
            } else {
                this.drawTextWithShadow(this.textManager, String.format("Time: %.2f", this.timer.ticks * 0.05f), 4, 184, 0xE0E0E0);
            }
        }
        if (!this.useTextFields) {
            this.timer.timeActive = (int) (this.activeTime.sliderValue * 60.0f * 20.0f);
            this.timer.timeDeactive = (int) (this.deactiveTime.sliderValue * 60.0f * 20.0f);
            this.timer.timeDelay = (int) (this.delayTime.sliderValue * 60.0f * 20.0f);
            this.delayTime.text = String.format("Delay for: %.2fs", (float) this.timer.timeDelay / 20.0f);
            this.activeTime.text = String.format("Active for: %.2fs", (float) this.timer.timeActive / 20.0f);
            this.deactiveTime.text = String.format("Deactive for: %.2fs", (float) this.timer.timeDeactive / 20.0f);
        } else {
            Float value;
            this.drawTextWithShadow(this.textManager, "Delay For:", 4, 84, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, "Active For:", 4, 104, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, "Deactive For:", 4, 124, 0xE0E0E0);
            this.activeTimeText.method_1883();
            this.deactiveTimeText.method_1883();
            this.delayTimeText.method_1883();
            try {
                value = Float.valueOf(this.activeTimeText.method_1876());
                if (value != null) {
                    this.timer.timeActive = (int) (value * 20.0f);
                }
            } catch (NumberFormatException e) {
            }
            try {
                value = Float.valueOf(this.deactiveTimeText.method_1876());
                if (value != null) {
                    this.timer.timeDeactive = (int) (value * 20.0f);
                }
            } catch (NumberFormatException e) {
            }
            try {
                value = Float.valueOf(this.delayTimeText.method_1876());
                if (value != null) {
                    this.timer.timeDelay = (int) (value * 20.0f);
                }
            } catch (NumberFormatException e) {
            }
        }
        this.world.getChunk(this.blockX, this.blockZ).markDirty();
        super.render(mouseX, mouseY, delta);
    }

    @Override
    protected void keyPressed(char character, int key) {
        if (this.useTextFields) {
            if (this.activeTimeText.field_2420 && (key == 14 || character >= '0' && character <= '9' || character == '.' || character == '\t')) {
                this.activeTimeText.method_1877(character, key);
            }
            if (this.deactiveTimeText.field_2420 && (key == 14 || character >= '0' && character <= '9' || character == '.' || character == '\t')) {
                this.deactiveTimeText.method_1877(character, key);
            }
            if (this.delayTimeText.field_2420 && (key == 14 || character >= '0' && character <= '9' || character == '.' || character == '\t')) {
                this.delayTimeText.method_1877(character, key);
            }
        }
        super.keyPressed(character, key);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.useTextFields) {
            this.delayTimeText.method_1879(mouseX, mouseY, button);
            this.activeTimeText.method_1879(mouseX, mouseY, button);
            this.deactiveTimeText.method_1879(mouseX, mouseY, button);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
