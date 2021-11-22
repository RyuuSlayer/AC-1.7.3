package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PauseScreen.class)
public class MixinPauseScreen extends Screen {

    @Shadow()
    private int field_2204 = 0;

    private int field_2205 = 0;

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void init() {
        this.field_2204 = 0;
        this.buttons.clear();
        int byte0 = -16;
        this.buttons.add((Object) new Button(1, this.width / 2 - 100, this.height / 4 + 120 + byte0, "Save and quit to title"));
        if (this.minecraft.isConnectedToServer()) {
            ((Button) this.buttons.get((int) 0)).text = "Disconnect";
        }
        this.buttons.add((Object) new Button(4, this.width / 2 - 100, this.height / 4 + 24 + byte0, "Back to game"));
        this.buttons.add((Object) new Button(0, this.width / 2 - 100, this.height / 4 + 96 + byte0, "Options..."));
        this.buttons.add((Object) new Button(5, this.width / 2 - 100, this.height / 4 + 48 + byte0, 98, 20, I18n.translate("gui.achievements")));
        this.buttons.add((Object) new Button(6, this.width / 2 + 2, this.height / 4 + 48 + byte0, 98, 20, I18n.translate("gui.stats")));
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.minecraft.openScreen(new OptionsScreen(this, this.minecraft.options));
        }
        if (button.id == 1) {
            this.minecraft.statManager.incrementStat(Stats.leaveGame, 1);
            if (this.minecraft.isConnectedToServer()) {
                this.minecraft.level.disconnect();
            }
            this.minecraft.setLevel(null);
            this.minecraft.overlay.scriptUI.clear();
            this.minecraft.cameraActive = false;
            this.minecraft.openScreen(new TitleScreen());
            this.minecraft.soundHelper.stopMusic();
        }
        if (button.id == 4) {
            this.minecraft.openScreen(null);
            this.minecraft.lockCursor();
        }
        if (button.id == 5) {
            this.minecraft.openScreen(new AchievementsScreen(this.minecraft.statManager));
        }
        if (button.id == 6) {
            this.minecraft.openScreen(new StatsScreen(this, this.minecraft.statManager));
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(int mouseX, int mouseY, float delta) {
        boolean flag;
        this.renderBackground();
        boolean bl = flag = !this.minecraft.level.method_274(this.field_2204++);
        if (flag || this.field_2205 < 20) {
            float f1 = ((float) (this.field_2205 % 10) + delta) / 10.0f;
            f1 = MathsHelper.sin(f1 * 3.141593f * 2.0f) * 0.2f + 0.8f;
            int k = (int) (255.0f * f1);
            this.drawTextWithShadow(this.textManager, "Saving level..", 8, this.height - 16, k << 16 | k << 8 | k);
        }
        this.drawTextWithShadowCentred(this.textManager, "Game menu", this.width / 2, 40, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }
}
