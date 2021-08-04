package io.github.ryuu.adventurecraft.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;

public class ScriptUILabel extends UIElement {
    private String text;

    private String[] textLines;

    public boolean shadow;

    public boolean centered;

    public float red;

    public float green;

    public float blue;

    public float alpha;

    public ScriptUILabel(String label, float xPos, float yPos) {
        this(label, xPos, yPos, Minecraft.minecraftInstance.v.scriptUI);
    }

    public ScriptUILabel(String label, float xPos, float yPos, ScriptUIContainer parent) {
        this.text = "";
        this.shadow = true;
        this.centered = false;
        this.red = 1.0F;
        this.green = 1.0F;
        this.blue = 1.0F;
        this.alpha = 1.0F;
        this.text = label;
        this.textLines = label.split("\n");
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        if (parent != null)
            parent.add(this);
    }

    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        int color = Math.max(Math.min((int) (this.alpha * 255.0F), 255), 0);
        if (color == 0)
            return;
        color = (color << 8) + Math.max(Math.min((int) (this.red * 255.0F), 255), 0);
        color = (color << 8) + Math.max(Math.min((int) (this.green * 255.0F), 255), 0);
        color = (color << 8) + Math.max(Math.min((int) (this.blue * 255.0F), 255), 0);
        float xPos = getXAtTime(partialTickTime);
        float yPos = getYAtTime(partialTickTime);
        for (String line : this.textLines) {
            float newXPos = xPos;
            if (this.centered)
                newXPos -= (fontRenderer.a(line) / 2);
            if (this.shadow) {
                fontRenderer.drawStringWithShadow(line, newXPos, yPos, color);
            } else {
                fontRenderer.drawString(line, newXPos, yPos, color);
            }
            yPos += 9.0F;
        }
    }

    public String getText() {
        return this.text;
    }

    public void setText(String t) {
        this.text = t;
        this.textLines = t.split("\n");
    }
}
