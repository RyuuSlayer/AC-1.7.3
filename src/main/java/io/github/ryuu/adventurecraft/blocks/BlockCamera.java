package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.Minecraft;
import io.github.ryuu.adventurecraft.entities.tile.TileEntityCamera;
import io.github.ryuu.adventurecraft.gui.GuiCameraBlock;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

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
        TileEntityCamera obj = (TileEntityCamera) world.getTileEntity(i, j, k);
        obj.loadCamera();
        Minecraft.minecraftInstance.cutsceneCamera.startCamera();
        Minecraft.minecraftInstance.cameraActive = true;
        Minecraft.minecraftInstance.cameraPause = obj.pauseGame;
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            Minecraft.minecraftInstance.overlay.addChatMessage("Set Active Editing Camera");
            TileEntityCamera obj = (TileEntityCamera) world.getTileEntity(i, j, k);
            Minecraft.minecraftInstance.activeCutsceneCamera = obj.camera;
            obj.camera.loadCameraEntities();
            GuiCameraBlock.showUI(obj);
            return true;
        }
        return false;
    }

    public boolean v_() {
        return DebugMode.active;
    }
}
