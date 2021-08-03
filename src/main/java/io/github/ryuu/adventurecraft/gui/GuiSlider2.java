package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiSlider2 extends ke {
    public float sliderValue;

    public boolean dragging;

    public GuiSlider2(int i, int j, int k, int l, String s, float f) {
        super(i, j, k, 150, 20, s);
        this.dragging = false;
        this.sliderValue = f;
    }

    protected int a(boolean flag) {
        return 0;
    }

    protected void b(Minecraft minecraft, int i, int j) {
        if (!this.h)
            return;
        if (this.dragging) {
            this.sliderValue = (i - this.c + 4) / (this.a - 8);
            if (this.sliderValue < 0.0F)
                this.sliderValue = 0.0F;
            if (this.sliderValue > 1.0F)
                this.sliderValue = 1.0F;
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        b(this.c + (int) (this.sliderValue * (this.a - 8)), this.d, 0, 66, 4, 20);
        b(this.c + (int) (this.sliderValue * (this.a - 8)) + 4, this.d, 196, 66, 4, 20);
    }

    public boolean c(Minecraft minecraft, int i, int j) {
        if (super.c(minecraft, i, j)) {
            this.sliderValue = (i - this.c + 4) / (this.a - 8);
            if (this.sliderValue < 0.0F)
                this.sliderValue = 0.0F;
            if (this.sliderValue > 1.0F)
                this.sliderValue = 1.0F;
            this.dragging = true;
            return true;
        }
        return false;
    }

    public void a(int i, int j) {
        this.dragging = false;
    }
}
