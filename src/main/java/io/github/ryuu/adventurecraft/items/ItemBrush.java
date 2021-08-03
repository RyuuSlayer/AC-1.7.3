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

    public boolean a(ItemInstance itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        Tile b = Tile.m[world.a(i, j, k)];
        if (b != null && b instanceof IBlockColor) {
            ((IBlockColor) b).incrementColor(world, i, j, k);
            world.j(i, j, k);
        } else {
            Minecraft.minecraftInstance.v.a("Doesn't implement Color :(");
        }
        return false;
    }
}
