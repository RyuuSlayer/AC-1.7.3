package io.github.ryuu.adventurecraft.gui;

import java.util.Arrays;

import io.github.ryuu.adventurecraft.util.JScriptInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;

public class GuiScriptStats extends Screen {
    int maxSize = 90;

    JScriptInfo[] scriptInfo;

    public GuiScriptStats() {
        Object[] info = Minecraft.minecraftInstance.f.scriptHandler.scripts.values().toArray();
        int numGood = 0;
        for (Object o : info) {
            JScriptInfo sInfo = (JScriptInfo) o;
            if (sInfo.count > 0)
                numGood++;
        }
        int index = 0;
        this.scriptInfo = new JScriptInfo[numGood];
        for (Object o : info) {
            JScriptInfo sInfo = (JScriptInfo) o;
            if (sInfo.count > 0)
                this.scriptInfo[index++] = sInfo;
        }
        for (JScriptInfo sInfo : this.scriptInfo) {
            int s = Minecraft.minecraftInstance.q.a(sInfo.name);
            if (s > this.maxSize)
                this.maxSize = s;
        }
        this.maxSize += 10;
        Arrays.sort(this.scriptInfo);
    }

    public static void showUI() {
        Minecraft.minecraftInstance.a(new GuiScriptStats());
    }

    @Override
    public void init() {
    }

    @Override
    public void tick() {
    }

    @Override
    protected void buttonClicked(Button guibutton) {
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        drawTextWithShadow(this.textManager, "Script", 4, 1, 14737632);
        drawTextWithShadow(this.textManager, "Avg", this.maxSize, 1, 14737632);
        drawTextWithShadow(this.textManager, "Max", this.maxSize + 50, 1, 14737632);
        drawTextWithShadow(this.textManager, "Total", this.maxSize + 100, 1, 14737632);
        drawTextWithShadow(this.textManager, "Count", this.maxSize + 150, 1, 14737632);
        int yOffset = 10;
        for (JScriptInfo sInfo : this.scriptInfo) {
            double totTime = sInfo.totalTime / 1000000.0D;
            double avgTime = totTime / sInfo.count;
            double maxTime = sInfo.maxTime / 1000000.0D;
            drawTextWithShadow(this.textManager, sInfo.name, 4, yOffset, 14737632);
            drawTextWithShadow(this.textManager, String.format("%.2f", avgTime), this.maxSize, yOffset, 14737632);
            drawTextWithShadow(this.textManager, String.format("%.2f", maxTime), this.maxSize + 50, yOffset, 14737632);
            drawTextWithShadow(this.textManager, String.format("%.2f", totTime), this.maxSize + 100, yOffset, 14737632);
            drawTextWithShadow(this.textManager, String.format("%d", sInfo.count), this.maxSize + 150, yOffset, 14737632);
            yOffset += 10;
        }
        super.render(i, j, f);
    }
}