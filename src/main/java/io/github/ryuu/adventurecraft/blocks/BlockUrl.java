package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityUrl;
import io.github.ryuu.adventurecraft.gui.GuiUrl;
import io.github.ryuu.adventurecraft.gui.GuiUrlRequest;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockUrl extends TileWithEntity {
    protected BlockUrl(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityUrl();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityUrl obj = (TileEntityUrl) world.getTileEntity(i, j, k);
        if (obj.url != null && !obj.url.equals(""))
            GuiUrlRequest.showUI(obj.url);
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityUrl obj = (TileEntityUrl) world.getTileEntity(i, j, k);
            GuiUrl.showUI(world, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
