package io.github.ryuu.adventurecraft.scripting;

import ji;
import net.minecraft.client.Minecraft;
import nw;
import org.lwjgl.opengl.GL11;
import sj;

public class ScriptUISprite extends UIElement {
    public String texture;

    public float width;

    public float height;

    public float imageWidth;

    public float imageHeight;

    public double u;

    public double v;

    public float red;

    public float green;

    public float blue;

    public float alpha;

    public ScriptUISprite(String t, float xPos, float yPos, float w, float h, double uT, double vT) {
        this(t, xPos, yPos, w, h, uT, vT, Minecraft.minecraftInstance.v.scriptUI);
    }

    public ScriptUISprite(String t, float xPos, float yPos, float w, float h, double uT, double vT, ScriptUIContainer parent) {
        this.imageWidth = 256.0F;
        this.imageHeight = 256.0F;
        this.red = 1.0F;
        this.green = 1.0F;
        this.blue = 1.0F;
        this.alpha = 1.0F;
        this.texture = t;
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        this.width = w;
        this.height = h;
        this.u = uT;
        this.v = vT;
        if (parent != null)
            parent.add(this);
    }

    public void render(sj fontRenderer, ji renderEngine, float partialTickTime) {
        if (this.texture.startsWith("http")) {
            renderEngine.b(renderEngine.a(this.texture, "./pack.png"));
        } else {
            renderEngine.b(renderEngine.b(this.texture));
        }
        GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
        float x = getXAtTime(partialTickTime);
        float y = getYAtTime(partialTickTime);
        float f = 1.0F / this.imageWidth;
        float f1 = 1.0F / this.imageHeight;
        nw tessellator = nw.a;
        tessellator.b();
        tessellator.a(x, (y + this.height), 0.0D, this.u * f, (this.v + this.height) * f1);
        tessellator.a((x + this.width), (y + this.height), 0.0D, ((float) (this.u + this.width) * f), ((float) (this.v + this.height) * f1));
        tessellator.a((x + this.width), y, 0.0D, ((float) (this.u + this.width) * f), this.v * f1);
        tessellator.a(x, y, 0.0D, this.u * f, this.v * f1);
        tessellator.a();
    }
}