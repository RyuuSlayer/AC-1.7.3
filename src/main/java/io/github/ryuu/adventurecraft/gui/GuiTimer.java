package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.blocks.Blocks;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityTimer;
import net.minecraft.client.Minecraft;

public class GuiTimer extends da {
    boolean ignoreNext;

    private TileEntityTimer timer;

    private int blockX;

    private int blockY;

    private int blockZ;

    private fd world;

    boolean useTextFields;

    GuiSlider2 activeTime;

    GuiSlider2 deactiveTime;

    GuiSlider2 delayTime;

    private ro activeTimeText;

    private ro deactiveTimeText;

    private ro delayTimeText;

    public GuiTimer(fd w, int x, int y, int z, TileEntityTimer timerClicked) {
        this.ignoreNext = false;
        this.world = w;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.timer = timerClicked;
    }

    public void a() {
        if (this.useTextFields) {
            this.delayTimeText.b();
            this.activeTimeText.b();
            this.deactiveTimeText.b();
        }
    }

    public void b() {
        this.e.clear();
        this.e.add(new ab(0, 4, 40, "Use Current Selection"));
        ab b = new ab(1, 4, 60, "Trigger Target");
        if (this.timer.resetOnTrigger)
            b.e = "Reset Target";
        this.e.add(b);
        if (!this.useTextFields) {
            this.delayTime = new GuiSlider2(4, 4, 80, 10, String.format("Delay for: %.2fs", new Object[] { Float.valueOf(this.timer.timeDelay / 20.0F) }), this.timer.timeDelay / 20.0F / 60.0F);
            this.e.add(this.delayTime);
            this.activeTime = new GuiSlider2(2, 4, 100, 10, String.format("Active for: %.2fs", new Object[] { Float.valueOf(this.timer.timeActive / 20.0F) }), this.timer.timeActive / 20.0F / 60.0F);
            this.e.add(this.activeTime);
            this.deactiveTime = new GuiSlider2(3, 4, 120, 10, String.format("Deactive for: %.2fs", new Object[] { Float.valueOf(this.timer.timeDeactive / 20.0F) }), this.timer.timeDeactive / 20.0F / 60.0F);
            this.e.add(this.deactiveTime);
        } else {
            this.delayTimeText = new ro(this, this.g, 80, 81, 70, 16, String.format("%.2f", new Object[] { Float.valueOf(this.timer.timeDelay / 20.0F) }));
            this.activeTimeText = new ro(this, this.g, 80, 101, 70, 16, String.format("%.2f", new Object[] { Float.valueOf(this.timer.timeActive / 20.0F) }));
            this.deactiveTimeText = new ro(this, this.g, 80, 121, 70, 16, String.format("%.2f", new Object[] { Float.valueOf(this.timer.timeDeactive / 20.0F) }));
        }
        this.e.add(new ab(5, 4, 140, "Switch Input Mode"));
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            int blockID = this.world.a(this.blockX, this.blockY, this.blockZ);
            if (blockID == Blocks.timer.bn)
                Blocks.timer.setTriggerToSelection(this.world, this.blockX, this.blockY, this.blockZ);
        } else if (guibutton.f == 1) {
            this.timer.resetOnTrigger = !this.timer.resetOnTrigger;
            if (this.timer.resetOnTrigger) {
                guibutton.e = "Reset Target";
            } else {
                guibutton.e = "Trigger Target";
            }
        } else if (guibutton.f == 5) {
            if (!this.ignoreNext) {
                this.useTextFields = !this.useTextFields;
                b();
                this.ignoreNext = true;
            }
        }
    }

    public void a(int i, int j, float f) {
        this.ignoreNext = false;
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, String.format("Min: (%d, %d, %d)", new Object[] { Integer.valueOf(this.timer.minX), Integer.valueOf(this.timer.minY), Integer.valueOf(this.timer.minZ) }), 4, 4, 14737632);
        b(this.g, String.format("Max: (%d, %d, %d)", new Object[] { Integer.valueOf(this.timer.maxX), Integer.valueOf(this.timer.maxY), Integer.valueOf(this.timer.maxZ) }), 4, 24, 14737632);
        if (!this.timer.active && this.timer.canActivate) {
            b(this.g, "State: Ready", 4, 164, 14737632);
        } else {
            if (this.timer.active) {
                b(this.g, "State: Active", 4, 164, 14737632);
            } else if (!this.timer.canActivate) {
                b(this.g, "State: Deactive", 4, 164, 14737632);
            }
            if (this.timer.ticksDelay > 0) {
                b(this.g, String.format("Delay: %.2f", new Object[] { Float.valueOf(this.timer.ticksDelay * 0.05F) }), 4, 184, 14737632);
            } else {
                b(this.g, String.format("Time: %.2f", new Object[] { Float.valueOf(this.timer.ticks * 0.05F) }), 4, 184, 14737632);
            }
        }
        if (!this.useTextFields) {
            this.timer.timeActive = (int)(this.activeTime.sliderValue * 60.0F * 20.0F);
            this.timer.timeDeactive = (int)(this.deactiveTime.sliderValue * 60.0F * 20.0F);
            this.timer.timeDelay = (int)(this.delayTime.sliderValue * 60.0F * 20.0F);
            this.delayTime.e = String.format("Delay for: %.2fs", new Object[] { Float.valueOf(this.timer.timeDelay / 20.0F) });
            this.activeTime.e = String.format("Active for: %.2fs", new Object[] { Float.valueOf(this.timer.timeActive / 20.0F) });
            this.deactiveTime.e = String.format("Deactive for: %.2fs", new Object[] { Float.valueOf(this.timer.timeDeactive / 20.0F) });
        } else {
            b(this.g, "Delay For:", 4, 84, 14737632);
            b(this.g, "Active For:", 4, 104, 14737632);
            b(this.g, "Deactive For:", 4, 124, 14737632);
            this.activeTimeText.c();
            this.deactiveTimeText.c();
            this.delayTimeText.c();
            try {
                Float value = Float.valueOf(this.activeTimeText.a());
                if (value != null)
                    this.timer.timeActive = (int)(value.floatValue() * 20.0F);
            } catch (NumberFormatException e) {}
            try {
                Float value = Float.valueOf(this.deactiveTimeText.a());
                if (value != null)
                    this.timer.timeDeactive = (int)(value.floatValue() * 20.0F);
            } catch (NumberFormatException e) {}
            try {
                Float value = Float.valueOf(this.delayTimeText.a());
                if (value != null)
                    this.timer.timeDelay = (int)(value.floatValue() * 20.0F);
            } catch (NumberFormatException e) {}
        }
        this.world.b(this.blockX, this.blockZ).g();
        super.a(i, j, f);
    }

    protected void a(char c, int i) {
        if (this.useTextFields) {
            if (this.activeTimeText.a && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                this.activeTimeText.a(c, i);
            if (this.deactiveTimeText.a && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                this.deactiveTimeText.a(c, i);
            if (this.delayTimeText.a && (i == 14 || (c >= '0' && c <= '9') || c == '.' || c == '\t'))
                this.delayTimeText.a(c, i);
        }
        super.a(c, i);
    }

    protected void a(int i, int j, int k) {
        if (this.useTextFields) {
            this.delayTimeText.a(i, j, k);
            this.activeTimeText.a(i, j, k);
            this.deactiveTimeText.a(i, j, k);
        }
        super.a(i, j, k);
    }

    public static void showUI(fd w, int x, int y, int z, TileEntityTimer timerClicked) {
        Minecraft.minecraftInstance.a(new AC_GuiTimer(w, x, y, z, timerClicked));
    }

    public boolean c() {
        return false;
    }
}