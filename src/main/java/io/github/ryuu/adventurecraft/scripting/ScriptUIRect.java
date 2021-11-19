package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class ScriptUIRect extends UIElement {
    public float width;
    public float height;
    public float red;
    public float green;
    public float blue;
    public float alpha;

    public ScriptUIRect(float xPos, float yPos, float w, float h, float r, float g, float b, float a) {
        this(xPos, yPos, w, h, r, g, b, a, Minecraft.minecraftInstance.v.scriptUI);
    }

    public ScriptUIRect(float xPos, float yPos, float w, float h, float r, float g, float b, float a, ScriptUIContainer parent) {
        this.red = 1.0F;
        this.green = 1.0F;
        this.blue = 1.0F;
        this.alpha = 1.0F;
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        this.width = w;
        this.height = h;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
        if (parent != null)
            parent.add(this);
    }

    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        float x = getXAtTime(partialTickTime);
        float y = getYAtTime(partialTickTime);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glDisable(3553);
        GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
        tessellator.start();
        tessellator.pos(x, (y + this.height), 0.0D);
        tessellator.pos((x + this.width), (y + this.height), 0.0D);
        tessellator.pos((x + this.width), y, 0.0D);
        tessellator.pos(x, y, 0.0D);
        tessellator.draw();
        GL11.glEnable(3553);
    }
}