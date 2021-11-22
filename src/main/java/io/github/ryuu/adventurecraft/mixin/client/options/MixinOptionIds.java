package io.github.ryuu.adventurecraft.mixin.client.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OptionIds.class)
class MixinOptionIds {

    @Shadow()
    static final int[] ids = new int[Option.values().length];

    MixinOptionIds() {
    }

    static {
        try {
            OptionIds.ids[Option.INVERT_MOUSE.ordinal()] = 1;
        } catch (NoSuchFieldError nosuchfielderror) {
        }
        try {
            OptionIds.ids[Option.VIEW_BOBBING.ordinal()] = 2;
        } catch (NoSuchFieldError nosuchfielderror1) {
        }
        try {
            OptionIds.ids[Option.ANAGLYPH.ordinal()] = 3;
        } catch (NoSuchFieldError nosuchfielderror2) {
        }
        try {
            OptionIds.ids[Option.ADVANCED_OPENGL.ordinal()] = 4;
        } catch (NoSuchFieldError nosuchfielderror3) {
        }
        try {
            OptionIds.ids[Option.AMBIENT_OCCLUSION.ordinal()] = 5;
        } catch (NoSuchFieldError nosuchfielderror4) {
        }
        try {
            OptionIds.ids[Option.AUTO_FAR_CLIP.ordinal()] = 7;
        } catch (NoSuchFieldError nosuchfielderror6) {
        }
        try {
            OptionIds.ids[Option.GRASS_3D.ordinal()] = 8;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
    }
}
