package io.github.ryuu.adventurecraft.mixin.client.options;

import com.chocohead.mm.api.ClassTinkerers;
import io.github.ryuu.adventurecraft.extensions.client.options.ExGameOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;

@Mixin(GameOptions.class)
public abstract class MixinGameOptions implements ExGameOptions {

    @Mutable
    @Final
    @Shadow
    private static String[] renderDistanceTranslationKeys;

    static {
        renderDistanceTranslationKeys = Arrays.copyOf(renderDistanceTranslationKeys, renderDistanceTranslationKeys.length + 1);
        renderDistanceTranslationKeys[renderDistanceTranslationKeys.length - 1] = "options.renderDistance.veryFar";
    }

    @Shadow
    public int viewDistance;

    @Shadow
    public boolean bobView;

    @Shadow
    public boolean anaglyph3d;

    @Shadow
    public boolean advancedOpengl;

    @Shadow
    public int fpsLimit;

    @Shadow
    public boolean fancyGraphics;

    @Shadow
    public boolean ao;

    @Shadow
    public String skin;

    @Shadow
    public KeyBinding forwardKey;

    @Shadow
    public KeyBinding leftKey;

    @Shadow
    public KeyBinding backKey;

    @Shadow
    public KeyBinding rightKey;

    @Shadow
    public KeyBinding jumpKey;

    @Shadow
    public KeyBinding inventoryKey;

    @Shadow
    public KeyBinding dropKey;

    @Shadow
    public KeyBinding chatKey;

    @Shadow
    public KeyBinding fogKey;

    @Shadow
    public KeyBinding sneakKey;

    @Shadow
    public boolean hideHud;

    @Shadow
    public String lastServer;

    @Shadow
    public boolean field_1445;

    @Shadow
    public boolean cinematicMode;

    @Shadow
    public boolean field_1447;

    @Shadow
    public float field_1448;

    @Shadow
    public float field_1449;

    @Shadow
    public int guiScale;

    @Shadow
    protected Minecraft minecraft;

    public boolean autoFarClip;
    public boolean grass3d;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;load()V"))
    private void GameOptions(Minecraft file, File par2, CallbackInfo ci) {
        this.viewDistance = 1;
        this.bobView = true;
        this.anaglyph3d = false;
        this.advancedOpengl = false;
        this.fpsLimit = 1;
        this.fancyGraphics = true;
        this.ao = true;
        this.skin = "Default";
        this.forwardKey = new KeyBinding("key.forward", 17);
        this.leftKey = new KeyBinding("key.left", 30);
        this.backKey = new KeyBinding("key.back", 31);
        this.rightKey = new KeyBinding("key.right", 32);
        this.jumpKey = new KeyBinding("key.jump", 57);
        this.inventoryKey = new KeyBinding("key.inventory", 18);
        this.dropKey = new KeyBinding("key.drop", 16);
        this.chatKey = new KeyBinding("key.chat", 20);
        this.fogKey = new KeyBinding("key.fog", 33);
        this.sneakKey = new KeyBinding("key.sneak", 42);
        this.hideHud = false;
        this.field_1445 = false;
        this.cinematicMode = false;
        this.field_1447 = false;
        this.field_1448 = 1.0f;
        this.field_1449 = 1.0f;
        this.guiScale = 0;
        this.autoFarClip = true;
        this.grass3d = true;
    }

    @Inject(method = "<init>()V", at = @At("TAIL"))
    private void GameOptions(CallbackInfo ci) {
        this.viewDistance = 0;
        this.bobView = true;
        this.anaglyph3d = false;
        this.advancedOpengl = false;
        this.fpsLimit = 1;
        this.fancyGraphics = true;
        this.ao = true;
        this.skin = "Default";
        this.forwardKey = new KeyBinding("key.forward", 17);
        this.leftKey = new KeyBinding("key.left", 30);
        this.backKey = new KeyBinding("key.back", 31);
        this.rightKey = new KeyBinding("key.right", 32);
        this.jumpKey = new KeyBinding("key.jump", 57);
        this.inventoryKey = new KeyBinding("key.inventory", 18);
        this.dropKey = new KeyBinding("key.drop", 16);
        this.chatKey = new KeyBinding("key.chat", 20);
        this.fogKey = new KeyBinding("key.fog", 33);
        this.sneakKey = new KeyBinding("key.sneak", 42);
    }

