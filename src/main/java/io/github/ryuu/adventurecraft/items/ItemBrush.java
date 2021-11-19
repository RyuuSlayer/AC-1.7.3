package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemBrush extends ItemType {
    protected ItemBrush(int i) {
        super(i);
    }

    @Override
    public boolean useOnTile(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        Tile b = Tile.BY_ID[world.getTileId(i, j, k)];
        if (b != null && b instanceof IBlockColor) {
            ((IBlockColor) b).incrementColor(world, i, j, k);
            world.j(i, j, k); // probably method_243 but not sure
        } else {
            Minecraft.minecraftInstance.v.a("Doesn't implement Color :(");
        }
        return false;
    }
}
