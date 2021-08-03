package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class ItemBrush extends gm {
    protected ItemBrush(int i) {
        super(i);
    }

    public boolean a(iz itemstack, Player entityplayer, Level world, int i, int j, int k, int l) {
        Tile b = Tile.m[world.a(i, j, k)];
        if (b != null && b instanceof IBlockColor) {
            ((IBlockColor)b).incrementColor(world, i, j, k);
            world.j(i, j, k);
        } else {
            Minecraft.minecraftInstance.v.a("Doesn't implement Color :(");
        }
        return false;
    }
}
