package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;
import net.minecraft.util.CharacterUtils;

public class GuiMessage extends MixinScreen {

    private TileEntityMessage msg;

    private TileEntityMessage soundFile;

    private MixinLevel world;

    private int page;

    public GuiMessage(MixinLevel w, TileEntityMessage tileEntityMsg) {
        this.world = w;
        this.msg = tileEntityMsg;
    }

    @Override
    public void init() {
        Button b;
        int maxEntries = 3 * ((this.height - 60) / 20);
        for (int i = 0; i + maxEntries * this.page - 1 < this.world.soundList.length && i < maxEntries; ++i) {
            String soundName = "None";
            if (i != 0 || this.page != 0) {
                soundName = this.world.soundList[i + maxEntries * this.page - 1];
            }
            b = new Button(i, 4 + i % 3 * this.width / 3, 60 + i / 3 * 20, (this.width - 16) / 3, 18, soundName);
            this.buttons.add((Object) b);
        }
        int numPages = this.world.soundList.length / maxEntries + 1;
        for (int i = 0; i < numPages; ++i) {
            b = new Button(100 + i, 4 + i * 50, 40, 46, 18, String.format((String) "Page %d", (Object[]) new Object[] { i + 1 }));
            this.buttons.add((Object) b);
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0 && this.page == 0) {
            this.msg.sound = "";
        } else if (button.id < 100) {
            int maxEntries = 3 * ((this.height - 60) / 20);
            this.msg.sound = this.world.soundList[button.id - 1 + maxEntries * this.page];
        } else {
            this.page = button.id - 100;
            this.buttons.clear();
            this.init();
        }
        this.world.getChunk(this.msg.x, this.msg.z).method_885();
    }

    @Override
    protected void keyPressed(char character, int key) {
        super.keyPressed(character, key);
        if (key == 14 && this.msg.message.length() > 0) {
            this.msg.message = this.msg.message.substring(0, this.msg.message.length() - 1);
        }
        if (CharacterUtils.SUPPORTED_CHARS.indexOf((int) character) >= 0 && this.msg.message.length() < 30) {
            this.msg.message = this.msg.message + character;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawTextWithShadow(this.textManager, String.format((String) "Message: '%s'", (Object[]) new Object[] { this.msg.message }), 4, 4, 0xE0E0E0);
        if (this.msg.sound != "") {
            this.drawTextWithShadow(this.textManager, String.format((String) "Sound: %s", (Object[]) new Object[] { this.msg.sound }), 4, 24, 0xE0E0E0);
        } else {
            this.drawTextWithShadow(this.textManager, String.format((String) "Sound: None", (Object[]) new Object[0]), 4, 24, 0xE0E0E0);
        }
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(MixinLevel w, TileEntityMessage tileEntityMsg) {
        Minecraft.minecraftInstance.openScreen(new GuiMessage(w, tileEntityMsg));
    }
}
