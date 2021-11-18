package io.github.ryuu.adventurecraft.scripting;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class ScriptUIContainer extends UIElement {
    private final List<UIElement> uiElements;
    public String text;

    public ScriptUIContainer(float xPos, float yPos) {
        this(xPos, yPos, Minecraft.minecraftInstance.v.scriptUI);
    }

    public ScriptUIContainer(float xPos, float yPos, ScriptUIContainer p) {
        this.text = "";
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        this.uiElements = new LinkedList<>();
        if (p != null)
            p.add(this);
    }

    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        float x = getXAtTime(partialTickTime);
        float y = getYAtTime(partialTickTime);
        if (x != 0.0F || y != 0.0F) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, 0.0D);
        }
        for (UIElement uie : this.uiElements)
            uie.render(fontRenderer, renderEngine, partialTickTime);
        if (x != 0.0F || y != 0.0F)
            GL11.glPopMatrix();
    }

    public void add(UIElement uie) {
        if (uie.parent != null)
            uie.parent.remove(uie);
        this.uiElements.add(uie);
        uie.parent = this;
    }

    public void addToBack(UIElement uie) {
        if (uie.parent != null)
            uie.parent.remove(uie);
        this.uiElements.add(0, uie);
        uie.parent = this;
    }

    public void remove(UIElement uie) {
        this.uiElements.remove(uie);
        uie.parent = null;
    }

    public void clear() {
        this.uiElements.clear();
    }

    public void onUpdate() {
        this.prevX = this.curX;
        this.prevY = this.curY;
        for (UIElement e : this.uiElements)
            e.onUpdate();
    }
}