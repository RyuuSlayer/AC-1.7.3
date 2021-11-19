/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin.client.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.options.Option;
import net.minecraft.client.options.OptionIds;
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
            // empty catch block
        }
        try {
            OptionIds.ids[Option.VIEW_BOBBING.ordinal()] = 2;
        } catch (NoSuchFieldError nosuchfielderror1) {
            // empty catch block
        }
        try {
            OptionIds.ids[Option.ANAGLYPH.ordinal()] = 3;
        } catch (NoSuchFieldError nosuchfielderror2) {
            // empty catch block
        }
        try {
            OptionIds.ids[Option.ADVANCED_OPENGL.ordinal()] = 4;
        } catch (NoSuchFieldError nosuchfielderror3) {
            // empty catch block
        }
        try {
            OptionIds.ids[Option.AMBIENT_OCCLUSION.ordinal()] = 5;
        } catch (NoSuchFieldError nosuchfielderror4) {
            // empty catch block
        }
        try {
            OptionIds.ids[Option.AUTO_FAR_CLIP.ordinal()] = 7;
        } catch (NoSuchFieldError nosuchfielderror6) {
            // empty catch block
        }
        try {
            OptionIds.ids[Option.GRASS_3D.ordinal()] = 8;
        } catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
