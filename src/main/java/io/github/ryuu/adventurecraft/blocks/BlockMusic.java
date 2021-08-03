package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMusic;
import io.github.ryuu.adventurecraft.gui.GuiMusic;
import io.github.ryuu.adventurecraft.items.Items;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockMusic extends TileWithEntity {
    protected BlockMusic(int i, int j) {
        super(i, j, Material.AIR);
    }

    protected TileEntity a_() {
        return new TileEntityMusic();
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(TileView blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityMusic obj = (TileEntityMusic) world.b(i, j, k);
        if (!obj.musicName.equals("")) {
            Minecraft.minecraftInstance.B.playMusicFromStreaming(obj.musicName, obj.fadeOut, obj.fadeIn);
        } else {
            Minecraft.minecraftInstance.B.stopMusic();
        }
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active && entityplayer.G() != null && (entityplayer.G()).c == Items.cursor.bf) {
            TileEntityMusic obj = (TileEntityMusic) world.b(i, j, k);
            GuiMusic.showUI(world, obj);
            return true;
        }
        return false;
    }

    public boolean v_() {
        return DebugMode.active;
    }
}
