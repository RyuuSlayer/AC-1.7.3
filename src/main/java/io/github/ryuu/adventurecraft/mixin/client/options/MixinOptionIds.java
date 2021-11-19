package io.github.ryuu.adventurecraft.mixin.client.options;

import net.minecraft.client.options.Option;

class MixinOptionIds {
    static final int[] ids = new int[Option.values().length];

    MixinOptionIds() {
    }

    static {
        try {
            MixinOptionIds.ids[Option.INVERT_MOUSE.ordinal()] = 1;
        }
        catch (NoSuchFieldError nosuchfielderror) {
            // empty catch block
        }
        try {
            MixinOptionIds.ids[Option.VIEW_BOBBING.ordinal()] = 2;
        }
        catch (NoSuchFieldError nosuchfielderror1) {
            // empty catch block
        }
        try {
            MixinOptionIds.ids[Option.ANAGLYPH.ordinal()] = 3;
        }
        catch (NoSuchFieldError nosuchfielderror2) {
            // empty catch block
        }
        try {
            MixinOptionIds.ids[Option.ADVANCED_OPENGL.ordinal()] = 4;
        }
        catch (NoSuchFieldError nosuchfielderror3) {
            // empty catch block
        }
        try {
            MixinOptionIds.ids[Option.AMBIENT_OCCLUSION.ordinal()] = 5;
        }
        catch (NoSuchFieldError nosuchfielderror4) {
            // empty catch block
        }
        try {
            MixinOptionIds.ids[Option.AUTO_FAR_CLIP.ordinal()] = 7;
        }
        catch (NoSuchFieldError nosuchfielderror6) {
            // empty catch block
        }
        try {
            MixinOptionIds.ids[Option.GRASS_3D.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}