package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;

import java.util.Arrays;

public class GuiScriptStats extends MixinScreen {

    int maxSize = 90;

    JScriptInfo[] scriptInfo;

    public GuiScriptStats() {
        Object[] info = Minecraft.minecraftInstance.level.scriptHandler.scripts.values().toArray();
        int numGood = 0;
        for (Object o : info) {
            JScriptInfo JScriptInfo = (JScriptInfo) o;
            if (JScriptInfo.count <= 0) continue;
            ++numGood;
        }
        int index = 0;
        this.scriptInfo = new JScriptInfo[numGood];
        for (Object object : info) {
            JScriptInfo sInfo = (JScriptInfo) object;
            if (sInfo.count <= 0) continue;
            this.scriptInfo[index++] = sInfo;
        }
        for (JScriptInfo JScriptInfo : this.scriptInfo) {
            int s = Minecraft.minecraftInstance.textRenderer.getTextWidth(JScriptInfo.name);
            if (s <= this.maxSize) continue;
            this.maxSize = s;
        }
        this.maxSize += 10;
        Arrays.sort((Object[]) this.scriptInfo);
    }

    public static void showUI() {
        Minecraft.minecraftInstance.openScreen(new GuiScriptStats());
    }

    @Override
    public void init() {
    }

    @Override
    public void tick() {
    }

    @Override
    protected void buttonClicked(Button button) {
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawTextWithShadow(this.textManager, "Script", 4, 1, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, "Avg", this.maxSize, 1, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, "Max", this.maxSize + 50, 1, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, "Total", this.maxSize + 100, 1, 0xE0E0E0);
        this.drawTextWithShadow(this.textManager, "Count", this.maxSize + 150, 1, 0xE0E0E0);
        int yOffset = 10;
        for (JScriptInfo sInfo : this.scriptInfo) {
            double totTime = (double) sInfo.totalTime / 1000000.0;
            double avgTime = totTime / (double) sInfo.count;
            double maxTime = (double) sInfo.maxTime / 1000000.0;
            this.drawTextWithShadow(this.textManager, sInfo.name, 4, yOffset, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, String.format("%.2f", new Object[]{avgTime}), this.maxSize, yOffset, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, String.format("%.2f", new Object[]{maxTime}), this.maxSize + 50, yOffset, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, String.format("%.2f", new Object[]{totTime}), this.maxSize + 100, yOffset, 0xE0E0E0);
            this.drawTextWithShadow(this.textManager, String.format("%d", new Object[]{sInfo.count}), this.maxSize + 150, yOffset, 0xE0E0E0);
            yOffset += 10;
        }
        super.render(mouseX, mouseY, delta);
    }
}
