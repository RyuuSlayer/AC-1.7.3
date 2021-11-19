package io.github.ryuu.adventurecraft.mixin.client.options;

import net.minecraft.client.options.Option;

public enum MixinOption {
    MUSIC("MUSIC", 0, "options.music", true, false),
    SOUND("SOUND", 1, "options.sound", true, false),
    INVERT_MOUSE("INVERT_MOUSE", 2, "options.invertMouse", false, true),
    SENSITIVITY("SENSITIVITY", 3, "options.sensitivity", true, false),
    RENDER_DISTANCE("RENDER_DISTANCE", 4, "options.renderDistance", false, false),
    VIEW_BOBBING("VIEW_BOBBING", 5, "options.viewBobbing", false, true),
    ANAGLYPH("ANAGLYPH", 6, "options.anaglyph", false, true),
    ADVANCED_OPENGL("ADVANCED_OPENGL", 7, "options.advancedOpengl", false, true),
    FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "options.framerateLimit", false, false),
    DIFFICULTY("DIFFICULTY", 9, "options.difficulty", false, false),
    GRAPHICS("GRAPHICS", 10, "options.graphics", false, false),
    AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 11, "options.ao", false, true),
    GUI_SCALE("GUI_SCALE", 12, "options.guiScale", false, false),
    AUTO_FAR_CLIP("AUTO_FAR_CLIP", 13, "options.adjustFarClip", false, true),
    GRASS_3D("GRASS_3D", 14, "options.grass3d", false, true);

    private final boolean slider;
    private final boolean field_1111;
    private final String translationKey;
    private static final Option[] field_1113;

    public static Option getById(int id) {
        for (Option enumoptions : Option.values()) {
            if (enumoptions.getId() != id) continue;
            return enumoptions;
        }
        return null;
    }

    private MixinOption(String s, int i, String s1, boolean flag, boolean flag1) {
        this.translationKey = s1;
        this.slider = flag;
        this.field_1111 = flag1;
    }

    public boolean isSlider() {
        return this.slider;
    }

    public boolean isToggle() {
        return this.field_1111;
    }

    public int getId() {
        return this.ordinal();
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    static {
        field_1113 = new Option[]{MUSIC, SOUND, INVERT_MOUSE, SENSITIVITY, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE};
    }
}