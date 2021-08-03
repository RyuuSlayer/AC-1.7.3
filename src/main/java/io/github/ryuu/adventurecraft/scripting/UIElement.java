package io.github.ryuu.adventurecraft.scripting;

import ji;
import net.minecraft.client.Minecraft;
import sj;

public class UIElement {
    public void addToScreen() {
        if (Minecraft.minecraftInstance.v != null)
            Minecraft.minecraftInstance.v.scriptUI.add(this);
    }

    public void removeFromScreen() {
        if (this.parent != null)
            this.parent.remove(this);
    }

    public void pushToFront() {
        if (this.parent != null)
            this.parent.add(this);
    }

    public void pushToBack() {
        if (this.parent != null)
            this.parent.addToBack(this);
    }

    public void render(sj fontRenderer, ji renderEngine, float partialTickTime) {
    }

    public void onUpdate() {
        this.prevX = this.curX;
        this.prevY = this.curY;
    }

    public float getX() {
        return this.curX;
    }

    public void setX(float x) {
        this.curX = this.prevX = x;
    }

    public float getY() {
        return this.curY;
    }

    public void setY(float x) {
        this.curY = this.prevY = x;
    }

    protected float getXAtTime(float partialTickTime) {
        return (1.0F - partialTickTime) * this.prevX + partialTickTime * this.curX;
    }

    protected float getYAtTime(float partialTickTime) {
        return (1.0F - partialTickTime) * this.prevY + partialTickTime * this.curY;
    }

    public void moveTo(float x, float y) {
        this.curX = x;
        this.curY = y;
    }

    public void moveBy(float x, float y) {
        this.curX += x;
        this.curY += y;
    }

    public float curX = 0.0F;

    public float curY = 0.0F;

    public float prevX = 0.0F;

    public float prevY = 0.0F;

    protected ScriptUIContainer parent;
}
