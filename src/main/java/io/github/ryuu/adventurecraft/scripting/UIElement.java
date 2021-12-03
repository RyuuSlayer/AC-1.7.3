package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.client.gui.ExOverlay;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;

@SuppressWarnings("unused")
public class UIElement {

    public float curX = 0.0f;

    public float curY = 0.0f;

    public float prevX = 0.0f;

    public float prevY = 0.0f;

    protected ScriptUIContainer parent;

    public void addToScreen() {
        if (AccessMinecraft.getInstance().overlay != null) {
            ((ExOverlay) AccessMinecraft.getInstance().overlay).getScriptUI().add(this);
        }
    }

    public void removeFromScreen() {
        if (this.parent != null) {
            this.parent.remove(this);
        }
    }

    public void pushToFront() {
        if (this.parent != null) {
            this.parent.add(this);
        }
    }

    public void pushToBack() {
        if (this.parent != null) {
            this.parent.addToBack(this);
        }
    }

    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
    }

    public void onUpdate() {
        this.prevX = this.curX;
        this.prevY = this.curY;
    }

    public float getX() {
        return this.curX;
    }

    public void setX(float x) {
        this.curX = x;
        this.prevX = x;
    }

    public float getY() {
        return this.curY;
    }

    public void setY(float y) {
        this.curY = y;
        this.prevY = y;
    }

    protected float getXAtTime(float partialTickTime) {
        return (1.0f - partialTickTime) * this.prevX + partialTickTime * this.curX;
    }

    protected float getYAtTime(float partialTickTime) {
        return (1.0f - partialTickTime) * this.prevY + partialTickTime * this.curY;
    }

    public void moveTo(float x, float y) {
        this.curX = x;
        this.curY = y;
    }

    public void moveBy(float x, float y) {
        this.curX += x;
        this.curY += y;
    }
}
