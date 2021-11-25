package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import io.github.ryuu.adventurecraft.util.ClipboardHandler;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class MixinChatScreen extends Screen {


    @Shadow
    protected String field_786;


    @Inject(method = "keyPressed", at = @At("HEAD"))
    protected void keyPressed(char character, int key, CallbackInfo ci) {
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
    }
}
