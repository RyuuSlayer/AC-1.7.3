package io.github.ryuu.adventurecraft.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.blocks.IBlockColor;

public class ItemBrush extends ItemType {

    protected ItemBrush(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        Tile b = Tile.BY_ID[level.getTileId(x, y, z)];
        if (b != null && b instanceof IBlockColor) {
            ((IBlockColor) ((Object) b)).incrementColor(level, x, y, z);
            level.method_243(x, y, z);
        } else {
            Minecraft.minecraftInstance.overlay.addChatMessage("Doesn't implement Color :(");
        }
        return false;
    }
}
