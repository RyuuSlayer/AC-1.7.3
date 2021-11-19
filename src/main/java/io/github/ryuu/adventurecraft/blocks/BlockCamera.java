package io.github.ryuu.adventurecraft.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockCamera extends TileWithEntity {

    protected BlockCamera(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected MixinTileEntity createTileEntity() {
        return new TileEntityCamera();
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
        TileEntityCamera obj = (TileEntityCamera) world.getTileEntity(i, j, k);
        obj.loadCamera();
        Minecraft.minecraftInstance.cutsceneCamera.startCamera();
        Minecraft.minecraftInstance.cameraActive = true;
        Minecraft.minecraftInstance.cameraPause = obj.pauseGame;
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
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
