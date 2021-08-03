package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
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

    public void a() {
    }

    public void b() {
        int maxEntries = 3 * (this.d - 60) / 20;
        for (int i = 0; i + maxEntries * this.page <= this.world.musicList.length && i < maxEntries; i++) {
            String musicName = "Stop Music";
            if (i != 0 || this.page != 0)
                musicName = this.world.musicList[i - 1 + maxEntries * this.page];
            ke b = new ke(i, 4 + i % 3 * this.c / 3, 60 + i / 3 * 20, (this.c - 16) / 3, 18, musicName);
            this.e.add(b);
        }
        this.fadeOut = new GuiSlider2(200, 4, 16, 10, String.format("Fade Out: %d", Integer.valueOf(this.music.fadeOut)), this.music.fadeOut / 5000.0F);
        this.fadeIn = new GuiSlider2(201, this.c / 2, 16, 10, String.format("Fade In: %d", Integer.valueOf(this.music.fadeIn)), this.music.fadeIn / 5000.0F);
        this.e.add(this.fadeOut);
        this.e.add(this.fadeIn);
        int numPages = (this.world.musicList.length - 1) / maxEntries + 1;
        for (int j = 0; j < numPages; j++) {
            ke b = new ke(100 + j, 4 + j * 50, 40, 46, 18, String.format("Page %d", new Object[]{Integer.valueOf(j + 1)}));
            this.e.add(b);
        }
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0 && this.page == 0) {
            this.music.musicName = "";
        } else if (guibutton.f < 100) {
            int maxEntries = 3 * (this.d - 60) / 20;
            this.music.musicName = this.world.musicList[guibutton.f + maxEntries * this.page - 1];
        } else if (guibutton.f < 200) {
            this.page = guibutton.f - 100;
            this.e.clear();
            b();
        }
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        if (this.music.musicName.equals("")) {
            b(this.g, "Music: Stop Music", 4, 4, 14737632);
        } else {
            b(this.g, String.format("Music: %s", new Object[]{this.music.musicName}), 4, 4, 14737632);
        }
        this.music.fadeOut = (int) (this.fadeOut.sliderValue * 5000.0F + 0.5F);
        this.fadeOut.e = String.format("Fade Out: %d", new Object[]{Integer.valueOf(this.music.fadeOut)});
        this.music.fadeIn = (int) (this.fadeIn.sliderValue * 5000.0F + 0.5F);
        this.fadeIn.e = String.format("Fade In: %d", new Object[]{Integer.valueOf(this.music.fadeIn)});
        super.a(i, j, f);
        this.world.b(this.music.e, this.music.g).g();
    }

    public static void showUI(Level w, TileEntityMusic m) {
        Minecraft.minecraftInstance.a(new GuiMusic(w, m));
    }

    public boolean c() {
        return false;
    }
}