package io.github.ryuu.adventurecraft.scripting;

import io.github.ryuu.adventurecraft.extensions.client.gui.ExOverlay;
import io.github.ryuu.adventurecraft.extensions.client.render.ExTextRenderer;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.texture.TextureManager;

@SuppressWarnings("unused")
public class ScriptUILabel extends UIElement {

    public boolean shadow = true;
    public boolean centered = false;
    public float red = 1.0f;
    public float green = 1.0f;
    public float blue = 1.0f;
    public float alpha = 1.0f;
    private String text = "";
    private String[] textLines;

    public ScriptUILabel(String label, float xPos, float yPos) {
        this(label, xPos, yPos, ((ExOverlay) AccessMinecraft.getInstance().overlay).getScriptUI());
    }

    public ScriptUILabel(String label, float xPos, float yPos, ScriptUIContainer parent) {
        this.text = label;
        this.textLines = label.split("\n");
        this.prevX = this.curX = xPos;
        this.prevY = this.curY = yPos;
        if (parent != null) {
            parent.add(this);
        }
    }

    @Override
    public void render(TextRenderer fontRenderer, TextureManager renderEngine, float partialTickTime) {
        int color = Math.max(Math.min((int) (this.alpha * 255.0f), 255), 0);
        if (color == 0) {
            return;
        }
        color = (color << 8) + Math.max(Math.min((int) (this.red * 255.0f), 255), 0);
        color = (color << 8) + Math.max(Math.min((int) (this.green * 255.0f), 255), 0);
        color = (color << 8) + Math.max(Math.min((int) (this.blue * 255.0f), 255), 0);
        float xPos = this.getXAtTime(partialTickTime);
        float yPos = this.getYAtTime(partialTickTime);
        for (String line : this.textLines) {
            float newXPos = xPos;
            if (this.centered) {
                newXPos -= (float) (fontRenderer.getTextWidth(line) / 2);
            }
            if (this.shadow) {
                ((ExTextRenderer) fontRenderer).drawStringWithShadow(line, newXPos, yPos, color);
            } else {
                ((ExTextRenderer) fontRenderer).drawString(line, newXPos, yPos, color);
            }
            yPos += 9.0f;
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
