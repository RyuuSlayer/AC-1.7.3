package io.github.ryuu.adventurecraft.scripting;

import ji;
import net.minecraft.client.Minecraft;
import nw;
import org.lwjgl.opengl.GL11;
import sj;

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

    public void render(sj fontRenderer, ji renderEngine, float partialTickTime) {
        float x = getXAtTime(partialTickTime);
        float y = getYAtTime(partialTickTime);
        nw tessellator = nw.a;
        GL11.glDisable(3553);
        GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
        tessellator.b();
        tessellator.a(x, (y + this.height), 0.0D);
        tessellator.a((x + this.width), (y + this.height), 0.0D);
        tessellator.a((x + this.width), y, 0.0D);
        tessellator.a(x, y, 0.0D);
        tessellator.a();
        GL11.glEnable(3553);
    }
}