    /* PROBABLY COMPLETELY UNNECESSARY
    @Redirect(method = "<init>()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;hideHud:Z"))
    private void removeFieldInitialization(GameOptions instance, boolean value) {
    }

    @Redirect(method = "<init>()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;cinematicMode:Z"))
    private void removeFieldInitialization1(GameOptions instance, boolean value) {
    }

    @Redirect(method = "<init>()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;field_1447:Z"))
    private void removeFieldInitialization2(GameOptions instance, boolean value) {
    }

    @Redirect(method = "<init>()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;field_1448:F"))
    private void removeFieldInitialization3(GameOptions instance, float value) {
    }

    @Redirect(method = "<init>()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;field_1449:F"))
    private void removeFieldInitialization4(GameOptions instance, float value) {
    }

    @Redirect(method = "<init>()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;guiScale:I"))
    private void removeFieldInitialization5(GameOptions instance, int value) {
    }*/

    @Redirect(method = "changeOption", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;viewDistance:I", ordinal = 1))
    private void changeMaxViewDistance(GameOptions instance, int value) {
        this.viewDistance = (this.viewDistance - 1) % 5;
        if (this.viewDistance < 0) {
            this.viewDistance = 4;
        }
    }

    @Inject(method = "changeOption", at = @At("HEAD"))
    private void initNewSettings(Option option, int i, CallbackInfo ci) {
        if (option == ClassTinkerers.getEnum(Option.class, "AUTO_FAR_CLIP")) {
            this.autoFarClip = !this.autoFarClip;
        } else if (option == ClassTinkerers.getEnum(Option.class, "GRASS_3D")) {
            this.grass3d = !this.grass3d;
            this.minecraft.worldRenderer.method_1537();
        }
    }


    @Inject(method = "getBooleanValue", at = @At("HEAD"), cancellable = true)
    private void getBooleanValue(Option option, CallbackInfoReturnable<Boolean> cir) {
        if (option == ClassTinkerers.getEnum(Option.class, "AUTO_FAR_CLIP")) {
            cir.setReturnValue(this.autoFarClip);
        } else if (option == ClassTinkerers.getEnum(Option.class, "GRASS_3D")) {
            cir.setReturnValue(this.grass3d);
        }
    }

    @Inject(method = "getTranslatedValue", at = @At("HEAD"), cancellable = true)
    private void getTranslatedValue(Option enumoptions, CallbackInfoReturnable<String> cir) {
        if (enumoptions == ClassTinkerers.getEnum(Option.class, "AUTO_FAR_CLIP")) {
            cir.setReturnValue("Auto Far Clip: " + (this.autoFarClip ? "ON" : "OFF"));
        } else if (enumoptions == ClassTinkerers.getEnum(Option.class, "GRASS_3D")) {
            cir.setReturnValue("Grass 3D: " + (this.grass3d ? "ON" : "OFF"));
        }
    }

    @ModifyVariable(method = "load", at = @At(value = "STORE"))
    private String[] load(String[] var3) {
        if (var3[0].equals("autoFarClip")) {
            this.autoFarClip = var3[1].equals("true");
        }
        if (var3[0].equals("grass3d")) {
            this.grass3d = var3[1].equals("true");
        }
        return var3;
    }

    @ModifyVariable(method = "saveOptions", at = @At("STORE"))
    private PrintWriter saveOptions(PrintWriter printWriter) {
        printWriter.println("ao:" + this.ao);
        printWriter.println("skin:" + this.skin);
        printWriter.println("lastServer:" + this.lastServer);
        printWriter.println("autoFarClip:" + this.autoFarClip);
        printWriter.println("grass3d:" + this.grass3d);
        return printWriter;
    }

    @Override
    public boolean isAutoFarClip() {
        return this.autoFarClip;
    }

    @Override
    public boolean isGrass3d() {
        return this.grass3d;
    }
}
