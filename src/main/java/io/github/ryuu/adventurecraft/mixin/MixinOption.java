package io.github.ryuu.adventurecraft.mixin;

import net.minecraft.client.options.Option;

public enum MixinOption {
    a("MUSIC", 0, "options.music", true, false),
    b("SOUND", 1, "options.sound", true, false),
    c("INVERT_MOUSE", 2, "options.invertMouse", false, true),
    d("SENSITIVITY", 3, "options.sensitivity", true, false),
    e("RENDER_DISTANCE", 4, "options.renderDistance", false, false),
    f("VIEW_BOBBING", 5, "options.viewBobbing", false, true),
    g("ANAGLYPH", 6, "options.anaglyph", false, true),
    h("ADVANCED_OPENGL", 7, "options.advancedOpengl", false, true),
    i("FRAMERATE_LIMIT", 8, "options.framerateLimit", false, false),
    j("DIFFICULTY", 9, "options.difficulty", false, false),
    k("GRAPHICS", 10, "options.graphics", false, false),
    l("AMBIENT_OCCLUSION", 11, "options.ao", false, true),
    m("GUI_SCALE", 12, "options.guiScale", false, false),
    AUTO_FAR_CLIP("AUTO_FAR_CLIP", 13, "options.adjustFarClip", false, true),
    GRASS_3D("GRASS_3D", 14, "options.grass3d", false, true);

    private static final Option[] q;

    static {
        q = new Option[]{
                a, b, c, d, e, f, g, h, i, j,
                k, l, m};
    }

    private final boolean n;
    private final boolean o;
    private final String p;

    MixinOption(String s, int i, String s1, boolean flag, boolean flag1) {
        this.p = s1;
        this.n = flag;
        this.o = flag1;
    }

    public static Option a(int i) {
        Option[] aenumoptions = values();
        int j = aenumoptions.length;
        for (int k = 0; k < j; k++) {
            Option enumoptions = aenumoptions[k];
            if (enumoptions.c() == i)
                return enumoptions;
        }
        return null;
    }

    public boolean a() {
        return this.n;
    }

    public boolean b() {
        return this.o;
    }

    public int c() {
        return ordinal();
    }

    public String d() {
        return this.p;
    }
}
