package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import io.github.ryuu.adventurecraft.extensions.client.ExMinecraft;
import io.github.ryuu.adventurecraft.extensions.client.gui.ExOverlay;
import io.github.ryuu.adventurecraft.extensions.client.sound.ExSoundHelper;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.PauseScreen;
import net.minecraft.client.gui.widgets.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public abstract class MixinPauseScreen extends Screen {

    @Inject(method = "buttonClicked", at = @At(
            value = "HEAD",
            target = "Lnet/minecraft/client/Minecraft;setLevel(Lnet/minecraft/level/Level;)V"))
    private void onOptionsScreen(Button button, CallbackInfo ci) {
        ((ExOverlay) this.minecraft.overlay).getScriptUI().clear();
        ((ExMinecraft) this.minecraft).setCameraActive(false);
    }

    @Inject(method = "buttonClicked", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;openScreen(Lnet/minecraft/client/gui/Screen;)V", ordinal = 1))
    private void onTitleScreen(Button button, CallbackInfo ci) {
        ((ExSoundHelper) this.minecraft.soundHelper).stopMusic();
    }
}
