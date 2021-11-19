package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;

public class GuiMessage extends Screen {
    private final TileEntityMessage msg;
    private final Level world;
    private TileEntityMessage soundFile;
    private int page;

    public GuiMessage(Level w, TileEntityMessage tileEntityMsg) {
        this.world = w;
        this.msg = tileEntityMsg;
    }

    public static void showUI(Level w, TileEntityMessage tileEntityMsg) {
        Minecraft.minecraftInstance.a(new GuiMessage(w, tileEntityMsg));
    }

    @Override
    public void init() {
        int maxEntries = 3 * (this.height - 60) / 20;
        for (int i = 0; i + maxEntries * this.page - 1 < this.world.soundList.length && i < maxEntries; i++) {
            String soundName = "None";
            if (i != 0 || this.page != 0)
                soundName = this.world.soundList[i + maxEntries * this.page - 1];
            Button b = new Button(i, 4 + i % 3 * this.width / 3, 60 + i / 3 * 20, (this.width - 16) / 3, 18, soundName);
            this.buttons.add(b);
        }
        int numPages = this.world.soundList.length / maxEntries + 1;
        for (int j = 0; j < numPages; j++) {
            Button b = new Button(100 + j, 4 + j * 50, 40, 46, 18, String.format("Page %d", j + 1));
            this.buttons.add(b);
        }
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0 && this.page == 0) {
            this.msg.sound = "";
        } else if (guibutton.id < 100) {
            int maxEntries = 3 * (this.height - 60) / 20;
            this.msg.sound = this.world.soundList[guibutton.id - 1 + maxEntries * this.page];
        } else {
            this.page = guibutton.id - 100;
            this.buttons.clear();
            init();
        }
        this.world.getChunk(this.msg.x, this.msg.z).method_885();
    }

    @Override
    protected void keyPressed(char c, int i) {
        super.keyPressed(c, i);
        if (i == 14 && this.msg.message.length() > 0)
            this.msg.message = this.msg.message.substring(0, this.msg.message.length() - 1);
        if (fp.a.indexOf(c) >= 0 && this.msg.message.length() < 30)
            this.msg.message += c;
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        drawTextWithShadow(this.textManager, String.format("Message: '%s'", this.msg.message), 4, 4, 14737632);
        if (this.msg.sound != "") {
            drawTextWithShadow(this.textManager, String.format("Sound: %s", this.msg.sound), 4, 24, 14737632);
        } else {
            drawTextWithShadow(this.textManager, String.format("Sound: None"), 4, 24, 14737632);
        }
        super.render(i, j, f);
    }
}