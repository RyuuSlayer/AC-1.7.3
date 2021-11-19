/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.script.ScriptUIContainer;
import io.github.ryuu.adventurecraft.mixin.item.MixinTextRenderer;
import io.github.ryuu.adventurecraft.mixin.item.MixinTextureManager;

public class UIElement {

    public float curX = 0.0f;

    public float curY = 0.0f;

    public float prevX = 0.0f;

    public float prevY = 0.0f;

    protected ScriptUIContainer parent;

    public void addToScreen() {
        if (Minecraft.minecraftInstance.overlay != null) {
            Minecraft.minecraftInstance.overlay.scriptUI.add(this);
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

    public void render(MixinTextRenderer fontRenderer, MixinTextureManager renderEngine, float partialTickTime) {
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
