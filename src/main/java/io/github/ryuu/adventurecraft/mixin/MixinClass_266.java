package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.class_266;
import net.minecraft.class_267;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.util.*;

@Mixin(class_266.class)
public abstract class MixinClass_266 {

    @Shadow
    private Map<String, ArrayList<class_267>> field_1089;

    @Inject(method = "method_959", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(
            value = "INVOKE_ASSIGN",
            target = "Ljava/lang/String;substring(II)Ljava/lang/String;",
            ordinal = 0,
            shift = At.Shift.BEFORE))
    private void removeExistingSound(String s, File file, CallbackInfoReturnable<class_267> cir, String string) {
        List<class_267> sounds = this.field_1089.get(s);
        if(sounds == null) return;
        for (class_267 sEntry : sounds) {
            if (string.equals(sEntry.field_2126.substring(0, sEntry.field_2126.indexOf(".")))) {
                sounds.remove(sEntry);
                break;
            }
        }
    }
}
