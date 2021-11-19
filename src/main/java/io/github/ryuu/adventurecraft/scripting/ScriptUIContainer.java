/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.LinkedList
 *  java.util.List
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.opengl.GL11
 */
package io.github.ryuu.adventurecraft.scripting;

import java.util.LinkedList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.script.UIElement;
import org.lwjgl.opengl.GL11;
import io.github.ryuu.adventurecraft.mixin.item.MixinTextRenderer;
import io.github.ryuu.adventurecraft.mixin.item.MixinTextureManager;

public class ScriptUIContainer extends UIElement {

    public String text = "";

    private List<UIElement> uiElements;

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
    public void render(MixinTextRenderer fontRenderer, MixinTextureManager renderEngine, float partialTickTime) {
        float x = this.getXAtTime(partialTickTime);
        float y = this.getYAtTime(partialTickTime);
        if (x != 0.0f || y != 0.0f) {
            GL11.glPushMatrix();
            GL11.glTranslated((double) x, (double) y, (double) 0.0);
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
        this.uiElements.add((Object) uie);
        uie.parent = this;
    }

    public void addToBack(UIElement uie) {
        if (uie.parent != null) {
            uie.parent.remove(uie);
        }
        this.uiElements.add(0, (Object) uie);
        uie.parent = this;
    }

    public void remove(UIElement uie) {
        this.uiElements.remove((Object) uie);
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
