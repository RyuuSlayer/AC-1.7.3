package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.gui.GuiMessage;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityMessage;

public class BlockMessage extends TileWithEntity {

    protected BlockMessage(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityMessage();
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
        TileEntityMessage obj = (TileEntityMessage) world.getTileEntity(i, j, k);
        if (!obj.message.equals((Object) "")) {
            Minecraft.minecraftInstance.overlay.addChatMessage(obj.message);
        }
        if (!obj.sound.equals((Object) "")) {
            world.playSound((double) i + 0.5, (double) j + 0.5, (double) k + 0.5, obj.sound, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
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
