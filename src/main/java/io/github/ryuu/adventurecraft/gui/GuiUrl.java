package io.github.ryuu.adventurecraft.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.util.CharacterUtils;
import org.lwjgl.input.Keyboard;

public class GuiUrl extends MixinScreen {

    private final TileEntityUrl msg;

    private final MixinLevel world;

    public GuiUrl(MixinLevel w, TileEntityUrl u) {
        this.world = w;
        this.msg = u;
    }

    public static void showUI(MixinLevel w, TileEntityUrl tileEntityMsg) {
        Minecraft.minecraftInstance.openScreen(new GuiUrl(w, tileEntityMsg));
    }

    @Override
    public void init() {
    }

    @Override
    protected void buttonClicked(Button button) {
    }

    @Override
    protected void keyPressed(char character, int key) {
        super.keyPressed(character, key);
        if (key == 47 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157) || Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220))) {
            this.msg.url = ClipboardHandler.getClipboard();
            this.world.getChunk(this.msg.x, this.msg.z).method_885();
            return;
        }
        if (key == 14 && this.msg.url.length() > 0) {
            this.msg.url = this.msg.url.substring(0, this.msg.url.length() - 1);
        }
        if (CharacterUtils.SUPPORTED_CHARS.indexOf(character) >= 0 && this.msg.url.length() < 30) {
            this.msg.url = this.msg.url + character;
        }
        this.world.getChunk(this.msg.x, this.msg.z).method_885();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawTextWithShadow(this.textManager, String.format("Url: '%s'", new Object[]{this.msg.url}), 4, 4, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }
}
