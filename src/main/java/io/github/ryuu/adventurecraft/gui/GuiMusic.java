package io.github.ryuu.adventurecraft.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;

public class GuiMusic extends MixinScreen {

    private MixinLevel world;

    private TileEntityMusic music;

    private GuiSlider2 fadeOut;

    private GuiSlider2 fadeIn;

    private int page;

    public GuiMusic(MixinLevel w, TileEntityMusic m) {
        this.world = w;
        this.music = m;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        Button b;
        int maxEntries = 3 * ((this.height - 60) / 20);
        for (int i = 0; i + maxEntries * this.page <= this.world.musicList.length && i < maxEntries; ++i) {
            String musicName = "Stop Music";
            if (i != 0 || this.page != 0) {
                musicName = this.world.musicList[i - 1 + maxEntries * this.page];
            }
            b = new Button(i, 4 + i % 3 * this.width / 3, 60 + i / 3 * 20, (this.width - 16) / 3, 18, musicName);
            this.buttons.add((Object) b);
        }
        this.fadeOut = new GuiSlider2(200, 4, 16, 10, String.format((String) "Fade Out: %d", (Object[]) new Object[] { this.music.fadeOut }), (float) this.music.fadeOut / 5000.0f);
        this.fadeIn = new GuiSlider2(201, this.width / 2, 16, 10, String.format((String) "Fade In: %d", (Object[]) new Object[] { this.music.fadeIn }), (float) this.music.fadeIn / 5000.0f);
        this.buttons.add((Object) this.fadeOut);
        this.buttons.add((Object) this.fadeIn);
        int numPages = (this.world.musicList.length - 1) / maxEntries + 1;
        for (int i = 0; i < numPages; ++i) {
            b = new Button(100 + i, 4 + i * 50, 40, 46, 18, String.format((String) "Page %d", (Object[]) new Object[] { i + 1 }));
            this.buttons.add((Object) b);
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0 && this.page == 0) {
            this.music.musicName = "";
        } else if (button.id < 100) {
            int maxEntries = 3 * ((this.height - 60) / 20);
            this.music.musicName = this.world.musicList[button.id + maxEntries * this.page - 1];
        } else if (button.id < 200) {
            this.page = button.id - 100;
            this.buttons.clear();
            this.init();
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        if (this.music.musicName.equals((Object) "")) {
            this.drawTextWithShadow(this.textManager, "Music: Stop Music", 4, 4, 0xE0E0E0);
        } else {
            this.drawTextWithShadow(this.textManager, String.format((String) "Music: %s", (Object[]) new Object[] { this.music.musicName }), 4, 4, 0xE0E0E0);
        }
        this.music.fadeOut = (int) (this.fadeOut.sliderValue * 5000.0f + 0.5f);
        this.fadeOut.text = String.format((String) "Fade Out: %d", (Object[]) new Object[] { this.music.fadeOut });
        this.music.fadeIn = (int) (this.fadeIn.sliderValue * 5000.0f + 0.5f);
        this.fadeIn.text = String.format((String) "Fade In: %d", (Object[]) new Object[] { this.music.fadeIn });
        super.render(mouseX, mouseY, delta);
        this.world.getChunk(this.music.x, this.music.z).method_885();
    }

    public static void showUI(MixinLevel w, TileEntityMusic m) {
        Minecraft.minecraftInstance.openScreen(new GuiMusic(w, m));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
