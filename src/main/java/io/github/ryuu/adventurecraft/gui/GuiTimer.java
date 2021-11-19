package io.github.ryuu.adventurecraft.gui;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.NumberFormatException
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
import net.minecraft.client.gui.widgets.Textbox;
import net.minecraft.level.Level;

public class GuiTimer extends Screen {

    boolean ignoreNext = false;

    private TileEntityTimer timer;

    private int blockX;

    private int blockY;

    private int blockZ;

    private Level world;

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
        this.buttons.add((Object) new OptionButton(0, 4, 40, "Use Current Selection"));
        OptionButton b = new OptionButton(1, 4, 60, "Trigger Target");
        if (this.timer.resetOnTrigger) {
            b.text = "Reset Target";
        }
        this.buttons.add((Object) b);
        if (!this.useTextFields) {
            this.delayTime = new GuiSlider2(4, 4, 80, 10, String.format((String) "Delay for: %.2fs", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeDelay / 20.0f)) }), (float) this.timer.timeDelay / 20.0f / 60.0f);
            this.buttons.add((Object) this.delayTime);
            this.activeTime = new GuiSlider2(2, 4, 100, 10, String.format((String) "Active for: %.2fs", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeActive / 20.0f)) }), (float) this.timer.timeActive / 20.0f / 60.0f);
            this.buttons.add((Object) this.activeTime);
            this.deactiveTime = new GuiSlider2(3, 4, 120, 10, String.format((String) "Deactive for: %.2fs", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeDeactive / 20.0f)) }), (float) this.timer.timeDeactive / 20.0f / 60.0f);
            this.buttons.add((Object) this.deactiveTime);
        } else {
            this.delayTimeText = new Textbox(this, this.textManager, 80, 81, 70, 16, String.format((String) "%.2f", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeDelay / 20.0f)) }));
            this.activeTimeText = new Textbox(this, this.textManager, 80, 101, 70, 16, String.format((String) "%.2f", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeActive / 20.0f)) }));
            this.deactiveTimeText = new Textbox(this, this.textManager, 80, 121, 70, 16, String.format((String) "%.2f", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeDeactive / 20.0f)) }));
        }
        this.buttons.add((Object) new OptionButton(5, 4, 140, "Switch Input Mode"));
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            int blockID = this.world.getTileId(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.timer.id) {
                Blocks.timer.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
            }
        } else if (button.id == 1) {
            boolean bl = this.timer.resetOnTrigger = !this.timer.resetOnTrigger;
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
        this.drawTextWithShadow(this.textManager, String.format((String) "Min: (%d, %d, %d)", (Object[]) new Object[] { this.timer.minX, this.timer.minY, this.timer.minZ }), 4, 4, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, String.format((String) "Max: (%d, %d, %d)", (Object[]) new Object[] { this.timer.maxX, this.timer.maxY, this.timer.maxZ }), 4, 24, 0xE0E0E0);
        if (!this.timer.active && this.timer.canActivate) {
            this.drawTextWithShadow(this.textManager, "State: Ready", 4, 164, 0xE0E0E0);
        } else {
            if (this.timer.active) {
                this.drawTextWithShadow(this.textManager, "State: Active", 4, 164, 0xE0E0E0);
            } else if (!this.timer.canActivate) {
                this.drawTextWithShadow(this.textManager, "State: Deactive", 4, 164, 0xE0E0E0);
            }
            if (this.timer.ticksDelay > 0) {
                this.drawTextWithShadow(this.textManager, String.format((String) "Delay: %.2f", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.ticksDelay * 0.05f)) }), 4, 184, 0xE0E0E0);
            } else {
                this.drawTextWithShadow(this.textManager, String.format((String) "Time: %.2f", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.ticks * 0.05f)) }), 4, 184, 0xE0E0E0);
            }
        }
        if (!this.useTextFields) {
            this.timer.timeActive = (int) (this.activeTime.sliderValue * 60.0f * 20.0f);
            this.timer.timeDeactive = (int) (this.deactiveTime.sliderValue * 60.0f * 20.0f);
            this.timer.timeDelay = (int) (this.delayTime.sliderValue * 60.0f * 20.0f);
            this.delayTime.text = String.format((String) "Delay for: %.2fs", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeDelay / 20.0f)) });
            this.activeTime.text = String.format((String) "Active for: %.2fs", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeActive / 20.0f)) });
            this.deactiveTime.text = String.format((String) "Deactive for: %.2fs", (Object[]) new Object[] { Float.valueOf((float) ((float) this.timer.timeDeactive / 20.0f)) });
        } else {
            Float value;
            this.drawTextWithShadow(this.textManager, "Delay For:", 4, 84, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, "Active For:", 4, 104, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, "Deactive For:", 4, 124, 0xE0E0E0);
            this.activeTimeText.method_1883();
            this.deactiveTimeText.method_1883();
            this.delayTimeText.method_1883();
            try {
                value = Float.valueOf((String) this.activeTimeText.method_1876());
                if (value != null) {
                    this.timer.timeActive = (int) (value.floatValue() * 20.0f);
                }
            } catch (NumberFormatException e) {
                // empty catch block
            }
            try {
                value = Float.valueOf((String) this.deactiveTimeText.method_1876());
                if (value != null) {
                    this.timer.timeDeactive = (int) (value.floatValue() * 20.0f);
                }
            } catch (NumberFormatException e) {
                // empty catch block
            }
            try {
                value = Float.valueOf((String) this.delayTimeText.method_1876());
                if (value != null) {
                    this.timer.timeDelay = (int) (value.floatValue() * 20.0f);
                }
            } catch (NumberFormatException e) {
                // empty catch block
            }
        }
        this.world.getChunk(this.blockX, this.blockZ).method_885();
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

    public static void showUI(Level w, int x, int y, int z, TileEntityTimer timerClicked) {
        Minecraft.minecraftInstance.openScreen(new GuiTimer(w, x, y, z, timerClicked));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
