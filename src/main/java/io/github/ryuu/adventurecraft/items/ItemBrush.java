package io.github.ryuu.adventurecraft.items;/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
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
            ((IBlockColor) ((Object) b)).incrementColor(level, x, y, z);
            level.method_243(x, y, z);
        } else {
            Minecraft.minecraftInstance.overlay.addChatMessage("Doesn't implement Color :(");
        }
        return false;
    }
}
