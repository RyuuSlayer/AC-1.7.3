package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DeathScreen.class)
public abstract class MixinDeathScreen extends Screen {

    @Redirect(method = "render", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/DeathScreen;drawTextWithShadowCentred(Lnet/minecraft/client/render/TextRenderer;Ljava/lang/String;III)V",
            ordinal = 1))
    public void render(DeathScreen instance, TextRenderer textRenderer, String s, int i, int j, int k) {
    }
}
