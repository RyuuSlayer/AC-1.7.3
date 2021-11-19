/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.opengl.GL11
 */
package io.github.ryuu.adventurecraft.scripting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.script.ScriptUIContainer;
import net.minecraft.script.UIElement;
import org.lwjgl.opengl.GL11;
import io.github.ryuu.adventurecraft.mixin.item.MixinTextRenderer;
import io.github.ryuu.adventurecraft.mixin.item.MixinTextureManager;

public class ScriptUIRect extends UIElement {

    public float width;

    public float height;

    public float red = 1.0f;

    public float green = 1.0f;

    public float blue = 1.0f;

    public float alpha = 1.0f;

    public ScriptUIRect(float xPos, float yPos, float w, float h, float r, float g, float b, float a) {
        this(xPos, yPos, w, h, r, g, b, a, Minecraft.minecraftInstance.overlay.scriptUI);
    }

    public ScriptUIRect(float xPos, float yPos, float w, float h, float r, float g, float b, float a, ScriptUIContainer parent) {
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        this.width = w;
        this.height = h;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
        if (parent != null) {
            parent.add(this);
        }
    }

    @Override
    public void render(MixinTextRenderer fontRenderer, MixinTextureManager renderEngine, float partialTickTime) {
        float x = this.getXAtTime(partialTickTime);
        float y = this.getYAtTime(partialTickTime);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glDisable((int) 3553);
        GL11.glColor4f((float) this.red, (float) this.green, (float) this.blue, (float) this.alpha);
        tessellator.start();
        tessellator.pos(x, y + this.height, 0.0);
        tessellator.pos(x + this.width, y + this.height, 0.0);
        tessellator.pos(x + this.width, y, 0.0);
        tessellator.pos(x, y, 0.0);
        tessellator.draw();
        GL11.glEnable((int) 3553);
    }
}
