package io.github.ryuu.adventurecraft.blocks;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityScript;
import io.github.ryuu.adventurecraft.extensions.level.ExLevel;
import io.github.ryuu.adventurecraft.extensions.tile.ExTile;
import io.github.ryuu.adventurecraft.gui.GuiScript;
import io.github.ryuu.adventurecraft.util.DebugMode;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import net.minecraft.level.TileView;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.Box;

public class BlockScript extends TileWithEntity implements AcTriggerTile, AcRenderConditionTile {

    protected BlockScript(int i, int j) {
        super(i, j, Material.AIR);
        this.hardness(5.0f);
        this.sounds(Tile.METAL_SOUNDS);
        ((ExTile) this).setTextureNum(2);
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
    public Box getCollisionShape(Level level, int x, int y, int z) {
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
    public void onTriggerActivated(Level level, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) level.getTileEntity(i, j, k);
        if (!obj.onTriggerScriptFile.equals("")) {
            ((ExLevel) level).getScriptHandler().runScript(obj.onTriggerScriptFile, obj.scope);
        }
        obj.isActivated = true;
    }

    @Override
    public void onTriggerDeactivated(Level level, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) level.getTileEntity(i, j, k);
        if (!obj.onDetriggerScriptFile.equals("")) {
            ((ExLevel) level).getScriptHandler().runScript(obj.onDetriggerScriptFile, obj.scope);
        }
        obj.isActivated = false;
    }

    @Override
    public boolean activate(Level level, int x, int y, int z, Player player) {
        if (DebugMode.active) {
            TileEntityScript obj = (TileEntityScript) level.getTileEntity(x, y, z);
            GuiScript.showUI(obj);
        }
        return true;
    }
}
