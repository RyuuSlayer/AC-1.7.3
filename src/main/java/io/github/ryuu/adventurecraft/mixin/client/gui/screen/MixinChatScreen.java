package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import io.github.ryuu.adventurecraft.util.ClipboardHandler;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.CharacterUtils;
import org.lwjgl.input.Keyboard;

public class MixinChatScreen extends Screen {
    protected String field_786 = "";
    private int field_787 = 0;
    private static final String field_788 = CharacterUtils.SUPPORTED_CHARS;

    public void init() {
        Keyboard.enableRepeatEvents(true);
    }

    public void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

    public void tick() {
        ++this.field_787;
    }

    protected void keyPressed(char character, int key) {
        if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157) || Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)) {
            if (key == 47) {
                this.field_786 = ClipboardHandler.getClipboard();
                return;
            }
            if (key == 46) {
                ClipboardHandler.setClipboard(this.field_786);
                return;
            }
        }
        if (key == 1) {
            this.minecraft.openScreen(null);
            return;
        }
        if (key == 28) {
            String s1;
            String s = this.field_786.trim();
            if (s.length() > 0 && !this.minecraft.handleClientCommand(s1 = this.field_786.trim())) {
                this.minecraft.player.sendChatMessage(s1);
            }
            if (this.minecraft.currentScreen instanceof ChatScreen) {
                this.minecraft.openScreen(null);
            }
            return;
        }
        if (key == 14 && this.field_786.length() > 0) {
            this.field_786 = this.field_786.substring(0, this.field_786.length() - 1);
        }
        if (field_788.indexOf((int)character) >= 0 && this.field_786.length() < 100) {
            this.field_786 = this.field_786 + character;
        }
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.fill(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, "> " + this.field_786 + (this.field_787 / 6 % 2 != 0 ? "" : "_"), 4, this.height - 12, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button != 0) {
            return;
        }
        if (this.minecraft.overlay.field_2541 == null) {
            super.mouseClicked(mouseX, mouseY, button);
            return;
        }
        if (this.field_786.length() > 0 && !this.field_786.endsWith(" ")) {
            this.field_786 = this.field_786 + " ";
        }
        this.field_786 = this.field_786 + this.minecraft.overlay.field_2541;
        int byte0 = 100;
        if (this.field_786.length() > byte0) {
            this.field_786 = this.field_786.substring(0, byte0);
        }
    }
}