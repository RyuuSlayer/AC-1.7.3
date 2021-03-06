package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.client.gui.ExOverlay;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("unused")
public class ScriptUIRect extends UIElement {

    public float width;

    public float height;

    public float red = 1.0f;

    public float green = 1.0f;

    public float blue = 1.0f;

    public float alpha = 1.0f;

    public ScriptUIRect(float xPos, float yPos, float w, float h, float r, float g, float b, float a) {
        this(xPos, yPos, w, h, r, g, b, a, ((ExOverlay) AccessMinecraft.getInstance().overlay).getScriptUI());
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
    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        float x = this.getXAtTime(partialTickTime);
        float y = this.getYAtTime(partialTickTime);
        Tessellator tessellator = Tessellator.INSTANCE;
        GL11.glDisable(3553);
        GL11.glColor4f(this.red, this.green, this.blue, this.alpha);
        tessellator.start();
        tessellator.pos(x, y + this.height, 0.0);
        tessellator.pos(x + this.width, y + this.height, 0.0);
        tessellator.pos(x + this.width, y, 0.0);
        tessellator.pos(x, y, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
    }
}
