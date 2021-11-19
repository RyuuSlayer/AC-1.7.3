package io.github.ryuu.adventurecraft.mixin.client.options;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

public enum Option {

    MUSIC("MUSIC", 0, "options.music", true, false), SOUND("SOUND", 1, "options.sound", true, false), INVERT_MOUSE("INVERT_MOUSE", 2, "options.invertMouse", false, true), SENSITIVITY("SENSITIVITY", 3, "options.sensitivity", true, false), RENDER_DISTANCE("RENDER_DISTANCE", 4, "options.renderDistance", false, false), VIEW_BOBBING("VIEW_BOBBING", 5, "options.viewBobbing", false, true), ANAGLYPH("ANAGLYPH", 6, "options.anaglyph", false, true), ADVANCED_OPENGL("ADVANCED_OPENGL", 7, "options.advancedOpengl", false, true), FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8, "options.framerateLimit", false, false), DIFFICULTY("DIFFICULTY", 9, "options.difficulty", false, false), GRAPHICS("GRAPHICS", 10, "options.graphics", false, false), AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 11, "options.ao", false, true), GUI_SCALE("GUI_SCALE", 12, "options.guiScale", false, false), AUTO_FAR_CLIP("AUTO_FAR_CLIP", 13, "options.adjustFarClip", false, true), GRASS_3D("GRASS_3D", 14, "options.grass3d", false, true);

    private static final Option[] field_1113;

    static {
        field_1113 = new Option[]{MUSIC, SOUND, INVERT_MOUSE, SENSITIVITY, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE};
    }

    @Shadow()
    private final boolean slider;
    private final boolean field_1111;
    private final String translationKey;

    MixinOption(String s, int i, String s1, boolean flag, boolean flag1) {
        this.translationKey = s1;
        this.slider = flag;
        this.field_1111 = flag1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public static Option getById(int id) {
        for (Option enumoptions : Option.values()) {
            if (enumoptions.getId() != id) continue;
            return enumoptions;
        }
        return null;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isSlider() {
        return this.slider;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public boolean isToggle() {
        return this.field_1111;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public int getId() {
        return this.ordinal();
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public String getTranslationKey() {
        return this.translationKey;
    }
}
