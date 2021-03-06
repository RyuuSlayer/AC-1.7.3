package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMusic;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;

import java.util.List;

public class GuiMusic extends Screen {

    private final Level level;

    private final TileEntityMusic music;

    private GuiSlider2 fadeOut;

    private GuiSlider2 fadeIn;

    private int page;

    public GuiMusic(Level w, TileEntityMusic m) {
        this.level = w;
        this.music = m;
    }

    public static void showUI(Level w, TileEntityMusic m) {
        AccessMinecraft.getInstance().openScreen(new GuiMusic(w, m));
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        List<Button> buttons = (List<Button>) this.buttons;
        String[] musicList = ((ExLevel) this.level).getMusicList();
        int maxEntries = 3 * ((this.height - 60) / 20);
        for (int i = 0; i + maxEntries * this.page <= musicList.length && i < maxEntries; ++i) {
            String musicName = "Stop Music";
            if (i != 0 || this.page != 0) {
                musicName = musicList[i - 1 + maxEntries * this.page];
            }
            Button b = new Button(i, 4 + i % 3 * this.width / 3, 60 + i / 3 * 20, (this.width - 16) / 3, 18, musicName);
            buttons.add(b);
        }
        this.fadeOut = new GuiSlider2(200, 4, 16, 10, String.format("Fade Out: %d", this.music.fadeOut), (float) this.music.fadeOut / 5000.0f);
        this.fadeIn = new GuiSlider2(201, this.width / 2, 16, 10, String.format("Fade In: %d", this.music.fadeIn), (float) this.music.fadeIn / 5000.0f);
        buttons.add(this.fadeOut);
        buttons.add(this.fadeIn);
        int numPages = (musicList.length - 1) / maxEntries + 1;
        for (int i = 0; i < numPages; ++i) {
            Button b = new Button(100 + i, 4 + i * 50, 40, 46, 18, String.format("Page %d", i + 1));
            buttons.add(b);
        }
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 0 && this.page == 0) {
            this.music.musicName = "";
        } else if (button.id < 100) {
            int maxEntries = 3 * ((this.height - 60) / 20);
            String[] musicList = ((ExLevel) this.level).getMusicList();
            this.music.musicName = musicList[button.id + maxEntries * this.page - 1];
        } else if (button.id < 200) {
            this.page = button.id - 100;
            this.buttons.clear();
            this.init();
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.fill(0, 0, this.width, this.height, Integer.MIN_VALUE);
        if (this.music.musicName.equals("")) {
            this.drawTextWithShadow(this.textManager, "Music: Stop Music", 4, 4, 0xE0E0E0);
        } else {
            this.drawTextWithShadow(this.textManager, String.format("Music: %s", this.music.musicName), 4, 4, 0xE0E0E0);
        }
        this.music.fadeOut = (int) (this.fadeOut.sliderValue * 5000.0f + 0.5f);
        this.fadeOut.text = String.format("Fade Out: %d", this.music.fadeOut);
        this.music.fadeIn = (int) (this.fadeIn.sliderValue * 5000.0f + 0.5f);
        this.fadeIn.text = String.format("Fade In: %d", this.music.fadeIn);
        super.render(mouseX, mouseY, delta);
        this.level.getChunk(this.music.x, this.music.z).markDirty();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
