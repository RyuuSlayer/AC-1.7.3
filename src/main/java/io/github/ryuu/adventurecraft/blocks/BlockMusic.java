package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockMusic extends TileWithEntity {

    protected BlockMusic(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityMusic();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(MixinLevel level, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
        TileEntityMusic obj = (TileEntityMusic) world.getTileEntity(i, j, k);
        if (!obj.musicName.equals((Object) "")) {
            Minecraft.minecraftInstance.soundHelper.playMusicFromStreaming(obj.musicName, obj.fadeOut, obj.fadeIn);
        } else {
            Minecraft.minecraftInstance.soundHelper.stopMusic();
        }
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active && player.getHeldItem() != null && player.getHeldItem().itemId == Items.cursor.id) {
            TileEntityMusic obj = (TileEntityMusic) level.getTileEntity(x, y, z);
            GuiMusic.showUI(level, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
