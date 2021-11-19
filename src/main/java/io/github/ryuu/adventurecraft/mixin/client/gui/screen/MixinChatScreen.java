/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.input.Keyboard
 */
package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.CharacterUtils;
import org.lwjgl.input.Keyboard;
import io.github.ryuu.adventurecraft.mixin.item.MixinScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinChatScreen;

@Mixin(ChatScreen.class)
public class MixinChatScreen extends MixinScreen {

    @Shadow()
    protected String field_786 = "";

    private int field_787 = 0;

    private static final String field_788 = CharacterUtils.SUPPORTED_CHARS;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void init() {
        Keyboard.enableRepeatEvents((boolean) true);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void onClose() {
        Keyboard.enableRepeatEvents((boolean) false);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        ++this.field_787;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void keyPressed(char character, int key) {
        if (Keyboard.isKeyDown((int) 29) || Keyboard.isKeyDown((int) 157) || Keyboard.isKeyDown((int) 219) || Keyboard.isKeyDown((int) 220)) {
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
            if (this.minecraft.currentScreen instanceof MixinChatScreen) {
                this.minecraft.openScreen(null);
            }
            return;
        }
        if (key == 14 && this.field_786.length() > 0) {
            this.field_786 = this.field_786.substring(0, this.field_786.length() - 1);
        }
        if (field_788.indexOf((int) character) >= 0 && this.field_786.length() < 100) {
            this.field_786 = this.field_786 + character;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.drawTextWithShadow(this.textManager, "> " + this.field_786 + (this.field_787 / 6 % 2 != 0 ? "" : "_"), 4, this.height - 12, 0xE0E0E0);
        super.render(mouseX, mouseY, delta);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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
