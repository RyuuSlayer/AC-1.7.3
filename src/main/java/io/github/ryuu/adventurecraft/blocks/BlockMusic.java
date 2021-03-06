package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMusic;
import io.github.ryuu.adventurecraft.extensions.client.sound.ExSoundHelper;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiMusic;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockMusic extends TileWithEntity implements AcTriggerTile, AcRenderConditionTile {

    protected BlockMusic(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(1.5f);
        this.blastResistance(10.0f);
        this.sounds(Tile.PISTON_SOUNDS);
        ((ExTile) this).setTextureNum(2);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityMusic();
    }

    @Override
    public boolean isFullOpaque() {
        return false;
    }

    @Override
    public Box getCollisionShape(Level level, int x, int y, int z) {
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
    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityMusic obj = (TileEntityMusic) world.getTileEntity(i, j, k);
        ExSoundHelper soundHelper = (ExSoundHelper) AccessMinecraft.getInstance().soundHelper;
        if (!obj.musicName.equals("")) {
            soundHelper.playMusicFromStreaming(obj.musicName, obj.fadeOut, obj.fadeIn);
        } else {
            soundHelper.stopMusic();
        }
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
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
