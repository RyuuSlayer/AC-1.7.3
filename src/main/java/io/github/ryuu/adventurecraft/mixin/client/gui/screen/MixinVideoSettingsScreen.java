package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VideoSettingsScreen.class)
public class MixinVideoSettingsScreen extends Screen {

    @Shadow()
    private Screen parent;

    protected String title = "Video Settings";

    private GameOptions options;

    private static Option[] OPTIONS = new Option[] { Option.GRAPHICS, Option.RENDER_DISTANCE, Option.AMBIENT_OCCLUSION, Option.FRAMERATE_LIMIT, Option.ANAGLYPH, Option.VIEW_BOBBING, Option.GUI_SCALE, Option.ADVANCED_OPENGL, Option.AUTO_FAR_CLIP, Option.GRASS_3D };

    public MixinVideoSettingsScreen(Screen parent, GameOptions gamesettings) {
        this.parent = parent;
        this.options = gamesettings;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void init() {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        this.title = stringtranslate.translate("options.videoTitle");
        int i = 0;
        for (Option enumoptions : OPTIONS) {
            if (!enumoptions.isSlider()) {
                this.buttons.add((Object) new OptionButton(enumoptions.getId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), enumoptions, this.options.getTranslatedValue(enumoptions)));
            } else {
                this.buttons.add((Object) new Slider(enumoptions.getId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), enumoptions, this.options.getTranslatedValue(enumoptions), this.options.getFloatValue(enumoptions)));
            }
            ++i;
        }
        this.buttons.add((Object) new Button(200, this.width / 2 - 100, this.height / 6 + 168, stringtranslate.translate("gui.done")));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void buttonClicked(Button button) {
        if (!button.active) {
            return;
        }
        if (button.id < 100 && button instanceof OptionButton) {
            this.options.changeOption(((OptionButton) button).getOption(), 1);
            button.text = this.options.getTranslatedValue(Option.getById(button.id));
        }
        if (button.id == 200) {
            this.minecraft.options.saveOptions();
            this.minecraft.openScreen(this.parent);
        }
        ScreenScaler scaledresolution = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        this.init(this.minecraft, i, j);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawTextWithShadowCentred(this.textManager, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }
}
