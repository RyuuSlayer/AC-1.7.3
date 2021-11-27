package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.VideoSettingsScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(VideoSettingsScreen.class)
public class MixinVideoSettingsScreen extends Screen {
    @Shadow private static Option[] OPTIONS;

    static {
        OPTIONS = Arrays.copyOf(OPTIONS, OPTIONS.length + 2);
        OPTIONS[OPTIONS.length - 2] = ClassTinkerers.getEnum(Option.class, "AUTO_FAR_CLIP");
        OPTIONS[OPTIONS.length - 1] = ClassTinkerers.getEnum(Option.class, "GRASS_3D");
    }
}