package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;

public class GuiSlider2 extends Button {
    public float sliderValue;

    public boolean dragging;

    public GuiSlider2(int i, int j, int k, int l, String s, float f) {
        super(i, j, k, 150, 20, s);
        this.dragging = false;
        this.sliderValue = f;
    }

    protected int getYImage(boolean flag) {
        return 0;
    }

    protected void postRender(Minecraft minecraft, int i, int j) {
        if (!this.visible)
            return;
        if (this.dragging) {
            this.sliderValue = (i - this.x + 4) / (this.width - 8);
            if (this.sliderValue < 0.0F)
                this.sliderValue = 0.0F;
            if (this.sliderValue > 1.0F)
                this.sliderValue = 1.0F;
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(this.x + (int) (this.sliderValue * (this.width - 8)), this.y, 0, 66, 4, 20);
        blit(this.x + (int) (this.sliderValue * (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
    }

    public boolean isMouseOver(Minecraft minecraft, int i, int j) {
        if (super.isMouseOver(minecraft, i, j)) {
            this.sliderValue = (i - this.x + 4) / (this.width - 8);
            if (this.sliderValue < 0.0F)
                this.sliderValue = 0.0F;
            if (this.sliderValue > 1.0F)
                this.sliderValue = 1.0F;
            this.dragging = true;
            return true;
        }
        return false;
    }

    public void mouseReleased(int i, int j) {
        this.dragging = false;
    }
}
