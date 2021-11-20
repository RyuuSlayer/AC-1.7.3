package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockMessage extends TileWithEntity {

    protected BlockMessage(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityMessage();
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
        TileEntityMessage obj = (TileEntityMessage) world.getTileEntity(i, j, k);
        if (!obj.message.equals((Object) "")) {
            Minecraft.minecraftInstance.overlay.addChatMessage(obj.message);
        }
        if (!obj.sound.equals((Object) "")) {
            world.playSound((double) i + 0.5, (double) j + 0.5, (double) k + 0.5, obj.sound, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active) {
            TileEntityMessage obj = (TileEntityMessage) level.getTileEntity(x, y, z);
            GuiMessage.showUI(level, obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
