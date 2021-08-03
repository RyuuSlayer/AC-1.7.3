package io.github.ryuu.adventurecraft.items;

import io.github.ryuu.adventurecraft.blocks.IBlockColor;
import net.minecraft.client.Minecraft;

public class ItemBrush extends gm {
    protected ItemBrush(int i) {
        super(i);
    }

    public boolean a(iz itemstack, gs entityplayer, fd world, int i, int j, int k, int l) {
        uu b = uu.m[world.a(i, j, k)];
        if (b != null && b instanceof IBlockColor) {
            ((IBlockColor)b).incrementColor(world, i, j, k);
            world.j(i, j, k);
        } else {
            Minecraft.minecraftInstance.v.a("Doesn't implement Color :(");
        }
        return false;
    }
}
