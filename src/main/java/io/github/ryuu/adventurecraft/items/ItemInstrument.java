package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.gui.GuiMusicSheet;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemInstrument extends gm {
    String instrument;

    protected ItemInstrument(int i, String instrumentToUse) {
        super(i);
        this.instrument = instrumentToUse;
    }

    public boolean a(iz itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (world.a(i, j, k) == Tile.aE.bn) {
            yk sign = (yk)world.b(i, j, k);
            sign.playSong(this.instrument);
        }
        return false;
    }

    public iz a(iz itemstack, Level world, Player entityplayer) {
        GuiMusicSheet.showUI(this.instrument);
        return itemstack;
    }
}
