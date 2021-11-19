package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityScript;
import io.github.ryuu.adventurecraft.gui.GuiScript;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockScript extends TileWithEntity {
    protected BlockScript(int i, int j) {
        super(i, j, Material.AIR);
    }

    @Override
    protected TileEntity createTileEntity() {
        return new TileEntityScript();
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

    @Override
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        return super.method_1626(iblockaccess, i, j, k, l);
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) world.getTileEntity(i, j, k);
        if (!obj.onTriggerScriptFile.equals(""))
            world.scriptHandler.runScript(obj.onTriggerScriptFile, obj.scope);
        obj.isActivated = true;
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) world.getTileEntity(i, j, k);
        if (!obj.onDetriggerScriptFile.equals(""))
            world.scriptHandler.runScript(obj.onDetriggerScriptFile, obj.scope);
        obj.isActivated = false;
    }

    @Override
    public boolean activate(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityScript obj = (TileEntityScript) world.getTileEntity(i, j, k);
            GuiScript.showUI(obj);
        }
        return true;
    }
}
