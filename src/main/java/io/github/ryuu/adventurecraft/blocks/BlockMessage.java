package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityMessage;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockMessage extends TileWithEntity {
    protected BlockMessage(int i, int j) {
        super(i, j, Material.AIR);
    }

    protected TileEntity a_() {
        return new TileEntityMessage();
    }

    public boolean c() {
        return false;
    }

    public Box e(Level world, int i, int j, int k) {
        return null;
    }

    public boolean shouldRender(xp blockAccess, int i, int j, int k) {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityMessage obj = (TileEntityMessage)world.b(i, j, k);
        if (!obj.message.equals(""))
            Minecraft.minecraftInstance.v.a(obj.message);
        if (!obj.sound.equals(""))
            world.a(i + 0.5D, j + 0.5D, k + 0.5D, obj.sound, 1.0F, 1.0F);
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityMessage obj = (TileEntityMessage)world.b(i, j, k);
            GuiMessage.showUI(world, obj);
            return true;
        }
        return false;
    }

    public boolean v_() {
        return DebugMode.active;
    }
}
