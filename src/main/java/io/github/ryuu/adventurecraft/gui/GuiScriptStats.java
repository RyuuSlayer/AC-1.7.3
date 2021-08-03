package io.github.ryuu.adventurecraft.gui;

import java.util.Arrays;

import io.github.ryuu.adventurecraft.util.JScriptInfo;
import net.minecraft.client.Minecraft;

public class GuiScriptStats extends da {
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

    public void b() {
    }

    public void a() {
    }

    protected void a(ke guibutton) {
    }

    public void a(int i, int j, float f) {
        i();
        b(this.g, "Script", 4, 1, 14737632);
        b(this.g, "Avg", this.maxSize, 1, 14737632);
        b(this.g, "Max", this.maxSize + 50, 1, 14737632);
        b(this.g, "Total", this.maxSize + 100, 1, 14737632);
        b(this.g, "Count", this.maxSize + 150, 1, 14737632);
        int yOffset = 10;
        for (JScriptInfo sInfo : this.scriptInfo) {
            double totTime = sInfo.totalTime / 1000000.0D;
            double avgTime = totTime / sInfo.count;
            double maxTime = sInfo.maxTime / 1000000.0D;
            b(this.g, sInfo.name, 4, yOffset, 14737632);
            b(this.g, String.format("%.2f", new Object[]{Double.valueOf(avgTime)}), this.maxSize, yOffset, 14737632);
            b(this.g, String.format("%.2f", new Object[]{Double.valueOf(maxTime)}), this.maxSize + 50, yOffset, 14737632);
            b(this.g, String.format("%.2f", new Object[]{Double.valueOf(totTime)}), this.maxSize + 100, yOffset, 14737632);
            b(this.g, String.format("%d", new Object[]{Integer.valueOf(sInfo.count)}), this.maxSize + 150, yOffset, 14737632);
            yOffset += 10;
        }
        super.a(i, j, f);
    }

    public static void showUI() {
        Minecraft.minecraftInstance.a(new GuiScriptStats());
    }
}