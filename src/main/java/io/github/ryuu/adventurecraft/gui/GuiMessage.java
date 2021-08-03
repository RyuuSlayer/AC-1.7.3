package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMessage;
import net.minecraft.client.Minecraft;

public class GuiMessage extends da {
    private final TileEntityMessage msg;

    private TileEntityMessage soundFile;

    private final fd world;

    private int page;

    public GuiMessage(fd w, TileEntityMessage tileEntityMsg) {
        this.world = w;
        this.msg = tileEntityMsg;
    }

    public void b() {
        int maxEntries = 3 * (this.d - 60) / 20;
        for (int i = 0; i + maxEntries * this.page - 1 < this.world.soundList.length && i < maxEntries; i++) {
            String soundName = "None";
            if (i != 0 || this.page != 0)
                soundName = this.world.soundList[i + maxEntries * this.page - 1];
            ke b = new ke(i, 4 + i % 3 * this.c / 3, 60 + i / 3 * 20, (this.c - 16) / 3, 18, soundName);
            this.e.add(b);
        }
        int numPages = this.world.soundList.length / maxEntries + 1;
        for (int j = 0; j < numPages; j++) {
            ke b = new ke(100 + j, 4 + j * 50, 40, 46, 18, String.format("Page %d", new Object[]{Integer.valueOf(j + 1)}));
            this.e.add(b);
        }
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0 && this.page == 0) {
            this.msg.sound = "";
        } else if (guibutton.f < 100) {
            int maxEntries = 3 * (this.d - 60) / 20;
            this.msg.sound = this.world.soundList[guibutton.f - 1 + maxEntries * this.page];
        } else {
            this.page = guibutton.f - 100;
            this.e.clear();
            b();
        }
        this.world.b(this.msg.e, this.msg.g).g();
    }

    protected void a(char c, int i) {
        super.a(c, i);
        if (i == 14 && this.msg.message.length() > 0)
            this.msg.message = this.msg.message.substring(0, this.msg.message.length() - 1);
        if (fp.a.indexOf(c) >= 0 && this.msg.message.length() < 30)
            this.msg.message += c;
    }

    public void a(int i, int j, float f) {
        i();
        b(this.g, String.format("Message: '%s'", new Object[]{this.msg.message}), 4, 4, 14737632);
        if (this.msg.sound != "") {
            b(this.g, String.format("Sound: %s", new Object[]{this.msg.sound}), 4, 24, 14737632);
        } else {
            b(this.g, String.format("Sound: None", new Object[0]), 4, 24, 14737632);
        }
        super.a(i, j, f);
    }

    public static void showUI(fd w, TileEntityMessage tileEntityMsg) {
        Minecraft.minecraftInstance.a(new AC_GuiMessage(w, tileEntityMsg));
    }
}