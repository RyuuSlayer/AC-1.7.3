package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.util.InventoryDebug;
import org.lwjgl.opengl.GL11;

public class GuiPalette extends hp {
    private final InventoryDebug palette;

    GuiSlider2 extraWidth;

    GuiSlider2 extraDepth;

    private ke a;

    public GuiPalette(lw iinventory, InventoryDebug p) {
        super(iinventory, (lw) p);
        this.palette = p;
    }

    public void b() {
        super.b();
        this.extraDepth = new GuiSlider2(50, this.c / 2 + 2, 3, 10, String.format("Extra Depth: %d", Integer.valueOf(this.b.c.destroyExtraDepth)), this.b.c.destroyExtraDepth / 16.0F);
        this.extraWidth = new GuiSlider2(50, this.c / 2 - 2 - this.extraDepth.a, 3, 10, String.format("Extra Width: %d", Integer.valueOf(this.b.c.destroyExtraWidth)), this.b.c.destroyExtraWidth / 5.0F);
        this.e.add(this.extraDepth);
        this.e.add(this.extraWidth);
    }

    protected void a(char c, int i) {
        super.a(c, i);
        if (i == 65 && this.palette.firstItem > 1) {
            this.palette.fillInventoryBackwards(this.palette.firstItem - 1);
        } else if (i == 66 && !this.palette.atEnd) {
            this.palette.fillInventory(this.palette.lastItem + 1);
        }
    }

    public void a(int i, int j, float f) {
        super.a(i, j, f);
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        for (int k = 0; k < this.e.size(); k++) {
            ke guibutton = this.e.get(k);
            guibutton.a(this.b, i, j);
        }
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
        this.b.c.destroyExtraDepth = (int) Math.min(16.0F * this.extraDepth.sliderValue, 15.0F);
        this.b.c.destroyExtraWidth = (int) Math.min(5.0F * this.extraWidth.sliderValue, 4.0F);
        this.extraWidth.e = String.format("Extra Width: %d", new Object[]{Integer.valueOf(this.b.c.destroyExtraWidth)});
        this.extraDepth.e = String.format("Extra Depth: %d", new Object[]{Integer.valueOf(this.b.c.destroyExtraDepth)});
    }

    protected void a(int i, int j, int k) {
        if (k == 0)
            for (int l = 0; l < this.e.size(); l++) {
                ke guibutton = this.e.get(l);
                if (guibutton.c(this.b, i, j)) {
                    this.a = guibutton;
                    this.b.B.a("random.click", 1.0F, 1.0F);
                    a(guibutton);
                }
            }
        super.a(i, j, k);
    }

    protected void b(int i, int j, int k) {
        if (this.a != null && k == 0) {
            this.a.a(i, j);
            this.a = null;
        }
        super.b(i, j, k);
    }
}