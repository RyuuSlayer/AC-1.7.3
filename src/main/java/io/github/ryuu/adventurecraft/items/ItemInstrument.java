package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.gui.GuiMusicSheet;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.Sign;

public class ItemInstrument extends ItemType {
    String instrument;

    protected ItemInstrument(int i, String instrumentToUse) {
        super(i);
        this.instrument = instrumentToUse;
    }

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        if (world.a(i, j, k) == Tile.aE.bn) {
            Sign sign = (Sign) world.b(i, j, k);
            sign.playSong(this.instrument);
        }
        return false;
    }

    public ItemInstance a(ItemInstance itemstack, Level world, Player entityplayer) {
        GuiMusicSheet.showUI(this.instrument);
        return itemstack;
    }
}
