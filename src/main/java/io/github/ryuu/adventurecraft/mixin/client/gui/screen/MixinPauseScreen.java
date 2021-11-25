package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.PauseScreen;
import net.minecraft.client.gui.widgets.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class) // TODO scriptUI, cameraActive and stopMusic is missing
public class MixinPauseScreen extends Screen {
    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setLevel(Lnet/minecraft/level/Level;)V"))
    private void buttonClicked(Button button, CallbackInfo ci) {
        this.minecraft.overlay.scriptUI.clear(); // TODO Needs cast
        this.minecraft.cameraActive = false; // TODO Needs cast
    }

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;openScreen(Lnet/minecraft/client/gui/Screen;)V", ordinal = 1))
    private void buttonClicked(Button button, CallbackInfo ci) {
        this.minecraft.soundHelper.stopMusic(); // TODO Needs cast
    }
}
