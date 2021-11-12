package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.client.options.Option;

class MixinOptionIds {
    static final int[] a = new int[(Option.values()).length];

    static {
        try {
            a[Option.c.ordinal()] = 1;
        } catch (NoSuchFieldError nosuchfielderror) {
        }
        try {
            a[Option.f.ordinal()] = 2;
        } catch (NoSuchFieldError nosuchfielderror1) {
        }
        try {
            a[Option.g.ordinal()] = 3;
        } catch (NoSuchFieldError nosuchfielderror2) {
        }
        try {
            a[Option.h.ordinal()] = 4;
        } catch (NoSuchFieldError nosuchfielderror3) {
        }
        try {
            a[Option.l.ordinal()] = 5;
        } catch (NoSuchFieldError nosuchfielderror4) {
        }
        try {
            a[Option.AUTO_FAR_CLIP.ordinal()] = 7;
        } catch (NoSuchFieldError nosuchfielderror6) {
        }
        try {
            a[Option.GRASS_3D.ordinal()] = 8;
        } catch (NoSuchFieldError nosuchfielderror7) {
        }
    }
}
