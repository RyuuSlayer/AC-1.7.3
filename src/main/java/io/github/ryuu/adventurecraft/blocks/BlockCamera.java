package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.gui.GuiCameraBlock;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityCamera;

public class BlockCamera extends TileWithEntity {

    protected BlockCamera(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityCamera();
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
        TileEntityCamera obj = (TileEntityCamera) world.getTileEntity(i, j, k);
        obj.loadCamera();
        Minecraft.minecraftInstance.cutsceneCamera.startCamera();
        Minecraft.minecraftInstance.cameraActive = true;
        Minecraft.minecraftInstance.cameraPause = obj.pauseGame;
    }

    @Override
    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active) {
            Minecraft.minecraftInstance.overlay.addChatMessage("Set Active Editing Camera");
            TileEntityCamera obj = (TileEntityCamera) level.getTileEntity(x, y, z);
            Minecraft.minecraftInstance.activeCutsceneCamera = obj.camera;
            obj.camera.loadCameraEntities();
            GuiCameraBlock.showUI(obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }
}
