package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemBrush extends ItemType {

    protected ItemBrush(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(ItemInstance item, Player player, Level level, int x, int y, int z, int facing) {
        Tile b = Tile.BY_ID[level.getTileId(x, y, z)];
        if (b != null && b instanceof IBlockColor) {
            ((IBlockColor) b).incrementColor(level, x, y, z);
            level.method_243(x, y, z);
        } else {
            AccessMinecraft.getInstance().overlay.addChatMessage("Doesn't implement Color :(");
        }
        return false;
    }
}
