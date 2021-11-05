package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.mixin.MixinTranslationStorage;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.OptionButton;
import net.minecraft.client.gui.widgets.Slider;
import net.minecraft.client.util.ScreenScaler;

public class VideoSettingsScreen extends Screen {
    private static final Option[] l = new Option[]{Option.k, Option.e, Option.l, Option.i, Option.g, Option.f, Option.m, Option.h, Option.AUTO_FAR_CLIP, Option.GRASS_3D};
    private final Screen i;
    private final GameOptions j;
    protected String a;

    public VideoSettingsScreen(Screen guiscreen, GameOptions gamesettings) {
        this.a = "Video Settings";
        this.i = guiscreen;
        this.j = gamesettings;
    }

    public void b() {
        MixinTranslationStorage stringtranslate = MixinTranslationStorage.a();
        this.a = stringtranslate.a("options.videoTitle");
        int i = 0;
        Option[] aenumoptions = l;
        int j = aenumoptions.length;
        for (int k = 0; k < j; k++) {
            Option enumoptions = aenumoptions[k];
            if (!enumoptions.a()) {
                this.e.add(new OptionButton(enumoptions.c(), this.c / 2 - 155 + i % 2 * 160, this.d / 6 + 24 * (i >> 1), enumoptions, this.j.c(enumoptions)));
            } else {
                this.e.add(new Slider(enumoptions.c(), this.c / 2 - 155 + i % 2 * 160, this.d / 6 + 24 * (i >> 1), enumoptions, this.j.c(enumoptions), this.j.a(enumoptions)));
            }
            i++;
        }
        this.e.add(new Button(200, this.c / 2 - 100, this.d / 6 + 168, stringtranslate.a("gui.done")));
    }

    protected void a(Button guibutton) {
        if (!guibutton.g)
            return;
        if (guibutton.f < 100 && guibutton instanceof OptionButton) {
            this.j.a(((OptionButton) guibutton).a(), 1);
            guibutton.e = this.j.c(Option.a(guibutton.f));
        }
        if (guibutton.f == 200) {
            this.b.z.b();
            this.b.a(this.i);
        }
        ScreenScaler scaledresolution = new ScreenScaler(this.b.z, this.b.d, this.b.e);
        int i = scaledresolution.a();
        int j = scaledresolution.b();
        a(this.b, i, j);
    }

    public void a(int i, int j, float f) {
        i();
        a(this.g, this.a, this.c / 2, 20, 16777215);
        super.a(i, j, f);
    }
}
