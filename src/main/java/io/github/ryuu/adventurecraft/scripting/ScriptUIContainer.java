package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

import java.util.LinkedList;
import java.util.List;

public class ScriptUIContainer extends UIElement {

    private final List<UIElement> uiElements;
    public String text = "";

    public ScriptUIContainer(float xPos, float yPos) {
        this(xPos, yPos, Minecraft.minecraftInstance.overlay.scriptUI);
    }

    public ScriptUIContainer(float xPos, float yPos, ScriptUIContainer p) {
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        this.uiElements = new LinkedList();
        if (p != null) {
            p.add(this);
        }
    }

    @Override
    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        float x = this.getXAtTime(partialTickTime);
        float y = this.getYAtTime(partialTickTime);
        if (x != 0.0f || y != 0.0f) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, 0.0);
        }
        for (UIElement uie : this.uiElements) {
            uie.render(fontRenderer, renderEngine, partialTickTime);
        }
        if (x != 0.0f || y != 0.0f) {
            GL11.glPopMatrix();
        }
    }

    public void add(UIElement uie) {
        if (uie.parent != null) {
            uie.parent.remove(uie);
        }
        this.uiElements.add(uie);
        uie.parent = this;
    }

    public void addToBack(UIElement uie) {
        if (uie.parent != null) {
            uie.parent.remove(uie);
        }
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

    @Override
    public void onUpdate() {
        this.prevX = this.curX;
        this.prevY = this.curY;
        for (UIElement e : this.uiElements) {
            e.onUpdate();
        }
    }
}
