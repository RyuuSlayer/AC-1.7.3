package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;

public class GuiMusic extends Screen {
    private final Level world;

    private final TileEntityMusic music;

    private GuiSlider2 fadeOut;

    private GuiSlider2 fadeIn;

    private int page;

    public GuiMusic(Level w, TileEntityMusic m) {
        this.world = w;
        this.music = m;
    }

    public static void showUI(Level w, TileEntityMusic m) {
        Minecraft.minecraftInstance.a(new GuiMusic(w, m));
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        int maxEntries = 3 * (this.height - 60) / 20;
        for (int i = 0; i + maxEntries * this.page <= this.world.musicList.length && i < maxEntries; i++) {
            String musicName = "Stop Music";
            if (i != 0 || this.page != 0)
                musicName = this.world.musicList[i - 1 + maxEntries * this.page];
            Button b = new Button(i, 4 + i % 3 * this.width / 3, 60 + i / 3 * 20, (this.width - 16) / 3, 18, musicName);
            this.buttons.add(b);
        }
        this.fadeOut = new GuiSlider2(200, 4, 16, 10, String.format("Fade Out: %d", this.music.fadeOut), this.music.fadeOut / 5000.0F);
        this.fadeIn = new GuiSlider2(201, this.width / 2, 16, 10, String.format("Fade In: %d", this.music.fadeIn), this.music.fadeIn / 5000.0F);
        this.buttons.add(this.fadeOut);
        this.buttons.add(this.fadeIn);
        int numPages = (this.world.musicList.length - 1) / maxEntries + 1;
        for (int j = 0; j < numPages; j++) {
            Button b = new Button(100 + j, 4 + j * 50, 40, 46, 18, String.format("Page %d", j + 1));
            this.buttons.add(b);
        }
    }

    @Override
    protected void buttonClicked(Button guibutton) {
        if (guibutton.id == 0 && this.page == 0) {
            this.music.musicName = "";
        } else if (guibutton.id < 100) {
            int maxEntries = 3 * (this.height - 60) / 20;
            this.music.musicName = this.world.musicList[guibutton.id + maxEntries * this.page - 1];
        } else if (guibutton.id < 200) {
            this.page = guibutton.id - 100;
            this.buttons.clear();
            init();
        }
    }

    @Override
    public void render(int i, int j, float f) {
        fill(0, 0, this.width, this.height, -2147483648);
        if (this.music.musicName.equals("")) {
            drawTextWithShadow(this.textManager, "Music: Stop Music", 4, 4, 14737632);
        } else {
            drawTextWithShadow(this.textManager, String.format("Music: %s", this.music.musicName), 4, 4, 14737632);
        }
        this.music.fadeOut = (int) (this.fadeOut.sliderValue * 5000.0F + 0.5F);
        this.fadeOut.text = String.format("Fade Out: %d", this.music.fadeOut);
        this.music.fadeIn = (int) (this.fadeIn.sliderValue * 5000.0F + 0.5F);
        this.fadeIn.text = String.format("Fade In: %d", this.music.fadeIn);
        super.render(i, j, f);
        this.world.getChunk(this.music.x, this.music.z).method_885();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}