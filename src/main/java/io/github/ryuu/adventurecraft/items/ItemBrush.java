package io.github.ryuu.adventurecraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.tile.Tile;

public class ItemBrush extends MixinItemType {

    protected ItemBrush(int id) {
        super(id);
    }

    @Override
    public boolean useOnTile(MixinItemInstance item, MixinPlayer player, MixinLevel level, int x, int y, int z, int facing) {
        MixinTile b = Tile.BY_ID[level.getTileId(x, y, z)];
        if (b != null && b instanceof IBlockColor) {
            ((IBlockColor) ((Object) b)).incrementColor(level, x, y, z);
            level.method_243(x, y, z);
        } else {
            Minecraft.minecraftInstance.overlay.addChatMessage("Doesn't implement Color :(");
        }
        return false;
    }
}
