package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.gui.GuiMusicSheet;

public class ItemInstrument extends ItemType {

    String instrument;

    protected ItemInstrument(int i, String instrumentToUse) {
        super(i);
        this.instrument = instrumentToUse;
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        if (level.getTileId(x, y, z) == Tile.STANDING_SIGN.id) {
            Sign sign = (Sign) level.getTileEntity(x, y, z);
            sign.playSong(this.instrument);
        }
        return false;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, Player player) {
        GuiMusicSheet.showUI(this.instrument);
        return item;
    }
}
