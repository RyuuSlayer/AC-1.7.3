package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.Sign;

public class ItemInstrument extends MixinItemType {

    String instrument;

    protected ItemInstrument(int i, String instrumentToUse) {
        super(i);
        this.instrument = instrumentToUse;
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        if (level.getTileId(x, y, z) == Tile.STANDING_SIGN.id) {
            MixinSign sign = (MixinSign) level.getTileEntity(x, y, z);
            sign.playSong(this.instrument);
        }
        return false;
    }

    @Override
    public MixinItemInstance use(MixinItemInstance item, MixinLevel level, MixinPlayer player) {
        GuiMusicSheet.showUI(this.instrument);
        return item;
    }
}
