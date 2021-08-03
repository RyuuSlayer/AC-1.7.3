package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.gui.GuiMusicSheet;

public class ItemInstrument extends gm {
    String instrument;

    protected ItemInstrument(int i, String instrumentToUse) {
        super(i);
        this.instrument = instrumentToUse;
    }

    public boolean a(iz itemstack, gs entityplayer, fd world, int i, int j, int k, int l) {
        if (world.a(i, j, k) == uu.aE.bn) {
            yk sign = (yk)world.b(i, j, k);
            sign.playSong(this.instrument);
        }
        return false;
    }

    public iz a(iz itemstack, fd world, gs entityplayer) {
        GuiMusicSheet.showUI(this.instrument);
        return itemstack;
    }
}
