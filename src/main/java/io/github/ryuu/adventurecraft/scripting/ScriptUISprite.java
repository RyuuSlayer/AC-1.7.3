package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class ScriptUISprite extends UIElement {

    public String texture;

    public float width;

    public float height;

    public float imageWidth = 256.0f;

    public float imageHeight = 256.0f;

    public double u;

    public double v;

    public float red = 1.0f;

    public float green = 1.0f;

    public float blue = 1.0f;

    public float alpha = 1.0f;

    public ScriptUISprite(String t, float xPos, float yPos, float w, float h, double uT, double vT) {
        this(t, xPos, yPos, w, h, uT, vT, AccessMinecraft.getInstance().overlay.scriptUI);
    }

    public ScriptUISprite(String t, float xPos, float yPos, float w, float h, double uT, double vT, ScriptUIContainer parent) {
        this.texture = t;
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        this.width = w;
        this.height = h;
        this.u = uT;
        this.v = vT;
        if (parent != null) {
            parent.add(this);
        }
    }

    @Override
    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        if (this.texture.startsWith("http")) {
            renderEngine.bindTexture(renderEngine.getTextureId(this.texture, "./pack.png"));
        } else {
            renderEngine.bindTexture(renderEngine.getTextureId(this.texture));
        }
        GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
        float x = this.getXAtTime(partialTickTime);
        float y = this.getYAtTime(partialTickTime);
        float f = 1.0f / this.imageWidth;
        float f1 = 1.0f / this.imageHeight;
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        tessellator.vertex(x, y + this.height, 0.0, this.u * (double) f, (this.v + (double) this.height) * (double) f1);
        tessellator.vertex(x + this.width, y + this.height, 0.0, (float) (this.u + (double) this.width) * f, (float) (this.v + (double) this.height) * f1);
        tessellator.vertex(x + this.width, y, 0.0, (float) (this.u + (double) this.width) * f, this.v * (double) f1);
        tessellator.vertex(x, y, 0.0, this.u * (double) f, this.v * (double) f1);
        tessellator.draw();
    }
}
