package io.github.ryuu.adventurecraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    protected MixinTileEntity createTileEntity() {
        return new TileEntityScript();
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
    public int method_1626(TileView iblockaccess, int i, int j, int k, int l) {
        return super.method_1626(iblockaccess, i, j, k, l);
    }

    @Override
    public boolean method_1576() {
        return DebugMode.active;
    }

    @Override
    public boolean canBeTriggered() {
        return true;
    }

    @Override
    public void onTriggerActivated(MixinLevel world, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) world.getTileEntity(i, j, k);
        if (!obj.onTriggerScriptFile.equals((Object) "")) {
            world.scriptHandler.runScript(obj.onTriggerScriptFile, obj.scope);
        }
        obj.isActivated = true;
    }

    @Override
    public void onTriggerDeactivated(MixinLevel world, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) world.getTileEntity(i, j, k);
        if (!obj.onDetriggerScriptFile.equals((Object) "")) {
            world.scriptHandler.runScript(obj.onDetriggerScriptFile, obj.scope);
        }
        obj.isActivated = false;
    }

    @Override
    public boolean activate(MixinLevel level, int x, int y, int z, MixinPlayer player) {
        if (DebugMode.active) {
            TileEntityScript obj = (TileEntityScript) level.getTileEntity(x, y, z);
            GuiScript.showUI(obj);
        }
        return true;
    }
}
