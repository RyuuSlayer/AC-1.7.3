package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import io.github.ryuu.adventurecraft.gui.GuiMapDownload;
import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Random;

@Mixin(TitleScreen.class) // TODO minecraftInstance missing
public class MixinTitleScreen extends Screen {


    @Shadow
    private float ticksOpened;

    @Shadow
    private String splashMessage;

    @Shadow
    @Final
    private static Random RANDOM;

    @Shadow
    private Button multiplayerButton;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void TitleScreen(CallbackInfo ci) {
        ScriptModel.clearAll();
        AccessMinecraft.getInstance().soundHelper.stopMusic();
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/language/TranslationStorage;getInstance()Lnet/minecraft/client/resource/language/TranslationStorage;", shift = At.Shift.BEFORE))
    private void init(CallbackInfo ci) {
        this.splashMessage = "A Minecraft Total Conversion!";
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/language/TranslationStorage;getInstance()Lnet/minecraft/client/resource/language/TranslationStorage;"), cancellable = true)
    private void init1(CallbackInfo ci) {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        int i = this.height / 4 + 48;
        this.buttons.add(new Button(6, this.width / 2 - 100, i, "New Save"));
        this.buttons.add(new Button(1, this.width / 2 - 100, i + 22, "Load Save"));
        this.buttons.add(new Button(7, this.width / 2 - 100, i + 44, "Craft a Map"));
        this.buttons.add(new Button(5, this.width / 2 - 100, i + 66, "Download a Map"));
        if (this.minecraft.isApplet) {
            this.buttons.add(new Button(0, this.width / 2 - 100, i + 88, stringtranslate.translate("menu.options")));
        } else {
            this.buttons.add(new Button(0, this.width / 2 - 100, i + 88 + 11, 98, 20, stringtranslate.translate("menu.options")));
            this.buttons.add(new Button(4, this.width / 2 + 2, i + 88 + 11, 98, 20, stringtranslate.translate("menu.quit")));
        }
        if (this.minecraft.session == null) {
            this.multiplayerButton.active = false;
        }
        ci.cancel();
    }

    @Inject(method = "buttonClicked", at = @At("TAIL"))
    private void buttonClicked(Button button, CallbackInfo ci) {
        if (button.id == 5) {
            this.minecraft.openScreen(new GuiMapDownload(this));
        } else if (button.id == 6) {
            this.minecraft.openScreen(new GuiMapSelect(this, ""));
        } else if (button.id == 7) {
            this.minecraft.openScreen(new GuiMapSelect(this, null));
        }
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 274))
    private int render(int s) {
        return 320;
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;blit(IIIIII)V"))
    private void render1(Args args) {
        args.set(4, 256);
        args.set(5, 44);
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;blit(IIIIII)V", ordinal = 1))
    private void render2(Args args) {
        int i = args.get(0);
        args.set(0, i - 155 + 256);
        args.set(3, 128);
        args.set(4, 64);
        args.set(5, 31);
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;getTextureId(Ljava/lang/String;)I"), index = 0)
    private String render3(String s) {
        return "/assets/adventurecraft/acLogo.png";
    }
}

