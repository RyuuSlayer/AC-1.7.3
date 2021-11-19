package io.github.ryuu.adventurecraft.gui;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.input.Keyboard
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;
import net.minecraft.util.CharacterUtils;
import org.lwjgl.input.Keyboard;

public class GuiUrl extends Screen {

    private TileEntityUrl msg;

    private Level world;

    public GuiUrl(Level w, TileEntityUrl u) {
        this.world = w;
        this.msg = u;
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
        if (key == 47 && (Keyboard.isKeyDown((int) 29) || Keyboard.isKeyDown((int) 157) || Keyboard.isKeyDown((int) 219) || Keyboard.isKeyDown((int) 220))) {
            this.msg.url = ClipboardHandler.getClipboard();
            this.world.getChunk(this.msg.x, this.msg.z).method_885();
            return;
        }
        if (key == 14 && this.msg.url.length() > 0) {
            this.msg.url = this.msg.url.substring(0, this.msg.url.length() - 1);
        }
        if (CharacterUtils.SUPPORTED_CHARS.indexOf((int) character) >= 0 && this.msg.url.length() < 30) {
            this.msg.url = this.msg.url + character;
        }
        this.world.getChunk(this.msg.x, this.msg.z).method_885();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawTextWithShadow(this.textManager, String.format((String) "Url: '%s'", (Object[]) new Object[] { this.msg.url }), 4, 4, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    public static void showUI(Level w, TileEntityUrl tileEntityMsg) {
        Minecraft.minecraftInstance.openScreen(new GuiUrl(w, tileEntityMsg));
    }
}
