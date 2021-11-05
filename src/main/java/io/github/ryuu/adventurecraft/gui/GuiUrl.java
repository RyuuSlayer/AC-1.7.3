package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityUrl;
import io.github.ryuu.adventurecraft.util.ClipboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;
import net.minecraft.util.CharacterUtils;
import org.lwjgl.input.Keyboard;

public class GuiUrl extends Screen {
    private final TileEntityUrl msg;

    private final Level world;

    public GuiUrl(Level w, TileEntityUrl u) {
        this.world = w;
        this.msg = u;
    }

    public static void showUI(Level w, TileEntityUrl tileEntityMsg) {
        Minecraft.minecraftInstance.a(new GuiUrl(w, tileEntityMsg));
    }

    @Override
    public void init() {
    }

    @Override
    protected void buttonClicked(Button guibutton) {
    }

    @Override
    protected void keyPressed(char c, int i) {
        super.keyPressed(c, i);
        if (i == 47 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157) || Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220))) {
            this.msg.url = ClipboardHandler.getClipboard();
            this.world.getChunk(this.msg.x, this.msg.z).method_885();
            return;
        }
        if (i == 14 && this.msg.url.length() > 0)
            this.msg.url = this.msg.url.substring(0, this.msg.url.length() - 1);
        if (CharacterUtils.field_298.indexOf(c) >= 0 && this.msg.url.length() < 30)
            this.msg.url += c;
        this.world.getChunk(this.msg.x, this.msg.z).method_885();
    }

    @Override
    public void render(int i, int j, float f) {
        renderBackground();
        drawTextWithShadow(this.textManager, String.format("Url: '%s'", this.msg.url), 4, 4, 14737632);
        super.render(i, j, f);
    }
}
