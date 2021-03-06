package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMessage;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;
import net.minecraft.util.CharacterUtils;

public class GuiMessage extends Screen {

    private final TileEntityMessage msg;
    private final Level level;
    private TileEntityMessage soundFile;
    private int page;

    public GuiMessage(Level level, TileEntityMessage tileEntityMsg) {
        this.level = level;
        this.msg = tileEntityMsg;
    }

    public static void showUI(Level level, TileEntityMessage tileEntityMsg) {
        AccessMinecraft.getInstance().openScreen(new GuiMessage(level, tileEntityMsg));
    }

    @Override
    public void init() {
        int maxEntries = 3 * ((this.height - 60) / 20);
        String[] soundList = ((ExLevel) this.level).getSoundList();
        for (int i = 0; i + maxEntries * this.page - 1 < soundList.length && i < maxEntries; ++i) {
            String soundName = "None";
            if (i != 0 || this.page != 0) {
                soundName = soundList[i + maxEntries * this.page - 1];
            }
            Button b = new Button(i, 4 + i % 3 * this.width / 3, 60 + i / 3 * 20, (this.width - 16) / 3, 18, soundName);
            this.buttons.add(b);
        }
        int numPages = soundList.length / maxEntries + 1;
        for (int i = 0; i < numPages; ++i) {
            Button b = new Button(100 + i, 4 + i * 50, 40, 46, 18, String.format("Page %d", i + 1));
            this.buttons.add(b);
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0 && this.page == 0) {
            this.msg.sound = "";
        } else if (button.id < 100) {
            int maxEntries = 3 * ((this.height - 60) / 20);
            String[] soundList = ((ExLevel) this.level).getSoundList();
            this.msg.sound = soundList[button.id - 1 + maxEntries * this.page];
        } else {
            this.page = button.id - 100;
            this.buttons.clear();
            this.init();
        }
        this.level.getChunk(this.msg.x, this.msg.z).markDirty();
    }

    @Override
    protected void keyPressed(char character, int key) {
        super.keyPressed(character, key);
        if (key == 14 && this.msg.message.length() > 0) {
            this.msg.message = this.msg.message.substring(0, this.msg.message.length() - 1);
        }
        if (CharacterUtils.SUPPORTED_CHARS.indexOf(character) >= 0 && this.msg.message.length() < 30) {
            this.msg.message = this.msg.message + character;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawTextWithShadow(this.textManager, String.format("Message: '%s'", this.msg.message), 4, 4, 0xE0E0E0);
        if (this.msg.sound != "") {
            this.drawTextWithShadow(this.textManager, String.format("Sound: %s", this.msg.sound), 4, 24, 0xE0E0E0);
        } else {
            this.drawTextWithShadow(this.textManager, "Sound: None", 4, 24, 0xE0E0E0);
        }
        super.render(mouseX, mouseY, delta);
    }
}
