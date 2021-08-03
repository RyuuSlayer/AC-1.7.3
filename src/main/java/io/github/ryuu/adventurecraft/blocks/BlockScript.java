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

    protected TileEntity a_() {
        return new TileEntityScript();
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

    public int a(TileView iblockaccess, int i, int j, int k, int l) {
        return super.a(iblockaccess, i, j, k, l);
    }

    public boolean v_() {
        return DebugMode.active;
    }

    public boolean canBeTriggered() {
        return true;
    }

    public void onTriggerActivated(Level world, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) world.b(i, j, k);
        if (!obj.onTriggerScriptFile.equals(""))
            world.scriptHandler.runScript(obj.onTriggerScriptFile, obj.scope);
        obj.isActivated = true;
    }

    public void onTriggerDeactivated(Level world, int i, int j, int k) {
        TileEntityScript obj = (TileEntityScript) world.b(i, j, k);
        if (!obj.onDetriggerScriptFile.equals(""))
            world.scriptHandler.runScript(obj.onDetriggerScriptFile, obj.scope);
        obj.isActivated = false;
    }

    public boolean a(Level world, int i, int j, int k, Player entityplayer) {
        if (DebugMode.active) {
            TileEntityScript obj = (TileEntityScript) world.b(i, j, k);
            GuiScript.showUI(obj);
        }
        return true;
    }
}
